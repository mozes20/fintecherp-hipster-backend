package com.fintech.erp.service.document;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentumTemplate;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekDokumentumTemplateRepository;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.OsztalekfizetesiKozgyulesekDokumentumService;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumDTO;
import com.fintech.erp.web.rest.vm.DocumentGenerationRequest;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for generating assembly meeting (közgyűlési) documents from DOCX templates.
 *
 * <p>Flow:
 * <ol>
 *   <li>Load the template record from DB → resolve the .docx file path
 *   <li>Build the placeholder map via {@link OsztalekfizetesiKozgyulesekTemplatePlaceholderService}
 *   <li>Merge manual overrides from the request
 *   <li>Fill the template via {@link DocxTemplateEngine}
 *   <li>Optionally convert to PDF via {@link PdfConversionService}
 *   <li>If persist=true → save bytes to disk + persist an {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum} record
 *   <li>Return {@link GeneratedDocumentResult}
 * </ol>
 */
@Service
@Transactional
public class OsztalekfizetesiKozgyulesekDocumentGenerationService {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekDocumentGenerationService.class);

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private static final String DEFAULT_TIPUS = "GENERALT_JEGYZOKONYV";

    private final OsztalekfizetesiKozgyulesekDokumentumTemplateRepository templateRepository;
    private final OsztalekfizetesiKozgyulesekDokumentumService dokumentumService;
    private final OsztalekfizetesiKozgyulesekTemplatePlaceholderService placeholderService;
    private final DocxTemplateEngine docxTemplateEngine;
    private final PdfConversionService pdfConversionService;
    private final OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository;

    public OsztalekfizetesiKozgyulesekDocumentGenerationService(
        OsztalekfizetesiKozgyulesekDokumentumTemplateRepository templateRepository,
        OsztalekfizetesiKozgyulesekDokumentumService dokumentumService,
        OsztalekfizetesiKozgyulesekTemplatePlaceholderService placeholderService,
        DocxTemplateEngine docxTemplateEngine,
        PdfConversionService pdfConversionService,
        OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository
    ) {
        this.templateRepository = templateRepository;
        this.dokumentumService = dokumentumService;
        this.placeholderService = placeholderService;
        this.docxTemplateEngine = docxTemplateEngine;
        this.pdfConversionService = pdfConversionService;
        this.kozgyulesekRepository = kozgyulesekRepository;
    }

    /**
     * Generate a document for the given közgyűlés.
     *
     * @param kozgyulesId the ID of the OsztalekfizetesiKozgyulesek record
     * @param request     contains templateId, optional placeholder overrides, format, persist flag, etc.
     * @return the generated document bytes + metadata
     */
    public GeneratedDocumentResult<OsztalekfizetesiKozgyulesekDokumentumDTO> generateFromTemplate(
        Long kozgyulesId,
        DocumentGenerationRequest request
    ) throws IOException {
        LOG.debug("Generating kozgyulesi document for kozgyules {} using template {}", kozgyulesId, request.getTemplateId());

        OsztalekfizetesiKozgyulesekDokumentumTemplate template;
        if (request.getTemplateId() != null) {
            template = templateRepository
                .findById(request.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("A megadott sablon nem található: " + request.getTemplateId()));
        } else {
            template = templateRepository
                .findFirstByDokumentumTipusaOrderByUtolsoModositasDesc(DEFAULT_TIPUS)
                .orElseGet(() ->
                    templateRepository
                        .findAll()
                        .stream()
                        .max(
                            java.util.Comparator.comparing(
                                OsztalekfizetesiKozgyulesekDokumentumTemplate::getUtolsoModositas,
                                java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())
                            )
                        )
                        .orElseThrow(() -> new IllegalArgumentException("Nincs elérhető dokumentum sablon. Kérem töltsön fel egy sablont."))
                );
        }

        Path templatePath = getTemplateBaseDir().resolve(template.getFajlUtvonal()).normalize();
        if (!Files.exists(templatePath)) {
            throw new IOException("A sablon fájl nem található: " + templatePath);
        }

        // Build auto placeholders from entity data
        Map<String, String> replacements = new LinkedHashMap<>(placeholderService.build(kozgyulesId));
        // Merge manual overrides (request body wins)
        if (request.getPlaceholders() != null && !request.getPlaceholders().isEmpty()) {
            replacements.putAll(request.getPlaceholders());
        }

        byte[] filledDocx = docxTemplateEngine.populateTemplate(templatePath, replacements);

        String baseFileName = sanitizeFileName(request.getDokumentumNev() != null ? request.getDokumentumNev() : template.getTemplateNev());
        DocumentFormat effectiveFormat = request.getFormat() != null ? request.getFormat() : DocumentFormat.DOCX;
        byte[] outputBytes = effectiveFormat == DocumentFormat.PDF
            ? pdfConversionService.convertDocxToPdf(filledDocx, baseFileName)
            : filledDocx;

        String fileName = baseFileName + effectiveFormat.getExtension();
        OsztalekfizetesiKozgyulesekDokumentumDTO persisted = null;

        if (request.isPersist()) {
            String tipusKod = request.getDokumentumTipus() != null && !request.getDokumentumTipus().isBlank()
                ? request.getDokumentumTipus()
                : (template.getDokumentumTipusa() != null ? template.getDokumentumTipusa() : DEFAULT_TIPUS);
            persisted = persistDocument(outputBytes, effectiveFormat, kozgyulesId, fileName, tipusKod);
        }

        return new GeneratedDocumentResult<>(outputBytes, fileName, effectiveFormat.getContentType(), persisted);
    }

    // -------------------------------------------------------------------------

    private OsztalekfizetesiKozgyulesekDokumentumDTO persistDocument(
        byte[] data,
        DocumentFormat format,
        Long kozgyulesId,
        String dokumentumNev,
        String dokumentumTipusa
    ) throws IOException {
        Path dir = getDocumentBaseDir(kozgyulesId);
        Files.createDirectories(dir);
        String storedFajlNev = "generated_" + FILE_TS.format(LocalDateTime.now()) + format.getExtension();
        Path targetPath = dir.resolve(storedFajlNev).normalize();
        Files.write(targetPath, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        OsztalekfizetesiKozgyulesekDokumentumDTO dto = new OsztalekfizetesiKozgyulesekDokumentumDTO();
        dto.setDokumentumTipusa(dokumentumTipusa);
        dto.setDokumentumNev(dokumentumNev);
        dto.setFajlNev(storedFajlNev);
        dto.setDokumentumUrl(dir.relativize(targetPath).toString().replace("\\", "/"));
        dto.setFeltoltesIdeje(Instant.now());

        // Link to parent közgyűlés (only ID needed for mapper)
        OsztalekfizetesiKozgyulesekDTO refDto = new OsztalekfizetesiKozgyulesekDTO();
        refDto.setId(kozgyulesId);
        dto.setKozgyules(refDto);

        OsztalekfizetesiKozgyulesekDokumentumDTO saved = dokumentumService.save(dto);

        // Update the parent entity's generaltDokumentumNev/Url fields so the
        // frontend (which reads those fields via GET /api/osztalekfizetesi-kozgyuleseks/{id})
        // immediately sees the newly generated document.
        kozgyulesekRepository
            .findById(kozgyulesId)
            .ifPresent(kozgyules -> {
                kozgyules.setGeneraltDokumentumNev(storedFajlNev);
                kozgyules.setGeneraltDokumentumUrl(storedFajlNev);
                kozgyulesekRepository.save(kozgyules);
                LOG.debug("Updated OsztalekfizetesiKozgyulesek {} generaltDokumentumNev to {}", kozgyulesId, storedFajlNev);
            });

        return saved;
    }

    private Path getTemplateBaseDir() {
        return Path.of("uploads", "templates", "osztalekfizetesi-kozgyulesek");
    }

    private Path getDocumentBaseDir(Long kozgyulesId) {
        return Path.of("uploads", "osztalekfizetesi-kozgyulesek", kozgyulesId.toString());
    }

    private String sanitizeFileName(String input) {
        if (input == null || input.isBlank()) {
            return "kozgyulesi_dokumentum";
        }
        return input.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }
}

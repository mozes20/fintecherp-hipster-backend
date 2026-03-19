package com.fintech.erp.service.document;

import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import com.fintech.erp.repository.AlvallalkozoiElszamolasokRepository;
import com.fintech.erp.service.AlvallalkozoiTigDokumentumokService;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
import com.fintech.erp.service.dto.AlvallalkozoiTigDokumentumokDTO;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Generates Alvállalkozói TIG Word documents from a DOCX template.
 */
@Service
@Transactional
public class AlvallalkozoiTigDocumentGenerationService {

    private static final Logger LOG = LoggerFactory.getLogger(AlvallalkozoiTigDocumentGenerationService.class);
    private static final String TEMPLATE_DIR = "uploads/templates/alvallalkozoi-tig";
    private static final String OUTPUT_DIR = "uploads/alvallalkozoi-tig";
    private static final DateTimeFormatter FILE_DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final AlvallalkozoiElszamolasokRepository elszamolasokRepository;
    private final AlvallalkozoiTigDokumentumokService dokumentumokService;
    private final DocxTemplateEngine docxTemplateEngine;
    private final AlvallalkozoiTigTemplatePlaceholderService placeholderService;

    public AlvallalkozoiTigDocumentGenerationService(
        AlvallalkozoiElszamolasokRepository elszamolasokRepository,
        AlvallalkozoiTigDokumentumokService dokumentumokService,
        DocxTemplateEngine docxTemplateEngine,
        AlvallalkozoiTigTemplatePlaceholderService placeholderService
    ) {
        this.elszamolasokRepository = elszamolasokRepository;
        this.dokumentumokService = dokumentumokService;
        this.docxTemplateEngine = docxTemplateEngine;
        this.placeholderService = placeholderService;
    }

    /**
     * Generates a TIG DOCX from the latest uploaded template for the given elszamolas.
     *
     * @param elszamolasId the ID of the AlvallalkozoiElszamolasok record
     * @param persist      if true, persists the generated document as AlvallalkozoiTigDokumentumok
     * @return result containing the file bytes, filename and content-type
     */
    public GeneratedDocumentResult<AlvallalkozoiTigDokumentumokDTO> generateTig(Long elszamolasId, boolean persist) throws IOException {
        LOG.debug("Generating Alvallalkozoi TIG for elszamolas {}", elszamolasId);

        AlvallalkozoiElszamolasok elszamolas = elszamolasokRepository
            .findById(elszamolasId)
            .orElseThrow(() -> new IllegalArgumentException("Alvállalkozói elszámolás nem található: " + elszamolasId));

        Path templatePath = findLatestTemplate();

        Map<String, String> placeholders = placeholderService.build(elszamolas);

        byte[] filledDocx = docxTemplateEngine.populateTemplate(templatePath, placeholders);

        String megrendelesSzam = Optional.ofNullable(elszamolas.getMegrendeles())
            .map(m -> sanitize(m.getMegrendelesSzam()))
            .orElse("ismeretlen");
        String dateStr = FILE_DATE_FMT.format(LocalDate.now());
        String fileName = "TIG_" + megrendelesSzam + "_" + dateStr + ".docx";

        AlvallalkozoiTigDokumentumokDTO persistedDto = null;
        if (persist) {
            persistedDto = persistDocument(filledDocx, fileName, elszamolasId);
        }

        return new GeneratedDocumentResult<>(
            filledDocx,
            fileName,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            persistedDto
        );
    }

    private Path findLatestTemplate() throws IOException {
        Path templateDir = Path.of(TEMPLATE_DIR);
        if (!Files.isDirectory(templateDir)) {
            throw new IOException("A TIG sablon könyvtár nem létezik: " + TEMPLATE_DIR);
        }
        try (Stream<Path> files = Files.list(templateDir)) {
            List<Path> templates = files
                .filter(p -> p.toString().endsWith(".docx") || p.toString().endsWith(".doc"))
                .sorted(Comparator.comparingLong(p -> {
                    try { return Files.getLastModifiedTime(p).toMillis(); } catch (IOException e) { return 0L; }
                }))
                .toList();
            if (templates.isEmpty()) {
                throw new IOException("Nincs feltöltött TIG sablon a könyvtárban: " + TEMPLATE_DIR);
            }
            return templates.get(templates.size() - 1);
        }
    }

    private AlvallalkozoiTigDokumentumokDTO persistDocument(byte[] data, String fileName, Long elszamolasId) throws IOException {
        Path outputDir = Path.of(OUTPUT_DIR);
        Files.createDirectories(outputDir);
        String storedFilename = "tig_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".docx";
        Path targetPath = outputDir.resolve(storedFilename).normalize();
        Files.write(targetPath, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        AlvallalkozoiTigDokumentumokDTO dto = new AlvallalkozoiTigDokumentumokDTO();
        dto.setDokumentumTipusa("GENERALT_WORD");
        dto.setDokumentum(OUTPUT_DIR + "/" + storedFilename);
        AlvallalkozoiElszamolasokDTO elszRef = new AlvallalkozoiElszamolasokDTO();
        elszRef.setId(elszamolasId);
        dto.setElszamolas(elszRef);

        return dokumentumokService.save(dto);
    }

    private static String sanitize(String input) {
        if (input == null || input.isBlank()) return "ismeretlen";
        return input.replaceAll("[^a-zA-Z0-9_\\-]", "_");
    }
}

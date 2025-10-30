package com.fintech.erp.service.document;

import com.fintech.erp.domain.MegrendelesDokumentumTemplate;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import com.fintech.erp.repository.MegrendelesDokumentumTemplateRepository;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.service.MegrendelesDokumentumokService;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MegrendelesDocumentGenerationService {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDocumentGenerationService.class);

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final MegrendelesDokumentumTemplateRepository templateRepository;
    private final MegrendelesDokumentumokService dokumentumokService;
    private final MegrendelesekRepository megrendelesekRepository;
    private final DocxTemplateEngine docxTemplateEngine;
    private final MegrendelesTemplatePlaceholderService placeholderService;

    public MegrendelesDocumentGenerationService(
        MegrendelesDokumentumTemplateRepository templateRepository,
        MegrendelesDokumentumokService dokumentumokService,
        MegrendelesekRepository megrendelesekRepository,
        DocxTemplateEngine docxTemplateEngine,
        MegrendelesTemplatePlaceholderService placeholderService
    ) {
        this.templateRepository = templateRepository;
        this.dokumentumokService = dokumentumokService;
        this.megrendelesekRepository = megrendelesekRepository;
        this.docxTemplateEngine = docxTemplateEngine;
        this.placeholderService = placeholderService;
    }

    public GeneratedDocumentResult<MegrendelesDokumentumokDTO> generateFromTemplate(
        Long templateId,
        Long megrendelesId,
        Map<String, String> placeholders,
        DocumentFormat format,
        boolean persist,
        String dokumentumNev
    ) throws IOException {
        LOG.debug("Generating order document from template {} for megrendeles {}", templateId, megrendelesId);
        MegrendelesDokumentumTemplate template = templateRepository
            .findById(templateId)
            .orElseThrow(() -> new IllegalArgumentException("A megadott megrendelés sablon nem található"));

        Path templatePath = getTemplateBaseDir().resolve(template.getFajlUtvonal()).normalize();
        if (!Files.exists(templatePath)) {
            throw new IOException("A sablon fájl nem található: " + templatePath);
        }

        Megrendelesek megrendeles = null;
        if (megrendelesId != null) {
            megrendeles = megrendelesekRepository
                .findById(megrendelesId)
                .orElseThrow(() -> new IllegalArgumentException("A megadott megrendeles nem talalhato"));
        }

        Map<String, String> replacements = new LinkedHashMap<>(placeholderService.build(megrendeles));
        if (placeholders != null && !placeholders.isEmpty()) {
            replacements.putAll(placeholders);
        }
        byte[] filledDocx = docxTemplateEngine.populateTemplate(templatePath, replacements);

        DocumentFormat effectiveFormat = format != null ? format : DocumentFormat.DOCX;
        byte[] outputBytes = effectiveFormat == DocumentFormat.PDF ? docxTemplateEngine.convertToPdf(filledDocx) : filledDocx;

        String baseFileName = sanitizeFileName(dokumentumNev != null ? dokumentumNev : template.getTemplateNev());
        String fileName = baseFileName + effectiveFormat.getExtension();

        MegrendelesDokumentumokDTO persisted = null;
        if (persist) {
            persisted = persistGeneratedDocument(outputBytes, effectiveFormat, megrendelesId);
            markOrderAsGenerated(megrendeles);
        }

        return new GeneratedDocumentResult<>(outputBytes, fileName, effectiveFormat.getContentType(), persisted);
    }

    private MegrendelesDokumentumokDTO persistGeneratedDocument(byte[] data, DocumentFormat format, Long megrendelesId) throws IOException {
        if (megrendelesId == null) {
            throw new IllegalArgumentException("A megrendelés azonosító megadása kötelező a mentéshez");
        }
        Path dir = getDocumentBaseDir(megrendelesId);
        Files.createDirectories(dir);
        String storedFilename = "generated_" + FILE_TS.format(LocalDateTime.now()) + format.getExtension();
        Path targetPath = dir.resolve(storedFilename).normalize();
        Files.write(targetPath, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        MegrendelesDokumentumokDTO dto = new MegrendelesDokumentumokDTO();
        dto.setDokumentumTipusa(mapFormatToType(format));
        dto.setDokumentum(getRelativePath(dir, targetPath));
        dto.setDokumentumUrl(buildRelativeUrl(megrendelesId, storedFilename));
        dto.setDokumentumAzonosito(null);
        MegrendelesekDTO orderRef = new MegrendelesekDTO();
        orderRef.setId(megrendelesId);
        orderRef.setMegrendelesDokumentumGeneralta(MegrendelesDokumentumEredet.GENERALTA);
        dto.setMegrendeles(orderRef);

        return dokumentumokService.save(dto);
    }

    private void markOrderAsGenerated(Megrendelesek megrendeles) {
        if (megrendeles == null || megrendeles.getId() == null) {
            return;
        }
        megrendeles.setMegrendelesDokumentumGeneralta(MegrendelesDokumentumEredet.GENERALTA);
        megrendelesekRepository.save(megrendeles);
    }

    private Path getTemplateBaseDir() {
        return Path.of("uploads", "templates", "megrendelesek");
    }

    private Path getDocumentBaseDir(Long megrendelesId) {
        return Path.of("uploads", "megrendelesek", megrendelesId.toString());
    }

    private String buildRelativeUrl(Long megrendelesId, String fileName) {
        return "uploads/megrendelesek/" + megrendelesId + "/" + fileName;
    }

    private String getRelativePath(Path baseDir, Path filePath) {
        return baseDir.relativize(filePath).toString().replace("\\", "/");
    }

    private MegrendelesDokumentumTipus mapFormatToType(DocumentFormat format) {
        return switch (format) {
            case PDF -> MegrendelesDokumentumTipus.GENERALTA_PDF;
            case DOCX -> MegrendelesDokumentumTipus.GENERALTA_WORD;
        };
    }

    private String sanitizeFileName(String input) {
        if (input == null || input.isBlank()) {
            return "megrendeles";
        }
        return input.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }
}

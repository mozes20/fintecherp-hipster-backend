package com.fintech.erp.service.document;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate;
import com.fintech.erp.repository.SzerzodesesJogviszonyDokumentumTemplateRepository;
import com.fintech.erp.service.SzerzodesesJogviszonyDokumentumService;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumDTO;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DocumentGenerationService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentGenerationService.class);

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final SzerzodesesJogviszonyDokumentumTemplateRepository templateRepository;
    private final SzerzodesesJogviszonyDokumentumService dokumentumService;
    private final DocxTemplateEngine docxTemplateEngine;

    public DocumentGenerationService(
        SzerzodesesJogviszonyDokumentumTemplateRepository templateRepository,
        SzerzodesesJogviszonyDokumentumService dokumentumService,
        DocxTemplateEngine docxTemplateEngine
    ) {
        this.templateRepository = templateRepository;
        this.dokumentumService = dokumentumService;
        this.docxTemplateEngine = docxTemplateEngine;
    }

    public GeneratedDocumentResult generateFromTemplate(
        Long templateId,
        Long szerzodesesJogviszonyId,
        Map<String, String> placeholders,
        DocumentFormat format,
        boolean persist,
        String dokumentumNev,
        Long dokumentumTipusId
    ) throws IOException {
        LOG.debug("Generating document from template {} for jogviszony {}", templateId, szerzodesesJogviszonyId);
        SzerzodesesJogviszonyDokumentumTemplate template = templateRepository
            .findById(templateId)
            .orElseThrow(() -> new IllegalArgumentException("A megadott sablon nem található"));

        Path templatePath = getTemplateBaseDir().resolve(template.getFajlUtvonal()).normalize();
        if (!Files.exists(templatePath)) {
            throw new IOException("A sablon fájl nem található: " + templatePath);
        }

        Map<String, String> replacements = placeholders != null ? placeholders : Collections.emptyMap();
        byte[] filledDocx = docxTemplateEngine.populateTemplate(templatePath, replacements);

        DocumentFormat effectiveFormat = format != null ? format : DocumentFormat.DOCX;
        byte[] outputBytes = effectiveFormat == DocumentFormat.PDF ? docxTemplateEngine.convertToPdf(filledDocx) : filledDocx;

        String baseFileName = sanitizeFileName(dokumentumNev != null ? dokumentumNev : template.getTemplateNev());
        String fileName = baseFileName + effectiveFormat.getExtension();
        SzerzodesesJogviszonyDokumentumDTO persisted = null;

        if (persist) {
            Long tipusId = dokumentumTipusId != null
                ? dokumentumTipusId
                : (template.getDokumentumTipus() != null ? template.getDokumentumTipus().getId() : null);
            if (tipusId == null) {
                throw new IllegalArgumentException("Dokumentumtípus azonosító megadása kötelező a mentéshez");
            }
            persisted = persistGeneratedDocument(outputBytes, effectiveFormat, szerzodesesJogviszonyId, fileName, replacements, tipusId);
        }

        return new GeneratedDocumentResult(outputBytes, fileName, effectiveFormat.getContentType(), persisted);
    }

    private SzerzodesesJogviszonyDokumentumDTO persistGeneratedDocument(
        byte[] data,
        DocumentFormat format,
        Long jogviszonyId,
        String fileName,
        Map<String, String> placeholders,
        Long dokumentumTipusId
    ) throws IOException {
        Path dir = getDocumentBaseDir(jogviszonyId);
        Files.createDirectories(dir);
        String storedFilename = "generated_" + FILE_TS.format(LocalDateTime.now()) + format.getExtension();
        Path targetPath = dir.resolve(storedFilename).normalize();
        Files.write(targetPath, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        SzerzodesesJogviszonyDokumentumDTO dto = new SzerzodesesJogviszonyDokumentumDTO();
        dto.setDokumentumNev(fileName);
        dto.setLeiras(generateDescription(placeholders));
        dto.setFajlUtvonal(getRelativePath(dir, targetPath));
        dto.setContentType(format.getContentType());
        dto.setFeltoltesIdeje(Instant.now());
        dto.setSzerzodesesJogviszonyId(jogviszonyId);
        dto.setDokumentumTipusId(dokumentumTipusId);
        return dokumentumService.save(dto);
    }

    private Path getTemplateBaseDir() {
        return Path.of("uploads", "templates", "szerzodeses-jogviszonyok");
    }

    private Path getDocumentBaseDir(Long jogviszonyId) {
        return Path.of("uploads", "szerzodeses-jogviszonyok", jogviszonyId.toString());
    }

    private String getRelativePath(Path base, Path target) {
        return base.relativize(target).toString();
    }

    private String sanitizeFileName(String input) {
        if (input == null || input.isBlank()) {
            return "dokumentum";
        }
        return input.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }

    private String generateDescription(Map<String, String> placeholders) {
        if (placeholders == null || placeholders.isEmpty()) {
            return null;
        }
        return "Generált sablon kitöltve " + placeholders.size() + " mezővel";
    }
}

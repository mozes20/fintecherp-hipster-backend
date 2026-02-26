package com.fintech.erp.web.rest;

import com.fintech.erp.service.OsztalekfizetesiKozgyulesekDokumentumTemplateService;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumTemplateDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentumTemplate}.
 */
@RestController
@RequestMapping("/api/osztalekfizetesi-kozgyulesek-dokumentum-templatek")
public class OsztalekfizetesiKozgyulesekDokumentumTemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekDokumentumTemplateResource.class);

    private static final String ENTITY_NAME = "osztalekfizetesiKozgyulesekDokumentumTemplate";

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsztalekfizetesiKozgyulesekDokumentumTemplateService templateService;

    public OsztalekfizetesiKozgyulesekDokumentumTemplateResource(OsztalekfizetesiKozgyulesekDokumentumTemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * POST /upload : Upload a new DOCX template file.
     */
    @PostMapping("/upload")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> uploadTemplate(
        @RequestParam("file") MultipartFile file,
        @RequestParam("templateNev") String templateNev,
        @RequestParam(value = "templateLeiras", required = false) String templateLeiras,
        @RequestParam(value = "dokumentumTipusa", required = false) String dokumentumTipusa
    ) throws IOException {
        LOG.debug("REST request to upload osztalekfizetesi kozgyulesek template: {}", file.getOriginalFilename());
        validateDocx(file);
        Path uploadDir = getTemplateUploadDir();
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "template_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto = new OsztalekfizetesiKozgyulesekDokumentumTemplateDTO();
        dto.setTemplateNev(templateNev);
        dto.setTemplateLeiras(templateLeiras);
        dto.setDokumentumTipusa(dokumentumTipusa);
        dto.setFajlUtvonal(uploadDir.relativize(target).toString());
        dto.setUtolsoModositas(Instant.now());

        dto = templateService.save(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * GET /{id}/download : Download the template file.
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download osztalekfizetesi kozgyulesek template : {}", id);
        OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template nem található", ENTITY_NAME, "idnotfound"));
        Path filePath = resolveTemplatePath(dto.getFajlUtvonal());
        if (!Files.exists(filePath)) {
            throw new BadRequestAlertException("A sablon fájl nem található", ENTITY_NAME, "filenotfound");
        }
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName())
            .body(resource);
    }

    /**
     * POST : Create a new template (metadata only, without file upload).
     */
    @PostMapping("")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> createTemplate(
        @Valid @RequestBody OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to save OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new template cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dto = templateService.save(dto);
        return ResponseEntity.created(new URI("/api/osztalekfizetesi-kozgyulesek-dokumentum-templatek/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    /**
     * PUT /{id} : Update an existing template.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> updateTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update OsztalekfizetesiKozgyulesekDokumentumTemplate : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        dto = templateService.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    /**
     * PATCH /{id} : Partially update an existing template.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> partialUpdateTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update OsztalekfizetesiKozgyulesekDokumentumTemplate : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> result = templateService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    /**
     * GET : Get all templates (paginated).
     */
    @GetMapping("")
    public ResponseEntity<List<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO>> getAllTemplates(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of OsztalekfizetesiKozgyulesekDokumentumTemplates");
        Page<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> page = templateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /{id} : Get a template by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> getTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", id);
        Optional<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> dto = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    /**
     * DELETE /{id} : Delete a template (metadata + file).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", id);
        OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template nem található", ENTITY_NAME, "idnotfound"));
        templateService.delete(id);
        Path filePath = resolveTemplatePath(dto.getFajlUtvonal());
        Files.deleteIfExists(filePath);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private Path getTemplateUploadDir() {
        return Path.of("uploads", "templates", "osztalekfizetesi-kozgyulesek");
    }

    private Path resolveTemplatePath(String relativePath) {
        Path baseDir = getTemplateUploadDir();
        if (relativePath == null || relativePath.isBlank()) {
            return baseDir;
        }
        Path rel = Path.of(relativePath);
        return rel.isAbsolute() ? rel.normalize() : baseDir.resolve(rel).normalize();
    }

    private void validateDocx(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestAlertException("A feltöltött fájl üres", ENTITY_NAME, "emptyfile");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".docx")) {
            throw new BadRequestAlertException("Csak DOCX fájl tölthető fel", ENTITY_NAME, "invalidextension");
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".docx";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }
}

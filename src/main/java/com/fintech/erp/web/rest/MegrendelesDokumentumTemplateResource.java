package com.fintech.erp.web.rest;

import com.fintech.erp.service.MegrendelesDokumentumTemplateService;
import com.fintech.erp.service.dto.MegrendelesDokumentumTemplateDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.MegrendelesDokumentumTemplate}.
 */
@RestController
@RequestMapping("/api/megrendeles-dokumentum-templatek")
public class MegrendelesDokumentumTemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDokumentumTemplateResource.class);

    private static final String ENTITY_NAME = "megrendelesDokumentumTemplate";

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MegrendelesDokumentumTemplateService templateService;

    public MegrendelesDokumentumTemplateResource(MegrendelesDokumentumTemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MegrendelesDokumentumTemplateDTO> uploadTemplate(
        @RequestParam("file") MultipartFile file,
        @RequestParam("templateNev") String templateNev,
        @RequestParam(value = "templateLeiras", required = false) String templateLeiras,
        @RequestParam(value = "dokumentumTipusa", required = false) String dokumentumTipusa
    ) throws IOException {
        LOG.debug("REST request to upload megrendeles template: {}", file.getOriginalFilename());
        validateDocx(file);
        Path uploadDir = getTemplateUploadDir();
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "template_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        MegrendelesDokumentumTemplateDTO dto = new MegrendelesDokumentumTemplateDTO();
        dto.setTemplateNev(templateNev);
        dto.setTemplateLeiras(templateLeiras);
        dto.setDokumentumTipusa(dokumentumTipusa);
        dto.setFajlUtvonal(uploadDir.relativize(target).toString());
        dto.setUtolsoModositas(Instant.now());

        dto = templateService.save(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download megrendeles template : {}", id);
        MegrendelesDokumentumTemplateDTO dto = templateService
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

    @PostMapping("")
    public ResponseEntity<MegrendelesDokumentumTemplateDTO> createMegrendelesDokumentumTemplate(
        @Valid @RequestBody MegrendelesDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to save MegrendelesDokumentumTemplate : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new megrendelesDokumentumTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dto = templateService.save(dto);
        return ResponseEntity.created(new URI("/api/megrendeles-dokumentum-templatek/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MegrendelesDokumentumTemplateDTO> updateMegrendelesDokumentumTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MegrendelesDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update MegrendelesDokumentumTemplate : {}, {}", id, dto);
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

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MegrendelesDokumentumTemplateDTO> partialUpdateMegrendelesDokumentumTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MegrendelesDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MegrendelesDokumentumTemplate partially : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<MegrendelesDokumentumTemplateDTO> result = templateService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<MegrendelesDokumentumTemplateDTO>> getAllMegrendelesDokumentumTemplates(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of MegrendelesDokumentumTemplates");
        Page<MegrendelesDokumentumTemplateDTO> page = templateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MegrendelesDokumentumTemplateDTO> getMegrendelesDokumentumTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MegrendelesDokumentumTemplate : {}", id);
        Optional<MegrendelesDokumentumTemplateDTO> dto = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMegrendelesDokumentumTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete MegrendelesDokumentumTemplate : {}", id);
        MegrendelesDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template nem található", ENTITY_NAME, "idnotfound"));
        templateService.delete(id);
        Path filePath = resolveTemplatePath(dto.getFajlUtvonal());
        Files.deleteIfExists(filePath);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Path getTemplateUploadDir() {
        return Path.of("uploads", "templates", "megrendelesek");
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

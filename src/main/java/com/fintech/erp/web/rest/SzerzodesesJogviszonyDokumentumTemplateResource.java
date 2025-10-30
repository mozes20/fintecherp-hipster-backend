package com.fintech.erp.web.rest;

import com.fintech.erp.service.SzerzodesesJogviszonyDokumentumTemplateService;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumTemplateDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * REST controller for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate}.
 */
@RestController
@RequestMapping("/api/szerzodeses-jogviszony-dokumentum-templatek")
public class SzerzodesesJogviszonyDokumentumTemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyDokumentumTemplateResource.class);

    private static final String ENTITY_NAME = "szerzodesesJogviszonyDokumentumTemplate";

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SzerzodesesJogviszonyDokumentumTemplateService templateService;

    public SzerzodesesJogviszonyDokumentumTemplateResource(SzerzodesesJogviszonyDokumentumTemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/upload")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumTemplateDTO> uploadTemplate(
        @RequestParam("file") MultipartFile file,
        @RequestParam("dokumentumTipus") String dokumentumTipus,
        @RequestParam("templateNev") String templateNev,
        @RequestParam(value = "templateLeiras", required = false) String templateLeiras
    ) throws IOException {
        LOG.debug("REST request to upload template file: {}", file.getOriginalFilename());
        validateDocx(file);
        if (dokumentumTipus == null || dokumentumTipus.isBlank()) {
            throw new BadRequestAlertException("A dokumentumtípus megadása kötelező", ENTITY_NAME, "dokumentumtipusmissing");
        }
        Path uploadDir = getTemplateUploadDir();
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "template_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        SzerzodesesJogviszonyDokumentumTemplateDTO dto = new SzerzodesesJogviszonyDokumentumTemplateDTO();
        dto.setTemplateNev(templateNev);
        dto.setTemplateLeiras(templateLeiras);
        dto.setDokumentumTipus(dokumentumTipus.trim());
        dto.setFajlUtvonal(uploadDir.relativize(target).toString());
        dto.setUtolsoModositas(java.time.Instant.now());

        dto = templateService.save(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download template : {}", id);
        SzerzodesesJogviszonyDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template not found", ENTITY_NAME, "idnotfound"));
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
    public ResponseEntity<SzerzodesesJogviszonyDokumentumTemplateDTO> createSzerzodesesJogviszonyDokumentumTemplate(
        @Valid @RequestBody SzerzodesesJogviszonyDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to save SzerzodesesJogviszonyDokumentumTemplate : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException(
                "A new szerzodesesJogviszonyDokumentumTemplate cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        dto = templateService.save(dto);
        return ResponseEntity.created(new URI("/api/szerzodeses-jogviszony-dokumentum-templatek/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumTemplateDTO> updateSzerzodesesJogviszonyDokumentumTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SzerzodesesJogviszonyDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update SzerzodesesJogviszonyDokumentumTemplate : {}, {}", id, dto);
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
    public ResponseEntity<SzerzodesesJogviszonyDokumentumTemplateDTO> partialUpdateSzerzodesesJogviszonyDokumentumTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SzerzodesesJogviszonyDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SzerzodesesJogviszonyDokumentumTemplate partially : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<SzerzodesesJogviszonyDokumentumTemplateDTO> result = templateService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SzerzodesesJogviszonyDokumentumTemplateDTO>> getAllSzerzodesesJogviszonyDokumentumTemplates(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SzerzodesesJogviszonyDokumentumTemplates");
        Page<SzerzodesesJogviszonyDokumentumTemplateDTO> page = templateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumTemplateDTO> getSzerzodesesJogviszonyDokumentumTemplate(
        @PathVariable("id") Long id
    ) {
        LOG.debug("REST request to get SzerzodesesJogviszonyDokumentumTemplate : {}", id);
        Optional<SzerzodesesJogviszonyDokumentumTemplateDTO> dto = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSzerzodesesJogviszonyDokumentumTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete SzerzodesesJogviszonyDokumentumTemplate : {}", id);
        SzerzodesesJogviszonyDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template not found", ENTITY_NAME, "idnotfound"));
        templateService.delete(id);
        Path filePath = resolveTemplatePath(dto.getFajlUtvonal());
        Files.deleteIfExists(filePath);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Path getTemplateUploadDir() {
        return Path.of("uploads", "templates", "szerzodeses-jogviszonyok");
    }

    private Path resolveTemplatePath(String relativePath) {
        Path uploadDir = getTemplateUploadDir();
        return uploadDir.resolve(relativePath != null ? relativePath : "").normalize();
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

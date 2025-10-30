package com.fintech.erp.web.rest;

import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.SzerzodesesJogviszonyDokumentumService;
import com.fintech.erp.service.document.SzerzodesesJogviszonyTemplatePlaceholderService;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import com.fintech.erp.web.rest.vm.TemplatePlaceholderResponse;
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
 * REST controller for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum}.
 */
@RestController
@RequestMapping("/api/szerzodeses-jogviszony-dokumentumoks")
public class SzerzodesesJogviszonyDokumentumResource {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyDokumentumResource.class);

    private static final String ENTITY_NAME = "szerzodesesJogviszonyDokumentum";

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SzerzodesesJogviszonyDokumentumService dokumentumService;

    private final SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    private final SzerzodesesJogviszonyTemplatePlaceholderService placeholderService;

    public SzerzodesesJogviszonyDokumentumResource(
        SzerzodesesJogviszonyDokumentumService dokumentumService,
        SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository,
        SzerzodesesJogviszonyTemplatePlaceholderService placeholderService
    ) {
        this.dokumentumService = dokumentumService;
        this.szerzodesesJogviszonyokRepository = szerzodesesJogviszonyokRepository;
        this.placeholderService = placeholderService;
    }

    @PostMapping("/upload")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumDTO> uploadDokumentum(
        @RequestParam("file") MultipartFile file,
        @RequestParam("dokumentumTipus") String dokumentumTipus,
        @RequestParam("szerzodesesJogviszonyId") Long szerzodesesJogviszonyId,
        @RequestParam(value = "dokumentumNev", required = false) String dokumentumNev,
        @RequestParam(value = "leiras", required = false) String leiras
    ) throws IOException {
        LOG.debug("REST request to upload jogviszony dokumentum: {}", file.getOriginalFilename());
        if (!szerzodesesJogviszonyokRepository.existsById(szerzodesesJogviszonyId)) {
            throw new BadRequestAlertException("A megadott szerződéses jogviszony nem található", ENTITY_NAME, "jogviszonynotfound");
        }
        if (file.isEmpty()) {
            throw new BadRequestAlertException("A feltöltött fájl üres", ENTITY_NAME, "emptyfile");
        }
        if (dokumentumTipus == null || dokumentumTipus.isBlank()) {
            throw new BadRequestAlertException("A dokumentumtípus megadása kötelező", ENTITY_NAME, "dokumentumtipusmissing");
        }
        Path uploadDir = getDocumentUploadDir(szerzodesesJogviszonyId);
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "document_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        SzerzodesesJogviszonyDokumentumDTO dto = new SzerzodesesJogviszonyDokumentumDTO();
        dto.setDokumentumTipus(dokumentumTipus.trim());
        dto.setSzerzodesesJogviszonyId(szerzodesesJogviszonyId);
        dto.setDokumentumNev(dokumentumNev != null && !dokumentumNev.isBlank() ? dokumentumNev : file.getOriginalFilename());
        dto.setLeiras(leiras);
        dto.setFajlUtvonal(getRelativePath(uploadDir, target));
        dto.setContentType(file.getContentType());
        dto.setFeltoltesIdeje(Instant.now());

        dto = dokumentumService.save(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/jogviszony/{jogviszonyId}")
    public ResponseEntity<List<SzerzodesesJogviszonyDokumentumDTO>> getAllForJogviszony(@PathVariable("jogviszonyId") Long jogviszonyId) {
        LOG.debug("REST request to get documents for jogviszony : {}", jogviszonyId);
        List<SzerzodesesJogviszonyDokumentumDTO> result = dokumentumService.findAllBySzerzodesesJogviszony(jogviszonyId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/jogviszony/{jogviszonyId}/placeholders")
    public ResponseEntity<TemplatePlaceholderResponse> getTemplatePlaceholders(@PathVariable("jogviszonyId") Long jogviszonyId) {
        LOG.debug("REST request to get template placeholders for jogviszony : {}", jogviszonyId);
        if (jogviszonyId == null) {
            throw new BadRequestAlertException("A jogviszony azonosító megadása kötelező", ENTITY_NAME, "jogviszonyidnull");
        }
        if (!szerzodesesJogviszonyokRepository.existsById(jogviszonyId)) {
            throw new BadRequestAlertException("A megadott szerződéses jogviszony nem található", ENTITY_NAME, "jogviszonynotfound");
        }
        TemplatePlaceholderResponse response = new TemplatePlaceholderResponse()
            .withEntityId(jogviszonyId)
            .withValues(placeholderService.build(jogviszonyId))
            .withDefinitions(placeholderService.getDefinitions());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDokumentum(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download dokumentum : {}", id);
        SzerzodesesJogviszonyDokumentumDTO dto = dokumentumService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Dokumentum nem található", ENTITY_NAME, "idnotfound"));
        Path filePath = resolveDocumentPath(dto.getSzerzodesesJogviszonyId(), dto.getFajlUtvonal());
        if (!Files.exists(filePath)) {
            throw new BadRequestAlertException("A dokumentum fájl nem található", ENTITY_NAME, "filenotfound");
        }
        Resource resource = new FileSystemResource(filePath);
        MediaType mediaType = dto.getContentType() != null
            ? MediaType.parseMediaType(dto.getContentType())
            : MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.ok()
            .contentType(mediaType)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName())
            .body(resource);
    }

    @PostMapping("")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumDTO> createSzerzodesesJogviszonyDokumentum(
        @Valid @RequestBody SzerzodesesJogviszonyDokumentumDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to save SzerzodesesJogviszonyDokumentum : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new szerzodesesJogviszonyDokumentum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dto = dokumentumService.save(dto);
        return ResponseEntity.created(new URI("/api/szerzodeses-jogviszony-dokumentumoks/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumDTO> updateSzerzodesesJogviszonyDokumentum(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SzerzodesesJogviszonyDokumentumDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update SzerzodesesJogviszonyDokumentum : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        dto = dokumentumService.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SzerzodesesJogviszonyDokumentumDTO> partialUpdateSzerzodesesJogviszonyDokumentum(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SzerzodesesJogviszonyDokumentumDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SzerzodesesJogviszonyDokumentum partially : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<SzerzodesesJogviszonyDokumentumDTO> result = dokumentumService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SzerzodesesJogviszonyDokumentumDTO>> getAllSzerzodesesJogviszonyDokumentumok(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SzerzodesesJogviszonyDokumentumok");
        Page<SzerzodesesJogviszonyDokumentumDTO> page = dokumentumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SzerzodesesJogviszonyDokumentumDTO> getSzerzodesesJogviszonyDokumentum(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SzerzodesesJogviszonyDokumentum : {}", id);
        Optional<SzerzodesesJogviszonyDokumentumDTO> dto = dokumentumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSzerzodesesJogviszonyDokumentum(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete SzerzodesesJogviszonyDokumentum : {}", id);
        SzerzodesesJogviszonyDokumentumDTO dto = dokumentumService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Dokumentum nem található", ENTITY_NAME, "idnotfound"));
        dokumentumService.delete(id);
        Path filePath = resolveDocumentPath(dto.getSzerzodesesJogviszonyId(), dto.getFajlUtvonal());
        Files.deleteIfExists(filePath);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Path getDocumentUploadDir(Long jogviszonyId) {
        return Path.of("uploads", "szerzodeses-jogviszonyok", jogviszonyId.toString());
    }

    private Path resolveDocumentPath(Long jogviszonyId, String relativePath) {
        return getDocumentUploadDir(jogviszonyId).resolve(relativePath != null ? relativePath : "").normalize();
    }

    private String getRelativePath(Path baseDir, Path filePath) {
        return baseDir.relativize(filePath).toString().replace("\\", "/");
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }
}

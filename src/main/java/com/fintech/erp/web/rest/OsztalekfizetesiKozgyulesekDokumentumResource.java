package com.fintech.erp.web.rest;

import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekDokumentumRepository;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.OsztalekfizetesiKozgyulesekDokumentumService;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum}.
 */
@RestController
@RequestMapping("/api/osztalekfizetesi-kozgyulesek-dokumentumok")
public class OsztalekfizetesiKozgyulesekDokumentumResource {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekDokumentumResource.class);

    private static final String ENTITY_NAME = "osztalekfizetesiKozgyulesekDokumentum";

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsztalekfizetesiKozgyulesekDokumentumService dokumentumService;
    private final OsztalekfizetesiKozgyulesekDokumentumRepository dokumentumRepository;
    private final OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository;

    public OsztalekfizetesiKozgyulesekDokumentumResource(
        OsztalekfizetesiKozgyulesekDokumentumService dokumentumService,
        OsztalekfizetesiKozgyulesekDokumentumRepository dokumentumRepository,
        OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository
    ) {
        this.dokumentumService = dokumentumService;
        this.dokumentumRepository = dokumentumRepository;
        this.kozgyulesekRepository = kozgyulesekRepository;
    }

    // -------------------------------------------------------------------------
    // Upload / Download
    // -------------------------------------------------------------------------

    /**
     * {@code POST /upload} : Upload a document (PDF or DOCX) for a given közgyűlés.
     */
    @PostMapping("/upload")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumDTO> uploadDokumentum(
        @RequestParam("file") MultipartFile file,
        @RequestParam("kozgyulesId") Long kozgyulesId,
        @RequestParam("dokumentumTipusa") String dokumentumTipusa
    ) throws IOException {
        LOG.debug("REST request to upload OsztalekfizetesiKozgyulesekDokumentum: {}", file.getOriginalFilename());

        if (!kozgyulesekRepository.existsById(kozgyulesId)) {
            throw new BadRequestAlertException("A megadott közgyűlés nem található", ENTITY_NAME, "kozgyulesnotfound");
        }
        if (file.isEmpty()) {
            throw new BadRequestAlertException("A feltöltött fájl üres", ENTITY_NAME, "emptyfile");
        }

        Path uploadDir = getUploadDir(kozgyulesId);
        Files.createDirectories(uploadDir);

        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "kozgyules_dokumentum_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        OsztalekfizetesiKozgyulesekDokumentumDTO dto = new OsztalekfizetesiKozgyulesekDokumentumDTO();
        dto.setDokumentumTipusa(dokumentumTipusa != null ? dokumentumTipusa.trim() : "FELTOLTOTT");
        dto.setDokumentumNev(file.getOriginalFilename() != null ? file.getOriginalFilename() : storedFilename);
        dto.setFajlNev(storedFilename);
        dto.setDokumentumUrl(buildRelativeUrl(kozgyulesId, storedFilename));
        dto.setFeltoltesIdeje(Instant.now());

        OsztalekfizetesiKozgyulesekDTO kozgyulesRef = new OsztalekfizetesiKozgyulesekDTO();
        kozgyulesRef.setId(kozgyulesId);
        dto.setKozgyules(kozgyulesRef);

        OsztalekfizetesiKozgyulesekDokumentumDTO saved = dokumentumService.save(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * {@code GET /{id}/download} : Download the file for a specific document record.
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDokumentum(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download OsztalekfizetesiKozgyulesekDokumentum : {}", id);

        OsztalekfizetesiKozgyulesekDokumentumDTO dto = dokumentumService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Dokumentum nem található", ENTITY_NAME, "idnotfound"));

        OsztalekfizetesiKozgyulesekDTO kozgyules = dto.getKozgyules();
        if (kozgyules == null || kozgyules.getId() == null) {
            throw new BadRequestAlertException("A dokumentumhoz nem tartozik közgyűlés", ENTITY_NAME, "nokozgyules");
        }

        Path filePath = getUploadDir(kozgyules.getId()).resolve(dto.getFajlNev()).normalize();
        if (!Files.exists(filePath)) {
            throw new BadRequestAlertException("A dokumentum fájl nem található a szerveren", ENTITY_NAME, "filenotfound");
        }

        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName() + "\"")
            .body(resource);
    }

    /**
     * {@code GET /kozgyules/{kozgyulesId}} : Get all documents for a közgyűlés.
     */
    @GetMapping("/kozgyules/{kozgyulesId}")
    public ResponseEntity<List<OsztalekfizetesiKozgyulesekDokumentumDTO>> getAllForKozgyules(
        @PathVariable("kozgyulesId") Long kozgyulesId
    ) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyulesekDokumentum for kozgyules : {}", kozgyulesId);
        List<OsztalekfizetesiKozgyulesekDokumentumDTO> result = dokumentumService.findAllByKozgyules(kozgyulesId);
        return ResponseEntity.ok(result);
    }

    // -------------------------------------------------------------------------
    // Standard CRUD
    // -------------------------------------------------------------------------

    /**
     * {@code POST /} : Create a new dokumentum entry.
     */
    @PostMapping("")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumDTO> createDokumentum(
        @RequestBody OsztalekfizetesiKozgyulesekDokumentumDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to save OsztalekfizetesiKozgyulesekDokumentum : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new dokumentum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dto = dokumentumService.save(dto);
        return ResponseEntity.created(new URI("/api/osztalekfizetesi-kozgyulesek-dokumentumok/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    /**
     * {@code PUT /{id}} : Update an existing dokumentum.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumDTO> updateDokumentum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OsztalekfizetesiKozgyulesekDokumentumDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update OsztalekfizetesiKozgyulesekDokumentum : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!dokumentumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        dto = dokumentumService.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    /**
     * {@code PATCH /{id}} : Partial update of an existing dokumentum.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumDTO> partialUpdateDokumentum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OsztalekfizetesiKozgyulesekDokumentumDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update OsztalekfizetesiKozgyulesekDokumentum : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!dokumentumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<OsztalekfizetesiKozgyulesekDokumentumDTO> result = dokumentumService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    /**
     * {@code GET /{id}} : Get a single dokumentum by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDokumentumDTO> getDokumentum(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyulesekDokumentum : {}", id);
        return ResponseUtil.wrapOrNotFound(dokumentumService.findOne(id));
    }

    /**
     * {@code DELETE /{id}} : Delete a dokumentum record (and its file from disk).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDokumentum(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete OsztalekfizetesiKozgyulesekDokumentum : {}", id);

        dokumentumService
            .findOne(id)
            .ifPresent(dto -> {
                OsztalekfizetesiKozgyulesekDTO kozgyules = dto.getKozgyules();
                if (kozgyules != null && kozgyules.getId() != null && dto.getFajlNev() != null) {
                    Path filePath = getUploadDir(kozgyules.getId()).resolve(dto.getFajlNev()).normalize();
                    try {
                        Files.deleteIfExists(filePath);
                    } catch (IOException e) {
                        LOG.warn("Nem sikerült törölni a dokumentum fájlt, id={}", id, e);
                    }
                }
            });

        dokumentumService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Path getUploadDir(Long kozgyulesId) {
        return Path.of("uploads", "osztalekfizetesi-kozgyulesek", kozgyulesId.toString());
    }

    private String buildRelativeUrl(Long kozgyulesId, String fileName) {
        return "uploads/osztalekfizetesi-kozgyulesek/" + kozgyulesId + "/" + fileName;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf('.'));
    }
}

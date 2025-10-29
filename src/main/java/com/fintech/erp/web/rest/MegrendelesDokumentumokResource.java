package com.fintech.erp.web.rest;

import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import com.fintech.erp.repository.MegrendelesDokumentumokRepository;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.service.MegrendelesDokumentumokQueryService;
import com.fintech.erp.service.MegrendelesDokumentumokService;
import com.fintech.erp.service.criteria.MegrendelesDokumentumokCriteria;
import com.fintech.erp.service.document.DocumentFormat;
import com.fintech.erp.service.document.GeneratedDocumentResult;
import com.fintech.erp.service.document.MegrendelesDocumentGenerationService;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import com.fintech.erp.web.rest.vm.MegrendelesDocumentGenerationRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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
 * REST controller for managing {@link com.fintech.erp.domain.MegrendelesDokumentumok}.
 */
@RestController
@RequestMapping("/api/megrendeles-dokumentumoks")
public class MegrendelesDokumentumokResource {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDokumentumokResource.class);

    private static final String ENTITY_NAME = "megrendelesDokumentumok";

    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MegrendelesDokumentumokService megrendelesDokumentumokService;

    private final MegrendelesDokumentumokRepository megrendelesDokumentumokRepository;

    private final MegrendelesDokumentumokQueryService megrendelesDokumentumokQueryService;

    private final MegrendelesekRepository megrendelesekRepository;

    private final MegrendelesDocumentGenerationService documentGenerationService;

    public MegrendelesDokumentumokResource(
        MegrendelesDokumentumokService megrendelesDokumentumokService,
        MegrendelesDokumentumokRepository megrendelesDokumentumokRepository,
        MegrendelesDokumentumokQueryService megrendelesDokumentumokQueryService,
        MegrendelesekRepository megrendelesekRepository,
        MegrendelesDocumentGenerationService documentGenerationService
    ) {
        this.megrendelesDokumentumokService = megrendelesDokumentumokService;
        this.megrendelesDokumentumokRepository = megrendelesDokumentumokRepository;
        this.megrendelesDokumentumokQueryService = megrendelesDokumentumokQueryService;
        this.megrendelesekRepository = megrendelesekRepository;
        this.documentGenerationService = documentGenerationService;
    }

    @PostMapping("/{megrendelesId}/generate")
    public ResponseEntity<Resource> generateDocument(
        @PathVariable("megrendelesId") Long megrendelesId,
        @Valid @RequestBody MegrendelesDocumentGenerationRequest request
    ) throws IOException {
        LOG.debug("REST request to generate megrendeles dokumentum for order {} using template {}", megrendelesId, request.getTemplateId());
        if (megrendelesId == null || !megrendelesekRepository.existsById(megrendelesId)) {
            throw new BadRequestAlertException("A megadott megrendelés nem található", ENTITY_NAME, "megrendelesnotfound");
        }
        try {
            GeneratedDocumentResult<MegrendelesDokumentumokDTO> result = documentGenerationService.generateFromTemplate(
                request.getTemplateId(),
                megrendelesId,
                request.getPlaceholders(),
                Optional.ofNullable(request.getFormat()).orElse(DocumentFormat.DOCX),
                request.isPersist(),
                request.getDokumentumNev()
            );

            ByteArrayResource resource = new ByteArrayResource(result.getData());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(result.getContentType()));
            headers.setContentLength(result.getData().length);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + result.getFileName());
            if (result.getPersistedDocument() != null && result.getPersistedDocument().getId() != null) {
                headers.add("X-Generated-Document-Id", result.getPersistedDocument().getId().toString());
                headers.addAll(
                    HeaderUtil.createEntityCreationAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        result.getPersistedDocument().getId().toString()
                    )
                );
            }
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "validationerror");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<MegrendelesDokumentumokDTO> uploadDokumentum(
        @RequestParam("file") MultipartFile file,
        @RequestParam("dokumentumTipusa") String dokumentumTipusa,
        @RequestParam("megrendelesId") Long megrendelesId
    ) throws IOException {
        LOG.debug("REST request to upload Megrendeles dokumentum: {}", file.getOriginalFilename());
        if (megrendelesId == null || !megrendelesekRepository.existsById(megrendelesId)) {
            throw new BadRequestAlertException("A megadott megrendelés nem található", ENTITY_NAME, "megrendelesnotfound");
        }
        if (file.isEmpty()) {
            throw new BadRequestAlertException("A feltöltött fájl üres", ENTITY_NAME, "emptyfile");
        }
        Path uploadDir = getDocumentUploadDir(megrendelesId);
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "megrendeles_dokumentum_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        MegrendelesDokumentumokDTO dto = new MegrendelesDokumentumokDTO();
        dto.setDokumentumTipusa(parseDokumentumTipus(dokumentumTipusa));
        dto.setDokumentum(getRelativePath(uploadDir, target));
        dto.setDokumentumUrl(buildRelativeUrl(megrendelesId, storedFilename));
        MegrendelesekDTO megrendelesDTO = new MegrendelesekDTO();
        megrendelesDTO.setId(megrendelesId);
        dto.setMegrendeles(megrendelesDTO);

        MegrendelesDokumentumokDTO saved = megrendelesDokumentumokService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/megrendeles/{megrendelesId}")
    public ResponseEntity<List<MegrendelesDokumentumokDTO>> getAllForMegrendeles(@PathVariable("megrendelesId") Long megrendelesId) {
        LOG.debug("REST request to get MegrendelesDokumentumok for megrendeles : {}", megrendelesId);
        List<MegrendelesDokumentumokDTO> result = megrendelesDokumentumokService.findAllByMegrendeles(megrendelesId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDokumentum(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download MegrendelesDokumentumok : {}", id);
        MegrendelesDokumentumokDTO dto = megrendelesDokumentumokService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Dokumentum nem található", ENTITY_NAME, "idnotfound"));
        MegrendelesekDTO megrendeles = dto.getMegrendeles();
        if (megrendeles == null || megrendeles.getId() == null) {
            throw new BadRequestAlertException("A dokumentumhoz nem tartozik megrendelés", ENTITY_NAME, "nomegrendeles");
        }
        Path filePath = resolveDocumentPath(megrendeles.getId(), dto.getDokumentum());
        if (!Files.exists(filePath)) {
            throw new BadRequestAlertException("A dokumentum fájl nem található", ENTITY_NAME, "filenotfound");
        }
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName())
            .body(resource);
    }

    /**
     * {@code POST  /megrendeles-dokumentumoks} : Create a new megrendelesDokumentumok.
     *
     * @param megrendelesDokumentumokDTO the megrendelesDokumentumokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new megrendelesDokumentumokDTO, or with status {@code 400 (Bad Request)} if the megrendelesDokumentumok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MegrendelesDokumentumokDTO> createMegrendelesDokumentumok(
        @RequestBody MegrendelesDokumentumokDTO megrendelesDokumentumokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save MegrendelesDokumentumok : {}", megrendelesDokumentumokDTO);
        if (megrendelesDokumentumokDTO.getId() != null) {
            throw new BadRequestAlertException("A new megrendelesDokumentumok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        megrendelesDokumentumokDTO = megrendelesDokumentumokService.save(megrendelesDokumentumokDTO);
        return ResponseEntity.created(new URI("/api/megrendeles-dokumentumoks/" + megrendelesDokumentumokDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, megrendelesDokumentumokDTO.getId().toString())
            )
            .body(megrendelesDokumentumokDTO);
    }

    /**
     * {@code PUT  /megrendeles-dokumentumoks/:id} : Updates an existing megrendelesDokumentumok.
     *
     * @param id the id of the megrendelesDokumentumokDTO to save.
     * @param megrendelesDokumentumokDTO the megrendelesDokumentumokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated megrendelesDokumentumokDTO,
     * or with status {@code 400 (Bad Request)} if the megrendelesDokumentumokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the megrendelesDokumentumokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MegrendelesDokumentumokDTO> updateMegrendelesDokumentumok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MegrendelesDokumentumokDTO megrendelesDokumentumokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MegrendelesDokumentumok : {}, {}", id, megrendelesDokumentumokDTO);
        if (megrendelesDokumentumokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, megrendelesDokumentumokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!megrendelesDokumentumokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        megrendelesDokumentumokDTO = megrendelesDokumentumokService.update(megrendelesDokumentumokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, megrendelesDokumentumokDTO.getId().toString()))
            .body(megrendelesDokumentumokDTO);
    }

    /**
     * {@code PATCH  /megrendeles-dokumentumoks/:id} : Partial updates given fields of an existing megrendelesDokumentumok, field will ignore if it is null
     *
     * @param id the id of the megrendelesDokumentumokDTO to save.
     * @param megrendelesDokumentumokDTO the megrendelesDokumentumokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated megrendelesDokumentumokDTO,
     * or with status {@code 400 (Bad Request)} if the megrendelesDokumentumokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the megrendelesDokumentumokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the megrendelesDokumentumokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MegrendelesDokumentumokDTO> partialUpdateMegrendelesDokumentumok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MegrendelesDokumentumokDTO megrendelesDokumentumokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MegrendelesDokumentumok partially : {}, {}", id, megrendelesDokumentumokDTO);
        if (megrendelesDokumentumokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, megrendelesDokumentumokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!megrendelesDokumentumokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MegrendelesDokumentumokDTO> result = megrendelesDokumentumokService.partialUpdate(megrendelesDokumentumokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, megrendelesDokumentumokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /megrendeles-dokumentumoks} : get all the megrendelesDokumentumoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of megrendelesDokumentumoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MegrendelesDokumentumokDTO>> getAllMegrendelesDokumentumoks(
        MegrendelesDokumentumokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get MegrendelesDokumentumoks by criteria: {}", criteria);

        Page<MegrendelesDokumentumokDTO> page = megrendelesDokumentumokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /megrendeles-dokumentumoks/count} : count all the megrendelesDokumentumoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMegrendelesDokumentumoks(MegrendelesDokumentumokCriteria criteria) {
        LOG.debug("REST request to count MegrendelesDokumentumoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(megrendelesDokumentumokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /megrendeles-dokumentumoks/:id} : get the "id" megrendelesDokumentumok.
     *
     * @param id the id of the megrendelesDokumentumokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the megrendelesDokumentumokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MegrendelesDokumentumokDTO> getMegrendelesDokumentumok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MegrendelesDokumentumok : {}", id);
        Optional<MegrendelesDokumentumokDTO> megrendelesDokumentumokDTO = megrendelesDokumentumokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(megrendelesDokumentumokDTO);
    }

    /**
     * {@code DELETE  /megrendeles-dokumentumoks/:id} : delete the "id" megrendelesDokumentumok.
     *
     * @param id the id of the megrendelesDokumentumokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMegrendelesDokumentumok(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete MegrendelesDokumentumok : {}", id);
        megrendelesDokumentumokService
            .findOne(id)
            .ifPresent(dto -> {
                MegrendelesekDTO megrendeles = dto.getMegrendeles();
                if (megrendeles != null && megrendeles.getId() != null) {
                    try {
                        Files.deleteIfExists(resolveDocumentPath(megrendeles.getId(), dto.getDokumentum()));
                    } catch (IOException e) {
                        LOG.warn("Failed to delete megrendeles dokumentum file for id {}", id, e);
                    }
                }
            });
        megrendelesDokumentumokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Path getDocumentUploadDir(Long megrendelesId) {
        return Path.of("uploads", "megrendelesek", megrendelesId.toString());
    }

    private Path resolveDocumentPath(Long megrendelesId, String storedPath) {
        if (storedPath == null || storedPath.isBlank()) {
            return getDocumentUploadDir(megrendelesId);
        }
        Path candidate = Path.of(storedPath);
        if (candidate.isAbsolute()) {
            return candidate.normalize();
        }
        if (storedPath.startsWith("uploads")) {
            return Path.of(storedPath).normalize();
        }
        return getDocumentUploadDir(megrendelesId).resolve(storedPath).normalize();
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

    private String buildRelativeUrl(Long megrendelesId, String fileName) {
        return "uploads/megrendelesek/" + megrendelesId + "/" + fileName;
    }

    private MegrendelesDokumentumTipus parseDokumentumTipus(String raw) {
        if (raw == null || raw.isBlank()) {
            return MegrendelesDokumentumTipus.KEZI_WORD;
        }
        try {
            return MegrendelesDokumentumTipus.valueOf(raw.trim());
        } catch (IllegalArgumentException ex) {
            LOG.warn("Ismeretlen dokumentum típus: {} — alapértelmezett érték használata", raw);
            return MegrendelesDokumentumTipus.KEZI_WORD;
        }
    }
}

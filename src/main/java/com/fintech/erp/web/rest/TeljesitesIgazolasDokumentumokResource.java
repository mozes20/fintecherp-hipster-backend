package com.fintech.erp.web.rest;

import com.fintech.erp.repository.TeljesitesIgazolasDokumentumokRepository;
import com.fintech.erp.service.TeljesitesIgazolasDokumentumokQueryService;
import com.fintech.erp.service.TeljesitesIgazolasDokumentumokService;
import com.fintech.erp.service.criteria.TeljesitesIgazolasDokumentumokCriteria;
import com.fintech.erp.service.dto.TeljesitesIgazolasDokumentumokDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fintech.erp.domain.TeljesitesIgazolasDokumentumok}.
 */
@RestController
@RequestMapping("/api/teljesites-igazolas-dokumentumoks")
public class TeljesitesIgazolasDokumentumokResource {

    private static final Logger LOG = LoggerFactory.getLogger(TeljesitesIgazolasDokumentumokResource.class);

    private static final String ENTITY_NAME = "teljesitesIgazolasDokumentumok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeljesitesIgazolasDokumentumokService teljesitesIgazolasDokumentumokService;

    private final TeljesitesIgazolasDokumentumokRepository teljesitesIgazolasDokumentumokRepository;

    private final TeljesitesIgazolasDokumentumokQueryService teljesitesIgazolasDokumentumokQueryService;

    private final com.fintech.erp.repository.UgyfelElszamolasokRepository ugyfelElszamolasokRepository;

    public TeljesitesIgazolasDokumentumokResource(
        TeljesitesIgazolasDokumentumokService teljesitesIgazolasDokumentumokService,
        TeljesitesIgazolasDokumentumokRepository teljesitesIgazolasDokumentumokRepository,
        TeljesitesIgazolasDokumentumokQueryService teljesitesIgazolasDokumentumokQueryService,
        com.fintech.erp.repository.UgyfelElszamolasokRepository ugyfelElszamolasokRepository
    ) {
        this.teljesitesIgazolasDokumentumokService = teljesitesIgazolasDokumentumokService;
        this.teljesitesIgazolasDokumentumokRepository = teljesitesIgazolasDokumentumokRepository;
        this.teljesitesIgazolasDokumentumokQueryService = teljesitesIgazolasDokumentumokQueryService;
        this.ugyfelElszamolasokRepository = ugyfelElszamolasokRepository;
    }

    /**
     * POST /dokumentumok/upload : Upload a document file and create a new TeljesitesIgazolasDokumentumok.
     *
     * @param file the file to upload
     * @param dokumentumTipusa the type of the document
     * @param teljesitesIgazolasId the ID of the related teljesitesIgazolas
     * @return the created TeljesitesIgazolasDokumentumokDTO
     */
    @PostMapping("/dokumentumok/upload")
    public ResponseEntity<TeljesitesIgazolasDokumentumokDTO> uploadDokumentum(
        @RequestParam("file") org.springframework.web.multipart.MultipartFile file,
        @RequestParam("dokumentumTipusa") String dokumentumTipusa,
        @RequestParam("teljesitesIgazolasId") Long teljesitesIgazolasId
    ) throws Exception {
        LOG.debug("REST request to upload Dokumentum file: {}", file.getOriginalFilename());

        // Ellenőrzés: létezik-e a teljesitesIgazolasId az ugyfel_elszamolasok táblában
        // Ellenőrzés: létezik-e a teljesitesIgazolasId az ugyfel_elszamolasok táblában
        if (!ugyfelElszamolasokRepository.existsById(teljesitesIgazolasId)) {
            return ResponseEntity.badRequest().body(null);
        }

        // Define the upload directory (relative to project root or configurable)
        String uploadDir = "uploads/dokumentumok";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(uploadDir));
        // Generate a unique filename
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String storedFilename = "dokumentum_" + timestamp + extension;
        java.nio.file.Path targetPath = java.nio.file.Paths.get(uploadDir).resolve(storedFilename).normalize();
        java.nio.file.Files.copy(file.getInputStream(), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        // Save only the relative path in the database
        TeljesitesIgazolasDokumentumokDTO dto = new TeljesitesIgazolasDokumentumokDTO();
        dto.setDokumentumTipusa(dokumentumTipusa);
        dto.setDokumentum(uploadDir + "/" + storedFilename);
        com.fintech.erp.service.dto.UgyfelElszamolasokDTO teljesitesIgazolas = new com.fintech.erp.service.dto.UgyfelElszamolasokDTO();
        teljesitesIgazolas.setId(teljesitesIgazolasId);
        dto.setTeljesitesIgazolas(teljesitesIgazolas);
        TeljesitesIgazolasDokumentumokDTO saved = teljesitesIgazolasDokumentumokService.save(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * {@code POST  /teljesites-igazolas-dokumentumoks} : Create a new teljesitesIgazolasDokumentumok.
     *
     * @param teljesitesIgazolasDokumentumokDTO the teljesitesIgazolasDokumentumokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teljesitesIgazolasDokumentumokDTO, or with status {@code 400 (Bad Request)} if the teljesitesIgazolasDokumentumok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TeljesitesIgazolasDokumentumokDTO> createTeljesitesIgazolasDokumentumok(
        @RequestBody TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save TeljesitesIgazolasDokumentumok : {}", teljesitesIgazolasDokumentumokDTO);
        if (teljesitesIgazolasDokumentumokDTO.getId() != null) {
            throw new BadRequestAlertException("A new teljesitesIgazolasDokumentumok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokService.save(teljesitesIgazolasDokumentumokDTO);
        return ResponseEntity.created(new URI("/api/teljesites-igazolas-dokumentumoks/" + teljesitesIgazolasDokumentumokDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    teljesitesIgazolasDokumentumokDTO.getId().toString()
                )
            )
            .body(teljesitesIgazolasDokumentumokDTO);
    }

    /**
     * {@code PUT  /teljesites-igazolas-dokumentumoks/:id} : Updates an existing teljesitesIgazolasDokumentumok.
     *
     * @param id the id of the teljesitesIgazolasDokumentumokDTO to save.
     * @param teljesitesIgazolasDokumentumokDTO the teljesitesIgazolasDokumentumokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teljesitesIgazolasDokumentumokDTO,
     * or with status {@code 400 (Bad Request)} if the teljesitesIgazolasDokumentumokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teljesitesIgazolasDokumentumokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeljesitesIgazolasDokumentumokDTO> updateTeljesitesIgazolasDokumentumok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TeljesitesIgazolasDokumentumok : {}, {}", id, teljesitesIgazolasDokumentumokDTO);
        if (teljesitesIgazolasDokumentumokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teljesitesIgazolasDokumentumokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teljesitesIgazolasDokumentumokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokService.update(teljesitesIgazolasDokumentumokDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teljesitesIgazolasDokumentumokDTO.getId().toString())
            )
            .body(teljesitesIgazolasDokumentumokDTO);
    }

    /**
     * {@code PATCH  /teljesites-igazolas-dokumentumoks/:id} : Partial updates given fields of an existing teljesitesIgazolasDokumentumok, field will ignore if it is null
     *
     * @param id the id of the teljesitesIgazolasDokumentumokDTO to save.
     * @param teljesitesIgazolasDokumentumokDTO the teljesitesIgazolasDokumentumokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teljesitesIgazolasDokumentumokDTO,
     * or with status {@code 400 (Bad Request)} if the teljesitesIgazolasDokumentumokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teljesitesIgazolasDokumentumokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teljesitesIgazolasDokumentumokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeljesitesIgazolasDokumentumokDTO> partialUpdateTeljesitesIgazolasDokumentumok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update TeljesitesIgazolasDokumentumok partially : {}, {}",
            id,
            teljesitesIgazolasDokumentumokDTO
        );
        if (teljesitesIgazolasDokumentumokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teljesitesIgazolasDokumentumokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teljesitesIgazolasDokumentumokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeljesitesIgazolasDokumentumokDTO> result = teljesitesIgazolasDokumentumokService.partialUpdate(
            teljesitesIgazolasDokumentumokDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teljesitesIgazolasDokumentumokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /teljesites-igazolas-dokumentumoks} : get all the teljesitesIgazolasDokumentumoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teljesitesIgazolasDokumentumoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TeljesitesIgazolasDokumentumokDTO>> getAllTeljesitesIgazolasDokumentumoks(
        TeljesitesIgazolasDokumentumokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get TeljesitesIgazolasDokumentumoks by criteria: {}", criteria);

        Page<TeljesitesIgazolasDokumentumokDTO> page = teljesitesIgazolasDokumentumokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teljesites-igazolas-dokumentumoks/count} : count all the teljesitesIgazolasDokumentumoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTeljesitesIgazolasDokumentumoks(TeljesitesIgazolasDokumentumokCriteria criteria) {
        LOG.debug("REST request to count TeljesitesIgazolasDokumentumoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(teljesitesIgazolasDokumentumokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /teljesites-igazolas-dokumentumoks/:id} : get the "id" teljesitesIgazolasDokumentumok.
     *
     * @param id the id of the teljesitesIgazolasDokumentumokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teljesitesIgazolasDokumentumokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeljesitesIgazolasDokumentumokDTO> getTeljesitesIgazolasDokumentumok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TeljesitesIgazolasDokumentumok : {}", id);
        Optional<TeljesitesIgazolasDokumentumokDTO> teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teljesitesIgazolasDokumentumokDTO);
    }

    /**
     * {@code DELETE  /teljesites-igazolas-dokumentumoks/:id} : delete the "id" teljesitesIgazolasDokumentumok.
     *
     * @param id the id of the teljesitesIgazolasDokumentumokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeljesitesIgazolasDokumentumok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TeljesitesIgazolasDokumentumok : {}", id);
        teljesitesIgazolasDokumentumokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * DELETE /dokumentumok/sync-files : Remove files from disk that are not referenced in the database.
     *
     * @return the number of deleted files
     */
    @DeleteMapping("/dokumentumok/sync-files")
    public ResponseEntity<Integer> syncUploadedFilesWithDatabase() throws Exception {
        String uploadDir = "uploads/dokumentumok";
        java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
        if (!java.nio.file.Files.exists(uploadPath)) {
            return ResponseEntity.ok(0);
        }

        // Get all file paths in the upload directory
        java.util.Set<String> filesOnDisk = new java.util.HashSet<>();
        try (java.util.stream.Stream<java.nio.file.Path> paths = java.nio.file.Files.list(uploadPath)) {
            paths.filter(java.nio.file.Files::isRegularFile).forEach(p -> filesOnDisk.add(p.getFileName().toString()));
        }

        // Get all file paths referenced in the database (relative path)
        java.util.Set<String> referencedFiles = new java.util.HashSet<>();
        for (com.fintech.erp.domain.TeljesitesIgazolasDokumentumok doc : teljesitesIgazolasDokumentumokRepository.findAll()) {
            String path = doc.getDokumentum();
            if (path != null && path.startsWith(uploadDir + "/")) {
                referencedFiles.add(path.substring((uploadDir + "/").length()));
            }
        }

        // Find files on disk not referenced in the database
        filesOnDisk.removeAll(referencedFiles);

        // Delete unreferenced files
        int deleted = 0;
        for (String filename : filesOnDisk) {
            java.nio.file.Files.deleteIfExists(uploadPath.resolve(filename));
            deleted++;
        }

        return ResponseEntity.ok(deleted);
    }
}

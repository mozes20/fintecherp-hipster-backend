package com.fintech.erp.web.rest;

import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.OsztalekfizetesiKozgyulesekQueryService;
import com.fintech.erp.service.OsztalekfizetesiKozgyulesekService;
import com.fintech.erp.service.criteria.OsztalekfizetesiKozgyulesekCriteria;
import com.fintech.erp.service.document.GeneratedDocumentResult;
import com.fintech.erp.service.document.OsztalekfizetesiKozgyulesekDocumentGenerationService;
import com.fintech.erp.service.document.OsztalekfizetesiKozgyulesekExcelService;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import com.fintech.erp.web.rest.vm.DocumentGenerationRequest;
import com.fintech.erp.web.rest.vm.OsztalekfizetesiElszamolasExcelRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
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
import org.springframework.util.StringUtils;
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
 * REST controller for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesek}.
 */
@RestController
@RequestMapping("/api/osztalekfizetesi-kozgyuleseks")
public class OsztalekfizetesiKozgyulesekResource {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekResource.class);

    private static final String ENTITY_NAME = "osztalekfizetesiKozgyulesek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsztalekfizetesiKozgyulesekService osztalekfizetesiKozgyulesekService;

    private final OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository;

    private final OsztalekfizetesiKozgyulesekQueryService osztalekfizetesiKozgyulesekQueryService;

    private final OsztalekfizetesiKozgyulesekExcelService excelService;

    private final OsztalekfizetesiKozgyulesekDocumentGenerationService documentGenerationService;

    public OsztalekfizetesiKozgyulesekResource(
        OsztalekfizetesiKozgyulesekService osztalekfizetesiKozgyulesekService,
        OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository,
        OsztalekfizetesiKozgyulesekQueryService osztalekfizetesiKozgyulesekQueryService,
        OsztalekfizetesiKozgyulesekExcelService excelService,
        OsztalekfizetesiKozgyulesekDocumentGenerationService documentGenerationService
    ) {
        this.osztalekfizetesiKozgyulesekService = osztalekfizetesiKozgyulesekService;
        this.osztalekfizetesiKozgyulesekRepository = osztalekfizetesiKozgyulesekRepository;
        this.osztalekfizetesiKozgyulesekQueryService = osztalekfizetesiKozgyulesekQueryService;
        this.excelService = excelService;
        this.documentGenerationService = documentGenerationService;
    }

    /**
     * {@code POST  /osztalekfizetesi-kozgyuleseks} : Create a new osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the osztalekfizetesiKozgyulesekDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new osztalekfizetesiKozgyulesekDTO, or with status {@code 400 (Bad Request)} if the osztalekfizetesiKozgyulesek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> createOsztalekfizetesiKozgyulesek(
        @Valid @RequestBody OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);
        if (osztalekfizetesiKozgyulesekDTO.getId() != null) {
            throw new BadRequestAlertException("A new osztalekfizetesiKozgyulesek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekService.save(osztalekfizetesiKozgyulesekDTO);
        return ResponseEntity.created(new URI("/api/osztalekfizetesi-kozgyuleseks/" + osztalekfizetesiKozgyulesekDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, osztalekfizetesiKozgyulesekDTO.getId().toString())
            )
            .body(osztalekfizetesiKozgyulesekDTO);
    }

    /**
     * {@code PUT  /osztalekfizetesi-kozgyuleseks/:id} : Updates an existing osztalekfizetesiKozgyulesek.
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to save.
     * @param osztalekfizetesiKozgyulesekDTO the osztalekfizetesiKozgyulesekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osztalekfizetesiKozgyulesekDTO,
     * or with status {@code 400 (Bad Request)} if the osztalekfizetesiKozgyulesekDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the osztalekfizetesiKozgyulesekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> updateOsztalekfizetesiKozgyulesek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update OsztalekfizetesiKozgyulesek : {}, {}", id, osztalekfizetesiKozgyulesekDTO);
        if (osztalekfizetesiKozgyulesekDTO.getId() == null) {
            osztalekfizetesiKozgyulesekDTO.setId(id);
        }
        if (!Objects.equals(id, osztalekfizetesiKozgyulesekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!osztalekfizetesiKozgyulesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekService.update(osztalekfizetesiKozgyulesekDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, osztalekfizetesiKozgyulesekDTO.getId().toString())
            )
            .body(osztalekfizetesiKozgyulesekDTO);
    }

    /**
     * {@code PATCH  /osztalekfizetesi-kozgyuleseks/:id} : Partial updates given fields of an existing osztalekfizetesiKozgyulesek, field will ignore if it is null
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to save.
     * @param osztalekfizetesiKozgyulesekDTO the osztalekfizetesiKozgyulesekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osztalekfizetesiKozgyulesekDTO,
     * or with status {@code 400 (Bad Request)} if the osztalekfizetesiKozgyulesekDTO is not valid,
     * or with status {@code 404 (Not Found)} if the osztalekfizetesiKozgyulesekDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the osztalekfizetesiKozgyulesekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> partialUpdateOsztalekfizetesiKozgyulesek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OsztalekfizetesiKozgyulesek partially : {}, {}", id, osztalekfizetesiKozgyulesekDTO);
        if (osztalekfizetesiKozgyulesekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, osztalekfizetesiKozgyulesekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!osztalekfizetesiKozgyulesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OsztalekfizetesiKozgyulesekDTO> result = osztalekfizetesiKozgyulesekService.partialUpdate(osztalekfizetesiKozgyulesekDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, osztalekfizetesiKozgyulesekDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /osztalekfizetesi-kozgyuleseks} : get all the osztalekfizetesiKozgyuleseks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of osztalekfizetesiKozgyuleseks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OsztalekfizetesiKozgyulesekDTO>> getAllOsztalekfizetesiKozgyuleseks(
        OsztalekfizetesiKozgyulesekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyuleseks by criteria: {}", criteria);

        Page<OsztalekfizetesiKozgyulesekDTO> page = osztalekfizetesiKozgyulesekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /osztalekfizetesi-kozgyuleseks/count} : count all the osztalekfizetesiKozgyuleseks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOsztalekfizetesiKozgyuleseks(OsztalekfizetesiKozgyulesekCriteria criteria) {
        LOG.debug("REST request to count OsztalekfizetesiKozgyuleseks by criteria: {}", criteria);
        return ResponseEntity.ok().body(osztalekfizetesiKozgyulesekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /osztalekfizetesi-kozgyuleseks/:id} : get the "id" osztalekfizetesiKozgyulesek.
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the osztalekfizetesiKozgyulesekDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> getOsztalekfizetesiKozgyulesek(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyulesek : {}", id);
        Optional<OsztalekfizetesiKozgyulesekDTO> osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(osztalekfizetesiKozgyulesekDTO);
    }

    /**
     * {@code DELETE  /osztalekfizetesi-kozgyuleseks/:id} : delete the "id" osztalekfizetesiKozgyulesek.
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOsztalekfizetesiKozgyulesek(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OsztalekfizetesiKozgyulesek : {}", id);
        osztalekfizetesiKozgyulesekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // -------------------------------------------------------------------------
    // Document endpoints
    // -------------------------------------------------------------------------

    /**
     * POST /{id}/signed-document : Upload signed assembly minutes for the given meeting.
     */
    @PostMapping(value = "/{id}/signed-document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> uploadSignedDocument(
        @PathVariable("id") Long id,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        LOG.debug("REST request to upload signed document for OsztalekfizetesiKozgyulesek : {}", id);
        try {
            OsztalekfizetesiKozgyulesekDTO updated = osztalekfizetesiKozgyulesekService.uploadSignedDocument(id, file);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "invalidsigneddocument");
        }
    }

    /**
     * GET /{id}/signed-document : Download the signed assembly minutes for the given meeting.
     */
    @GetMapping(value = "/{id}/signed-document")
    public ResponseEntity<Resource> downloadSignedDocument(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download signed document for OsztalekfizetesiKozgyulesek : {}", id);
        Path signedPath = osztalekfizetesiKozgyulesekService
            .resolveSignedDocumentPath(id)
            .orElseThrow(() -> new BadRequestAlertException("A kért aláírt dokumentum nem található", ENTITY_NAME, "signeddocnotfound"));
        Resource resource = new FileSystemResource(signedPath);
        if (!resource.exists()) {
            throw new BadRequestAlertException("A kért aláírt dokumentum nem található", ENTITY_NAME, "signeddocnotfound");
        }
        String fileName = resource.getFilename();
        String contentType = fileName != null && fileName.toLowerCase().endsWith(".pdf") ? "application/pdf" : "application/octet-stream";
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .body(resource);
    }

    /**
     * DELETE /{id}/signed-document : Delete the signed assembly minutes for the given meeting.
     */
    @DeleteMapping(value = "/{id}/signed-document")
    public ResponseEntity<Void> deleteSignedDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete signed document for OsztalekfizetesiKozgyulesek : {}", id);
        try {
            osztalekfizetesiKozgyulesekService.deleteSignedDocument(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "invalidsigneddocument");
        }
    }

    /**
     * GET /{id}/generated-document/{fileName} : Download the generated assembly minutes for the given meeting.
     */
    @GetMapping(value = "/{id}/generated-document/{fileName:.+}")
    public ResponseEntity<Resource> downloadGeneratedDocument(@PathVariable("id") Long id, @PathVariable("fileName") String fileName)
        throws IOException {
        LOG.debug("REST request to download generated document for OsztalekfizetesiKozgyulesek : {}, file: {}", id, fileName);
        if (!StringUtils.hasText(fileName) || fileName.contains("..")) {
            throw new BadRequestAlertException("Érvénytelen fájlnév", ENTITY_NAME, "invalidfilename");
        }
        Path documentPath = osztalekfizetesiKozgyulesekService
            .resolveGeneratedDocumentPath(id, fileName)
            .orElseThrow(() -> new BadRequestAlertException("A kért dokumentum nem található", ENTITY_NAME, "generateddocnotfound"));
        Resource resource = new FileSystemResource(documentPath);
        if (!resource.exists()) {
            throw new BadRequestAlertException("A kért dokumentum nem található", ENTITY_NAME, "generateddocnotfound");
        }
        String contentType = fileName.toLowerCase().endsWith(".pdf") ? "application/pdf" : "application/octet-stream";
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
            .header(HttpHeaders.PRAGMA, "no-cache")
            .header(HttpHeaders.EXPIRES, "0")
            .body(resource);
    }

    /**
     * {@code POST /{id}/excel-export} : Generate the settlement Excel workbook for the given assembly meeting.
     *
     * @param id      the id of the OsztalekfizetesiKozgyulesek
     * @param request optional overhead cost items (can be empty body or null)
     * @return .xlsx file download
     */
    @PostMapping(value = "/{id}/excel-export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> exportElszamolasExcel(
        @PathVariable("id") Long id,
        @RequestBody(required = false) OsztalekfizetesiElszamolasExcelRequest request
    ) throws IOException {
        LOG.debug("REST request to export Excel for OsztalekfizetesiKozgyulesek : {}", id);
        if (!osztalekfizetesiKozgyulesekRepository.existsById(id)) {
            throw new BadRequestAlertException("A közgyűlés nem található", ENTITY_NAME, "idnotfound");
        }
        try {
            byte[] xlsx = excelService.generateExcel(id, request);
            String fileName = "elszamolas_kozgyules_" + id + ".xlsx";
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(xlsx.length)
                .body(xlsx);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "excelexporterror");
        }
    }

    /**
     * {@code POST /{id}/generate} : Generate a közgyűlési document from a DOCX template.
     *
     * <p>Request body:
     * <pre>
     * {
     *   "templateId": 1,
     *   "dokumentumNev": "Kozgyulesi_Jegyzokonyv_2025",   // optional
     *   "dokumentumTipus": "GENERALT_JEGYZOKONYV",         // optional
     *   "format": "DOCX",                                  // DOCX | PDF
     *   "persist": true,                                   // save to DB + disk
     *   "placeholders": { "sajat_ceg.nev": "FinTech Kft." } // optional overrides
     * }
     * </pre>
     *
     * @param id      the id of the OsztalekfizetesiKozgyulesek
     * @param request document generation parameters
     * @return the generated file as a download; if persist=true the saved document ID is in X-Generated-Document-Id header
     */
    @PostMapping("/{id}/generate-document")
    public ResponseEntity<Resource> generateDocument(
        @PathVariable("id") Long id,
        @RequestBody(required = false) DocumentGenerationRequest request
    ) throws IOException {
        if (request == null) {
            request = new DocumentGenerationRequest();
        }
        // Always persist and return as PDF so the document cannot be edited
        request.setPersist(true);
        if (request.getFormat() == null) {
            request.setFormat(com.fintech.erp.service.document.DocumentFormat.PDF);
        }
        LOG.debug("REST request to generate document for OsztalekfizetesiKozgyulesek {} using template {}", id, request.getTemplateId());
        if (!osztalekfizetesiKozgyulesekRepository.existsById(id)) {
            throw new BadRequestAlertException("A közgyűlés nem található", ENTITY_NAME, "idnotfound");
        }
        try {
            GeneratedDocumentResult<OsztalekfizetesiKozgyulesekDokumentumDTO> result = documentGenerationService.generateFromTemplate(
                id,
                request
            );

            ByteArrayResource resource = new ByteArrayResource(result.getData());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(result.getContentType()));
            headers.setContentLength(result.getData().length);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getFileName() + "\"");
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
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "generationerror");
        }
    }
}

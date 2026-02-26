package com.fintech.erp.web.rest;

import com.fintech.erp.repository.WorkingDayTemplateRepository;
import com.fintech.erp.security.AuthoritiesConstants;
import com.fintech.erp.service.WorkingDayTemplateQueryService;
import com.fintech.erp.service.WorkingDayTemplateService;
import com.fintech.erp.service.criteria.WorkingDayTemplateCriteria;
import com.fintech.erp.service.dto.WorkingDayTemplateDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.fintech.erp.domain.WorkingDayTemplate}.
 * Csak ADMIN jogosultságú felhasználók használhatják.
 */
@RestController
@RequestMapping("/api/working-day-templates")
public class WorkingDayTemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(WorkingDayTemplateResource.class);

    private static final String ENTITY_NAME = "workingDayTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingDayTemplateService workingDayTemplateService;

    private final WorkingDayTemplateRepository workingDayTemplateRepository;

    private final WorkingDayTemplateQueryService workingDayTemplateQueryService;

    public WorkingDayTemplateResource(
        WorkingDayTemplateService workingDayTemplateService,
        WorkingDayTemplateRepository workingDayTemplateRepository,
        WorkingDayTemplateQueryService workingDayTemplateQueryService
    ) {
        this.workingDayTemplateService = workingDayTemplateService;
        this.workingDayTemplateRepository = workingDayTemplateRepository;
        this.workingDayTemplateQueryService = workingDayTemplateQueryService;
    }

    /**
     * {@code POST  /working-day-templates} : Create a new workingDayTemplate.
     * Csak ADMIN jogosultsággal.
     *
     * @param workingDayTemplateDTO the workingDayTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new workingDayTemplateDTO, or with status
     *         {@code 400 (Bad Request)} if the workingDayTemplate has already an
     *         ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WorkingDayTemplateDTO> createWorkingDayTemplate(@Valid @RequestBody WorkingDayTemplateDTO workingDayTemplateDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save WorkingDayTemplate : {}", workingDayTemplateDTO);
        if (workingDayTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingDayTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        workingDayTemplateDTO = workingDayTemplateService.save(workingDayTemplateDTO);
        return ResponseEntity.created(new URI("/api/working-day-templates/" + workingDayTemplateDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, workingDayTemplateDTO.getId().toString()))
            .body(workingDayTemplateDTO);
    }

    /**
     * {@code PUT  /working-day-templates/:id} : Updates an existing
     * workingDayTemplate.
     * Csak ADMIN jogosultsággal.
     *
     * @param id                    the id of the workingDayTemplateDTO to save.
     * @param workingDayTemplateDTO the workingDayTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated workingDayTemplateDTO,
     *         or with status {@code 400 (Bad Request)} if the workingDayTemplateDTO
     *         is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         workingDayTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WorkingDayTemplateDTO> updateWorkingDayTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkingDayTemplateDTO workingDayTemplateDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update WorkingDayTemplate : {}, {}", id, workingDayTemplateDTO);

        // Ha a body-ban nincs ID, automatikusan beállítjuk az URL-ből
        if (workingDayTemplateDTO.getId() == null) {
            workingDayTemplateDTO.setId(id);
        }

        if (!Objects.equals(id, workingDayTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingDayTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        workingDayTemplateDTO = workingDayTemplateService.update(workingDayTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingDayTemplateDTO.getId().toString()))
            .body(workingDayTemplateDTO);
    }

    /**
     * {@code PATCH  /working-day-templates/:id} : Partial updates given fields of
     * an existing workingDayTemplate, field will ignore if it is null
     * Csak ADMIN jogosultsággal.
     *
     * @param id                    the id of the workingDayTemplateDTO to save.
     * @param workingDayTemplateDTO the workingDayTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated workingDayTemplateDTO,
     *         or with status {@code 400 (Bad Request)} if the workingDayTemplateDTO
     *         is not valid,
     *         or with status {@code 404 (Not Found)} if the workingDayTemplateDTO
     *         is not found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         workingDayTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<WorkingDayTemplateDTO> partialUpdateWorkingDayTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkingDayTemplateDTO workingDayTemplateDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WorkingDayTemplate partially : {}, {}", id, workingDayTemplateDTO);

        // Ha a body-ban nincs ID, automatikusan beállítjuk az URL-ből
        if (workingDayTemplateDTO.getId() == null) {
            workingDayTemplateDTO.setId(id);
        }

        if (!Objects.equals(id, workingDayTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingDayTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkingDayTemplateDTO> result = workingDayTemplateService.partialUpdate(workingDayTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingDayTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /working-day-templates} : get all the workingDayTemplates.
     * Minden bejelentkezett felhasználó lekérheti (olvasásra mindenkinek kell).
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of workingDayTemplates in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WorkingDayTemplateDTO>> getAllWorkingDayTemplates(
        WorkingDayTemplateCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get WorkingDayTemplates by criteria: {}", criteria);

        Page<WorkingDayTemplateDTO> page = workingDayTemplateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-day-templates/count} : count all the
     * workingDayTemplates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWorkingDayTemplates(WorkingDayTemplateCriteria criteria) {
        LOG.debug("REST request to count WorkingDayTemplates by criteria: {}", criteria);
        return ResponseEntity.ok().body(workingDayTemplateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /working-day-templates/:id} : get the "id" workingDayTemplate.
     *
     * @param id the id of the workingDayTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the workingDayTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkingDayTemplateDTO> getWorkingDayTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WorkingDayTemplate : {}", id);
        Optional<WorkingDayTemplateDTO> workingDayTemplateDTO = workingDayTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingDayTemplateDTO);
    }

    /**
     * {@code GET  /working-day-templates/by-month/:yearMonth} : get the
     * workingDayTemplate by yearMonth.
     *
     * @param yearMonth the yearMonth (e.g., "2026-01") of the workingDayTemplateDTO
     *                  to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the workingDayTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/by-month/{yearMonth}")
    public ResponseEntity<WorkingDayTemplateDTO> getWorkingDayTemplateByMonth(@PathVariable("yearMonth") String yearMonth) {
        LOG.debug("REST request to get WorkingDayTemplate by yearMonth : {}", yearMonth);
        Optional<WorkingDayTemplateDTO> workingDayTemplateDTO = workingDayTemplateService.findByYearMonth(yearMonth);
        return ResponseUtil.wrapOrNotFound(workingDayTemplateDTO);
    }

    /**
     * {@code DELETE  /working-day-templates/:id} : delete the "id"
     * workingDayTemplate.
     * Csak ADMIN jogosultsággal.
     *
     * @param id the id of the workingDayTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteWorkingDayTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WorkingDayTemplate : {}", id);
        workingDayTemplateService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

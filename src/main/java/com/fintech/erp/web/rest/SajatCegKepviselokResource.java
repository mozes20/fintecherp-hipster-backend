package com.fintech.erp.web.rest;

import com.fintech.erp.repository.SajatCegKepviselokRepository;
import com.fintech.erp.service.SajatCegKepviselokQueryService;
import com.fintech.erp.service.SajatCegKepviselokService;
import com.fintech.erp.service.criteria.SajatCegKepviselokCriteria;
import com.fintech.erp.service.dto.SajatCegKepviselokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.SajatCegKepviselok}.
 */
@RestController
@RequestMapping("/api/sajat-ceg-kepviseloks")
public class SajatCegKepviselokResource {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegKepviselokResource.class);

    private static final String ENTITY_NAME = "sajatCegKepviselok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SajatCegKepviselokService sajatCegKepviselokService;

    private final SajatCegKepviselokRepository sajatCegKepviselokRepository;

    private final SajatCegKepviselokQueryService sajatCegKepviselokQueryService;

    public SajatCegKepviselokResource(
        SajatCegKepviselokService sajatCegKepviselokService,
        SajatCegKepviselokRepository sajatCegKepviselokRepository,
        SajatCegKepviselokQueryService sajatCegKepviselokQueryService
    ) {
        this.sajatCegKepviselokService = sajatCegKepviselokService;
        this.sajatCegKepviselokRepository = sajatCegKepviselokRepository;
        this.sajatCegKepviselokQueryService = sajatCegKepviselokQueryService;
    }

    /**
     * {@code POST  /sajat-ceg-kepviseloks} : Create a new sajatCegKepviselok.
     *
     * @param sajatCegKepviselokDTO the sajatCegKepviselokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sajatCegKepviselokDTO, or with status {@code 400 (Bad Request)} if the sajatCegKepviselok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SajatCegKepviselokDTO> createSajatCegKepviselok(@RequestBody SajatCegKepviselokDTO sajatCegKepviselokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SajatCegKepviselok : {}", sajatCegKepviselokDTO);
        if (sajatCegKepviselokDTO.getId() != null) {
            throw new BadRequestAlertException("A new sajatCegKepviselok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sajatCegKepviselokDTO = sajatCegKepviselokService.save(sajatCegKepviselokDTO);
        return ResponseEntity.created(new URI("/api/sajat-ceg-kepviseloks/" + sajatCegKepviselokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sajatCegKepviselokDTO.getId().toString()))
            .body(sajatCegKepviselokDTO);
    }

    /**
     * {@code PUT  /sajat-ceg-kepviseloks/:id} : Updates an existing sajatCegKepviselok.
     *
     * @param id the id of the sajatCegKepviselokDTO to save.
     * @param sajatCegKepviselokDTO the sajatCegKepviselokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sajatCegKepviselokDTO,
     * or with status {@code 400 (Bad Request)} if the sajatCegKepviselokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sajatCegKepviselokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SajatCegKepviselokDTO> updateSajatCegKepviselok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SajatCegKepviselokDTO sajatCegKepviselokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SajatCegKepviselok : {}, {}", id, sajatCegKepviselokDTO);
        if (sajatCegKepviselokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sajatCegKepviselokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sajatCegKepviselokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sajatCegKepviselokDTO = sajatCegKepviselokService.update(sajatCegKepviselokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sajatCegKepviselokDTO.getId().toString()))
            .body(sajatCegKepviselokDTO);
    }

    /**
     * {@code PATCH  /sajat-ceg-kepviseloks/:id} : Partial updates given fields of an existing sajatCegKepviselok, field will ignore if it is null
     *
     * @param id the id of the sajatCegKepviselokDTO to save.
     * @param sajatCegKepviselokDTO the sajatCegKepviselokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sajatCegKepviselokDTO,
     * or with status {@code 400 (Bad Request)} if the sajatCegKepviselokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sajatCegKepviselokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sajatCegKepviselokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SajatCegKepviselokDTO> partialUpdateSajatCegKepviselok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SajatCegKepviselokDTO sajatCegKepviselokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SajatCegKepviselok partially : {}, {}", id, sajatCegKepviselokDTO);
        if (sajatCegKepviselokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sajatCegKepviselokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sajatCegKepviselokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SajatCegKepviselokDTO> result = sajatCegKepviselokService.partialUpdate(sajatCegKepviselokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sajatCegKepviselokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sajat-ceg-kepviseloks} : get all the sajatCegKepviseloks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sajatCegKepviseloks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SajatCegKepviselokDTO>> getAllSajatCegKepviseloks(
        SajatCegKepviselokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SajatCegKepviseloks by criteria: {}", criteria);

        Page<SajatCegKepviselokDTO> page = sajatCegKepviselokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sajat-ceg-kepviseloks/count} : count all the sajatCegKepviseloks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSajatCegKepviseloks(SajatCegKepviselokCriteria criteria) {
        LOG.debug("REST request to count SajatCegKepviseloks by criteria: {}", criteria);
        return ResponseEntity.ok().body(sajatCegKepviselokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sajat-ceg-kepviseloks/:id} : get the "id" sajatCegKepviselok.
     *
     * @param id the id of the sajatCegKepviselokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sajatCegKepviselokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SajatCegKepviselokDTO> getSajatCegKepviselok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SajatCegKepviselok : {}", id);
        Optional<SajatCegKepviselokDTO> sajatCegKepviselokDTO = sajatCegKepviselokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sajatCegKepviselokDTO);
    }

    /**
     * {@code DELETE  /sajat-ceg-kepviseloks/:id} : delete the "id" sajatCegKepviselok.
     *
     * @param id the id of the sajatCegKepviselokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSajatCegKepviselok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SajatCegKepviselok : {}", id);
        sajatCegKepviselokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

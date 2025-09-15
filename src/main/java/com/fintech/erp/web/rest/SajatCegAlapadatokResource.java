package com.fintech.erp.web.rest;

import com.fintech.erp.repository.SajatCegAlapadatokRepository;
import com.fintech.erp.service.SajatCegAlapadatokQueryService;
import com.fintech.erp.service.SajatCegAlapadatokService;
import com.fintech.erp.service.criteria.SajatCegAlapadatokCriteria;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.SajatCegAlapadatok}.
 */
@RestController
@RequestMapping("/api/sajat-ceg-alapadatoks")
public class SajatCegAlapadatokResource {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegAlapadatokResource.class);

    private static final String ENTITY_NAME = "sajatCegAlapadatok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SajatCegAlapadatokService sajatCegAlapadatokService;

    private final SajatCegAlapadatokRepository sajatCegAlapadatokRepository;

    private final SajatCegAlapadatokQueryService sajatCegAlapadatokQueryService;

    public SajatCegAlapadatokResource(
        SajatCegAlapadatokService sajatCegAlapadatokService,
        SajatCegAlapadatokRepository sajatCegAlapadatokRepository,
        SajatCegAlapadatokQueryService sajatCegAlapadatokQueryService
    ) {
        this.sajatCegAlapadatokService = sajatCegAlapadatokService;
        this.sajatCegAlapadatokRepository = sajatCegAlapadatokRepository;
        this.sajatCegAlapadatokQueryService = sajatCegAlapadatokQueryService;
    }

    /**
     * {@code POST  /sajat-ceg-alapadatoks} : Create a new sajatCegAlapadatok.
     *
     * @param sajatCegAlapadatokDTO the sajatCegAlapadatokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sajatCegAlapadatokDTO, or with status {@code 400 (Bad Request)} if the sajatCegAlapadatok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SajatCegAlapadatokDTO> createSajatCegAlapadatok(@RequestBody SajatCegAlapadatokDTO sajatCegAlapadatokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SajatCegAlapadatok : {}", sajatCegAlapadatokDTO);
        if (sajatCegAlapadatokDTO.getId() != null) {
            throw new BadRequestAlertException("A new sajatCegAlapadatok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sajatCegAlapadatokDTO = sajatCegAlapadatokService.save(sajatCegAlapadatokDTO);
        return ResponseEntity.created(new URI("/api/sajat-ceg-alapadatoks/" + sajatCegAlapadatokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sajatCegAlapadatokDTO.getId().toString()))
            .body(sajatCegAlapadatokDTO);
    }

    /**
     * {@code PUT  /sajat-ceg-alapadatoks/:id} : Updates an existing sajatCegAlapadatok.
     *
     * @param id the id of the sajatCegAlapadatokDTO to save.
     * @param sajatCegAlapadatokDTO the sajatCegAlapadatokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sajatCegAlapadatokDTO,
     * or with status {@code 400 (Bad Request)} if the sajatCegAlapadatokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sajatCegAlapadatokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SajatCegAlapadatokDTO> updateSajatCegAlapadatok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SajatCegAlapadatokDTO sajatCegAlapadatokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SajatCegAlapadatok : {}, {}", id, sajatCegAlapadatokDTO);
        if (sajatCegAlapadatokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sajatCegAlapadatokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sajatCegAlapadatokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sajatCegAlapadatokDTO = sajatCegAlapadatokService.update(sajatCegAlapadatokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sajatCegAlapadatokDTO.getId().toString()))
            .body(sajatCegAlapadatokDTO);
    }

    /**
     * {@code PATCH  /sajat-ceg-alapadatoks/:id} : Partial updates given fields of an existing sajatCegAlapadatok, field will ignore if it is null
     *
     * @param id the id of the sajatCegAlapadatokDTO to save.
     * @param sajatCegAlapadatokDTO the sajatCegAlapadatokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sajatCegAlapadatokDTO,
     * or with status {@code 400 (Bad Request)} if the sajatCegAlapadatokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sajatCegAlapadatokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sajatCegAlapadatokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SajatCegAlapadatokDTO> partialUpdateSajatCegAlapadatok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SajatCegAlapadatokDTO sajatCegAlapadatokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SajatCegAlapadatok partially : {}, {}", id, sajatCegAlapadatokDTO);
        if (sajatCegAlapadatokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sajatCegAlapadatokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sajatCegAlapadatokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SajatCegAlapadatokDTO> result = sajatCegAlapadatokService.partialUpdate(sajatCegAlapadatokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sajatCegAlapadatokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sajat-ceg-alapadatoks} : get all the sajatCegAlapadatoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sajatCegAlapadatoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SajatCegAlapadatokDTO>> getAllSajatCegAlapadatoks(
        SajatCegAlapadatokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SajatCegAlapadatoks by criteria: {}", criteria);

        Page<SajatCegAlapadatokDTO> page = sajatCegAlapadatokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sajat-ceg-alapadatoks/count} : count all the sajatCegAlapadatoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSajatCegAlapadatoks(SajatCegAlapadatokCriteria criteria) {
        LOG.debug("REST request to count SajatCegAlapadatoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(sajatCegAlapadatokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sajat-ceg-alapadatoks/:id} : get the "id" sajatCegAlapadatok.
     *
     * @param id the id of the sajatCegAlapadatokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sajatCegAlapadatokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SajatCegAlapadatokDTO> getSajatCegAlapadatok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SajatCegAlapadatok : {}", id);
        Optional<SajatCegAlapadatokDTO> sajatCegAlapadatokDTO = sajatCegAlapadatokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sajatCegAlapadatokDTO);
    }

    /**
     * {@code DELETE  /sajat-ceg-alapadatoks/:id} : delete the "id" sajatCegAlapadatok.
     *
     * @param id the id of the sajatCegAlapadatokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSajatCegAlapadatok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SajatCegAlapadatok : {}", id);
        sajatCegAlapadatokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

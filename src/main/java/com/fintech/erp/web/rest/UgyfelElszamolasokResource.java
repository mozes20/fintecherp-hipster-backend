package com.fintech.erp.web.rest;

import com.fintech.erp.repository.UgyfelElszamolasokRepository;
import com.fintech.erp.service.UgyfelElszamolasokQueryService;
import com.fintech.erp.service.UgyfelElszamolasokService;
import com.fintech.erp.service.criteria.UgyfelElszamolasokCriteria;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.UgyfelElszamolasok}.
 */
@RestController
@RequestMapping("/api/ugyfel-elszamolasoks")
public class UgyfelElszamolasokResource {

    private static final Logger LOG = LoggerFactory.getLogger(UgyfelElszamolasokResource.class);

    private static final String ENTITY_NAME = "ugyfelElszamolasok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UgyfelElszamolasokService ugyfelElszamolasokService;

    private final UgyfelElszamolasokRepository ugyfelElszamolasokRepository;

    private final UgyfelElszamolasokQueryService ugyfelElszamolasokQueryService;

    public UgyfelElszamolasokResource(
        UgyfelElszamolasokService ugyfelElszamolasokService,
        UgyfelElszamolasokRepository ugyfelElszamolasokRepository,
        UgyfelElszamolasokQueryService ugyfelElszamolasokQueryService
    ) {
        this.ugyfelElszamolasokService = ugyfelElszamolasokService;
        this.ugyfelElszamolasokRepository = ugyfelElszamolasokRepository;
        this.ugyfelElszamolasokQueryService = ugyfelElszamolasokQueryService;
    }

    /**
     * {@code POST  /ugyfel-elszamolasoks} : Create a new ugyfelElszamolasok.
     *
     * @param ugyfelElszamolasokDTO the ugyfelElszamolasokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ugyfelElszamolasokDTO, or with status {@code 400 (Bad Request)} if the ugyfelElszamolasok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UgyfelElszamolasokDTO> createUgyfelElszamolasok(@RequestBody UgyfelElszamolasokDTO ugyfelElszamolasokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save UgyfelElszamolasok : {}", ugyfelElszamolasokDTO);
        if (ugyfelElszamolasokDTO.getId() != null) {
            throw new BadRequestAlertException("A new ugyfelElszamolasok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ugyfelElszamolasokDTO = ugyfelElszamolasokService.save(ugyfelElszamolasokDTO);
        return ResponseEntity.created(new URI("/api/ugyfel-elszamolasoks/" + ugyfelElszamolasokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ugyfelElszamolasokDTO.getId().toString()))
            .body(ugyfelElszamolasokDTO);
    }

    /**
     * {@code PUT  /ugyfel-elszamolasoks/:id} : Updates an existing ugyfelElszamolasok.
     *
     * @param id the id of the ugyfelElszamolasokDTO to save.
     * @param ugyfelElszamolasokDTO the ugyfelElszamolasokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ugyfelElszamolasokDTO,
     * or with status {@code 400 (Bad Request)} if the ugyfelElszamolasokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ugyfelElszamolasokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UgyfelElszamolasokDTO> updateUgyfelElszamolasok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UgyfelElszamolasokDTO ugyfelElszamolasokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update UgyfelElszamolasok : {}, {}", id, ugyfelElszamolasokDTO);
        if (ugyfelElszamolasokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ugyfelElszamolasokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ugyfelElszamolasokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ugyfelElszamolasokDTO = ugyfelElszamolasokService.update(ugyfelElszamolasokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ugyfelElszamolasokDTO.getId().toString()))
            .body(ugyfelElszamolasokDTO);
    }

    /**
     * {@code PATCH  /ugyfel-elszamolasoks/:id} : Partial updates given fields of an existing ugyfelElszamolasok, field will ignore if it is null
     *
     * @param id the id of the ugyfelElszamolasokDTO to save.
     * @param ugyfelElszamolasokDTO the ugyfelElszamolasokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ugyfelElszamolasokDTO,
     * or with status {@code 400 (Bad Request)} if the ugyfelElszamolasokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ugyfelElszamolasokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ugyfelElszamolasokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UgyfelElszamolasokDTO> partialUpdateUgyfelElszamolasok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UgyfelElszamolasokDTO ugyfelElszamolasokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UgyfelElszamolasok partially : {}, {}", id, ugyfelElszamolasokDTO);
        if (ugyfelElszamolasokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ugyfelElszamolasokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ugyfelElszamolasokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UgyfelElszamolasokDTO> result = ugyfelElszamolasokService.partialUpdate(ugyfelElszamolasokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ugyfelElszamolasokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ugyfel-elszamolasoks} : get all the ugyfelElszamolasoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ugyfelElszamolasoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UgyfelElszamolasokDTO>> getAllUgyfelElszamolasoks(
        UgyfelElszamolasokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get UgyfelElszamolasoks by criteria: {}", criteria);

        Page<UgyfelElszamolasokDTO> page = ugyfelElszamolasokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ugyfel-elszamolasoks/count} : count all the ugyfelElszamolasoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUgyfelElszamolasoks(UgyfelElszamolasokCriteria criteria) {
        LOG.debug("REST request to count UgyfelElszamolasoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(ugyfelElszamolasokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ugyfel-elszamolasoks/:id} : get the "id" ugyfelElszamolasok.
     *
     * @param id the id of the ugyfelElszamolasokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ugyfelElszamolasokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UgyfelElszamolasokDTO> getUgyfelElszamolasok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UgyfelElszamolasok : {}", id);
        Optional<UgyfelElszamolasokDTO> ugyfelElszamolasokDTO = ugyfelElszamolasokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ugyfelElszamolasokDTO);
    }

    /**
     * {@code DELETE  /ugyfel-elszamolasoks/:id} : delete the "id" ugyfelElszamolasok.
     *
     * @param id the id of the ugyfelElszamolasokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUgyfelElszamolasok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UgyfelElszamolasok : {}", id);
        ugyfelElszamolasokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

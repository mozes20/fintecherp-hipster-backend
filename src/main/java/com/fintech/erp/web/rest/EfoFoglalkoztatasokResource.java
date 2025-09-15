package com.fintech.erp.web.rest;

import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.service.EfoFoglalkoztatasokQueryService;
import com.fintech.erp.service.EfoFoglalkoztatasokService;
import com.fintech.erp.service.criteria.EfoFoglalkoztatasokCriteria;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fintech.erp.domain.EfoFoglalkoztatasok}.
 */
@RestController
@RequestMapping("/api/efo-foglalkoztatasoks")
public class EfoFoglalkoztatasokResource {

    private static final Logger LOG = LoggerFactory.getLogger(EfoFoglalkoztatasokResource.class);

    private static final String ENTITY_NAME = "efoFoglalkoztatasok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EfoFoglalkoztatasokService efoFoglalkoztatasokService;

    private final EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;

    private final EfoFoglalkoztatasokQueryService efoFoglalkoztatasokQueryService;

    public EfoFoglalkoztatasokResource(
        EfoFoglalkoztatasokService efoFoglalkoztatasokService,
        EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository,
        EfoFoglalkoztatasokQueryService efoFoglalkoztatasokQueryService
    ) {
        this.efoFoglalkoztatasokService = efoFoglalkoztatasokService;
        this.efoFoglalkoztatasokRepository = efoFoglalkoztatasokRepository;
        this.efoFoglalkoztatasokQueryService = efoFoglalkoztatasokQueryService;
    }

    /**
     * {@code POST  /efo-foglalkoztatasoks} : Create a new efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the efoFoglalkoztatasokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new efoFoglalkoztatasokDTO, or with status {@code 400 (Bad Request)} if the efoFoglalkoztatasok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EfoFoglalkoztatasokDTO> createEfoFoglalkoztatasok(
        @Valid @RequestBody EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);
        if (efoFoglalkoztatasokDTO.getId() != null) {
            throw new BadRequestAlertException("A new efoFoglalkoztatasok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        efoFoglalkoztatasokDTO = efoFoglalkoztatasokService.save(efoFoglalkoztatasokDTO);
        return ResponseEntity.created(new URI("/api/efo-foglalkoztatasoks/" + efoFoglalkoztatasokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, efoFoglalkoztatasokDTO.getId().toString()))
            .body(efoFoglalkoztatasokDTO);
    }

    /**
     * {@code PUT  /efo-foglalkoztatasoks/:id} : Updates an existing efoFoglalkoztatasok.
     *
     * @param id the id of the efoFoglalkoztatasokDTO to save.
     * @param efoFoglalkoztatasokDTO the efoFoglalkoztatasokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated efoFoglalkoztatasokDTO,
     * or with status {@code 400 (Bad Request)} if the efoFoglalkoztatasokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the efoFoglalkoztatasokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EfoFoglalkoztatasokDTO> updateEfoFoglalkoztatasok(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EfoFoglalkoztatasok : {}, {}", id, efoFoglalkoztatasokDTO);
        if (efoFoglalkoztatasokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, efoFoglalkoztatasokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!efoFoglalkoztatasokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        efoFoglalkoztatasokDTO = efoFoglalkoztatasokService.update(efoFoglalkoztatasokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, efoFoglalkoztatasokDTO.getId().toString()))
            .body(efoFoglalkoztatasokDTO);
    }

    /**
     * {@code PATCH  /efo-foglalkoztatasoks/:id} : Partial updates given fields of an existing efoFoglalkoztatasok, field will ignore if it is null
     *
     * @param id the id of the efoFoglalkoztatasokDTO to save.
     * @param efoFoglalkoztatasokDTO the efoFoglalkoztatasokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated efoFoglalkoztatasokDTO,
     * or with status {@code 400 (Bad Request)} if the efoFoglalkoztatasokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the efoFoglalkoztatasokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the efoFoglalkoztatasokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EfoFoglalkoztatasokDTO> partialUpdateEfoFoglalkoztatasok(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EfoFoglalkoztatasok partially : {}, {}", id, efoFoglalkoztatasokDTO);
        if (efoFoglalkoztatasokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, efoFoglalkoztatasokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!efoFoglalkoztatasokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EfoFoglalkoztatasokDTO> result = efoFoglalkoztatasokService.partialUpdate(efoFoglalkoztatasokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, efoFoglalkoztatasokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /efo-foglalkoztatasoks} : get all the efoFoglalkoztatasoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of efoFoglalkoztatasoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EfoFoglalkoztatasokDTO>> getAllEfoFoglalkoztatasoks(
        EfoFoglalkoztatasokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get EfoFoglalkoztatasoks by criteria: {}", criteria);

        Page<EfoFoglalkoztatasokDTO> page = efoFoglalkoztatasokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /efo-foglalkoztatasoks/count} : count all the efoFoglalkoztatasoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEfoFoglalkoztatasoks(EfoFoglalkoztatasokCriteria criteria) {
        LOG.debug("REST request to count EfoFoglalkoztatasoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(efoFoglalkoztatasokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /efo-foglalkoztatasoks/:id} : get the "id" efoFoglalkoztatasok.
     *
     * @param id the id of the efoFoglalkoztatasokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the efoFoglalkoztatasokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EfoFoglalkoztatasokDTO> getEfoFoglalkoztatasok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EfoFoglalkoztatasok : {}", id);
        Optional<EfoFoglalkoztatasokDTO> efoFoglalkoztatasokDTO = efoFoglalkoztatasokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(efoFoglalkoztatasokDTO);
    }

    /**
     * {@code DELETE  /efo-foglalkoztatasoks/:id} : delete the "id" efoFoglalkoztatasok.
     *
     * @param id the id of the efoFoglalkoztatasokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEfoFoglalkoztatasok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EfoFoglalkoztatasok : {}", id);
        efoFoglalkoztatasokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

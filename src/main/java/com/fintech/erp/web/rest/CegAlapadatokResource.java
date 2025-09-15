package com.fintech.erp.web.rest;

import com.fintech.erp.repository.CegAlapadatokRepository;
import com.fintech.erp.service.CegAlapadatokQueryService;
import com.fintech.erp.service.CegAlapadatokService;
import com.fintech.erp.service.criteria.CegAlapadatokCriteria;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.CegAlapadatok}.
 */
@RestController
@RequestMapping("/api/ceg-alapadatoks")
public class CegAlapadatokResource {

    private static final Logger LOG = LoggerFactory.getLogger(CegAlapadatokResource.class);

    private static final String ENTITY_NAME = "cegAlapadatok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CegAlapadatokService cegAlapadatokService;

    private final CegAlapadatokRepository cegAlapadatokRepository;

    private final CegAlapadatokQueryService cegAlapadatokQueryService;

    public CegAlapadatokResource(
        CegAlapadatokService cegAlapadatokService,
        CegAlapadatokRepository cegAlapadatokRepository,
        CegAlapadatokQueryService cegAlapadatokQueryService
    ) {
        this.cegAlapadatokService = cegAlapadatokService;
        this.cegAlapadatokRepository = cegAlapadatokRepository;
        this.cegAlapadatokQueryService = cegAlapadatokQueryService;
    }

    /**
     * {@code POST  /ceg-alapadatoks} : Create a new cegAlapadatok.
     *
     * @param cegAlapadatokDTO the cegAlapadatokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cegAlapadatokDTO, or with status {@code 400 (Bad Request)} if the cegAlapadatok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CegAlapadatokDTO> createCegAlapadatok(@Valid @RequestBody CegAlapadatokDTO cegAlapadatokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CegAlapadatok : {}", cegAlapadatokDTO);
        if (cegAlapadatokDTO.getId() != null) {
            throw new BadRequestAlertException("A new cegAlapadatok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cegAlapadatokDTO = cegAlapadatokService.save(cegAlapadatokDTO);
        return ResponseEntity.created(new URI("/api/ceg-alapadatoks/" + cegAlapadatokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cegAlapadatokDTO.getId().toString()))
            .body(cegAlapadatokDTO);
    }

    /**
     * {@code PUT  /ceg-alapadatoks/:id} : Updates an existing cegAlapadatok.
     *
     * @param id the id of the cegAlapadatokDTO to save.
     * @param cegAlapadatokDTO the cegAlapadatokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cegAlapadatokDTO,
     * or with status {@code 400 (Bad Request)} if the cegAlapadatokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cegAlapadatokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CegAlapadatokDTO> updateCegAlapadatok(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CegAlapadatokDTO cegAlapadatokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CegAlapadatok : {}, {}", id, cegAlapadatokDTO);
        if (cegAlapadatokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cegAlapadatokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cegAlapadatokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cegAlapadatokDTO = cegAlapadatokService.update(cegAlapadatokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cegAlapadatokDTO.getId().toString()))
            .body(cegAlapadatokDTO);
    }

    /**
     * {@code PATCH  /ceg-alapadatoks/:id} : Partial updates given fields of an existing cegAlapadatok, field will ignore if it is null
     *
     * @param id the id of the cegAlapadatokDTO to save.
     * @param cegAlapadatokDTO the cegAlapadatokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cegAlapadatokDTO,
     * or with status {@code 400 (Bad Request)} if the cegAlapadatokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cegAlapadatokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cegAlapadatokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CegAlapadatokDTO> partialUpdateCegAlapadatok(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CegAlapadatokDTO cegAlapadatokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CegAlapadatok partially : {}, {}", id, cegAlapadatokDTO);
        if (cegAlapadatokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cegAlapadatokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cegAlapadatokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CegAlapadatokDTO> result = cegAlapadatokService.partialUpdate(cegAlapadatokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cegAlapadatokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ceg-alapadatoks} : get all the cegAlapadatoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cegAlapadatoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CegAlapadatokDTO>> getAllCegAlapadatoks(
        CegAlapadatokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get CegAlapadatoks by criteria: {}", criteria);

        Page<CegAlapadatokDTO> page = cegAlapadatokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ceg-alapadatoks/count} : count all the cegAlapadatoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCegAlapadatoks(CegAlapadatokCriteria criteria) {
        LOG.debug("REST request to count CegAlapadatoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(cegAlapadatokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ceg-alapadatoks/:id} : get the "id" cegAlapadatok.
     *
     * @param id the id of the cegAlapadatokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cegAlapadatokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CegAlapadatokDTO> getCegAlapadatok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CegAlapadatok : {}", id);
        Optional<CegAlapadatokDTO> cegAlapadatokDTO = cegAlapadatokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cegAlapadatokDTO);
    }

    /**
     * {@code DELETE  /ceg-alapadatoks/:id} : delete the "id" cegAlapadatok.
     *
     * @param id the id of the cegAlapadatokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCegAlapadatok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CegAlapadatok : {}", id);
        cegAlapadatokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

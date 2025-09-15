package com.fintech.erp.web.rest;

import com.fintech.erp.repository.SajatCegTulajdonosokRepository;
import com.fintech.erp.service.SajatCegTulajdonosokQueryService;
import com.fintech.erp.service.SajatCegTulajdonosokService;
import com.fintech.erp.service.criteria.SajatCegTulajdonosokCriteria;
import com.fintech.erp.service.dto.SajatCegTulajdonosokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.SajatCegTulajdonosok}.
 */
@RestController
@RequestMapping("/api/sajat-ceg-tulajdonosoks")
public class SajatCegTulajdonosokResource {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegTulajdonosokResource.class);

    private static final String ENTITY_NAME = "sajatCegTulajdonosok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SajatCegTulajdonosokService sajatCegTulajdonosokService;

    private final SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository;

    private final SajatCegTulajdonosokQueryService sajatCegTulajdonosokQueryService;

    public SajatCegTulajdonosokResource(
        SajatCegTulajdonosokService sajatCegTulajdonosokService,
        SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository,
        SajatCegTulajdonosokQueryService sajatCegTulajdonosokQueryService
    ) {
        this.sajatCegTulajdonosokService = sajatCegTulajdonosokService;
        this.sajatCegTulajdonosokRepository = sajatCegTulajdonosokRepository;
        this.sajatCegTulajdonosokQueryService = sajatCegTulajdonosokQueryService;
    }

    /**
     * {@code POST  /sajat-ceg-tulajdonosoks} : Create a new sajatCegTulajdonosok.
     *
     * @param sajatCegTulajdonosokDTO the sajatCegTulajdonosokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sajatCegTulajdonosokDTO, or with status {@code 400 (Bad Request)} if the sajatCegTulajdonosok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SajatCegTulajdonosokDTO> createSajatCegTulajdonosok(@RequestBody SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SajatCegTulajdonosok : {}", sajatCegTulajdonosokDTO);
        if (sajatCegTulajdonosokDTO.getId() != null) {
            throw new BadRequestAlertException("A new sajatCegTulajdonosok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sajatCegTulajdonosokDTO = sajatCegTulajdonosokService.save(sajatCegTulajdonosokDTO);
        return ResponseEntity.created(new URI("/api/sajat-ceg-tulajdonosoks/" + sajatCegTulajdonosokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sajatCegTulajdonosokDTO.getId().toString()))
            .body(sajatCegTulajdonosokDTO);
    }

    /**
     * {@code PUT  /sajat-ceg-tulajdonosoks/:id} : Updates an existing sajatCegTulajdonosok.
     *
     * @param id the id of the sajatCegTulajdonosokDTO to save.
     * @param sajatCegTulajdonosokDTO the sajatCegTulajdonosokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sajatCegTulajdonosokDTO,
     * or with status {@code 400 (Bad Request)} if the sajatCegTulajdonosokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sajatCegTulajdonosokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SajatCegTulajdonosokDTO> updateSajatCegTulajdonosok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SajatCegTulajdonosok : {}, {}", id, sajatCegTulajdonosokDTO);
        if (sajatCegTulajdonosokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sajatCegTulajdonosokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sajatCegTulajdonosokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sajatCegTulajdonosokDTO = sajatCegTulajdonosokService.update(sajatCegTulajdonosokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sajatCegTulajdonosokDTO.getId().toString()))
            .body(sajatCegTulajdonosokDTO);
    }

    /**
     * {@code PATCH  /sajat-ceg-tulajdonosoks/:id} : Partial updates given fields of an existing sajatCegTulajdonosok, field will ignore if it is null
     *
     * @param id the id of the sajatCegTulajdonosokDTO to save.
     * @param sajatCegTulajdonosokDTO the sajatCegTulajdonosokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sajatCegTulajdonosokDTO,
     * or with status {@code 400 (Bad Request)} if the sajatCegTulajdonosokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sajatCegTulajdonosokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sajatCegTulajdonosokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SajatCegTulajdonosokDTO> partialUpdateSajatCegTulajdonosok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SajatCegTulajdonosok partially : {}, {}", id, sajatCegTulajdonosokDTO);
        if (sajatCegTulajdonosokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sajatCegTulajdonosokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sajatCegTulajdonosokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SajatCegTulajdonosokDTO> result = sajatCegTulajdonosokService.partialUpdate(sajatCegTulajdonosokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sajatCegTulajdonosokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sajat-ceg-tulajdonosoks} : get all the sajatCegTulajdonosoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sajatCegTulajdonosoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SajatCegTulajdonosokDTO>> getAllSajatCegTulajdonosoks(
        SajatCegTulajdonosokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SajatCegTulajdonosoks by criteria: {}", criteria);

        Page<SajatCegTulajdonosokDTO> page = sajatCegTulajdonosokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sajat-ceg-tulajdonosoks/count} : count all the sajatCegTulajdonosoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSajatCegTulajdonosoks(SajatCegTulajdonosokCriteria criteria) {
        LOG.debug("REST request to count SajatCegTulajdonosoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(sajatCegTulajdonosokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sajat-ceg-tulajdonosoks/:id} : get the "id" sajatCegTulajdonosok.
     *
     * @param id the id of the sajatCegTulajdonosokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sajatCegTulajdonosokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SajatCegTulajdonosokDTO> getSajatCegTulajdonosok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SajatCegTulajdonosok : {}", id);
        Optional<SajatCegTulajdonosokDTO> sajatCegTulajdonosokDTO = sajatCegTulajdonosokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sajatCegTulajdonosokDTO);
    }

    /**
     * {@code DELETE  /sajat-ceg-tulajdonosoks/:id} : delete the "id" sajatCegTulajdonosok.
     *
     * @param id the id of the sajatCegTulajdonosokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSajatCegTulajdonosok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SajatCegTulajdonosok : {}", id);
        sajatCegTulajdonosokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

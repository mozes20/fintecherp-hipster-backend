package com.fintech.erp.web.rest;

import com.fintech.erp.repository.MegrendelesDokumentumokRepository;
import com.fintech.erp.service.MegrendelesDokumentumokQueryService;
import com.fintech.erp.service.MegrendelesDokumentumokService;
import com.fintech.erp.service.criteria.MegrendelesDokumentumokCriteria;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.MegrendelesDokumentumok}.
 */
@RestController
@RequestMapping("/api/megrendeles-dokumentumoks")
public class MegrendelesDokumentumokResource {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDokumentumokResource.class);

    private static final String ENTITY_NAME = "megrendelesDokumentumok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MegrendelesDokumentumokService megrendelesDokumentumokService;

    private final MegrendelesDokumentumokRepository megrendelesDokumentumokRepository;

    private final MegrendelesDokumentumokQueryService megrendelesDokumentumokQueryService;

    public MegrendelesDokumentumokResource(
        MegrendelesDokumentumokService megrendelesDokumentumokService,
        MegrendelesDokumentumokRepository megrendelesDokumentumokRepository,
        MegrendelesDokumentumokQueryService megrendelesDokumentumokQueryService
    ) {
        this.megrendelesDokumentumokService = megrendelesDokumentumokService;
        this.megrendelesDokumentumokRepository = megrendelesDokumentumokRepository;
        this.megrendelesDokumentumokQueryService = megrendelesDokumentumokQueryService;
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
    public ResponseEntity<Void> deleteMegrendelesDokumentumok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MegrendelesDokumentumok : {}", id);
        megrendelesDokumentumokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

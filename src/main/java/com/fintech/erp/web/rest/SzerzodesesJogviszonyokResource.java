package com.fintech.erp.web.rest;

import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.SzerzodesesJogviszonyokQueryService;
import com.fintech.erp.service.SzerzodesesJogviszonyokService;
import com.fintech.erp.service.criteria.SzerzodesesJogviszonyokCriteria;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
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
 * REST controller for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyok}.
 */
@RestController
@RequestMapping("/api/szerzodeses-jogviszonyoks")
public class SzerzodesesJogviszonyokResource {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyokResource.class);

    private static final String ENTITY_NAME = "szerzodesesJogviszonyok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SzerzodesesJogviszonyokService szerzodesesJogviszonyokService;

    private final SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    private final SzerzodesesJogviszonyokQueryService szerzodesesJogviszonyokQueryService;

    public SzerzodesesJogviszonyokResource(
        SzerzodesesJogviszonyokService szerzodesesJogviszonyokService,
        SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository,
        SzerzodesesJogviszonyokQueryService szerzodesesJogviszonyokQueryService
    ) {
        this.szerzodesesJogviszonyokService = szerzodesesJogviszonyokService;
        this.szerzodesesJogviszonyokRepository = szerzodesesJogviszonyokRepository;
        this.szerzodesesJogviszonyokQueryService = szerzodesesJogviszonyokQueryService;
    }

    /**
     * {@code POST  /szerzodeses-jogviszonyoks} : Create a new szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the szerzodesesJogviszonyokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new szerzodesesJogviszonyokDTO, or with status {@code 400 (Bad Request)} if the szerzodesesJogviszonyok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SzerzodesesJogviszonyokDTO> createSzerzodesesJogviszonyok(
        @Valid @RequestBody SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);
        if (szerzodesesJogviszonyokDTO.getId() != null) {
            throw new BadRequestAlertException("A new szerzodesesJogviszonyok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokService.save(szerzodesesJogviszonyokDTO);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "invaliddates");
        }
        return ResponseEntity.created(new URI("/api/szerzodeses-jogviszonyoks/" + szerzodesesJogviszonyokDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, szerzodesesJogviszonyokDTO.getId().toString())
            )
            .body(szerzodesesJogviszonyokDTO);
    }

    /**
     * {@code PUT  /szerzodeses-jogviszonyoks/:id} : Updates an existing szerzodesesJogviszonyok.
     *
     * @param id the id of the szerzodesesJogviszonyokDTO to save.
     * @param szerzodesesJogviszonyokDTO the szerzodesesJogviszonyokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated szerzodesesJogviszonyokDTO,
     * or with status {@code 400 (Bad Request)} if the szerzodesesJogviszonyokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the szerzodesesJogviszonyokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SzerzodesesJogviszonyokDTO> updateSzerzodesesJogviszonyok(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SzerzodesesJogviszonyok : {}, {}", id, szerzodesesJogviszonyokDTO);
        if (szerzodesesJogviszonyokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, szerzodesesJogviszonyokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!szerzodesesJogviszonyokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        try {
            szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokService.update(szerzodesesJogviszonyokDTO);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "invaliddates");
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, szerzodesesJogviszonyokDTO.getId().toString()))
            .body(szerzodesesJogviszonyokDTO);
    }

    /**
     * {@code PATCH  /szerzodeses-jogviszonyoks/:id} : Partial updates given fields of an existing szerzodesesJogviszonyok, field will ignore if it is null
     *
     * @param id the id of the szerzodesesJogviszonyokDTO to save.
     * @param szerzodesesJogviszonyokDTO the szerzodesesJogviszonyokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated szerzodesesJogviszonyokDTO,
     * or with status {@code 400 (Bad Request)} if the szerzodesesJogviszonyokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the szerzodesesJogviszonyokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the szerzodesesJogviszonyokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SzerzodesesJogviszonyokDTO> partialUpdateSzerzodesesJogviszonyok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SzerzodesesJogviszonyok partially : {}, {}", id, szerzodesesJogviszonyokDTO);
        if (szerzodesesJogviszonyokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, szerzodesesJogviszonyokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!szerzodesesJogviszonyokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SzerzodesesJogviszonyokDTO> result;
        try {
            result = szerzodesesJogviszonyokService.partialUpdate(szerzodesesJogviszonyokDTO);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "invaliddates");
        }

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, szerzodesesJogviszonyokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /szerzodeses-jogviszonyoks} : get all the szerzodesesJogviszonyoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of szerzodesesJogviszonyoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SzerzodesesJogviszonyokDTO>> getAllSzerzodesesJogviszonyoks(
        SzerzodesesJogviszonyokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SzerzodesesJogviszonyoks by criteria: {}", criteria);

        Page<SzerzodesesJogviszonyokDTO> page = szerzodesesJogviszonyokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /szerzodeses-jogviszonyoks/count} : count all the szerzodesesJogviszonyoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSzerzodesesJogviszonyoks(SzerzodesesJogviszonyokCriteria criteria) {
        LOG.debug("REST request to count SzerzodesesJogviszonyoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(szerzodesesJogviszonyokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /szerzodeses-jogviszonyoks/:id} : get the "id" szerzodesesJogviszonyok.
     *
     * @param id the id of the szerzodesesJogviszonyokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the szerzodesesJogviszonyokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SzerzodesesJogviszonyokDTO> getSzerzodesesJogviszonyok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SzerzodesesJogviszonyok : {}", id);
        Optional<SzerzodesesJogviszonyokDTO> szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(szerzodesesJogviszonyokDTO);
    }

    /**
     * {@code DELETE  /szerzodeses-jogviszonyoks/:id} : delete the "id" szerzodesesJogviszonyok.
     *
     * @param id the id of the szerzodesesJogviszonyokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSzerzodesesJogviszonyok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SzerzodesesJogviszonyok : {}", id);
        szerzodesesJogviszonyokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.fintech.erp.web.rest;

import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.MunkavallalokQueryService;
import com.fintech.erp.service.MunkavallalokService;
import com.fintech.erp.service.criteria.MunkavallalokCriteria;
import com.fintech.erp.service.dto.MunkavallalokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.Munkavallalok}.
 */
@RestController
@RequestMapping("/api/munkavallaloks")
public class MunkavallalokResource {

    private static final Logger LOG = LoggerFactory.getLogger(MunkavallalokResource.class);

    private static final String ENTITY_NAME = "munkavallalok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MunkavallalokService munkavallalokService;

    private final MunkavallalokRepository munkavallalokRepository;

    private final MunkavallalokQueryService munkavallalokQueryService;

    public MunkavallalokResource(
        MunkavallalokService munkavallalokService,
        MunkavallalokRepository munkavallalokRepository,
        MunkavallalokQueryService munkavallalokQueryService
    ) {
        this.munkavallalokService = munkavallalokService;
        this.munkavallalokRepository = munkavallalokRepository;
        this.munkavallalokQueryService = munkavallalokQueryService;
    }

    /**
     * {@code POST  /munkavallaloks} : Create a new munkavallalok.
     *
     * @param munkavallalokDTO the munkavallalokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new munkavallalokDTO, or with status {@code 400 (Bad Request)} if the munkavallalok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MunkavallalokDTO> createMunkavallalok(@RequestBody MunkavallalokDTO munkavallalokDTO) throws URISyntaxException {
        LOG.debug("REST request to save Munkavallalok : {}", munkavallalokDTO);
        if (munkavallalokDTO.getId() != null) {
            throw new BadRequestAlertException("A new munkavallalok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        munkavallalokDTO = munkavallalokService.save(munkavallalokDTO);
        return ResponseEntity.created(new URI("/api/munkavallaloks/" + munkavallalokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, munkavallalokDTO.getId().toString()))
            .body(munkavallalokDTO);
    }

    /**
     * {@code PUT  /munkavallaloks/:id} : Updates an existing munkavallalok.
     *
     * @param id the id of the munkavallalokDTO to save.
     * @param munkavallalokDTO the munkavallalokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated munkavallalokDTO,
     * or with status {@code 400 (Bad Request)} if the munkavallalokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the munkavallalokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MunkavallalokDTO> updateMunkavallalok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MunkavallalokDTO munkavallalokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Munkavallalok : {}, {}", id, munkavallalokDTO);
        if (munkavallalokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, munkavallalokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!munkavallalokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        munkavallalokDTO = munkavallalokService.update(munkavallalokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, munkavallalokDTO.getId().toString()))
            .body(munkavallalokDTO);
    }

    /**
     * {@code PATCH  /munkavallaloks/:id} : Partial updates given fields of an existing munkavallalok, field will ignore if it is null
     *
     * @param id the id of the munkavallalokDTO to save.
     * @param munkavallalokDTO the munkavallalokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated munkavallalokDTO,
     * or with status {@code 400 (Bad Request)} if the munkavallalokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the munkavallalokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the munkavallalokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MunkavallalokDTO> partialUpdateMunkavallalok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MunkavallalokDTO munkavallalokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Munkavallalok partially : {}, {}", id, munkavallalokDTO);
        if (munkavallalokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, munkavallalokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!munkavallalokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MunkavallalokDTO> result = munkavallalokService.partialUpdate(munkavallalokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, munkavallalokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /munkavallaloks} : get all the munkavallaloks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of munkavallaloks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MunkavallalokDTO>> getAllMunkavallaloks(
        MunkavallalokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Munkavallaloks by criteria: {}", criteria);

        Page<MunkavallalokDTO> page = munkavallalokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /munkavallaloks/count} : count all the munkavallaloks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMunkavallaloks(MunkavallalokCriteria criteria) {
        LOG.debug("REST request to count Munkavallaloks by criteria: {}", criteria);
        return ResponseEntity.ok().body(munkavallalokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /munkavallaloks/:id} : get the "id" munkavallalok.
     *
     * @param id the id of the munkavallalokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the munkavallalokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MunkavallalokDTO> getMunkavallalok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Munkavallalok : {}", id);
        Optional<MunkavallalokDTO> munkavallalokDTO = munkavallalokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(munkavallalokDTO);
    }

    /**
     * {@code DELETE  /munkavallaloks/:id} : delete the "id" munkavallalok.
     *
     * @param id the id of the munkavallalokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMunkavallalok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Munkavallalok : {}", id);
        munkavallalokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

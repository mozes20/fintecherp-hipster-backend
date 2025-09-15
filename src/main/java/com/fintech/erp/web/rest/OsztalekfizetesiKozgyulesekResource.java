package com.fintech.erp.web.rest;

import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.OsztalekfizetesiKozgyulesekQueryService;
import com.fintech.erp.service.OsztalekfizetesiKozgyulesekService;
import com.fintech.erp.service.criteria.OsztalekfizetesiKozgyulesekCriteria;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesek}.
 */
@RestController
@RequestMapping("/api/osztalekfizetesi-kozgyuleseks")
public class OsztalekfizetesiKozgyulesekResource {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekResource.class);

    private static final String ENTITY_NAME = "osztalekfizetesiKozgyulesek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsztalekfizetesiKozgyulesekService osztalekfizetesiKozgyulesekService;

    private final OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository;

    private final OsztalekfizetesiKozgyulesekQueryService osztalekfizetesiKozgyulesekQueryService;

    public OsztalekfizetesiKozgyulesekResource(
        OsztalekfizetesiKozgyulesekService osztalekfizetesiKozgyulesekService,
        OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository,
        OsztalekfizetesiKozgyulesekQueryService osztalekfizetesiKozgyulesekQueryService
    ) {
        this.osztalekfizetesiKozgyulesekService = osztalekfizetesiKozgyulesekService;
        this.osztalekfizetesiKozgyulesekRepository = osztalekfizetesiKozgyulesekRepository;
        this.osztalekfizetesiKozgyulesekQueryService = osztalekfizetesiKozgyulesekQueryService;
    }

    /**
     * {@code POST  /osztalekfizetesi-kozgyuleseks} : Create a new osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the osztalekfizetesiKozgyulesekDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new osztalekfizetesiKozgyulesekDTO, or with status {@code 400 (Bad Request)} if the osztalekfizetesiKozgyulesek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> createOsztalekfizetesiKozgyulesek(
        @Valid @RequestBody OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);
        if (osztalekfizetesiKozgyulesekDTO.getId() != null) {
            throw new BadRequestAlertException("A new osztalekfizetesiKozgyulesek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekService.save(osztalekfizetesiKozgyulesekDTO);
        return ResponseEntity.created(new URI("/api/osztalekfizetesi-kozgyuleseks/" + osztalekfizetesiKozgyulesekDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, osztalekfizetesiKozgyulesekDTO.getId().toString())
            )
            .body(osztalekfizetesiKozgyulesekDTO);
    }

    /**
     * {@code PUT  /osztalekfizetesi-kozgyuleseks/:id} : Updates an existing osztalekfizetesiKozgyulesek.
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to save.
     * @param osztalekfizetesiKozgyulesekDTO the osztalekfizetesiKozgyulesekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osztalekfizetesiKozgyulesekDTO,
     * or with status {@code 400 (Bad Request)} if the osztalekfizetesiKozgyulesekDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the osztalekfizetesiKozgyulesekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> updateOsztalekfizetesiKozgyulesek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update OsztalekfizetesiKozgyulesek : {}, {}", id, osztalekfizetesiKozgyulesekDTO);
        if (osztalekfizetesiKozgyulesekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, osztalekfizetesiKozgyulesekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!osztalekfizetesiKozgyulesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekService.update(osztalekfizetesiKozgyulesekDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, osztalekfizetesiKozgyulesekDTO.getId().toString())
            )
            .body(osztalekfizetesiKozgyulesekDTO);
    }

    /**
     * {@code PATCH  /osztalekfizetesi-kozgyuleseks/:id} : Partial updates given fields of an existing osztalekfizetesiKozgyulesek, field will ignore if it is null
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to save.
     * @param osztalekfizetesiKozgyulesekDTO the osztalekfizetesiKozgyulesekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osztalekfizetesiKozgyulesekDTO,
     * or with status {@code 400 (Bad Request)} if the osztalekfizetesiKozgyulesekDTO is not valid,
     * or with status {@code 404 (Not Found)} if the osztalekfizetesiKozgyulesekDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the osztalekfizetesiKozgyulesekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> partialUpdateOsztalekfizetesiKozgyulesek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OsztalekfizetesiKozgyulesek partially : {}, {}", id, osztalekfizetesiKozgyulesekDTO);
        if (osztalekfizetesiKozgyulesekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, osztalekfizetesiKozgyulesekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!osztalekfizetesiKozgyulesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OsztalekfizetesiKozgyulesekDTO> result = osztalekfizetesiKozgyulesekService.partialUpdate(osztalekfizetesiKozgyulesekDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, osztalekfizetesiKozgyulesekDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /osztalekfizetesi-kozgyuleseks} : get all the osztalekfizetesiKozgyuleseks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of osztalekfizetesiKozgyuleseks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OsztalekfizetesiKozgyulesekDTO>> getAllOsztalekfizetesiKozgyuleseks(
        OsztalekfizetesiKozgyulesekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyuleseks by criteria: {}", criteria);

        Page<OsztalekfizetesiKozgyulesekDTO> page = osztalekfizetesiKozgyulesekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /osztalekfizetesi-kozgyuleseks/count} : count all the osztalekfizetesiKozgyuleseks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOsztalekfizetesiKozgyuleseks(OsztalekfizetesiKozgyulesekCriteria criteria) {
        LOG.debug("REST request to count OsztalekfizetesiKozgyuleseks by criteria: {}", criteria);
        return ResponseEntity.ok().body(osztalekfizetesiKozgyulesekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /osztalekfizetesi-kozgyuleseks/:id} : get the "id" osztalekfizetesiKozgyulesek.
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the osztalekfizetesiKozgyulesekDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OsztalekfizetesiKozgyulesekDTO> getOsztalekfizetesiKozgyulesek(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OsztalekfizetesiKozgyulesek : {}", id);
        Optional<OsztalekfizetesiKozgyulesekDTO> osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(osztalekfizetesiKozgyulesekDTO);
    }

    /**
     * {@code DELETE  /osztalekfizetesi-kozgyuleseks/:id} : delete the "id" osztalekfizetesiKozgyulesek.
     *
     * @param id the id of the osztalekfizetesiKozgyulesekDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOsztalekfizetesiKozgyulesek(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OsztalekfizetesiKozgyulesek : {}", id);
        osztalekfizetesiKozgyulesekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

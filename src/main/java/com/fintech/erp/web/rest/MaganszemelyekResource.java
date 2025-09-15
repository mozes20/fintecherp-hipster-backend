package com.fintech.erp.web.rest;

import com.fintech.erp.repository.MaganszemelyekRepository;
import com.fintech.erp.service.MaganszemelyekQueryService;
import com.fintech.erp.service.MaganszemelyekService;
import com.fintech.erp.service.criteria.MaganszemelyekCriteria;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.Maganszemelyek}.
 */
@RestController
@RequestMapping("/api/maganszemelyeks")
public class MaganszemelyekResource {

    private static final Logger LOG = LoggerFactory.getLogger(MaganszemelyekResource.class);

    private static final String ENTITY_NAME = "maganszemelyek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaganszemelyekService maganszemelyekService;

    private final MaganszemelyekRepository maganszemelyekRepository;

    private final MaganszemelyekQueryService maganszemelyekQueryService;

    public MaganszemelyekResource(
        MaganszemelyekService maganszemelyekService,
        MaganszemelyekRepository maganszemelyekRepository,
        MaganszemelyekQueryService maganszemelyekQueryService
    ) {
        this.maganszemelyekService = maganszemelyekService;
        this.maganszemelyekRepository = maganszemelyekRepository;
        this.maganszemelyekQueryService = maganszemelyekQueryService;
    }

    /**
     * {@code POST  /maganszemelyeks} : Create a new maganszemelyek.
     *
     * @param maganszemelyekDTO the maganszemelyekDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maganszemelyekDTO, or with status {@code 400 (Bad Request)} if the maganszemelyek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MaganszemelyekDTO> createMaganszemelyek(@Valid @RequestBody MaganszemelyekDTO maganszemelyekDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Maganszemelyek : {}", maganszemelyekDTO);
        if (maganszemelyekDTO.getId() != null) {
            throw new BadRequestAlertException("A new maganszemelyek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        maganszemelyekDTO = maganszemelyekService.save(maganszemelyekDTO);
        return ResponseEntity.created(new URI("/api/maganszemelyeks/" + maganszemelyekDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, maganszemelyekDTO.getId().toString()))
            .body(maganszemelyekDTO);
    }

    /**
     * {@code PUT  /maganszemelyeks/:id} : Updates an existing maganszemelyek.
     *
     * @param id the id of the maganszemelyekDTO to save.
     * @param maganszemelyekDTO the maganszemelyekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maganszemelyekDTO,
     * or with status {@code 400 (Bad Request)} if the maganszemelyekDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maganszemelyekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaganszemelyekDTO> updateMaganszemelyek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MaganszemelyekDTO maganszemelyekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Maganszemelyek : {}, {}", id, maganszemelyekDTO);
        if (maganszemelyekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maganszemelyekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maganszemelyekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        maganszemelyekDTO = maganszemelyekService.update(maganszemelyekDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maganszemelyekDTO.getId().toString()))
            .body(maganszemelyekDTO);
    }

    /**
     * {@code PATCH  /maganszemelyeks/:id} : Partial updates given fields of an existing maganszemelyek, field will ignore if it is null
     *
     * @param id the id of the maganszemelyekDTO to save.
     * @param maganszemelyekDTO the maganszemelyekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maganszemelyekDTO,
     * or with status {@code 400 (Bad Request)} if the maganszemelyekDTO is not valid,
     * or with status {@code 404 (Not Found)} if the maganszemelyekDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the maganszemelyekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaganszemelyekDTO> partialUpdateMaganszemelyek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MaganszemelyekDTO maganszemelyekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Maganszemelyek partially : {}, {}", id, maganszemelyekDTO);
        if (maganszemelyekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maganszemelyekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maganszemelyekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaganszemelyekDTO> result = maganszemelyekService.partialUpdate(maganszemelyekDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maganszemelyekDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /maganszemelyeks} : get all the maganszemelyeks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maganszemelyeks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MaganszemelyekDTO>> getAllMaganszemelyeks(
        MaganszemelyekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Maganszemelyeks by criteria: {}", criteria);

        Page<MaganszemelyekDTO> page = maganszemelyekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /maganszemelyeks/count} : count all the maganszemelyeks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMaganszemelyeks(MaganszemelyekCriteria criteria) {
        LOG.debug("REST request to count Maganszemelyeks by criteria: {}", criteria);
        return ResponseEntity.ok().body(maganszemelyekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /maganszemelyeks/:id} : get the "id" maganszemelyek.
     *
     * @param id the id of the maganszemelyekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maganszemelyekDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaganszemelyekDTO> getMaganszemelyek(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Maganszemelyek : {}", id);
        Optional<MaganszemelyekDTO> maganszemelyekDTO = maganszemelyekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(maganszemelyekDTO);
    }

    /**
     * {@code DELETE  /maganszemelyeks/:id} : delete the "id" maganszemelyek.
     *
     * @param id the id of the maganszemelyekDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaganszemelyek(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Maganszemelyek : {}", id);
        maganszemelyekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

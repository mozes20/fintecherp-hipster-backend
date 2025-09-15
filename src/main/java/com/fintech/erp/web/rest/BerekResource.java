package com.fintech.erp.web.rest;

import com.fintech.erp.repository.BerekRepository;
import com.fintech.erp.service.BerekQueryService;
import com.fintech.erp.service.BerekService;
import com.fintech.erp.service.criteria.BerekCriteria;
import com.fintech.erp.service.dto.BerekDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.Berek}.
 */
@RestController
@RequestMapping("/api/bereks")
public class BerekResource {

    private static final Logger LOG = LoggerFactory.getLogger(BerekResource.class);

    private static final String ENTITY_NAME = "berek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BerekService berekService;

    private final BerekRepository berekRepository;

    private final BerekQueryService berekQueryService;

    public BerekResource(BerekService berekService, BerekRepository berekRepository, BerekQueryService berekQueryService) {
        this.berekService = berekService;
        this.berekRepository = berekRepository;
        this.berekQueryService = berekQueryService;
    }

    /**
     * {@code POST  /bereks} : Create a new berek.
     *
     * @param berekDTO the berekDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new berekDTO, or with status {@code 400 (Bad Request)} if the berek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BerekDTO> createBerek(@Valid @RequestBody BerekDTO berekDTO) throws URISyntaxException {
        LOG.debug("REST request to save Berek : {}", berekDTO);
        if (berekDTO.getId() != null) {
            throw new BadRequestAlertException("A new berek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        berekDTO = berekService.save(berekDTO);
        return ResponseEntity.created(new URI("/api/bereks/" + berekDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, berekDTO.getId().toString()))
            .body(berekDTO);
    }

    /**
     * {@code PUT  /bereks/:id} : Updates an existing berek.
     *
     * @param id the id of the berekDTO to save.
     * @param berekDTO the berekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated berekDTO,
     * or with status {@code 400 (Bad Request)} if the berekDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the berekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BerekDTO> updateBerek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BerekDTO berekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Berek : {}, {}", id, berekDTO);
        if (berekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, berekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!berekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        berekDTO = berekService.update(berekDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, berekDTO.getId().toString()))
            .body(berekDTO);
    }

    /**
     * {@code PATCH  /bereks/:id} : Partial updates given fields of an existing berek, field will ignore if it is null
     *
     * @param id the id of the berekDTO to save.
     * @param berekDTO the berekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated berekDTO,
     * or with status {@code 400 (Bad Request)} if the berekDTO is not valid,
     * or with status {@code 404 (Not Found)} if the berekDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the berekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BerekDTO> partialUpdateBerek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BerekDTO berekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Berek partially : {}, {}", id, berekDTO);
        if (berekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, berekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!berekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BerekDTO> result = berekService.partialUpdate(berekDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, berekDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bereks} : get all the bereks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bereks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BerekDTO>> getAllBereks(
        BerekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Bereks by criteria: {}", criteria);

        Page<BerekDTO> page = berekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bereks/count} : count all the bereks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBereks(BerekCriteria criteria) {
        LOG.debug("REST request to count Bereks by criteria: {}", criteria);
        return ResponseEntity.ok().body(berekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bereks/:id} : get the "id" berek.
     *
     * @param id the id of the berekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the berekDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BerekDTO> getBerek(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Berek : {}", id);
        Optional<BerekDTO> berekDTO = berekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(berekDTO);
    }

    /**
     * {@code DELETE  /bereks/:id} : delete the "id" berek.
     *
     * @param id the id of the berekDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBerek(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Berek : {}", id);
        berekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

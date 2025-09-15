package com.fintech.erp.web.rest;

import com.fintech.erp.repository.PartnerCegMunkavallalokRepository;
import com.fintech.erp.service.PartnerCegMunkavallalokQueryService;
import com.fintech.erp.service.PartnerCegMunkavallalokService;
import com.fintech.erp.service.criteria.PartnerCegMunkavallalokCriteria;
import com.fintech.erp.service.dto.PartnerCegMunkavallalokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.PartnerCegMunkavallalok}.
 */
@RestController
@RequestMapping("/api/partner-ceg-munkavallaloks")
public class PartnerCegMunkavallalokResource {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegMunkavallalokResource.class);

    private static final String ENTITY_NAME = "partnerCegMunkavallalok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerCegMunkavallalokService partnerCegMunkavallalokService;

    private final PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository;

    private final PartnerCegMunkavallalokQueryService partnerCegMunkavallalokQueryService;

    public PartnerCegMunkavallalokResource(
        PartnerCegMunkavallalokService partnerCegMunkavallalokService,
        PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository,
        PartnerCegMunkavallalokQueryService partnerCegMunkavallalokQueryService
    ) {
        this.partnerCegMunkavallalokService = partnerCegMunkavallalokService;
        this.partnerCegMunkavallalokRepository = partnerCegMunkavallalokRepository;
        this.partnerCegMunkavallalokQueryService = partnerCegMunkavallalokQueryService;
    }

    /**
     * {@code POST  /partner-ceg-munkavallaloks} : Create a new partnerCegMunkavallalok.
     *
     * @param partnerCegMunkavallalokDTO the partnerCegMunkavallalokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerCegMunkavallalokDTO, or with status {@code 400 (Bad Request)} if the partnerCegMunkavallalok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartnerCegMunkavallalokDTO> createPartnerCegMunkavallalok(
        @RequestBody PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save PartnerCegMunkavallalok : {}", partnerCegMunkavallalokDTO);
        if (partnerCegMunkavallalokDTO.getId() != null) {
            throw new BadRequestAlertException("A new partnerCegMunkavallalok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partnerCegMunkavallalokDTO = partnerCegMunkavallalokService.save(partnerCegMunkavallalokDTO);
        return ResponseEntity.created(new URI("/api/partner-ceg-munkavallaloks/" + partnerCegMunkavallalokDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, partnerCegMunkavallalokDTO.getId().toString())
            )
            .body(partnerCegMunkavallalokDTO);
    }

    /**
     * {@code PUT  /partner-ceg-munkavallaloks/:id} : Updates an existing partnerCegMunkavallalok.
     *
     * @param id the id of the partnerCegMunkavallalokDTO to save.
     * @param partnerCegMunkavallalokDTO the partnerCegMunkavallalokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerCegMunkavallalokDTO,
     * or with status {@code 400 (Bad Request)} if the partnerCegMunkavallalokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerCegMunkavallalokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartnerCegMunkavallalokDTO> updatePartnerCegMunkavallalok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PartnerCegMunkavallalok : {}, {}", id, partnerCegMunkavallalokDTO);
        if (partnerCegMunkavallalokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerCegMunkavallalokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerCegMunkavallalokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partnerCegMunkavallalokDTO = partnerCegMunkavallalokService.update(partnerCegMunkavallalokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerCegMunkavallalokDTO.getId().toString()))
            .body(partnerCegMunkavallalokDTO);
    }

    /**
     * {@code PATCH  /partner-ceg-munkavallaloks/:id} : Partial updates given fields of an existing partnerCegMunkavallalok, field will ignore if it is null
     *
     * @param id the id of the partnerCegMunkavallalokDTO to save.
     * @param partnerCegMunkavallalokDTO the partnerCegMunkavallalokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerCegMunkavallalokDTO,
     * or with status {@code 400 (Bad Request)} if the partnerCegMunkavallalokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partnerCegMunkavallalokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partnerCegMunkavallalokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartnerCegMunkavallalokDTO> partialUpdatePartnerCegMunkavallalok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PartnerCegMunkavallalok partially : {}, {}", id, partnerCegMunkavallalokDTO);
        if (partnerCegMunkavallalokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerCegMunkavallalokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerCegMunkavallalokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartnerCegMunkavallalokDTO> result = partnerCegMunkavallalokService.partialUpdate(partnerCegMunkavallalokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerCegMunkavallalokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /partner-ceg-munkavallaloks} : get all the partnerCegMunkavallaloks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerCegMunkavallaloks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartnerCegMunkavallalokDTO>> getAllPartnerCegMunkavallaloks(
        PartnerCegMunkavallalokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get PartnerCegMunkavallaloks by criteria: {}", criteria);

        Page<PartnerCegMunkavallalokDTO> page = partnerCegMunkavallalokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partner-ceg-munkavallaloks/count} : count all the partnerCegMunkavallaloks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPartnerCegMunkavallaloks(PartnerCegMunkavallalokCriteria criteria) {
        LOG.debug("REST request to count PartnerCegMunkavallaloks by criteria: {}", criteria);
        return ResponseEntity.ok().body(partnerCegMunkavallalokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /partner-ceg-munkavallaloks/:id} : get the "id" partnerCegMunkavallalok.
     *
     * @param id the id of the partnerCegMunkavallalokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerCegMunkavallalokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartnerCegMunkavallalokDTO> getPartnerCegMunkavallalok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PartnerCegMunkavallalok : {}", id);
        Optional<PartnerCegMunkavallalokDTO> partnerCegMunkavallalokDTO = partnerCegMunkavallalokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerCegMunkavallalokDTO);
    }

    /**
     * {@code DELETE  /partner-ceg-munkavallaloks/:id} : delete the "id" partnerCegMunkavallalok.
     *
     * @param id the id of the partnerCegMunkavallalokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartnerCegMunkavallalok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PartnerCegMunkavallalok : {}", id);
        partnerCegMunkavallalokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

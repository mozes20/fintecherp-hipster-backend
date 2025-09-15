package com.fintech.erp.web.rest;

import com.fintech.erp.repository.PartnerCegKapcsolattartokRepository;
import com.fintech.erp.service.PartnerCegKapcsolattartokQueryService;
import com.fintech.erp.service.PartnerCegKapcsolattartokService;
import com.fintech.erp.service.criteria.PartnerCegKapcsolattartokCriteria;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.PartnerCegKapcsolattartok}.
 */
@RestController
@RequestMapping("/api/partner-ceg-kapcsolattartoks")
public class PartnerCegKapcsolattartokResource {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegKapcsolattartokResource.class);

    private static final String ENTITY_NAME = "partnerCegKapcsolattartok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerCegKapcsolattartokService partnerCegKapcsolattartokService;

    private final PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository;

    private final PartnerCegKapcsolattartokQueryService partnerCegKapcsolattartokQueryService;

    public PartnerCegKapcsolattartokResource(
        PartnerCegKapcsolattartokService partnerCegKapcsolattartokService,
        PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository,
        PartnerCegKapcsolattartokQueryService partnerCegKapcsolattartokQueryService
    ) {
        this.partnerCegKapcsolattartokService = partnerCegKapcsolattartokService;
        this.partnerCegKapcsolattartokRepository = partnerCegKapcsolattartokRepository;
        this.partnerCegKapcsolattartokQueryService = partnerCegKapcsolattartokQueryService;
    }

    /**
     * {@code POST  /partner-ceg-kapcsolattartoks} : Create a new partnerCegKapcsolattartok.
     *
     * @param partnerCegKapcsolattartokDTO the partnerCegKapcsolattartokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerCegKapcsolattartokDTO, or with status {@code 400 (Bad Request)} if the partnerCegKapcsolattartok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartnerCegKapcsolattartokDTO> createPartnerCegKapcsolattartok(
        @RequestBody PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save PartnerCegKapcsolattartok : {}", partnerCegKapcsolattartokDTO);
        if (partnerCegKapcsolattartokDTO.getId() != null) {
            throw new BadRequestAlertException("A new partnerCegKapcsolattartok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokService.save(partnerCegKapcsolattartokDTO);
        return ResponseEntity.created(new URI("/api/partner-ceg-kapcsolattartoks/" + partnerCegKapcsolattartokDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, partnerCegKapcsolattartokDTO.getId().toString())
            )
            .body(partnerCegKapcsolattartokDTO);
    }

    /**
     * {@code PUT  /partner-ceg-kapcsolattartoks/:id} : Updates an existing partnerCegKapcsolattartok.
     *
     * @param id the id of the partnerCegKapcsolattartokDTO to save.
     * @param partnerCegKapcsolattartokDTO the partnerCegKapcsolattartokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerCegKapcsolattartokDTO,
     * or with status {@code 400 (Bad Request)} if the partnerCegKapcsolattartokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerCegKapcsolattartokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartnerCegKapcsolattartokDTO> updatePartnerCegKapcsolattartok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PartnerCegKapcsolattartok : {}, {}", id, partnerCegKapcsolattartokDTO);
        if (partnerCegKapcsolattartokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerCegKapcsolattartokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerCegKapcsolattartokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokService.update(partnerCegKapcsolattartokDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerCegKapcsolattartokDTO.getId().toString())
            )
            .body(partnerCegKapcsolattartokDTO);
    }

    /**
     * {@code PATCH  /partner-ceg-kapcsolattartoks/:id} : Partial updates given fields of an existing partnerCegKapcsolattartok, field will ignore if it is null
     *
     * @param id the id of the partnerCegKapcsolattartokDTO to save.
     * @param partnerCegKapcsolattartokDTO the partnerCegKapcsolattartokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerCegKapcsolattartokDTO,
     * or with status {@code 400 (Bad Request)} if the partnerCegKapcsolattartokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partnerCegKapcsolattartokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partnerCegKapcsolattartokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartnerCegKapcsolattartokDTO> partialUpdatePartnerCegKapcsolattartok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PartnerCegKapcsolattartok partially : {}, {}", id, partnerCegKapcsolattartokDTO);
        if (partnerCegKapcsolattartokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerCegKapcsolattartokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerCegKapcsolattartokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartnerCegKapcsolattartokDTO> result = partnerCegKapcsolattartokService.partialUpdate(partnerCegKapcsolattartokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerCegKapcsolattartokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /partner-ceg-kapcsolattartoks} : get all the partnerCegKapcsolattartoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerCegKapcsolattartoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartnerCegKapcsolattartokDTO>> getAllPartnerCegKapcsolattartoks(
        PartnerCegKapcsolattartokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get PartnerCegKapcsolattartoks by criteria: {}", criteria);

        Page<PartnerCegKapcsolattartokDTO> page = partnerCegKapcsolattartokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partner-ceg-kapcsolattartoks/count} : count all the partnerCegKapcsolattartoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPartnerCegKapcsolattartoks(PartnerCegKapcsolattartokCriteria criteria) {
        LOG.debug("REST request to count PartnerCegKapcsolattartoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(partnerCegKapcsolattartokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /partner-ceg-kapcsolattartoks/:id} : get the "id" partnerCegKapcsolattartok.
     *
     * @param id the id of the partnerCegKapcsolattartokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerCegKapcsolattartokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartnerCegKapcsolattartokDTO> getPartnerCegKapcsolattartok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PartnerCegKapcsolattartok : {}", id);
        Optional<PartnerCegKapcsolattartokDTO> partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerCegKapcsolattartokDTO);
    }

    /**
     * {@code DELETE  /partner-ceg-kapcsolattartoks/:id} : delete the "id" partnerCegKapcsolattartok.
     *
     * @param id the id of the partnerCegKapcsolattartokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartnerCegKapcsolattartok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PartnerCegKapcsolattartok : {}", id);
        partnerCegKapcsolattartokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

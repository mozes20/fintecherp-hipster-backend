package com.fintech.erp.web.rest;

import com.fintech.erp.repository.PartnerCegAdatokRepository;
import com.fintech.erp.service.PartnerCegAdatokQueryService;
import com.fintech.erp.service.PartnerCegAdatokService;
import com.fintech.erp.service.criteria.PartnerCegAdatokCriteria;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.PartnerCegAdatok}.
 */
@RestController
@RequestMapping("/api/partner-ceg-adatoks")
public class PartnerCegAdatokResource {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegAdatokResource.class);

    private static final String ENTITY_NAME = "partnerCegAdatok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerCegAdatokService partnerCegAdatokService;

    private final PartnerCegAdatokRepository partnerCegAdatokRepository;

    private final PartnerCegAdatokQueryService partnerCegAdatokQueryService;

    public PartnerCegAdatokResource(
        PartnerCegAdatokService partnerCegAdatokService,
        PartnerCegAdatokRepository partnerCegAdatokRepository,
        PartnerCegAdatokQueryService partnerCegAdatokQueryService
    ) {
        this.partnerCegAdatokService = partnerCegAdatokService;
        this.partnerCegAdatokRepository = partnerCegAdatokRepository;
        this.partnerCegAdatokQueryService = partnerCegAdatokQueryService;
    }

    /**
     * {@code POST  /partner-ceg-adatoks} : Create a new partnerCegAdatok.
     *
     * @param partnerCegAdatokDTO the partnerCegAdatokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerCegAdatokDTO, or with status {@code 400 (Bad Request)} if the partnerCegAdatok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartnerCegAdatokDTO> createPartnerCegAdatok(@RequestBody PartnerCegAdatokDTO partnerCegAdatokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PartnerCegAdatok : {}", partnerCegAdatokDTO);
        if (partnerCegAdatokDTO.getId() != null) {
            throw new BadRequestAlertException("A new partnerCegAdatok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partnerCegAdatokDTO = partnerCegAdatokService.save(partnerCegAdatokDTO);
        return ResponseEntity.created(new URI("/api/partner-ceg-adatoks/" + partnerCegAdatokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, partnerCegAdatokDTO.getId().toString()))
            .body(partnerCegAdatokDTO);
    }

    /**
     * {@code PUT  /partner-ceg-adatoks/:id} : Updates an existing partnerCegAdatok.
     *
     * @param id the id of the partnerCegAdatokDTO to save.
     * @param partnerCegAdatokDTO the partnerCegAdatokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerCegAdatokDTO,
     * or with status {@code 400 (Bad Request)} if the partnerCegAdatokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerCegAdatokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartnerCegAdatokDTO> updatePartnerCegAdatok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartnerCegAdatokDTO partnerCegAdatokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PartnerCegAdatok : {}, {}", id, partnerCegAdatokDTO);
        if (partnerCegAdatokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerCegAdatokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerCegAdatokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partnerCegAdatokDTO = partnerCegAdatokService.update(partnerCegAdatokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerCegAdatokDTO.getId().toString()))
            .body(partnerCegAdatokDTO);
    }

    /**
     * {@code PATCH  /partner-ceg-adatoks/:id} : Partial updates given fields of an existing partnerCegAdatok, field will ignore if it is null
     *
     * @param id the id of the partnerCegAdatokDTO to save.
     * @param partnerCegAdatokDTO the partnerCegAdatokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerCegAdatokDTO,
     * or with status {@code 400 (Bad Request)} if the partnerCegAdatokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partnerCegAdatokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partnerCegAdatokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartnerCegAdatokDTO> partialUpdatePartnerCegAdatok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PartnerCegAdatokDTO partnerCegAdatokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PartnerCegAdatok partially : {}, {}", id, partnerCegAdatokDTO);
        if (partnerCegAdatokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerCegAdatokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerCegAdatokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartnerCegAdatokDTO> result = partnerCegAdatokService.partialUpdate(partnerCegAdatokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerCegAdatokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /partner-ceg-adatoks} : get all the partnerCegAdatoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerCegAdatoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartnerCegAdatokDTO>> getAllPartnerCegAdatoks(
        PartnerCegAdatokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get PartnerCegAdatoks by criteria: {}", criteria);

        Page<PartnerCegAdatokDTO> page = partnerCegAdatokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partner-ceg-adatoks/count} : count all the partnerCegAdatoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPartnerCegAdatoks(PartnerCegAdatokCriteria criteria) {
        LOG.debug("REST request to count PartnerCegAdatoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(partnerCegAdatokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /partner-ceg-adatoks/:id} : get the "id" partnerCegAdatok.
     *
     * @param id the id of the partnerCegAdatokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerCegAdatokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartnerCegAdatokDTO> getPartnerCegAdatok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PartnerCegAdatok : {}", id);
        Optional<PartnerCegAdatokDTO> partnerCegAdatokDTO = partnerCegAdatokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerCegAdatokDTO);
    }

    /**
     * {@code DELETE  /partner-ceg-adatoks/:id} : delete the "id" partnerCegAdatok.
     *
     * @param id the id of the partnerCegAdatokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartnerCegAdatok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PartnerCegAdatok : {}", id);
        partnerCegAdatokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

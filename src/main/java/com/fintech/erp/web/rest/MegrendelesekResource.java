package com.fintech.erp.web.rest;

import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.service.MegrendelesDokumentumokService;
import com.fintech.erp.service.MegrendelesekQueryService;
import com.fintech.erp.service.MegrendelesekService;
import com.fintech.erp.service.criteria.MegrendelesekCriteria;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fintech.erp.domain.Megrendelesek}.
 */
@RestController
@RequestMapping("/api/megrendeleseks")
public class MegrendelesekResource {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesekResource.class);

    private static final String ENTITY_NAME = "megrendelesek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MegrendelesekService megrendelesekService;

    private final MegrendelesekRepository megrendelesekRepository;

    private final MegrendelesekQueryService megrendelesekQueryService;

    private final MegrendelesDokumentumokService megrendelesDokumentumokService;

    public MegrendelesekResource(
        MegrendelesekService megrendelesekService,
        MegrendelesekRepository megrendelesekRepository,
        MegrendelesekQueryService megrendelesekQueryService,
        MegrendelesDokumentumokService megrendelesDokumentumokService
    ) {
        this.megrendelesekService = megrendelesekService;
        this.megrendelesekRepository = megrendelesekRepository;
        this.megrendelesekQueryService = megrendelesekQueryService;
        this.megrendelesDokumentumokService = megrendelesDokumentumokService;
    }

    /**
     * {@code POST  /megrendeleseks} : Create a new megrendelesek.
     *
     * @param megrendelesekDTO the megrendelesekDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new megrendelesekDTO, or with status {@code 400 (Bad Request)} if the megrendelesek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MegrendelesekDTO> createMegrendelesek(@RequestBody MegrendelesekDTO megrendelesekDTO) throws URISyntaxException {
        LOG.debug("REST request to save Megrendelesek : {}", megrendelesekDTO);
        if (megrendelesekDTO.getId() != null) {
            throw new BadRequestAlertException("A new megrendelesek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        megrendelesekDTO = megrendelesekService.save(megrendelesekDTO);
        return ResponseEntity.created(new URI("/api/megrendeleseks/" + megrendelesekDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, megrendelesekDTO.getId().toString()))
            .body(megrendelesekDTO);
    }

    /**
     * {@code PUT  /megrendeleseks/:id} : Updates an existing megrendelesek.
     *
     * @param id the id of the megrendelesekDTO to save.
     * @param megrendelesekDTO the megrendelesekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated megrendelesekDTO,
     * or with status {@code 400 (Bad Request)} if the megrendelesekDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the megrendelesekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MegrendelesekDTO> updateMegrendelesek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MegrendelesekDTO megrendelesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Megrendelesek : {}, {}", id, megrendelesekDTO);
        if (megrendelesekDTO.getId() == null) {
            LOG.debug("Body id hiányzik, beállítjuk a path paraméter alapján: {}", id);
            megrendelesekDTO.setId(id);
        }
        if (!Objects.equals(id, megrendelesekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!megrendelesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        megrendelesekDTO = megrendelesekService.update(megrendelesekDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, megrendelesekDTO.getId().toString()))
            .body(megrendelesekDTO);
    }

    /**
     * {@code PATCH  /megrendeleseks/:id} : Partial updates given fields of an existing megrendelesek, field will ignore if it is null
     *
     * @param id the id of the megrendelesekDTO to save.
     * @param megrendelesekDTO the megrendelesekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated megrendelesekDTO,
     * or with status {@code 400 (Bad Request)} if the megrendelesekDTO is not valid,
     * or with status {@code 404 (Not Found)} if the megrendelesekDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the megrendelesekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MegrendelesekDTO> partialUpdateMegrendelesek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MegrendelesekDTO megrendelesekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Megrendelesek partially : {}, {}", id, megrendelesekDTO);
        if (megrendelesekDTO.getId() == null) {
            LOG.debug("Body id hiányzik részleges frissítésnél, beállítjuk path param szerint: {}", id);
            megrendelesekDTO.setId(id);
        }
        if (!Objects.equals(id, megrendelesekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!megrendelesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MegrendelesekDTO> result = megrendelesekService.partialUpdate(megrendelesekDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, megrendelesekDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /megrendeleseks} : get all the megrendeleseks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of megrendeleseks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MegrendelesekDTO>> getAllMegrendeleseks(
        MegrendelesekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Megrendeleseks by criteria: {}", criteria);

        Page<MegrendelesekDTO> page = megrendelesekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}/dokumentumok")
    public ResponseEntity<List<MegrendelesDokumentumokDTO>> getDocumentsForMegrendeles(@PathVariable("id") Long id) {
        LOG.debug("REST request to get documents for Megrendeles : {}", id);
        if (!megrendelesekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        List<MegrendelesDokumentumokDTO> documents = megrendelesDokumentumokService.findAllByMegrendeles(id);
        return ResponseEntity.ok(documents);
    }

    /**
     * {@code GET  /megrendeleseks/count} : count all the megrendeleseks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMegrendeleseks(MegrendelesekCriteria criteria) {
        LOG.debug("REST request to count Megrendeleseks by criteria: {}", criteria);
        return ResponseEntity.ok().body(megrendelesekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /megrendeleseks/:id} : get the "id" megrendelesek.
     *
     * @param id the id of the megrendelesekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the megrendelesekDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MegrendelesekDTO> getMegrendelesek(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Megrendelesek : {}", id);
        Optional<MegrendelesekDTO> megrendelesekDTO = megrendelesekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(megrendelesekDTO);
    }

    /**
     * {@code DELETE  /megrendeleseks/:id} : delete the "id" megrendelesek.
     *
     * @param id the id of the megrendelesekDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMegrendelesek(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Megrendelesek : {}", id);
        megrendelesekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

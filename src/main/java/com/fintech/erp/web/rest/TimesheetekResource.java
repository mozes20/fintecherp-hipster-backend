package com.fintech.erp.web.rest;

import com.fintech.erp.repository.TimesheetekRepository;
import com.fintech.erp.service.TimesheetekQueryService;
import com.fintech.erp.service.TimesheetekService;
import com.fintech.erp.service.criteria.TimesheetekCriteria;
import com.fintech.erp.service.dto.TimesheetekDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.Timesheetek}.
 */
@RestController
@RequestMapping("/api/timesheeteks")
public class TimesheetekResource {

    private static final Logger LOG = LoggerFactory.getLogger(TimesheetekResource.class);

    private static final String ENTITY_NAME = "timesheetek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimesheetekService timesheetekService;

    private final TimesheetekRepository timesheetekRepository;

    private final TimesheetekQueryService timesheetekQueryService;

    public TimesheetekResource(
        TimesheetekService timesheetekService,
        TimesheetekRepository timesheetekRepository,
        TimesheetekQueryService timesheetekQueryService
    ) {
        this.timesheetekService = timesheetekService;
        this.timesheetekRepository = timesheetekRepository;
        this.timesheetekQueryService = timesheetekQueryService;
    }

    /**
     * {@code POST  /timesheeteks} : Create a new timesheetek.
     *
     * @param timesheetekDTO the timesheetekDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timesheetekDTO, or with status {@code 400 (Bad Request)} if the timesheetek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TimesheetekDTO> createTimesheetek(@Valid @RequestBody TimesheetekDTO timesheetekDTO) throws URISyntaxException {
        LOG.debug("REST request to save Timesheetek : {}", timesheetekDTO);
        if (timesheetekDTO.getId() != null) {
            throw new BadRequestAlertException("A new timesheetek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        timesheetekDTO = timesheetekService.save(timesheetekDTO);
        return ResponseEntity.created(new URI("/api/timesheeteks/" + timesheetekDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, timesheetekDTO.getId().toString()))
            .body(timesheetekDTO);
    }

    /**
     * {@code PUT  /timesheeteks/:id} : Updates an existing timesheetek.
     *
     * @param id the id of the timesheetekDTO to save.
     * @param timesheetekDTO the timesheetekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timesheetekDTO,
     * or with status {@code 400 (Bad Request)} if the timesheetekDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timesheetekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetekDTO> updateTimesheetek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TimesheetekDTO timesheetekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Timesheetek : {}, {}", id, timesheetekDTO);
        if (timesheetekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timesheetekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timesheetekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        timesheetekDTO = timesheetekService.update(timesheetekDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timesheetekDTO.getId().toString()))
            .body(timesheetekDTO);
    }

    /**
     * {@code PATCH  /timesheeteks/:id} : Partial updates given fields of an existing timesheetek, field will ignore if it is null
     *
     * @param id the id of the timesheetekDTO to save.
     * @param timesheetekDTO the timesheetekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timesheetekDTO,
     * or with status {@code 400 (Bad Request)} if the timesheetekDTO is not valid,
     * or with status {@code 404 (Not Found)} if the timesheetekDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the timesheetekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TimesheetekDTO> partialUpdateTimesheetek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TimesheetekDTO timesheetekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Timesheetek partially : {}, {}", id, timesheetekDTO);
        if (timesheetekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timesheetekDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timesheetekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TimesheetekDTO> result = timesheetekService.partialUpdate(timesheetekDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timesheetekDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /timesheeteks} : get all the timesheeteks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timesheeteks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TimesheetekDTO>> getAllTimesheeteks(
        TimesheetekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Timesheeteks by criteria: {}", criteria);

        Page<TimesheetekDTO> page = timesheetekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /timesheeteks/count} : count all the timesheeteks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTimesheeteks(TimesheetekCriteria criteria) {
        LOG.debug("REST request to count Timesheeteks by criteria: {}", criteria);
        return ResponseEntity.ok().body(timesheetekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /timesheeteks/:id} : get the "id" timesheetek.
     *
     * @param id the id of the timesheetekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timesheetekDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetekDTO> getTimesheetek(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Timesheetek : {}", id);
        Optional<TimesheetekDTO> timesheetekDTO = timesheetekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timesheetekDTO);
    }

    /**
     * {@code DELETE  /timesheeteks/:id} : delete the "id" timesheetek.
     *
     * @param id the id of the timesheetekDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimesheetek(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Timesheetek : {}", id);
        timesheetekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

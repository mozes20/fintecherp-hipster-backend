package com.fintech.erp.web.rest;

import com.fintech.erp.repository.AlvallalkozoiElszamolasokRepository;
import com.fintech.erp.service.AlvallalkozoiElszamolasokQueryService;
import com.fintech.erp.service.AlvallalkozoiElszamolasokService;
import com.fintech.erp.service.criteria.AlvallalkozoiElszamolasokCriteria;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.AlvallalkozoiElszamolasok}.
 */
@RestController
@RequestMapping("/api/alvallalkozoi-elszamolasoks")
public class AlvallalkozoiElszamolasokResource {

    private static final Logger LOG = LoggerFactory.getLogger(AlvallalkozoiElszamolasokResource.class);
    private static final String ENTITY_NAME = "alvallalkozoiElszamolasok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlvallalkozoiElszamolasokService service;
    private final AlvallalkozoiElszamolasokRepository repository;
    private final AlvallalkozoiElszamolasokQueryService queryService;

    public AlvallalkozoiElszamolasokResource(
        AlvallalkozoiElszamolasokService service,
        AlvallalkozoiElszamolasokRepository repository,
        AlvallalkozoiElszamolasokQueryService queryService
    ) {
        this.service = service;
        this.repository = repository;
        this.queryService = queryService;
    }

    @PostMapping("")
    public ResponseEntity<AlvallalkozoiElszamolasokDTO> create(@RequestBody AlvallalkozoiElszamolasokDTO dto)
        throws URISyntaxException {
        LOG.debug("REST request to save AlvallalkozoiElszamolasok : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new alvallalkozoiElszamolasok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dto = service.save(dto);
        return ResponseEntity.created(new URI("/api/alvallalkozoi-elszamolasoks/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlvallalkozoiElszamolasokDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlvallalkozoiElszamolasokDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update AlvallalkozoiElszamolasok : {}, {}", id, dto);
        if (dto.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        if (!Objects.equals(id, dto.getId())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        if (!repository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        dto = service.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlvallalkozoiElszamolasokDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlvallalkozoiElszamolasokDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AlvallalkozoiElszamolasok : {}, {}", id, dto);
        if (dto.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        if (!Objects.equals(id, dto.getId())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        if (!repository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        Optional<AlvallalkozoiElszamolasokDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<AlvallalkozoiElszamolasokDTO>> getAll(
        AlvallalkozoiElszamolasokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get AlvallalkozoiElszamolasoks by criteria: {}", criteria);
        Page<AlvallalkozoiElszamolasokDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(AlvallalkozoiElszamolasokCriteria criteria) {
        LOG.debug("REST request to count AlvallalkozoiElszamolasoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(queryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlvallalkozoiElszamolasokDTO> getOne(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AlvallalkozoiElszamolasok : {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AlvallalkozoiElszamolasok : {}", id);
        service.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

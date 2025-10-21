package com.fintech.erp.web.rest;

import com.fintech.erp.service.SzerzodesDokumentumTipusService;
import com.fintech.erp.service.dto.SzerzodesDokumentumTipusDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.SzerzodesDokumentumTipus}.
 */
@RestController
@RequestMapping("/api/szerzodes-dokumentum-tipusoks")
public class SzerzodesDokumentumTipusResource {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesDokumentumTipusResource.class);

    private static final String ENTITY_NAME = "szerzodesDokumentumTipus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SzerzodesDokumentumTipusService szerzodesDokumentumTipusService;

    public SzerzodesDokumentumTipusResource(SzerzodesDokumentumTipusService szerzodesDokumentumTipusService) {
        this.szerzodesDokumentumTipusService = szerzodesDokumentumTipusService;
    }

    @PostMapping("")
    public ResponseEntity<SzerzodesDokumentumTipusDTO> createSzerzodesDokumentumTipus(
        @Valid @RequestBody SzerzodesDokumentumTipusDTO szerzodesDokumentumTipusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save SzerzodesDokumentumTipus : {}", szerzodesDokumentumTipusDTO);
        if (szerzodesDokumentumTipusDTO.getId() != null) {
            throw new BadRequestAlertException("A new szerzodesDokumentumTipus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            szerzodesDokumentumTipusDTO = szerzodesDokumentumTipusService.save(szerzodesDokumentumTipusDTO);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "validationerror");
        }
        return ResponseEntity.created(new URI("/api/szerzodes-dokumentum-tipusoks/" + szerzodesDokumentumTipusDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, szerzodesDokumentumTipusDTO.getId().toString())
            )
            .body(szerzodesDokumentumTipusDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SzerzodesDokumentumTipusDTO> updateSzerzodesDokumentumTipus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SzerzodesDokumentumTipusDTO szerzodesDokumentumTipusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SzerzodesDokumentumTipus : {}, {}", id, szerzodesDokumentumTipusDTO);
        if (szerzodesDokumentumTipusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, szerzodesDokumentumTipusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        try {
            szerzodesDokumentumTipusDTO = szerzodesDokumentumTipusService.update(szerzodesDokumentumTipusDTO);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "validationerror");
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, szerzodesDokumentumTipusDTO.getId().toString()))
            .body(szerzodesDokumentumTipusDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SzerzodesDokumentumTipusDTO> partialUpdateSzerzodesDokumentumTipus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SzerzodesDokumentumTipusDTO szerzodesDokumentumTipusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SzerzodesDokumentumTipus partially : {}, {}", id, szerzodesDokumentumTipusDTO);
        if (szerzodesDokumentumTipusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, szerzodesDokumentumTipusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<SzerzodesDokumentumTipusDTO> result;
        try {
            result = szerzodesDokumentumTipusService.partialUpdate(szerzodesDokumentumTipusDTO);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "validationerror");
        }

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, szerzodesDokumentumTipusDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SzerzodesDokumentumTipusDTO>> getAllSzerzodesDokumentumTipusok(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SzerzodesDokumentumTipusok");
        Page<SzerzodesDokumentumTipusDTO> page = szerzodesDokumentumTipusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SzerzodesDokumentumTipusDTO> getSzerzodesDokumentumTipus(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SzerzodesDokumentumTipus : {}", id);
        Optional<SzerzodesDokumentumTipusDTO> szerzodesDokumentumTipusDTO = szerzodesDokumentumTipusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(szerzodesDokumentumTipusDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSzerzodesDokumentumTipus(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SzerzodesDokumentumTipus : {}", id);
        szerzodesDokumentumTipusService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

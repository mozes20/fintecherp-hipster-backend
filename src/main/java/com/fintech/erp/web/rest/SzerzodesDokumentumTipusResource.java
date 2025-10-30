package com.fintech.erp.web.rest;

import com.fintech.erp.service.dto.SzerzodesDokumentumTipusDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

/**
 * @deprecated Legacy REST resource retained temporarily; all operations now return HTTP 410 (Gone).
 */
@Deprecated
@RestController
@RequestMapping("/api/szerzodes-dokumentum-tipusoks")
public class SzerzodesDokumentumTipusResource {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesDokumentumTipusResource.class);

    private static ResponseStatusException gone() {
        return new ResponseStatusException(HttpStatus.GONE, "A szerzodes dokumentum tipus endpoint megszunt.");
    }

    @PostMapping("")
    public ResponseEntity<SzerzodesDokumentumTipusDTO> create(@Valid @RequestBody SzerzodesDokumentumTipusDTO body) {
        LOG.warn("Legacy dokumentum tipus create endpoint called after deprecation.");
        throw gone();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SzerzodesDokumentumTipusDTO> update(
        @PathVariable("id") Long id,
        @Valid @RequestBody SzerzodesDokumentumTipusDTO body
    ) {
        LOG.warn("Legacy dokumentum tipus update endpoint called after deprecation: {}", id);
        throw gone();
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SzerzodesDokumentumTipusDTO> partialUpdate(
        @PathVariable("id") Long id,
        @NotNull @RequestBody SzerzodesDokumentumTipusDTO body
    ) {
        LOG.warn("Legacy dokumentum tipus partial-update endpoint called after deprecation: {}", id);
        throw gone();
    }

    @GetMapping("")
    public ResponseEntity<List<SzerzodesDokumentumTipusDTO>> list() {
        LOG.warn("Legacy dokumentum tipus list endpoint called after deprecation.");
        throw gone();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SzerzodesDokumentumTipusDTO> get(@PathVariable("id") Long id) {
        LOG.warn("Legacy dokumentum tipus get endpoint called after deprecation: {}", id);
        throw gone();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.warn("Legacy dokumentum tipus delete endpoint called after deprecation: {}", id);
        throw gone();
    }
}

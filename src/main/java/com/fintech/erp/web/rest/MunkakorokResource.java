package com.fintech.erp.web.rest;

import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.service.MunkakorokService;
import com.fintech.erp.service.dto.MunkakorokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.Munkakorok}.
 */
@RestController
@RequestMapping("/api/munkakoroks")
public class MunkakorokResource {

    private static final Logger LOG = LoggerFactory.getLogger(MunkakorokResource.class);

    private static final String ENTITY_NAME = "munkakorok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MunkakorokService munkakorokService;

    private final MunkakorokRepository munkakorokRepository;

    public MunkakorokResource(MunkakorokService munkakorokService, MunkakorokRepository munkakorokRepository) {
        this.munkakorokService = munkakorokService;
        this.munkakorokRepository = munkakorokRepository;
    }

    /**
     * {@code PATCH  /munkakoroks/:id} : Partial updates given fields of an existing munkakorok, field will ignore if it is null
     *
     * @param id the id of the munkakorokDTO to save.
     * @param munkakorokDTO the munkakorokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated munkakorokDTO,
     * or with status {@code 400 (Bad Request)} if the munkakorokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the munkakorokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the munkakorokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MunkakorokDTO> partialUpdateMunkakorok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MunkakorokDTO munkakorokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Munkakorok partially : {}, {}", id, munkakorokDTO);
        if (munkakorokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, munkakorokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!munkakorokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MunkakorokDTO> result = munkakorokService.partialUpdate(munkakorokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, munkakorokDTO.getId().toString())
        );
    }

    /**
     * {@code POST  /munkakoroks} : Create a new munkakorok.
     *
     * @param munkakorokDTO the munkakorokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new munkakorokDTO, or with status {@code 400 (Bad Request)} if the munkakorok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<MunkakorokDTO> createMunkakorok(@RequestBody MunkakorokDTO munkakorokDTO) throws URISyntaxException {
        LOG.debug("REST request to save Munkakorok : {}", munkakorokDTO);
        if (munkakorokDTO.getId() != null) {
            throw new BadRequestAlertException("A new munkakorok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MunkakorokDTO result = munkakorokService.save(munkakorokDTO);
        return ResponseEntity.created(new URI("/api/munkakoroks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /munkakoroks/:id} : Updates an existing munkakorok.
     *
     * @param id the id of the munkakorokDTO to save.
     * @param munkakorokDTO the munkakorokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated munkakorokDTO,
     * or with status {@code 400 (Bad Request)} if the munkakorokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the munkakorokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MunkakorokDTO> updateMunkakorok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MunkakorokDTO munkakorokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Munkakorok : {}, {}", id, munkakorokDTO);
        if (munkakorokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, munkakorokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!munkakorokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MunkakorokDTO result = munkakorokService.update(munkakorokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, munkakorokDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /munkakoroks} : get all the munkakoroks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of munkakoroks in body.
     */
    @GetMapping
    public ResponseEntity<List<MunkakorokDTO>> getAllMunkakoroks(Pageable pageable) {
        LOG.debug("REST request to get a page of Munkakoroks");
        Page<MunkakorokDTO> page = munkakorokService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /munkakoroks/:id} : get the "id" munkakorok.
     *
     * @param id the id of the munkakorokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the munkakorokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MunkakorokDTO> getMunkakorok(@PathVariable Long id) {
        LOG.debug("REST request to get Munkakorok : {}", id);
        Optional<MunkakorokDTO> munkakorokDTO = munkakorokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(munkakorokDTO);
    }

    /**
     * {@code DELETE  /munkakoroks/:id} : delete the "id" munkakorok.
     *
     * @param id the id of the munkakorokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMunkakorok(@PathVariable Long id) {
        LOG.debug("REST request to delete Munkakorok : {}", id);
        munkakorokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

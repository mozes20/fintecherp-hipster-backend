package com.fintech.erp.web.rest;

import com.fintech.erp.repository.BankszamlaszamokRepository;
import com.fintech.erp.service.BankszamlaszamokQueryService;
import com.fintech.erp.service.BankszamlaszamokService;
import com.fintech.erp.service.criteria.BankszamlaszamokCriteria;
import com.fintech.erp.service.dto.BankszamlaszamokDTO;
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
 * REST controller for managing {@link com.fintech.erp.domain.Bankszamlaszamok}.
 */
@RestController
@RequestMapping("/api/bankszamlaszamoks")
public class BankszamlaszamokResource {

    private static final Logger LOG = LoggerFactory.getLogger(BankszamlaszamokResource.class);

    private static final String ENTITY_NAME = "bankszamlaszamok";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankszamlaszamokService bankszamlaszamokService;

    private final BankszamlaszamokRepository bankszamlaszamokRepository;

    private final BankszamlaszamokQueryService bankszamlaszamokQueryService;

    public BankszamlaszamokResource(
        BankszamlaszamokService bankszamlaszamokService,
        BankszamlaszamokRepository bankszamlaszamokRepository,
        BankszamlaszamokQueryService bankszamlaszamokQueryService
    ) {
        this.bankszamlaszamokService = bankszamlaszamokService;
        this.bankszamlaszamokRepository = bankszamlaszamokRepository;
        this.bankszamlaszamokQueryService = bankszamlaszamokQueryService;
    }

    /**
     * {@code POST  /bankszamlaszamoks/by-ceg/{cegId}} : Create a new bankszamlaszamok for a given company (cegId).
     *
     * @param cegId the company id to associate.
     * @param bankszamlaszamokDTO the bankszamlaszamokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankszamlaszamokDTO, or with status {@code 400 (Bad Request)} if the bankszamlaszamok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/by-ceg/{cegId}")
    public ResponseEntity<BankszamlaszamokDTO> createBankszamlaszamokForCeg(
        @PathVariable("cegId") Long cegId,
        @RequestBody BankszamlaszamokDTO bankszamlaszamokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save Bankszamlaszamok for cegId {} : {}", cegId, bankszamlaszamokDTO);
        if (bankszamlaszamokDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankszamlaszamok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (bankszamlaszamokDTO.getCeg() == null) {
            bankszamlaszamokDTO.setCeg(new com.fintech.erp.service.dto.CegAlapadatokDTO());
        }
        bankszamlaszamokDTO.getCeg().setId(cegId);
        bankszamlaszamokDTO = bankszamlaszamokService.save(bankszamlaszamokDTO);
        return ResponseEntity.created(new URI("/api/bankszamlaszamoks/" + bankszamlaszamokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bankszamlaszamokDTO.getId().toString()))
            .body(bankszamlaszamokDTO);
    }

    /**
     * {@code PUT  /bankszamlaszamoks/by-ceg/{cegId}/:id} : Updates an existing bankszamlaszamok for a given company (cegId).
     *
     * @param cegId the company id to associate.
     * @param id the id of the bankszamlaszamokDTO to save.
     * @param bankszamlaszamokDTO the bankszamlaszamokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankszamlaszamokDTO,
     * or with status {@code 400 (Bad Request)} if the bankszamlaszamokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankszamlaszamokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/by-ceg/{cegId}/{id}")
    public ResponseEntity<BankszamlaszamokDTO> updateBankszamlaszamokForCeg(
        @PathVariable("cegId") Long cegId,
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankszamlaszamokDTO bankszamlaszamokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Bankszamlaszamok for cegId {} : {}, {}", cegId, id, bankszamlaszamokDTO);
        if (bankszamlaszamokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankszamlaszamokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!bankszamlaszamokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (bankszamlaszamokDTO.getCeg() == null) {
            bankszamlaszamokDTO.setCeg(new com.fintech.erp.service.dto.CegAlapadatokDTO());
        }
        bankszamlaszamokDTO.getCeg().setId(cegId);
        bankszamlaszamokDTO = bankszamlaszamokService.update(bankszamlaszamokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankszamlaszamokDTO.getId().toString()))
            .body(bankszamlaszamokDTO);
    }

    /**
     * {@code POST  /bankszamlaszamoks} : Create a new bankszamlaszamok.
     *
     * @param bankszamlaszamokDTO the bankszamlaszamokDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankszamlaszamokDTO, or with status {@code 400 (Bad Request)} if the bankszamlaszamok has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BankszamlaszamokDTO> createBankszamlaszamok(@RequestBody BankszamlaszamokDTO bankszamlaszamokDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Bankszamlaszamok : {}", bankszamlaszamokDTO);
        if (bankszamlaszamokDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankszamlaszamok cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bankszamlaszamokDTO = bankszamlaszamokService.save(bankszamlaszamokDTO);
        return ResponseEntity.created(new URI("/api/bankszamlaszamoks/" + bankszamlaszamokDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bankszamlaszamokDTO.getId().toString()))
            .body(bankszamlaszamokDTO);
    }

    /**
     * {@code PUT  /bankszamlaszamoks/:id} : Updates an existing bankszamlaszamok.
     *
     * @param id the id of the bankszamlaszamokDTO to save.
     * @param bankszamlaszamokDTO the bankszamlaszamokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankszamlaszamokDTO,
     * or with status {@code 400 (Bad Request)} if the bankszamlaszamokDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankszamlaszamokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankszamlaszamokDTO> updateBankszamlaszamok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankszamlaszamokDTO bankszamlaszamokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Bankszamlaszamok : {}, {}", id, bankszamlaszamokDTO);
        if (bankszamlaszamokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankszamlaszamokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankszamlaszamokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankszamlaszamokDTO = bankszamlaszamokService.update(bankszamlaszamokDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankszamlaszamokDTO.getId().toString()))
            .body(bankszamlaszamokDTO);
    }

    /**
     * {@code PATCH  /bankszamlaszamoks/:id} : Partial updates given fields of an existing bankszamlaszamok, field will ignore if it is null
     *
     * @param id the id of the bankszamlaszamokDTO to save.
     * @param bankszamlaszamokDTO the bankszamlaszamokDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankszamlaszamokDTO,
     * or with status {@code 400 (Bad Request)} if the bankszamlaszamokDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankszamlaszamokDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankszamlaszamokDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankszamlaszamokDTO> partialUpdateBankszamlaszamok(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankszamlaszamokDTO bankszamlaszamokDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Bankszamlaszamok partially : {}, {}", id, bankszamlaszamokDTO);
        if (bankszamlaszamokDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankszamlaszamokDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankszamlaszamokRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankszamlaszamokDTO> result = bankszamlaszamokService.partialUpdate(bankszamlaszamokDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankszamlaszamokDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bankszamlaszamoks} : get all the bankszamlaszamoks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankszamlaszamoks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BankszamlaszamokDTO>> getAllBankszamlaszamoks(
        BankszamlaszamokCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Bankszamlaszamoks by criteria: {}", criteria);

        Page<BankszamlaszamokDTO> page = bankszamlaszamokQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bankszamlaszamoks/count} : count all the bankszamlaszamoks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBankszamlaszamoks(BankszamlaszamokCriteria criteria) {
        LOG.debug("REST request to count Bankszamlaszamoks by criteria: {}", criteria);
        return ResponseEntity.ok().body(bankszamlaszamokQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bankszamlaszamoks/:id} : get the "id" bankszamlaszamok.
     *
     * @param id the id of the bankszamlaszamokDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankszamlaszamokDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankszamlaszamokDTO> getBankszamlaszamok(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Bankszamlaszamok : {}", id);
        Optional<BankszamlaszamokDTO> bankszamlaszamokDTO = bankszamlaszamokService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankszamlaszamokDTO);
    }

    /**
     * {@code DELETE  /bankszamlaszamoks/:id} : delete the "id" bankszamlaszamok.
     *
     * @param id the id of the bankszamlaszamokDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankszamlaszamok(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Bankszamlaszamok : {}", id);
        bankszamlaszamokService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

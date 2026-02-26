package com.fintech.erp.web.rest;

import com.fintech.erp.repository.TimesheetekRepository;
import com.fintech.erp.security.AuthoritiesConstants;
import com.fintech.erp.security.SecurityUtils;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new timesheetekDTO, or with status {@code 400 (Bad Request)}
     *         if the timesheetek has already an ID.
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
     * @param id             the id of the timesheetekDTO to save.
     * @param timesheetekDTO the timesheetekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated timesheetekDTO,
     *         or with status {@code 400 (Bad Request)} if the timesheetekDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         timesheetekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetekDTO> updateTimesheetek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TimesheetekDTO timesheetekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Timesheetek : {}, {}", id, timesheetekDTO);

        // Ha a body-ban nincs ID, automatikusan beállítjuk az URL-ből
        if (timesheetekDTO.getId() == null) {
            timesheetekDTO.setId(id);
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
     * {@code PATCH  /timesheeteks/:id} : Partial updates given fields of an
     * existing timesheetek, field will ignore if it is null
     *
     * @param id             the id of the timesheetekDTO to save.
     * @param timesheetekDTO the timesheetekDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated timesheetekDTO,
     *         or with status {@code 400 (Bad Request)} if the timesheetekDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the timesheetekDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         timesheetekDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TimesheetekDTO> partialUpdateTimesheetek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TimesheetekDTO timesheetekDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Timesheetek partially : {}, {}", id, timesheetekDTO);

        // Ha a body-ban nincs ID, automatikusan beállítjuk az URL-ből
        if (timesheetekDTO.getId() == null) {
            timesheetekDTO.setId(id);
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
     * Ha nem ADMIN, csak a saját timesheet-eket adja vissza.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of timesheeteks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TimesheetekDTO>> getAllTimesheeteks(
        TimesheetekCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Timesheeteks by criteria: {}", criteria);
        LOG.info("📋 getAllTimesheeteks() meghívva - criteria: {}", criteria);

        // Ellenőrizzük a felhasználó jogosultságait
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElse("anonymous");

        // DEBUG: Vizsgáljuk meg az authorities-t
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("🔍 Authentication objektum: {}", authentication != null ? authentication.getClass().getSimpleName() : "null");
        if (authentication != null) {
            LOG.info("🔍 Authorities: {}", authentication.getAuthorities());
        }

        boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN);

        LOG.info("🔐 Felhasználó: {}, Admin: {}, AuthoritiesConstants.ADMIN={}", currentUserLogin, isAdmin, AuthoritiesConstants.ADMIN);

        // Ha nem admin, csak a saját timesheet-eket láthatja
        if (!isAdmin) {
            if (currentUserLogin.equals("anonymous")) {
                throw new BadRequestAlertException("Nem sikerült azonosítani a felhasználót", ENTITY_NAME, "usernotfound");
            }

            LOG.info("🔐 Nem-admin felhasználó: {}, szűrés saját bejegyzésekre", currentUserLogin);

            // Szűrés a userLogin alapján
            if (criteria.getUserLogin() == null) {
                criteria.setUserLogin(new tech.jhipster.service.filter.StringFilter());
            }
            criteria.getUserLogin().setEquals(currentUserLogin);
            LOG.info("✅ userLogin filter beállítva: {}", currentUserLogin);
        } else {
            LOG.info("👑 ADMIN felhasználó ({}) - MINDEN bejegyzés látható", currentUserLogin);
            LOG.info(
                "📝 Frontend által küldött criteria (userLogin filter): {}",
                criteria.getUserLogin() != null ? criteria.getUserLogin().getEquals() : "nincs"
            );

            // KRITIKUS: Admin esetén TÖRÖLJÜK a userLogin filtert, ha be van állítva
            if (criteria.getUserLogin() != null) {
                LOG.info("⚠️ Admin részére TÖRÖLJÜK a userLogin filtert!");
                criteria.setUserLogin(null);
            }
        }

        Page<TimesheetekDTO> page = timesheetekQueryService.findByCriteria(criteria, pageable);
        LOG.info("📦 getAllTimesheeteks() válasz - elemek száma: {}, teljes: {}", page.getContent().size(), page.getTotalElements());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /timesheeteks/count} : count all the timesheeteks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTimesheeteks(TimesheetekCriteria criteria) {
        LOG.debug("REST request to count Timesheeteks by criteria: {}", criteria);
        return ResponseEntity.ok().body(timesheetekQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /timesheeteks/by-month/:yearMonth} : get timesheeteks for a
     * specific month.
     * Hónap szerinti szűrés kényelmi endpoint.
     *
     * @param yearMonth the year-month in format YYYY-MM (e.g., "2026-01")
     * @param pageable  the pagination information
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of timesheeteks in body
     */
    @GetMapping("/by-month/{yearMonth}")
    public ResponseEntity<List<TimesheetekDTO>> getTimesheeteksByMonth(
        @PathVariable String yearMonth,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.info("📅 REST request to get Timesheeteks for month: {}", yearMonth);

        // Parse yearMonth (format: YYYY-MM)
        java.time.YearMonth ym;
        try {
            ym = java.time.YearMonth.parse(yearMonth);
        } catch (Exception e) {
            throw new BadRequestAlertException("Invalid yearMonth format. Expected YYYY-MM", ENTITY_NAME, "invalidformat");
        }

        java.time.LocalDate startDate = ym.atDay(1);
        java.time.LocalDate endDate = ym.atEndOfMonth();

        LOG.info("📅 Szűrés időszak: {} - {}", startDate, endDate);

        // Jogosultság ellenőrzés
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("Nem sikerült azonosítani a felhasználót", ENTITY_NAME, "usernotfound"));
        boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN);

        // Criteria beállítása
        TimesheetekCriteria criteria = new TimesheetekCriteria();

        // Dátum szűrő beállítása a hónapra
        tech.jhipster.service.filter.LocalDateFilter dateFilter = new tech.jhipster.service.filter.LocalDateFilter();
        dateFilter.setGreaterThanOrEqual(startDate);
        dateFilter.setLessThanOrEqual(endDate);
        criteria.setDatum(dateFilter);

        // User szűrő (ha nem admin)
        if (!isAdmin) {
            tech.jhipster.service.filter.StringFilter userFilter = new tech.jhipster.service.filter.StringFilter();
            userFilter.setEquals(currentUserLogin);
            criteria.setUserLogin(userFilter);
            LOG.info("🔒 Nem-admin felhasználó - szűrés saját bejegyzésekre: {}", currentUserLogin);
        } else {
            LOG.info("👑 ADMIN felhasználó - minden bejegyzés látható");
        }

        Page<TimesheetekDTO> page = timesheetekQueryService.findByCriteria(criteria, pageable);

        LOG.info("📦 Visszaadott elemek: {} db ({}. hónap)", page.getContent().size(), yearMonth);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /timesheeteks/:id} : get the "id" timesheetek.
     *
     * @param id the id of the timesheetekDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the timesheetekDTO, or with status {@code 404 (Not Found)}.
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

        // Csak a saját timesheet-et törölheti (admin kivétel)
        if (!SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            Optional<TimesheetekDTO> existing = timesheetekService.findOne(id);
            if (existing.isPresent()) {
                String currentUserLogin = SecurityUtils.getCurrentUserLogin()
                    .orElseThrow(() -> new BadRequestAlertException("Nem sikerült azonosítani a felhasználót", ENTITY_NAME, "usernotfound")
                    );

                if (!existing.orElseThrow().getUserLogin().equals(currentUserLogin)) {
                    throw new BadRequestAlertException("Nem törölheted más felhasználó timesheet-jét", ENTITY_NAME, "forbidden");
                }
            }
        }

        timesheetekService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /timesheeteks/my-timesheets} : get the current user's timesheets.
     * Admin esetén az összes felhasználó timesheet-jeit adja vissza.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of timesheeteks in body.
     */
    @GetMapping("/my-timesheets")
    public ResponseEntity<List<TimesheetekDTO>> getMyTimesheets(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get my Timesheeteks");

        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("Nem sikerült azonosítani a felhasználót", ENTITY_NAME, "usernotfound"));

        boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN);

        LOG.info("👤 Saját időbejegyzések lekérdezése - Felhasználó: {}, Admin: {}", currentUserLogin, isAdmin);

        TimesheetekCriteria criteria = new TimesheetekCriteria();

        // Ha NEM admin, szűrés csak a saját bejegyzésekre
        if (!isAdmin) {
            criteria.setUserLogin(new tech.jhipster.service.filter.StringFilter());
            criteria.getUserLogin().setEquals(currentUserLogin);
            LOG.info("🔒 Nem-admin felhasználó - szűrés saját bejegyzésekre: {}", currentUserLogin);
        } else {
            LOG.info("👑 ADMIN felhasználó - MINDEN felhasználó bejegyzése látható");
        }

        Page<TimesheetekDTO> page = timesheetekQueryService.findByCriteria(criteria, pageable);

        LOG.info("📦 Visszaadott elemek száma: {}, Teljes találat: {}", page.getContent().size(), page.getTotalElements());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /timesheeteks/submit/:yearMonth} : Submit all timesheets for
     * approval for a given month.
     * Beküldés jóváhagyásra - az adott hónap összes DRAFT státuszú timesheet-jét
     * SUBMITTED-re állítja.
     *
     * @param yearMonth the year-month (format: YYYY-MM)
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     *         number of updated timesheets.
     */
    @PostMapping("/submit/{yearMonth}")
    public ResponseEntity<Integer> submitTimesheetsForMonth(@PathVariable String yearMonth) {
        LOG.info("📤 REST request to submit Timesheeteks for month: {}", yearMonth);

        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("Nem sikerült azonosítani a felhasználót", ENTITY_NAME, "usernotfound"));

        int updatedCount = timesheetekService.submitTimesheetsForMonth(currentUserLogin, yearMonth);

        LOG.info("✅ Beküldve jóváhagyásra: {} darab timesheet (felhasználó: {}, hónap: {})", updatedCount, currentUserLogin, yearMonth);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert(applicationName, "timesheetekApp.timesheetek.submitted", String.valueOf(updatedCount)))
            .body(updatedCount);
    }

    /**
     * {@code POST  /timesheeteks/approve/:yearMonth/:userLogin} : Approve all
     * timesheets for a user and month.
     * Admin jóváhagyja egy felhasználó adott havi timesheet-jeit (SUBMITTED ->
     * APPROVED).
     *
     * @param yearMonth the year-month (format: YYYY-MM)
     * @param userLogin the user's login
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     *         number of approved timesheets.
     */
    @PostMapping("/approve/{yearMonth}/{userLogin}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Integer> approveTimesheetsForUserAndMonth(@PathVariable String yearMonth, @PathVariable String userLogin) {
        LOG.info("✅ REST request to approve Timesheeteks for user: {} and month: {}", userLogin, yearMonth);

        int approvedCount = timesheetekService.approveTimesheetsForUserAndMonth(userLogin, yearMonth);

        LOG.info("✅ Jóváhagyva: {} darab timesheet (felhasználó: {}, hónap: {})", approvedCount, userLogin, yearMonth);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert(applicationName, "timesheetekApp.timesheetek.approved", String.valueOf(approvedCount)))
            .body(approvedCount);
    }

    /**
     * {@code POST  /timesheeteks/reject/:yearMonth/:userLogin} : Reject all
     * timesheets for a user and month.
     * Admin visszautasítja egy felhasználó adott havi timesheet-jeit (SUBMITTED ->
     * REJECTED).
     *
     * @param yearMonth the year-month (format: YYYY-MM)
     * @param userLogin the user's login
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     *         number of rejected timesheets.
     */
    @PostMapping("/reject/{yearMonth}/{userLogin}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Integer> rejectTimesheetsForUserAndMonth(@PathVariable String yearMonth, @PathVariable String userLogin) {
        LOG.info("❌ REST request to reject Timesheeteks for user: {} and month: {}", userLogin, yearMonth);

        int rejectedCount = timesheetekService.rejectTimesheetsForUserAndMonth(userLogin, yearMonth);

        LOG.info("❌ Visszautasítva: {} darab timesheet (felhasználó: {}, hónap: {})", rejectedCount, userLogin, yearMonth);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert(applicationName, "timesheetekApp.timesheetek.rejected", String.valueOf(rejectedCount)))
            .body(rejectedCount);
    }

    /**
     * {@code GET  /timesheeteks/pending-approval} : Get all timesheets pending
     * approval (SUBMITTED status).
     * Admin végpont - az összes jóváhagyásra váró timesheet lekérdezése.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of timesheeteks.
     */
    @GetMapping("/pending-approval")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<TimesheetekDTO>> getPendingApprovalTimesheets(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.info("📋 REST request to get pending approval Timesheeteks");

        TimesheetekCriteria criteria = new TimesheetekCriteria();
        criteria.setStatusz(new tech.jhipster.service.filter.StringFilter());
        criteria.getStatusz().setEquals("SUBMITTED");

        Page<TimesheetekDTO> page = timesheetekQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

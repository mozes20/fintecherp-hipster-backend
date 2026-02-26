package com.fintech.erp.service;

import com.fintech.erp.domain.Timesheetek;
import com.fintech.erp.repository.TimesheetekRepository;
import com.fintech.erp.security.SecurityUtils;
import com.fintech.erp.service.dto.TimesheetekDTO;
import com.fintech.erp.service.mapper.TimesheetekMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Timesheetek}.
 */
@Service
@Transactional
public class TimesheetekService {

    private static final Logger LOG = LoggerFactory.getLogger(TimesheetekService.class);

    private final TimesheetekRepository timesheetekRepository;

    private final TimesheetekMapper timesheetekMapper;

    public TimesheetekService(TimesheetekRepository timesheetekRepository, TimesheetekMapper timesheetekMapper) {
        this.timesheetekRepository = timesheetekRepository;
        this.timesheetekMapper = timesheetekMapper;
    }

    /**
     * Save a timesheetek.
     *
     * @param timesheetekDTO the entity to save.
     * @return the persisted entity.
     */
    public TimesheetekDTO save(TimesheetekDTO timesheetekDTO) {
        LOG.debug("Request to save Timesheetek : {}", timesheetekDTO);

        // Beállítjuk az aktuális felhasználó login-ját
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new RuntimeException("Nem sikerült azonosítani a felhasználót"));
        LOG.info("🔐 Bejelentkezett felhasználó: {}", currentUserLogin);

        timesheetekDTO.setUserLogin(currentUserLogin);
        LOG.info(
            "📝 Mentésre kerülő adatok: datum={}, userLogin={}, oraMennyiseg={}, statusz={}, megjegyzes={}",
            timesheetekDTO.getDatum(),
            timesheetekDTO.getUserLogin(),
            timesheetekDTO.getOraMennyiseg(),
            timesheetekDTO.getStatusz(),
            timesheetekDTO.getMegjegyzes()
        );

        Timesheetek timesheetek = timesheetekMapper.toEntity(timesheetekDTO);
        timesheetek = timesheetekRepository.save(timesheetek);

        TimesheetekDTO savedDTO = timesheetekMapper.toDto(timesheetek);
        LOG.info(
            "✅ Elmentett adatok: id={}, datum={}, userLogin={}, oraMennyiseg={}",
            savedDTO.getId(),
            savedDTO.getDatum(),
            savedDTO.getUserLogin(),
            savedDTO.getOraMennyiseg()
        );

        return savedDTO;
    }

    /**
     * Update a timesheetek.
     *
     * @param timesheetekDTO the entity to save.
     * @return the persisted entity.
     */
    public TimesheetekDTO update(TimesheetekDTO timesheetekDTO) {
        LOG.debug("Request to update Timesheetek : {}", timesheetekDTO);

        // Megőrizzük az eredeti userLogin értéket az adatbázisból
        Timesheetek existing = timesheetekRepository
            .findById(timesheetekDTO.getId())
            .orElseThrow(() -> new RuntimeException("Timesheet not found with id: " + timesheetekDTO.getId()));

        Timesheetek timesheetek = timesheetekMapper.toEntity(timesheetekDTO);

        // Ha a DTO-ban nincs userLogin, visszaállítjuk az eredetit
        if (timesheetek.getUserLogin() == null) {
            timesheetek.setUserLogin(existing.getUserLogin());
        }

        timesheetek = timesheetekRepository.save(timesheetek);
        return timesheetekMapper.toDto(timesheetek);
    }

    /**
     * Partially update a timesheetek.
     *
     * @param timesheetekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TimesheetekDTO> partialUpdate(TimesheetekDTO timesheetekDTO) {
        LOG.debug("Request to partially update Timesheetek : {}", timesheetekDTO);

        return timesheetekRepository
            .findById(timesheetekDTO.getId())
            .map(existingTimesheetek -> {
                timesheetekMapper.partialUpdate(existingTimesheetek, timesheetekDTO);

                return existingTimesheetek;
            })
            .map(timesheetekRepository::save)
            .map(timesheetekMapper::toDto);
    }

    /**
     * Get one timesheetek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetekDTO> findOne(Long id) {
        LOG.debug("Request to get Timesheetek : {}", id);
        return timesheetekRepository.findById(id).map(timesheetekMapper::toDto);
    }

    /**
     * Delete the timesheetek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Timesheetek : {}", id);
        timesheetekRepository.deleteById(id);
    }

    /**
     * Submit all DRAFT timesheets for a user and month for approval (DRAFT -> SUBMITTED).
     *
     * @param userLogin the user's login
     * @param yearMonth the year-month (format: YYYY-MM)
     * @return the number of updated timesheets
     */
    @Transactional
    public int submitTimesheetsForMonth(String userLogin, String yearMonth) {
        LOG.info("📤 Request to submit timesheets for user: {} and month: {}", userLogin, yearMonth);

        // Parse yearMonth to get start and end dates
        String startDate = yearMonth + "-01";
        String endDate = yearMonth + "-31"; // Simplified, good enough for filtering

        List<Timesheetek> timesheets = timesheetekRepository.findByUserLoginAndDatumBetween(
            userLogin,
            java.time.LocalDate.parse(startDate),
            java.time.LocalDate.parse(endDate)
        );

        int updatedCount = 0;
        for (Timesheetek timesheet : timesheets) {
            if ("DRAFT".equals(timesheet.getStatusz())) {
                timesheet.setStatusz("SUBMITTED");
                timesheetekRepository.save(timesheet);
                updatedCount++;
            }
        }

        LOG.info("✅ Submitted {} timesheets for user: {} and month: {}", updatedCount, userLogin, yearMonth);
        return updatedCount;
    }

    /**
     * Approve all SUBMITTED timesheets for a user and month (SUBMITTED -> APPROVED).
     * Admin funkció.
     *
     * @param userLogin the user's login
     * @param yearMonth the year-month (format: YYYY-MM)
     * @return the number of approved timesheets
     */
    @Transactional
    public int approveTimesheetsForUserAndMonth(String userLogin, String yearMonth) {
        LOG.info("✅ Request to approve timesheets for user: {} and month: {}", userLogin, yearMonth);

        String startDate = yearMonth + "-01";
        String endDate = yearMonth + "-31";

        List<Timesheetek> timesheets = timesheetekRepository.findByUserLoginAndDatumBetween(
            userLogin,
            java.time.LocalDate.parse(startDate),
            java.time.LocalDate.parse(endDate)
        );

        int approvedCount = 0;
        for (Timesheetek timesheet : timesheets) {
            if ("SUBMITTED".equals(timesheet.getStatusz())) {
                timesheet.setStatusz("APPROVED");
                timesheetekRepository.save(timesheet);
                approvedCount++;
            }
        }

        LOG.info("✅ Approved {} timesheets for user: {} and month: {}", approvedCount, userLogin, yearMonth);
        return approvedCount;
    }

    /**
     * Reject all SUBMITTED timesheets for a user and month (SUBMITTED -> REJECTED).
     * Admin funkció - visszaküldés szerkesztésre.
     *
     * @param userLogin the user's login
     * @param yearMonth the year-month (format: YYYY-MM)
     * @return the number of rejected timesheets
     */
    @Transactional
    public int rejectTimesheetsForUserAndMonth(String userLogin, String yearMonth) {
        LOG.info("❌ Request to reject timesheets for user: {} and month: {}", userLogin, yearMonth);

        String startDate = yearMonth + "-01";
        String endDate = yearMonth + "-31";

        List<Timesheetek> timesheets = timesheetekRepository.findByUserLoginAndDatumBetween(
            userLogin,
            java.time.LocalDate.parse(startDate),
            java.time.LocalDate.parse(endDate)
        );

        int rejectedCount = 0;
        for (Timesheetek timesheet : timesheets) {
            if ("SUBMITTED".equals(timesheet.getStatusz())) {
                timesheet.setStatusz("REJECTED");
                timesheetekRepository.save(timesheet);
                rejectedCount++;
            }
        }

        LOG.info("❌ Rejected {} timesheets for user: {} and month: {}", rejectedCount, userLogin, yearMonth);
        return rejectedCount;
    }
}

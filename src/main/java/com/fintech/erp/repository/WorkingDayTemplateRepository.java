package com.fintech.erp.repository;

import com.fintech.erp.domain.WorkingDayTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkingDayTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkingDayTemplateRepository
        extends JpaRepository<WorkingDayTemplate, Long>, JpaSpecificationExecutor<WorkingDayTemplate> {

    /**
     * Megkeresi a munkasablon-t az adott év-hónap alapján.
     * 
     * @param yearMonth Az év-hónap (pl. "2026-01")
     * @return A munkasablon, ha létezik
     */
    Optional<WorkingDayTemplate> findByYearMonth(String yearMonth);

    /**
     * Ellenőrzi, hogy létezik-e már sablon az adott hónapra.
     * 
     * @param yearMonth Az év-hónap
     * @return true ha létezik, false ha nem
     */
    boolean existsByYearMonth(String yearMonth);
}

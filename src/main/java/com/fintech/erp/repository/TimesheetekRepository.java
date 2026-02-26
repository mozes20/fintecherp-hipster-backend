package com.fintech.erp.repository;

import com.fintech.erp.domain.Timesheetek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Timesheetek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetekRepository extends JpaRepository<Timesheetek, Long>, JpaSpecificationExecutor<Timesheetek> {
    /**
     * Megkeresi az adott felhasználó timesheet bejegyzéseit.
     * @param userLogin A felhasználó login neve
     * @return A felhasználó timesheet listája
     */
    List<Timesheetek> findByUserLogin(String userLogin);

    /**
     * Megkeresi az adott felhasználó timesheet bejegyzéseit egy adott hónapban.
     * @param userLogin A felhasználó login neve
     * @param startDate A hónap kezdő dátuma
     * @param endDate A hónap záró dátuma
     * @return A felhasználó timesheet listája az adott hónapra
     */
    List<Timesheetek> findByUserLoginAndDatumBetween(String userLogin, LocalDate startDate, LocalDate endDate);
}

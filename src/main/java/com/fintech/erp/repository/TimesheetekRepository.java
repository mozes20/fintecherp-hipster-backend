package com.fintech.erp.repository;

import com.fintech.erp.domain.Timesheetek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Timesheetek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetekRepository extends JpaRepository<Timesheetek, Long>, JpaSpecificationExecutor<Timesheetek> {}

package com.fintech.erp.repository;

import com.fintech.erp.domain.SajatCegKepviselok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SajatCegKepviselok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SajatCegKepviselokRepository
    extends JpaRepository<SajatCegKepviselok, Long>, JpaSpecificationExecutor<SajatCegKepviselok> {}

package com.fintech.erp.repository;

import com.fintech.erp.domain.SajatCegTulajdonosok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SajatCegTulajdonosok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SajatCegTulajdonosokRepository
    extends JpaRepository<SajatCegTulajdonosok, Long>, JpaSpecificationExecutor<SajatCegTulajdonosok> {}

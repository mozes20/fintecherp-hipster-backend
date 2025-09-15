package com.fintech.erp.repository;

import com.fintech.erp.domain.PartnerCegMunkavallalok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PartnerCegMunkavallalok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerCegMunkavallalokRepository
    extends JpaRepository<PartnerCegMunkavallalok, Long>, JpaSpecificationExecutor<PartnerCegMunkavallalok> {}

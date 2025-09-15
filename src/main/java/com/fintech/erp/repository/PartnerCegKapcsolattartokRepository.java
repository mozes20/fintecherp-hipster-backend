package com.fintech.erp.repository;

import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PartnerCegKapcsolattartok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerCegKapcsolattartokRepository
    extends JpaRepository<PartnerCegKapcsolattartok, Long>, JpaSpecificationExecutor<PartnerCegKapcsolattartok> {}

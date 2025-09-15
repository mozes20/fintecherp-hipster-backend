package com.fintech.erp.repository;

import com.fintech.erp.domain.PartnerCegAdatok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PartnerCegAdatok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerCegAdatokRepository extends JpaRepository<PartnerCegAdatok, Long>, JpaSpecificationExecutor<PartnerCegAdatok> {}

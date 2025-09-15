package com.fintech.erp.repository;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OsztalekfizetesiKozgyulesek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OsztalekfizetesiKozgyulesekRepository
    extends JpaRepository<OsztalekfizetesiKozgyulesek, Long>, JpaSpecificationExecutor<OsztalekfizetesiKozgyulesek> {}

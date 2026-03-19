package com.fintech.erp.repository;

import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlvallalkozoiElszamolasok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlvallalkozoiElszamolasokRepository
    extends JpaRepository<AlvallalkozoiElszamolasok, Long>, JpaSpecificationExecutor<AlvallalkozoiElszamolasok> {}

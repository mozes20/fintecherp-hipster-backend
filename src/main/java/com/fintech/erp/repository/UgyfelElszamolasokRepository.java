package com.fintech.erp.repository;

import com.fintech.erp.domain.UgyfelElszamolasok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UgyfelElszamolasok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UgyfelElszamolasokRepository
    extends JpaRepository<UgyfelElszamolasok, Long>, JpaSpecificationExecutor<UgyfelElszamolasok> {}

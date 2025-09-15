package com.fintech.erp.repository;

import com.fintech.erp.domain.SajatCegAlapadatok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SajatCegAlapadatok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SajatCegAlapadatokRepository
    extends JpaRepository<SajatCegAlapadatok, Long>, JpaSpecificationExecutor<SajatCegAlapadatok> {}

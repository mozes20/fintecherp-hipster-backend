package com.fintech.erp.repository;

import com.fintech.erp.domain.CegAlapadatok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CegAlapadatok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CegAlapadatokRepository extends JpaRepository<CegAlapadatok, Long>, JpaSpecificationExecutor<CegAlapadatok> {}

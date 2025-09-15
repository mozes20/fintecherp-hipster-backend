package com.fintech.erp.repository;

import com.fintech.erp.domain.Munkavallalok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Munkavallalok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunkavallalokRepository extends JpaRepository<Munkavallalok, Long>, JpaSpecificationExecutor<Munkavallalok> {}

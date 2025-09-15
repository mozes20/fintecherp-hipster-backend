package com.fintech.erp.repository;

import com.fintech.erp.domain.Megrendelesek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Megrendelesek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MegrendelesekRepository extends JpaRepository<Megrendelesek, Long>, JpaSpecificationExecutor<Megrendelesek> {}

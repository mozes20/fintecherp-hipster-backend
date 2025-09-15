package com.fintech.erp.repository;

import com.fintech.erp.domain.EfoFoglalkoztatasok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EfoFoglalkoztatasok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EfoFoglalkoztatasokRepository
    extends JpaRepository<EfoFoglalkoztatasok, Long>, JpaSpecificationExecutor<EfoFoglalkoztatasok> {}

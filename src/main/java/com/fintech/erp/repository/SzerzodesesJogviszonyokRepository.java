package com.fintech.erp.repository;

import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SzerzodesesJogviszonyok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SzerzodesesJogviszonyokRepository
    extends JpaRepository<SzerzodesesJogviszonyok, Long>, JpaSpecificationExecutor<SzerzodesesJogviszonyok> {}

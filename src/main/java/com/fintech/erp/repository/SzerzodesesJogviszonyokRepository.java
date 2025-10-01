package com.fintech.erp.repository;

import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SzerzodesesJogviszonyok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SzerzodesesJogviszonyokRepository
    extends JpaRepository<SzerzodesesJogviszonyok, Long>, JpaSpecificationExecutor<SzerzodesesJogviszonyok> {
    long countByMegrendeloCeg_CegRovidAzonositoAndVallalkozoCeg_CegRovidAzonosito(
        String megrendeloCegAzonosito,
        String vallalkozoCegAzonosito
    );
}

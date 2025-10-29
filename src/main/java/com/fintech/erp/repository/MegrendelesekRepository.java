package com.fintech.erp.repository;

import com.fintech.erp.domain.Megrendelesek;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Megrendelesek entity.
 */
@Repository
public interface MegrendelesekRepository extends JpaRepository<Megrendelesek, Long>, JpaSpecificationExecutor<Megrendelesek> {
    Optional<Megrendelesek> findFirstByMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(String prefix);

    Optional<Megrendelesek> findFirstBySzerzodesesJogviszony_IdAndMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(
        Long szerzodesesJogviszonyId,
        String prefix
    );
}

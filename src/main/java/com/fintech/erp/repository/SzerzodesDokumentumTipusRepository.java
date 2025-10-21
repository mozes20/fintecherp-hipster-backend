package com.fintech.erp.repository;

import com.fintech.erp.domain.SzerzodesDokumentumTipus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SzerzodesDokumentumTipus entity.
 */
@Repository
public interface SzerzodesDokumentumTipusRepository extends JpaRepository<SzerzodesDokumentumTipus, Long> {
    Optional<SzerzodesDokumentumTipus> findOneByNevIgnoreCase(String nev);
}

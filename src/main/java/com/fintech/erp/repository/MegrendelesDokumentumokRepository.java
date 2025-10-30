package com.fintech.erp.repository;

import com.fintech.erp.domain.MegrendelesDokumentumok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MegrendelesDokumentumok entity.
 */
@Repository
public interface MegrendelesDokumentumokRepository
    extends JpaRepository<MegrendelesDokumentumok, Long>, JpaSpecificationExecutor<MegrendelesDokumentumok> {
    java.util.List<MegrendelesDokumentumok> findAllByMegrendeles_Id(Long megrendelesId);

    java.util.Optional<MegrendelesDokumentumok> findFirstByMegrendeles_IdOrderByDokumentumAzonositoDesc(Long megrendelesId);
}

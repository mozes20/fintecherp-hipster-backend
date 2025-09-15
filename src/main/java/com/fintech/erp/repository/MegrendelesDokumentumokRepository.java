package com.fintech.erp.repository;

import com.fintech.erp.domain.MegrendelesDokumentumok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MegrendelesDokumentumok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MegrendelesDokumentumokRepository
    extends JpaRepository<MegrendelesDokumentumok, Long>, JpaSpecificationExecutor<MegrendelesDokumentumok> {}

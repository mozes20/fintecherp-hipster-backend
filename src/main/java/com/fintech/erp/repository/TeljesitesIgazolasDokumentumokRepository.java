package com.fintech.erp.repository;

import com.fintech.erp.domain.TeljesitesIgazolasDokumentumok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeljesitesIgazolasDokumentumok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeljesitesIgazolasDokumentumokRepository
    extends JpaRepository<TeljesitesIgazolasDokumentumok, Long>, JpaSpecificationExecutor<TeljesitesIgazolasDokumentumok> {}

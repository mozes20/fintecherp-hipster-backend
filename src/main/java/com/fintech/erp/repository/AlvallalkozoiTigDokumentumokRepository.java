package com.fintech.erp.repository;

import com.fintech.erp.domain.AlvallalkozoiTigDokumentumok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlvallalkozoiTigDokumentumok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlvallalkozoiTigDokumentumokRepository
    extends JpaRepository<AlvallalkozoiTigDokumentumok, Long>, JpaSpecificationExecutor<AlvallalkozoiTigDokumentumok> {}

package com.fintech.erp.repository;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentumTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OsztalekfizetesiKozgyulesekDokumentumTemplate entity.
 */
@Repository
public interface OsztalekfizetesiKozgyulesekDokumentumTemplateRepository
    extends
        JpaRepository<OsztalekfizetesiKozgyulesekDokumentumTemplate, Long>,
        JpaSpecificationExecutor<OsztalekfizetesiKozgyulesekDokumentumTemplate> {
    Optional<OsztalekfizetesiKozgyulesekDokumentumTemplate> findFirstByDokumentumTipusaOrderByUtolsoModositasDesc(String dokumentumTipusa);
}

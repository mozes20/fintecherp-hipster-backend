package com.fintech.erp.repository;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OsztalekfizetesiKozgyulesekDokumentum entity.
 */
@Repository
public interface OsztalekfizetesiKozgyulesekDokumentumRepository
    extends JpaRepository<OsztalekfizetesiKozgyulesekDokumentum, Long>, JpaSpecificationExecutor<OsztalekfizetesiKozgyulesekDokumentum> {
    List<OsztalekfizetesiKozgyulesekDokumentum> findAllByKozgyules_Id(Long kozgyulesId);

    List<OsztalekfizetesiKozgyulesekDokumentum> findAllByKozgyules_IdAndDokumentumTipusa(Long kozgyulesId, String dokumentumTipusa);

    Optional<OsztalekfizetesiKozgyulesekDokumentum> findFirstByKozgyules_IdOrderByDokumentumAzonositoDesc(Long kozgyulesId);
}

package com.fintech.erp.repository;

import com.fintech.erp.domain.EfoDokumentumTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link com.fintech.erp.domain.EfoDokumentumTemplate}.
 */
@Repository
public interface EfoDokumentumTemplateRepository extends JpaRepository<EfoDokumentumTemplate, Long> {
    Optional<EfoDokumentumTemplate> findFirstByDokumentumTipusOrderByUtolsoModositasDesc(String dokumentumTipus);
}

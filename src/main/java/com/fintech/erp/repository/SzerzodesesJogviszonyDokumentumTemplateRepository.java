package com.fintech.erp.repository;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SzerzodesesJogviszonyDokumentumTemplate entity.
 */
@Repository
public interface SzerzodesesJogviszonyDokumentumTemplateRepository extends JpaRepository<SzerzodesesJogviszonyDokumentumTemplate, Long> {
    Optional<SzerzodesesJogviszonyDokumentumTemplate> findFirstByDokumentumTipusOrderByUtolsoModositasDesc(String dokumentumTipus);
}

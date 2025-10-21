package com.fintech.erp.repository;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SzerzodesesJogviszonyDokumentum entity.
 */
@Repository
public interface SzerzodesesJogviszonyDokumentumRepository extends JpaRepository<SzerzodesesJogviszonyDokumentum, Long> {
    List<SzerzodesesJogviszonyDokumentum> findAllBySzerzodesesJogviszony_Id(Long szerzodesesJogviszonyId);
}

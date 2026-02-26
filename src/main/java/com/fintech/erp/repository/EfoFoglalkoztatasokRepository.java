package com.fintech.erp.repository;

import com.fintech.erp.domain.EfoFoglalkoztatasok;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EfoFoglalkoztatasok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EfoFoglalkoztatasokRepository
    extends JpaRepository<EfoFoglalkoztatasok, Long>, JpaSpecificationExecutor<EfoFoglalkoztatasok> {
    Optional<EfoFoglalkoztatasok> findFirstByMunkavallaloIdAndDatum(Long munkavallaloId, LocalDate datum);

    @EntityGraph(attributePaths = { "munkavallalo", "munkavallalo.maganszemely", "munkavallalo.sajatCeg", "munkakor" })
    Optional<EfoFoglalkoztatasok> findById(Long id);

    @Query(
        "SELECT e FROM EfoFoglalkoztatasok e " +
        "JOIN FETCH e.munkavallalo m " +
        "JOIN FETCH m.maganszemely " +
        "WHERE m.sajatCeg.id = :sajatCegId " +
        "AND YEAR(e.datum) = :ev"
    )
    List<EfoFoglalkoztatasok> findAllBySajatCegIdAndEv(@Param("sajatCegId") Long sajatCegId, @Param("ev") int ev);
}

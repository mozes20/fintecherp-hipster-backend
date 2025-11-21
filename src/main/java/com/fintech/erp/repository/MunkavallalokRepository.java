package com.fintech.erp.repository;

import com.fintech.erp.domain.Munkavallalok;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Munkavallalok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunkavallalokRepository extends JpaRepository<Munkavallalok, Long>, JpaSpecificationExecutor<Munkavallalok> {
    @Query(
        "select mv from Munkavallalok mv " +
        "left join fetch mv.sajatCeg sc " +
        "left join fetch sc.ceg ceg " +
        "join fetch mv.maganszemely ms " +
        "where sc.id = :sajatCegId and lower(ms.adoAzonositoJel) = lower(:adoAzonositoJel)"
    )
    Optional<Munkavallalok> findBySajatCegIdAndMaganszemelyAdoAzonositoJel(Long sajatCegId, String adoAzonositoJel);
}

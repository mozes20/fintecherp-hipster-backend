package com.fintech.erp.repository;

import com.fintech.erp.domain.SajatCegKepviselok;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SajatCegKepviselok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SajatCegKepviselokRepository
    extends JpaRepository<SajatCegKepviselok, Long>, JpaSpecificationExecutor<SajatCegKepviselok> {
    @Query("select k from SajatCegKepviselok k join fetch k.maganszemely where k.sajatCeg.id = :sajatCegId order by k.id desc")
    List<SajatCegKepviselok> findBySajatCegIdWithMaganszemely(@Param("sajatCegId") Long sajatCegId);
}

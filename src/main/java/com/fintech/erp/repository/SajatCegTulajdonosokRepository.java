package com.fintech.erp.repository;

import com.fintech.erp.domain.SajatCegTulajdonosok;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SajatCegTulajdonosok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SajatCegTulajdonosokRepository
    extends JpaRepository<SajatCegTulajdonosok, Long>, JpaSpecificationExecutor<SajatCegTulajdonosok> {
    @Query("select t from SajatCegTulajdonosok t join fetch t.maganszemely where t.sajatCeg.id = :sajatCegId")
    List<SajatCegTulajdonosok> findBySajatCegIdWithMaganszemely(@Param("sajatCegId") Long sajatCegId);
}

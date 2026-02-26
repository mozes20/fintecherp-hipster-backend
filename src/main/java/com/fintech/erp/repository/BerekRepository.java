package com.fintech.erp.repository;

import com.fintech.erp.domain.Berek;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Berek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BerekRepository extends JpaRepository<Berek, Long>, JpaSpecificationExecutor<Berek> {
    Optional<Berek> findFirstByMunkavallalo_IdOrderByErvenyessegKezdeteDesc(Long munkavallaloId);
}

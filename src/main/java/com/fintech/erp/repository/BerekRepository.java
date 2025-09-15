package com.fintech.erp.repository;

import com.fintech.erp.domain.Berek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Berek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BerekRepository extends JpaRepository<Berek, Long>, JpaSpecificationExecutor<Berek> {}

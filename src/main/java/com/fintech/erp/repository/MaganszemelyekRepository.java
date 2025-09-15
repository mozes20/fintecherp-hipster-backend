package com.fintech.erp.repository;

import com.fintech.erp.domain.Maganszemelyek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Maganszemelyek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaganszemelyekRepository extends JpaRepository<Maganszemelyek, Long>, JpaSpecificationExecutor<Maganszemelyek> {}

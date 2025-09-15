package com.fintech.erp.repository;

import com.fintech.erp.domain.Bankszamlaszamok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bankszamlaszamok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankszamlaszamokRepository extends JpaRepository<Bankszamlaszamok, Long>, JpaSpecificationExecutor<Bankszamlaszamok> {}

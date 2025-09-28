package com.fintech.erp.repository;

import com.fintech.erp.domain.Bankszamlaszamok;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Spring Data JPA repository for the Bankszamlaszamok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankszamlaszamokRepository extends JpaRepository<Bankszamlaszamok, Long>, JpaSpecificationExecutor<Bankszamlaszamok> {
    @EntityGraph(attributePaths = "ceg")
    Optional<Bankszamlaszamok> findWithCegById(Long id);

    @Override
    @EntityGraph(attributePaths = "ceg")
    Page<Bankszamlaszamok> findAll(Specification<Bankszamlaszamok> spec, Pageable pageable);
}

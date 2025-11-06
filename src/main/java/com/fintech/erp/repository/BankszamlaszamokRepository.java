package com.fintech.erp.repository;

import com.fintech.erp.domain.Bankszamlaszamok;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

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

    Optional<Bankszamlaszamok> findFirstByCegIdAndStatuszOrderByIdAsc(Long cegId, String statusz);

    Optional<Bankszamlaszamok> findFirstByCegIdOrderByIdAsc(Long cegId);
}

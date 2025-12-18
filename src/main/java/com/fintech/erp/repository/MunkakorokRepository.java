package com.fintech.erp.repository;

import com.fintech.erp.domain.Munkakorok;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Munkakorok entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunkakorokRepository extends JpaRepository<Munkakorok, Long> {
    Optional<Munkakorok> findFirstByOrderByMunkakorKodDesc();

    Optional<Munkakorok> findByMunkakorKod(String munkakorKod);

    boolean existsByMunkakorKod(String munkakorKod);
}

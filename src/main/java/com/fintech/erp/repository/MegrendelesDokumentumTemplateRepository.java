package com.fintech.erp.repository;

import com.fintech.erp.domain.MegrendelesDokumentumTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MegrendelesDokumentumTemplate entity.
 */
@Repository
public interface MegrendelesDokumentumTemplateRepository
    extends JpaRepository<MegrendelesDokumentumTemplate, Long>, JpaSpecificationExecutor<MegrendelesDokumentumTemplate> {}

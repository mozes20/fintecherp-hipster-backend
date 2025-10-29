package com.fintech.erp.service;

import com.fintech.erp.domain.MegrendelesDokumentumok;
import com.fintech.erp.domain.MegrendelesDokumentumok_;
import com.fintech.erp.domain.Megrendelesek_;
import com.fintech.erp.repository.MegrendelesDokumentumokRepository;
import com.fintech.erp.service.criteria.MegrendelesDokumentumokCriteria;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.mapper.MegrendelesDokumentumokMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger; // for static metamodels
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MegrendelesDokumentumok} entities in the database.
 * The main input is a {@link MegrendelesDokumentumokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MegrendelesDokumentumokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MegrendelesDokumentumokQueryService extends QueryService<MegrendelesDokumentumok> {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDokumentumokQueryService.class);

    private final MegrendelesDokumentumokRepository megrendelesDokumentumokRepository;

    private final MegrendelesDokumentumokMapper megrendelesDokumentumokMapper;

    public MegrendelesDokumentumokQueryService(
        MegrendelesDokumentumokRepository megrendelesDokumentumokRepository,
        MegrendelesDokumentumokMapper megrendelesDokumentumokMapper
    ) {
        this.megrendelesDokumentumokRepository = megrendelesDokumentumokRepository;
        this.megrendelesDokumentumokMapper = megrendelesDokumentumokMapper;
    }

    /**
     * Return a {@link Page} of {@link MegrendelesDokumentumokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MegrendelesDokumentumokDTO> findByCriteria(MegrendelesDokumentumokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MegrendelesDokumentumok> specification = createSpecification(criteria);
        return megrendelesDokumentumokRepository.findAll(specification, page).map(megrendelesDokumentumokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MegrendelesDokumentumokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<MegrendelesDokumentumok> specification = createSpecification(criteria);
        return megrendelesDokumentumokRepository.count(specification);
    }

    /**
     * Function to convert {@link MegrendelesDokumentumokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MegrendelesDokumentumok> createSpecification(MegrendelesDokumentumokCriteria criteria) {
        Specification<MegrendelesDokumentumok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), MegrendelesDokumentumok_.id),
                buildSpecification(criteria.getDokumentumTipusa(), MegrendelesDokumentumok_.dokumentumTipusa),
                buildStringSpecification(criteria.getDokumentum(), MegrendelesDokumentumok_.dokumentum),
                buildStringSpecification(criteria.getDokumentumUrl(), MegrendelesDokumentumok_.dokumentumUrl),
                buildStringSpecification(criteria.getDokumentumAzonosito(), MegrendelesDokumentumok_.dokumentumAzonosito),
                buildSpecification(criteria.getMegrendelesId(), root ->
                    root.join(MegrendelesDokumentumok_.megrendeles, JoinType.LEFT).get(Megrendelesek_.id)
                )
            );
        }
        return specification;
    }
}

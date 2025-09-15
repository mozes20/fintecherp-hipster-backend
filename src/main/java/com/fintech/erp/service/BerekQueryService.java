package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.Berek;
import com.fintech.erp.repository.BerekRepository;
import com.fintech.erp.service.criteria.BerekCriteria;
import com.fintech.erp.service.dto.BerekDTO;
import com.fintech.erp.service.mapper.BerekMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Berek} entities in the database.
 * The main input is a {@link BerekCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BerekDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BerekQueryService extends QueryService<Berek> {

    private static final Logger LOG = LoggerFactory.getLogger(BerekQueryService.class);

    private final BerekRepository berekRepository;

    private final BerekMapper berekMapper;

    public BerekQueryService(BerekRepository berekRepository, BerekMapper berekMapper) {
        this.berekRepository = berekRepository;
        this.berekMapper = berekMapper;
    }

    /**
     * Return a {@link Page} of {@link BerekDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BerekDTO> findByCriteria(BerekCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Berek> specification = createSpecification(criteria);
        return berekRepository.findAll(specification, page).map(berekMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BerekCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Berek> specification = createSpecification(criteria);
        return berekRepository.count(specification);
    }

    /**
     * Function to convert {@link BerekCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Berek> createSpecification(BerekCriteria criteria) {
        Specification<Berek> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Berek_.id),
                buildRangeSpecification(criteria.getErvenyessegKezdete(), Berek_.ervenyessegKezdete),
                buildRangeSpecification(criteria.getBruttoHaviMunkaberVagyNapdij(), Berek_.bruttoHaviMunkaberVagyNapdij),
                buildStringSpecification(criteria.getMunkaszerzodes(), Berek_.munkaszerzodes),
                buildRangeSpecification(criteria.getTeljesKoltseg(), Berek_.teljesKoltseg),
                buildSpecification(criteria.getMunkavallaloId(), root ->
                    root.join(Berek_.munkavallalo, JoinType.LEFT).get(Munkavallalok_.id)
                )
            );
        }
        return specification;
    }
}

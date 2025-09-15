package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.SajatCegKepviselok;
import com.fintech.erp.repository.SajatCegKepviselokRepository;
import com.fintech.erp.service.criteria.SajatCegKepviselokCriteria;
import com.fintech.erp.service.dto.SajatCegKepviselokDTO;
import com.fintech.erp.service.mapper.SajatCegKepviselokMapper;
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
 * Service for executing complex queries for {@link SajatCegKepviselok} entities in the database.
 * The main input is a {@link SajatCegKepviselokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SajatCegKepviselokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SajatCegKepviselokQueryService extends QueryService<SajatCegKepviselok> {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegKepviselokQueryService.class);

    private final SajatCegKepviselokRepository sajatCegKepviselokRepository;

    private final SajatCegKepviselokMapper sajatCegKepviselokMapper;

    public SajatCegKepviselokQueryService(
        SajatCegKepviselokRepository sajatCegKepviselokRepository,
        SajatCegKepviselokMapper sajatCegKepviselokMapper
    ) {
        this.sajatCegKepviselokRepository = sajatCegKepviselokRepository;
        this.sajatCegKepviselokMapper = sajatCegKepviselokMapper;
    }

    /**
     * Return a {@link Page} of {@link SajatCegKepviselokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SajatCegKepviselokDTO> findByCriteria(SajatCegKepviselokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SajatCegKepviselok> specification = createSpecification(criteria);
        return sajatCegKepviselokRepository.findAll(specification, page).map(sajatCegKepviselokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SajatCegKepviselokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SajatCegKepviselok> specification = createSpecification(criteria);
        return sajatCegKepviselokRepository.count(specification);
    }

    /**
     * Function to convert {@link SajatCegKepviselokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SajatCegKepviselok> createSpecification(SajatCegKepviselokCriteria criteria) {
        Specification<SajatCegKepviselok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SajatCegKepviselok_.id),
                buildStringSpecification(criteria.getStatusz(), SajatCegKepviselok_.statusz),
                buildSpecification(criteria.getSajatCegId(), root ->
                    root.join(SajatCegKepviselok_.sajatCeg, JoinType.LEFT).get(SajatCegAlapadatok_.id)
                ),
                buildSpecification(criteria.getMaganszemelyId(), root ->
                    root.join(SajatCegKepviselok_.maganszemely, JoinType.LEFT).get(Maganszemelyek_.id)
                )
            );
        }
        return specification;
    }
}

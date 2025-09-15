package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.SajatCegTulajdonosok;
import com.fintech.erp.repository.SajatCegTulajdonosokRepository;
import com.fintech.erp.service.criteria.SajatCegTulajdonosokCriteria;
import com.fintech.erp.service.dto.SajatCegTulajdonosokDTO;
import com.fintech.erp.service.mapper.SajatCegTulajdonosokMapper;
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
 * Service for executing complex queries for {@link SajatCegTulajdonosok} entities in the database.
 * The main input is a {@link SajatCegTulajdonosokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SajatCegTulajdonosokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SajatCegTulajdonosokQueryService extends QueryService<SajatCegTulajdonosok> {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegTulajdonosokQueryService.class);

    private final SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository;

    private final SajatCegTulajdonosokMapper sajatCegTulajdonosokMapper;

    public SajatCegTulajdonosokQueryService(
        SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository,
        SajatCegTulajdonosokMapper sajatCegTulajdonosokMapper
    ) {
        this.sajatCegTulajdonosokRepository = sajatCegTulajdonosokRepository;
        this.sajatCegTulajdonosokMapper = sajatCegTulajdonosokMapper;
    }

    /**
     * Return a {@link Page} of {@link SajatCegTulajdonosokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SajatCegTulajdonosokDTO> findByCriteria(SajatCegTulajdonosokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SajatCegTulajdonosok> specification = createSpecification(criteria);
        return sajatCegTulajdonosokRepository.findAll(specification, page).map(sajatCegTulajdonosokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SajatCegTulajdonosokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SajatCegTulajdonosok> specification = createSpecification(criteria);
        return sajatCegTulajdonosokRepository.count(specification);
    }

    /**
     * Function to convert {@link SajatCegTulajdonosokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SajatCegTulajdonosok> createSpecification(SajatCegTulajdonosokCriteria criteria) {
        Specification<SajatCegTulajdonosok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SajatCegTulajdonosok_.id),
                buildRangeSpecification(criteria.getBruttoOsztalek(), SajatCegTulajdonosok_.bruttoOsztalek),
                buildStringSpecification(criteria.getStatusz(), SajatCegTulajdonosok_.statusz),
                buildSpecification(criteria.getSajatCegId(), root ->
                    root.join(SajatCegTulajdonosok_.sajatCeg, JoinType.LEFT).get(SajatCegAlapadatok_.id)
                ),
                buildSpecification(criteria.getMaganszemelyId(), root ->
                    root.join(SajatCegTulajdonosok_.maganszemely, JoinType.LEFT).get(Maganszemelyek_.id)
                )
            );
        }
        return specification;
    }
}

package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.PartnerCegMunkavallalok;
import com.fintech.erp.repository.PartnerCegMunkavallalokRepository;
import com.fintech.erp.service.criteria.PartnerCegMunkavallalokCriteria;
import com.fintech.erp.service.dto.PartnerCegMunkavallalokDTO;
import com.fintech.erp.service.mapper.PartnerCegMunkavallalokMapper;
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
 * Service for executing complex queries for {@link PartnerCegMunkavallalok} entities in the database.
 * The main input is a {@link PartnerCegMunkavallalokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PartnerCegMunkavallalokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartnerCegMunkavallalokQueryService extends QueryService<PartnerCegMunkavallalok> {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegMunkavallalokQueryService.class);

    private final PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository;

    private final PartnerCegMunkavallalokMapper partnerCegMunkavallalokMapper;

    public PartnerCegMunkavallalokQueryService(
        PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository,
        PartnerCegMunkavallalokMapper partnerCegMunkavallalokMapper
    ) {
        this.partnerCegMunkavallalokRepository = partnerCegMunkavallalokRepository;
        this.partnerCegMunkavallalokMapper = partnerCegMunkavallalokMapper;
    }

    /**
     * Return a {@link Page} of {@link PartnerCegMunkavallalokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartnerCegMunkavallalokDTO> findByCriteria(PartnerCegMunkavallalokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartnerCegMunkavallalok> specification = createSpecification(criteria);
        return partnerCegMunkavallalokRepository.findAll(specification, page).map(partnerCegMunkavallalokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartnerCegMunkavallalokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<PartnerCegMunkavallalok> specification = createSpecification(criteria);
        return partnerCegMunkavallalokRepository.count(specification);
    }

    /**
     * Function to convert {@link PartnerCegMunkavallalokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartnerCegMunkavallalok> createSpecification(PartnerCegMunkavallalokCriteria criteria) {
        Specification<PartnerCegMunkavallalok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), PartnerCegMunkavallalok_.id),
                buildStringSpecification(criteria.getStatusz(), PartnerCegMunkavallalok_.statusz),
                buildSpecification(criteria.getPartnerCegId(), root ->
                    root.join(PartnerCegMunkavallalok_.partnerCeg, JoinType.LEFT).get(PartnerCegAdatok_.id)
                ),
                buildSpecification(criteria.getMaganszemelyId(), root ->
                    root.join(PartnerCegMunkavallalok_.maganszemely, JoinType.LEFT).get(Maganszemelyek_.id)
                )
            );
        }
        return specification;
    }
}

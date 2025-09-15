package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import com.fintech.erp.repository.PartnerCegKapcsolattartokRepository;
import com.fintech.erp.service.criteria.PartnerCegKapcsolattartokCriteria;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
import com.fintech.erp.service.mapper.PartnerCegKapcsolattartokMapper;
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
 * Service for executing complex queries for {@link PartnerCegKapcsolattartok} entities in the database.
 * The main input is a {@link PartnerCegKapcsolattartokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PartnerCegKapcsolattartokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartnerCegKapcsolattartokQueryService extends QueryService<PartnerCegKapcsolattartok> {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegKapcsolattartokQueryService.class);

    private final PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository;

    private final PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper;

    public PartnerCegKapcsolattartokQueryService(
        PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository,
        PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper
    ) {
        this.partnerCegKapcsolattartokRepository = partnerCegKapcsolattartokRepository;
        this.partnerCegKapcsolattartokMapper = partnerCegKapcsolattartokMapper;
    }

    /**
     * Return a {@link Page} of {@link PartnerCegKapcsolattartokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartnerCegKapcsolattartokDTO> findByCriteria(PartnerCegKapcsolattartokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartnerCegKapcsolattartok> specification = createSpecification(criteria);
        return partnerCegKapcsolattartokRepository.findAll(specification, page).map(partnerCegKapcsolattartokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartnerCegKapcsolattartokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<PartnerCegKapcsolattartok> specification = createSpecification(criteria);
        return partnerCegKapcsolattartokRepository.count(specification);
    }

    /**
     * Function to convert {@link PartnerCegKapcsolattartokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartnerCegKapcsolattartok> createSpecification(PartnerCegKapcsolattartokCriteria criteria) {
        Specification<PartnerCegKapcsolattartok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), PartnerCegKapcsolattartok_.id),
                buildStringSpecification(criteria.getKapcsolattartoTitulus(), PartnerCegKapcsolattartok_.kapcsolattartoTitulus),
                buildStringSpecification(criteria.getStatusz(), PartnerCegKapcsolattartok_.statusz),
                buildSpecification(criteria.getPartnerCegId(), root ->
                    root.join(PartnerCegKapcsolattartok_.partnerCeg, JoinType.LEFT).get(PartnerCegAdatok_.id)
                ),
                buildSpecification(criteria.getMaganszemelyId(), root ->
                    root.join(PartnerCegKapcsolattartok_.maganszemely, JoinType.LEFT).get(Maganszemelyek_.id)
                )
            );
        }
        return specification;
    }
}

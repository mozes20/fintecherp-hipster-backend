package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.service.criteria.EfoFoglalkoztatasokCriteria;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import com.fintech.erp.service.mapper.EfoFoglalkoztatasokMapper;
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
 * Service for executing complex queries for {@link EfoFoglalkoztatasok} entities in the database.
 * The main input is a {@link EfoFoglalkoztatasokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EfoFoglalkoztatasokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EfoFoglalkoztatasokQueryService extends QueryService<EfoFoglalkoztatasok> {

    private static final Logger LOG = LoggerFactory.getLogger(EfoFoglalkoztatasokQueryService.class);

    private final EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;

    private final EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper;

    public EfoFoglalkoztatasokQueryService(
        EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository,
        EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper
    ) {
        this.efoFoglalkoztatasokRepository = efoFoglalkoztatasokRepository;
        this.efoFoglalkoztatasokMapper = efoFoglalkoztatasokMapper;
    }

    /**
     * Return a {@link Page} of {@link EfoFoglalkoztatasokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EfoFoglalkoztatasokDTO> findByCriteria(EfoFoglalkoztatasokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EfoFoglalkoztatasok> specification = createSpecification(criteria);
        return efoFoglalkoztatasokRepository.findAll(specification, page).map(efoFoglalkoztatasokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EfoFoglalkoztatasokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<EfoFoglalkoztatasok> specification = createSpecification(criteria);
        return efoFoglalkoztatasokRepository.count(specification);
    }

    /**
     * Function to convert {@link EfoFoglalkoztatasokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EfoFoglalkoztatasok> createSpecification(EfoFoglalkoztatasokCriteria criteria) {
        Specification<EfoFoglalkoztatasok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), EfoFoglalkoztatasok_.id),
                buildRangeSpecification(criteria.getDatum(), EfoFoglalkoztatasok_.datum),
                buildRangeSpecification(criteria.getOsszeg(), EfoFoglalkoztatasok_.osszeg),
                buildSpecification(criteria.getGeneraltEfoSzerzodes(), EfoFoglalkoztatasok_.generaltEfoSzerzodes),
                buildSpecification(criteria.getAlairtEfoSzerzodes(), EfoFoglalkoztatasok_.alairtEfoSzerzodes),
                buildStringSpecification(criteria.getGeneraltDokumentumNev(), EfoFoglalkoztatasok_.generaltDokumentumNev),
                buildStringSpecification(criteria.getGeneraltDokumentumUrl(), EfoFoglalkoztatasok_.generaltDokumentumUrl),
                buildStringSpecification(criteria.getAlairtDokumentumUrl(), EfoFoglalkoztatasok_.alairtDokumentumUrl),
                buildSpecification(criteria.getMunkavallaloId(), root ->
                    root.join(EfoFoglalkoztatasok_.munkavallalo, JoinType.LEFT).get(Munkavallalok_.id)
                ),
                buildSpecification(criteria.getMunkakorId(), root ->
                    root.join(EfoFoglalkoztatasok_.munkakor, JoinType.LEFT).get(Munkakorok_.id)
                )
            );
        }
        return specification;
    }
}

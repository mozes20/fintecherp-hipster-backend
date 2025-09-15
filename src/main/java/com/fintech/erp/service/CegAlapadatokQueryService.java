package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.repository.CegAlapadatokRepository;
import com.fintech.erp.service.criteria.CegAlapadatokCriteria;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.mapper.CegAlapadatokMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CegAlapadatok} entities in the database.
 * The main input is a {@link CegAlapadatokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CegAlapadatokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CegAlapadatokQueryService extends QueryService<CegAlapadatok> {

    private static final Logger LOG = LoggerFactory.getLogger(CegAlapadatokQueryService.class);

    private final CegAlapadatokRepository cegAlapadatokRepository;

    private final CegAlapadatokMapper cegAlapadatokMapper;

    public CegAlapadatokQueryService(CegAlapadatokRepository cegAlapadatokRepository, CegAlapadatokMapper cegAlapadatokMapper) {
        this.cegAlapadatokRepository = cegAlapadatokRepository;
        this.cegAlapadatokMapper = cegAlapadatokMapper;
    }

    /**
     * Return a {@link Page} of {@link CegAlapadatokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CegAlapadatokDTO> findByCriteria(CegAlapadatokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CegAlapadatok> specification = createSpecification(criteria);
        return cegAlapadatokRepository.findAll(specification, page).map(cegAlapadatokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CegAlapadatokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<CegAlapadatok> specification = createSpecification(criteria);
        return cegAlapadatokRepository.count(specification);
    }

    /**
     * Function to convert {@link CegAlapadatokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CegAlapadatok> createSpecification(CegAlapadatokCriteria criteria) {
        Specification<CegAlapadatok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), CegAlapadatok_.id),
                buildStringSpecification(criteria.getCegNev(), CegAlapadatok_.cegNev),
                buildStringSpecification(criteria.getCegRovidAzonosito(), CegAlapadatok_.cegRovidAzonosito),
                buildStringSpecification(criteria.getCegSzekhely(), CegAlapadatok_.cegSzekhely),
                buildStringSpecification(criteria.getAdoszam(), CegAlapadatok_.adoszam),
                buildStringSpecification(criteria.getCegjegyzekszam(), CegAlapadatok_.cegjegyzekszam),
                buildStringSpecification(criteria.getCegKozpontiEmail(), CegAlapadatok_.cegKozpontiEmail),
                buildStringSpecification(criteria.getCegKozpontiTel(), CegAlapadatok_.cegKozpontiTel),
                buildStringSpecification(criteria.getStatusz(), CegAlapadatok_.statusz)
            );
        }
        return specification;
    }
}

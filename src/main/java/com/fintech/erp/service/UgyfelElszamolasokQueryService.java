package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.repository.UgyfelElszamolasokRepository;
import com.fintech.erp.service.criteria.UgyfelElszamolasokCriteria;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
import com.fintech.erp.service.mapper.UgyfelElszamolasokMapper;
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
 * Service for executing complex queries for {@link UgyfelElszamolasok} entities in the database.
 * The main input is a {@link UgyfelElszamolasokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UgyfelElszamolasokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UgyfelElszamolasokQueryService extends QueryService<UgyfelElszamolasok> {

    private static final Logger LOG = LoggerFactory.getLogger(UgyfelElszamolasokQueryService.class);

    private final UgyfelElszamolasokRepository ugyfelElszamolasokRepository;

    private final UgyfelElszamolasokMapper ugyfelElszamolasokMapper;

    public UgyfelElszamolasokQueryService(
        UgyfelElszamolasokRepository ugyfelElszamolasokRepository,
        UgyfelElszamolasokMapper ugyfelElszamolasokMapper
    ) {
        this.ugyfelElszamolasokRepository = ugyfelElszamolasokRepository;
        this.ugyfelElszamolasokMapper = ugyfelElszamolasokMapper;
    }

    /**
     * Return a {@link Page} of {@link UgyfelElszamolasokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UgyfelElszamolasokDTO> findByCriteria(UgyfelElszamolasokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UgyfelElszamolasok> specification = createSpecification(criteria);
        return ugyfelElszamolasokRepository.findAll(specification, page).map(ugyfelElszamolasokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UgyfelElszamolasokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<UgyfelElszamolasok> specification = createSpecification(criteria);
        return ugyfelElszamolasokRepository.count(specification);
    }

    /**
     * Function to convert {@link UgyfelElszamolasokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UgyfelElszamolasok> createSpecification(UgyfelElszamolasokCriteria criteria) {
        Specification<UgyfelElszamolasok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), UgyfelElszamolasok_.id),
                buildRangeSpecification(criteria.getTeljesitesiIdoszakKezdete(), UgyfelElszamolasok_.teljesitesiIdoszakKezdete),
                buildRangeSpecification(criteria.getTeljesitesiIdoszakVege(), UgyfelElszamolasok_.teljesitesiIdoszakVege),
                buildRangeSpecification(criteria.getNapokSzama(), UgyfelElszamolasok_.napokSzama),
                buildRangeSpecification(
                    criteria.getTeljesitesIgazolasonSzereploOsszeg(),
                    UgyfelElszamolasok_.teljesitesIgazolasonSzereploOsszeg
                ),
                buildSpecification(criteria.getKapcsolodoSzamlaSorszamRogzitve(), UgyfelElszamolasok_.kapcsolodoSzamlaSorszamRogzitve),
                buildSpecification(criteria.getMegrendelesId(), root ->
                    root.join(UgyfelElszamolasok_.megrendeles, JoinType.LEFT).get(Megrendelesek_.id)
                )
            );
        }
        return specification;
    }
}

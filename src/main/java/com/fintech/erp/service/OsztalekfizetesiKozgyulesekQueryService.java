package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.criteria.OsztalekfizetesiKozgyulesekCriteria;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.mapper.OsztalekfizetesiKozgyulesekMapper;
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
 * Service for executing complex queries for {@link OsztalekfizetesiKozgyulesek} entities in the database.
 * The main input is a {@link OsztalekfizetesiKozgyulesekCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link OsztalekfizetesiKozgyulesekDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OsztalekfizetesiKozgyulesekQueryService extends QueryService<OsztalekfizetesiKozgyulesek> {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekQueryService.class);

    private final OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository;

    private final OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper;

    public OsztalekfizetesiKozgyulesekQueryService(
        OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository,
        OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper
    ) {
        this.osztalekfizetesiKozgyulesekRepository = osztalekfizetesiKozgyulesekRepository;
        this.osztalekfizetesiKozgyulesekMapper = osztalekfizetesiKozgyulesekMapper;
    }

    /**
     * Return a {@link Page} of {@link OsztalekfizetesiKozgyulesekDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OsztalekfizetesiKozgyulesekDTO> findByCriteria(OsztalekfizetesiKozgyulesekCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OsztalekfizetesiKozgyulesek> specification = createSpecification(criteria);
        return osztalekfizetesiKozgyulesekRepository.findAll(specification, page).map(osztalekfizetesiKozgyulesekMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OsztalekfizetesiKozgyulesekCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<OsztalekfizetesiKozgyulesek> specification = createSpecification(criteria);
        return osztalekfizetesiKozgyulesekRepository.count(specification);
    }

    /**
     * Function to convert {@link OsztalekfizetesiKozgyulesekCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OsztalekfizetesiKozgyulesek> createSpecification(OsztalekfizetesiKozgyulesekCriteria criteria) {
        Specification<OsztalekfizetesiKozgyulesek> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), OsztalekfizetesiKozgyulesek_.id),
                buildRangeSpecification(criteria.getKozgyulesDatum(), OsztalekfizetesiKozgyulesek_.kozgyulesDatum),
                buildSpecification(
                    criteria.getKozgyulesiJegyzokonyvGeneralta(),
                    OsztalekfizetesiKozgyulesek_.kozgyulesiJegyzokonyvGeneralta
                ),
                buildSpecification(criteria.getKozgyulesiJegyzokonyvAlairt(), OsztalekfizetesiKozgyulesek_.kozgyulesiJegyzokonyvAlairt),
                buildSpecification(criteria.getSajatCegId(), root ->
                    root.join(OsztalekfizetesiKozgyulesek_.sajatCeg, JoinType.LEFT).get(SajatCegAlapadatok_.id)
                )
            );
        }
        return specification;
    }
}

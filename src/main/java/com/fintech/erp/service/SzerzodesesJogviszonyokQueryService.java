package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.criteria.SzerzodesesJogviszonyokCriteria;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import com.fintech.erp.service.mapper.SzerzodesesJogviszonyokMapper;
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
 * Service for executing complex queries for {@link SzerzodesesJogviszonyok} entities in the database.
 * The main input is a {@link SzerzodesesJogviszonyokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SzerzodesesJogviszonyokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SzerzodesesJogviszonyokQueryService extends QueryService<SzerzodesesJogviszonyok> {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyokQueryService.class);

    private final SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    private final SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper;

    public SzerzodesesJogviszonyokQueryService(
        SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository,
        SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper
    ) {
        this.szerzodesesJogviszonyokRepository = szerzodesesJogviszonyokRepository;
        this.szerzodesesJogviszonyokMapper = szerzodesesJogviszonyokMapper;
    }

    /**
     * Return a {@link Page} of {@link SzerzodesesJogviszonyokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SzerzodesesJogviszonyokDTO> findByCriteria(SzerzodesesJogviszonyokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SzerzodesesJogviszonyok> specification = createSpecification(criteria);
        return szerzodesesJogviszonyokRepository.findAll(specification, page).map(szerzodesesJogviszonyokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SzerzodesesJogviszonyokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SzerzodesesJogviszonyok> specification = createSpecification(criteria);
        return szerzodesesJogviszonyokRepository.count(specification);
    }

    /**
     * Function to convert {@link SzerzodesesJogviszonyokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SzerzodesesJogviszonyok> createSpecification(SzerzodesesJogviszonyokCriteria criteria) {
        Specification<SzerzodesesJogviszonyok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SzerzodesesJogviszonyok_.id),
                buildStringSpecification(criteria.getSzerzodesAzonosito(), SzerzodesesJogviszonyok_.szerzodesAzonosito),
                buildRangeSpecification(criteria.getJogviszonyKezdete(), SzerzodesesJogviszonyok_.jogviszonyKezdete),
                buildRangeSpecification(criteria.getJogviszonyLejarata(), SzerzodesesJogviszonyok_.jogviszonyLejarata),
                buildSpecification(criteria.getMegrendeloCegId(), root ->
                    root.join(SzerzodesesJogviszonyok_.megrendeloCeg, JoinType.LEFT).get(CegAlapadatok_.id)
                ),
                buildSpecification(criteria.getVallalkozoCegId(), root ->
                    root.join(SzerzodesesJogviszonyok_.vallalkozoCeg, JoinType.LEFT).get(CegAlapadatok_.id)
                )
            );
        }
        return specification;
    }
}

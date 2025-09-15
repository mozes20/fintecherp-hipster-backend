package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.criteria.MunkavallalokCriteria;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import com.fintech.erp.service.mapper.MunkavallalokMapper;
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
 * Service for executing complex queries for {@link Munkavallalok} entities in the database.
 * The main input is a {@link MunkavallalokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MunkavallalokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MunkavallalokQueryService extends QueryService<Munkavallalok> {

    private static final Logger LOG = LoggerFactory.getLogger(MunkavallalokQueryService.class);

    private final MunkavallalokRepository munkavallalokRepository;

    private final MunkavallalokMapper munkavallalokMapper;

    public MunkavallalokQueryService(MunkavallalokRepository munkavallalokRepository, MunkavallalokMapper munkavallalokMapper) {
        this.munkavallalokRepository = munkavallalokRepository;
        this.munkavallalokMapper = munkavallalokMapper;
    }

    /**
     * Return a {@link Page} of {@link MunkavallalokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MunkavallalokDTO> findByCriteria(MunkavallalokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Munkavallalok> specification = createSpecification(criteria);
        return munkavallalokRepository.findAll(specification, page).map(munkavallalokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MunkavallalokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Munkavallalok> specification = createSpecification(criteria);
        return munkavallalokRepository.count(specification);
    }

    /**
     * Function to convert {@link MunkavallalokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Munkavallalok> createSpecification(MunkavallalokCriteria criteria) {
        Specification<Munkavallalok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Munkavallalok_.id),
                buildStringSpecification(criteria.getFoglalkoztatasTipusa(), Munkavallalok_.foglalkoztatasTipusa),
                buildRangeSpecification(criteria.getFoglalkoztatasKezdete(), Munkavallalok_.foglalkoztatasKezdete),
                buildRangeSpecification(criteria.getFoglalkoztatasVege(), Munkavallalok_.foglalkoztatasVege),
                buildSpecification(criteria.getSajatCegId(), root ->
                    root.join(Munkavallalok_.sajatCeg, JoinType.LEFT).get(SajatCegAlapadatok_.id)
                ),
                buildSpecification(criteria.getMaganszemelyId(), root ->
                    root.join(Munkavallalok_.maganszemely, JoinType.LEFT).get(Maganszemelyek_.id)
                )
            );
        }
        return specification;
    }
}

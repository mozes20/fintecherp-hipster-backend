package com.fintech.erp.service;

import com.fintech.erp.domain.CegAlapadatok_;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.domain.PartnerCegAdatok_;
import com.fintech.erp.repository.PartnerCegAdatokRepository;
import com.fintech.erp.service.criteria.PartnerCegAdatokCriteria;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import com.fintech.erp.service.mapper.PartnerCegAdatokMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger; // for static metamodels
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PartnerCegAdatok} entities in the database.
 * The main input is a {@link PartnerCegAdatokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PartnerCegAdatokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartnerCegAdatokQueryService extends QueryService<PartnerCegAdatok> {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegAdatokQueryService.class);

    private final PartnerCegAdatokRepository partnerCegAdatokRepository;

    private final PartnerCegAdatokMapper partnerCegAdatokMapper;

    public PartnerCegAdatokQueryService(
        PartnerCegAdatokRepository partnerCegAdatokRepository,
        PartnerCegAdatokMapper partnerCegAdatokMapper
    ) {
        this.partnerCegAdatokRepository = partnerCegAdatokRepository;
        this.partnerCegAdatokMapper = partnerCegAdatokMapper;
    }

    /**
     * Return a {@link Page} of {@link PartnerCegAdatokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartnerCegAdatokDTO> findByCriteria(PartnerCegAdatokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartnerCegAdatok> specification = createSpecification(criteria);
        return partnerCegAdatokRepository.findAll(specification, page).map(partnerCegAdatokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartnerCegAdatokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<PartnerCegAdatok> specification = createSpecification(criteria);
        return partnerCegAdatokRepository.count(specification);
    }

    /**
     * Function to convert {@link PartnerCegAdatokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartnerCegAdatok> createSpecification(PartnerCegAdatokCriteria criteria) {
        Specification<PartnerCegAdatok> specification = Specification.where(null);
        if (criteria != null) {
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), PartnerCegAdatok_.id),
                buildStringSpecification(criteria.getStatusz(), PartnerCegAdatok_.statusz),
                buildSpecification(criteria.getCegId(), root -> root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.id)),
                criteria.getCegCegNev() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegNev)),
                            "%" + criteria.getCegCegNev().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegCegSzekhely() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegSzekhely)),
                            "%" + criteria.getCegCegSzekhely().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegAdoszam() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.adoszam)),
                            "%" + criteria.getCegAdoszam().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegCegRovidAzonosito() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegRovidAzonosito)),
                            "%" + criteria.getCegCegRovidAzonosito().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegCegjegyzekszam() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegjegyzekszam)),
                            "%" + criteria.getCegCegjegyzekszam().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegKozpontiEmail() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegKozpontiEmail)),
                            "%" + criteria.getCegKozpontiEmail().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegKozpontiTel() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegKozpontiTel)),
                            "%" + criteria.getCegKozpontiTel().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegStatusz() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(PartnerCegAdatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.statusz)),
                            "%" + criteria.getCegStatusz().getContains().toLowerCase() + "%"
                        )
                    : null
            );
        }
        return specification;
    }
}

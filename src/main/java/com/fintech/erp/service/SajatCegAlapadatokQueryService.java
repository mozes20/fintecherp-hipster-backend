package com.fintech.erp.service;

import com.fintech.erp.domain.CegAlapadatok_;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegAlapadatok_;
import com.fintech.erp.repository.SajatCegAlapadatokRepository;
import com.fintech.erp.service.criteria.SajatCegAlapadatokCriteria;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import com.fintech.erp.service.mapper.SajatCegAlapadatokMapper;
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
 * Service for executing complex queries for {@link SajatCegAlapadatok} entities in the database.
 * The main input is a {@link SajatCegAlapadatokCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SajatCegAlapadatokDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SajatCegAlapadatokQueryService extends QueryService<SajatCegAlapadatok> {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegAlapadatokQueryService.class);

    private final SajatCegAlapadatokRepository sajatCegAlapadatokRepository;

    private final SajatCegAlapadatokMapper sajatCegAlapadatokMapper;

    public SajatCegAlapadatokQueryService(
        SajatCegAlapadatokRepository sajatCegAlapadatokRepository,
        SajatCegAlapadatokMapper sajatCegAlapadatokMapper
    ) {
        this.sajatCegAlapadatokRepository = sajatCegAlapadatokRepository;
        this.sajatCegAlapadatokMapper = sajatCegAlapadatokMapper;
    }

    /**
     * Return a {@link Page} of {@link SajatCegAlapadatokDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SajatCegAlapadatokDTO> findByCriteria(SajatCegAlapadatokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SajatCegAlapadatok> specification = createSpecification(criteria);
        return sajatCegAlapadatokRepository.findAll(specification, page).map(sajatCegAlapadatokMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SajatCegAlapadatokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SajatCegAlapadatok> specification = createSpecification(criteria);
        return sajatCegAlapadatokRepository.count(specification);
    }

    /**
     * Function to convert {@link SajatCegAlapadatokCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SajatCegAlapadatok> createSpecification(SajatCegAlapadatokCriteria criteria) {
        Specification<SajatCegAlapadatok> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SajatCegAlapadatok_.id),
                buildRangeSpecification(criteria.getCegAdminisztraciosHaviKoltseg(), SajatCegAlapadatok_.cegAdminisztraciosHaviKoltseg),
                buildStringSpecification(criteria.getStatusz(), SajatCegAlapadatok_.statusz),
                buildSpecification(criteria.getCegId(), root -> root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.id)),
                criteria.getCegCegNev() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegNev)),
                            "%" + criteria.getCegCegNev().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegCegRovidAzonosito() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegRovidAzonosito)),
                            "%" + criteria.getCegCegRovidAzonosito().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegCegSzekhely() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegSzekhely)),
                            "%" + criteria.getCegCegSzekhely().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegAdoszam() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.adoszam)),
                            "%" + criteria.getCegAdoszam().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegCegjegyzekszam() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegjegyzekszam)),
                            "%" + criteria.getCegCegjegyzekszam().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegKozpontiEmail() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegKozpontiEmail)),
                            "%" + criteria.getCegKozpontiEmail().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegKozpontiTel() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.cegKozpontiTel)),
                            "%" + criteria.getCegKozpontiTel().getContains().toLowerCase() + "%"
                        )
                    : null,
                criteria.getCegStatusz() != null
                    ? (root, query, cb) ->
                        cb.like(
                            cb.lower(root.join(SajatCegAlapadatok_.ceg, JoinType.LEFT).get(CegAlapadatok_.statusz)),
                            "%" + criteria.getCegStatusz().getContains().toLowerCase() + "%"
                        )
                    : null
            );
        }
        return specification;
    }
}

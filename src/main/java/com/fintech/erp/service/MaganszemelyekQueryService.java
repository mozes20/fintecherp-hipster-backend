package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.repository.MaganszemelyekRepository;
import com.fintech.erp.service.criteria.MaganszemelyekCriteria;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.mapper.MaganszemelyekMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Maganszemelyek} entities in the database.
 * The main input is a {@link MaganszemelyekCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MaganszemelyekDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaganszemelyekQueryService extends QueryService<Maganszemelyek> {

    private static final Logger LOG = LoggerFactory.getLogger(MaganszemelyekQueryService.class);

    private final MaganszemelyekRepository maganszemelyekRepository;

    private final MaganszemelyekMapper maganszemelyekMapper;

    public MaganszemelyekQueryService(MaganszemelyekRepository maganszemelyekRepository, MaganszemelyekMapper maganszemelyekMapper) {
        this.maganszemelyekRepository = maganszemelyekRepository;
        this.maganszemelyekMapper = maganszemelyekMapper;
    }

    /**
     * Return a {@link Page} of {@link MaganszemelyekDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaganszemelyekDTO> findByCriteria(MaganszemelyekCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Maganszemelyek> specification = createSpecification(criteria);
        return maganszemelyekRepository.findAll(specification, page).map(maganszemelyekMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaganszemelyekCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Maganszemelyek> specification = createSpecification(criteria);
        return maganszemelyekRepository.count(specification);
    }

    /**
     * Function to convert {@link MaganszemelyekCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Maganszemelyek> createSpecification(MaganszemelyekCriteria criteria) {
        Specification<Maganszemelyek> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Maganszemelyek_.id),
                buildStringSpecification(criteria.getMaganszemelyNeve(), Maganszemelyek_.maganszemelyNeve),
                buildStringSpecification(criteria.getSzemelyiIgazolvanySzama(), Maganszemelyek_.szemelyiIgazolvanySzama),
                buildStringSpecification(criteria.getAdoAzonositoJel(), Maganszemelyek_.adoAzonositoJel),
                buildStringSpecification(criteria.getTbAzonosito(), Maganszemelyek_.tbAzonosito),
                buildStringSpecification(criteria.getBankszamlaszam(), Maganszemelyek_.bankszamlaszam),
                buildStringSpecification(criteria.getTelefonszam(), Maganszemelyek_.telefonszam),
                buildStringSpecification(criteria.getEmailcim(), Maganszemelyek_.emailcim),
                buildStringSpecification(criteria.getStatusz(), Maganszemelyek_.statusz)
            );
        }
        return specification;
    }
}

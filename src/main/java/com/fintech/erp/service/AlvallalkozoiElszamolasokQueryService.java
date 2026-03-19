package com.fintech.erp.service;

import com.fintech.erp.domain.*; // for static metamodels
import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import com.fintech.erp.repository.AlvallalkozoiElszamolasokRepository;
import com.fintech.erp.service.criteria.AlvallalkozoiElszamolasokCriteria;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
import com.fintech.erp.service.mapper.AlvallalkozoiElszamolasokMapper;
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
 * Service for executing complex queries for {@link AlvallalkozoiElszamolasok} entities in the database.
 */
@Service
@Transactional(readOnly = true)
public class AlvallalkozoiElszamolasokQueryService extends QueryService<AlvallalkozoiElszamolasok> {

    private static final Logger LOG = LoggerFactory.getLogger(AlvallalkozoiElszamolasokQueryService.class);

    private final AlvallalkozoiElszamolasokRepository repository;
    private final AlvallalkozoiElszamolasokMapper mapper;

    public AlvallalkozoiElszamolasokQueryService(
        AlvallalkozoiElszamolasokRepository repository,
        AlvallalkozoiElszamolasokMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<AlvallalkozoiElszamolasokDTO> findByCriteria(AlvallalkozoiElszamolasokCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlvallalkozoiElszamolasok> specification = createSpecification(criteria);
        return repository.findAll(specification, page).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(AlvallalkozoiElszamolasokCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        return repository.count(createSpecification(criteria));
    }

    protected Specification<AlvallalkozoiElszamolasok> createSpecification(AlvallalkozoiElszamolasokCriteria criteria) {
        Specification<AlvallalkozoiElszamolasok> specification = Specification.where(null);
        if (criteria != null) {
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), AlvallalkozoiElszamolasok_.id),
                buildRangeSpecification(criteria.getTeljesitesiIdoszakKezdete(), AlvallalkozoiElszamolasok_.teljesitesiIdoszakKezdete),
                buildRangeSpecification(criteria.getTeljesitesiIdoszakVege(), AlvallalkozoiElszamolasok_.teljesitesiIdoszakVege),
                buildRangeSpecification(criteria.getNapokSzama(), AlvallalkozoiElszamolasok_.napokSzama),
                buildRangeSpecification(criteria.getTeljesitesIgazolasonSzereploOsszeg(), AlvallalkozoiElszamolasok_.teljesitesIgazolasonSzereploOsszeg),
                buildSpecification(criteria.getBejovoSzamlaSorszamRogzitve(), AlvallalkozoiElszamolasok_.bejovoSzamlaSorszamRogzitve),
                buildStringSpecification(criteria.getBejovoSzamlaSorszam(), AlvallalkozoiElszamolasok_.bejovoSzamlaSorszam),
                buildSpecification(criteria.getMegrendelesId(), root ->
                    root.join(AlvallalkozoiElszamolasok_.megrendeles, JoinType.LEFT).get(Megrendelesek_.id)
                )
            );
        }
        return specification;
    }
}

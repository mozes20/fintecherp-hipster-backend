package com.fintech.erp.service;

import com.fintech.erp.domain.WorkingDayTemplate;
import com.fintech.erp.domain.WorkingDayTemplate_;
import com.fintech.erp.repository.WorkingDayTemplateRepository;
import com.fintech.erp.service.criteria.WorkingDayTemplateCriteria;
import com.fintech.erp.service.dto.WorkingDayTemplateDTO;
import com.fintech.erp.service.mapper.WorkingDayTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link WorkingDayTemplate} entities
 * in the database.
 * The main input is a {@link WorkingDayTemplateCriteria} which gets converted
 * to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link WorkingDayTemplateDTO} which fulfills the
 * criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkingDayTemplateQueryService extends QueryService<WorkingDayTemplate> {

    private static final Logger LOG = LoggerFactory.getLogger(WorkingDayTemplateQueryService.class);

    private final WorkingDayTemplateRepository workingDayTemplateRepository;

    private final WorkingDayTemplateMapper workingDayTemplateMapper;

    public WorkingDayTemplateQueryService(
            WorkingDayTemplateRepository workingDayTemplateRepository,
            WorkingDayTemplateMapper workingDayTemplateMapper) {
        this.workingDayTemplateRepository = workingDayTemplateRepository;
        this.workingDayTemplateMapper = workingDayTemplateMapper;
    }

    /**
     * Return a {@link Page} of {@link WorkingDayTemplateDTO} which matches the
     * criteria from the database.
     * 
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkingDayTemplateDTO> findByCriteria(WorkingDayTemplateCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkingDayTemplate> specification = createSpecification(criteria);
        return workingDayTemplateRepository.findAll(specification, page).map(workingDayTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * 
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkingDayTemplateCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<WorkingDayTemplate> specification = createSpecification(criteria);
        return workingDayTemplateRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkingDayTemplateCriteria} to a
     * {@link Specification}
     * 
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkingDayTemplate> createSpecification(WorkingDayTemplateCriteria criteria) {
        Specification<WorkingDayTemplate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkingDayTemplate_.id));
            }
            if (criteria.getYearMonth() != null) {
                specification = specification
                        .and(buildStringSpecification(criteria.getYearMonth(), WorkingDayTemplate_.yearMonth));
            }
            if (criteria.getStatus() != null) {
                specification = specification
                        .and(buildStringSpecification(criteria.getStatus(), WorkingDayTemplate_.status));
            }
        }
        return specification;
    }
}

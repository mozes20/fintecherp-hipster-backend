package com.fintech.erp.service;

import com.fintech.erp.domain.WorkingDayTemplate;
import com.fintech.erp.repository.WorkingDayTemplateRepository;
import com.fintech.erp.service.dto.WorkingDayTemplateDTO;
import com.fintech.erp.service.mapper.WorkingDayTemplateMapper;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.fintech.erp.domain.WorkingDayTemplate}.
 */
@Service
@Transactional
public class WorkingDayTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkingDayTemplateService.class);

    private final WorkingDayTemplateRepository workingDayTemplateRepository;

    private final WorkingDayTemplateMapper workingDayTemplateMapper;

    public WorkingDayTemplateService(
            WorkingDayTemplateRepository workingDayTemplateRepository,
            WorkingDayTemplateMapper workingDayTemplateMapper) {
        this.workingDayTemplateRepository = workingDayTemplateRepository;
        this.workingDayTemplateMapper = workingDayTemplateMapper;
    }

    /**
     * Save a workingDayTemplate.
     *
     * @param workingDayTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkingDayTemplateDTO save(WorkingDayTemplateDTO workingDayTemplateDTO) {
        LOG.debug("Request to save WorkingDayTemplate : {}", workingDayTemplateDTO);

        // Ellenőrizzük, hogy már létezik-e sablon erre a hónapra
        if (workingDayTemplateDTO.getId() == null) {
            Optional<WorkingDayTemplate> existing = workingDayTemplateRepository
                    .findByYearMonth(workingDayTemplateDTO.getYearMonth());
            if (existing.isPresent()) {
                throw new BadRequestAlertException(
                        "Már létezik munkasablon erre a hónapra: " + workingDayTemplateDTO.getYearMonth(),
                        "workingDayTemplate",
                        "monthexists");
            }
        }

        WorkingDayTemplate workingDayTemplate = workingDayTemplateMapper.toEntity(workingDayTemplateDTO);
        workingDayTemplate = workingDayTemplateRepository.save(workingDayTemplate);
        return workingDayTemplateMapper.toDto(workingDayTemplate);
    }

    /**
     * Update a workingDayTemplate.
     *
     * @param workingDayTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkingDayTemplateDTO update(WorkingDayTemplateDTO workingDayTemplateDTO) {
        LOG.debug("Request to update WorkingDayTemplate : {}", workingDayTemplateDTO);
        WorkingDayTemplate workingDayTemplate = workingDayTemplateMapper.toEntity(workingDayTemplateDTO);
        workingDayTemplate = workingDayTemplateRepository.save(workingDayTemplate);
        return workingDayTemplateMapper.toDto(workingDayTemplate);
    }

    /**
     * Partially update a workingDayTemplate.
     *
     * @param workingDayTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkingDayTemplateDTO> partialUpdate(WorkingDayTemplateDTO workingDayTemplateDTO) {
        LOG.debug("Request to partially update WorkingDayTemplate : {}", workingDayTemplateDTO);

        return workingDayTemplateRepository
                .findById(workingDayTemplateDTO.getId())
                .map(existingWorkingDayTemplate -> {
                    workingDayTemplateMapper.partialUpdate(existingWorkingDayTemplate, workingDayTemplateDTO);
                    return existingWorkingDayTemplate;
                })
                .map(workingDayTemplateRepository::save)
                .map(workingDayTemplateMapper::toDto);
    }

    /**
     * Get one workingDayTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkingDayTemplateDTO> findOne(Long id) {
        LOG.debug("Request to get WorkingDayTemplate : {}", id);
        return workingDayTemplateRepository.findById(id).map(workingDayTemplateMapper::toDto);
    }

    /**
     * Get workingDayTemplate by yearMonth.
     *
     * @param yearMonth the yearMonth (e.g., "2026-01").
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkingDayTemplateDTO> findByYearMonth(String yearMonth) {
        LOG.debug("Request to get WorkingDayTemplate by yearMonth : {}", yearMonth);
        return workingDayTemplateRepository.findByYearMonth(yearMonth).map(workingDayTemplateMapper::toDto);
    }

    /**
     * Delete the workingDayTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete WorkingDayTemplate : {}", id);
        workingDayTemplateRepository.deleteById(id);
    }
}

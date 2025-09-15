package com.fintech.erp.service;

import com.fintech.erp.domain.Timesheetek;
import com.fintech.erp.repository.TimesheetekRepository;
import com.fintech.erp.service.dto.TimesheetekDTO;
import com.fintech.erp.service.mapper.TimesheetekMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Timesheetek}.
 */
@Service
@Transactional
public class TimesheetekService {

    private static final Logger LOG = LoggerFactory.getLogger(TimesheetekService.class);

    private final TimesheetekRepository timesheetekRepository;

    private final TimesheetekMapper timesheetekMapper;

    public TimesheetekService(TimesheetekRepository timesheetekRepository, TimesheetekMapper timesheetekMapper) {
        this.timesheetekRepository = timesheetekRepository;
        this.timesheetekMapper = timesheetekMapper;
    }

    /**
     * Save a timesheetek.
     *
     * @param timesheetekDTO the entity to save.
     * @return the persisted entity.
     */
    public TimesheetekDTO save(TimesheetekDTO timesheetekDTO) {
        LOG.debug("Request to save Timesheetek : {}", timesheetekDTO);
        Timesheetek timesheetek = timesheetekMapper.toEntity(timesheetekDTO);
        timesheetek = timesheetekRepository.save(timesheetek);
        return timesheetekMapper.toDto(timesheetek);
    }

    /**
     * Update a timesheetek.
     *
     * @param timesheetekDTO the entity to save.
     * @return the persisted entity.
     */
    public TimesheetekDTO update(TimesheetekDTO timesheetekDTO) {
        LOG.debug("Request to update Timesheetek : {}", timesheetekDTO);
        Timesheetek timesheetek = timesheetekMapper.toEntity(timesheetekDTO);
        timesheetek = timesheetekRepository.save(timesheetek);
        return timesheetekMapper.toDto(timesheetek);
    }

    /**
     * Partially update a timesheetek.
     *
     * @param timesheetekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TimesheetekDTO> partialUpdate(TimesheetekDTO timesheetekDTO) {
        LOG.debug("Request to partially update Timesheetek : {}", timesheetekDTO);

        return timesheetekRepository
            .findById(timesheetekDTO.getId())
            .map(existingTimesheetek -> {
                timesheetekMapper.partialUpdate(existingTimesheetek, timesheetekDTO);

                return existingTimesheetek;
            })
            .map(timesheetekRepository::save)
            .map(timesheetekMapper::toDto);
    }

    /**
     * Get one timesheetek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetekDTO> findOne(Long id) {
        LOG.debug("Request to get Timesheetek : {}", id);
        return timesheetekRepository.findById(id).map(timesheetekMapper::toDto);
    }

    /**
     * Delete the timesheetek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Timesheetek : {}", id);
        timesheetekRepository.deleteById(id);
    }
}

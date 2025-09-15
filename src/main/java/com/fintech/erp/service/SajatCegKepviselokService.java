package com.fintech.erp.service;

import com.fintech.erp.domain.SajatCegKepviselok;
import com.fintech.erp.repository.SajatCegKepviselokRepository;
import com.fintech.erp.service.dto.SajatCegKepviselokDTO;
import com.fintech.erp.service.mapper.SajatCegKepviselokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SajatCegKepviselok}.
 */
@Service
@Transactional
public class SajatCegKepviselokService {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegKepviselokService.class);

    private final SajatCegKepviselokRepository sajatCegKepviselokRepository;

    private final SajatCegKepviselokMapper sajatCegKepviselokMapper;

    public SajatCegKepviselokService(
        SajatCegKepviselokRepository sajatCegKepviselokRepository,
        SajatCegKepviselokMapper sajatCegKepviselokMapper
    ) {
        this.sajatCegKepviselokRepository = sajatCegKepviselokRepository;
        this.sajatCegKepviselokMapper = sajatCegKepviselokMapper;
    }

    /**
     * Save a sajatCegKepviselok.
     *
     * @param sajatCegKepviselokDTO the entity to save.
     * @return the persisted entity.
     */
    public SajatCegKepviselokDTO save(SajatCegKepviselokDTO sajatCegKepviselokDTO) {
        LOG.debug("Request to save SajatCegKepviselok : {}", sajatCegKepviselokDTO);
        SajatCegKepviselok sajatCegKepviselok = sajatCegKepviselokMapper.toEntity(sajatCegKepviselokDTO);
        sajatCegKepviselok = sajatCegKepviselokRepository.save(sajatCegKepviselok);
        return sajatCegKepviselokMapper.toDto(sajatCegKepviselok);
    }

    /**
     * Update a sajatCegKepviselok.
     *
     * @param sajatCegKepviselokDTO the entity to save.
     * @return the persisted entity.
     */
    public SajatCegKepviselokDTO update(SajatCegKepviselokDTO sajatCegKepviselokDTO) {
        LOG.debug("Request to update SajatCegKepviselok : {}", sajatCegKepviselokDTO);
        SajatCegKepviselok sajatCegKepviselok = sajatCegKepviselokMapper.toEntity(sajatCegKepviselokDTO);
        sajatCegKepviselok = sajatCegKepviselokRepository.save(sajatCegKepviselok);
        return sajatCegKepviselokMapper.toDto(sajatCegKepviselok);
    }

    /**
     * Partially update a sajatCegKepviselok.
     *
     * @param sajatCegKepviselokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SajatCegKepviselokDTO> partialUpdate(SajatCegKepviselokDTO sajatCegKepviselokDTO) {
        LOG.debug("Request to partially update SajatCegKepviselok : {}", sajatCegKepviselokDTO);

        return sajatCegKepviselokRepository
            .findById(sajatCegKepviselokDTO.getId())
            .map(existingSajatCegKepviselok -> {
                sajatCegKepviselokMapper.partialUpdate(existingSajatCegKepviselok, sajatCegKepviselokDTO);

                return existingSajatCegKepviselok;
            })
            .map(sajatCegKepviselokRepository::save)
            .map(sajatCegKepviselokMapper::toDto);
    }

    /**
     * Get one sajatCegKepviselok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SajatCegKepviselokDTO> findOne(Long id) {
        LOG.debug("Request to get SajatCegKepviselok : {}", id);
        return sajatCegKepviselokRepository.findById(id).map(sajatCegKepviselokMapper::toDto);
    }

    /**
     * Delete the sajatCegKepviselok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SajatCegKepviselok : {}", id);
        sajatCegKepviselokRepository.deleteById(id);
    }
}

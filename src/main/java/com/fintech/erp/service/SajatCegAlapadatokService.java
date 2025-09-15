package com.fintech.erp.service;

import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.SajatCegAlapadatokRepository;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import com.fintech.erp.service.mapper.SajatCegAlapadatokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SajatCegAlapadatok}.
 */
@Service
@Transactional
public class SajatCegAlapadatokService {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegAlapadatokService.class);

    private final SajatCegAlapadatokRepository sajatCegAlapadatokRepository;

    private final SajatCegAlapadatokMapper sajatCegAlapadatokMapper;

    public SajatCegAlapadatokService(
        SajatCegAlapadatokRepository sajatCegAlapadatokRepository,
        SajatCegAlapadatokMapper sajatCegAlapadatokMapper
    ) {
        this.sajatCegAlapadatokRepository = sajatCegAlapadatokRepository;
        this.sajatCegAlapadatokMapper = sajatCegAlapadatokMapper;
    }

    /**
     * Save a sajatCegAlapadatok.
     *
     * @param sajatCegAlapadatokDTO the entity to save.
     * @return the persisted entity.
     */
    public SajatCegAlapadatokDTO save(SajatCegAlapadatokDTO sajatCegAlapadatokDTO) {
        LOG.debug("Request to save SajatCegAlapadatok : {}", sajatCegAlapadatokDTO);
        SajatCegAlapadatok sajatCegAlapadatok = sajatCegAlapadatokMapper.toEntity(sajatCegAlapadatokDTO);
        sajatCegAlapadatok = sajatCegAlapadatokRepository.save(sajatCegAlapadatok);
        return sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);
    }

    /**
     * Update a sajatCegAlapadatok.
     *
     * @param sajatCegAlapadatokDTO the entity to save.
     * @return the persisted entity.
     */
    public SajatCegAlapadatokDTO update(SajatCegAlapadatokDTO sajatCegAlapadatokDTO) {
        LOG.debug("Request to update SajatCegAlapadatok : {}", sajatCegAlapadatokDTO);
        SajatCegAlapadatok sajatCegAlapadatok = sajatCegAlapadatokMapper.toEntity(sajatCegAlapadatokDTO);
        sajatCegAlapadatok = sajatCegAlapadatokRepository.save(sajatCegAlapadatok);
        return sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);
    }

    /**
     * Partially update a sajatCegAlapadatok.
     *
     * @param sajatCegAlapadatokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SajatCegAlapadatokDTO> partialUpdate(SajatCegAlapadatokDTO sajatCegAlapadatokDTO) {
        LOG.debug("Request to partially update SajatCegAlapadatok : {}", sajatCegAlapadatokDTO);

        return sajatCegAlapadatokRepository
            .findById(sajatCegAlapadatokDTO.getId())
            .map(existingSajatCegAlapadatok -> {
                sajatCegAlapadatokMapper.partialUpdate(existingSajatCegAlapadatok, sajatCegAlapadatokDTO);

                return existingSajatCegAlapadatok;
            })
            .map(sajatCegAlapadatokRepository::save)
            .map(sajatCegAlapadatokMapper::toDto);
    }

    /**
     * Get one sajatCegAlapadatok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SajatCegAlapadatokDTO> findOne(Long id) {
        LOG.debug("Request to get SajatCegAlapadatok : {}", id);
        return sajatCegAlapadatokRepository.findById(id).map(sajatCegAlapadatokMapper::toDto);
    }

    /**
     * Delete the sajatCegAlapadatok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SajatCegAlapadatok : {}", id);
        sajatCegAlapadatokRepository.deleteById(id);
    }
}

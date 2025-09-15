package com.fintech.erp.service;

import com.fintech.erp.domain.SajatCegTulajdonosok;
import com.fintech.erp.repository.SajatCegTulajdonosokRepository;
import com.fintech.erp.service.dto.SajatCegTulajdonosokDTO;
import com.fintech.erp.service.mapper.SajatCegTulajdonosokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SajatCegTulajdonosok}.
 */
@Service
@Transactional
public class SajatCegTulajdonosokService {

    private static final Logger LOG = LoggerFactory.getLogger(SajatCegTulajdonosokService.class);

    private final SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository;

    private final SajatCegTulajdonosokMapper sajatCegTulajdonosokMapper;

    public SajatCegTulajdonosokService(
        SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository,
        SajatCegTulajdonosokMapper sajatCegTulajdonosokMapper
    ) {
        this.sajatCegTulajdonosokRepository = sajatCegTulajdonosokRepository;
        this.sajatCegTulajdonosokMapper = sajatCegTulajdonosokMapper;
    }

    /**
     * Save a sajatCegTulajdonosok.
     *
     * @param sajatCegTulajdonosokDTO the entity to save.
     * @return the persisted entity.
     */
    public SajatCegTulajdonosokDTO save(SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO) {
        LOG.debug("Request to save SajatCegTulajdonosok : {}", sajatCegTulajdonosokDTO);
        SajatCegTulajdonosok sajatCegTulajdonosok = sajatCegTulajdonosokMapper.toEntity(sajatCegTulajdonosokDTO);
        sajatCegTulajdonosok = sajatCegTulajdonosokRepository.save(sajatCegTulajdonosok);
        return sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);
    }

    /**
     * Update a sajatCegTulajdonosok.
     *
     * @param sajatCegTulajdonosokDTO the entity to save.
     * @return the persisted entity.
     */
    public SajatCegTulajdonosokDTO update(SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO) {
        LOG.debug("Request to update SajatCegTulajdonosok : {}", sajatCegTulajdonosokDTO);
        SajatCegTulajdonosok sajatCegTulajdonosok = sajatCegTulajdonosokMapper.toEntity(sajatCegTulajdonosokDTO);
        sajatCegTulajdonosok = sajatCegTulajdonosokRepository.save(sajatCegTulajdonosok);
        return sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);
    }

    /**
     * Partially update a sajatCegTulajdonosok.
     *
     * @param sajatCegTulajdonosokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SajatCegTulajdonosokDTO> partialUpdate(SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO) {
        LOG.debug("Request to partially update SajatCegTulajdonosok : {}", sajatCegTulajdonosokDTO);

        return sajatCegTulajdonosokRepository
            .findById(sajatCegTulajdonosokDTO.getId())
            .map(existingSajatCegTulajdonosok -> {
                sajatCegTulajdonosokMapper.partialUpdate(existingSajatCegTulajdonosok, sajatCegTulajdonosokDTO);

                return existingSajatCegTulajdonosok;
            })
            .map(sajatCegTulajdonosokRepository::save)
            .map(sajatCegTulajdonosokMapper::toDto);
    }

    /**
     * Get one sajatCegTulajdonosok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SajatCegTulajdonosokDTO> findOne(Long id) {
        LOG.debug("Request to get SajatCegTulajdonosok : {}", id);
        return sajatCegTulajdonosokRepository.findById(id).map(sajatCegTulajdonosokMapper::toDto);
    }

    /**
     * Delete the sajatCegTulajdonosok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SajatCegTulajdonosok : {}", id);
        sajatCegTulajdonosokRepository.deleteById(id);
    }
}

package com.fintech.erp.service;

import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import com.fintech.erp.service.mapper.EfoFoglalkoztatasokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.EfoFoglalkoztatasok}.
 */
@Service
@Transactional
public class EfoFoglalkoztatasokService {

    private static final Logger LOG = LoggerFactory.getLogger(EfoFoglalkoztatasokService.class);

    private final EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;

    private final EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper;

    public EfoFoglalkoztatasokService(
        EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository,
        EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper
    ) {
        this.efoFoglalkoztatasokRepository = efoFoglalkoztatasokRepository;
        this.efoFoglalkoztatasokMapper = efoFoglalkoztatasokMapper;
    }

    /**
     * Save a efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the entity to save.
     * @return the persisted entity.
     */
    public EfoFoglalkoztatasokDTO save(EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO) {
        LOG.debug("Request to save EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);
        EfoFoglalkoztatasok efoFoglalkoztatasok = efoFoglalkoztatasokMapper.toEntity(efoFoglalkoztatasokDTO);
        efoFoglalkoztatasok = efoFoglalkoztatasokRepository.save(efoFoglalkoztatasok);
        return efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);
    }

    /**
     * Update a efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the entity to save.
     * @return the persisted entity.
     */
    public EfoFoglalkoztatasokDTO update(EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO) {
        LOG.debug("Request to update EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);
        EfoFoglalkoztatasok efoFoglalkoztatasok = efoFoglalkoztatasokMapper.toEntity(efoFoglalkoztatasokDTO);
        efoFoglalkoztatasok = efoFoglalkoztatasokRepository.save(efoFoglalkoztatasok);
        return efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);
    }

    /**
     * Partially update a efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EfoFoglalkoztatasokDTO> partialUpdate(EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO) {
        LOG.debug("Request to partially update EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);

        return efoFoglalkoztatasokRepository
            .findById(efoFoglalkoztatasokDTO.getId())
            .map(existingEfoFoglalkoztatasok -> {
                efoFoglalkoztatasokMapper.partialUpdate(existingEfoFoglalkoztatasok, efoFoglalkoztatasokDTO);

                return existingEfoFoglalkoztatasok;
            })
            .map(efoFoglalkoztatasokRepository::save)
            .map(efoFoglalkoztatasokMapper::toDto);
    }

    /**
     * Get one efoFoglalkoztatasok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EfoFoglalkoztatasokDTO> findOne(Long id) {
        LOG.debug("Request to get EfoFoglalkoztatasok : {}", id);
        return efoFoglalkoztatasokRepository.findById(id).map(efoFoglalkoztatasokMapper::toDto);
    }

    /**
     * Delete the efoFoglalkoztatasok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete EfoFoglalkoztatasok : {}", id);
        efoFoglalkoztatasokRepository.deleteById(id);
    }
}

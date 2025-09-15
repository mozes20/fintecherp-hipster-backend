package com.fintech.erp.service;

import com.fintech.erp.domain.Berek;
import com.fintech.erp.repository.BerekRepository;
import com.fintech.erp.service.dto.BerekDTO;
import com.fintech.erp.service.mapper.BerekMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Berek}.
 */
@Service
@Transactional
public class BerekService {

    private static final Logger LOG = LoggerFactory.getLogger(BerekService.class);

    private final BerekRepository berekRepository;

    private final BerekMapper berekMapper;

    public BerekService(BerekRepository berekRepository, BerekMapper berekMapper) {
        this.berekRepository = berekRepository;
        this.berekMapper = berekMapper;
    }

    /**
     * Save a berek.
     *
     * @param berekDTO the entity to save.
     * @return the persisted entity.
     */
    public BerekDTO save(BerekDTO berekDTO) {
        LOG.debug("Request to save Berek : {}", berekDTO);
        Berek berek = berekMapper.toEntity(berekDTO);
        berek = berekRepository.save(berek);
        return berekMapper.toDto(berek);
    }

    /**
     * Update a berek.
     *
     * @param berekDTO the entity to save.
     * @return the persisted entity.
     */
    public BerekDTO update(BerekDTO berekDTO) {
        LOG.debug("Request to update Berek : {}", berekDTO);
        Berek berek = berekMapper.toEntity(berekDTO);
        berek = berekRepository.save(berek);
        return berekMapper.toDto(berek);
    }

    /**
     * Partially update a berek.
     *
     * @param berekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BerekDTO> partialUpdate(BerekDTO berekDTO) {
        LOG.debug("Request to partially update Berek : {}", berekDTO);

        return berekRepository
            .findById(berekDTO.getId())
            .map(existingBerek -> {
                berekMapper.partialUpdate(existingBerek, berekDTO);

                return existingBerek;
            })
            .map(berekRepository::save)
            .map(berekMapper::toDto);
    }

    /**
     * Get one berek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BerekDTO> findOne(Long id) {
        LOG.debug("Request to get Berek : {}", id);
        return berekRepository.findById(id).map(berekMapper::toDto);
    }

    /**
     * Delete the berek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Berek : {}", id);
        berekRepository.deleteById(id);
    }
}

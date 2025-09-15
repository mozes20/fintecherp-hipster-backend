package com.fintech.erp.service;

import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.mapper.MegrendelesekMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Megrendelesek}.
 */
@Service
@Transactional
public class MegrendelesekService {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesekService.class);

    private final MegrendelesekRepository megrendelesekRepository;

    private final MegrendelesekMapper megrendelesekMapper;

    public MegrendelesekService(MegrendelesekRepository megrendelesekRepository, MegrendelesekMapper megrendelesekMapper) {
        this.megrendelesekRepository = megrendelesekRepository;
        this.megrendelesekMapper = megrendelesekMapper;
    }

    /**
     * Save a megrendelesek.
     *
     * @param megrendelesekDTO the entity to save.
     * @return the persisted entity.
     */
    public MegrendelesekDTO save(MegrendelesekDTO megrendelesekDTO) {
        LOG.debug("Request to save Megrendelesek : {}", megrendelesekDTO);
        Megrendelesek megrendelesek = megrendelesekMapper.toEntity(megrendelesekDTO);
        megrendelesek = megrendelesekRepository.save(megrendelesek);
        return megrendelesekMapper.toDto(megrendelesek);
    }

    /**
     * Update a megrendelesek.
     *
     * @param megrendelesekDTO the entity to save.
     * @return the persisted entity.
     */
    public MegrendelesekDTO update(MegrendelesekDTO megrendelesekDTO) {
        LOG.debug("Request to update Megrendelesek : {}", megrendelesekDTO);
        Megrendelesek megrendelesek = megrendelesekMapper.toEntity(megrendelesekDTO);
        megrendelesek = megrendelesekRepository.save(megrendelesek);
        return megrendelesekMapper.toDto(megrendelesek);
    }

    /**
     * Partially update a megrendelesek.
     *
     * @param megrendelesekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MegrendelesekDTO> partialUpdate(MegrendelesekDTO megrendelesekDTO) {
        LOG.debug("Request to partially update Megrendelesek : {}", megrendelesekDTO);

        return megrendelesekRepository
            .findById(megrendelesekDTO.getId())
            .map(existingMegrendelesek -> {
                megrendelesekMapper.partialUpdate(existingMegrendelesek, megrendelesekDTO);

                return existingMegrendelesek;
            })
            .map(megrendelesekRepository::save)
            .map(megrendelesekMapper::toDto);
    }

    /**
     * Get one megrendelesek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MegrendelesekDTO> findOne(Long id) {
        LOG.debug("Request to get Megrendelesek : {}", id);
        return megrendelesekRepository.findById(id).map(megrendelesekMapper::toDto);
    }

    /**
     * Delete the megrendelesek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Megrendelesek : {}", id);
        megrendelesekRepository.deleteById(id);
    }
}

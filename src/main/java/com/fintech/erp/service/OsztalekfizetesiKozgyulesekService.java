package com.fintech.erp.service;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.mapper.OsztalekfizetesiKozgyulesekMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesek}.
 */
@Service
@Transactional
public class OsztalekfizetesiKozgyulesekService {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekService.class);

    private final OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository;

    private final OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper;

    public OsztalekfizetesiKozgyulesekService(
        OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository,
        OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper
    ) {
        this.osztalekfizetesiKozgyulesekRepository = osztalekfizetesiKozgyulesekRepository;
        this.osztalekfizetesiKozgyulesekMapper = osztalekfizetesiKozgyulesekMapper;
    }

    /**
     * Save a osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the entity to save.
     * @return the persisted entity.
     */
    public OsztalekfizetesiKozgyulesekDTO save(OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO) {
        LOG.debug("Request to save OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekMapper.toEntity(
            osztalekfizetesiKozgyulesekDTO
        );
        osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.save(osztalekfizetesiKozgyulesek);
        return osztalekfizetesiKozgyulesekMapper.toDto(osztalekfizetesiKozgyulesek);
    }

    /**
     * Update a osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the entity to save.
     * @return the persisted entity.
     */
    public OsztalekfizetesiKozgyulesekDTO update(OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO) {
        LOG.debug("Request to update OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekMapper.toEntity(
            osztalekfizetesiKozgyulesekDTO
        );
        osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.save(osztalekfizetesiKozgyulesek);
        return osztalekfizetesiKozgyulesekMapper.toDto(osztalekfizetesiKozgyulesek);
    }

    /**
     * Partially update a osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OsztalekfizetesiKozgyulesekDTO> partialUpdate(OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO) {
        LOG.debug("Request to partially update OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);

        return osztalekfizetesiKozgyulesekRepository
            .findById(osztalekfizetesiKozgyulesekDTO.getId())
            .map(existingOsztalekfizetesiKozgyulesek -> {
                osztalekfizetesiKozgyulesekMapper.partialUpdate(existingOsztalekfizetesiKozgyulesek, osztalekfizetesiKozgyulesekDTO);

                return existingOsztalekfizetesiKozgyulesek;
            })
            .map(osztalekfizetesiKozgyulesekRepository::save)
            .map(osztalekfizetesiKozgyulesekMapper::toDto);
    }

    /**
     * Get one osztalekfizetesiKozgyulesek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OsztalekfizetesiKozgyulesekDTO> findOne(Long id) {
        LOG.debug("Request to get OsztalekfizetesiKozgyulesek : {}", id);
        return osztalekfizetesiKozgyulesekRepository.findById(id).map(osztalekfizetesiKozgyulesekMapper::toDto);
    }

    /**
     * Delete the osztalekfizetesiKozgyulesek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OsztalekfizetesiKozgyulesek : {}", id);
        osztalekfizetesiKozgyulesekRepository.deleteById(id);
    }
}

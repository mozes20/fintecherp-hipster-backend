package com.fintech.erp.service;

import com.fintech.erp.domain.MegrendelesDokumentumok;
import com.fintech.erp.repository.MegrendelesDokumentumokRepository;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.mapper.MegrendelesDokumentumokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.MegrendelesDokumentumok}.
 */
@Service
@Transactional
public class MegrendelesDokumentumokService {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDokumentumokService.class);

    private final MegrendelesDokumentumokRepository megrendelesDokumentumokRepository;

    private final MegrendelesDokumentumokMapper megrendelesDokumentumokMapper;

    public MegrendelesDokumentumokService(
        MegrendelesDokumentumokRepository megrendelesDokumentumokRepository,
        MegrendelesDokumentumokMapper megrendelesDokumentumokMapper
    ) {
        this.megrendelesDokumentumokRepository = megrendelesDokumentumokRepository;
        this.megrendelesDokumentumokMapper = megrendelesDokumentumokMapper;
    }

    /**
     * Save a megrendelesDokumentumok.
     *
     * @param megrendelesDokumentumokDTO the entity to save.
     * @return the persisted entity.
     */
    public MegrendelesDokumentumokDTO save(MegrendelesDokumentumokDTO megrendelesDokumentumokDTO) {
        LOG.debug("Request to save MegrendelesDokumentumok : {}", megrendelesDokumentumokDTO);
        MegrendelesDokumentumok megrendelesDokumentumok = megrendelesDokumentumokMapper.toEntity(megrendelesDokumentumokDTO);
        megrendelesDokumentumok = megrendelesDokumentumokRepository.save(megrendelesDokumentumok);
        return megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);
    }

    /**
     * Update a megrendelesDokumentumok.
     *
     * @param megrendelesDokumentumokDTO the entity to save.
     * @return the persisted entity.
     */
    public MegrendelesDokumentumokDTO update(MegrendelesDokumentumokDTO megrendelesDokumentumokDTO) {
        LOG.debug("Request to update MegrendelesDokumentumok : {}", megrendelesDokumentumokDTO);
        MegrendelesDokumentumok megrendelesDokumentumok = megrendelesDokumentumokMapper.toEntity(megrendelesDokumentumokDTO);
        megrendelesDokumentumok = megrendelesDokumentumokRepository.save(megrendelesDokumentumok);
        return megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);
    }

    /**
     * Partially update a megrendelesDokumentumok.
     *
     * @param megrendelesDokumentumokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MegrendelesDokumentumokDTO> partialUpdate(MegrendelesDokumentumokDTO megrendelesDokumentumokDTO) {
        LOG.debug("Request to partially update MegrendelesDokumentumok : {}", megrendelesDokumentumokDTO);

        return megrendelesDokumentumokRepository
            .findById(megrendelesDokumentumokDTO.getId())
            .map(existingMegrendelesDokumentumok -> {
                megrendelesDokumentumokMapper.partialUpdate(existingMegrendelesDokumentumok, megrendelesDokumentumokDTO);

                return existingMegrendelesDokumentumok;
            })
            .map(megrendelesDokumentumokRepository::save)
            .map(megrendelesDokumentumokMapper::toDto);
    }

    /**
     * Get one megrendelesDokumentumok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MegrendelesDokumentumokDTO> findOne(Long id) {
        LOG.debug("Request to get MegrendelesDokumentumok : {}", id);
        return megrendelesDokumentumokRepository.findById(id).map(megrendelesDokumentumokMapper::toDto);
    }

    /**
     * Delete the megrendelesDokumentumok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MegrendelesDokumentumok : {}", id);
        megrendelesDokumentumokRepository.deleteById(id);
    }
}

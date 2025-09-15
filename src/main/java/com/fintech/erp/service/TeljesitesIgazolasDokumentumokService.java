package com.fintech.erp.service;

import com.fintech.erp.domain.TeljesitesIgazolasDokumentumok;
import com.fintech.erp.repository.TeljesitesIgazolasDokumentumokRepository;
import com.fintech.erp.service.dto.TeljesitesIgazolasDokumentumokDTO;
import com.fintech.erp.service.mapper.TeljesitesIgazolasDokumentumokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.TeljesitesIgazolasDokumentumok}.
 */
@Service
@Transactional
public class TeljesitesIgazolasDokumentumokService {

    private static final Logger LOG = LoggerFactory.getLogger(TeljesitesIgazolasDokumentumokService.class);

    private final TeljesitesIgazolasDokumentumokRepository teljesitesIgazolasDokumentumokRepository;

    private final TeljesitesIgazolasDokumentumokMapper teljesitesIgazolasDokumentumokMapper;

    public TeljesitesIgazolasDokumentumokService(
        TeljesitesIgazolasDokumentumokRepository teljesitesIgazolasDokumentumokRepository,
        TeljesitesIgazolasDokumentumokMapper teljesitesIgazolasDokumentumokMapper
    ) {
        this.teljesitesIgazolasDokumentumokRepository = teljesitesIgazolasDokumentumokRepository;
        this.teljesitesIgazolasDokumentumokMapper = teljesitesIgazolasDokumentumokMapper;
    }

    /**
     * Save a teljesitesIgazolasDokumentumok.
     *
     * @param teljesitesIgazolasDokumentumokDTO the entity to save.
     * @return the persisted entity.
     */
    public TeljesitesIgazolasDokumentumokDTO save(TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO) {
        LOG.debug("Request to save TeljesitesIgazolasDokumentumok : {}", teljesitesIgazolasDokumentumokDTO);
        TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokMapper.toEntity(
            teljesitesIgazolasDokumentumokDTO
        );
        teljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.save(teljesitesIgazolasDokumentumok);
        return teljesitesIgazolasDokumentumokMapper.toDto(teljesitesIgazolasDokumentumok);
    }

    /**
     * Update a teljesitesIgazolasDokumentumok.
     *
     * @param teljesitesIgazolasDokumentumokDTO the entity to save.
     * @return the persisted entity.
     */
    public TeljesitesIgazolasDokumentumokDTO update(TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO) {
        LOG.debug("Request to update TeljesitesIgazolasDokumentumok : {}", teljesitesIgazolasDokumentumokDTO);
        TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokMapper.toEntity(
            teljesitesIgazolasDokumentumokDTO
        );
        teljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.save(teljesitesIgazolasDokumentumok);
        return teljesitesIgazolasDokumentumokMapper.toDto(teljesitesIgazolasDokumentumok);
    }

    /**
     * Partially update a teljesitesIgazolasDokumentumok.
     *
     * @param teljesitesIgazolasDokumentumokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeljesitesIgazolasDokumentumokDTO> partialUpdate(TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO) {
        LOG.debug("Request to partially update TeljesitesIgazolasDokumentumok : {}", teljesitesIgazolasDokumentumokDTO);

        return teljesitesIgazolasDokumentumokRepository
            .findById(teljesitesIgazolasDokumentumokDTO.getId())
            .map(existingTeljesitesIgazolasDokumentumok -> {
                teljesitesIgazolasDokumentumokMapper.partialUpdate(
                    existingTeljesitesIgazolasDokumentumok,
                    teljesitesIgazolasDokumentumokDTO
                );

                return existingTeljesitesIgazolasDokumentumok;
            })
            .map(teljesitesIgazolasDokumentumokRepository::save)
            .map(teljesitesIgazolasDokumentumokMapper::toDto);
    }

    /**
     * Get one teljesitesIgazolasDokumentumok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeljesitesIgazolasDokumentumokDTO> findOne(Long id) {
        LOG.debug("Request to get TeljesitesIgazolasDokumentumok : {}", id);
        return teljesitesIgazolasDokumentumokRepository.findById(id).map(teljesitesIgazolasDokumentumokMapper::toDto);
    }

    /**
     * Delete the teljesitesIgazolasDokumentumok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete TeljesitesIgazolasDokumentumok : {}", id);
        teljesitesIgazolasDokumentumokRepository.deleteById(id);
    }
}

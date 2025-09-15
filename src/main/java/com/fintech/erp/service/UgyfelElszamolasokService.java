package com.fintech.erp.service;

import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.repository.UgyfelElszamolasokRepository;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
import com.fintech.erp.service.mapper.UgyfelElszamolasokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.UgyfelElszamolasok}.
 */
@Service
@Transactional
public class UgyfelElszamolasokService {

    private static final Logger LOG = LoggerFactory.getLogger(UgyfelElszamolasokService.class);

    private final UgyfelElszamolasokRepository ugyfelElszamolasokRepository;

    private final UgyfelElszamolasokMapper ugyfelElszamolasokMapper;

    public UgyfelElszamolasokService(
        UgyfelElszamolasokRepository ugyfelElszamolasokRepository,
        UgyfelElszamolasokMapper ugyfelElszamolasokMapper
    ) {
        this.ugyfelElszamolasokRepository = ugyfelElszamolasokRepository;
        this.ugyfelElszamolasokMapper = ugyfelElszamolasokMapper;
    }

    /**
     * Save a ugyfelElszamolasok.
     *
     * @param ugyfelElszamolasokDTO the entity to save.
     * @return the persisted entity.
     */
    public UgyfelElszamolasokDTO save(UgyfelElszamolasokDTO ugyfelElszamolasokDTO) {
        LOG.debug("Request to save UgyfelElszamolasok : {}", ugyfelElszamolasokDTO);
        UgyfelElszamolasok ugyfelElszamolasok = ugyfelElszamolasokMapper.toEntity(ugyfelElszamolasokDTO);
        ugyfelElszamolasok = ugyfelElszamolasokRepository.save(ugyfelElszamolasok);
        return ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);
    }

    /**
     * Update a ugyfelElszamolasok.
     *
     * @param ugyfelElszamolasokDTO the entity to save.
     * @return the persisted entity.
     */
    public UgyfelElszamolasokDTO update(UgyfelElszamolasokDTO ugyfelElszamolasokDTO) {
        LOG.debug("Request to update UgyfelElszamolasok : {}", ugyfelElszamolasokDTO);
        UgyfelElszamolasok ugyfelElszamolasok = ugyfelElszamolasokMapper.toEntity(ugyfelElszamolasokDTO);
        ugyfelElszamolasok = ugyfelElszamolasokRepository.save(ugyfelElszamolasok);
        return ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);
    }

    /**
     * Partially update a ugyfelElszamolasok.
     *
     * @param ugyfelElszamolasokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UgyfelElszamolasokDTO> partialUpdate(UgyfelElszamolasokDTO ugyfelElszamolasokDTO) {
        LOG.debug("Request to partially update UgyfelElszamolasok : {}", ugyfelElszamolasokDTO);

        return ugyfelElszamolasokRepository
            .findById(ugyfelElszamolasokDTO.getId())
            .map(existingUgyfelElszamolasok -> {
                ugyfelElszamolasokMapper.partialUpdate(existingUgyfelElszamolasok, ugyfelElszamolasokDTO);

                return existingUgyfelElszamolasok;
            })
            .map(ugyfelElszamolasokRepository::save)
            .map(ugyfelElszamolasokMapper::toDto);
    }

    /**
     * Get one ugyfelElszamolasok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UgyfelElszamolasokDTO> findOne(Long id) {
        LOG.debug("Request to get UgyfelElszamolasok : {}", id);
        return ugyfelElszamolasokRepository.findById(id).map(ugyfelElszamolasokMapper::toDto);
    }

    /**
     * Delete the ugyfelElszamolasok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete UgyfelElszamolasok : {}", id);
        ugyfelElszamolasokRepository.deleteById(id);
    }
}

package com.fintech.erp.service;

import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import com.fintech.erp.service.mapper.SzerzodesesJogviszonyokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyok}.
 */
@Service
@Transactional
public class SzerzodesesJogviszonyokService {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyokService.class);

    private final SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    private final SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper;

    public SzerzodesesJogviszonyokService(
        SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository,
        SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper
    ) {
        this.szerzodesesJogviszonyokRepository = szerzodesesJogviszonyokRepository;
        this.szerzodesesJogviszonyokMapper = szerzodesesJogviszonyokMapper;
    }

    /**
     * Save a szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the entity to save.
     * @return the persisted entity.
     */
    public SzerzodesesJogviszonyokDTO save(SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO) {
        LOG.debug("Request to save SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);
        SzerzodesesJogviszonyok szerzodesesJogviszonyok = szerzodesesJogviszonyokMapper.toEntity(szerzodesesJogviszonyokDTO);
        szerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.save(szerzodesesJogviszonyok);
        return szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);
    }

    /**
     * Update a szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the entity to save.
     * @return the persisted entity.
     */
    public SzerzodesesJogviszonyokDTO update(SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO) {
        LOG.debug("Request to update SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);
        SzerzodesesJogviszonyok szerzodesesJogviszonyok = szerzodesesJogviszonyokMapper.toEntity(szerzodesesJogviszonyokDTO);
        szerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.save(szerzodesesJogviszonyok);
        return szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);
    }

    /**
     * Partially update a szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SzerzodesesJogviszonyokDTO> partialUpdate(SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO) {
        LOG.debug("Request to partially update SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);

        return szerzodesesJogviszonyokRepository
            .findById(szerzodesesJogviszonyokDTO.getId())
            .map(existingSzerzodesesJogviszonyok -> {
                szerzodesesJogviszonyokMapper.partialUpdate(existingSzerzodesesJogviszonyok, szerzodesesJogviszonyokDTO);

                return existingSzerzodesesJogviszonyok;
            })
            .map(szerzodesesJogviszonyokRepository::save)
            .map(szerzodesesJogviszonyokMapper::toDto);
    }

    /**
     * Get one szerzodesesJogviszonyok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SzerzodesesJogviszonyokDTO> findOne(Long id) {
        LOG.debug("Request to get SzerzodesesJogviszonyok : {}", id);
        return szerzodesesJogviszonyokRepository.findById(id).map(szerzodesesJogviszonyokMapper::toDto);
    }

    /**
     * Delete the szerzodesesJogviszonyok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SzerzodesesJogviszonyok : {}", id);
        szerzodesesJogviszonyokRepository.deleteById(id);
    }
}

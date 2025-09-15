package com.fintech.erp.service;

import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import com.fintech.erp.service.mapper.MunkavallalokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Munkavallalok}.
 */
@Service
@Transactional
public class MunkavallalokService {

    private static final Logger LOG = LoggerFactory.getLogger(MunkavallalokService.class);

    private final MunkavallalokRepository munkavallalokRepository;

    private final MunkavallalokMapper munkavallalokMapper;

    public MunkavallalokService(MunkavallalokRepository munkavallalokRepository, MunkavallalokMapper munkavallalokMapper) {
        this.munkavallalokRepository = munkavallalokRepository;
        this.munkavallalokMapper = munkavallalokMapper;
    }

    /**
     * Save a munkavallalok.
     *
     * @param munkavallalokDTO the entity to save.
     * @return the persisted entity.
     */
    public MunkavallalokDTO save(MunkavallalokDTO munkavallalokDTO) {
        LOG.debug("Request to save Munkavallalok : {}", munkavallalokDTO);
        Munkavallalok munkavallalok = munkavallalokMapper.toEntity(munkavallalokDTO);
        munkavallalok = munkavallalokRepository.save(munkavallalok);
        return munkavallalokMapper.toDto(munkavallalok);
    }

    /**
     * Update a munkavallalok.
     *
     * @param munkavallalokDTO the entity to save.
     * @return the persisted entity.
     */
    public MunkavallalokDTO update(MunkavallalokDTO munkavallalokDTO) {
        LOG.debug("Request to update Munkavallalok : {}", munkavallalokDTO);
        Munkavallalok munkavallalok = munkavallalokMapper.toEntity(munkavallalokDTO);
        munkavallalok = munkavallalokRepository.save(munkavallalok);
        return munkavallalokMapper.toDto(munkavallalok);
    }

    /**
     * Partially update a munkavallalok.
     *
     * @param munkavallalokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MunkavallalokDTO> partialUpdate(MunkavallalokDTO munkavallalokDTO) {
        LOG.debug("Request to partially update Munkavallalok : {}", munkavallalokDTO);

        return munkavallalokRepository
            .findById(munkavallalokDTO.getId())
            .map(existingMunkavallalok -> {
                munkavallalokMapper.partialUpdate(existingMunkavallalok, munkavallalokDTO);

                return existingMunkavallalok;
            })
            .map(munkavallalokRepository::save)
            .map(munkavallalokMapper::toDto);
    }

    /**
     * Get one munkavallalok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MunkavallalokDTO> findOne(Long id) {
        LOG.debug("Request to get Munkavallalok : {}", id);
        return munkavallalokRepository.findById(id).map(munkavallalokMapper::toDto);
    }

    /**
     * Delete the munkavallalok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Munkavallalok : {}", id);
        munkavallalokRepository.deleteById(id);
    }
}

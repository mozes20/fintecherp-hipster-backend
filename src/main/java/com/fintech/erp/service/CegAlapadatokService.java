package com.fintech.erp.service;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.repository.CegAlapadatokRepository;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.mapper.CegAlapadatokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.CegAlapadatok}.
 */
@Service
@Transactional
public class CegAlapadatokService {

    private static final Logger LOG = LoggerFactory.getLogger(CegAlapadatokService.class);

    private final CegAlapadatokRepository cegAlapadatokRepository;

    private final CegAlapadatokMapper cegAlapadatokMapper;

    public CegAlapadatokService(CegAlapadatokRepository cegAlapadatokRepository, CegAlapadatokMapper cegAlapadatokMapper) {
        this.cegAlapadatokRepository = cegAlapadatokRepository;
        this.cegAlapadatokMapper = cegAlapadatokMapper;
    }

    /**
     * Save a cegAlapadatok.
     *
     * @param cegAlapadatokDTO the entity to save.
     * @return the persisted entity.
     */
    public CegAlapadatokDTO save(CegAlapadatokDTO cegAlapadatokDTO) {
        LOG.debug("Request to save CegAlapadatok : {}", cegAlapadatokDTO);
        CegAlapadatok cegAlapadatok = cegAlapadatokMapper.toEntity(cegAlapadatokDTO);
        cegAlapadatok = cegAlapadatokRepository.save(cegAlapadatok);
        return cegAlapadatokMapper.toDto(cegAlapadatok);
    }

    /**
     * Update a cegAlapadatok.
     *
     * @param cegAlapadatokDTO the entity to save.
     * @return the persisted entity.
     */
    public CegAlapadatokDTO update(CegAlapadatokDTO cegAlapadatokDTO) {
        LOG.debug("Request to update CegAlapadatok : {}", cegAlapadatokDTO);
        CegAlapadatok cegAlapadatok = cegAlapadatokMapper.toEntity(cegAlapadatokDTO);
        cegAlapadatok = cegAlapadatokRepository.save(cegAlapadatok);
        return cegAlapadatokMapper.toDto(cegAlapadatok);
    }

    /**
     * Partially update a cegAlapadatok.
     *
     * @param cegAlapadatokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CegAlapadatokDTO> partialUpdate(CegAlapadatokDTO cegAlapadatokDTO) {
        LOG.debug("Request to partially update CegAlapadatok : {}", cegAlapadatokDTO);

        return cegAlapadatokRepository
            .findById(cegAlapadatokDTO.getId())
            .map(existingCegAlapadatok -> {
                cegAlapadatokMapper.partialUpdate(existingCegAlapadatok, cegAlapadatokDTO);

                return existingCegAlapadatok;
            })
            .map(cegAlapadatokRepository::save)
            .map(cegAlapadatokMapper::toDto);
    }

    /**
     * Get one cegAlapadatok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CegAlapadatokDTO> findOne(Long id) {
        LOG.debug("Request to get CegAlapadatok : {}", id);
        return cegAlapadatokRepository.findById(id).map(cegAlapadatokMapper::toDto);
    }

    /**
     * Delete the cegAlapadatok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete CegAlapadatok : {}", id);
        cegAlapadatokRepository.deleteById(id);
    }
}

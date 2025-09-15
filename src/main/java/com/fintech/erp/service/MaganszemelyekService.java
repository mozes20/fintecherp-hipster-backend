package com.fintech.erp.service;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.repository.MaganszemelyekRepository;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.mapper.MaganszemelyekMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Maganszemelyek}.
 */
@Service
@Transactional
public class MaganszemelyekService {

    private static final Logger LOG = LoggerFactory.getLogger(MaganszemelyekService.class);

    private final MaganszemelyekRepository maganszemelyekRepository;

    private final MaganszemelyekMapper maganszemelyekMapper;

    public MaganszemelyekService(MaganszemelyekRepository maganszemelyekRepository, MaganszemelyekMapper maganszemelyekMapper) {
        this.maganszemelyekRepository = maganszemelyekRepository;
        this.maganszemelyekMapper = maganszemelyekMapper;
    }

    /**
     * Save a maganszemelyek.
     *
     * @param maganszemelyekDTO the entity to save.
     * @return the persisted entity.
     */
    public MaganszemelyekDTO save(MaganszemelyekDTO maganszemelyekDTO) {
        LOG.debug("Request to save Maganszemelyek : {}", maganszemelyekDTO);
        Maganszemelyek maganszemelyek = maganszemelyekMapper.toEntity(maganszemelyekDTO);
        maganszemelyek = maganszemelyekRepository.save(maganszemelyek);
        return maganszemelyekMapper.toDto(maganszemelyek);
    }

    /**
     * Update a maganszemelyek.
     *
     * @param maganszemelyekDTO the entity to save.
     * @return the persisted entity.
     */
    public MaganszemelyekDTO update(MaganszemelyekDTO maganszemelyekDTO) {
        LOG.debug("Request to update Maganszemelyek : {}", maganszemelyekDTO);
        Maganszemelyek maganszemelyek = maganszemelyekMapper.toEntity(maganszemelyekDTO);
        maganszemelyek = maganszemelyekRepository.save(maganszemelyek);
        return maganszemelyekMapper.toDto(maganszemelyek);
    }

    /**
     * Partially update a maganszemelyek.
     *
     * @param maganszemelyekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MaganszemelyekDTO> partialUpdate(MaganszemelyekDTO maganszemelyekDTO) {
        LOG.debug("Request to partially update Maganszemelyek : {}", maganszemelyekDTO);

        return maganszemelyekRepository
            .findById(maganszemelyekDTO.getId())
            .map(existingMaganszemelyek -> {
                maganszemelyekMapper.partialUpdate(existingMaganszemelyek, maganszemelyekDTO);

                return existingMaganszemelyek;
            })
            .map(maganszemelyekRepository::save)
            .map(maganszemelyekMapper::toDto);
    }

    /**
     * Get one maganszemelyek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MaganszemelyekDTO> findOne(Long id) {
        LOG.debug("Request to get Maganszemelyek : {}", id);
        return maganszemelyekRepository.findById(id).map(maganszemelyekMapper::toDto);
    }

    /**
     * Delete the maganszemelyek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Maganszemelyek : {}", id);
        maganszemelyekRepository.deleteById(id);
    }
}

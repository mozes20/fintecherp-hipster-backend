package com.fintech.erp.service;

import com.fintech.erp.domain.PartnerCegMunkavallalok;
import com.fintech.erp.repository.PartnerCegMunkavallalokRepository;
import com.fintech.erp.service.dto.PartnerCegMunkavallalokDTO;
import com.fintech.erp.service.mapper.PartnerCegMunkavallalokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.PartnerCegMunkavallalok}.
 */
@Service
@Transactional
public class PartnerCegMunkavallalokService {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegMunkavallalokService.class);

    private final PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository;

    private final PartnerCegMunkavallalokMapper partnerCegMunkavallalokMapper;

    public PartnerCegMunkavallalokService(
        PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository,
        PartnerCegMunkavallalokMapper partnerCegMunkavallalokMapper
    ) {
        this.partnerCegMunkavallalokRepository = partnerCegMunkavallalokRepository;
        this.partnerCegMunkavallalokMapper = partnerCegMunkavallalokMapper;
    }

    /**
     * Save a partnerCegMunkavallalok.
     *
     * @param partnerCegMunkavallalokDTO the entity to save.
     * @return the persisted entity.
     */
    public PartnerCegMunkavallalokDTO save(PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO) {
        LOG.debug("Request to save PartnerCegMunkavallalok : {}", partnerCegMunkavallalokDTO);
        PartnerCegMunkavallalok partnerCegMunkavallalok = partnerCegMunkavallalokMapper.toEntity(partnerCegMunkavallalokDTO);
        partnerCegMunkavallalok = partnerCegMunkavallalokRepository.save(partnerCegMunkavallalok);
        return partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);
    }

    /**
     * Update a partnerCegMunkavallalok.
     *
     * @param partnerCegMunkavallalokDTO the entity to save.
     * @return the persisted entity.
     */
    public PartnerCegMunkavallalokDTO update(PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO) {
        LOG.debug("Request to update PartnerCegMunkavallalok : {}", partnerCegMunkavallalokDTO);
        PartnerCegMunkavallalok partnerCegMunkavallalok = partnerCegMunkavallalokMapper.toEntity(partnerCegMunkavallalokDTO);
        partnerCegMunkavallalok = partnerCegMunkavallalokRepository.save(partnerCegMunkavallalok);
        return partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);
    }

    /**
     * Partially update a partnerCegMunkavallalok.
     *
     * @param partnerCegMunkavallalokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PartnerCegMunkavallalokDTO> partialUpdate(PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO) {
        LOG.debug("Request to partially update PartnerCegMunkavallalok : {}", partnerCegMunkavallalokDTO);

        return partnerCegMunkavallalokRepository
            .findById(partnerCegMunkavallalokDTO.getId())
            .map(existingPartnerCegMunkavallalok -> {
                partnerCegMunkavallalokMapper.partialUpdate(existingPartnerCegMunkavallalok, partnerCegMunkavallalokDTO);

                return existingPartnerCegMunkavallalok;
            })
            .map(partnerCegMunkavallalokRepository::save)
            .map(partnerCegMunkavallalokMapper::toDto);
    }

    /**
     * Get one partnerCegMunkavallalok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartnerCegMunkavallalokDTO> findOne(Long id) {
        LOG.debug("Request to get PartnerCegMunkavallalok : {}", id);
        return partnerCegMunkavallalokRepository.findById(id).map(partnerCegMunkavallalokMapper::toDto);
    }

    /**
     * Delete the partnerCegMunkavallalok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PartnerCegMunkavallalok : {}", id);
        partnerCegMunkavallalokRepository.deleteById(id);
    }
}

package com.fintech.erp.service;

import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.repository.PartnerCegAdatokRepository;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import com.fintech.erp.service.mapper.PartnerCegAdatokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.PartnerCegAdatok}.
 */
@Service
@Transactional
public class PartnerCegAdatokService {

    private final com.fintech.erp.repository.CegAlapadatokRepository cegAlapadatokRepository;

    public PartnerCegAdatokService(
        PartnerCegAdatokRepository partnerCegAdatokRepository,
        PartnerCegAdatokMapper partnerCegAdatokMapper,
        com.fintech.erp.repository.CegAlapadatokRepository cegAlapadatokRepository
    ) {
        this.partnerCegAdatokRepository = partnerCegAdatokRepository;
        this.partnerCegAdatokMapper = partnerCegAdatokMapper;
        this.cegAlapadatokRepository = cegAlapadatokRepository;
    }

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegAdatokService.class);

    private final PartnerCegAdatokRepository partnerCegAdatokRepository;

    private final PartnerCegAdatokMapper partnerCegAdatokMapper;

    /**
     * Save a partnerCegAdatok.
     *
     * @param partnerCegAdatokDTO the entity to save.
     * @return the persisted entity.
     */
    public PartnerCegAdatokDTO save(PartnerCegAdatokDTO partnerCegAdatokDTO) {
        LOG.debug("Request to save PartnerCegAdatok : {}", partnerCegAdatokDTO);
        PartnerCegAdatok partnerCegAdatok = partnerCegAdatokMapper.toEntity(partnerCegAdatokDTO);
        if (partnerCegAdatokDTO.getCegId() != null) {
            partnerCegAdatok.setCeg(cegAlapadatokRepository.findById(partnerCegAdatokDTO.getCegId()).orElse(null));
        }
        partnerCegAdatok = partnerCegAdatokRepository.save(partnerCegAdatok);
        return partnerCegAdatokMapper.toDto(partnerCegAdatok);
    }

    /**
     * Update a partnerCegAdatok.
     *
     * @param partnerCegAdatokDTO the entity to save.
     * @return the persisted entity.
     */
    public PartnerCegAdatokDTO update(PartnerCegAdatokDTO partnerCegAdatokDTO) {
        LOG.debug("Request to update PartnerCegAdatok : {}", partnerCegAdatokDTO);
        PartnerCegAdatok partnerCegAdatok = partnerCegAdatokMapper.toEntity(partnerCegAdatokDTO);
        if (partnerCegAdatokDTO.getCegId() != null) {
            partnerCegAdatok.setCeg(cegAlapadatokRepository.findById(partnerCegAdatokDTO.getCegId()).orElse(null));
        }
        partnerCegAdatok = partnerCegAdatokRepository.save(partnerCegAdatok);
        return partnerCegAdatokMapper.toDto(partnerCegAdatok);
    }

    /**
     * Partially update a partnerCegAdatok.
     *
     * @param partnerCegAdatokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PartnerCegAdatokDTO> partialUpdate(PartnerCegAdatokDTO partnerCegAdatokDTO) {
        LOG.debug("Request to partially update PartnerCegAdatok : {}", partnerCegAdatokDTO);

        return partnerCegAdatokRepository
            .findById(partnerCegAdatokDTO.getId())
            .map(existingPartnerCegAdatok -> {
                partnerCegAdatokMapper.partialUpdate(existingPartnerCegAdatok, partnerCegAdatokDTO);
                if (partnerCegAdatokDTO.getCegId() != null) {
                    existingPartnerCegAdatok.setCeg(cegAlapadatokRepository.findById(partnerCegAdatokDTO.getCegId()).orElse(null));
                }
                return existingPartnerCegAdatok;
            })
            .map(partnerCegAdatokRepository::save)
            .map(partnerCegAdatokMapper::toDto);
    }

    /**
     * Get one partnerCegAdatok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartnerCegAdatokDTO> findOne(Long id) {
        LOG.debug("Request to get PartnerCegAdatok : {}", id);
        return partnerCegAdatokRepository.findById(id).map(partnerCegAdatokMapper::toDto);
    }

    /**
     * Delete the partnerCegAdatok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PartnerCegAdatok : {}", id);
        partnerCegAdatokRepository.deleteById(id);
    }
}

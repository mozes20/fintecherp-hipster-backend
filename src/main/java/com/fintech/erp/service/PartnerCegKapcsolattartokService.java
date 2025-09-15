package com.fintech.erp.service;

import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import com.fintech.erp.repository.PartnerCegKapcsolattartokRepository;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
import com.fintech.erp.service.mapper.PartnerCegKapcsolattartokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.PartnerCegKapcsolattartok}.
 */
@Service
@Transactional
public class PartnerCegKapcsolattartokService {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCegKapcsolattartokService.class);

    private final PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository;

    private final PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper;

    public PartnerCegKapcsolattartokService(
        PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository,
        PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper
    ) {
        this.partnerCegKapcsolattartokRepository = partnerCegKapcsolattartokRepository;
        this.partnerCegKapcsolattartokMapper = partnerCegKapcsolattartokMapper;
    }

    /**
     * Save a partnerCegKapcsolattartok.
     *
     * @param partnerCegKapcsolattartokDTO the entity to save.
     * @return the persisted entity.
     */
    public PartnerCegKapcsolattartokDTO save(PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO) {
        LOG.debug("Request to save PartnerCegKapcsolattartok : {}", partnerCegKapcsolattartokDTO);
        PartnerCegKapcsolattartok partnerCegKapcsolattartok = partnerCegKapcsolattartokMapper.toEntity(partnerCegKapcsolattartokDTO);
        partnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.save(partnerCegKapcsolattartok);
        return partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);
    }

    /**
     * Update a partnerCegKapcsolattartok.
     *
     * @param partnerCegKapcsolattartokDTO the entity to save.
     * @return the persisted entity.
     */
    public PartnerCegKapcsolattartokDTO update(PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO) {
        LOG.debug("Request to update PartnerCegKapcsolattartok : {}", partnerCegKapcsolattartokDTO);
        PartnerCegKapcsolattartok partnerCegKapcsolattartok = partnerCegKapcsolattartokMapper.toEntity(partnerCegKapcsolattartokDTO);
        partnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.save(partnerCegKapcsolattartok);
        return partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);
    }

    /**
     * Partially update a partnerCegKapcsolattartok.
     *
     * @param partnerCegKapcsolattartokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PartnerCegKapcsolattartokDTO> partialUpdate(PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO) {
        LOG.debug("Request to partially update PartnerCegKapcsolattartok : {}", partnerCegKapcsolattartokDTO);

        return partnerCegKapcsolattartokRepository
            .findById(partnerCegKapcsolattartokDTO.getId())
            .map(existingPartnerCegKapcsolattartok -> {
                partnerCegKapcsolattartokMapper.partialUpdate(existingPartnerCegKapcsolattartok, partnerCegKapcsolattartokDTO);

                return existingPartnerCegKapcsolattartok;
            })
            .map(partnerCegKapcsolattartokRepository::save)
            .map(partnerCegKapcsolattartokMapper::toDto);
    }

    /**
     * Get one partnerCegKapcsolattartok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartnerCegKapcsolattartokDTO> findOne(Long id) {
        LOG.debug("Request to get PartnerCegKapcsolattartok : {}", id);
        return partnerCegKapcsolattartokRepository.findById(id).map(partnerCegKapcsolattartokMapper::toDto);
    }

    /**
     * Delete the partnerCegKapcsolattartok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PartnerCegKapcsolattartok : {}", id);
        partnerCegKapcsolattartokRepository.deleteById(id);
    }
}

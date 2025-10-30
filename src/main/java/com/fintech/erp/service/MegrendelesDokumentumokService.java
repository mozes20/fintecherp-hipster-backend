package com.fintech.erp.service;

import com.fintech.erp.domain.MegrendelesDokumentumok;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import com.fintech.erp.repository.MegrendelesDokumentumokRepository;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.mapper.MegrendelesDokumentumokMapper;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
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
        applyDefaults(megrendelesDokumentumok);
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
        applyDefaults(megrendelesDokumentumok);
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
                applyDefaults(existingMegrendelesDokumentumok);
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

    @Transactional(readOnly = true)
    public java.util.List<MegrendelesDokumentumokDTO> findAllByMegrendeles(Long megrendelesId) {
        LOG.debug("Request to get MegrendelesDokumentumok for Megrendeles : {}", megrendelesId);
        return megrendelesDokumentumokRepository
            .findAllByMegrendeles_Id(megrendelesId)
            .stream()
            .sorted(Comparator.comparing(MegrendelesDokumentumok::getDokumentumAzonosito, Comparator.nullsLast(String::compareTo)))
            .map(megrendelesDokumentumokMapper::toDto)
            .collect(Collectors.toList());
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

    private void applyDefaults(MegrendelesDokumentumok entity) {
        if (entity == null) {
            return;
        }
        if (entity.getDokumentumTipusa() == null) {
            entity.setDokumentumTipusa(MegrendelesDokumentumTipus.KEZI_WORD);
        }
        if (entity.getDokumentumAzonosito() == null && entity.getMegrendeles() != null && entity.getMegrendeles().getId() != null) {
            entity.setDokumentumAzonosito(generateNextIdentifier(entity.getMegrendeles().getId()));
        }
    }

    private String generateNextIdentifier(Long megrendelesId) {
        int lastValue = megrendelesDokumentumokRepository
            .findFirstByMegrendeles_IdOrderByDokumentumAzonositoDesc(megrendelesId)
            .map(MegrendelesDokumentumok::getDokumentumAzonosito)
            .map(this::parseSequence)
            .orElse(0);
        return String.format("%03d", lastValue + 1);
    }

    private int parseSequence(String candidate) {
        if (candidate == null) {
            return 0;
        }
        try {
            return Integer.parseInt(candidate.trim());
        } catch (NumberFormatException ex) {
            LOG.warn("A dokumentum azonosító nem értelmezhető számként: {}", candidate);
            return 0;
        }
    }
}

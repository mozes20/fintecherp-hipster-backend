package com.fintech.erp.service;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekDokumentumRepository;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumDTO;
import com.fintech.erp.service.mapper.OsztalekfizetesiKozgyulesekDokumentumMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum}.
 */
@Service
@Transactional
public class OsztalekfizetesiKozgyulesekDokumentumService {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekDokumentumService.class);

    private final OsztalekfizetesiKozgyulesekDokumentumRepository dokumentumRepository;
    private final OsztalekfizetesiKozgyulesekDokumentumMapper dokumentumMapper;

    public OsztalekfizetesiKozgyulesekDokumentumService(
        OsztalekfizetesiKozgyulesekDokumentumRepository dokumentumRepository,
        OsztalekfizetesiKozgyulesekDokumentumMapper dokumentumMapper
    ) {
        this.dokumentumRepository = dokumentumRepository;
        this.dokumentumMapper = dokumentumMapper;
    }

    /**
     * Save a dokumentum.
     */
    public OsztalekfizetesiKozgyulesekDokumentumDTO save(OsztalekfizetesiKozgyulesekDokumentumDTO dto) {
        LOG.debug("Request to save OsztalekfizetesiKozgyulesekDokumentum : {}", dto);
        OsztalekfizetesiKozgyulesekDokumentum entity = dokumentumMapper.toEntity(dto);
        applyDefaults(entity);
        entity = dokumentumRepository.save(entity);
        return dokumentumMapper.toDto(entity);
    }

    /**
     * Update a dokumentum.
     */
    public OsztalekfizetesiKozgyulesekDokumentumDTO update(OsztalekfizetesiKozgyulesekDokumentumDTO dto) {
        LOG.debug("Request to update OsztalekfizetesiKozgyulesekDokumentum : {}", dto);
        OsztalekfizetesiKozgyulesekDokumentum entity = dokumentumMapper.toEntity(dto);
        applyDefaults(entity);
        entity = dokumentumRepository.save(entity);
        return dokumentumMapper.toDto(entity);
    }

    /**
     * Partially update a dokumentum.
     */
    public Optional<OsztalekfizetesiKozgyulesekDokumentumDTO> partialUpdate(OsztalekfizetesiKozgyulesekDokumentumDTO dto) {
        LOG.debug("Request to partially update OsztalekfizetesiKozgyulesekDokumentum : {}", dto);

        return dokumentumRepository
            .findById(dto.getId())
            .map(existing -> {
                dokumentumMapper.partialUpdate(existing, dto);
                applyDefaults(existing);
                return existing;
            })
            .map(dokumentumRepository::save)
            .map(dokumentumMapper::toDto);
    }

    /**
     * Get one dokumentum by id.
     */
    @Transactional(readOnly = true)
    public Optional<OsztalekfizetesiKozgyulesekDokumentumDTO> findOne(Long id) {
        LOG.debug("Request to get OsztalekfizetesiKozgyulesekDokumentum : {}", id);
        return dokumentumRepository.findById(id).map(dokumentumMapper::toDto);
    }

    /**
     * Get all documents for a given közgyűlés.
     */
    @Transactional(readOnly = true)
    public List<OsztalekfizetesiKozgyulesekDokumentumDTO> findAllByKozgyules(Long kozgyulesId) {
        LOG.debug("Request to get OsztalekfizetesiKozgyulesekDokumentum entries for kozgyules : {}", kozgyulesId);
        return dokumentumRepository
            .findAllByKozgyules_Id(kozgyulesId)
            .stream()
            .sorted(
                Comparator.comparing(OsztalekfizetesiKozgyulesekDokumentum::getDokumentumAzonosito, Comparator.nullsLast(String::compareTo))
            )
            .map(dokumentumMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Delete the dokumentum by id.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OsztalekfizetesiKozgyulesekDokumentum : {}", id);
        dokumentumRepository.deleteById(id);
    }

    /**
     * Generate the next sequential identifier ("001", "002", ...) for a given közgyűlés.
     */
    public String generateNextIdentifier(Long kozgyulesId) {
        int lastValue = dokumentumRepository
            .findFirstByKozgyules_IdOrderByDokumentumAzonositoDesc(kozgyulesId)
            .map(OsztalekfizetesiKozgyulesekDokumentum::getDokumentumAzonosito)
            .map(this::parseSequence)
            .orElse(0);
        return String.format("%03d", lastValue + 1);
    }

    // -------------------------------------------------------------------------

    private void applyDefaults(OsztalekfizetesiKozgyulesekDokumentum entity) {
        if (entity == null) return;
        if (entity.getDokumentumAzonosito() == null && entity.getKozgyules() != null && entity.getKozgyules().getId() != null) {
            entity.setDokumentumAzonosito(generateNextIdentifier(entity.getKozgyules().getId()));
        }
    }

    private int parseSequence(String candidate) {
        if (candidate == null) return 0;
        try {
            return Integer.parseInt(candidate.trim());
        } catch (NumberFormatException ex) {
            LOG.warn("A dokumentum azonosító nem értelmezhető számként: {}", candidate);
            return 0;
        }
    }
}

package com.fintech.erp.service;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentumTemplate;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekDokumentumTemplateRepository;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumTemplateDTO;
import com.fintech.erp.service.mapper.OsztalekfizetesiKozgyulesekDokumentumTemplateMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for {@link OsztalekfizetesiKozgyulesekDokumentumTemplate}.
 */
@Service
@Transactional
public class OsztalekfizetesiKozgyulesekDokumentumTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekDokumentumTemplateService.class);

    private final OsztalekfizetesiKozgyulesekDokumentumTemplateRepository templateRepository;
    private final OsztalekfizetesiKozgyulesekDokumentumTemplateMapper templateMapper;

    public OsztalekfizetesiKozgyulesekDokumentumTemplateService(
        OsztalekfizetesiKozgyulesekDokumentumTemplateRepository templateRepository,
        OsztalekfizetesiKozgyulesekDokumentumTemplateMapper templateMapper
    ) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplateDTO save(OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto) {
        LOG.debug("Request to save OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", dto);
        OsztalekfizetesiKozgyulesekDokumentumTemplate entity = templateMapper.toEntity(dto);
        entity.setUtolsoModositas(Instant.now());
        entity = templateRepository.save(entity);
        return templateMapper.toDto(entity);
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplateDTO update(OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto) {
        LOG.debug("Request to update OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", dto);
        return save(dto);
    }

    public Optional<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> partialUpdate(OsztalekfizetesiKozgyulesekDokumentumTemplateDTO dto) {
        LOG.debug("Request to partially update OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", dto);
        return templateRepository
            .findById(dto.getId())
            .map(existing -> {
                templateMapper.partialUpdate(existing, dto);
                existing.setUtolsoModositas(Instant.now());
                return existing;
            })
            .map(templateRepository::save)
            .map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all OsztalekfizetesiKozgyulesekDokumentumTemplates");
        return templateRepository.findAll(pageable).map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO> findOne(Long id) {
        LOG.debug("Request to get OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", id);
        return templateRepository.findById(id).map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OsztalekfizetesiKozgyulesekDokumentumTemplate> findLatestByDokumentumTipus(String dokumentumTipusa) {
        LOG.debug("Request to get latest template for dokumentumTipusa : {}", dokumentumTipusa);
        if (dokumentumTipusa == null || dokumentumTipusa.isBlank()) {
            return Optional.empty();
        }
        return templateRepository.findFirstByDokumentumTipusaOrderByUtolsoModositasDesc(dokumentumTipusa.trim());
    }

    public void delete(Long id) {
        LOG.debug("Request to delete OsztalekfizetesiKozgyulesekDokumentumTemplate : {}", id);
        templateRepository.deleteById(id);
    }
}

package com.fintech.erp.service;

import com.fintech.erp.domain.EfoDokumentumTemplate;
import com.fintech.erp.repository.EfoDokumentumTemplateRepository;
import com.fintech.erp.service.dto.EfoDokumentumTemplateDTO;
import com.fintech.erp.service.mapper.EfoDokumentumTemplateMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for {@link com.fintech.erp.domain.EfoDokumentumTemplate}.
 */
@Service
@Transactional
public class EfoDokumentumTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(EfoDokumentumTemplateService.class);

    private final EfoDokumentumTemplateRepository templateRepository;
    private final EfoDokumentumTemplateMapper templateMapper;

    public EfoDokumentumTemplateService(EfoDokumentumTemplateRepository templateRepository, EfoDokumentumTemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    public EfoDokumentumTemplateDTO save(EfoDokumentumTemplateDTO dto) {
        LOG.debug("Request to save EfoDokumentumTemplate : {}", dto);
        EfoDokumentumTemplate entity = templateMapper.toEntity(dto);
        entity.setUtolsoModositas(Instant.now());
        entity = templateRepository.save(entity);
        return templateMapper.toDto(entity);
    }

    public EfoDokumentumTemplateDTO update(EfoDokumentumTemplateDTO dto) {
        LOG.debug("Request to update EfoDokumentumTemplate : {}", dto);
        return save(dto);
    }

    public Optional<EfoDokumentumTemplateDTO> partialUpdate(EfoDokumentumTemplateDTO dto) {
        LOG.debug("Request to partially update EfoDokumentumTemplate : {}", dto);
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
    public Page<EfoDokumentumTemplateDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all EfoDokumentumTemplates");
        return templateRepository.findAll(pageable).map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EfoDokumentumTemplateDTO> findOne(Long id) {
        LOG.debug("Request to get EfoDokumentumTemplate : {}", id);
        return templateRepository.findById(id).map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EfoDokumentumTemplate> findLatestForDokumentumTipus(String dokumentumTipus) {
        LOG.debug("Request to get latest EFO template for dokumentumTipus : {}", dokumentumTipus);
        if (dokumentumTipus == null || dokumentumTipus.isBlank()) {
            return Optional.empty();
        }
        return templateRepository.findFirstByDokumentumTipusOrderByUtolsoModositasDesc(dokumentumTipus.trim());
    }

    public void delete(Long id) {
        LOG.debug("Request to delete EfoDokumentumTemplate : {}", id);
        templateRepository.deleteById(id);
    }
}

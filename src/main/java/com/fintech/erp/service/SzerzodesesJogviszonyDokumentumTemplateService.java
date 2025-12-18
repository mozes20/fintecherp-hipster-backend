package com.fintech.erp.service;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate;
import com.fintech.erp.repository.SzerzodesesJogviszonyDokumentumTemplateRepository;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumTemplateDTO;
import com.fintech.erp.service.mapper.SzerzodesesJogviszonyDokumentumTemplateMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate}.
 */
@Service
@Transactional
public class SzerzodesesJogviszonyDokumentumTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyDokumentumTemplateService.class);

    private final SzerzodesesJogviszonyDokumentumTemplateRepository templateRepository;

    private final SzerzodesesJogviszonyDokumentumTemplateMapper templateMapper;

    public SzerzodesesJogviszonyDokumentumTemplateService(
        SzerzodesesJogviszonyDokumentumTemplateRepository templateRepository,
        SzerzodesesJogviszonyDokumentumTemplateMapper templateMapper
    ) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    public SzerzodesesJogviszonyDokumentumTemplateDTO save(SzerzodesesJogviszonyDokumentumTemplateDTO dto) {
        LOG.debug("Request to save SzerzodesesJogviszonyDokumentumTemplate : {}", dto);
        SzerzodesesJogviszonyDokumentumTemplate entity = templateMapper.toEntity(dto);
        entity.setUtolsoModositas(Instant.now());
        entity = templateRepository.save(entity);
        return templateMapper.toDto(entity);
    }

    public SzerzodesesJogviszonyDokumentumTemplateDTO update(SzerzodesesJogviszonyDokumentumTemplateDTO dto) {
        LOG.debug("Request to update SzerzodesesJogviszonyDokumentumTemplate : {}", dto);
        return save(dto);
    }

    public Optional<SzerzodesesJogviszonyDokumentumTemplateDTO> partialUpdate(SzerzodesesJogviszonyDokumentumTemplateDTO dto) {
        LOG.debug("Request to partially update SzerzodesesJogviszonyDokumentumTemplate : {}", dto);

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
    public Page<SzerzodesesJogviszonyDokumentumTemplateDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SzerzodesesJogviszonyDokumentumTemplates");
        return templateRepository.findAll(pageable).map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SzerzodesesJogviszonyDokumentumTemplateDTO> findOne(Long id) {
        LOG.debug("Request to get SzerzodesesJogviszonyDokumentumTemplate : {}", id);
        return templateRepository.findById(id).map(templateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SzerzodesesJogviszonyDokumentumTemplate> findLatestForDokumentumTipus(String dokumentumTipus) {
        LOG.debug("Request to get latest template for dokumentumTipus : {}", dokumentumTipus);
        if (dokumentumTipus == null || dokumentumTipus.isBlank()) {
            return Optional.empty();
        }
        return templateRepository.findFirstByDokumentumTipusOrderByUtolsoModositasDesc(dokumentumTipus.trim());
    }

    public void delete(Long id) {
        LOG.debug("Request to delete SzerzodesesJogviszonyDokumentumTemplate : {}", id);
        templateRepository.deleteById(id);
    }
}

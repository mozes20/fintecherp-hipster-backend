package com.fintech.erp.service;

import com.fintech.erp.domain.MegrendelesDokumentumTemplate;
import com.fintech.erp.repository.MegrendelesDokumentumTemplateRepository;
import com.fintech.erp.service.dto.MegrendelesDokumentumTemplateDTO;
import com.fintech.erp.service.mapper.MegrendelesDokumentumTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.MegrendelesDokumentumTemplate}.
 */
@Service
@Transactional
public class MegrendelesDokumentumTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesDokumentumTemplateService.class);

    private final MegrendelesDokumentumTemplateRepository megrendelesDokumentumTemplateRepository;

    private final MegrendelesDokumentumTemplateMapper megrendelesDokumentumTemplateMapper;

    public MegrendelesDokumentumTemplateService(
        MegrendelesDokumentumTemplateRepository megrendelesDokumentumTemplateRepository,
        MegrendelesDokumentumTemplateMapper megrendelesDokumentumTemplateMapper
    ) {
        this.megrendelesDokumentumTemplateRepository = megrendelesDokumentumTemplateRepository;
        this.megrendelesDokumentumTemplateMapper = megrendelesDokumentumTemplateMapper;
    }

    public MegrendelesDokumentumTemplateDTO save(MegrendelesDokumentumTemplateDTO dto) {
        LOG.debug("Request to save MegrendelesDokumentumTemplate : {}", dto);
        MegrendelesDokumentumTemplate entity = megrendelesDokumentumTemplateMapper.toEntity(dto);
        entity = megrendelesDokumentumTemplateRepository.save(entity);
        return megrendelesDokumentumTemplateMapper.toDto(entity);
    }

    public MegrendelesDokumentumTemplateDTO update(MegrendelesDokumentumTemplateDTO dto) {
        LOG.debug("Request to update MegrendelesDokumentumTemplate : {}", dto);
        MegrendelesDokumentumTemplate entity = megrendelesDokumentumTemplateMapper.toEntity(dto);
        entity = megrendelesDokumentumTemplateRepository.save(entity);
        return megrendelesDokumentumTemplateMapper.toDto(entity);
    }

    public Optional<MegrendelesDokumentumTemplateDTO> partialUpdate(MegrendelesDokumentumTemplateDTO dto) {
        LOG.debug("Request to partially update MegrendelesDokumentumTemplate : {}", dto);

        return megrendelesDokumentumTemplateRepository
            .findById(dto.getId())
            .map(existing -> {
                megrendelesDokumentumTemplateMapper.partialUpdate(existing, dto);
                return existing;
            })
            .map(megrendelesDokumentumTemplateRepository::save)
            .map(megrendelesDokumentumTemplateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<MegrendelesDokumentumTemplateDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MegrendelesDokumentumTemplates");
        return megrendelesDokumentumTemplateRepository.findAll(pageable).map(megrendelesDokumentumTemplateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<MegrendelesDokumentumTemplateDTO> findOne(Long id) {
        LOG.debug("Request to get MegrendelesDokumentumTemplate : {}", id);
        return megrendelesDokumentumTemplateRepository.findById(id).map(megrendelesDokumentumTemplateMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete MegrendelesDokumentumTemplate : {}", id);
        megrendelesDokumentumTemplateRepository.deleteById(id);
    }
}

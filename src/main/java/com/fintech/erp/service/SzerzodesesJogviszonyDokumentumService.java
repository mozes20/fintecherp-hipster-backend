package com.fintech.erp.service;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum;
import com.fintech.erp.repository.SzerzodesesJogviszonyDokumentumRepository;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumDTO;
import com.fintech.erp.service.mapper.SzerzodesesJogviszonyDokumentumMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum}.
 */
@Service
@Transactional
public class SzerzodesesJogviszonyDokumentumService {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyDokumentumService.class);

    private final SzerzodesesJogviszonyDokumentumRepository dokumentumRepository;

    private final SzerzodesesJogviszonyDokumentumMapper dokumentumMapper;

    public SzerzodesesJogviszonyDokumentumService(
        SzerzodesesJogviszonyDokumentumRepository dokumentumRepository,
        SzerzodesesJogviszonyDokumentumMapper dokumentumMapper
    ) {
        this.dokumentumRepository = dokumentumRepository;
        this.dokumentumMapper = dokumentumMapper;
    }

    public SzerzodesesJogviszonyDokumentumDTO save(SzerzodesesJogviszonyDokumentumDTO dto) {
        LOG.debug("Request to save SzerzodesesJogviszonyDokumentum : {}", dto);
        SzerzodesesJogviszonyDokumentum entity = dokumentumMapper.toEntity(dto);
        if (entity.getFeltoltesIdeje() == null) {
            entity.setFeltoltesIdeje(Instant.now());
        }
        entity = dokumentumRepository.save(entity);
        return dokumentumMapper.toDto(entity);
    }

    public SzerzodesesJogviszonyDokumentumDTO update(SzerzodesesJogviszonyDokumentumDTO dto) {
        LOG.debug("Request to update SzerzodesesJogviszonyDokumentum : {}", dto);
        return save(dto);
    }

    public Optional<SzerzodesesJogviszonyDokumentumDTO> partialUpdate(SzerzodesesJogviszonyDokumentumDTO dto) {
        LOG.debug("Request to partially update SzerzodesesJogviszonyDokumentum : {}", dto);

        return dokumentumRepository
            .findById(dto.getId())
            .map(existing -> {
                dokumentumMapper.partialUpdate(existing, dto);
                if (dto.getFeltoltesIdeje() == null) {
                    existing.setFeltoltesIdeje(existing.getFeltoltesIdeje() != null ? existing.getFeltoltesIdeje() : Instant.now());
                }
                return existing;
            })
            .map(dokumentumRepository::save)
            .map(dokumentumMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SzerzodesesJogviszonyDokumentumDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SzerzodesesJogviszonyDokumentumok");
        return dokumentumRepository.findAll(pageable).map(dokumentumMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SzerzodesesJogviszonyDokumentumDTO> findAllBySzerzodesesJogviszony(Long szerzodesesJogviszonyId) {
        LOG.debug("Request to get documents for Szerzodeses Jogviszony : {}", szerzodesesJogviszonyId);
        return dokumentumRepository
            .findAllBySzerzodesesJogviszony_Id(szerzodesesJogviszonyId)
            .stream()
            .map(dokumentumMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<SzerzodesesJogviszonyDokumentumDTO> findOne(Long id) {
        LOG.debug("Request to get SzerzodesesJogviszonyDokumentum : {}", id);
        return dokumentumRepository.findById(id).map(dokumentumMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete SzerzodesesJogviszonyDokumentum : {}", id);
        dokumentumRepository.deleteById(id);
    }
}

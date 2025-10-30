package com.fintech.erp.service;

import com.fintech.erp.domain.SzerzodesDokumentumTipus;
import com.fintech.erp.repository.SzerzodesDokumentumTipusRepository;
import com.fintech.erp.service.dto.SzerzodesDokumentumTipusDTO;
import com.fintech.erp.service.mapper.SzerzodesDokumentumTipusMapper;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SzerzodesDokumentumTipus}.
 */
@Service
@Transactional
public class SzerzodesDokumentumTipusService {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesDokumentumTipusService.class);

    private final SzerzodesDokumentumTipusRepository szerzodesDokumentumTipusRepository;

    private final SzerzodesDokumentumTipusMapper szerzodesDokumentumTipusMapper;

    public SzerzodesDokumentumTipusService(
        SzerzodesDokumentumTipusRepository szerzodesDokumentumTipusRepository,
        SzerzodesDokumentumTipusMapper szerzodesDokumentumTipusMapper
    ) {
        this.szerzodesDokumentumTipusRepository = szerzodesDokumentumTipusRepository;
        this.szerzodesDokumentumTipusMapper = szerzodesDokumentumTipusMapper;
    }

    public SzerzodesDokumentumTipusDTO save(SzerzodesDokumentumTipusDTO szerzodesDokumentumTipusDTO) {
        LOG.debug("Request to save SzerzodesDokumentumTipus : {}", szerzodesDokumentumTipusDTO);
        Optional<SzerzodesDokumentumTipus> existing = szerzodesDokumentumTipusRepository.findOneByNevIgnoreCase(
            szerzodesDokumentumTipusDTO.getNev()
        );
        existing
            .map(SzerzodesDokumentumTipus::getId)
            .filter(foundId -> !Objects.equals(foundId, szerzodesDokumentumTipusDTO.getId()))
            .ifPresent(foundId -> {
                throw new IllegalArgumentException("Már létezik ilyen dokumentumtípus név");
            });
        SzerzodesDokumentumTipus szerzodesDokumentumTipus = szerzodesDokumentumTipusMapper.toEntity(szerzodesDokumentumTipusDTO);
        szerzodesDokumentumTipus = szerzodesDokumentumTipusRepository.save(szerzodesDokumentumTipus);
        return szerzodesDokumentumTipusMapper.toDto(szerzodesDokumentumTipus);
    }

    public SzerzodesDokumentumTipusDTO update(SzerzodesDokumentumTipusDTO szerzodesDokumentumTipusDTO) {
        LOG.debug("Request to update SzerzodesDokumentumTipus : {}", szerzodesDokumentumTipusDTO);
        return save(szerzodesDokumentumTipusDTO);
    }

    public Optional<SzerzodesDokumentumTipusDTO> partialUpdate(SzerzodesDokumentumTipusDTO szerzodesDokumentumTipusDTO) {
        LOG.debug("Request to partially update SzerzodesDokumentumTipus : {}", szerzodesDokumentumTipusDTO);

        return szerzodesDokumentumTipusRepository
            .findById(szerzodesDokumentumTipusDTO.getId())
            .map(existing -> {
                szerzodesDokumentumTipusMapper.partialUpdate(existing, szerzodesDokumentumTipusDTO);
                return existing;
            })
            .map(szerzodesDokumentumTipusRepository::save)
            .map(szerzodesDokumentumTipusMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SzerzodesDokumentumTipusDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SzerzodesDokumentumTipus");
        return szerzodesDokumentumTipusRepository.findAll(pageable).map(szerzodesDokumentumTipusMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SzerzodesDokumentumTipusDTO> findOne(Long id) {
        LOG.debug("Request to get SzerzodesDokumentumTipus : {}", id);
        return szerzodesDokumentumTipusRepository.findById(id).map(szerzodesDokumentumTipusMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete SzerzodesDokumentumTipus : {}", id);
        szerzodesDokumentumTipusRepository.deleteById(id);
    }
}

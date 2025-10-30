package com.fintech.erp.service;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.CegAlapadatokRepository;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import com.fintech.erp.service.mapper.SzerzodesesJogviszonyokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.SzerzodesesJogviszonyok}.
 */
@Service
@Transactional
public class SzerzodesesJogviszonyokService {

    private static final Logger LOG = LoggerFactory.getLogger(SzerzodesesJogviszonyokService.class);

    private final SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    private final SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper;

    private final CegAlapadatokRepository cegAlapadatokRepository;

    public SzerzodesesJogviszonyokService(
        SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository,
        SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper,
        CegAlapadatokRepository cegAlapadatokRepository
    ) {
        this.szerzodesesJogviszonyokRepository = szerzodesesJogviszonyokRepository;
        this.szerzodesesJogviszonyokMapper = szerzodesesJogviszonyokMapper;
        this.cegAlapadatokRepository = cegAlapadatokRepository;
    }

    /**
     * Save a szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the entity to save.
     * @return the persisted entity.
     */
    public SzerzodesesJogviszonyokDTO save(SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO) {
        LOG.debug("Request to save SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);
        validateDates(szerzodesesJogviszonyokDTO);
        // Auto-generate szerzodesAzonosito if not provided
        if (szerzodesesJogviszonyokDTO.getSzerzodesAzonosito() == null || szerzodesesJogviszonyokDTO.getSzerzodesAzonosito().isBlank()) {
            String sajatCegAzonosito = null;
            String partnerCegAzonosito = null;
            // Try to get both cég rövid azonosító from the related companies
            if (
                szerzodesesJogviszonyokDTO.getMegrendeloCeg() != null &&
                szerzodesesJogviszonyokDTO.getMegrendeloCeg().getCegRovidAzonosito() != null
            ) {
                sajatCegAzonosito = szerzodesesJogviszonyokDTO.getMegrendeloCeg().getCegRovidAzonosito();
            }
            if (
                szerzodesesJogviszonyokDTO.getVallalkozoCeg() != null &&
                szerzodesesJogviszonyokDTO.getVallalkozoCeg().getCegRovidAzonosito() != null
            ) {
                partnerCegAzonosito = szerzodesesJogviszonyokDTO.getVallalkozoCeg().getCegRovidAzonosito();
            }
            // Fallback: try to get from IDs if DTOs are not present (requires repository lookup)
            if (szerzodesesJogviszonyokDTO.getMegrendeloCegId() != null && sajatCegAzonosito == null) {
                sajatCegAzonosito = cegAlapadatokRepository
                    .findById(szerzodesesJogviszonyokDTO.getMegrendeloCegId())
                    .map(CegAlapadatok::getCegRovidAzonosito)
                    .orElse(null);
            }
            if (szerzodesesJogviszonyokDTO.getVallalkozoCegId() != null && partnerCegAzonosito == null) {
                partnerCegAzonosito = cegAlapadatokRepository
                    .findById(szerzodesesJogviszonyokDTO.getVallalkozoCegId())
                    .map(CegAlapadatok::getCegRovidAzonosito)
                    .orElse(null);
            }
            // If either is still null, set to "UNKNOWN"
            if (sajatCegAzonosito == null) sajatCegAzonosito = "UNKNOWN";
            if (partnerCegAzonosito == null) partnerCegAzonosito = "UNKNOWN";
            // Find the next sequence number for this pair
            long count = szerzodesesJogviszonyokRepository.countByMegrendeloCeg_CegRovidAzonositoAndVallalkozoCeg_CegRovidAzonosito(
                sajatCegAzonosito,
                partnerCegAzonosito
            );
            String sorszam = String.format("%03d", count + 1);
            String autoAzonosito = sajatCegAzonosito + "/" + partnerCegAzonosito + "/" + sorszam;
            szerzodesesJogviszonyokDTO.setSzerzodesAzonosito(autoAzonosito);
        }
        SzerzodesesJogviszonyok szerzodesesJogviszonyok = szerzodesesJogviszonyokMapper.toEntity(szerzodesesJogviszonyokDTO);
        szerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.save(szerzodesesJogviszonyok);
        return szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);
    }

    /**
     * Update a szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the entity to save.
     * @return the persisted entity.
     */
    public SzerzodesesJogviszonyokDTO update(SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO) {
        LOG.debug("Request to update SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);
        validateDates(szerzodesesJogviszonyokDTO);
        SzerzodesesJogviszonyok szerzodesesJogviszonyok = szerzodesesJogviszonyokMapper.toEntity(szerzodesesJogviszonyokDTO);
        szerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.save(szerzodesesJogviszonyok);
        return szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);
    }

    /**
     * Partially update a szerzodesesJogviszonyok.
     *
     * @param szerzodesesJogviszonyokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SzerzodesesJogviszonyokDTO> partialUpdate(SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO) {
        LOG.debug("Request to partially update SzerzodesesJogviszonyok : {}", szerzodesesJogviszonyokDTO);

        return szerzodesesJogviszonyokRepository
            .findById(szerzodesesJogviszonyokDTO.getId())
            .map(existingSzerzodesesJogviszonyok -> {
                if (
                    szerzodesesJogviszonyokDTO.getJogviszonyKezdete() != null &&
                    szerzodesesJogviszonyokDTO.getJogviszonyLejarata() != null &&
                    !szerzodesesJogviszonyokDTO.getJogviszonyLejarata().isAfter(szerzodesesJogviszonyokDTO.getJogviszonyKezdete())
                ) {
                    throw new IllegalArgumentException("A jogviszony lejáratának későbbinek kell lennie, mint a kezdő dátum");
                }
                szerzodesesJogviszonyokMapper.partialUpdate(existingSzerzodesesJogviszonyok, szerzodesesJogviszonyokDTO);

                return existingSzerzodesesJogviszonyok;
            })
            .map(szerzodesesJogviszonyokRepository::save)
            .map(szerzodesesJogviszonyokMapper::toDto);
    }

    /**
     * Get one szerzodesesJogviszonyok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SzerzodesesJogviszonyokDTO> findOne(Long id) {
        LOG.debug("Request to get SzerzodesesJogviszonyok : {}", id);
        return szerzodesesJogviszonyokRepository.findById(id).map(szerzodesesJogviszonyokMapper::toDto);
    }

    /**
     * Delete the szerzodesesJogviszonyok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SzerzodesesJogviszonyok : {}", id);
        szerzodesesJogviszonyokRepository.deleteById(id);
    }

    private void validateDates(SzerzodesesJogviszonyokDTO dto) {
        if (
            dto.getJogviszonyKezdete() != null &&
            dto.getJogviszonyLejarata() != null &&
            !dto.getJogviszonyLejarata().isAfter(dto.getJogviszonyKezdete())
        ) {
            throw new IllegalArgumentException("A jogviszony lejáratának későbbinek kell lennie, mint a kezdő dátum");
        }
    }
}

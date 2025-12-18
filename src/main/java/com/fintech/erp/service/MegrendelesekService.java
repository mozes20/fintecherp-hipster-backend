package com.fintech.erp.service;

import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.mapper.MegrendelesekMapper;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Megrendelesek}.
 */
@Service
@Transactional
public class MegrendelesekService {

    private static final String ENTITY_NAME = "megrendelesek";

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesekService.class);

    private final MegrendelesekRepository megrendelesekRepository;

    private final MegrendelesekMapper megrendelesekMapper;

    private final SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    private final MunkakorokRepository munkakorokRepository;

    public MegrendelesekService(
        MegrendelesekRepository megrendelesekRepository,
        MegrendelesekMapper megrendelesekMapper,
        SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository,
        MunkakorokRepository munkakorokRepository
    ) {
        this.megrendelesekRepository = megrendelesekRepository;
        this.megrendelesekMapper = megrendelesekMapper;
        this.szerzodesesJogviszonyokRepository = szerzodesesJogviszonyokRepository;
        this.munkakorokRepository = munkakorokRepository;
    }

    /**
     * Save a megrendelesek.
     *
     * @param megrendelesekDTO the entity to save.
     * @return the persisted entity.
     */
    public MegrendelesekDTO save(MegrendelesekDTO megrendelesekDTO) {
        LOG.debug("Request to save Megrendelesek : {}", megrendelesekDTO);
        Megrendelesek megrendelesek = megrendelesekMapper.toEntity(megrendelesekDTO);
        applyMunkakorReference(megrendelesek, megrendelesekDTO, false);
        SzerzodesesJogviszonyok jogviszony = resolveSzerzodesesJogviszony(megrendelesek);
        applyDefaults(megrendelesek);
        validateBusinessRules(megrendelesek, jogviszony);
        ensureOrderNumber(megrendelesek, jogviszony);
        megrendelesek = megrendelesekRepository.save(megrendelesek);
        return megrendelesekMapper.toDto(megrendelesek);
    }

    /**
     * Update a megrendelesek.
     *
     * @param megrendelesekDTO the entity to save.
     * @return the persisted entity.
     */
    public MegrendelesekDTO update(MegrendelesekDTO megrendelesekDTO) {
        LOG.debug("Request to update Megrendelesek : {}", megrendelesekDTO);
        Megrendelesek megrendelesek = megrendelesekMapper.toEntity(megrendelesekDTO);
        applyMunkakorReference(megrendelesek, megrendelesekDTO, false);
        SzerzodesesJogviszonyok jogviszony = resolveSzerzodesesJogviszony(megrendelesek);
        applyDefaults(megrendelesek);
        validateBusinessRules(megrendelesek, jogviszony);
        ensureOrderNumber(megrendelesek, jogviszony);
        megrendelesek = megrendelesekRepository.save(megrendelesek);
        return megrendelesekMapper.toDto(megrendelesek);
    }

    /**
     * Partially update a megrendelesek.
     *
     * @param megrendelesekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MegrendelesekDTO> partialUpdate(MegrendelesekDTO megrendelesekDTO) {
        LOG.debug("Request to partially update Megrendelesek : {}", megrendelesekDTO);

        return megrendelesekRepository
            .findById(megrendelesekDTO.getId())
            .map(existingMegrendelesek -> {
                megrendelesekMapper.partialUpdate(existingMegrendelesek, megrendelesekDTO);
                applyMunkakorReference(existingMegrendelesek, megrendelesekDTO, true);
                SzerzodesesJogviszonyok jogviszony = resolveSzerzodesesJogviszony(existingMegrendelesek);
                applyDefaults(existingMegrendelesek);
                validateBusinessRules(existingMegrendelesek, jogviszony);
                ensureOrderNumber(existingMegrendelesek, jogviszony);

                return existingMegrendelesek;
            })
            .map(megrendelesekRepository::save)
            .map(megrendelesekMapper::toDto);
    }

    /**
     * Get one megrendelesek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MegrendelesekDTO> findOne(Long id) {
        LOG.debug("Request to get Megrendelesek : {}", id);
        return megrendelesekRepository.findById(id).map(megrendelesekMapper::toDto);
    }

    /**
     * Delete the megrendelesek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Megrendelesek : {}", id);
        megrendelesekRepository.deleteById(id);
    }

    private void applyDefaults(Megrendelesek entity) {
        if (entity.getMegrendelesDokumentumGeneralta() == null) {
            entity.setMegrendelesDokumentumGeneralta(MegrendelesDokumentumEredet.KEZI);
        }
        if (entity.getMegrendelesDatuma() == null) {
            entity.setMegrendelesDatuma(LocalDate.now());
        }
    }

    private SzerzodesesJogviszonyok resolveSzerzodesesJogviszony(Megrendelesek entity) {
        if (entity.getSzerzodesesJogviszony() == null || entity.getSzerzodesesJogviszony().getId() == null) {
            throw new BadRequestAlertException("A szerződéses jogviszony megadása kötelező", ENTITY_NAME, "jogviszonyhiany");
        }
        SzerzodesesJogviszonyok jelenlegi = entity.getSzerzodesesJogviszony();
        if (jelenlegi.getSzerzodesAzonosito() != null && !jelenlegi.getSzerzodesAzonosito().isBlank()) {
            return jelenlegi;
        }
        Long jogviszonyId = jelenlegi.getId();
        return szerzodesesJogviszonyokRepository
            .findById(jogviszonyId)
            .map(talalt -> {
                entity.setSzerzodesesJogviszony(talalt);
                return talalt;
            })
            .orElseThrow(() ->
                new BadRequestAlertException("A megadott szerződéses jogviszony nem található", ENTITY_NAME, "jogviszonynotfound")
            );
    }

    private void validateBusinessRules(Megrendelesek entity, SzerzodesesJogviszonyok jogviszony) {
        if (entity.getMegrendelesTipusa() == null) {
            throw new BadRequestAlertException("A megrendelés típusának megadása kötelező", ENTITY_NAME, "tipushiany");
        }
        if (entity.getMegrendelesKezdete() == null) {
            throw new BadRequestAlertException("A megrendelés kezdete megadása kötelező", ENTITY_NAME, "kezdetehiany");
        }
        if (entity.getMegrendelesVege() == null) {
            throw new BadRequestAlertException("A megrendelés vége megadása kötelező", ENTITY_NAME, "vegehiany");
        }
        if (entity.getMegrendelesKezdete() != null && entity.getMegrendelesVege() != null) {
            if (entity.getMegrendelesVege().isBefore(entity.getMegrendelesKezdete())) {
                throw new BadRequestAlertException("A megrendelés vége nem lehet korábbi, mint a kezdete", ENTITY_NAME, "vegeelottkezdet");
            }
        }
        if (entity.getDevizanem() == null) {
            throw new BadRequestAlertException("A devizanem megadása kötelező", ENTITY_NAME, "devizanemhiany");
        }
        if (entity.getDijazasTipusa() == null) {
            throw new BadRequestAlertException("A díjazás típusa megadása kötelező", ENTITY_NAME, "dijazastipushiany");
        }
        if (entity.getDijOsszege() == null || entity.getDijOsszege().signum() <= 0) {
            throw new BadRequestAlertException("A díj összegének pozitívnak kell lennie", ENTITY_NAME, "dijosszeg");
        }
        if (
            entity.getMegrendelesTipusa() == MegrendelesTipus.FOLYAMATOS_TELESTITES && entity.getDijazasTipusa() == DijazasTipusa.EGYSZERI
        ) {
            throw new BadRequestAlertException(
                "Folyamatos teljesítés esetén a díjazás csak napi- vagy havidíj lehet",
                ENTITY_NAME,
                "inkonzisztensdijazas"
            );
        }
        if (entity.getMegrendelesTipusa() == MegrendelesTipus.EGYSZERI && entity.getDijazasTipusa() != DijazasTipusa.EGYSZERI) {
            throw new BadRequestAlertException(
                "Egyszeri megrendelésnél csak egyszeri díjazás választható",
                ENTITY_NAME,
                "inkonzisztensdijazas"
            );
        }
        if (jogviszony.getSzerzodesAzonosito() == null || jogviszony.getSzerzodesAzonosito().isBlank()) {
            throw new BadRequestAlertException("A szerződéses jogviszony azonosítója hiányzik", ENTITY_NAME, "uresjogazonosito");
        }
        if (entity.getMunkakorId() != null && !munkakorokRepository.existsById(entity.getMunkakorId())) {
            throw new BadRequestAlertException("A megadott munkakör nem található", ENTITY_NAME, "ismeretlenmunkakor");
        }
    }

    private void ensureOrderNumber(Megrendelesek entity, SzerzodesesJogviszonyok jogviszony) {
        if (entity.getMegrendelesSzam() != null && !entity.getMegrendelesSzam().isBlank()) {
            return;
        }

        LocalDate referenceDate = Optional.ofNullable(entity.getMegrendelesKezdete()).orElse(LocalDate.now());
        String prefix = jogviszony.getSzerzodesAzonosito() + "/" + referenceDate.getYear() + "/";
        int nextSequence =
            megrendelesekRepository
                .findFirstBySzerzodesesJogviszony_IdAndMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(jogviszony.getId(), prefix)
                .map(Megrendelesek::getMegrendelesSzam)
                .map(existing -> extractSequence(existing, prefix))
                .orElse(0) +
            1;
        entity.setMegrendelesSzam(prefix + String.format("%03d", nextSequence));
    }

    private void applyMunkakorReference(Megrendelesek entity, MegrendelesekDTO source, boolean partial) {
        if (source == null) {
            return;
        }

        Long resolvedId = source.getMunkakorId();
        if (resolvedId == null && source.getMunkakor() != null) {
            resolvedId = source.getMunkakor().getId();
        }

        if (resolvedId != null) {
            entity.setMunkakorId(resolvedId);
        } else if (!partial) {
            entity.setMunkakorId(null);
        }
    }

    private int extractSequence(String currentValue, String prefix) {
        if (currentValue == null || !currentValue.startsWith(prefix)) {
            return 0;
        }
        String suffix = currentValue.substring(prefix.length());
        try {
            return Integer.parseInt(suffix);
        } catch (NumberFormatException ex) {
            LOG.warn("A meglévő megrendelésszám nem felelt meg a várt formátumnak: {}", currentValue);
            return 0;
        }
    }
}

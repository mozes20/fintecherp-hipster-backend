package com.fintech.erp.service;

import com.fintech.erp.domain.Maganszemelyek_;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.Megrendelesek_;
import com.fintech.erp.domain.SzerzodesesJogviszonyok_;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.service.criteria.MegrendelesekCriteria;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.mapper.MegrendelesekMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger; // for static metamodels
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Megrendelesek} entities in the database.
 * The main input is a {@link MegrendelesekCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MegrendelesekDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MegrendelesekQueryService extends QueryService<Megrendelesek> {

    private static final Logger LOG = LoggerFactory.getLogger(MegrendelesekQueryService.class);

    private final MegrendelesekRepository megrendelesekRepository;

    private final MegrendelesekMapper megrendelesekMapper;

    public MegrendelesekQueryService(MegrendelesekRepository megrendelesekRepository, MegrendelesekMapper megrendelesekMapper) {
        this.megrendelesekRepository = megrendelesekRepository;
        this.megrendelesekMapper = megrendelesekMapper;
    }

    /**
     * Return a {@link Page} of {@link MegrendelesekDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MegrendelesekDTO> findByCriteria(MegrendelesekCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Megrendelesek> specification = createSpecification(criteria);
        return megrendelesekRepository.findAll(specification, page).map(megrendelesekMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MegrendelesekCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Megrendelesek> specification = createSpecification(criteria);
        return megrendelesekRepository.count(specification);
    }

    /**
     * Function to convert {@link MegrendelesekCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Megrendelesek> createSpecification(MegrendelesekCriteria criteria) {
        Specification<Megrendelesek> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Megrendelesek_.id));
            }
            if (criteria.getMegrendelesTipusa() != null) {
                specification = specification.and(buildSpecification(criteria.getMegrendelesTipusa(), Megrendelesek_.megrendelesTipusa));
            }
            if (criteria.getFeladatRovidLeirasa() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getFeladatRovidLeirasa(), Megrendelesek_.feladatRovidLeirasa)
                );
            }
            if (criteria.getFeladatReszletesLeirasa() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getFeladatReszletesLeirasa(), Megrendelesek_.feladatReszletesLeirasa)
                );
            }
            if (criteria.getMegrendelesKezdete() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMegrendelesKezdete(), Megrendelesek_.megrendelesKezdete)
                );
            }
            if (criteria.getMegrendelesVege() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMegrendelesVege(), Megrendelesek_.megrendelesVege));
            }
            if (criteria.getResztvevoKollagaTipusa() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getResztvevoKollagaTipusa(), Megrendelesek_.resztvevoKollagaTipusa)
                );
            }
            if (criteria.getResztvevoTipus() != null) {
                specification = specification.and(buildSpecification(criteria.getResztvevoTipus(), Megrendelesek_.resztvevoTipus));
            }
            if (criteria.getDevizanem() != null) {
                specification = specification.and(buildSpecification(criteria.getDevizanem(), Megrendelesek_.devizanem));
            }
            if (criteria.getDijazasTipusa() != null) {
                specification = specification.and(buildSpecification(criteria.getDijazasTipusa(), Megrendelesek_.dijazasTipusa));
            }
            if (criteria.getDijOsszege() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDijOsszege(), Megrendelesek_.dijOsszege));
            }
            if (criteria.getMegrendelesDokumentumGeneralta() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getMegrendelesDokumentumGeneralta(), Megrendelesek_.megrendelesDokumentumGeneralta)
                );
            }
            if (criteria.getUgyfelMegrendelesId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getUgyfelMegrendelesId(), Megrendelesek_.ugyfelMegrendelesId)
                );
            }
            if (criteria.getMegrendelesSzam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMegrendelesSzam(), Megrendelesek_.megrendelesSzam));
            }
            if (criteria.getMegrendelesStatusz() != null) {
                specification = specification.and(buildSpecification(criteria.getMegrendelesStatusz(), Megrendelesek_.megrendelesStatusz));
            }
            if (criteria.getMegrendelesForrasa() != null) {
                specification = specification.and(buildSpecification(criteria.getMegrendelesForrasa(), Megrendelesek_.megrendelesForrasa));
            }
            if (criteria.getPeldanyokSzama() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeldanyokSzama(), Megrendelesek_.peldanyokSzama));
            }
            if (criteria.getSzamlazando() != null) {
                specification = specification.and(buildSpecification(criteria.getSzamlazando(), Megrendelesek_.szamlazando));
            }
            if (criteria.getSzerzodesesJogviszonyId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSzerzodesesJogviszonyId(), root ->
                        root.join(Megrendelesek_.szerzodesesJogviszony, JoinType.LEFT).get(SzerzodesesJogviszonyok_.id)
                    )
                );
            }
            if (criteria.getMaganszemelyId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getMaganszemelyId(), root ->
                        root.join(Megrendelesek_.maganszemely, JoinType.LEFT).get(Maganszemelyek_.id)
                    )
                );
            }
        }
        return specification;
    }
}

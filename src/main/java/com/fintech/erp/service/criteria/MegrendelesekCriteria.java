package com.fintech.erp.service.criteria;

import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fintech.erp.domain.Megrendelesek} entity. This class is used
 * in {@link com.fintech.erp.web.rest.MegrendelesekResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /megrendeleseks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesekCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    public static class MegrendelesTipusFilter extends Filter<MegrendelesTipus> {

        public MegrendelesTipusFilter() {}

        public MegrendelesTipusFilter(MegrendelesTipusFilter filter) {
            super(filter);
        }

        @Override
        public MegrendelesTipusFilter copy() {
            return new MegrendelesTipusFilter(this);
        }
    }

    public static class ResztvevoKollagaTipusFilter extends Filter<ResztvevoKollagaTipus> {

        public ResztvevoKollagaTipusFilter() {}

        public ResztvevoKollagaTipusFilter(ResztvevoKollagaTipusFilter filter) {
            super(filter);
        }

        @Override
        public ResztvevoKollagaTipusFilter copy() {
            return new ResztvevoKollagaTipusFilter(this);
        }
    }

    public static class DevizanemFilter extends Filter<Devizanem> {

        public DevizanemFilter() {}

        public DevizanemFilter(DevizanemFilter filter) {
            super(filter);
        }

        @Override
        public DevizanemFilter copy() {
            return new DevizanemFilter(this);
        }
    }

    public static class DijazasTipusaFilter extends Filter<DijazasTipusa> {

        public DijazasTipusaFilter() {}

        public DijazasTipusaFilter(DijazasTipusaFilter filter) {
            super(filter);
        }

        @Override
        public DijazasTipusaFilter copy() {
            return new DijazasTipusaFilter(this);
        }
    }

    public static class MegrendelesDokumentumEredetFilter extends Filter<MegrendelesDokumentumEredet> {

        public MegrendelesDokumentumEredetFilter() {}

        public MegrendelesDokumentumEredetFilter(MegrendelesDokumentumEredetFilter filter) {
            super(filter);
        }

        @Override
        public MegrendelesDokumentumEredetFilter copy() {
            return new MegrendelesDokumentumEredetFilter(this);
        }
    }

    private LongFilter id;

    private MegrendelesTipusFilter megrendelesTipusa;

    private StringFilter feladatRovidLeirasa;

    private StringFilter feladatReszletesLeirasa;

    private LocalDateFilter megrendelesDatuma;

    private LocalDateFilter megrendelesKezdete;

    private LocalDateFilter megrendelesVege;

    private ResztvevoKollagaTipusFilter resztvevoKollagaTipusa;

    private DevizanemFilter devizanem;

    private DijazasTipusaFilter dijazasTipusa;

    private BigDecimalFilter dijOsszege;

    private StringFilter szallitasraKeruloTetelek;

    private MegrendelesDokumentumEredetFilter megrendelesDokumentumGeneralta;

    private LongFilter ugyfelMegrendelesId;

    private StringFilter megrendelesSzam;

    private LongFilter munkakorId;

    private LongFilter szerzodesesJogviszonyId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public MegrendelesekCriteria() {}

    public MegrendelesekCriteria(MegrendelesekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.megrendelesTipusa = other.optionalMegrendelesTipusa().map(MegrendelesTipusFilter::copy).orElse(null);
        this.feladatRovidLeirasa = other.optionalFeladatRovidLeirasa().map(StringFilter::copy).orElse(null);
        this.feladatReszletesLeirasa = other.optionalFeladatReszletesLeirasa().map(StringFilter::copy).orElse(null);
        this.megrendelesDatuma = other.optionalMegrendelesDatuma().map(LocalDateFilter::copy).orElse(null);
        this.megrendelesKezdete = other.optionalMegrendelesKezdete().map(LocalDateFilter::copy).orElse(null);
        this.megrendelesVege = other.optionalMegrendelesVege().map(LocalDateFilter::copy).orElse(null);
        this.resztvevoKollagaTipusa = other.optionalResztvevoKollagaTipusa().map(ResztvevoKollagaTipusFilter::copy).orElse(null);
        this.devizanem = other.optionalDevizanem().map(DevizanemFilter::copy).orElse(null);
        this.dijazasTipusa = other.optionalDijazasTipusa().map(DijazasTipusaFilter::copy).orElse(null);
        this.dijOsszege = other.optionalDijOsszege().map(BigDecimalFilter::copy).orElse(null);
        this.szallitasraKeruloTetelek = other.optionalSzallitasraKeruloTetelek().map(StringFilter::copy).orElse(null);
        this.megrendelesDokumentumGeneralta = other
            .optionalMegrendelesDokumentumGeneralta()
            .map(MegrendelesDokumentumEredetFilter::copy)
            .orElse(null);
        this.ugyfelMegrendelesId = other.optionalUgyfelMegrendelesId().map(LongFilter::copy).orElse(null);
        this.megrendelesSzam = other.optionalMegrendelesSzam().map(StringFilter::copy).orElse(null);
        this.munkakorId = other.optionalMunkakorId().map(LongFilter::copy).orElse(null);
        this.szerzodesesJogviszonyId = other.optionalSzerzodesesJogviszonyId().map(LongFilter::copy).orElse(null);
        this.maganszemelyId = other.optionalMaganszemelyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MegrendelesekCriteria copy() {
        return new MegrendelesekCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public MegrendelesTipusFilter getMegrendelesTipusa() {
        return megrendelesTipusa;
    }

    public Optional<MegrendelesTipusFilter> optionalMegrendelesTipusa() {
        return Optional.ofNullable(megrendelesTipusa);
    }

    public MegrendelesTipusFilter megrendelesTipusa() {
        if (megrendelesTipusa == null) {
            setMegrendelesTipusa(new MegrendelesTipusFilter());
        }
        return megrendelesTipusa;
    }

    public void setMegrendelesTipusa(MegrendelesTipusFilter megrendelesTipusa) {
        this.megrendelesTipusa = megrendelesTipusa;
    }

    public StringFilter getFeladatRovidLeirasa() {
        return feladatRovidLeirasa;
    }

    public Optional<StringFilter> optionalFeladatRovidLeirasa() {
        return Optional.ofNullable(feladatRovidLeirasa);
    }

    public StringFilter feladatRovidLeirasa() {
        if (feladatRovidLeirasa == null) {
            setFeladatRovidLeirasa(new StringFilter());
        }
        return feladatRovidLeirasa;
    }

    public void setFeladatRovidLeirasa(StringFilter feladatRovidLeirasa) {
        this.feladatRovidLeirasa = feladatRovidLeirasa;
    }

    public StringFilter getFeladatReszletesLeirasa() {
        return feladatReszletesLeirasa;
    }

    public Optional<StringFilter> optionalFeladatReszletesLeirasa() {
        return Optional.ofNullable(feladatReszletesLeirasa);
    }

    public StringFilter feladatReszletesLeirasa() {
        if (feladatReszletesLeirasa == null) {
            setFeladatReszletesLeirasa(new StringFilter());
        }
        return feladatReszletesLeirasa;
    }

    public void setFeladatReszletesLeirasa(StringFilter feladatReszletesLeirasa) {
        this.feladatReszletesLeirasa = feladatReszletesLeirasa;
    }

    public LocalDateFilter getMegrendelesDatuma() {
        return megrendelesDatuma;
    }

    public Optional<LocalDateFilter> optionalMegrendelesDatuma() {
        return Optional.ofNullable(megrendelesDatuma);
    }

    public LocalDateFilter megrendelesDatuma() {
        if (megrendelesDatuma == null) {
            setMegrendelesDatuma(new LocalDateFilter());
        }
        return megrendelesDatuma;
    }

    public void setMegrendelesDatuma(LocalDateFilter megrendelesDatuma) {
        this.megrendelesDatuma = megrendelesDatuma;
    }

    public LocalDateFilter getMegrendelesKezdete() {
        return megrendelesKezdete;
    }

    public Optional<LocalDateFilter> optionalMegrendelesKezdete() {
        return Optional.ofNullable(megrendelesKezdete);
    }

    public LocalDateFilter megrendelesKezdete() {
        if (megrendelesKezdete == null) {
            setMegrendelesKezdete(new LocalDateFilter());
        }
        return megrendelesKezdete;
    }

    public void setMegrendelesKezdete(LocalDateFilter megrendelesKezdete) {
        this.megrendelesKezdete = megrendelesKezdete;
    }

    public LocalDateFilter getMegrendelesVege() {
        return megrendelesVege;
    }

    public Optional<LocalDateFilter> optionalMegrendelesVege() {
        return Optional.ofNullable(megrendelesVege);
    }

    public LocalDateFilter megrendelesVege() {
        if (megrendelesVege == null) {
            setMegrendelesVege(new LocalDateFilter());
        }
        return megrendelesVege;
    }

    public void setMegrendelesVege(LocalDateFilter megrendelesVege) {
        this.megrendelesVege = megrendelesVege;
    }

    public ResztvevoKollagaTipusFilter getResztvevoKollagaTipusa() {
        return resztvevoKollagaTipusa;
    }

    public Optional<ResztvevoKollagaTipusFilter> optionalResztvevoKollagaTipusa() {
        return Optional.ofNullable(resztvevoKollagaTipusa);
    }

    public ResztvevoKollagaTipusFilter resztvevoKollagaTipusa() {
        if (resztvevoKollagaTipusa == null) {
            setResztvevoKollagaTipusa(new ResztvevoKollagaTipusFilter());
        }
        return resztvevoKollagaTipusa;
    }

    public void setResztvevoKollagaTipusa(ResztvevoKollagaTipusFilter resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
    }

    public DevizanemFilter getDevizanem() {
        return devizanem;
    }

    public Optional<DevizanemFilter> optionalDevizanem() {
        return Optional.ofNullable(devizanem);
    }

    public DevizanemFilter devizanem() {
        if (devizanem == null) {
            setDevizanem(new DevizanemFilter());
        }
        return devizanem;
    }

    public void setDevizanem(DevizanemFilter devizanem) {
        this.devizanem = devizanem;
    }

    public DijazasTipusaFilter getDijazasTipusa() {
        return dijazasTipusa;
    }

    public Optional<DijazasTipusaFilter> optionalDijazasTipusa() {
        return Optional.ofNullable(dijazasTipusa);
    }

    public DijazasTipusaFilter dijazasTipusa() {
        if (dijazasTipusa == null) {
            setDijazasTipusa(new DijazasTipusaFilter());
        }
        return dijazasTipusa;
    }

    public void setDijazasTipusa(DijazasTipusaFilter dijazasTipusa) {
        this.dijazasTipusa = dijazasTipusa;
    }

    public BigDecimalFilter getDijOsszege() {
        return dijOsszege;
    }

    public Optional<BigDecimalFilter> optionalDijOsszege() {
        return Optional.ofNullable(dijOsszege);
    }

    public StringFilter getSzallitasraKeruloTetelek() {
        return szallitasraKeruloTetelek;
    }

    public Optional<StringFilter> optionalSzallitasraKeruloTetelek() {
        return Optional.ofNullable(szallitasraKeruloTetelek);
    }

    public StringFilter szallitasraKeruloTetelek() {
        if (szallitasraKeruloTetelek == null) {
            setSzallitasraKeruloTetelek(new StringFilter());
        }
        return szallitasraKeruloTetelek;
    }

    public void setSzallitasraKeruloTetelek(StringFilter szallitasraKeruloTetelek) {
        this.szallitasraKeruloTetelek = szallitasraKeruloTetelek;
    }

    public StringFilter getMegrendelesSzam() {
        return megrendelesSzam;
    }

    public Optional<StringFilter> optionalMegrendelesSzam() {
        return Optional.ofNullable(megrendelesSzam);
    }

    public StringFilter megrendelesSzam() {
        if (megrendelesSzam == null) {
            setMegrendelesSzam(new StringFilter());
        }
        return megrendelesSzam;
    }

    public void setMegrendelesSzam(StringFilter megrendelesSzam) {
        this.megrendelesSzam = megrendelesSzam;
    }

    public BigDecimalFilter dijOsszege() {
        if (dijOsszege == null) {
            setDijOsszege(new BigDecimalFilter());
        }
        return dijOsszege;
    }

    public void setDijOsszege(BigDecimalFilter dijOsszege) {
        this.dijOsszege = dijOsszege;
    }

    public MegrendelesDokumentumEredetFilter getMegrendelesDokumentumGeneralta() {
        return megrendelesDokumentumGeneralta;
    }

    public Optional<MegrendelesDokumentumEredetFilter> optionalMegrendelesDokumentumGeneralta() {
        return Optional.ofNullable(megrendelesDokumentumGeneralta);
    }

    public MegrendelesDokumentumEredetFilter megrendelesDokumentumGeneralta() {
        if (megrendelesDokumentumGeneralta == null) {
            setMegrendelesDokumentumGeneralta(new MegrendelesDokumentumEredetFilter());
        }
        return megrendelesDokumentumGeneralta;
    }

    public void setMegrendelesDokumentumGeneralta(MegrendelesDokumentumEredetFilter megrendelesDokumentumGeneralta) {
        this.megrendelesDokumentumGeneralta = megrendelesDokumentumGeneralta;
    }

    public LongFilter getUgyfelMegrendelesId() {
        return ugyfelMegrendelesId;
    }

    public Optional<LongFilter> optionalUgyfelMegrendelesId() {
        return Optional.ofNullable(ugyfelMegrendelesId);
    }

    public LongFilter ugyfelMegrendelesId() {
        if (ugyfelMegrendelesId == null) {
            setUgyfelMegrendelesId(new LongFilter());
        }
        return ugyfelMegrendelesId;
    }

    public void setUgyfelMegrendelesId(LongFilter ugyfelMegrendelesId) {
        this.ugyfelMegrendelesId = ugyfelMegrendelesId;
    }

    public LongFilter getMunkakorId() {
        return munkakorId;
    }

    public Optional<LongFilter> optionalMunkakorId() {
        return Optional.ofNullable(munkakorId);
    }

    public LongFilter munkakorId() {
        if (munkakorId == null) {
            setMunkakorId(new LongFilter());
        }
        return munkakorId;
    }

    public void setMunkakorId(LongFilter munkakorId) {
        this.munkakorId = munkakorId;
    }

    public LongFilter getSzerzodesesJogviszonyId() {
        return szerzodesesJogviszonyId;
    }

    public Optional<LongFilter> optionalSzerzodesesJogviszonyId() {
        return Optional.ofNullable(szerzodesesJogviszonyId);
    }

    public LongFilter szerzodesesJogviszonyId() {
        if (szerzodesesJogviszonyId == null) {
            setSzerzodesesJogviszonyId(new LongFilter());
        }
        return szerzodesesJogviszonyId;
    }

    public void setSzerzodesesJogviszonyId(LongFilter szerzodesesJogviszonyId) {
        this.szerzodesesJogviszonyId = szerzodesesJogviszonyId;
    }

    public LongFilter getMaganszemelyId() {
        return maganszemelyId;
    }

    public Optional<LongFilter> optionalMaganszemelyId() {
        return Optional.ofNullable(maganszemelyId);
    }

    public LongFilter maganszemelyId() {
        if (maganszemelyId == null) {
            setMaganszemelyId(new LongFilter());
        }
        return maganszemelyId;
    }

    public void setMaganszemelyId(LongFilter maganszemelyId) {
        this.maganszemelyId = maganszemelyId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MegrendelesekCriteria that = (MegrendelesekCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(megrendelesTipusa, that.megrendelesTipusa) &&
            Objects.equals(feladatRovidLeirasa, that.feladatRovidLeirasa) &&
            Objects.equals(feladatReszletesLeirasa, that.feladatReszletesLeirasa) &&
            Objects.equals(megrendelesDatuma, that.megrendelesDatuma) &&
            Objects.equals(megrendelesKezdete, that.megrendelesKezdete) &&
            Objects.equals(megrendelesVege, that.megrendelesVege) &&
            Objects.equals(resztvevoKollagaTipusa, that.resztvevoKollagaTipusa) &&
            Objects.equals(devizanem, that.devizanem) &&
            Objects.equals(dijazasTipusa, that.dijazasTipusa) &&
            Objects.equals(dijOsszege, that.dijOsszege) &&
            Objects.equals(szallitasraKeruloTetelek, that.szallitasraKeruloTetelek) &&
            Objects.equals(megrendelesDokumentumGeneralta, that.megrendelesDokumentumGeneralta) &&
            Objects.equals(ugyfelMegrendelesId, that.ugyfelMegrendelesId) &&
            Objects.equals(megrendelesSzam, that.megrendelesSzam) &&
            Objects.equals(munkakorId, that.munkakorId) &&
            Objects.equals(szerzodesesJogviszonyId, that.szerzodesesJogviszonyId) &&
            Objects.equals(maganszemelyId, that.maganszemelyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            megrendelesTipusa,
            feladatRovidLeirasa,
            feladatReszletesLeirasa,
            megrendelesDatuma,
            megrendelesKezdete,
            megrendelesVege,
            resztvevoKollagaTipusa,
            devizanem,
            dijazasTipusa,
            dijOsszege,
            szallitasraKeruloTetelek,
            megrendelesDokumentumGeneralta,
            ugyfelMegrendelesId,
            megrendelesSzam,
            munkakorId,
            szerzodesesJogviszonyId,
            maganszemelyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MegrendelesekCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMegrendelesTipusa().map(f -> "megrendelesTipusa=" + f + ", ").orElse("") +
            optionalFeladatRovidLeirasa().map(f -> "feladatRovidLeirasa=" + f + ", ").orElse("") +
            optionalFeladatReszletesLeirasa().map(f -> "feladatReszletesLeirasa=" + f + ", ").orElse("") +
            optionalMegrendelesDatuma().map(f -> "megrendelesDatuma=" + f + ", ").orElse("") +
            optionalMegrendelesKezdete().map(f -> "megrendelesKezdete=" + f + ", ").orElse("") +
            optionalMegrendelesVege().map(f -> "megrendelesVege=" + f + ", ").orElse("") +
            optionalResztvevoKollagaTipusa().map(f -> "resztvevoKollagaTipusa=" + f + ", ").orElse("") +
            optionalDevizanem().map(f -> "devizanem=" + f + ", ").orElse("") +
            optionalDijazasTipusa().map(f -> "dijazasTipusa=" + f + ", ").orElse("") +
            optionalDijOsszege().map(f -> "dijOsszege=" + f + ", ").orElse("") +
            optionalSzallitasraKeruloTetelek().map(f -> "szallitasraKeruloTetelek=" + f + ", ").orElse("") +
            optionalMegrendelesDokumentumGeneralta().map(f -> "megrendelesDokumentumGeneralta=" + f + ", ").orElse("") +
            optionalUgyfelMegrendelesId().map(f -> "ugyfelMegrendelesId=" + f + ", ").orElse("") +
            optionalMegrendelesSzam().map(f -> "megrendelesSzam=" + f + ", ").orElse("") +
            optionalMunkakorId().map(f -> "munkakorId=" + f + ", ").orElse("") +
            optionalSzerzodesesJogviszonyId().map(f -> "szerzodesesJogviszonyId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

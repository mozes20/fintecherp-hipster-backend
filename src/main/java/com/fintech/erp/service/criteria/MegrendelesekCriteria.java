package com.fintech.erp.service.criteria;

import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesForras;
import com.fintech.erp.domain.enumeration.MegrendelesStatusz;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import com.fintech.erp.domain.enumeration.ResztvevoTipus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
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

    public static class ResztvevoTipusFilter extends Filter<ResztvevoTipus> {

        public ResztvevoTipusFilter() {}

        public ResztvevoTipusFilter(ResztvevoTipusFilter filter) {
            super(filter);
        }

        @Override
        public ResztvevoTipusFilter copy() {
            return new ResztvevoTipusFilter(this);
        }
    }

    public static class MegrendelesStatuszFilter extends Filter<MegrendelesStatusz> {

        public MegrendelesStatuszFilter() {}

        public MegrendelesStatuszFilter(MegrendelesStatuszFilter filter) {
            super(filter);
        }

        @Override
        public MegrendelesStatuszFilter copy() {
            return new MegrendelesStatuszFilter(this);
        }
    }

    public static class MegrendelesForrasFilter extends Filter<MegrendelesForras> {

        public MegrendelesForrasFilter() {}

        public MegrendelesForrasFilter(MegrendelesForrasFilter filter) {
            super(filter);
        }

        @Override
        public MegrendelesForrasFilter copy() {
            return new MegrendelesForrasFilter(this);
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

    private LocalDateFilter megrendelesKezdete;

    private LocalDateFilter megrendelesVege;

    private ResztvevoKollagaTipusFilter resztvevoKollagaTipusa;

    private ResztvevoTipusFilter resztvevoTipus;

    private DevizanemFilter devizanem;

    private DijazasTipusaFilter dijazasTipusa;

    private BigDecimalFilter dijOsszege;

    private MegrendelesDokumentumEredetFilter megrendelesDokumentumGeneralta;

    private LongFilter ugyfelMegrendelesId;

    private StringFilter megrendelesSzam;

    private MegrendelesStatuszFilter megrendelesStatusz;

    private MegrendelesForrasFilter megrendelesForrasa;

    private IntegerFilter peldanyokSzama;

    private BooleanFilter szamlazando;

    private LongFilter szerzodesesJogviszonyId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public MegrendelesekCriteria() {}

    public MegrendelesekCriteria(MegrendelesekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.megrendelesTipusa = other.optionalMegrendelesTipusa().map(MegrendelesTipusFilter::copy).orElse(null);
        this.feladatRovidLeirasa = other.optionalFeladatRovidLeirasa().map(StringFilter::copy).orElse(null);
        this.feladatReszletesLeirasa = other.optionalFeladatReszletesLeirasa().map(StringFilter::copy).orElse(null);
        this.megrendelesKezdete = other.optionalMegrendelesKezdete().map(LocalDateFilter::copy).orElse(null);
        this.megrendelesVege = other.optionalMegrendelesVege().map(LocalDateFilter::copy).orElse(null);
        this.resztvevoKollagaTipusa = other.optionalResztvevoKollagaTipusa().map(ResztvevoKollagaTipusFilter::copy).orElse(null);
        this.resztvevoTipus = other.optionalResztvevoTipus().map(ResztvevoTipusFilter::copy).orElse(null);
        this.devizanem = other.optionalDevizanem().map(DevizanemFilter::copy).orElse(null);
        this.dijazasTipusa = other.optionalDijazasTipusa().map(DijazasTipusaFilter::copy).orElse(null);
        this.dijOsszege = other.optionalDijOsszege().map(BigDecimalFilter::copy).orElse(null);
        this.megrendelesDokumentumGeneralta = other
            .optionalMegrendelesDokumentumGeneralta()
            .map(MegrendelesDokumentumEredetFilter::copy)
            .orElse(null);
        this.ugyfelMegrendelesId = other.optionalUgyfelMegrendelesId().map(LongFilter::copy).orElse(null);
        this.megrendelesSzam = other.optionalMegrendelesSzam().map(StringFilter::copy).orElse(null);
        this.megrendelesStatusz = other.optionalMegrendelesStatusz().map(MegrendelesStatuszFilter::copy).orElse(null);
        this.megrendelesForrasa = other.optionalMegrendelesForrasa().map(MegrendelesForrasFilter::copy).orElse(null);
        this.peldanyokSzama = other.optionalPeldanyokSzama().map(IntegerFilter::copy).orElse(null);
        this.szamlazando = other.optionalSzamlazando().map(BooleanFilter::copy).orElse(null);
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

    public ResztvevoTipusFilter getResztvevoTipus() {
        return resztvevoTipus;
    }

    public Optional<ResztvevoTipusFilter> optionalResztvevoTipus() {
        return Optional.ofNullable(resztvevoTipus);
    }

    public ResztvevoTipusFilter resztvevoTipus() {
        if (resztvevoTipus == null) {
            setResztvevoTipus(new ResztvevoTipusFilter());
        }
        return resztvevoTipus;
    }

    public void setResztvevoTipus(ResztvevoTipusFilter resztvevoTipus) {
        this.resztvevoTipus = resztvevoTipus;
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

    public MegrendelesStatuszFilter getMegrendelesStatusz() {
        return megrendelesStatusz;
    }

    public Optional<MegrendelesStatuszFilter> optionalMegrendelesStatusz() {
        return Optional.ofNullable(megrendelesStatusz);
    }

    public MegrendelesStatuszFilter megrendelesStatusz() {
        if (megrendelesStatusz == null) {
            setMegrendelesStatusz(new MegrendelesStatuszFilter());
        }
        return megrendelesStatusz;
    }

    public void setMegrendelesStatusz(MegrendelesStatuszFilter megrendelesStatusz) {
        this.megrendelesStatusz = megrendelesStatusz;
    }

    public MegrendelesForrasFilter getMegrendelesForrasa() {
        return megrendelesForrasa;
    }

    public Optional<MegrendelesForrasFilter> optionalMegrendelesForrasa() {
        return Optional.ofNullable(megrendelesForrasa);
    }

    public MegrendelesForrasFilter megrendelesForrasa() {
        if (megrendelesForrasa == null) {
            setMegrendelesForrasa(new MegrendelesForrasFilter());
        }
        return megrendelesForrasa;
    }

    public void setMegrendelesForrasa(MegrendelesForrasFilter megrendelesForrasa) {
        this.megrendelesForrasa = megrendelesForrasa;
    }

    public IntegerFilter getPeldanyokSzama() {
        return peldanyokSzama;
    }

    public Optional<IntegerFilter> optionalPeldanyokSzama() {
        return Optional.ofNullable(peldanyokSzama);
    }

    public IntegerFilter peldanyokSzama() {
        if (peldanyokSzama == null) {
            setPeldanyokSzama(new IntegerFilter());
        }
        return peldanyokSzama;
    }

    public void setPeldanyokSzama(IntegerFilter peldanyokSzama) {
        this.peldanyokSzama = peldanyokSzama;
    }

    public BooleanFilter getSzamlazando() {
        return szamlazando;
    }

    public Optional<BooleanFilter> optionalSzamlazando() {
        return Optional.ofNullable(szamlazando);
    }

    public BooleanFilter szamlazando() {
        if (szamlazando == null) {
            setSzamlazando(new BooleanFilter());
        }
        return szamlazando;
    }

    public void setSzamlazando(BooleanFilter szamlazando) {
        this.szamlazando = szamlazando;
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
            Objects.equals(megrendelesKezdete, that.megrendelesKezdete) &&
            Objects.equals(megrendelesVege, that.megrendelesVege) &&
            Objects.equals(resztvevoKollagaTipusa, that.resztvevoKollagaTipusa) &&
            Objects.equals(resztvevoTipus, that.resztvevoTipus) &&
            Objects.equals(devizanem, that.devizanem) &&
            Objects.equals(dijazasTipusa, that.dijazasTipusa) &&
            Objects.equals(dijOsszege, that.dijOsszege) &&
            Objects.equals(megrendelesDokumentumGeneralta, that.megrendelesDokumentumGeneralta) &&
            Objects.equals(ugyfelMegrendelesId, that.ugyfelMegrendelesId) &&
            Objects.equals(megrendelesSzam, that.megrendelesSzam) &&
            Objects.equals(megrendelesStatusz, that.megrendelesStatusz) &&
            Objects.equals(megrendelesForrasa, that.megrendelesForrasa) &&
            Objects.equals(peldanyokSzama, that.peldanyokSzama) &&
            Objects.equals(szamlazando, that.szamlazando) &&
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
            megrendelesKezdete,
            megrendelesVege,
            resztvevoKollagaTipusa,
            resztvevoTipus,
            devizanem,
            dijazasTipusa,
            dijOsszege,
            megrendelesDokumentumGeneralta,
            ugyfelMegrendelesId,
            megrendelesSzam,
            megrendelesStatusz,
            megrendelesForrasa,
            peldanyokSzama,
            szamlazando,
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
            optionalMegrendelesKezdete().map(f -> "megrendelesKezdete=" + f + ", ").orElse("") +
            optionalMegrendelesVege().map(f -> "megrendelesVege=" + f + ", ").orElse("") +
            optionalResztvevoKollagaTipusa().map(f -> "resztvevoKollagaTipusa=" + f + ", ").orElse("") +
            optionalResztvevoTipus().map(f -> "resztvevoTipus=" + f + ", ").orElse("") +
            optionalDevizanem().map(f -> "devizanem=" + f + ", ").orElse("") +
            optionalDijazasTipusa().map(f -> "dijazasTipusa=" + f + ", ").orElse("") +
            optionalDijOsszege().map(f -> "dijOsszege=" + f + ", ").orElse("") +
            optionalMegrendelesDokumentumGeneralta().map(f -> "megrendelesDokumentumGeneralta=" + f + ", ").orElse("") +
            optionalUgyfelMegrendelesId().map(f -> "ugyfelMegrendelesId=" + f + ", ").orElse("") +
            optionalMegrendelesSzam().map(f -> "megrendelesSzam=" + f + ", ").orElse("") +
            optionalMegrendelesStatusz().map(f -> "megrendelesStatusz=" + f + ", ").orElse("") +
            optionalMegrendelesForrasa().map(f -> "megrendelesForrasa=" + f + ", ").orElse("") +
            optionalPeldanyokSzama().map(f -> "peldanyokSzama=" + f + ", ").orElse("") +
            optionalSzamlazando().map(f -> "szamlazando=" + f + ", ").orElse("") +
            optionalSzerzodesesJogviszonyId().map(f -> "szerzodesesJogviszonyId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

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

    private LongFilter id;

    private StringFilter megrendelesTipusa;

    private StringFilter feladatRovidLeirasa;

    private StringFilter feladatReszletesLeirasa;

    private LocalDateFilter megrendelesKezdete;

    private LocalDateFilter megrendelesVege;

    private StringFilter resztvevoKollagaTipusa;

    private StringFilter devizanem;

    private StringFilter dijazasTipusa;

    private BigDecimalFilter dijOsszege;

    private BooleanFilter megrendelesDokumentumGeneralta;

    private LongFilter ugyfelMegrendelesId;

    private LongFilter szerzodesesJogviszonyId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public MegrendelesekCriteria() {}

    public MegrendelesekCriteria(MegrendelesekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.megrendelesTipusa = other.optionalMegrendelesTipusa().map(StringFilter::copy).orElse(null);
        this.feladatRovidLeirasa = other.optionalFeladatRovidLeirasa().map(StringFilter::copy).orElse(null);
        this.feladatReszletesLeirasa = other.optionalFeladatReszletesLeirasa().map(StringFilter::copy).orElse(null);
        this.megrendelesKezdete = other.optionalMegrendelesKezdete().map(LocalDateFilter::copy).orElse(null);
        this.megrendelesVege = other.optionalMegrendelesVege().map(LocalDateFilter::copy).orElse(null);
        this.resztvevoKollagaTipusa = other.optionalResztvevoKollagaTipusa().map(StringFilter::copy).orElse(null);
        this.devizanem = other.optionalDevizanem().map(StringFilter::copy).orElse(null);
        this.dijazasTipusa = other.optionalDijazasTipusa().map(StringFilter::copy).orElse(null);
        this.dijOsszege = other.optionalDijOsszege().map(BigDecimalFilter::copy).orElse(null);
        this.megrendelesDokumentumGeneralta = other.optionalMegrendelesDokumentumGeneralta().map(BooleanFilter::copy).orElse(null);
        this.ugyfelMegrendelesId = other.optionalUgyfelMegrendelesId().map(LongFilter::copy).orElse(null);
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

    public StringFilter getMegrendelesTipusa() {
        return megrendelesTipusa;
    }

    public Optional<StringFilter> optionalMegrendelesTipusa() {
        return Optional.ofNullable(megrendelesTipusa);
    }

    public StringFilter megrendelesTipusa() {
        if (megrendelesTipusa == null) {
            setMegrendelesTipusa(new StringFilter());
        }
        return megrendelesTipusa;
    }

    public void setMegrendelesTipusa(StringFilter megrendelesTipusa) {
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

    public StringFilter getResztvevoKollagaTipusa() {
        return resztvevoKollagaTipusa;
    }

    public Optional<StringFilter> optionalResztvevoKollagaTipusa() {
        return Optional.ofNullable(resztvevoKollagaTipusa);
    }

    public StringFilter resztvevoKollagaTipusa() {
        if (resztvevoKollagaTipusa == null) {
            setResztvevoKollagaTipusa(new StringFilter());
        }
        return resztvevoKollagaTipusa;
    }

    public void setResztvevoKollagaTipusa(StringFilter resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
    }

    public StringFilter getDevizanem() {
        return devizanem;
    }

    public Optional<StringFilter> optionalDevizanem() {
        return Optional.ofNullable(devizanem);
    }

    public StringFilter devizanem() {
        if (devizanem == null) {
            setDevizanem(new StringFilter());
        }
        return devizanem;
    }

    public void setDevizanem(StringFilter devizanem) {
        this.devizanem = devizanem;
    }

    public StringFilter getDijazasTipusa() {
        return dijazasTipusa;
    }

    public Optional<StringFilter> optionalDijazasTipusa() {
        return Optional.ofNullable(dijazasTipusa);
    }

    public StringFilter dijazasTipusa() {
        if (dijazasTipusa == null) {
            setDijazasTipusa(new StringFilter());
        }
        return dijazasTipusa;
    }

    public void setDijazasTipusa(StringFilter dijazasTipusa) {
        this.dijazasTipusa = dijazasTipusa;
    }

    public BigDecimalFilter getDijOsszege() {
        return dijOsszege;
    }

    public Optional<BigDecimalFilter> optionalDijOsszege() {
        return Optional.ofNullable(dijOsszege);
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

    public BooleanFilter getMegrendelesDokumentumGeneralta() {
        return megrendelesDokumentumGeneralta;
    }

    public Optional<BooleanFilter> optionalMegrendelesDokumentumGeneralta() {
        return Optional.ofNullable(megrendelesDokumentumGeneralta);
    }

    public BooleanFilter megrendelesDokumentumGeneralta() {
        if (megrendelesDokumentumGeneralta == null) {
            setMegrendelesDokumentumGeneralta(new BooleanFilter());
        }
        return megrendelesDokumentumGeneralta;
    }

    public void setMegrendelesDokumentumGeneralta(BooleanFilter megrendelesDokumentumGeneralta) {
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
            Objects.equals(devizanem, that.devizanem) &&
            Objects.equals(dijazasTipusa, that.dijazasTipusa) &&
            Objects.equals(dijOsszege, that.dijOsszege) &&
            Objects.equals(megrendelesDokumentumGeneralta, that.megrendelesDokumentumGeneralta) &&
            Objects.equals(ugyfelMegrendelesId, that.ugyfelMegrendelesId) &&
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
            devizanem,
            dijazasTipusa,
            dijOsszege,
            megrendelesDokumentumGeneralta,
            ugyfelMegrendelesId,
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
            optionalDevizanem().map(f -> "devizanem=" + f + ", ").orElse("") +
            optionalDijazasTipusa().map(f -> "dijazasTipusa=" + f + ", ").orElse("") +
            optionalDijOsszege().map(f -> "dijOsszege=" + f + ", ").orElse("") +
            optionalMegrendelesDokumentumGeneralta().map(f -> "megrendelesDokumentumGeneralta=" + f + ", ").orElse("") +
            optionalUgyfelMegrendelesId().map(f -> "ugyfelMegrendelesId=" + f + ", ").orElse("") +
            optionalSzerzodesesJogviszonyId().map(f -> "szerzodesesJogviszonyId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.UgyfelElszamolasok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.UgyfelElszamolasokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ugyfel-elszamolasoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UgyfelElszamolasokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter teljesitesiIdoszakKezdete;

    private LocalDateFilter teljesitesiIdoszakVege;

    private IntegerFilter napokSzama;

    private BigDecimalFilter teljesitesIgazolasonSzereploOsszeg;

    private BooleanFilter kapcsolodoSzamlaSorszamRogzitve;

    private LongFilter megrendelesId;

    private Boolean distinct;

    public UgyfelElszamolasokCriteria() {}

    public UgyfelElszamolasokCriteria(UgyfelElszamolasokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.teljesitesiIdoszakKezdete = other.optionalTeljesitesiIdoszakKezdete().map(LocalDateFilter::copy).orElse(null);
        this.teljesitesiIdoszakVege = other.optionalTeljesitesiIdoszakVege().map(LocalDateFilter::copy).orElse(null);
        this.napokSzama = other.optionalNapokSzama().map(IntegerFilter::copy).orElse(null);
        this.teljesitesIgazolasonSzereploOsszeg = other
            .optionalTeljesitesIgazolasonSzereploOsszeg()
            .map(BigDecimalFilter::copy)
            .orElse(null);
        this.kapcsolodoSzamlaSorszamRogzitve = other.optionalKapcsolodoSzamlaSorszamRogzitve().map(BooleanFilter::copy).orElse(null);
        this.megrendelesId = other.optionalMegrendelesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public UgyfelElszamolasokCriteria copy() {
        return new UgyfelElszamolasokCriteria(this);
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

    public LocalDateFilter getTeljesitesiIdoszakKezdete() {
        return teljesitesiIdoszakKezdete;
    }

    public Optional<LocalDateFilter> optionalTeljesitesiIdoszakKezdete() {
        return Optional.ofNullable(teljesitesiIdoszakKezdete);
    }

    public LocalDateFilter teljesitesiIdoszakKezdete() {
        if (teljesitesiIdoszakKezdete == null) {
            setTeljesitesiIdoszakKezdete(new LocalDateFilter());
        }
        return teljesitesiIdoszakKezdete;
    }

    public void setTeljesitesiIdoszakKezdete(LocalDateFilter teljesitesiIdoszakKezdete) {
        this.teljesitesiIdoszakKezdete = teljesitesiIdoszakKezdete;
    }

    public LocalDateFilter getTeljesitesiIdoszakVege() {
        return teljesitesiIdoszakVege;
    }

    public Optional<LocalDateFilter> optionalTeljesitesiIdoszakVege() {
        return Optional.ofNullable(teljesitesiIdoszakVege);
    }

    public LocalDateFilter teljesitesiIdoszakVege() {
        if (teljesitesiIdoszakVege == null) {
            setTeljesitesiIdoszakVege(new LocalDateFilter());
        }
        return teljesitesiIdoszakVege;
    }

    public void setTeljesitesiIdoszakVege(LocalDateFilter teljesitesiIdoszakVege) {
        this.teljesitesiIdoszakVege = teljesitesiIdoszakVege;
    }

    public IntegerFilter getNapokSzama() {
        return napokSzama;
    }

    public Optional<IntegerFilter> optionalNapokSzama() {
        return Optional.ofNullable(napokSzama);
    }

    public IntegerFilter napokSzama() {
        if (napokSzama == null) {
            setNapokSzama(new IntegerFilter());
        }
        return napokSzama;
    }

    public void setNapokSzama(IntegerFilter napokSzama) {
        this.napokSzama = napokSzama;
    }

    public BigDecimalFilter getTeljesitesIgazolasonSzereploOsszeg() {
        return teljesitesIgazolasonSzereploOsszeg;
    }

    public Optional<BigDecimalFilter> optionalTeljesitesIgazolasonSzereploOsszeg() {
        return Optional.ofNullable(teljesitesIgazolasonSzereploOsszeg);
    }

    public BigDecimalFilter teljesitesIgazolasonSzereploOsszeg() {
        if (teljesitesIgazolasonSzereploOsszeg == null) {
            setTeljesitesIgazolasonSzereploOsszeg(new BigDecimalFilter());
        }
        return teljesitesIgazolasonSzereploOsszeg;
    }

    public void setTeljesitesIgazolasonSzereploOsszeg(BigDecimalFilter teljesitesIgazolasonSzereploOsszeg) {
        this.teljesitesIgazolasonSzereploOsszeg = teljesitesIgazolasonSzereploOsszeg;
    }

    public BooleanFilter getKapcsolodoSzamlaSorszamRogzitve() {
        return kapcsolodoSzamlaSorszamRogzitve;
    }

    public Optional<BooleanFilter> optionalKapcsolodoSzamlaSorszamRogzitve() {
        return Optional.ofNullable(kapcsolodoSzamlaSorszamRogzitve);
    }

    public BooleanFilter kapcsolodoSzamlaSorszamRogzitve() {
        if (kapcsolodoSzamlaSorszamRogzitve == null) {
            setKapcsolodoSzamlaSorszamRogzitve(new BooleanFilter());
        }
        return kapcsolodoSzamlaSorszamRogzitve;
    }

    public void setKapcsolodoSzamlaSorszamRogzitve(BooleanFilter kapcsolodoSzamlaSorszamRogzitve) {
        this.kapcsolodoSzamlaSorszamRogzitve = kapcsolodoSzamlaSorszamRogzitve;
    }

    public LongFilter getMegrendelesId() {
        return megrendelesId;
    }

    public Optional<LongFilter> optionalMegrendelesId() {
        return Optional.ofNullable(megrendelesId);
    }

    public LongFilter megrendelesId() {
        if (megrendelesId == null) {
            setMegrendelesId(new LongFilter());
        }
        return megrendelesId;
    }

    public void setMegrendelesId(LongFilter megrendelesId) {
        this.megrendelesId = megrendelesId;
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
        final UgyfelElszamolasokCriteria that = (UgyfelElszamolasokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(teljesitesiIdoszakKezdete, that.teljesitesiIdoszakKezdete) &&
            Objects.equals(teljesitesiIdoszakVege, that.teljesitesiIdoszakVege) &&
            Objects.equals(napokSzama, that.napokSzama) &&
            Objects.equals(teljesitesIgazolasonSzereploOsszeg, that.teljesitesIgazolasonSzereploOsszeg) &&
            Objects.equals(kapcsolodoSzamlaSorszamRogzitve, that.kapcsolodoSzamlaSorszamRogzitve) &&
            Objects.equals(megrendelesId, that.megrendelesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            teljesitesiIdoszakKezdete,
            teljesitesiIdoszakVege,
            napokSzama,
            teljesitesIgazolasonSzereploOsszeg,
            kapcsolodoSzamlaSorszamRogzitve,
            megrendelesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UgyfelElszamolasokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTeljesitesiIdoszakKezdete().map(f -> "teljesitesiIdoszakKezdete=" + f + ", ").orElse("") +
            optionalTeljesitesiIdoszakVege().map(f -> "teljesitesiIdoszakVege=" + f + ", ").orElse("") +
            optionalNapokSzama().map(f -> "napokSzama=" + f + ", ").orElse("") +
            optionalTeljesitesIgazolasonSzereploOsszeg().map(f -> "teljesitesIgazolasonSzereploOsszeg=" + f + ", ").orElse("") +
            optionalKapcsolodoSzamlaSorszamRogzitve().map(f -> "kapcsolodoSzamlaSorszamRogzitve=" + f + ", ").orElse("") +
            optionalMegrendelesId().map(f -> "megrendelesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

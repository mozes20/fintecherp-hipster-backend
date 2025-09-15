package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.CegAlapadatok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.CegAlapadatokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ceg-alapadatoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CegAlapadatokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cegNev;

    private StringFilter cegRovidAzonosito;

    private StringFilter cegSzekhely;

    private StringFilter adoszam;

    private StringFilter cegjegyzekszam;

    private StringFilter cegKozpontiEmail;

    private StringFilter cegKozpontiTel;

    private StringFilter statusz;

    private Boolean distinct;

    public CegAlapadatokCriteria() {}

    public CegAlapadatokCriteria(CegAlapadatokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.cegNev = other.optionalCegNev().map(StringFilter::copy).orElse(null);
        this.cegRovidAzonosito = other.optionalCegRovidAzonosito().map(StringFilter::copy).orElse(null);
        this.cegSzekhely = other.optionalCegSzekhely().map(StringFilter::copy).orElse(null);
        this.adoszam = other.optionalAdoszam().map(StringFilter::copy).orElse(null);
        this.cegjegyzekszam = other.optionalCegjegyzekszam().map(StringFilter::copy).orElse(null);
        this.cegKozpontiEmail = other.optionalCegKozpontiEmail().map(StringFilter::copy).orElse(null);
        this.cegKozpontiTel = other.optionalCegKozpontiTel().map(StringFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CegAlapadatokCriteria copy() {
        return new CegAlapadatokCriteria(this);
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

    public StringFilter getCegNev() {
        return cegNev;
    }

    public Optional<StringFilter> optionalCegNev() {
        return Optional.ofNullable(cegNev);
    }

    public StringFilter cegNev() {
        if (cegNev == null) {
            setCegNev(new StringFilter());
        }
        return cegNev;
    }

    public void setCegNev(StringFilter cegNev) {
        this.cegNev = cegNev;
    }

    public StringFilter getCegRovidAzonosito() {
        return cegRovidAzonosito;
    }

    public Optional<StringFilter> optionalCegRovidAzonosito() {
        return Optional.ofNullable(cegRovidAzonosito);
    }

    public StringFilter cegRovidAzonosito() {
        if (cegRovidAzonosito == null) {
            setCegRovidAzonosito(new StringFilter());
        }
        return cegRovidAzonosito;
    }

    public void setCegRovidAzonosito(StringFilter cegRovidAzonosito) {
        this.cegRovidAzonosito = cegRovidAzonosito;
    }

    public StringFilter getCegSzekhely() {
        return cegSzekhely;
    }

    public Optional<StringFilter> optionalCegSzekhely() {
        return Optional.ofNullable(cegSzekhely);
    }

    public StringFilter cegSzekhely() {
        if (cegSzekhely == null) {
            setCegSzekhely(new StringFilter());
        }
        return cegSzekhely;
    }

    public void setCegSzekhely(StringFilter cegSzekhely) {
        this.cegSzekhely = cegSzekhely;
    }

    public StringFilter getAdoszam() {
        return adoszam;
    }

    public Optional<StringFilter> optionalAdoszam() {
        return Optional.ofNullable(adoszam);
    }

    public StringFilter adoszam() {
        if (adoszam == null) {
            setAdoszam(new StringFilter());
        }
        return adoszam;
    }

    public void setAdoszam(StringFilter adoszam) {
        this.adoszam = adoszam;
    }

    public StringFilter getCegjegyzekszam() {
        return cegjegyzekszam;
    }

    public Optional<StringFilter> optionalCegjegyzekszam() {
        return Optional.ofNullable(cegjegyzekszam);
    }

    public StringFilter cegjegyzekszam() {
        if (cegjegyzekszam == null) {
            setCegjegyzekszam(new StringFilter());
        }
        return cegjegyzekszam;
    }

    public void setCegjegyzekszam(StringFilter cegjegyzekszam) {
        this.cegjegyzekszam = cegjegyzekszam;
    }

    public StringFilter getCegKozpontiEmail() {
        return cegKozpontiEmail;
    }

    public Optional<StringFilter> optionalCegKozpontiEmail() {
        return Optional.ofNullable(cegKozpontiEmail);
    }

    public StringFilter cegKozpontiEmail() {
        if (cegKozpontiEmail == null) {
            setCegKozpontiEmail(new StringFilter());
        }
        return cegKozpontiEmail;
    }

    public void setCegKozpontiEmail(StringFilter cegKozpontiEmail) {
        this.cegKozpontiEmail = cegKozpontiEmail;
    }

    public StringFilter getCegKozpontiTel() {
        return cegKozpontiTel;
    }

    public Optional<StringFilter> optionalCegKozpontiTel() {
        return Optional.ofNullable(cegKozpontiTel);
    }

    public StringFilter cegKozpontiTel() {
        if (cegKozpontiTel == null) {
            setCegKozpontiTel(new StringFilter());
        }
        return cegKozpontiTel;
    }

    public void setCegKozpontiTel(StringFilter cegKozpontiTel) {
        this.cegKozpontiTel = cegKozpontiTel;
    }

    public StringFilter getStatusz() {
        return statusz;
    }

    public Optional<StringFilter> optionalStatusz() {
        return Optional.ofNullable(statusz);
    }

    public StringFilter statusz() {
        if (statusz == null) {
            setStatusz(new StringFilter());
        }
        return statusz;
    }

    public void setStatusz(StringFilter statusz) {
        this.statusz = statusz;
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
        final CegAlapadatokCriteria that = (CegAlapadatokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cegNev, that.cegNev) &&
            Objects.equals(cegRovidAzonosito, that.cegRovidAzonosito) &&
            Objects.equals(cegSzekhely, that.cegSzekhely) &&
            Objects.equals(adoszam, that.adoszam) &&
            Objects.equals(cegjegyzekszam, that.cegjegyzekszam) &&
            Objects.equals(cegKozpontiEmail, that.cegKozpontiEmail) &&
            Objects.equals(cegKozpontiTel, that.cegKozpontiTel) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cegNev,
            cegRovidAzonosito,
            cegSzekhely,
            adoszam,
            cegjegyzekszam,
            cegKozpontiEmail,
            cegKozpontiTel,
            statusz,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CegAlapadatokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCegNev().map(f -> "cegNev=" + f + ", ").orElse("") +
            optionalCegRovidAzonosito().map(f -> "cegRovidAzonosito=" + f + ", ").orElse("") +
            optionalCegSzekhely().map(f -> "cegSzekhely=" + f + ", ").orElse("") +
            optionalAdoszam().map(f -> "adoszam=" + f + ", ").orElse("") +
            optionalCegjegyzekszam().map(f -> "cegjegyzekszam=" + f + ", ").orElse("") +
            optionalCegKozpontiEmail().map(f -> "cegKozpontiEmail=" + f + ", ").orElse("") +
            optionalCegKozpontiTel().map(f -> "cegKozpontiTel=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

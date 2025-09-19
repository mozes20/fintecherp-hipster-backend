package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fintech.erp.domain.SajatCegAlapadatok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.SajatCegAlapadatokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sajat-ceg-alapadatoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegAlapadatokCriteria implements Serializable, Criteria {

    private StringFilter cegCegNev;
    private StringFilter cegCegRovidAzonosito;
    private StringFilter cegCegSzekhely;
    private StringFilter cegAdoszam;
    private StringFilter cegCegjegyzekszam;
    private StringFilter cegKozpontiEmail;
    private StringFilter cegKozpontiTel;
    private StringFilter cegStatusz;

    public StringFilter getCegCegNev() {
        return cegCegNev;
    }

    public void setCegCegNev(StringFilter cegCegNev) {
        this.cegCegNev = cegCegNev;
    }

    public StringFilter getCegCegRovidAzonosito() {
        return cegCegRovidAzonosito;
    }

    public void setCegCegRovidAzonosito(StringFilter cegCegRovidAzonosito) {
        this.cegCegRovidAzonosito = cegCegRovidAzonosito;
    }

    public StringFilter getCegCegSzekhely() {
        return cegCegSzekhely;
    }

    public void setCegCegSzekhely(StringFilter cegCegSzekhely) {
        this.cegCegSzekhely = cegCegSzekhely;
    }

    public StringFilter getCegAdoszam() {
        return cegAdoszam;
    }

    public void setCegAdoszam(StringFilter cegAdoszam) {
        this.cegAdoszam = cegAdoszam;
    }

    public StringFilter getCegCegjegyzekszam() {
        return cegCegjegyzekszam;
    }

    public void setCegCegjegyzekszam(StringFilter cegCegjegyzekszam) {
        this.cegCegjegyzekszam = cegCegjegyzekszam;
    }

    public StringFilter getCegKozpontiEmail() {
        return cegKozpontiEmail;
    }

    public void setCegKozpontiEmail(StringFilter cegKozpontiEmail) {
        this.cegKozpontiEmail = cegKozpontiEmail;
    }

    public StringFilter getCegKozpontiTel() {
        return cegKozpontiTel;
    }

    public void setCegKozpontiTel(StringFilter cegKozpontiTel) {
        this.cegKozpontiTel = cegKozpontiTel;
    }

    public StringFilter getCegStatusz() {
        return cegStatusz;
    }

    public void setCegStatusz(StringFilter cegStatusz) {
        this.cegStatusz = cegStatusz;
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter cegAdminisztraciosHaviKoltseg;

    private StringFilter statusz;

    private LongFilter cegId;

    private Boolean distinct;

    public SajatCegAlapadatokCriteria() {}

    public SajatCegAlapadatokCriteria(SajatCegAlapadatokCriteria other) {
        this.cegCegNev = other.getCegCegNev() != null ? other.getCegCegNev().copy() : null;
        this.cegCegRovidAzonosito = other.getCegCegRovidAzonosito() != null ? other.getCegCegRovidAzonosito().copy() : null;
        this.cegCegSzekhely = other.getCegCegSzekhely() != null ? other.getCegCegSzekhely().copy() : null;
        this.cegAdoszam = other.getCegAdoszam() != null ? other.getCegAdoszam().copy() : null;
        this.cegCegjegyzekszam = other.getCegCegjegyzekszam() != null ? other.getCegCegjegyzekszam().copy() : null;
        this.cegKozpontiEmail = other.getCegKozpontiEmail() != null ? other.getCegKozpontiEmail().copy() : null;
        this.cegKozpontiTel = other.getCegKozpontiTel() != null ? other.getCegKozpontiTel().copy() : null;
        this.cegStatusz = other.getCegStatusz() != null ? other.getCegStatusz().copy() : null;
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.cegAdminisztraciosHaviKoltseg = other.optionalCegAdminisztraciosHaviKoltseg().map(BigDecimalFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.cegId = other.optionalCegId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SajatCegAlapadatokCriteria copy() {
        return new SajatCegAlapadatokCriteria(this);
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

    public BigDecimalFilter getCegAdminisztraciosHaviKoltseg() {
        return cegAdminisztraciosHaviKoltseg;
    }

    public Optional<BigDecimalFilter> optionalCegAdminisztraciosHaviKoltseg() {
        return Optional.ofNullable(cegAdminisztraciosHaviKoltseg);
    }

    public BigDecimalFilter cegAdminisztraciosHaviKoltseg() {
        if (cegAdminisztraciosHaviKoltseg == null) {
            setCegAdminisztraciosHaviKoltseg(new BigDecimalFilter());
        }
        return cegAdminisztraciosHaviKoltseg;
    }

    public void setCegAdminisztraciosHaviKoltseg(BigDecimalFilter cegAdminisztraciosHaviKoltseg) {
        this.cegAdminisztraciosHaviKoltseg = cegAdminisztraciosHaviKoltseg;
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

    public LongFilter getCegId() {
        return cegId;
    }

    public Optional<LongFilter> optionalCegId() {
        return Optional.ofNullable(cegId);
    }

    public LongFilter cegId() {
        if (cegId == null) {
            setCegId(new LongFilter());
        }
        return cegId;
    }

    public void setCegId(LongFilter cegId) {
        this.cegId = cegId;
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
        final SajatCegAlapadatokCriteria that = (SajatCegAlapadatokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cegAdminisztraciosHaviKoltseg, that.cegAdminisztraciosHaviKoltseg) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(cegId, that.cegId) &&
            Objects.equals(cegCegNev, that.cegCegNev) &&
            Objects.equals(cegCegRovidAzonosito, that.cegCegRovidAzonosito) &&
            Objects.equals(cegCegSzekhely, that.cegCegSzekhely) &&
            Objects.equals(cegAdoszam, that.cegAdoszam) &&
            Objects.equals(cegCegjegyzekszam, that.cegCegjegyzekszam) &&
            Objects.equals(cegKozpontiEmail, that.cegKozpontiEmail) &&
            Objects.equals(cegKozpontiTel, that.cegKozpontiTel) &&
            Objects.equals(cegStatusz, that.cegStatusz) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cegAdminisztraciosHaviKoltseg,
            statusz,
            cegId,
            cegCegNev,
            cegCegRovidAzonosito,
            cegCegSzekhely,
            cegAdoszam,
            cegCegjegyzekszam,
            cegKozpontiEmail,
            cegKozpontiTel,
            cegStatusz,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegAlapadatokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCegAdminisztraciosHaviKoltseg().map(f -> "cegAdminisztraciosHaviKoltseg=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalCegId().map(f -> "cegId=" + f + ", ").orElse("") +
            (cegCegNev != null ? "cegCegNev=" + cegCegNev + ", " : "") +
            (cegCegRovidAzonosito != null ? "cegCegRovidAzonosito=" + cegCegRovidAzonosito + ", " : "") +
            (cegCegSzekhely != null ? "cegCegSzekhely=" + cegCegSzekhely + ", " : "") +
                (cegAdoszam != null ? "cegAdoszam=" + cegAdoszam + ", " : "") +
            (cegCegjegyzekszam != null ? "cegCegjegyzekszam=" + cegCegjegyzekszam + ", " : "") +
            (cegKozpontiEmail != null ? "cegKozpontiEmail=" + cegKozpontiEmail + ", " : "") +
            (cegKozpontiTel != null ? "cegKozpontiTel=" + cegKozpontiTel + ", " : "") +
            (cegStatusz != null ? "cegStatusz=" + cegStatusz + ", " : "") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

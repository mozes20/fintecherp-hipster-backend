package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.SajatCegTulajdonosok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.SajatCegTulajdonosokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sajat-ceg-tulajdonosoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegTulajdonosokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter bruttoOsztalek;

    private StringFilter statusz;

    private LongFilter sajatCegId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public SajatCegTulajdonosokCriteria() {}

    public SajatCegTulajdonosokCriteria(SajatCegTulajdonosokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.bruttoOsztalek = other.optionalBruttoOsztalek().map(BigDecimalFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.sajatCegId = other.optionalSajatCegId().map(LongFilter::copy).orElse(null);
        this.maganszemelyId = other.optionalMaganszemelyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SajatCegTulajdonosokCriteria copy() {
        return new SajatCegTulajdonosokCriteria(this);
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

    public BigDecimalFilter getBruttoOsztalek() {
        return bruttoOsztalek;
    }

    public Optional<BigDecimalFilter> optionalBruttoOsztalek() {
        return Optional.ofNullable(bruttoOsztalek);
    }

    public BigDecimalFilter bruttoOsztalek() {
        if (bruttoOsztalek == null) {
            setBruttoOsztalek(new BigDecimalFilter());
        }
        return bruttoOsztalek;
    }

    public void setBruttoOsztalek(BigDecimalFilter bruttoOsztalek) {
        this.bruttoOsztalek = bruttoOsztalek;
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

    public LongFilter getSajatCegId() {
        return sajatCegId;
    }

    public Optional<LongFilter> optionalSajatCegId() {
        return Optional.ofNullable(sajatCegId);
    }

    public LongFilter sajatCegId() {
        if (sajatCegId == null) {
            setSajatCegId(new LongFilter());
        }
        return sajatCegId;
    }

    public void setSajatCegId(LongFilter sajatCegId) {
        this.sajatCegId = sajatCegId;
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
        final SajatCegTulajdonosokCriteria that = (SajatCegTulajdonosokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bruttoOsztalek, that.bruttoOsztalek) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(sajatCegId, that.sajatCegId) &&
            Objects.equals(maganszemelyId, that.maganszemelyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bruttoOsztalek, statusz, sajatCegId, maganszemelyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegTulajdonosokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalBruttoOsztalek().map(f -> "bruttoOsztalek=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalSajatCegId().map(f -> "sajatCegId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

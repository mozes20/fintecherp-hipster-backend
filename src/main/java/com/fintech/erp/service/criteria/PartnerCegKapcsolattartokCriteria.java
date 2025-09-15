package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.PartnerCegKapcsolattartok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.PartnerCegKapcsolattartokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /partner-ceg-kapcsolattartoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegKapcsolattartokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter kapcsolattartoTitulus;

    private StringFilter statusz;

    private LongFilter partnerCegId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public PartnerCegKapcsolattartokCriteria() {}

    public PartnerCegKapcsolattartokCriteria(PartnerCegKapcsolattartokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.kapcsolattartoTitulus = other.optionalKapcsolattartoTitulus().map(StringFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.partnerCegId = other.optionalPartnerCegId().map(LongFilter::copy).orElse(null);
        this.maganszemelyId = other.optionalMaganszemelyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PartnerCegKapcsolattartokCriteria copy() {
        return new PartnerCegKapcsolattartokCriteria(this);
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

    public StringFilter getKapcsolattartoTitulus() {
        return kapcsolattartoTitulus;
    }

    public Optional<StringFilter> optionalKapcsolattartoTitulus() {
        return Optional.ofNullable(kapcsolattartoTitulus);
    }

    public StringFilter kapcsolattartoTitulus() {
        if (kapcsolattartoTitulus == null) {
            setKapcsolattartoTitulus(new StringFilter());
        }
        return kapcsolattartoTitulus;
    }

    public void setKapcsolattartoTitulus(StringFilter kapcsolattartoTitulus) {
        this.kapcsolattartoTitulus = kapcsolattartoTitulus;
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

    public LongFilter getPartnerCegId() {
        return partnerCegId;
    }

    public Optional<LongFilter> optionalPartnerCegId() {
        return Optional.ofNullable(partnerCegId);
    }

    public LongFilter partnerCegId() {
        if (partnerCegId == null) {
            setPartnerCegId(new LongFilter());
        }
        return partnerCegId;
    }

    public void setPartnerCegId(LongFilter partnerCegId) {
        this.partnerCegId = partnerCegId;
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
        final PartnerCegKapcsolattartokCriteria that = (PartnerCegKapcsolattartokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(kapcsolattartoTitulus, that.kapcsolattartoTitulus) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(partnerCegId, that.partnerCegId) &&
            Objects.equals(maganszemelyId, that.maganszemelyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kapcsolattartoTitulus, statusz, partnerCegId, maganszemelyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerCegKapcsolattartokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalKapcsolattartoTitulus().map(f -> "kapcsolattartoTitulus=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalPartnerCegId().map(f -> "partnerCegId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

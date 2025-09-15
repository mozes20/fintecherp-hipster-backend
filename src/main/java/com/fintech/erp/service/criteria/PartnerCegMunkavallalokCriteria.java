package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.PartnerCegMunkavallalok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.PartnerCegMunkavallalokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /partner-ceg-munkavallaloks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegMunkavallalokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statusz;

    private LongFilter partnerCegId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public PartnerCegMunkavallalokCriteria() {}

    public PartnerCegMunkavallalokCriteria(PartnerCegMunkavallalokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.partnerCegId = other.optionalPartnerCegId().map(LongFilter::copy).orElse(null);
        this.maganszemelyId = other.optionalMaganszemelyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PartnerCegMunkavallalokCriteria copy() {
        return new PartnerCegMunkavallalokCriteria(this);
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
        final PartnerCegMunkavallalokCriteria that = (PartnerCegMunkavallalokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(partnerCegId, that.partnerCegId) &&
            Objects.equals(maganszemelyId, that.maganszemelyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusz, partnerCegId, maganszemelyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerCegMunkavallalokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalPartnerCegId().map(f -> "partnerCegId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

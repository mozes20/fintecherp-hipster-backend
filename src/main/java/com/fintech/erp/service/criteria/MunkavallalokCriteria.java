package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.Munkavallalok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.MunkavallalokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /munkavallaloks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MunkavallalokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter foglalkoztatasTipusa;

    private LocalDateFilter foglalkoztatasKezdete;

    private LocalDateFilter foglalkoztatasVege;

    private LongFilter sajatCegId;

    private LongFilter maganszemelyId;

    private Boolean distinct;

    public MunkavallalokCriteria() {}

    public MunkavallalokCriteria(MunkavallalokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.foglalkoztatasTipusa = other.optionalFoglalkoztatasTipusa().map(StringFilter::copy).orElse(null);
        this.foglalkoztatasKezdete = other.optionalFoglalkoztatasKezdete().map(LocalDateFilter::copy).orElse(null);
        this.foglalkoztatasVege = other.optionalFoglalkoztatasVege().map(LocalDateFilter::copy).orElse(null);
        this.sajatCegId = other.optionalSajatCegId().map(LongFilter::copy).orElse(null);
        this.maganszemelyId = other.optionalMaganszemelyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MunkavallalokCriteria copy() {
        return new MunkavallalokCriteria(this);
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

    public StringFilter getFoglalkoztatasTipusa() {
        return foglalkoztatasTipusa;
    }

    public Optional<StringFilter> optionalFoglalkoztatasTipusa() {
        return Optional.ofNullable(foglalkoztatasTipusa);
    }

    public StringFilter foglalkoztatasTipusa() {
        if (foglalkoztatasTipusa == null) {
            setFoglalkoztatasTipusa(new StringFilter());
        }
        return foglalkoztatasTipusa;
    }

    public void setFoglalkoztatasTipusa(StringFilter foglalkoztatasTipusa) {
        this.foglalkoztatasTipusa = foglalkoztatasTipusa;
    }

    public LocalDateFilter getFoglalkoztatasKezdete() {
        return foglalkoztatasKezdete;
    }

    public Optional<LocalDateFilter> optionalFoglalkoztatasKezdete() {
        return Optional.ofNullable(foglalkoztatasKezdete);
    }

    public LocalDateFilter foglalkoztatasKezdete() {
        if (foglalkoztatasKezdete == null) {
            setFoglalkoztatasKezdete(new LocalDateFilter());
        }
        return foglalkoztatasKezdete;
    }

    public void setFoglalkoztatasKezdete(LocalDateFilter foglalkoztatasKezdete) {
        this.foglalkoztatasKezdete = foglalkoztatasKezdete;
    }

    public LocalDateFilter getFoglalkoztatasVege() {
        return foglalkoztatasVege;
    }

    public Optional<LocalDateFilter> optionalFoglalkoztatasVege() {
        return Optional.ofNullable(foglalkoztatasVege);
    }

    public LocalDateFilter foglalkoztatasVege() {
        if (foglalkoztatasVege == null) {
            setFoglalkoztatasVege(new LocalDateFilter());
        }
        return foglalkoztatasVege;
    }

    public void setFoglalkoztatasVege(LocalDateFilter foglalkoztatasVege) {
        this.foglalkoztatasVege = foglalkoztatasVege;
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
        final MunkavallalokCriteria that = (MunkavallalokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(foglalkoztatasTipusa, that.foglalkoztatasTipusa) &&
            Objects.equals(foglalkoztatasKezdete, that.foglalkoztatasKezdete) &&
            Objects.equals(foglalkoztatasVege, that.foglalkoztatasVege) &&
            Objects.equals(sajatCegId, that.sajatCegId) &&
            Objects.equals(maganszemelyId, that.maganszemelyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foglalkoztatasTipusa, foglalkoztatasKezdete, foglalkoztatasVege, sajatCegId, maganszemelyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MunkavallalokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFoglalkoztatasTipusa().map(f -> "foglalkoztatasTipusa=" + f + ", ").orElse("") +
            optionalFoglalkoztatasKezdete().map(f -> "foglalkoztatasKezdete=" + f + ", ").orElse("") +
            optionalFoglalkoztatasVege().map(f -> "foglalkoztatasVege=" + f + ", ").orElse("") +
            optionalSajatCegId().map(f -> "sajatCegId=" + f + ", ").orElse("") +
            optionalMaganszemelyId().map(f -> "maganszemelyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

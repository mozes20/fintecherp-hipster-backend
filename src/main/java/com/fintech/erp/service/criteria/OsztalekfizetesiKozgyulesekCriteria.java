package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesek} entity. This class is used
 * in {@link com.fintech.erp.web.rest.OsztalekfizetesiKozgyulesekResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /osztalekfizetesi-kozgyuleseks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OsztalekfizetesiKozgyulesekCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter kozgyulesDatum;

    private BooleanFilter kozgyulesiJegyzokonyvGeneralta;

    private BooleanFilter kozgyulesiJegyzokonyvAlairt;

    private LongFilter sajatCegId;

    private Boolean distinct;

    public OsztalekfizetesiKozgyulesekCriteria() {}

    public OsztalekfizetesiKozgyulesekCriteria(OsztalekfizetesiKozgyulesekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.kozgyulesDatum = other.optionalKozgyulesDatum().map(LocalDateFilter::copy).orElse(null);
        this.kozgyulesiJegyzokonyvGeneralta = other.optionalKozgyulesiJegyzokonyvGeneralta().map(BooleanFilter::copy).orElse(null);
        this.kozgyulesiJegyzokonyvAlairt = other.optionalKozgyulesiJegyzokonyvAlairt().map(BooleanFilter::copy).orElse(null);
        this.sajatCegId = other.optionalSajatCegId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public OsztalekfizetesiKozgyulesekCriteria copy() {
        return new OsztalekfizetesiKozgyulesekCriteria(this);
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

    public LocalDateFilter getKozgyulesDatum() {
        return kozgyulesDatum;
    }

    public Optional<LocalDateFilter> optionalKozgyulesDatum() {
        return Optional.ofNullable(kozgyulesDatum);
    }

    public LocalDateFilter kozgyulesDatum() {
        if (kozgyulesDatum == null) {
            setKozgyulesDatum(new LocalDateFilter());
        }
        return kozgyulesDatum;
    }

    public void setKozgyulesDatum(LocalDateFilter kozgyulesDatum) {
        this.kozgyulesDatum = kozgyulesDatum;
    }

    public BooleanFilter getKozgyulesiJegyzokonyvGeneralta() {
        return kozgyulesiJegyzokonyvGeneralta;
    }

    public Optional<BooleanFilter> optionalKozgyulesiJegyzokonyvGeneralta() {
        return Optional.ofNullable(kozgyulesiJegyzokonyvGeneralta);
    }

    public BooleanFilter kozgyulesiJegyzokonyvGeneralta() {
        if (kozgyulesiJegyzokonyvGeneralta == null) {
            setKozgyulesiJegyzokonyvGeneralta(new BooleanFilter());
        }
        return kozgyulesiJegyzokonyvGeneralta;
    }

    public void setKozgyulesiJegyzokonyvGeneralta(BooleanFilter kozgyulesiJegyzokonyvGeneralta) {
        this.kozgyulesiJegyzokonyvGeneralta = kozgyulesiJegyzokonyvGeneralta;
    }

    public BooleanFilter getKozgyulesiJegyzokonyvAlairt() {
        return kozgyulesiJegyzokonyvAlairt;
    }

    public Optional<BooleanFilter> optionalKozgyulesiJegyzokonyvAlairt() {
        return Optional.ofNullable(kozgyulesiJegyzokonyvAlairt);
    }

    public BooleanFilter kozgyulesiJegyzokonyvAlairt() {
        if (kozgyulesiJegyzokonyvAlairt == null) {
            setKozgyulesiJegyzokonyvAlairt(new BooleanFilter());
        }
        return kozgyulesiJegyzokonyvAlairt;
    }

    public void setKozgyulesiJegyzokonyvAlairt(BooleanFilter kozgyulesiJegyzokonyvAlairt) {
        this.kozgyulesiJegyzokonyvAlairt = kozgyulesiJegyzokonyvAlairt;
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
        final OsztalekfizetesiKozgyulesekCriteria that = (OsztalekfizetesiKozgyulesekCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(kozgyulesDatum, that.kozgyulesDatum) &&
            Objects.equals(kozgyulesiJegyzokonyvGeneralta, that.kozgyulesiJegyzokonyvGeneralta) &&
            Objects.equals(kozgyulesiJegyzokonyvAlairt, that.kozgyulesiJegyzokonyvAlairt) &&
            Objects.equals(sajatCegId, that.sajatCegId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kozgyulesDatum, kozgyulesiJegyzokonyvGeneralta, kozgyulesiJegyzokonyvAlairt, sajatCegId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsztalekfizetesiKozgyulesekCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalKozgyulesDatum().map(f -> "kozgyulesDatum=" + f + ", ").orElse("") +
            optionalKozgyulesiJegyzokonyvGeneralta().map(f -> "kozgyulesiJegyzokonyvGeneralta=" + f + ", ").orElse("") +
            optionalKozgyulesiJegyzokonyvAlairt().map(f -> "kozgyulesiJegyzokonyvAlairt=" + f + ", ").orElse("") +
            optionalSajatCegId().map(f -> "sajatCegId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

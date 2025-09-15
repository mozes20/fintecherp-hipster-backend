package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.EfoFoglalkoztatasok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.EfoFoglalkoztatasokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /efo-foglalkoztatasoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EfoFoglalkoztatasokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter datum;

    private BigDecimalFilter osszeg;

    private BooleanFilter generaltEfoSzerzodes;

    private BooleanFilter alairtEfoSzerzodes;

    private LongFilter munkavallaloId;

    private Boolean distinct;

    public EfoFoglalkoztatasokCriteria() {}

    public EfoFoglalkoztatasokCriteria(EfoFoglalkoztatasokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.datum = other.optionalDatum().map(LocalDateFilter::copy).orElse(null);
        this.osszeg = other.optionalOsszeg().map(BigDecimalFilter::copy).orElse(null);
        this.generaltEfoSzerzodes = other.optionalGeneraltEfoSzerzodes().map(BooleanFilter::copy).orElse(null);
        this.alairtEfoSzerzodes = other.optionalAlairtEfoSzerzodes().map(BooleanFilter::copy).orElse(null);
        this.munkavallaloId = other.optionalMunkavallaloId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EfoFoglalkoztatasokCriteria copy() {
        return new EfoFoglalkoztatasokCriteria(this);
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

    public LocalDateFilter getDatum() {
        return datum;
    }

    public Optional<LocalDateFilter> optionalDatum() {
        return Optional.ofNullable(datum);
    }

    public LocalDateFilter datum() {
        if (datum == null) {
            setDatum(new LocalDateFilter());
        }
        return datum;
    }

    public void setDatum(LocalDateFilter datum) {
        this.datum = datum;
    }

    public BigDecimalFilter getOsszeg() {
        return osszeg;
    }

    public Optional<BigDecimalFilter> optionalOsszeg() {
        return Optional.ofNullable(osszeg);
    }

    public BigDecimalFilter osszeg() {
        if (osszeg == null) {
            setOsszeg(new BigDecimalFilter());
        }
        return osszeg;
    }

    public void setOsszeg(BigDecimalFilter osszeg) {
        this.osszeg = osszeg;
    }

    public BooleanFilter getGeneraltEfoSzerzodes() {
        return generaltEfoSzerzodes;
    }

    public Optional<BooleanFilter> optionalGeneraltEfoSzerzodes() {
        return Optional.ofNullable(generaltEfoSzerzodes);
    }

    public BooleanFilter generaltEfoSzerzodes() {
        if (generaltEfoSzerzodes == null) {
            setGeneraltEfoSzerzodes(new BooleanFilter());
        }
        return generaltEfoSzerzodes;
    }

    public void setGeneraltEfoSzerzodes(BooleanFilter generaltEfoSzerzodes) {
        this.generaltEfoSzerzodes = generaltEfoSzerzodes;
    }

    public BooleanFilter getAlairtEfoSzerzodes() {
        return alairtEfoSzerzodes;
    }

    public Optional<BooleanFilter> optionalAlairtEfoSzerzodes() {
        return Optional.ofNullable(alairtEfoSzerzodes);
    }

    public BooleanFilter alairtEfoSzerzodes() {
        if (alairtEfoSzerzodes == null) {
            setAlairtEfoSzerzodes(new BooleanFilter());
        }
        return alairtEfoSzerzodes;
    }

    public void setAlairtEfoSzerzodes(BooleanFilter alairtEfoSzerzodes) {
        this.alairtEfoSzerzodes = alairtEfoSzerzodes;
    }

    public LongFilter getMunkavallaloId() {
        return munkavallaloId;
    }

    public Optional<LongFilter> optionalMunkavallaloId() {
        return Optional.ofNullable(munkavallaloId);
    }

    public LongFilter munkavallaloId() {
        if (munkavallaloId == null) {
            setMunkavallaloId(new LongFilter());
        }
        return munkavallaloId;
    }

    public void setMunkavallaloId(LongFilter munkavallaloId) {
        this.munkavallaloId = munkavallaloId;
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
        final EfoFoglalkoztatasokCriteria that = (EfoFoglalkoztatasokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(datum, that.datum) &&
            Objects.equals(osszeg, that.osszeg) &&
            Objects.equals(generaltEfoSzerzodes, that.generaltEfoSzerzodes) &&
            Objects.equals(alairtEfoSzerzodes, that.alairtEfoSzerzodes) &&
            Objects.equals(munkavallaloId, that.munkavallaloId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datum, osszeg, generaltEfoSzerzodes, alairtEfoSzerzodes, munkavallaloId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EfoFoglalkoztatasokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDatum().map(f -> "datum=" + f + ", ").orElse("") +
            optionalOsszeg().map(f -> "osszeg=" + f + ", ").orElse("") +
            optionalGeneraltEfoSzerzodes().map(f -> "generaltEfoSzerzodes=" + f + ", ").orElse("") +
            optionalAlairtEfoSzerzodes().map(f -> "alairtEfoSzerzodes=" + f + ", ").orElse("") +
            optionalMunkavallaloId().map(f -> "munkavallaloId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

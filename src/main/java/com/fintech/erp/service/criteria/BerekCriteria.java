package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.Berek} entity. This class is used
 * in {@link com.fintech.erp.web.rest.BerekResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bereks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BerekCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter ervenyessegKezdete;

    private BigDecimalFilter bruttoHaviMunkaberVagyNapdij;

    private StringFilter munkaszerzodes;

    private BigDecimalFilter teljesKoltseg;

    private LongFilter munkavallaloId;

    private Boolean distinct;

    public BerekCriteria() {}

    public BerekCriteria(BerekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.ervenyessegKezdete = other.optionalErvenyessegKezdete().map(LocalDateFilter::copy).orElse(null);
        this.bruttoHaviMunkaberVagyNapdij = other.optionalBruttoHaviMunkaberVagyNapdij().map(BigDecimalFilter::copy).orElse(null);
        this.munkaszerzodes = other.optionalMunkaszerzodes().map(StringFilter::copy).orElse(null);
        this.teljesKoltseg = other.optionalTeljesKoltseg().map(BigDecimalFilter::copy).orElse(null);
        this.munkavallaloId = other.optionalMunkavallaloId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BerekCriteria copy() {
        return new BerekCriteria(this);
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

    public LocalDateFilter getErvenyessegKezdete() {
        return ervenyessegKezdete;
    }

    public Optional<LocalDateFilter> optionalErvenyessegKezdete() {
        return Optional.ofNullable(ervenyessegKezdete);
    }

    public LocalDateFilter ervenyessegKezdete() {
        if (ervenyessegKezdete == null) {
            setErvenyessegKezdete(new LocalDateFilter());
        }
        return ervenyessegKezdete;
    }

    public void setErvenyessegKezdete(LocalDateFilter ervenyessegKezdete) {
        this.ervenyessegKezdete = ervenyessegKezdete;
    }

    public BigDecimalFilter getBruttoHaviMunkaberVagyNapdij() {
        return bruttoHaviMunkaberVagyNapdij;
    }

    public Optional<BigDecimalFilter> optionalBruttoHaviMunkaberVagyNapdij() {
        return Optional.ofNullable(bruttoHaviMunkaberVagyNapdij);
    }

    public BigDecimalFilter bruttoHaviMunkaberVagyNapdij() {
        if (bruttoHaviMunkaberVagyNapdij == null) {
            setBruttoHaviMunkaberVagyNapdij(new BigDecimalFilter());
        }
        return bruttoHaviMunkaberVagyNapdij;
    }

    public void setBruttoHaviMunkaberVagyNapdij(BigDecimalFilter bruttoHaviMunkaberVagyNapdij) {
        this.bruttoHaviMunkaberVagyNapdij = bruttoHaviMunkaberVagyNapdij;
    }

    public StringFilter getMunkaszerzodes() {
        return munkaszerzodes;
    }

    public Optional<StringFilter> optionalMunkaszerzodes() {
        return Optional.ofNullable(munkaszerzodes);
    }

    public StringFilter munkaszerzodes() {
        if (munkaszerzodes == null) {
            setMunkaszerzodes(new StringFilter());
        }
        return munkaszerzodes;
    }

    public void setMunkaszerzodes(StringFilter munkaszerzodes) {
        this.munkaszerzodes = munkaszerzodes;
    }

    public BigDecimalFilter getTeljesKoltseg() {
        return teljesKoltseg;
    }

    public Optional<BigDecimalFilter> optionalTeljesKoltseg() {
        return Optional.ofNullable(teljesKoltseg);
    }

    public BigDecimalFilter teljesKoltseg() {
        if (teljesKoltseg == null) {
            setTeljesKoltseg(new BigDecimalFilter());
        }
        return teljesKoltseg;
    }

    public void setTeljesKoltseg(BigDecimalFilter teljesKoltseg) {
        this.teljesKoltseg = teljesKoltseg;
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
        final BerekCriteria that = (BerekCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ervenyessegKezdete, that.ervenyessegKezdete) &&
            Objects.equals(bruttoHaviMunkaberVagyNapdij, that.bruttoHaviMunkaberVagyNapdij) &&
            Objects.equals(munkaszerzodes, that.munkaszerzodes) &&
            Objects.equals(teljesKoltseg, that.teljesKoltseg) &&
            Objects.equals(munkavallaloId, that.munkavallaloId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ervenyessegKezdete, bruttoHaviMunkaberVagyNapdij, munkaszerzodes, teljesKoltseg, munkavallaloId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BerekCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalErvenyessegKezdete().map(f -> "ervenyessegKezdete=" + f + ", ").orElse("") +
            optionalBruttoHaviMunkaberVagyNapdij().map(f -> "bruttoHaviMunkaberVagyNapdij=" + f + ", ").orElse("") +
            optionalMunkaszerzodes().map(f -> "munkaszerzodes=" + f + ", ").orElse("") +
            optionalTeljesKoltseg().map(f -> "teljesKoltseg=" + f + ", ").orElse("") +
            optionalMunkavallaloId().map(f -> "munkavallaloId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

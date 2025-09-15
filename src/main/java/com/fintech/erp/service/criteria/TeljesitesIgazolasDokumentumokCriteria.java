package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.TeljesitesIgazolasDokumentumok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.TeljesitesIgazolasDokumentumokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /teljesites-igazolas-dokumentumoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeljesitesIgazolasDokumentumokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dokumentumTipusa;

    private StringFilter dokumentum;

    private LongFilter teljesitesIgazolasId;

    private Boolean distinct;

    public TeljesitesIgazolasDokumentumokCriteria() {}

    public TeljesitesIgazolasDokumentumokCriteria(TeljesitesIgazolasDokumentumokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dokumentumTipusa = other.optionalDokumentumTipusa().map(StringFilter::copy).orElse(null);
        this.dokumentum = other.optionalDokumentum().map(StringFilter::copy).orElse(null);
        this.teljesitesIgazolasId = other.optionalTeljesitesIgazolasId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TeljesitesIgazolasDokumentumokCriteria copy() {
        return new TeljesitesIgazolasDokumentumokCriteria(this);
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

    public StringFilter getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public Optional<StringFilter> optionalDokumentumTipusa() {
        return Optional.ofNullable(dokumentumTipusa);
    }

    public StringFilter dokumentumTipusa() {
        if (dokumentumTipusa == null) {
            setDokumentumTipusa(new StringFilter());
        }
        return dokumentumTipusa;
    }

    public void setDokumentumTipusa(StringFilter dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public StringFilter getDokumentum() {
        return dokumentum;
    }

    public Optional<StringFilter> optionalDokumentum() {
        return Optional.ofNullable(dokumentum);
    }

    public StringFilter dokumentum() {
        if (dokumentum == null) {
            setDokumentum(new StringFilter());
        }
        return dokumentum;
    }

    public void setDokumentum(StringFilter dokumentum) {
        this.dokumentum = dokumentum;
    }

    public LongFilter getTeljesitesIgazolasId() {
        return teljesitesIgazolasId;
    }

    public Optional<LongFilter> optionalTeljesitesIgazolasId() {
        return Optional.ofNullable(teljesitesIgazolasId);
    }

    public LongFilter teljesitesIgazolasId() {
        if (teljesitesIgazolasId == null) {
            setTeljesitesIgazolasId(new LongFilter());
        }
        return teljesitesIgazolasId;
    }

    public void setTeljesitesIgazolasId(LongFilter teljesitesIgazolasId) {
        this.teljesitesIgazolasId = teljesitesIgazolasId;
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
        final TeljesitesIgazolasDokumentumokCriteria that = (TeljesitesIgazolasDokumentumokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dokumentumTipusa, that.dokumentumTipusa) &&
            Objects.equals(dokumentum, that.dokumentum) &&
            Objects.equals(teljesitesIgazolasId, that.teljesitesIgazolasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dokumentumTipusa, dokumentum, teljesitesIgazolasId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeljesitesIgazolasDokumentumokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDokumentumTipusa().map(f -> "dokumentumTipusa=" + f + ", ").orElse("") +
            optionalDokumentum().map(f -> "dokumentum=" + f + ", ").orElse("") +
            optionalTeljesitesIgazolasId().map(f -> "teljesitesIgazolasId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

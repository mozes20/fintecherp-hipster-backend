package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.MegrendelesDokumentumok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.MegrendelesDokumentumokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /megrendeles-dokumentumoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesDokumentumokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dokumentumTipusa;

    private StringFilter dokumentum;

    private LongFilter megrendelesId;

    private Boolean distinct;

    public MegrendelesDokumentumokCriteria() {}

    public MegrendelesDokumentumokCriteria(MegrendelesDokumentumokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dokumentumTipusa = other.optionalDokumentumTipusa().map(StringFilter::copy).orElse(null);
        this.dokumentum = other.optionalDokumentum().map(StringFilter::copy).orElse(null);
        this.megrendelesId = other.optionalMegrendelesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MegrendelesDokumentumokCriteria copy() {
        return new MegrendelesDokumentumokCriteria(this);
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

    public LongFilter getMegrendelesId() {
        return megrendelesId;
    }

    public Optional<LongFilter> optionalMegrendelesId() {
        return Optional.ofNullable(megrendelesId);
    }

    public LongFilter megrendelesId() {
        if (megrendelesId == null) {
            setMegrendelesId(new LongFilter());
        }
        return megrendelesId;
    }

    public void setMegrendelesId(LongFilter megrendelesId) {
        this.megrendelesId = megrendelesId;
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
        final MegrendelesDokumentumokCriteria that = (MegrendelesDokumentumokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dokumentumTipusa, that.dokumentumTipusa) &&
            Objects.equals(dokumentum, that.dokumentum) &&
            Objects.equals(megrendelesId, that.megrendelesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dokumentumTipusa, dokumentum, megrendelesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MegrendelesDokumentumokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDokumentumTipusa().map(f -> "dokumentumTipusa=" + f + ", ").orElse("") +
            optionalDokumentum().map(f -> "dokumentum=" + f + ", ").orElse("") +
            optionalMegrendelesId().map(f -> "megrendelesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

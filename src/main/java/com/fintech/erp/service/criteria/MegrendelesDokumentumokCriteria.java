package com.fintech.erp.service.criteria;

import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

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

    public static class MegrendelesDokumentumTipusFilter extends Filter<MegrendelesDokumentumTipus> {

        public MegrendelesDokumentumTipusFilter() {}

        public MegrendelesDokumentumTipusFilter(MegrendelesDokumentumTipusFilter filter) {
            super(filter);
        }

        @Override
        public MegrendelesDokumentumTipusFilter copy() {
            return new MegrendelesDokumentumTipusFilter(this);
        }
    }

    private LongFilter id;

    private MegrendelesDokumentumTipusFilter dokumentumTipusa;

    private StringFilter dokumentum;

    private StringFilter dokumentumUrl;

    private StringFilter dokumentumAzonosito;

    private LongFilter megrendelesId;

    private Boolean distinct;

    public MegrendelesDokumentumokCriteria() {}

    public MegrendelesDokumentumokCriteria(MegrendelesDokumentumokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dokumentumTipusa = other.optionalDokumentumTipusa().map(MegrendelesDokumentumTipusFilter::copy).orElse(null);
        this.dokumentum = other.optionalDokumentum().map(StringFilter::copy).orElse(null);
        this.dokumentumUrl = other.optionalDokumentumUrl().map(StringFilter::copy).orElse(null);
        this.dokumentumAzonosito = other.optionalDokumentumAzonosito().map(StringFilter::copy).orElse(null);
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

    public MegrendelesDokumentumTipusFilter getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public Optional<MegrendelesDokumentumTipusFilter> optionalDokumentumTipusa() {
        return Optional.ofNullable(dokumentumTipusa);
    }

    public MegrendelesDokumentumTipusFilter dokumentumTipusa() {
        if (dokumentumTipusa == null) {
            setDokumentumTipusa(new MegrendelesDokumentumTipusFilter());
        }
        return dokumentumTipusa;
    }

    public void setDokumentumTipusa(MegrendelesDokumentumTipusFilter dokumentumTipusa) {
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

    public StringFilter getDokumentumUrl() {
        return dokumentumUrl;
    }

    public Optional<StringFilter> optionalDokumentumUrl() {
        return Optional.ofNullable(dokumentumUrl);
    }

    public StringFilter dokumentumUrl() {
        if (dokumentumUrl == null) {
            setDokumentumUrl(new StringFilter());
        }
        return dokumentumUrl;
    }

    public void setDokumentumUrl(StringFilter dokumentumUrl) {
        this.dokumentumUrl = dokumentumUrl;
    }

    public StringFilter getDokumentumAzonosito() {
        return dokumentumAzonosito;
    }

    public Optional<StringFilter> optionalDokumentumAzonosito() {
        return Optional.ofNullable(dokumentumAzonosito);
    }

    public StringFilter dokumentumAzonosito() {
        if (dokumentumAzonosito == null) {
            setDokumentumAzonosito(new StringFilter());
        }
        return dokumentumAzonosito;
    }

    public void setDokumentumAzonosito(StringFilter dokumentumAzonosito) {
        this.dokumentumAzonosito = dokumentumAzonosito;
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
            Objects.equals(dokumentumUrl, that.dokumentumUrl) &&
            Objects.equals(dokumentumAzonosito, that.dokumentumAzonosito) &&
            Objects.equals(megrendelesId, that.megrendelesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dokumentumTipusa, dokumentum, dokumentumUrl, dokumentumAzonosito, megrendelesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MegrendelesDokumentumokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDokumentumTipusa().map(f -> "dokumentumTipusa=" + f + ", ").orElse("") +
            optionalDokumentum().map(f -> "dokumentum=" + f + ", ").orElse("") +
            optionalDokumentumUrl().map(f -> "dokumentumUrl=" + f + ", ").orElse("") +
            optionalDokumentumAzonosito().map(f -> "dokumentumAzonosito=" + f + ", ").orElse("") +
            optionalMegrendelesId().map(f -> "megrendelesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

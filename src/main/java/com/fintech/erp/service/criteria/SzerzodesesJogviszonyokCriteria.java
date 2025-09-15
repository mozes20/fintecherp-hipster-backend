package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.SzerzodesesJogviszonyok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.SzerzodesesJogviszonyokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /szerzodeses-jogviszonyoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SzerzodesesJogviszonyokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter szerzodesAzonosito;

    private LocalDateFilter jogviszonyKezdete;

    private LocalDateFilter jogviszonyLejarata;

    private LongFilter megrendeloCegId;

    private LongFilter vallalkozoCegId;

    private Boolean distinct;

    public SzerzodesesJogviszonyokCriteria() {}

    public SzerzodesesJogviszonyokCriteria(SzerzodesesJogviszonyokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.szerzodesAzonosito = other.optionalSzerzodesAzonosito().map(StringFilter::copy).orElse(null);
        this.jogviszonyKezdete = other.optionalJogviszonyKezdete().map(LocalDateFilter::copy).orElse(null);
        this.jogviszonyLejarata = other.optionalJogviszonyLejarata().map(LocalDateFilter::copy).orElse(null);
        this.megrendeloCegId = other.optionalMegrendeloCegId().map(LongFilter::copy).orElse(null);
        this.vallalkozoCegId = other.optionalVallalkozoCegId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SzerzodesesJogviszonyokCriteria copy() {
        return new SzerzodesesJogviszonyokCriteria(this);
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

    public StringFilter getSzerzodesAzonosito() {
        return szerzodesAzonosito;
    }

    public Optional<StringFilter> optionalSzerzodesAzonosito() {
        return Optional.ofNullable(szerzodesAzonosito);
    }

    public StringFilter szerzodesAzonosito() {
        if (szerzodesAzonosito == null) {
            setSzerzodesAzonosito(new StringFilter());
        }
        return szerzodesAzonosito;
    }

    public void setSzerzodesAzonosito(StringFilter szerzodesAzonosito) {
        this.szerzodesAzonosito = szerzodesAzonosito;
    }

    public LocalDateFilter getJogviszonyKezdete() {
        return jogviszonyKezdete;
    }

    public Optional<LocalDateFilter> optionalJogviszonyKezdete() {
        return Optional.ofNullable(jogviszonyKezdete);
    }

    public LocalDateFilter jogviszonyKezdete() {
        if (jogviszonyKezdete == null) {
            setJogviszonyKezdete(new LocalDateFilter());
        }
        return jogviszonyKezdete;
    }

    public void setJogviszonyKezdete(LocalDateFilter jogviszonyKezdete) {
        this.jogviszonyKezdete = jogviszonyKezdete;
    }

    public LocalDateFilter getJogviszonyLejarata() {
        return jogviszonyLejarata;
    }

    public Optional<LocalDateFilter> optionalJogviszonyLejarata() {
        return Optional.ofNullable(jogviszonyLejarata);
    }

    public LocalDateFilter jogviszonyLejarata() {
        if (jogviszonyLejarata == null) {
            setJogviszonyLejarata(new LocalDateFilter());
        }
        return jogviszonyLejarata;
    }

    public void setJogviszonyLejarata(LocalDateFilter jogviszonyLejarata) {
        this.jogviszonyLejarata = jogviszonyLejarata;
    }

    public LongFilter getMegrendeloCegId() {
        return megrendeloCegId;
    }

    public Optional<LongFilter> optionalMegrendeloCegId() {
        return Optional.ofNullable(megrendeloCegId);
    }

    public LongFilter megrendeloCegId() {
        if (megrendeloCegId == null) {
            setMegrendeloCegId(new LongFilter());
        }
        return megrendeloCegId;
    }

    public void setMegrendeloCegId(LongFilter megrendeloCegId) {
        this.megrendeloCegId = megrendeloCegId;
    }

    public LongFilter getVallalkozoCegId() {
        return vallalkozoCegId;
    }

    public Optional<LongFilter> optionalVallalkozoCegId() {
        return Optional.ofNullable(vallalkozoCegId);
    }

    public LongFilter vallalkozoCegId() {
        if (vallalkozoCegId == null) {
            setVallalkozoCegId(new LongFilter());
        }
        return vallalkozoCegId;
    }

    public void setVallalkozoCegId(LongFilter vallalkozoCegId) {
        this.vallalkozoCegId = vallalkozoCegId;
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
        final SzerzodesesJogviszonyokCriteria that = (SzerzodesesJogviszonyokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(szerzodesAzonosito, that.szerzodesAzonosito) &&
            Objects.equals(jogviszonyKezdete, that.jogviszonyKezdete) &&
            Objects.equals(jogviszonyLejarata, that.jogviszonyLejarata) &&
            Objects.equals(megrendeloCegId, that.megrendeloCegId) &&
            Objects.equals(vallalkozoCegId, that.vallalkozoCegId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, szerzodesAzonosito, jogviszonyKezdete, jogviszonyLejarata, megrendeloCegId, vallalkozoCegId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SzerzodesesJogviszonyokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSzerzodesAzonosito().map(f -> "szerzodesAzonosito=" + f + ", ").orElse("") +
            optionalJogviszonyKezdete().map(f -> "jogviszonyKezdete=" + f + ", ").orElse("") +
            optionalJogviszonyLejarata().map(f -> "jogviszonyLejarata=" + f + ", ").orElse("") +
            optionalMegrendeloCegId().map(f -> "megrendeloCegId=" + f + ", ").orElse("") +
            optionalVallalkozoCegId().map(f -> "vallalkozoCegId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.Timesheetek} entity. This class is used
 * in {@link com.fintech.erp.web.rest.TimesheetekResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /timesheeteks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimesheetekCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter datum;

    private StringFilter munkanapStatusza;

    private StringFilter statusz;

    private LongFilter munkavallaloId;

    private Boolean distinct;

    public TimesheetekCriteria() {}

    public TimesheetekCriteria(TimesheetekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.datum = other.optionalDatum().map(LocalDateFilter::copy).orElse(null);
        this.munkanapStatusza = other.optionalMunkanapStatusza().map(StringFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.munkavallaloId = other.optionalMunkavallaloId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TimesheetekCriteria copy() {
        return new TimesheetekCriteria(this);
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

    public StringFilter getMunkanapStatusza() {
        return munkanapStatusza;
    }

    public Optional<StringFilter> optionalMunkanapStatusza() {
        return Optional.ofNullable(munkanapStatusza);
    }

    public StringFilter munkanapStatusza() {
        if (munkanapStatusza == null) {
            setMunkanapStatusza(new StringFilter());
        }
        return munkanapStatusza;
    }

    public void setMunkanapStatusza(StringFilter munkanapStatusza) {
        this.munkanapStatusza = munkanapStatusza;
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
        final TimesheetekCriteria that = (TimesheetekCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(datum, that.datum) &&
            Objects.equals(munkanapStatusza, that.munkanapStatusza) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(munkavallaloId, that.munkavallaloId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datum, munkanapStatusza, statusz, munkavallaloId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimesheetekCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDatum().map(f -> "datum=" + f + ", ").orElse("") +
            optionalMunkanapStatusza().map(f -> "munkanapStatusza=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalMunkavallaloId().map(f -> "munkavallaloId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

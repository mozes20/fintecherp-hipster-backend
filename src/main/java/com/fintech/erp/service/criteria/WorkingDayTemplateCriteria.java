package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.WorkingDayTemplate}
 * entity. This class is used
 * in {@link com.fintech.erp.web.rest.WorkingDayTemplateResource} to receive all
 * the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /working-day-templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingDayTemplateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter yearMonth;

    private StringFilter status;

    private Boolean distinct;

    public WorkingDayTemplateCriteria() {
    }

    public WorkingDayTemplateCriteria(WorkingDayTemplateCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.yearMonth = other.optionalYearMonth().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public WorkingDayTemplateCriteria copy() {
        return new WorkingDayTemplateCriteria(this);
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

    public StringFilter getYearMonth() {
        return yearMonth;
    }

    public Optional<StringFilter> optionalYearMonth() {
        return Optional.ofNullable(yearMonth);
    }

    public StringFilter yearMonth() {
        if (yearMonth == null) {
            setYearMonth(new StringFilter());
        }
        return yearMonth;
    }

    public void setYearMonth(StringFilter yearMonth) {
        this.yearMonth = yearMonth;
    }

    public StringFilter getStatus() {
        return status;
    }

    public Optional<StringFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public StringFilter status() {
        if (status == null) {
            setStatus(new StringFilter());
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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
        final WorkingDayTemplateCriteria that = (WorkingDayTemplateCriteria) o;
        return (Objects.equals(id, that.id) &&
                Objects.equals(yearMonth, that.yearMonth) &&
                Objects.equals(status, that.status) &&
                Objects.equals(distinct, that.distinct));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, yearMonth, status, distinct);
    }

    @Override
    public String toString() {
        return "WorkingDayTemplateCriteria{" +
                optionalId().map(f -> "id=" + f + ", ").orElse("") +
                optionalYearMonth().map(f -> "yearMonth=" + f + ", ").orElse("") +
                optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
                optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
                "}";
    }
}

package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.Bankszamlaszamok} entity. This class is used
 * in {@link com.fintech.erp.web.rest.BankszamlaszamokResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bankszamlaszamoks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankszamlaszamokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bankszamlaDevizanem;

    private StringFilter bankszamlaGIRO;

    private StringFilter bankszamlaIBAN;

    private StringFilter statusz;

    private LongFilter cegId;

    private Boolean distinct;

    public BankszamlaszamokCriteria() {}

    public BankszamlaszamokCriteria(BankszamlaszamokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.bankszamlaDevizanem = other.optionalBankszamlaDevizanem().map(StringFilter::copy).orElse(null);
        this.bankszamlaGIRO = other.optionalBankszamlaGIRO().map(StringFilter::copy).orElse(null);
        this.bankszamlaIBAN = other.optionalBankszamlaIBAN().map(StringFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.cegId = other.optionalCegId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BankszamlaszamokCriteria copy() {
        return new BankszamlaszamokCriteria(this);
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

    public StringFilter getBankszamlaDevizanem() {
        return bankszamlaDevizanem;
    }

    public Optional<StringFilter> optionalBankszamlaDevizanem() {
        return Optional.ofNullable(bankszamlaDevizanem);
    }

    public StringFilter bankszamlaDevizanem() {
        if (bankszamlaDevizanem == null) {
            setBankszamlaDevizanem(new StringFilter());
        }
        return bankszamlaDevizanem;
    }

    public void setBankszamlaDevizanem(StringFilter bankszamlaDevizanem) {
        this.bankszamlaDevizanem = bankszamlaDevizanem;
    }

    public StringFilter getBankszamlaGIRO() {
        return bankszamlaGIRO;
    }

    public Optional<StringFilter> optionalBankszamlaGIRO() {
        return Optional.ofNullable(bankszamlaGIRO);
    }

    public StringFilter bankszamlaGIRO() {
        if (bankszamlaGIRO == null) {
            setBankszamlaGIRO(new StringFilter());
        }
        return bankszamlaGIRO;
    }

    public void setBankszamlaGIRO(StringFilter bankszamlaGIRO) {
        this.bankszamlaGIRO = bankszamlaGIRO;
    }

    public StringFilter getBankszamlaIBAN() {
        return bankszamlaIBAN;
    }

    public Optional<StringFilter> optionalBankszamlaIBAN() {
        return Optional.ofNullable(bankszamlaIBAN);
    }

    public StringFilter bankszamlaIBAN() {
        if (bankszamlaIBAN == null) {
            setBankszamlaIBAN(new StringFilter());
        }
        return bankszamlaIBAN;
    }

    public void setBankszamlaIBAN(StringFilter bankszamlaIBAN) {
        this.bankszamlaIBAN = bankszamlaIBAN;
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

    public LongFilter getCegId() {
        return cegId;
    }

    public Optional<LongFilter> optionalCegId() {
        return Optional.ofNullable(cegId);
    }

    public LongFilter cegId() {
        if (cegId == null) {
            setCegId(new LongFilter());
        }
        return cegId;
    }

    public void setCegId(LongFilter cegId) {
        this.cegId = cegId;
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
        final BankszamlaszamokCriteria that = (BankszamlaszamokCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bankszamlaDevizanem, that.bankszamlaDevizanem) &&
            Objects.equals(bankszamlaGIRO, that.bankszamlaGIRO) &&
            Objects.equals(bankszamlaIBAN, that.bankszamlaIBAN) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(cegId, that.cegId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankszamlaDevizanem, bankszamlaGIRO, bankszamlaIBAN, statusz, cegId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankszamlaszamokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalBankszamlaDevizanem().map(f -> "bankszamlaDevizanem=" + f + ", ").orElse("") +
            optionalBankszamlaGIRO().map(f -> "bankszamlaGIRO=" + f + ", ").orElse("") +
            optionalBankszamlaIBAN().map(f -> "bankszamlaIBAN=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalCegId().map(f -> "cegId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

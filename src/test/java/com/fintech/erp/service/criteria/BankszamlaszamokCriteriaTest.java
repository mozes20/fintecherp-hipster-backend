package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BankszamlaszamokCriteriaTest {

    @Test
    void newBankszamlaszamokCriteriaHasAllFiltersNullTest() {
        var bankszamlaszamokCriteria = new BankszamlaszamokCriteria();
        assertThat(bankszamlaszamokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void bankszamlaszamokCriteriaFluentMethodsCreatesFiltersTest() {
        var bankszamlaszamokCriteria = new BankszamlaszamokCriteria();

        setAllFilters(bankszamlaszamokCriteria);

        assertThat(bankszamlaszamokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void bankszamlaszamokCriteriaCopyCreatesNullFilterTest() {
        var bankszamlaszamokCriteria = new BankszamlaszamokCriteria();
        var copy = bankszamlaszamokCriteria.copy();

        assertThat(bankszamlaszamokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(bankszamlaszamokCriteria)
        );
    }

    @Test
    void bankszamlaszamokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var bankszamlaszamokCriteria = new BankszamlaszamokCriteria();
        setAllFilters(bankszamlaszamokCriteria);

        var copy = bankszamlaszamokCriteria.copy();

        assertThat(bankszamlaszamokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(bankszamlaszamokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var bankszamlaszamokCriteria = new BankszamlaszamokCriteria();

        assertThat(bankszamlaszamokCriteria).hasToString("BankszamlaszamokCriteria{}");
    }

    private static void setAllFilters(BankszamlaszamokCriteria bankszamlaszamokCriteria) {
        bankszamlaszamokCriteria.id();
        bankszamlaszamokCriteria.bankszamlaDevizanem();
        bankszamlaszamokCriteria.bankszamlaGIRO();
        bankszamlaszamokCriteria.bankszamlaIBAN();
        bankszamlaszamokCriteria.statusz();
        bankszamlaszamokCriteria.cegId();
        bankszamlaszamokCriteria.distinct();
    }

    private static Condition<BankszamlaszamokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getBankszamlaDevizanem()) &&
                condition.apply(criteria.getBankszamlaGIRO()) &&
                condition.apply(criteria.getBankszamlaIBAN()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getCegId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BankszamlaszamokCriteria> copyFiltersAre(
        BankszamlaszamokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getBankszamlaDevizanem(), copy.getBankszamlaDevizanem()) &&
                condition.apply(criteria.getBankszamlaGIRO(), copy.getBankszamlaGIRO()) &&
                condition.apply(criteria.getBankszamlaIBAN(), copy.getBankszamlaIBAN()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getCegId(), copy.getCegId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

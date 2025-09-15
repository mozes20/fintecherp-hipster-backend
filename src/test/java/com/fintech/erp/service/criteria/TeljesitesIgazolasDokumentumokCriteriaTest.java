package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TeljesitesIgazolasDokumentumokCriteriaTest {

    @Test
    void newTeljesitesIgazolasDokumentumokCriteriaHasAllFiltersNullTest() {
        var teljesitesIgazolasDokumentumokCriteria = new TeljesitesIgazolasDokumentumokCriteria();
        assertThat(teljesitesIgazolasDokumentumokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void teljesitesIgazolasDokumentumokCriteriaFluentMethodsCreatesFiltersTest() {
        var teljesitesIgazolasDokumentumokCriteria = new TeljesitesIgazolasDokumentumokCriteria();

        setAllFilters(teljesitesIgazolasDokumentumokCriteria);

        assertThat(teljesitesIgazolasDokumentumokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void teljesitesIgazolasDokumentumokCriteriaCopyCreatesNullFilterTest() {
        var teljesitesIgazolasDokumentumokCriteria = new TeljesitesIgazolasDokumentumokCriteria();
        var copy = teljesitesIgazolasDokumentumokCriteria.copy();

        assertThat(teljesitesIgazolasDokumentumokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(teljesitesIgazolasDokumentumokCriteria)
        );
    }

    @Test
    void teljesitesIgazolasDokumentumokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var teljesitesIgazolasDokumentumokCriteria = new TeljesitesIgazolasDokumentumokCriteria();
        setAllFilters(teljesitesIgazolasDokumentumokCriteria);

        var copy = teljesitesIgazolasDokumentumokCriteria.copy();

        assertThat(teljesitesIgazolasDokumentumokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(teljesitesIgazolasDokumentumokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var teljesitesIgazolasDokumentumokCriteria = new TeljesitesIgazolasDokumentumokCriteria();

        assertThat(teljesitesIgazolasDokumentumokCriteria).hasToString("TeljesitesIgazolasDokumentumokCriteria{}");
    }

    private static void setAllFilters(TeljesitesIgazolasDokumentumokCriteria teljesitesIgazolasDokumentumokCriteria) {
        teljesitesIgazolasDokumentumokCriteria.id();
        teljesitesIgazolasDokumentumokCriteria.dokumentumTipusa();
        teljesitesIgazolasDokumentumokCriteria.dokumentum();
        teljesitesIgazolasDokumentumokCriteria.teljesitesIgazolasId();
        teljesitesIgazolasDokumentumokCriteria.distinct();
    }

    private static Condition<TeljesitesIgazolasDokumentumokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDokumentumTipusa()) &&
                condition.apply(criteria.getDokumentum()) &&
                condition.apply(criteria.getTeljesitesIgazolasId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TeljesitesIgazolasDokumentumokCriteria> copyFiltersAre(
        TeljesitesIgazolasDokumentumokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDokumentumTipusa(), copy.getDokumentumTipusa()) &&
                condition.apply(criteria.getDokumentum(), copy.getDokumentum()) &&
                condition.apply(criteria.getTeljesitesIgazolasId(), copy.getTeljesitesIgazolasId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

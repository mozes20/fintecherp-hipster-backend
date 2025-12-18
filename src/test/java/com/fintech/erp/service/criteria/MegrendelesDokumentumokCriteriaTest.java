package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MegrendelesDokumentumokCriteriaTest {

    @Test
    void newMegrendelesDokumentumokCriteriaHasAllFiltersNullTest() {
        var megrendelesDokumentumokCriteria = new MegrendelesDokumentumokCriteria();
        assertThat(megrendelesDokumentumokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void megrendelesDokumentumokCriteriaFluentMethodsCreatesFiltersTest() {
        var megrendelesDokumentumokCriteria = new MegrendelesDokumentumokCriteria();

        setAllFilters(megrendelesDokumentumokCriteria);

        assertThat(megrendelesDokumentumokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void megrendelesDokumentumokCriteriaCopyCreatesNullFilterTest() {
        var megrendelesDokumentumokCriteria = new MegrendelesDokumentumokCriteria();
        var copy = megrendelesDokumentumokCriteria.copy();

        assertThat(megrendelesDokumentumokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(megrendelesDokumentumokCriteria)
        );
    }

    @Test
    void megrendelesDokumentumokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var megrendelesDokumentumokCriteria = new MegrendelesDokumentumokCriteria();
        setAllFilters(megrendelesDokumentumokCriteria);

        var copy = megrendelesDokumentumokCriteria.copy();

        assertThat(megrendelesDokumentumokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(megrendelesDokumentumokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var megrendelesDokumentumokCriteria = new MegrendelesDokumentumokCriteria();

        assertThat(megrendelesDokumentumokCriteria).hasToString("MegrendelesDokumentumokCriteria{}");
    }

    private static void setAllFilters(MegrendelesDokumentumokCriteria megrendelesDokumentumokCriteria) {
        megrendelesDokumentumokCriteria.id();
        megrendelesDokumentumokCriteria.dokumentumTipusa();
        megrendelesDokumentumokCriteria.dokumentum();
        megrendelesDokumentumokCriteria.dokumentumUrl();
        megrendelesDokumentumokCriteria.dokumentumAzonosito();
        megrendelesDokumentumokCriteria.megrendelesId();
        megrendelesDokumentumokCriteria.distinct();
    }

    private static Condition<MegrendelesDokumentumokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDokumentumTipusa()) &&
                condition.apply(criteria.getDokumentum()) &&
                condition.apply(criteria.getDokumentumUrl()) &&
                condition.apply(criteria.getDokumentumAzonosito()) &&
                condition.apply(criteria.getMegrendelesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MegrendelesDokumentumokCriteria> copyFiltersAre(
        MegrendelesDokumentumokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDokumentumTipusa(), copy.getDokumentumTipusa()) &&
                condition.apply(criteria.getDokumentum(), copy.getDokumentum()) &&
                condition.apply(criteria.getDokumentumUrl(), copy.getDokumentumUrl()) &&
                condition.apply(criteria.getDokumentumAzonosito(), copy.getDokumentumAzonosito()) &&
                condition.apply(criteria.getMegrendelesId(), copy.getMegrendelesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SajatCegKepviselokCriteriaTest {

    @Test
    void newSajatCegKepviselokCriteriaHasAllFiltersNullTest() {
        var sajatCegKepviselokCriteria = new SajatCegKepviselokCriteria();
        assertThat(sajatCegKepviselokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void sajatCegKepviselokCriteriaFluentMethodsCreatesFiltersTest() {
        var sajatCegKepviselokCriteria = new SajatCegKepviselokCriteria();

        setAllFilters(sajatCegKepviselokCriteria);

        assertThat(sajatCegKepviselokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void sajatCegKepviselokCriteriaCopyCreatesNullFilterTest() {
        var sajatCegKepviselokCriteria = new SajatCegKepviselokCriteria();
        var copy = sajatCegKepviselokCriteria.copy();

        assertThat(sajatCegKepviselokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(sajatCegKepviselokCriteria)
        );
    }

    @Test
    void sajatCegKepviselokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sajatCegKepviselokCriteria = new SajatCegKepviselokCriteria();
        setAllFilters(sajatCegKepviselokCriteria);

        var copy = sajatCegKepviselokCriteria.copy();

        assertThat(sajatCegKepviselokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(sajatCegKepviselokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sajatCegKepviselokCriteria = new SajatCegKepviselokCriteria();

        assertThat(sajatCegKepviselokCriteria).hasToString("SajatCegKepviselokCriteria{}");
    }

    private static void setAllFilters(SajatCegKepviselokCriteria sajatCegKepviselokCriteria) {
        sajatCegKepviselokCriteria.id();
        sajatCegKepviselokCriteria.statusz();
        sajatCegKepviselokCriteria.sajatCegId();
        sajatCegKepviselokCriteria.maganszemelyId();
        sajatCegKepviselokCriteria.distinct();
    }

    private static Condition<SajatCegKepviselokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getSajatCegId()) &&
                condition.apply(criteria.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SajatCegKepviselokCriteria> copyFiltersAre(
        SajatCegKepviselokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getSajatCegId(), copy.getSajatCegId()) &&
                condition.apply(criteria.getMaganszemelyId(), copy.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

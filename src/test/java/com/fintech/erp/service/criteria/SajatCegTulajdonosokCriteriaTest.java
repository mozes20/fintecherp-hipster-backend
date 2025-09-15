package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SajatCegTulajdonosokCriteriaTest {

    @Test
    void newSajatCegTulajdonosokCriteriaHasAllFiltersNullTest() {
        var sajatCegTulajdonosokCriteria = new SajatCegTulajdonosokCriteria();
        assertThat(sajatCegTulajdonosokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void sajatCegTulajdonosokCriteriaFluentMethodsCreatesFiltersTest() {
        var sajatCegTulajdonosokCriteria = new SajatCegTulajdonosokCriteria();

        setAllFilters(sajatCegTulajdonosokCriteria);

        assertThat(sajatCegTulajdonosokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void sajatCegTulajdonosokCriteriaCopyCreatesNullFilterTest() {
        var sajatCegTulajdonosokCriteria = new SajatCegTulajdonosokCriteria();
        var copy = sajatCegTulajdonosokCriteria.copy();

        assertThat(sajatCegTulajdonosokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(sajatCegTulajdonosokCriteria)
        );
    }

    @Test
    void sajatCegTulajdonosokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sajatCegTulajdonosokCriteria = new SajatCegTulajdonosokCriteria();
        setAllFilters(sajatCegTulajdonosokCriteria);

        var copy = sajatCegTulajdonosokCriteria.copy();

        assertThat(sajatCegTulajdonosokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(sajatCegTulajdonosokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sajatCegTulajdonosokCriteria = new SajatCegTulajdonosokCriteria();

        assertThat(sajatCegTulajdonosokCriteria).hasToString("SajatCegTulajdonosokCriteria{}");
    }

    private static void setAllFilters(SajatCegTulajdonosokCriteria sajatCegTulajdonosokCriteria) {
        sajatCegTulajdonosokCriteria.id();
        sajatCegTulajdonosokCriteria.bruttoOsztalek();
        sajatCegTulajdonosokCriteria.statusz();
        sajatCegTulajdonosokCriteria.sajatCegId();
        sajatCegTulajdonosokCriteria.maganszemelyId();
        sajatCegTulajdonosokCriteria.distinct();
    }

    private static Condition<SajatCegTulajdonosokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getBruttoOsztalek()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getSajatCegId()) &&
                condition.apply(criteria.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SajatCegTulajdonosokCriteria> copyFiltersAre(
        SajatCegTulajdonosokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getBruttoOsztalek(), copy.getBruttoOsztalek()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getSajatCegId(), copy.getSajatCegId()) &&
                condition.apply(criteria.getMaganszemelyId(), copy.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

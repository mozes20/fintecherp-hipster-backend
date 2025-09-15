package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EfoFoglalkoztatasokCriteriaTest {

    @Test
    void newEfoFoglalkoztatasokCriteriaHasAllFiltersNullTest() {
        var efoFoglalkoztatasokCriteria = new EfoFoglalkoztatasokCriteria();
        assertThat(efoFoglalkoztatasokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void efoFoglalkoztatasokCriteriaFluentMethodsCreatesFiltersTest() {
        var efoFoglalkoztatasokCriteria = new EfoFoglalkoztatasokCriteria();

        setAllFilters(efoFoglalkoztatasokCriteria);

        assertThat(efoFoglalkoztatasokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void efoFoglalkoztatasokCriteriaCopyCreatesNullFilterTest() {
        var efoFoglalkoztatasokCriteria = new EfoFoglalkoztatasokCriteria();
        var copy = efoFoglalkoztatasokCriteria.copy();

        assertThat(efoFoglalkoztatasokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(efoFoglalkoztatasokCriteria)
        );
    }

    @Test
    void efoFoglalkoztatasokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var efoFoglalkoztatasokCriteria = new EfoFoglalkoztatasokCriteria();
        setAllFilters(efoFoglalkoztatasokCriteria);

        var copy = efoFoglalkoztatasokCriteria.copy();

        assertThat(efoFoglalkoztatasokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(efoFoglalkoztatasokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var efoFoglalkoztatasokCriteria = new EfoFoglalkoztatasokCriteria();

        assertThat(efoFoglalkoztatasokCriteria).hasToString("EfoFoglalkoztatasokCriteria{}");
    }

    private static void setAllFilters(EfoFoglalkoztatasokCriteria efoFoglalkoztatasokCriteria) {
        efoFoglalkoztatasokCriteria.id();
        efoFoglalkoztatasokCriteria.datum();
        efoFoglalkoztatasokCriteria.osszeg();
        efoFoglalkoztatasokCriteria.generaltEfoSzerzodes();
        efoFoglalkoztatasokCriteria.alairtEfoSzerzodes();
        efoFoglalkoztatasokCriteria.munkavallaloId();
        efoFoglalkoztatasokCriteria.distinct();
    }

    private static Condition<EfoFoglalkoztatasokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDatum()) &&
                condition.apply(criteria.getOsszeg()) &&
                condition.apply(criteria.getGeneraltEfoSzerzodes()) &&
                condition.apply(criteria.getAlairtEfoSzerzodes()) &&
                condition.apply(criteria.getMunkavallaloId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EfoFoglalkoztatasokCriteria> copyFiltersAre(
        EfoFoglalkoztatasokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDatum(), copy.getDatum()) &&
                condition.apply(criteria.getOsszeg(), copy.getOsszeg()) &&
                condition.apply(criteria.getGeneraltEfoSzerzodes(), copy.getGeneraltEfoSzerzodes()) &&
                condition.apply(criteria.getAlairtEfoSzerzodes(), copy.getAlairtEfoSzerzodes()) &&
                condition.apply(criteria.getMunkavallaloId(), copy.getMunkavallaloId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

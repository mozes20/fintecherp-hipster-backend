package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class OsztalekfizetesiKozgyulesekCriteriaTest {

    @Test
    void newOsztalekfizetesiKozgyulesekCriteriaHasAllFiltersNullTest() {
        var osztalekfizetesiKozgyulesekCriteria = new OsztalekfizetesiKozgyulesekCriteria();
        assertThat(osztalekfizetesiKozgyulesekCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void osztalekfizetesiKozgyulesekCriteriaFluentMethodsCreatesFiltersTest() {
        var osztalekfizetesiKozgyulesekCriteria = new OsztalekfizetesiKozgyulesekCriteria();

        setAllFilters(osztalekfizetesiKozgyulesekCriteria);

        assertThat(osztalekfizetesiKozgyulesekCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void osztalekfizetesiKozgyulesekCriteriaCopyCreatesNullFilterTest() {
        var osztalekfizetesiKozgyulesekCriteria = new OsztalekfizetesiKozgyulesekCriteria();
        var copy = osztalekfizetesiKozgyulesekCriteria.copy();

        assertThat(osztalekfizetesiKozgyulesekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(osztalekfizetesiKozgyulesekCriteria)
        );
    }

    @Test
    void osztalekfizetesiKozgyulesekCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var osztalekfizetesiKozgyulesekCriteria = new OsztalekfizetesiKozgyulesekCriteria();
        setAllFilters(osztalekfizetesiKozgyulesekCriteria);

        var copy = osztalekfizetesiKozgyulesekCriteria.copy();

        assertThat(osztalekfizetesiKozgyulesekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(osztalekfizetesiKozgyulesekCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var osztalekfizetesiKozgyulesekCriteria = new OsztalekfizetesiKozgyulesekCriteria();

        assertThat(osztalekfizetesiKozgyulesekCriteria).hasToString("OsztalekfizetesiKozgyulesekCriteria{}");
    }

    private static void setAllFilters(OsztalekfizetesiKozgyulesekCriteria osztalekfizetesiKozgyulesekCriteria) {
        osztalekfizetesiKozgyulesekCriteria.id();
        osztalekfizetesiKozgyulesekCriteria.kozgyulesDatum();
        osztalekfizetesiKozgyulesekCriteria.kozgyulesiJegyzokonyvGeneralta();
        osztalekfizetesiKozgyulesekCriteria.kozgyulesiJegyzokonyvAlairt();
        osztalekfizetesiKozgyulesekCriteria.sajatCegId();
        osztalekfizetesiKozgyulesekCriteria.distinct();
    }

    private static Condition<OsztalekfizetesiKozgyulesekCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getKozgyulesDatum()) &&
                condition.apply(criteria.getKozgyulesiJegyzokonyvGeneralta()) &&
                condition.apply(criteria.getKozgyulesiJegyzokonyvAlairt()) &&
                condition.apply(criteria.getSajatCegId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<OsztalekfizetesiKozgyulesekCriteria> copyFiltersAre(
        OsztalekfizetesiKozgyulesekCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getKozgyulesDatum(), copy.getKozgyulesDatum()) &&
                condition.apply(criteria.getKozgyulesiJegyzokonyvGeneralta(), copy.getKozgyulesiJegyzokonyvGeneralta()) &&
                condition.apply(criteria.getKozgyulesiJegyzokonyvAlairt(), copy.getKozgyulesiJegyzokonyvAlairt()) &&
                condition.apply(criteria.getSajatCegId(), copy.getSajatCegId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SajatCegAlapadatokCriteriaTest {

    @Test
    void newSajatCegAlapadatokCriteriaHasAllFiltersNullTest() {
        var sajatCegAlapadatokCriteria = new SajatCegAlapadatokCriteria();
        assertThat(sajatCegAlapadatokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void sajatCegAlapadatokCriteriaFluentMethodsCreatesFiltersTest() {
        var sajatCegAlapadatokCriteria = new SajatCegAlapadatokCriteria();

        setAllFilters(sajatCegAlapadatokCriteria);

        assertThat(sajatCegAlapadatokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void sajatCegAlapadatokCriteriaCopyCreatesNullFilterTest() {
        var sajatCegAlapadatokCriteria = new SajatCegAlapadatokCriteria();
        var copy = sajatCegAlapadatokCriteria.copy();

        assertThat(sajatCegAlapadatokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(sajatCegAlapadatokCriteria)
        );
    }

    @Test
    void sajatCegAlapadatokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sajatCegAlapadatokCriteria = new SajatCegAlapadatokCriteria();
        setAllFilters(sajatCegAlapadatokCriteria);

        var copy = sajatCegAlapadatokCriteria.copy();

        assertThat(sajatCegAlapadatokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(sajatCegAlapadatokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sajatCegAlapadatokCriteria = new SajatCegAlapadatokCriteria();

        assertThat(sajatCegAlapadatokCriteria).hasToString("SajatCegAlapadatokCriteria{}");
    }

    private static void setAllFilters(SajatCegAlapadatokCriteria sajatCegAlapadatokCriteria) {
        sajatCegAlapadatokCriteria.id();
        sajatCegAlapadatokCriteria.cegAdminisztraciosHaviKoltseg();
        sajatCegAlapadatokCriteria.statusz();
        sajatCegAlapadatokCriteria.cegId();
        sajatCegAlapadatokCriteria.distinct();
    }

    private static Condition<SajatCegAlapadatokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCegAdminisztraciosHaviKoltseg()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getCegId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SajatCegAlapadatokCriteria> copyFiltersAre(
        SajatCegAlapadatokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCegAdminisztraciosHaviKoltseg(), copy.getCegAdminisztraciosHaviKoltseg()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getCegId(), copy.getCegId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

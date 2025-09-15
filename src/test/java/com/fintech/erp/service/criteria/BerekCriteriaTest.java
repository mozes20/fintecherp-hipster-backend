package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BerekCriteriaTest {

    @Test
    void newBerekCriteriaHasAllFiltersNullTest() {
        var berekCriteria = new BerekCriteria();
        assertThat(berekCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void berekCriteriaFluentMethodsCreatesFiltersTest() {
        var berekCriteria = new BerekCriteria();

        setAllFilters(berekCriteria);

        assertThat(berekCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void berekCriteriaCopyCreatesNullFilterTest() {
        var berekCriteria = new BerekCriteria();
        var copy = berekCriteria.copy();

        assertThat(berekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(berekCriteria)
        );
    }

    @Test
    void berekCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var berekCriteria = new BerekCriteria();
        setAllFilters(berekCriteria);

        var copy = berekCriteria.copy();

        assertThat(berekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(berekCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var berekCriteria = new BerekCriteria();

        assertThat(berekCriteria).hasToString("BerekCriteria{}");
    }

    private static void setAllFilters(BerekCriteria berekCriteria) {
        berekCriteria.id();
        berekCriteria.ervenyessegKezdete();
        berekCriteria.bruttoHaviMunkaberVagyNapdij();
        berekCriteria.munkaszerzodes();
        berekCriteria.teljesKoltseg();
        berekCriteria.munkavallaloId();
        berekCriteria.distinct();
    }

    private static Condition<BerekCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getErvenyessegKezdete()) &&
                condition.apply(criteria.getBruttoHaviMunkaberVagyNapdij()) &&
                condition.apply(criteria.getMunkaszerzodes()) &&
                condition.apply(criteria.getTeljesKoltseg()) &&
                condition.apply(criteria.getMunkavallaloId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BerekCriteria> copyFiltersAre(BerekCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getErvenyessegKezdete(), copy.getErvenyessegKezdete()) &&
                condition.apply(criteria.getBruttoHaviMunkaberVagyNapdij(), copy.getBruttoHaviMunkaberVagyNapdij()) &&
                condition.apply(criteria.getMunkaszerzodes(), copy.getMunkaszerzodes()) &&
                condition.apply(criteria.getTeljesKoltseg(), copy.getTeljesKoltseg()) &&
                condition.apply(criteria.getMunkavallaloId(), copy.getMunkavallaloId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MunkavallalokCriteriaTest {

    @Test
    void newMunkavallalokCriteriaHasAllFiltersNullTest() {
        var munkavallalokCriteria = new MunkavallalokCriteria();
        assertThat(munkavallalokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void munkavallalokCriteriaFluentMethodsCreatesFiltersTest() {
        var munkavallalokCriteria = new MunkavallalokCriteria();

        setAllFilters(munkavallalokCriteria);

        assertThat(munkavallalokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void munkavallalokCriteriaCopyCreatesNullFilterTest() {
        var munkavallalokCriteria = new MunkavallalokCriteria();
        var copy = munkavallalokCriteria.copy();

        assertThat(munkavallalokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(munkavallalokCriteria)
        );
    }

    @Test
    void munkavallalokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var munkavallalokCriteria = new MunkavallalokCriteria();
        setAllFilters(munkavallalokCriteria);

        var copy = munkavallalokCriteria.copy();

        assertThat(munkavallalokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(munkavallalokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var munkavallalokCriteria = new MunkavallalokCriteria();

        assertThat(munkavallalokCriteria).hasToString("MunkavallalokCriteria{}");
    }

    private static void setAllFilters(MunkavallalokCriteria munkavallalokCriteria) {
        munkavallalokCriteria.id();
        munkavallalokCriteria.foglalkoztatasTipusa();
        munkavallalokCriteria.foglalkoztatasKezdete();
        munkavallalokCriteria.foglalkoztatasVege();
        munkavallalokCriteria.sajatCegId();
        munkavallalokCriteria.maganszemelyId();
        munkavallalokCriteria.distinct();
    }

    private static Condition<MunkavallalokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFoglalkoztatasTipusa()) &&
                condition.apply(criteria.getFoglalkoztatasKezdete()) &&
                condition.apply(criteria.getFoglalkoztatasVege()) &&
                condition.apply(criteria.getSajatCegId()) &&
                condition.apply(criteria.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MunkavallalokCriteria> copyFiltersAre(
        MunkavallalokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFoglalkoztatasTipusa(), copy.getFoglalkoztatasTipusa()) &&
                condition.apply(criteria.getFoglalkoztatasKezdete(), copy.getFoglalkoztatasKezdete()) &&
                condition.apply(criteria.getFoglalkoztatasVege(), copy.getFoglalkoztatasVege()) &&
                condition.apply(criteria.getSajatCegId(), copy.getSajatCegId()) &&
                condition.apply(criteria.getMaganszemelyId(), copy.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

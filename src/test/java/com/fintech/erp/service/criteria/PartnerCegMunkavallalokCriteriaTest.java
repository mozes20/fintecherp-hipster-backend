package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PartnerCegMunkavallalokCriteriaTest {

    @Test
    void newPartnerCegMunkavallalokCriteriaHasAllFiltersNullTest() {
        var partnerCegMunkavallalokCriteria = new PartnerCegMunkavallalokCriteria();
        assertThat(partnerCegMunkavallalokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void partnerCegMunkavallalokCriteriaFluentMethodsCreatesFiltersTest() {
        var partnerCegMunkavallalokCriteria = new PartnerCegMunkavallalokCriteria();

        setAllFilters(partnerCegMunkavallalokCriteria);

        assertThat(partnerCegMunkavallalokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void partnerCegMunkavallalokCriteriaCopyCreatesNullFilterTest() {
        var partnerCegMunkavallalokCriteria = new PartnerCegMunkavallalokCriteria();
        var copy = partnerCegMunkavallalokCriteria.copy();

        assertThat(partnerCegMunkavallalokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(partnerCegMunkavallalokCriteria)
        );
    }

    @Test
    void partnerCegMunkavallalokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var partnerCegMunkavallalokCriteria = new PartnerCegMunkavallalokCriteria();
        setAllFilters(partnerCegMunkavallalokCriteria);

        var copy = partnerCegMunkavallalokCriteria.copy();

        assertThat(partnerCegMunkavallalokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(partnerCegMunkavallalokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var partnerCegMunkavallalokCriteria = new PartnerCegMunkavallalokCriteria();

        assertThat(partnerCegMunkavallalokCriteria).hasToString("PartnerCegMunkavallalokCriteria{}");
    }

    private static void setAllFilters(PartnerCegMunkavallalokCriteria partnerCegMunkavallalokCriteria) {
        partnerCegMunkavallalokCriteria.id();
        partnerCegMunkavallalokCriteria.statusz();
        partnerCegMunkavallalokCriteria.partnerCegId();
        partnerCegMunkavallalokCriteria.maganszemelyId();
        partnerCegMunkavallalokCriteria.distinct();
    }

    private static Condition<PartnerCegMunkavallalokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getPartnerCegId()) &&
                condition.apply(criteria.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PartnerCegMunkavallalokCriteria> copyFiltersAre(
        PartnerCegMunkavallalokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getPartnerCegId(), copy.getPartnerCegId()) &&
                condition.apply(criteria.getMaganszemelyId(), copy.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

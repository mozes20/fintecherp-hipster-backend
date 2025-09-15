package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PartnerCegAdatokCriteriaTest {

    @Test
    void newPartnerCegAdatokCriteriaHasAllFiltersNullTest() {
        var partnerCegAdatokCriteria = new PartnerCegAdatokCriteria();
        assertThat(partnerCegAdatokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void partnerCegAdatokCriteriaFluentMethodsCreatesFiltersTest() {
        var partnerCegAdatokCriteria = new PartnerCegAdatokCriteria();

        setAllFilters(partnerCegAdatokCriteria);

        assertThat(partnerCegAdatokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void partnerCegAdatokCriteriaCopyCreatesNullFilterTest() {
        var partnerCegAdatokCriteria = new PartnerCegAdatokCriteria();
        var copy = partnerCegAdatokCriteria.copy();

        assertThat(partnerCegAdatokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(partnerCegAdatokCriteria)
        );
    }

    @Test
    void partnerCegAdatokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var partnerCegAdatokCriteria = new PartnerCegAdatokCriteria();
        setAllFilters(partnerCegAdatokCriteria);

        var copy = partnerCegAdatokCriteria.copy();

        assertThat(partnerCegAdatokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(partnerCegAdatokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var partnerCegAdatokCriteria = new PartnerCegAdatokCriteria();

        assertThat(partnerCegAdatokCriteria).hasToString("PartnerCegAdatokCriteria{}");
    }

    private static void setAllFilters(PartnerCegAdatokCriteria partnerCegAdatokCriteria) {
        partnerCegAdatokCriteria.id();
        partnerCegAdatokCriteria.statusz();
        partnerCegAdatokCriteria.cegId();
        partnerCegAdatokCriteria.distinct();
    }

    private static Condition<PartnerCegAdatokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getCegId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PartnerCegAdatokCriteria> copyFiltersAre(
        PartnerCegAdatokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getCegId(), copy.getCegId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

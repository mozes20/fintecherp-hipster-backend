package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PartnerCegKapcsolattartokCriteriaTest {

    @Test
    void newPartnerCegKapcsolattartokCriteriaHasAllFiltersNullTest() {
        var partnerCegKapcsolattartokCriteria = new PartnerCegKapcsolattartokCriteria();
        assertThat(partnerCegKapcsolattartokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void partnerCegKapcsolattartokCriteriaFluentMethodsCreatesFiltersTest() {
        var partnerCegKapcsolattartokCriteria = new PartnerCegKapcsolattartokCriteria();

        setAllFilters(partnerCegKapcsolattartokCriteria);

        assertThat(partnerCegKapcsolattartokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void partnerCegKapcsolattartokCriteriaCopyCreatesNullFilterTest() {
        var partnerCegKapcsolattartokCriteria = new PartnerCegKapcsolattartokCriteria();
        var copy = partnerCegKapcsolattartokCriteria.copy();

        assertThat(partnerCegKapcsolattartokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(partnerCegKapcsolattartokCriteria)
        );
    }

    @Test
    void partnerCegKapcsolattartokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var partnerCegKapcsolattartokCriteria = new PartnerCegKapcsolattartokCriteria();
        setAllFilters(partnerCegKapcsolattartokCriteria);

        var copy = partnerCegKapcsolattartokCriteria.copy();

        assertThat(partnerCegKapcsolattartokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(partnerCegKapcsolattartokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var partnerCegKapcsolattartokCriteria = new PartnerCegKapcsolattartokCriteria();

        assertThat(partnerCegKapcsolattartokCriteria).hasToString("PartnerCegKapcsolattartokCriteria{}");
    }

    private static void setAllFilters(PartnerCegKapcsolattartokCriteria partnerCegKapcsolattartokCriteria) {
        partnerCegKapcsolattartokCriteria.id();
        partnerCegKapcsolattartokCriteria.kapcsolattartoTitulus();
        partnerCegKapcsolattartokCriteria.statusz();
        partnerCegKapcsolattartokCriteria.partnerCegId();
        partnerCegKapcsolattartokCriteria.maganszemelyId();
        partnerCegKapcsolattartokCriteria.distinct();
    }

    private static Condition<PartnerCegKapcsolattartokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getKapcsolattartoTitulus()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getPartnerCegId()) &&
                condition.apply(criteria.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PartnerCegKapcsolattartokCriteria> copyFiltersAre(
        PartnerCegKapcsolattartokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getKapcsolattartoTitulus(), copy.getKapcsolattartoTitulus()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getPartnerCegId(), copy.getPartnerCegId()) &&
                condition.apply(criteria.getMaganszemelyId(), copy.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

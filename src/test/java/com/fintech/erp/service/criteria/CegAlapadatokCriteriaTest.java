package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CegAlapadatokCriteriaTest {

    @Test
    void newCegAlapadatokCriteriaHasAllFiltersNullTest() {
        var cegAlapadatokCriteria = new CegAlapadatokCriteria();
        assertThat(cegAlapadatokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void cegAlapadatokCriteriaFluentMethodsCreatesFiltersTest() {
        var cegAlapadatokCriteria = new CegAlapadatokCriteria();

        setAllFilters(cegAlapadatokCriteria);

        assertThat(cegAlapadatokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void cegAlapadatokCriteriaCopyCreatesNullFilterTest() {
        var cegAlapadatokCriteria = new CegAlapadatokCriteria();
        var copy = cegAlapadatokCriteria.copy();

        assertThat(cegAlapadatokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(cegAlapadatokCriteria)
        );
    }

    @Test
    void cegAlapadatokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cegAlapadatokCriteria = new CegAlapadatokCriteria();
        setAllFilters(cegAlapadatokCriteria);

        var copy = cegAlapadatokCriteria.copy();

        assertThat(cegAlapadatokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(cegAlapadatokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cegAlapadatokCriteria = new CegAlapadatokCriteria();

        assertThat(cegAlapadatokCriteria).hasToString("CegAlapadatokCriteria{}");
    }

    private static void setAllFilters(CegAlapadatokCriteria cegAlapadatokCriteria) {
        cegAlapadatokCriteria.id();
        cegAlapadatokCriteria.cegNev();
        cegAlapadatokCriteria.cegRovidAzonosito();
        cegAlapadatokCriteria.cegSzekhely();
        cegAlapadatokCriteria.adoszam();
        cegAlapadatokCriteria.cegjegyzekszam();
        cegAlapadatokCriteria.cegKozpontiEmail();
        cegAlapadatokCriteria.cegKozpontiTel();
        cegAlapadatokCriteria.statusz();
        cegAlapadatokCriteria.distinct();
    }

    private static Condition<CegAlapadatokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCegNev()) &&
                condition.apply(criteria.getCegRovidAzonosito()) &&
                condition.apply(criteria.getCegSzekhely()) &&
                condition.apply(criteria.getAdoszam()) &&
                condition.apply(criteria.getCegjegyzekszam()) &&
                condition.apply(criteria.getCegKozpontiEmail()) &&
                condition.apply(criteria.getCegKozpontiTel()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CegAlapadatokCriteria> copyFiltersAre(
        CegAlapadatokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCegNev(), copy.getCegNev()) &&
                condition.apply(criteria.getCegRovidAzonosito(), copy.getCegRovidAzonosito()) &&
                condition.apply(criteria.getCegSzekhely(), copy.getCegSzekhely()) &&
                condition.apply(criteria.getAdoszam(), copy.getAdoszam()) &&
                condition.apply(criteria.getCegjegyzekszam(), copy.getCegjegyzekszam()) &&
                condition.apply(criteria.getCegKozpontiEmail(), copy.getCegKozpontiEmail()) &&
                condition.apply(criteria.getCegKozpontiTel(), copy.getCegKozpontiTel()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UgyfelElszamolasokCriteriaTest {

    @Test
    void newUgyfelElszamolasokCriteriaHasAllFiltersNullTest() {
        var ugyfelElszamolasokCriteria = new UgyfelElszamolasokCriteria();
        assertThat(ugyfelElszamolasokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void ugyfelElszamolasokCriteriaFluentMethodsCreatesFiltersTest() {
        var ugyfelElszamolasokCriteria = new UgyfelElszamolasokCriteria();

        setAllFilters(ugyfelElszamolasokCriteria);

        assertThat(ugyfelElszamolasokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void ugyfelElszamolasokCriteriaCopyCreatesNullFilterTest() {
        var ugyfelElszamolasokCriteria = new UgyfelElszamolasokCriteria();
        var copy = ugyfelElszamolasokCriteria.copy();

        assertThat(ugyfelElszamolasokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(ugyfelElszamolasokCriteria)
        );
    }

    @Test
    void ugyfelElszamolasokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ugyfelElszamolasokCriteria = new UgyfelElszamolasokCriteria();
        setAllFilters(ugyfelElszamolasokCriteria);

        var copy = ugyfelElszamolasokCriteria.copy();

        assertThat(ugyfelElszamolasokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(ugyfelElszamolasokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ugyfelElszamolasokCriteria = new UgyfelElszamolasokCriteria();

        assertThat(ugyfelElszamolasokCriteria).hasToString("UgyfelElszamolasokCriteria{}");
    }

    private static void setAllFilters(UgyfelElszamolasokCriteria ugyfelElszamolasokCriteria) {
        ugyfelElszamolasokCriteria.id();
        ugyfelElszamolasokCriteria.teljesitesiIdoszakKezdete();
        ugyfelElszamolasokCriteria.teljesitesiIdoszakVege();
        ugyfelElszamolasokCriteria.napokSzama();
        ugyfelElszamolasokCriteria.teljesitesIgazolasonSzereploOsszeg();
        ugyfelElszamolasokCriteria.kapcsolodoSzamlaSorszamRogzitve();
        ugyfelElszamolasokCriteria.megrendelesId();
        ugyfelElszamolasokCriteria.distinct();
    }

    private static Condition<UgyfelElszamolasokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTeljesitesiIdoszakKezdete()) &&
                condition.apply(criteria.getTeljesitesiIdoszakVege()) &&
                condition.apply(criteria.getNapokSzama()) &&
                condition.apply(criteria.getTeljesitesIgazolasonSzereploOsszeg()) &&
                condition.apply(criteria.getKapcsolodoSzamlaSorszamRogzitve()) &&
                condition.apply(criteria.getMegrendelesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UgyfelElszamolasokCriteria> copyFiltersAre(
        UgyfelElszamolasokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTeljesitesiIdoszakKezdete(), copy.getTeljesitesiIdoszakKezdete()) &&
                condition.apply(criteria.getTeljesitesiIdoszakVege(), copy.getTeljesitesiIdoszakVege()) &&
                condition.apply(criteria.getNapokSzama(), copy.getNapokSzama()) &&
                condition.apply(criteria.getTeljesitesIgazolasonSzereploOsszeg(), copy.getTeljesitesIgazolasonSzereploOsszeg()) &&
                condition.apply(criteria.getKapcsolodoSzamlaSorszamRogzitve(), copy.getKapcsolodoSzamlaSorszamRogzitve()) &&
                condition.apply(criteria.getMegrendelesId(), copy.getMegrendelesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

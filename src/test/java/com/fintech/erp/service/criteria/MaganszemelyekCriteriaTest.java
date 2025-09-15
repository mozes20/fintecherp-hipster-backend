package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MaganszemelyekCriteriaTest {

    @Test
    void newMaganszemelyekCriteriaHasAllFiltersNullTest() {
        var maganszemelyekCriteria = new MaganszemelyekCriteria();
        assertThat(maganszemelyekCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void maganszemelyekCriteriaFluentMethodsCreatesFiltersTest() {
        var maganszemelyekCriteria = new MaganszemelyekCriteria();

        setAllFilters(maganszemelyekCriteria);

        assertThat(maganszemelyekCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void maganszemelyekCriteriaCopyCreatesNullFilterTest() {
        var maganszemelyekCriteria = new MaganszemelyekCriteria();
        var copy = maganszemelyekCriteria.copy();

        assertThat(maganszemelyekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(maganszemelyekCriteria)
        );
    }

    @Test
    void maganszemelyekCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var maganszemelyekCriteria = new MaganszemelyekCriteria();
        setAllFilters(maganszemelyekCriteria);

        var copy = maganszemelyekCriteria.copy();

        assertThat(maganszemelyekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(maganszemelyekCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var maganszemelyekCriteria = new MaganszemelyekCriteria();

        assertThat(maganszemelyekCriteria).hasToString("MaganszemelyekCriteria{}");
    }

    private static void setAllFilters(MaganszemelyekCriteria maganszemelyekCriteria) {
        maganszemelyekCriteria.id();
        maganszemelyekCriteria.maganszemelyNeve();
        maganszemelyekCriteria.szemelyiIgazolvanySzama();
        maganszemelyekCriteria.adoAzonositoJel();
        maganszemelyekCriteria.tbAzonosito();
        maganszemelyekCriteria.bankszamlaszam();
        maganszemelyekCriteria.telefonszam();
        maganszemelyekCriteria.emailcim();
        maganszemelyekCriteria.statusz();
        maganszemelyekCriteria.distinct();
    }

    private static Condition<MaganszemelyekCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMaganszemelyNeve()) &&
                condition.apply(criteria.getSzemelyiIgazolvanySzama()) &&
                condition.apply(criteria.getAdoAzonositoJel()) &&
                condition.apply(criteria.getTbAzonosito()) &&
                condition.apply(criteria.getBankszamlaszam()) &&
                condition.apply(criteria.getTelefonszam()) &&
                condition.apply(criteria.getEmailcim()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MaganszemelyekCriteria> copyFiltersAre(
        MaganszemelyekCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMaganszemelyNeve(), copy.getMaganszemelyNeve()) &&
                condition.apply(criteria.getSzemelyiIgazolvanySzama(), copy.getSzemelyiIgazolvanySzama()) &&
                condition.apply(criteria.getAdoAzonositoJel(), copy.getAdoAzonositoJel()) &&
                condition.apply(criteria.getTbAzonosito(), copy.getTbAzonosito()) &&
                condition.apply(criteria.getBankszamlaszam(), copy.getBankszamlaszam()) &&
                condition.apply(criteria.getTelefonszam(), copy.getTelefonszam()) &&
                condition.apply(criteria.getEmailcim(), copy.getEmailcim()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

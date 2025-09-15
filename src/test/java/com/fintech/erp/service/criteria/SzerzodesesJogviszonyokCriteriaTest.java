package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SzerzodesesJogviszonyokCriteriaTest {

    @Test
    void newSzerzodesesJogviszonyokCriteriaHasAllFiltersNullTest() {
        var szerzodesesJogviszonyokCriteria = new SzerzodesesJogviszonyokCriteria();
        assertThat(szerzodesesJogviszonyokCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void szerzodesesJogviszonyokCriteriaFluentMethodsCreatesFiltersTest() {
        var szerzodesesJogviszonyokCriteria = new SzerzodesesJogviszonyokCriteria();

        setAllFilters(szerzodesesJogviszonyokCriteria);

        assertThat(szerzodesesJogviszonyokCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void szerzodesesJogviszonyokCriteriaCopyCreatesNullFilterTest() {
        var szerzodesesJogviszonyokCriteria = new SzerzodesesJogviszonyokCriteria();
        var copy = szerzodesesJogviszonyokCriteria.copy();

        assertThat(szerzodesesJogviszonyokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(szerzodesesJogviszonyokCriteria)
        );
    }

    @Test
    void szerzodesesJogviszonyokCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var szerzodesesJogviszonyokCriteria = new SzerzodesesJogviszonyokCriteria();
        setAllFilters(szerzodesesJogviszonyokCriteria);

        var copy = szerzodesesJogviszonyokCriteria.copy();

        assertThat(szerzodesesJogviszonyokCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(szerzodesesJogviszonyokCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var szerzodesesJogviszonyokCriteria = new SzerzodesesJogviszonyokCriteria();

        assertThat(szerzodesesJogviszonyokCriteria).hasToString("SzerzodesesJogviszonyokCriteria{}");
    }

    private static void setAllFilters(SzerzodesesJogviszonyokCriteria szerzodesesJogviszonyokCriteria) {
        szerzodesesJogviszonyokCriteria.id();
        szerzodesesJogviszonyokCriteria.szerzodesAzonosito();
        szerzodesesJogviszonyokCriteria.jogviszonyKezdete();
        szerzodesesJogviszonyokCriteria.jogviszonyLejarata();
        szerzodesesJogviszonyokCriteria.megrendeloCegId();
        szerzodesesJogviszonyokCriteria.vallalkozoCegId();
        szerzodesesJogviszonyokCriteria.distinct();
    }

    private static Condition<SzerzodesesJogviszonyokCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSzerzodesAzonosito()) &&
                condition.apply(criteria.getJogviszonyKezdete()) &&
                condition.apply(criteria.getJogviszonyLejarata()) &&
                condition.apply(criteria.getMegrendeloCegId()) &&
                condition.apply(criteria.getVallalkozoCegId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SzerzodesesJogviszonyokCriteria> copyFiltersAre(
        SzerzodesesJogviszonyokCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSzerzodesAzonosito(), copy.getSzerzodesAzonosito()) &&
                condition.apply(criteria.getJogviszonyKezdete(), copy.getJogviszonyKezdete()) &&
                condition.apply(criteria.getJogviszonyLejarata(), copy.getJogviszonyLejarata()) &&
                condition.apply(criteria.getMegrendeloCegId(), copy.getMegrendeloCegId()) &&
                condition.apply(criteria.getVallalkozoCegId(), copy.getVallalkozoCegId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

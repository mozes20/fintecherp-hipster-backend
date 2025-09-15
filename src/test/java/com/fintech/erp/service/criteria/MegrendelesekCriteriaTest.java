package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MegrendelesekCriteriaTest {

    @Test
    void newMegrendelesekCriteriaHasAllFiltersNullTest() {
        var megrendelesekCriteria = new MegrendelesekCriteria();
        assertThat(megrendelesekCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void megrendelesekCriteriaFluentMethodsCreatesFiltersTest() {
        var megrendelesekCriteria = new MegrendelesekCriteria();

        setAllFilters(megrendelesekCriteria);

        assertThat(megrendelesekCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void megrendelesekCriteriaCopyCreatesNullFilterTest() {
        var megrendelesekCriteria = new MegrendelesekCriteria();
        var copy = megrendelesekCriteria.copy();

        assertThat(megrendelesekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(megrendelesekCriteria)
        );
    }

    @Test
    void megrendelesekCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var megrendelesekCriteria = new MegrendelesekCriteria();
        setAllFilters(megrendelesekCriteria);

        var copy = megrendelesekCriteria.copy();

        assertThat(megrendelesekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(megrendelesekCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var megrendelesekCriteria = new MegrendelesekCriteria();

        assertThat(megrendelesekCriteria).hasToString("MegrendelesekCriteria{}");
    }

    private static void setAllFilters(MegrendelesekCriteria megrendelesekCriteria) {
        megrendelesekCriteria.id();
        megrendelesekCriteria.megrendelesTipusa();
        megrendelesekCriteria.feladatRovidLeirasa();
        megrendelesekCriteria.feladatReszletesLeirasa();
        megrendelesekCriteria.megrendelesKezdete();
        megrendelesekCriteria.megrendelesVege();
        megrendelesekCriteria.resztvevoKollagaTipusa();
        megrendelesekCriteria.devizanem();
        megrendelesekCriteria.dijazasTipusa();
        megrendelesekCriteria.dijOsszege();
        megrendelesekCriteria.megrendelesDokumentumGeneralta();
        megrendelesekCriteria.ugyfelMegrendelesId();
        megrendelesekCriteria.szerzodesesJogviszonyId();
        megrendelesekCriteria.maganszemelyId();
        megrendelesekCriteria.distinct();
    }

    private static Condition<MegrendelesekCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMegrendelesTipusa()) &&
                condition.apply(criteria.getFeladatRovidLeirasa()) &&
                condition.apply(criteria.getFeladatReszletesLeirasa()) &&
                condition.apply(criteria.getMegrendelesKezdete()) &&
                condition.apply(criteria.getMegrendelesVege()) &&
                condition.apply(criteria.getResztvevoKollagaTipusa()) &&
                condition.apply(criteria.getDevizanem()) &&
                condition.apply(criteria.getDijazasTipusa()) &&
                condition.apply(criteria.getDijOsszege()) &&
                condition.apply(criteria.getMegrendelesDokumentumGeneralta()) &&
                condition.apply(criteria.getUgyfelMegrendelesId()) &&
                condition.apply(criteria.getSzerzodesesJogviszonyId()) &&
                condition.apply(criteria.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MegrendelesekCriteria> copyFiltersAre(
        MegrendelesekCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMegrendelesTipusa(), copy.getMegrendelesTipusa()) &&
                condition.apply(criteria.getFeladatRovidLeirasa(), copy.getFeladatRovidLeirasa()) &&
                condition.apply(criteria.getFeladatReszletesLeirasa(), copy.getFeladatReszletesLeirasa()) &&
                condition.apply(criteria.getMegrendelesKezdete(), copy.getMegrendelesKezdete()) &&
                condition.apply(criteria.getMegrendelesVege(), copy.getMegrendelesVege()) &&
                condition.apply(criteria.getResztvevoKollagaTipusa(), copy.getResztvevoKollagaTipusa()) &&
                condition.apply(criteria.getDevizanem(), copy.getDevizanem()) &&
                condition.apply(criteria.getDijazasTipusa(), copy.getDijazasTipusa()) &&
                condition.apply(criteria.getDijOsszege(), copy.getDijOsszege()) &&
                condition.apply(criteria.getMegrendelesDokumentumGeneralta(), copy.getMegrendelesDokumentumGeneralta()) &&
                condition.apply(criteria.getUgyfelMegrendelesId(), copy.getUgyfelMegrendelesId()) &&
                condition.apply(criteria.getSzerzodesesJogviszonyId(), copy.getSzerzodesesJogviszonyId()) &&
                condition.apply(criteria.getMaganszemelyId(), copy.getMaganszemelyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

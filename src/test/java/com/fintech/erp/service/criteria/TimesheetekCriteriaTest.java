package com.fintech.erp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TimesheetekCriteriaTest {

    @Test
    void newTimesheetekCriteriaHasAllFiltersNullTest() {
        var timesheetekCriteria = new TimesheetekCriteria();
        assertThat(timesheetekCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void timesheetekCriteriaFluentMethodsCreatesFiltersTest() {
        var timesheetekCriteria = new TimesheetekCriteria();

        setAllFilters(timesheetekCriteria);

        assertThat(timesheetekCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void timesheetekCriteriaCopyCreatesNullFilterTest() {
        var timesheetekCriteria = new TimesheetekCriteria();
        var copy = timesheetekCriteria.copy();

        assertThat(timesheetekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(timesheetekCriteria)
        );
    }

    @Test
    void timesheetekCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var timesheetekCriteria = new TimesheetekCriteria();
        setAllFilters(timesheetekCriteria);

        var copy = timesheetekCriteria.copy();

        assertThat(timesheetekCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(timesheetekCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var timesheetekCriteria = new TimesheetekCriteria();

        assertThat(timesheetekCriteria).hasToString("TimesheetekCriteria{}");
    }

    private static void setAllFilters(TimesheetekCriteria timesheetekCriteria) {
        timesheetekCriteria.id();
        timesheetekCriteria.datum();
        timesheetekCriteria.munkanapStatusza();
        timesheetekCriteria.statusz();
        timesheetekCriteria.munkavallaloId();
        timesheetekCriteria.distinct();
    }

    private static Condition<TimesheetekCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDatum()) &&
                condition.apply(criteria.getMunkanapStatusza()) &&
                condition.apply(criteria.getStatusz()) &&
                condition.apply(criteria.getMunkavallaloId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TimesheetekCriteria> copyFiltersAre(TimesheetekCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDatum(), copy.getDatum()) &&
                condition.apply(criteria.getMunkanapStatusza(), copy.getMunkanapStatusza()) &&
                condition.apply(criteria.getStatusz(), copy.getStatusz()) &&
                condition.apply(criteria.getMunkavallaloId(), copy.getMunkavallaloId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

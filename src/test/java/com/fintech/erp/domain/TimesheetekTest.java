package com.fintech.erp.domain;

import static com.fintech.erp.domain.MunkavallalokTestSamples.*;
import static com.fintech.erp.domain.TimesheetekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimesheetekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timesheetek.class);
        Timesheetek timesheetek1 = getTimesheetekSample1();
        Timesheetek timesheetek2 = new Timesheetek();
        assertThat(timesheetek1).isNotEqualTo(timesheetek2);

        timesheetek2.setId(timesheetek1.getId());
        assertThat(timesheetek1).isEqualTo(timesheetek2);

        timesheetek2 = getTimesheetekSample2();
        assertThat(timesheetek1).isNotEqualTo(timesheetek2);
    }

    @Test
    void munkavallaloTest() {
        Timesheetek timesheetek = getTimesheetekRandomSampleGenerator();
        Munkavallalok munkavallalokBack = getMunkavallalokRandomSampleGenerator();

        timesheetek.setMunkavallalo(munkavallalokBack);
        assertThat(timesheetek.getMunkavallalo()).isEqualTo(munkavallalokBack);

        timesheetek.munkavallalo(null);
        assertThat(timesheetek.getMunkavallalo()).isNull();
    }
}

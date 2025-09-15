package com.fintech.erp.domain;

import static com.fintech.erp.domain.MegrendelesekTestSamples.*;
import static com.fintech.erp.domain.UgyfelElszamolasokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UgyfelElszamolasokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UgyfelElszamolasok.class);
        UgyfelElszamolasok ugyfelElszamolasok1 = getUgyfelElszamolasokSample1();
        UgyfelElszamolasok ugyfelElszamolasok2 = new UgyfelElszamolasok();
        assertThat(ugyfelElszamolasok1).isNotEqualTo(ugyfelElszamolasok2);

        ugyfelElszamolasok2.setId(ugyfelElszamolasok1.getId());
        assertThat(ugyfelElszamolasok1).isEqualTo(ugyfelElszamolasok2);

        ugyfelElszamolasok2 = getUgyfelElszamolasokSample2();
        assertThat(ugyfelElszamolasok1).isNotEqualTo(ugyfelElszamolasok2);
    }

    @Test
    void megrendelesTest() {
        UgyfelElszamolasok ugyfelElszamolasok = getUgyfelElszamolasokRandomSampleGenerator();
        Megrendelesek megrendelesekBack = getMegrendelesekRandomSampleGenerator();

        ugyfelElszamolasok.setMegrendeles(megrendelesekBack);
        assertThat(ugyfelElszamolasok.getMegrendeles()).isEqualTo(megrendelesekBack);

        ugyfelElszamolasok.megrendeles(null);
        assertThat(ugyfelElszamolasok.getMegrendeles()).isNull();
    }
}

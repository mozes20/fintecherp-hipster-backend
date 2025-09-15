package com.fintech.erp.domain;

import static com.fintech.erp.domain.MegrendelesDokumentumokTestSamples.*;
import static com.fintech.erp.domain.MegrendelesekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MegrendelesDokumentumokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MegrendelesDokumentumok.class);
        MegrendelesDokumentumok megrendelesDokumentumok1 = getMegrendelesDokumentumokSample1();
        MegrendelesDokumentumok megrendelesDokumentumok2 = new MegrendelesDokumentumok();
        assertThat(megrendelesDokumentumok1).isNotEqualTo(megrendelesDokumentumok2);

        megrendelesDokumentumok2.setId(megrendelesDokumentumok1.getId());
        assertThat(megrendelesDokumentumok1).isEqualTo(megrendelesDokumentumok2);

        megrendelesDokumentumok2 = getMegrendelesDokumentumokSample2();
        assertThat(megrendelesDokumentumok1).isNotEqualTo(megrendelesDokumentumok2);
    }

    @Test
    void megrendelesTest() {
        MegrendelesDokumentumok megrendelesDokumentumok = getMegrendelesDokumentumokRandomSampleGenerator();
        Megrendelesek megrendelesekBack = getMegrendelesekRandomSampleGenerator();

        megrendelesDokumentumok.setMegrendeles(megrendelesekBack);
        assertThat(megrendelesDokumentumok.getMegrendeles()).isEqualTo(megrendelesekBack);

        megrendelesDokumentumok.megrendeles(null);
        assertThat(megrendelesDokumentumok.getMegrendeles()).isNull();
    }
}

package com.fintech.erp.domain;

import static com.fintech.erp.domain.TeljesitesIgazolasDokumentumokTestSamples.*;
import static com.fintech.erp.domain.UgyfelElszamolasokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeljesitesIgazolasDokumentumokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeljesitesIgazolasDokumentumok.class);
        TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok1 = getTeljesitesIgazolasDokumentumokSample1();
        TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok2 = new TeljesitesIgazolasDokumentumok();
        assertThat(teljesitesIgazolasDokumentumok1).isNotEqualTo(teljesitesIgazolasDokumentumok2);

        teljesitesIgazolasDokumentumok2.setId(teljesitesIgazolasDokumentumok1.getId());
        assertThat(teljesitesIgazolasDokumentumok1).isEqualTo(teljesitesIgazolasDokumentumok2);

        teljesitesIgazolasDokumentumok2 = getTeljesitesIgazolasDokumentumokSample2();
        assertThat(teljesitesIgazolasDokumentumok1).isNotEqualTo(teljesitesIgazolasDokumentumok2);
    }

    @Test
    void teljesitesIgazolasTest() {
        TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok = getTeljesitesIgazolasDokumentumokRandomSampleGenerator();
        UgyfelElszamolasok ugyfelElszamolasokBack = getUgyfelElszamolasokRandomSampleGenerator();

        teljesitesIgazolasDokumentumok.setTeljesitesIgazolas(ugyfelElszamolasokBack);
        assertThat(teljesitesIgazolasDokumentumok.getTeljesitesIgazolas()).isEqualTo(ugyfelElszamolasokBack);

        teljesitesIgazolasDokumentumok.teljesitesIgazolas(null);
        assertThat(teljesitesIgazolasDokumentumok.getTeljesitesIgazolas()).isNull();
    }
}

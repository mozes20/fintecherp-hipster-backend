package com.fintech.erp.domain;

import static com.fintech.erp.domain.OsztalekfizetesiKozgyulesekTestSamples.*;
import static com.fintech.erp.domain.SajatCegAlapadatokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OsztalekfizetesiKozgyulesekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OsztalekfizetesiKozgyulesek.class);
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek1 = getOsztalekfizetesiKozgyulesekSample1();
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek2 = new OsztalekfizetesiKozgyulesek();
        assertThat(osztalekfizetesiKozgyulesek1).isNotEqualTo(osztalekfizetesiKozgyulesek2);

        osztalekfizetesiKozgyulesek2.setId(osztalekfizetesiKozgyulesek1.getId());
        assertThat(osztalekfizetesiKozgyulesek1).isEqualTo(osztalekfizetesiKozgyulesek2);

        osztalekfizetesiKozgyulesek2 = getOsztalekfizetesiKozgyulesekSample2();
        assertThat(osztalekfizetesiKozgyulesek1).isNotEqualTo(osztalekfizetesiKozgyulesek2);
    }

    @Test
    void sajatCegTest() {
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek = getOsztalekfizetesiKozgyulesekRandomSampleGenerator();
        SajatCegAlapadatok sajatCegAlapadatokBack = getSajatCegAlapadatokRandomSampleGenerator();

        osztalekfizetesiKozgyulesek.setSajatCeg(sajatCegAlapadatokBack);
        assertThat(osztalekfizetesiKozgyulesek.getSajatCeg()).isEqualTo(sajatCegAlapadatokBack);

        osztalekfizetesiKozgyulesek.sajatCeg(null);
        assertThat(osztalekfizetesiKozgyulesek.getSajatCeg()).isNull();
    }
}

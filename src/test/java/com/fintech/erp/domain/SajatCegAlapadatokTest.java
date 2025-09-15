package com.fintech.erp.domain;

import static com.fintech.erp.domain.CegAlapadatokTestSamples.*;
import static com.fintech.erp.domain.SajatCegAlapadatokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SajatCegAlapadatokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SajatCegAlapadatok.class);
        SajatCegAlapadatok sajatCegAlapadatok1 = getSajatCegAlapadatokSample1();
        SajatCegAlapadatok sajatCegAlapadatok2 = new SajatCegAlapadatok();
        assertThat(sajatCegAlapadatok1).isNotEqualTo(sajatCegAlapadatok2);

        sajatCegAlapadatok2.setId(sajatCegAlapadatok1.getId());
        assertThat(sajatCegAlapadatok1).isEqualTo(sajatCegAlapadatok2);

        sajatCegAlapadatok2 = getSajatCegAlapadatokSample2();
        assertThat(sajatCegAlapadatok1).isNotEqualTo(sajatCegAlapadatok2);
    }

    @Test
    void cegTest() {
        SajatCegAlapadatok sajatCegAlapadatok = getSajatCegAlapadatokRandomSampleGenerator();
        CegAlapadatok cegAlapadatokBack = getCegAlapadatokRandomSampleGenerator();

        sajatCegAlapadatok.setCeg(cegAlapadatokBack);
        assertThat(sajatCegAlapadatok.getCeg()).isEqualTo(cegAlapadatokBack);

        sajatCegAlapadatok.ceg(null);
        assertThat(sajatCegAlapadatok.getCeg()).isNull();
    }
}

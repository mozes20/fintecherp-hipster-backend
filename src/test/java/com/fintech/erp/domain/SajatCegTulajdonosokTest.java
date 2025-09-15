package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static com.fintech.erp.domain.SajatCegAlapadatokTestSamples.*;
import static com.fintech.erp.domain.SajatCegTulajdonosokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SajatCegTulajdonosokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SajatCegTulajdonosok.class);
        SajatCegTulajdonosok sajatCegTulajdonosok1 = getSajatCegTulajdonosokSample1();
        SajatCegTulajdonosok sajatCegTulajdonosok2 = new SajatCegTulajdonosok();
        assertThat(sajatCegTulajdonosok1).isNotEqualTo(sajatCegTulajdonosok2);

        sajatCegTulajdonosok2.setId(sajatCegTulajdonosok1.getId());
        assertThat(sajatCegTulajdonosok1).isEqualTo(sajatCegTulajdonosok2);

        sajatCegTulajdonosok2 = getSajatCegTulajdonosokSample2();
        assertThat(sajatCegTulajdonosok1).isNotEqualTo(sajatCegTulajdonosok2);
    }

    @Test
    void sajatCegTest() {
        SajatCegTulajdonosok sajatCegTulajdonosok = getSajatCegTulajdonosokRandomSampleGenerator();
        SajatCegAlapadatok sajatCegAlapadatokBack = getSajatCegAlapadatokRandomSampleGenerator();

        sajatCegTulajdonosok.setSajatCeg(sajatCegAlapadatokBack);
        assertThat(sajatCegTulajdonosok.getSajatCeg()).isEqualTo(sajatCegAlapadatokBack);

        sajatCegTulajdonosok.sajatCeg(null);
        assertThat(sajatCegTulajdonosok.getSajatCeg()).isNull();
    }

    @Test
    void maganszemelyTest() {
        SajatCegTulajdonosok sajatCegTulajdonosok = getSajatCegTulajdonosokRandomSampleGenerator();
        Maganszemelyek maganszemelyekBack = getMaganszemelyekRandomSampleGenerator();

        sajatCegTulajdonosok.setMaganszemely(maganszemelyekBack);
        assertThat(sajatCegTulajdonosok.getMaganszemely()).isEqualTo(maganszemelyekBack);

        sajatCegTulajdonosok.maganszemely(null);
        assertThat(sajatCegTulajdonosok.getMaganszemely()).isNull();
    }
}

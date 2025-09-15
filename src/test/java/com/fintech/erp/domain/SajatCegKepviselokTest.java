package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static com.fintech.erp.domain.SajatCegAlapadatokTestSamples.*;
import static com.fintech.erp.domain.SajatCegKepviselokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SajatCegKepviselokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SajatCegKepviselok.class);
        SajatCegKepviselok sajatCegKepviselok1 = getSajatCegKepviselokSample1();
        SajatCegKepviselok sajatCegKepviselok2 = new SajatCegKepviselok();
        assertThat(sajatCegKepviselok1).isNotEqualTo(sajatCegKepviselok2);

        sajatCegKepviselok2.setId(sajatCegKepviselok1.getId());
        assertThat(sajatCegKepviselok1).isEqualTo(sajatCegKepviselok2);

        sajatCegKepviselok2 = getSajatCegKepviselokSample2();
        assertThat(sajatCegKepviselok1).isNotEqualTo(sajatCegKepviselok2);
    }

    @Test
    void sajatCegTest() {
        SajatCegKepviselok sajatCegKepviselok = getSajatCegKepviselokRandomSampleGenerator();
        SajatCegAlapadatok sajatCegAlapadatokBack = getSajatCegAlapadatokRandomSampleGenerator();

        sajatCegKepviselok.setSajatCeg(sajatCegAlapadatokBack);
        assertThat(sajatCegKepviselok.getSajatCeg()).isEqualTo(sajatCegAlapadatokBack);

        sajatCegKepviselok.sajatCeg(null);
        assertThat(sajatCegKepviselok.getSajatCeg()).isNull();
    }

    @Test
    void maganszemelyTest() {
        SajatCegKepviselok sajatCegKepviselok = getSajatCegKepviselokRandomSampleGenerator();
        Maganszemelyek maganszemelyekBack = getMaganszemelyekRandomSampleGenerator();

        sajatCegKepviselok.setMaganszemely(maganszemelyekBack);
        assertThat(sajatCegKepviselok.getMaganszemely()).isEqualTo(maganszemelyekBack);

        sajatCegKepviselok.maganszemely(null);
        assertThat(sajatCegKepviselok.getMaganszemely()).isNull();
    }
}

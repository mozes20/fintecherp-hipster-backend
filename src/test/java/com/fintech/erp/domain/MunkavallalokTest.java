package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static com.fintech.erp.domain.MunkavallalokTestSamples.*;
import static com.fintech.erp.domain.SajatCegAlapadatokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MunkavallalokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Munkavallalok.class);
        Munkavallalok munkavallalok1 = getMunkavallalokSample1();
        Munkavallalok munkavallalok2 = new Munkavallalok();
        assertThat(munkavallalok1).isNotEqualTo(munkavallalok2);

        munkavallalok2.setId(munkavallalok1.getId());
        assertThat(munkavallalok1).isEqualTo(munkavallalok2);

        munkavallalok2 = getMunkavallalokSample2();
        assertThat(munkavallalok1).isNotEqualTo(munkavallalok2);
    }

    @Test
    void sajatCegTest() {
        Munkavallalok munkavallalok = getMunkavallalokRandomSampleGenerator();
        SajatCegAlapadatok sajatCegAlapadatokBack = getSajatCegAlapadatokRandomSampleGenerator();

        munkavallalok.setSajatCeg(sajatCegAlapadatokBack);
        assertThat(munkavallalok.getSajatCeg()).isEqualTo(sajatCegAlapadatokBack);

        munkavallalok.sajatCeg(null);
        assertThat(munkavallalok.getSajatCeg()).isNull();
    }

    @Test
    void maganszemelyTest() {
        Munkavallalok munkavallalok = getMunkavallalokRandomSampleGenerator();
        Maganszemelyek maganszemelyekBack = getMaganszemelyekRandomSampleGenerator();

        munkavallalok.setMaganszemely(maganszemelyekBack);
        assertThat(munkavallalok.getMaganszemely()).isEqualTo(maganszemelyekBack);

        munkavallalok.maganszemely(null);
        assertThat(munkavallalok.getMaganszemely()).isNull();
    }
}

package com.fintech.erp.domain;

import static com.fintech.erp.domain.CegAlapadatokTestSamples.*;
import static com.fintech.erp.domain.SzerzodesesJogviszonyokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SzerzodesesJogviszonyokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SzerzodesesJogviszonyok.class);
        SzerzodesesJogviszonyok szerzodesesJogviszonyok1 = getSzerzodesesJogviszonyokSample1();
        SzerzodesesJogviszonyok szerzodesesJogviszonyok2 = new SzerzodesesJogviszonyok();
        assertThat(szerzodesesJogviszonyok1).isNotEqualTo(szerzodesesJogviszonyok2);

        szerzodesesJogviszonyok2.setId(szerzodesesJogviszonyok1.getId());
        assertThat(szerzodesesJogviszonyok1).isEqualTo(szerzodesesJogviszonyok2);

        szerzodesesJogviszonyok2 = getSzerzodesesJogviszonyokSample2();
        assertThat(szerzodesesJogviszonyok1).isNotEqualTo(szerzodesesJogviszonyok2);
    }

    @Test
    void megrendeloCegTest() {
        SzerzodesesJogviszonyok szerzodesesJogviszonyok = getSzerzodesesJogviszonyokRandomSampleGenerator();
        CegAlapadatok cegAlapadatokBack = getCegAlapadatokRandomSampleGenerator();

        szerzodesesJogviszonyok.setMegrendeloCeg(cegAlapadatokBack);
        assertThat(szerzodesesJogviszonyok.getMegrendeloCeg()).isEqualTo(cegAlapadatokBack);

        szerzodesesJogviszonyok.megrendeloCeg(null);
        assertThat(szerzodesesJogviszonyok.getMegrendeloCeg()).isNull();
    }

    @Test
    void vallalkozoCegTest() {
        SzerzodesesJogviszonyok szerzodesesJogviszonyok = getSzerzodesesJogviszonyokRandomSampleGenerator();
        CegAlapadatok cegAlapadatokBack = getCegAlapadatokRandomSampleGenerator();

        szerzodesesJogviszonyok.setVallalkozoCeg(cegAlapadatokBack);
        assertThat(szerzodesesJogviszonyok.getVallalkozoCeg()).isEqualTo(cegAlapadatokBack);

        szerzodesesJogviszonyok.vallalkozoCeg(null);
        assertThat(szerzodesesJogviszonyok.getVallalkozoCeg()).isNull();
    }
}

package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static com.fintech.erp.domain.MegrendelesekTestSamples.*;
import static com.fintech.erp.domain.SzerzodesesJogviszonyokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MegrendelesekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Megrendelesek.class);
        Megrendelesek megrendelesek1 = getMegrendelesekSample1();
        Megrendelesek megrendelesek2 = new Megrendelesek();
        assertThat(megrendelesek1).isNotEqualTo(megrendelesek2);

        megrendelesek2.setId(megrendelesek1.getId());
        assertThat(megrendelesek1).isEqualTo(megrendelesek2);

        megrendelesek2 = getMegrendelesekSample2();
        assertThat(megrendelesek1).isNotEqualTo(megrendelesek2);
    }

    @Test
    void szerzodesesJogviszonyTest() {
        Megrendelesek megrendelesek = getMegrendelesekRandomSampleGenerator();
        SzerzodesesJogviszonyok szerzodesesJogviszonyokBack = getSzerzodesesJogviszonyokRandomSampleGenerator();

        megrendelesek.setSzerzodesesJogviszony(szerzodesesJogviszonyokBack);
        assertThat(megrendelesek.getSzerzodesesJogviszony()).isEqualTo(szerzodesesJogviszonyokBack);

        megrendelesek.szerzodesesJogviszony(null);
        assertThat(megrendelesek.getSzerzodesesJogviszony()).isNull();
    }

    @Test
    void maganszemelyTest() {
        Megrendelesek megrendelesek = getMegrendelesekRandomSampleGenerator();
        Maganszemelyek maganszemelyekBack = getMaganszemelyekRandomSampleGenerator();

        megrendelesek.setMaganszemely(maganszemelyekBack);
        assertThat(megrendelesek.getMaganszemely()).isEqualTo(maganszemelyekBack);

        megrendelesek.maganszemely(null);
        assertThat(megrendelesek.getMaganszemely()).isNull();
    }
}

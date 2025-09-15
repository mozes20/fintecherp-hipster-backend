package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static com.fintech.erp.domain.PartnerCegAdatokTestSamples.*;
import static com.fintech.erp.domain.PartnerCegMunkavallalokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerCegMunkavallalokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerCegMunkavallalok.class);
        PartnerCegMunkavallalok partnerCegMunkavallalok1 = getPartnerCegMunkavallalokSample1();
        PartnerCegMunkavallalok partnerCegMunkavallalok2 = new PartnerCegMunkavallalok();
        assertThat(partnerCegMunkavallalok1).isNotEqualTo(partnerCegMunkavallalok2);

        partnerCegMunkavallalok2.setId(partnerCegMunkavallalok1.getId());
        assertThat(partnerCegMunkavallalok1).isEqualTo(partnerCegMunkavallalok2);

        partnerCegMunkavallalok2 = getPartnerCegMunkavallalokSample2();
        assertThat(partnerCegMunkavallalok1).isNotEqualTo(partnerCegMunkavallalok2);
    }

    @Test
    void partnerCegTest() {
        PartnerCegMunkavallalok partnerCegMunkavallalok = getPartnerCegMunkavallalokRandomSampleGenerator();
        PartnerCegAdatok partnerCegAdatokBack = getPartnerCegAdatokRandomSampleGenerator();

        partnerCegMunkavallalok.setPartnerCeg(partnerCegAdatokBack);
        assertThat(partnerCegMunkavallalok.getPartnerCeg()).isEqualTo(partnerCegAdatokBack);

        partnerCegMunkavallalok.partnerCeg(null);
        assertThat(partnerCegMunkavallalok.getPartnerCeg()).isNull();
    }

    @Test
    void maganszemelyTest() {
        PartnerCegMunkavallalok partnerCegMunkavallalok = getPartnerCegMunkavallalokRandomSampleGenerator();
        Maganszemelyek maganszemelyekBack = getMaganszemelyekRandomSampleGenerator();

        partnerCegMunkavallalok.setMaganszemely(maganszemelyekBack);
        assertThat(partnerCegMunkavallalok.getMaganszemely()).isEqualTo(maganszemelyekBack);

        partnerCegMunkavallalok.maganszemely(null);
        assertThat(partnerCegMunkavallalok.getMaganszemely()).isNull();
    }
}

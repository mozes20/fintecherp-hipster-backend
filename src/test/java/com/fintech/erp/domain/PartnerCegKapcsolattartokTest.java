package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static com.fintech.erp.domain.PartnerCegAdatokTestSamples.*;
import static com.fintech.erp.domain.PartnerCegKapcsolattartokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerCegKapcsolattartokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerCegKapcsolattartok.class);
        PartnerCegKapcsolattartok partnerCegKapcsolattartok1 = getPartnerCegKapcsolattartokSample1();
        PartnerCegKapcsolattartok partnerCegKapcsolattartok2 = new PartnerCegKapcsolattartok();
        assertThat(partnerCegKapcsolattartok1).isNotEqualTo(partnerCegKapcsolattartok2);

        partnerCegKapcsolattartok2.setId(partnerCegKapcsolattartok1.getId());
        assertThat(partnerCegKapcsolattartok1).isEqualTo(partnerCegKapcsolattartok2);

        partnerCegKapcsolattartok2 = getPartnerCegKapcsolattartokSample2();
        assertThat(partnerCegKapcsolattartok1).isNotEqualTo(partnerCegKapcsolattartok2);
    }

    @Test
    void partnerCegTest() {
        PartnerCegKapcsolattartok partnerCegKapcsolattartok = getPartnerCegKapcsolattartokRandomSampleGenerator();
        PartnerCegAdatok partnerCegAdatokBack = getPartnerCegAdatokRandomSampleGenerator();

        partnerCegKapcsolattartok.setPartnerCeg(partnerCegAdatokBack);
        assertThat(partnerCegKapcsolattartok.getPartnerCeg()).isEqualTo(partnerCegAdatokBack);

        partnerCegKapcsolattartok.partnerCeg(null);
        assertThat(partnerCegKapcsolattartok.getPartnerCeg()).isNull();
    }

    @Test
    void maganszemelyTest() {
        PartnerCegKapcsolattartok partnerCegKapcsolattartok = getPartnerCegKapcsolattartokRandomSampleGenerator();
        Maganszemelyek maganszemelyekBack = getMaganszemelyekRandomSampleGenerator();

        partnerCegKapcsolattartok.setMaganszemely(maganszemelyekBack);
        assertThat(partnerCegKapcsolattartok.getMaganszemely()).isEqualTo(maganszemelyekBack);

        partnerCegKapcsolattartok.maganszemely(null);
        assertThat(partnerCegKapcsolattartok.getMaganszemely()).isNull();
    }
}

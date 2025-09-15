package com.fintech.erp.domain;

import static com.fintech.erp.domain.CegAlapadatokTestSamples.*;
import static com.fintech.erp.domain.PartnerCegAdatokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerCegAdatokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerCegAdatok.class);
        PartnerCegAdatok partnerCegAdatok1 = getPartnerCegAdatokSample1();
        PartnerCegAdatok partnerCegAdatok2 = new PartnerCegAdatok();
        assertThat(partnerCegAdatok1).isNotEqualTo(partnerCegAdatok2);

        partnerCegAdatok2.setId(partnerCegAdatok1.getId());
        assertThat(partnerCegAdatok1).isEqualTo(partnerCegAdatok2);

        partnerCegAdatok2 = getPartnerCegAdatokSample2();
        assertThat(partnerCegAdatok1).isNotEqualTo(partnerCegAdatok2);
    }

    @Test
    void cegTest() {
        PartnerCegAdatok partnerCegAdatok = getPartnerCegAdatokRandomSampleGenerator();
        CegAlapadatok cegAlapadatokBack = getCegAlapadatokRandomSampleGenerator();

        partnerCegAdatok.setCeg(cegAlapadatokBack);
        assertThat(partnerCegAdatok.getCeg()).isEqualTo(cegAlapadatokBack);

        partnerCegAdatok.ceg(null);
        assertThat(partnerCegAdatok.getCeg()).isNull();
    }
}

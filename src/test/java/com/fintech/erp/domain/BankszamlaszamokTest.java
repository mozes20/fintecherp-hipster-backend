package com.fintech.erp.domain;

import static com.fintech.erp.domain.BankszamlaszamokTestSamples.*;
import static com.fintech.erp.domain.CegAlapadatokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankszamlaszamokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bankszamlaszamok.class);
        Bankszamlaszamok bankszamlaszamok1 = getBankszamlaszamokSample1();
        Bankszamlaszamok bankszamlaszamok2 = new Bankszamlaszamok();
        assertThat(bankszamlaszamok1).isNotEqualTo(bankszamlaszamok2);

        bankszamlaszamok2.setId(bankszamlaszamok1.getId());
        assertThat(bankszamlaszamok1).isEqualTo(bankszamlaszamok2);

        bankszamlaszamok2 = getBankszamlaszamokSample2();
        assertThat(bankszamlaszamok1).isNotEqualTo(bankszamlaszamok2);
    }

    @Test
    void cegTest() {
        Bankszamlaszamok bankszamlaszamok = getBankszamlaszamokRandomSampleGenerator();
        CegAlapadatok cegAlapadatokBack = getCegAlapadatokRandomSampleGenerator();

        bankszamlaszamok.setCeg(cegAlapadatokBack);
        assertThat(bankszamlaszamok.getCeg()).isEqualTo(cegAlapadatokBack);

        bankszamlaszamok.ceg(null);
        assertThat(bankszamlaszamok.getCeg()).isNull();
    }
}

package com.fintech.erp.domain;

import static com.fintech.erp.domain.CegAlapadatokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CegAlapadatokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CegAlapadatok.class);
        CegAlapadatok cegAlapadatok1 = getCegAlapadatokSample1();
        CegAlapadatok cegAlapadatok2 = new CegAlapadatok();
        assertThat(cegAlapadatok1).isNotEqualTo(cegAlapadatok2);

        cegAlapadatok2.setId(cegAlapadatok1.getId());
        assertThat(cegAlapadatok1).isEqualTo(cegAlapadatok2);

        cegAlapadatok2 = getCegAlapadatokSample2();
        assertThat(cegAlapadatok1).isNotEqualTo(cegAlapadatok2);
    }
}

package com.fintech.erp.domain;

import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaganszemelyekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maganszemelyek.class);
        Maganszemelyek maganszemelyek1 = getMaganszemelyekSample1();
        Maganszemelyek maganszemelyek2 = new Maganszemelyek();
        assertThat(maganszemelyek1).isNotEqualTo(maganszemelyek2);

        maganszemelyek2.setId(maganszemelyek1.getId());
        assertThat(maganszemelyek1).isEqualTo(maganszemelyek2);

        maganszemelyek2 = getMaganszemelyekSample2();
        assertThat(maganszemelyek1).isNotEqualTo(maganszemelyek2);
    }
}

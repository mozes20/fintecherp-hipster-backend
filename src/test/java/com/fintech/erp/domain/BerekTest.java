package com.fintech.erp.domain;

import static com.fintech.erp.domain.BerekTestSamples.*;
import static com.fintech.erp.domain.MunkavallalokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BerekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Berek.class);
        Berek berek1 = getBerekSample1();
        Berek berek2 = new Berek();
        assertThat(berek1).isNotEqualTo(berek2);

        berek2.setId(berek1.getId());
        assertThat(berek1).isEqualTo(berek2);

        berek2 = getBerekSample2();
        assertThat(berek1).isNotEqualTo(berek2);
    }

    @Test
    void munkavallaloTest() {
        Berek berek = getBerekRandomSampleGenerator();
        Munkavallalok munkavallalokBack = getMunkavallalokRandomSampleGenerator();

        berek.setMunkavallalo(munkavallalokBack);
        assertThat(berek.getMunkavallalo()).isEqualTo(munkavallalokBack);

        berek.munkavallalo(null);
        assertThat(berek.getMunkavallalo()).isNull();
    }
}

package com.fintech.erp.domain;

import static com.fintech.erp.domain.EfoFoglalkoztatasokTestSamples.*;
import static com.fintech.erp.domain.MunkavallalokTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EfoFoglalkoztatasokTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EfoFoglalkoztatasok.class);
        EfoFoglalkoztatasok efoFoglalkoztatasok1 = getEfoFoglalkoztatasokSample1();
        EfoFoglalkoztatasok efoFoglalkoztatasok2 = new EfoFoglalkoztatasok();
        assertThat(efoFoglalkoztatasok1).isNotEqualTo(efoFoglalkoztatasok2);

        efoFoglalkoztatasok2.setId(efoFoglalkoztatasok1.getId());
        assertThat(efoFoglalkoztatasok1).isEqualTo(efoFoglalkoztatasok2);

        efoFoglalkoztatasok2 = getEfoFoglalkoztatasokSample2();
        assertThat(efoFoglalkoztatasok1).isNotEqualTo(efoFoglalkoztatasok2);
    }

    @Test
    void munkavallaloTest() {
        EfoFoglalkoztatasok efoFoglalkoztatasok = getEfoFoglalkoztatasokRandomSampleGenerator();
        Munkavallalok munkavallalokBack = getMunkavallalokRandomSampleGenerator();

        efoFoglalkoztatasok.setMunkavallalo(munkavallalokBack);
        assertThat(efoFoglalkoztatasok.getMunkavallalo()).isEqualTo(munkavallalokBack);

        efoFoglalkoztatasok.munkavallalo(null);
        assertThat(efoFoglalkoztatasok.getMunkavallalo()).isNull();
    }
}

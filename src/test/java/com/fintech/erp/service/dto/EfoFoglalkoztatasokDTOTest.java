package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EfoFoglalkoztatasokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EfoFoglalkoztatasokDTO.class);
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO1 = new EfoFoglalkoztatasokDTO();
        efoFoglalkoztatasokDTO1.setId(1L);
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO2 = new EfoFoglalkoztatasokDTO();
        assertThat(efoFoglalkoztatasokDTO1).isNotEqualTo(efoFoglalkoztatasokDTO2);
        efoFoglalkoztatasokDTO2.setId(efoFoglalkoztatasokDTO1.getId());
        assertThat(efoFoglalkoztatasokDTO1).isEqualTo(efoFoglalkoztatasokDTO2);
        efoFoglalkoztatasokDTO2.setId(2L);
        assertThat(efoFoglalkoztatasokDTO1).isNotEqualTo(efoFoglalkoztatasokDTO2);
        efoFoglalkoztatasokDTO1.setId(null);
        assertThat(efoFoglalkoztatasokDTO1).isNotEqualTo(efoFoglalkoztatasokDTO2);
    }
}

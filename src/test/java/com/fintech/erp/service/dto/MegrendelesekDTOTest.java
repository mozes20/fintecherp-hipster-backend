package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MegrendelesekDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MegrendelesekDTO.class);
        MegrendelesekDTO megrendelesekDTO1 = new MegrendelesekDTO();
        megrendelesekDTO1.setId(1L);
        MegrendelesekDTO megrendelesekDTO2 = new MegrendelesekDTO();
        assertThat(megrendelesekDTO1).isNotEqualTo(megrendelesekDTO2);
        megrendelesekDTO2.setId(megrendelesekDTO1.getId());
        assertThat(megrendelesekDTO1).isEqualTo(megrendelesekDTO2);
        megrendelesekDTO2.setId(2L);
        assertThat(megrendelesekDTO1).isNotEqualTo(megrendelesekDTO2);
        megrendelesekDTO1.setId(null);
        assertThat(megrendelesekDTO1).isNotEqualTo(megrendelesekDTO2);
    }
}

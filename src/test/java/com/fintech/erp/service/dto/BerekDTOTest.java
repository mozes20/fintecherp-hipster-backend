package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BerekDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BerekDTO.class);
        BerekDTO berekDTO1 = new BerekDTO();
        berekDTO1.setId(1L);
        BerekDTO berekDTO2 = new BerekDTO();
        assertThat(berekDTO1).isNotEqualTo(berekDTO2);
        berekDTO2.setId(berekDTO1.getId());
        assertThat(berekDTO1).isEqualTo(berekDTO2);
        berekDTO2.setId(2L);
        assertThat(berekDTO1).isNotEqualTo(berekDTO2);
        berekDTO1.setId(null);
        assertThat(berekDTO1).isNotEqualTo(berekDTO2);
    }
}

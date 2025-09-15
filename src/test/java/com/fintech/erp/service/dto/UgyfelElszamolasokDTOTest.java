package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UgyfelElszamolasokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UgyfelElszamolasokDTO.class);
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO1 = new UgyfelElszamolasokDTO();
        ugyfelElszamolasokDTO1.setId(1L);
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO2 = new UgyfelElszamolasokDTO();
        assertThat(ugyfelElszamolasokDTO1).isNotEqualTo(ugyfelElszamolasokDTO2);
        ugyfelElszamolasokDTO2.setId(ugyfelElszamolasokDTO1.getId());
        assertThat(ugyfelElszamolasokDTO1).isEqualTo(ugyfelElszamolasokDTO2);
        ugyfelElszamolasokDTO2.setId(2L);
        assertThat(ugyfelElszamolasokDTO1).isNotEqualTo(ugyfelElszamolasokDTO2);
        ugyfelElszamolasokDTO1.setId(null);
        assertThat(ugyfelElszamolasokDTO1).isNotEqualTo(ugyfelElszamolasokDTO2);
    }
}

package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SajatCegAlapadatokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SajatCegAlapadatokDTO.class);
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO1 = new SajatCegAlapadatokDTO();
        sajatCegAlapadatokDTO1.setId(1L);
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO2 = new SajatCegAlapadatokDTO();
        assertThat(sajatCegAlapadatokDTO1).isNotEqualTo(sajatCegAlapadatokDTO2);
        sajatCegAlapadatokDTO2.setId(sajatCegAlapadatokDTO1.getId());
        assertThat(sajatCegAlapadatokDTO1).isEqualTo(sajatCegAlapadatokDTO2);
        sajatCegAlapadatokDTO2.setId(2L);
        assertThat(sajatCegAlapadatokDTO1).isNotEqualTo(sajatCegAlapadatokDTO2);
        sajatCegAlapadatokDTO1.setId(null);
        assertThat(sajatCegAlapadatokDTO1).isNotEqualTo(sajatCegAlapadatokDTO2);
    }
}

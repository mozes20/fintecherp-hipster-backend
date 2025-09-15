package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SajatCegKepviselokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SajatCegKepviselokDTO.class);
        SajatCegKepviselokDTO sajatCegKepviselokDTO1 = new SajatCegKepviselokDTO();
        sajatCegKepviselokDTO1.setId(1L);
        SajatCegKepviselokDTO sajatCegKepviselokDTO2 = new SajatCegKepviselokDTO();
        assertThat(sajatCegKepviselokDTO1).isNotEqualTo(sajatCegKepviselokDTO2);
        sajatCegKepviselokDTO2.setId(sajatCegKepviselokDTO1.getId());
        assertThat(sajatCegKepviselokDTO1).isEqualTo(sajatCegKepviselokDTO2);
        sajatCegKepviselokDTO2.setId(2L);
        assertThat(sajatCegKepviselokDTO1).isNotEqualTo(sajatCegKepviselokDTO2);
        sajatCegKepviselokDTO1.setId(null);
        assertThat(sajatCegKepviselokDTO1).isNotEqualTo(sajatCegKepviselokDTO2);
    }
}

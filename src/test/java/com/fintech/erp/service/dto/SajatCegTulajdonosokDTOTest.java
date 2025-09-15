package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SajatCegTulajdonosokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SajatCegTulajdonosokDTO.class);
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO1 = new SajatCegTulajdonosokDTO();
        sajatCegTulajdonosokDTO1.setId(1L);
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO2 = new SajatCegTulajdonosokDTO();
        assertThat(sajatCegTulajdonosokDTO1).isNotEqualTo(sajatCegTulajdonosokDTO2);
        sajatCegTulajdonosokDTO2.setId(sajatCegTulajdonosokDTO1.getId());
        assertThat(sajatCegTulajdonosokDTO1).isEqualTo(sajatCegTulajdonosokDTO2);
        sajatCegTulajdonosokDTO2.setId(2L);
        assertThat(sajatCegTulajdonosokDTO1).isNotEqualTo(sajatCegTulajdonosokDTO2);
        sajatCegTulajdonosokDTO1.setId(null);
        assertThat(sajatCegTulajdonosokDTO1).isNotEqualTo(sajatCegTulajdonosokDTO2);
    }
}

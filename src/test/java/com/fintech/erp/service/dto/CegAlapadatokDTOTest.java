package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CegAlapadatokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CegAlapadatokDTO.class);
        CegAlapadatokDTO cegAlapadatokDTO1 = new CegAlapadatokDTO();
        cegAlapadatokDTO1.setId(1L);
        CegAlapadatokDTO cegAlapadatokDTO2 = new CegAlapadatokDTO();
        assertThat(cegAlapadatokDTO1).isNotEqualTo(cegAlapadatokDTO2);
        cegAlapadatokDTO2.setId(cegAlapadatokDTO1.getId());
        assertThat(cegAlapadatokDTO1).isEqualTo(cegAlapadatokDTO2);
        cegAlapadatokDTO2.setId(2L);
        assertThat(cegAlapadatokDTO1).isNotEqualTo(cegAlapadatokDTO2);
        cegAlapadatokDTO1.setId(null);
        assertThat(cegAlapadatokDTO1).isNotEqualTo(cegAlapadatokDTO2);
    }
}

package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerCegMunkavallalokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerCegMunkavallalokDTO.class);
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO1 = new PartnerCegMunkavallalokDTO();
        partnerCegMunkavallalokDTO1.setId(1L);
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO2 = new PartnerCegMunkavallalokDTO();
        assertThat(partnerCegMunkavallalokDTO1).isNotEqualTo(partnerCegMunkavallalokDTO2);
        partnerCegMunkavallalokDTO2.setId(partnerCegMunkavallalokDTO1.getId());
        assertThat(partnerCegMunkavallalokDTO1).isEqualTo(partnerCegMunkavallalokDTO2);
        partnerCegMunkavallalokDTO2.setId(2L);
        assertThat(partnerCegMunkavallalokDTO1).isNotEqualTo(partnerCegMunkavallalokDTO2);
        partnerCegMunkavallalokDTO1.setId(null);
        assertThat(partnerCegMunkavallalokDTO1).isNotEqualTo(partnerCegMunkavallalokDTO2);
    }
}

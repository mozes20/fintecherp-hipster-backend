package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerCegAdatokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerCegAdatokDTO.class);
        PartnerCegAdatokDTO partnerCegAdatokDTO1 = new PartnerCegAdatokDTO();
        partnerCegAdatokDTO1.setId(1L);
        PartnerCegAdatokDTO partnerCegAdatokDTO2 = new PartnerCegAdatokDTO();
        assertThat(partnerCegAdatokDTO1).isNotEqualTo(partnerCegAdatokDTO2);
        partnerCegAdatokDTO2.setId(partnerCegAdatokDTO1.getId());
        assertThat(partnerCegAdatokDTO1).isEqualTo(partnerCegAdatokDTO2);
        partnerCegAdatokDTO2.setId(2L);
        assertThat(partnerCegAdatokDTO1).isNotEqualTo(partnerCegAdatokDTO2);
        partnerCegAdatokDTO1.setId(null);
        assertThat(partnerCegAdatokDTO1).isNotEqualTo(partnerCegAdatokDTO2);
    }
}

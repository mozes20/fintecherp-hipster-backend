package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerCegKapcsolattartokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerCegKapcsolattartokDTO.class);
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO1 = new PartnerCegKapcsolattartokDTO();
        partnerCegKapcsolattartokDTO1.setId(1L);
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO2 = new PartnerCegKapcsolattartokDTO();
        assertThat(partnerCegKapcsolattartokDTO1).isNotEqualTo(partnerCegKapcsolattartokDTO2);
        partnerCegKapcsolattartokDTO2.setId(partnerCegKapcsolattartokDTO1.getId());
        assertThat(partnerCegKapcsolattartokDTO1).isEqualTo(partnerCegKapcsolattartokDTO2);
        partnerCegKapcsolattartokDTO2.setId(2L);
        assertThat(partnerCegKapcsolattartokDTO1).isNotEqualTo(partnerCegKapcsolattartokDTO2);
        partnerCegKapcsolattartokDTO1.setId(null);
        assertThat(partnerCegKapcsolattartokDTO1).isNotEqualTo(partnerCegKapcsolattartokDTO2);
    }
}

package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeljesitesIgazolasDokumentumokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeljesitesIgazolasDokumentumokDTO.class);
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO1 = new TeljesitesIgazolasDokumentumokDTO();
        teljesitesIgazolasDokumentumokDTO1.setId(1L);
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO2 = new TeljesitesIgazolasDokumentumokDTO();
        assertThat(teljesitesIgazolasDokumentumokDTO1).isNotEqualTo(teljesitesIgazolasDokumentumokDTO2);
        teljesitesIgazolasDokumentumokDTO2.setId(teljesitesIgazolasDokumentumokDTO1.getId());
        assertThat(teljesitesIgazolasDokumentumokDTO1).isEqualTo(teljesitesIgazolasDokumentumokDTO2);
        teljesitesIgazolasDokumentumokDTO2.setId(2L);
        assertThat(teljesitesIgazolasDokumentumokDTO1).isNotEqualTo(teljesitesIgazolasDokumentumokDTO2);
        teljesitesIgazolasDokumentumokDTO1.setId(null);
        assertThat(teljesitesIgazolasDokumentumokDTO1).isNotEqualTo(teljesitesIgazolasDokumentumokDTO2);
    }
}

package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MegrendelesDokumentumokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MegrendelesDokumentumokDTO.class);
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO1 = new MegrendelesDokumentumokDTO();
        megrendelesDokumentumokDTO1.setId(1L);
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO2 = new MegrendelesDokumentumokDTO();
        assertThat(megrendelesDokumentumokDTO1).isNotEqualTo(megrendelesDokumentumokDTO2);
        megrendelesDokumentumokDTO2.setId(megrendelesDokumentumokDTO1.getId());
        assertThat(megrendelesDokumentumokDTO1).isEqualTo(megrendelesDokumentumokDTO2);
        megrendelesDokumentumokDTO2.setId(2L);
        assertThat(megrendelesDokumentumokDTO1).isNotEqualTo(megrendelesDokumentumokDTO2);
        megrendelesDokumentumokDTO1.setId(null);
        assertThat(megrendelesDokumentumokDTO1).isNotEqualTo(megrendelesDokumentumokDTO2);
    }
}

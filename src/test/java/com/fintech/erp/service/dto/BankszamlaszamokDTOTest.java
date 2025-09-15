package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankszamlaszamokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankszamlaszamokDTO.class);
        BankszamlaszamokDTO bankszamlaszamokDTO1 = new BankszamlaszamokDTO();
        bankszamlaszamokDTO1.setId(1L);
        BankszamlaszamokDTO bankszamlaszamokDTO2 = new BankszamlaszamokDTO();
        assertThat(bankszamlaszamokDTO1).isNotEqualTo(bankszamlaszamokDTO2);
        bankszamlaszamokDTO2.setId(bankszamlaszamokDTO1.getId());
        assertThat(bankszamlaszamokDTO1).isEqualTo(bankszamlaszamokDTO2);
        bankszamlaszamokDTO2.setId(2L);
        assertThat(bankszamlaszamokDTO1).isNotEqualTo(bankszamlaszamokDTO2);
        bankszamlaszamokDTO1.setId(null);
        assertThat(bankszamlaszamokDTO1).isNotEqualTo(bankszamlaszamokDTO2);
    }
}

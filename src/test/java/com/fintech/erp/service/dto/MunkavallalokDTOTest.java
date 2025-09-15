package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MunkavallalokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MunkavallalokDTO.class);
        MunkavallalokDTO munkavallalokDTO1 = new MunkavallalokDTO();
        munkavallalokDTO1.setId(1L);
        MunkavallalokDTO munkavallalokDTO2 = new MunkavallalokDTO();
        assertThat(munkavallalokDTO1).isNotEqualTo(munkavallalokDTO2);
        munkavallalokDTO2.setId(munkavallalokDTO1.getId());
        assertThat(munkavallalokDTO1).isEqualTo(munkavallalokDTO2);
        munkavallalokDTO2.setId(2L);
        assertThat(munkavallalokDTO1).isNotEqualTo(munkavallalokDTO2);
        munkavallalokDTO1.setId(null);
        assertThat(munkavallalokDTO1).isNotEqualTo(munkavallalokDTO2);
    }
}

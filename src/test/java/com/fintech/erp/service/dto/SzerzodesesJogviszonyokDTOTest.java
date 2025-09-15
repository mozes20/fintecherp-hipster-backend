package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SzerzodesesJogviszonyokDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SzerzodesesJogviszonyokDTO.class);
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO1 = new SzerzodesesJogviszonyokDTO();
        szerzodesesJogviszonyokDTO1.setId(1L);
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO2 = new SzerzodesesJogviszonyokDTO();
        assertThat(szerzodesesJogviszonyokDTO1).isNotEqualTo(szerzodesesJogviszonyokDTO2);
        szerzodesesJogviszonyokDTO2.setId(szerzodesesJogviszonyokDTO1.getId());
        assertThat(szerzodesesJogviszonyokDTO1).isEqualTo(szerzodesesJogviszonyokDTO2);
        szerzodesesJogviszonyokDTO2.setId(2L);
        assertThat(szerzodesesJogviszonyokDTO1).isNotEqualTo(szerzodesesJogviszonyokDTO2);
        szerzodesesJogviszonyokDTO1.setId(null);
        assertThat(szerzodesesJogviszonyokDTO1).isNotEqualTo(szerzodesesJogviszonyokDTO2);
    }
}

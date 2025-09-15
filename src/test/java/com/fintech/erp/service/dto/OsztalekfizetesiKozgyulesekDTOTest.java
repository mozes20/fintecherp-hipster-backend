package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OsztalekfizetesiKozgyulesekDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OsztalekfizetesiKozgyulesekDTO.class);
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO1 = new OsztalekfizetesiKozgyulesekDTO();
        osztalekfizetesiKozgyulesekDTO1.setId(1L);
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO2 = new OsztalekfizetesiKozgyulesekDTO();
        assertThat(osztalekfizetesiKozgyulesekDTO1).isNotEqualTo(osztalekfizetesiKozgyulesekDTO2);
        osztalekfizetesiKozgyulesekDTO2.setId(osztalekfizetesiKozgyulesekDTO1.getId());
        assertThat(osztalekfizetesiKozgyulesekDTO1).isEqualTo(osztalekfizetesiKozgyulesekDTO2);
        osztalekfizetesiKozgyulesekDTO2.setId(2L);
        assertThat(osztalekfizetesiKozgyulesekDTO1).isNotEqualTo(osztalekfizetesiKozgyulesekDTO2);
        osztalekfizetesiKozgyulesekDTO1.setId(null);
        assertThat(osztalekfizetesiKozgyulesekDTO1).isNotEqualTo(osztalekfizetesiKozgyulesekDTO2);
    }
}

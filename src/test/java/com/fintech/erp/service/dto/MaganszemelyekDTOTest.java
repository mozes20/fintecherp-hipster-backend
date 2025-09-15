package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaganszemelyekDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaganszemelyekDTO.class);
        MaganszemelyekDTO maganszemelyekDTO1 = new MaganszemelyekDTO();
        maganszemelyekDTO1.setId(1L);
        MaganszemelyekDTO maganszemelyekDTO2 = new MaganszemelyekDTO();
        assertThat(maganszemelyekDTO1).isNotEqualTo(maganszemelyekDTO2);
        maganszemelyekDTO2.setId(maganszemelyekDTO1.getId());
        assertThat(maganszemelyekDTO1).isEqualTo(maganszemelyekDTO2);
        maganszemelyekDTO2.setId(2L);
        assertThat(maganszemelyekDTO1).isNotEqualTo(maganszemelyekDTO2);
        maganszemelyekDTO1.setId(null);
        assertThat(maganszemelyekDTO1).isNotEqualTo(maganszemelyekDTO2);
    }
}

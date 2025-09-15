package com.fintech.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fintech.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimesheetekDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetekDTO.class);
        TimesheetekDTO timesheetekDTO1 = new TimesheetekDTO();
        timesheetekDTO1.setId(1L);
        TimesheetekDTO timesheetekDTO2 = new TimesheetekDTO();
        assertThat(timesheetekDTO1).isNotEqualTo(timesheetekDTO2);
        timesheetekDTO2.setId(timesheetekDTO1.getId());
        assertThat(timesheetekDTO1).isEqualTo(timesheetekDTO2);
        timesheetekDTO2.setId(2L);
        assertThat(timesheetekDTO1).isNotEqualTo(timesheetekDTO2);
        timesheetekDTO1.setId(null);
        assertThat(timesheetekDTO1).isNotEqualTo(timesheetekDTO2);
    }
}

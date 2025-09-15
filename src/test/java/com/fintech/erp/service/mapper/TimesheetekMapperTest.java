package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.TimesheetekAsserts.*;
import static com.fintech.erp.domain.TimesheetekTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimesheetekMapperTest {

    private TimesheetekMapper timesheetekMapper;

    @BeforeEach
    void setUp() {
        timesheetekMapper = new TimesheetekMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTimesheetekSample1();
        var actual = timesheetekMapper.toEntity(timesheetekMapper.toDto(expected));
        assertTimesheetekAllPropertiesEquals(expected, actual);
    }
}

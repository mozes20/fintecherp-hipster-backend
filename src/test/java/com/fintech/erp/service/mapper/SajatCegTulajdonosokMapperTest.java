package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.SajatCegTulajdonosokAsserts.*;
import static com.fintech.erp.domain.SajatCegTulajdonosokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SajatCegTulajdonosokMapperTest {

    private SajatCegTulajdonosokMapper sajatCegTulajdonosokMapper;

    @BeforeEach
    void setUp() {
        sajatCegTulajdonosokMapper = new SajatCegTulajdonosokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSajatCegTulajdonosokSample1();
        var actual = sajatCegTulajdonosokMapper.toEntity(sajatCegTulajdonosokMapper.toDto(expected));
        assertSajatCegTulajdonosokAllPropertiesEquals(expected, actual);
    }
}

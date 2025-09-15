package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.SajatCegKepviselokAsserts.*;
import static com.fintech.erp.domain.SajatCegKepviselokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SajatCegKepviselokMapperTest {

    private SajatCegKepviselokMapper sajatCegKepviselokMapper;

    @BeforeEach
    void setUp() {
        sajatCegKepviselokMapper = new SajatCegKepviselokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSajatCegKepviselokSample1();
        var actual = sajatCegKepviselokMapper.toEntity(sajatCegKepviselokMapper.toDto(expected));
        assertSajatCegKepviselokAllPropertiesEquals(expected, actual);
    }
}

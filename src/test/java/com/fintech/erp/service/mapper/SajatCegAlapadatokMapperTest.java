package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.SajatCegAlapadatokAsserts.*;
import static com.fintech.erp.domain.SajatCegAlapadatokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SajatCegAlapadatokMapperTest {

    private SajatCegAlapadatokMapper sajatCegAlapadatokMapper;

    @BeforeEach
    void setUp() {
        sajatCegAlapadatokMapper = new SajatCegAlapadatokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSajatCegAlapadatokSample1();
        var actual = sajatCegAlapadatokMapper.toEntity(sajatCegAlapadatokMapper.toDto(expected));
        assertSajatCegAlapadatokAllPropertiesEquals(expected, actual);
    }
}

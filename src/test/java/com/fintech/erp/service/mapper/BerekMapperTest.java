package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.BerekAsserts.*;
import static com.fintech.erp.domain.BerekTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BerekMapperTest {

    private BerekMapper berekMapper;

    @BeforeEach
    void setUp() {
        berekMapper = new BerekMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBerekSample1();
        var actual = berekMapper.toEntity(berekMapper.toDto(expected));
        assertBerekAllPropertiesEquals(expected, actual);
    }
}

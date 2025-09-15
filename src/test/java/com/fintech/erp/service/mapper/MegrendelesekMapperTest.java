package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.MegrendelesekAsserts.*;
import static com.fintech.erp.domain.MegrendelesekTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MegrendelesekMapperTest {

    private MegrendelesekMapper megrendelesekMapper;

    @BeforeEach
    void setUp() {
        megrendelesekMapper = new MegrendelesekMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMegrendelesekSample1();
        var actual = megrendelesekMapper.toEntity(megrendelesekMapper.toDto(expected));
        assertMegrendelesekAllPropertiesEquals(expected, actual);
    }
}

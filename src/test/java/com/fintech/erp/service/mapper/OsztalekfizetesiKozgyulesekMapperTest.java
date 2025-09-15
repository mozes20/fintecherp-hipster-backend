package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.OsztalekfizetesiKozgyulesekAsserts.*;
import static com.fintech.erp.domain.OsztalekfizetesiKozgyulesekTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OsztalekfizetesiKozgyulesekMapperTest {

    private OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper;

    @BeforeEach
    void setUp() {
        osztalekfizetesiKozgyulesekMapper = new OsztalekfizetesiKozgyulesekMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOsztalekfizetesiKozgyulesekSample1();
        var actual = osztalekfizetesiKozgyulesekMapper.toEntity(osztalekfizetesiKozgyulesekMapper.toDto(expected));
        assertOsztalekfizetesiKozgyulesekAllPropertiesEquals(expected, actual);
    }
}

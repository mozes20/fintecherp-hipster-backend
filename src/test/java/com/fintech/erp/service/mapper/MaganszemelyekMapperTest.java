package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.MaganszemelyekAsserts.*;
import static com.fintech.erp.domain.MaganszemelyekTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaganszemelyekMapperTest {

    private MaganszemelyekMapper maganszemelyekMapper;

    @BeforeEach
    void setUp() {
        maganszemelyekMapper = new MaganszemelyekMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMaganszemelyekSample1();
        var actual = maganszemelyekMapper.toEntity(maganszemelyekMapper.toDto(expected));
        assertMaganszemelyekAllPropertiesEquals(expected, actual);
    }
}

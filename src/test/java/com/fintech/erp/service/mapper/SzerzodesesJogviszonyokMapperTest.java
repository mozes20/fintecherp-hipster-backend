package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.SzerzodesesJogviszonyokAsserts.*;
import static com.fintech.erp.domain.SzerzodesesJogviszonyokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SzerzodesesJogviszonyokMapperTest {

    private SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper;

    @BeforeEach
    void setUp() {
        szerzodesesJogviszonyokMapper = new SzerzodesesJogviszonyokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSzerzodesesJogviszonyokSample1();
        var actual = szerzodesesJogviszonyokMapper.toEntity(szerzodesesJogviszonyokMapper.toDto(expected));
        assertSzerzodesesJogviszonyokAllPropertiesEquals(expected, actual);
    }
}

package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.MunkavallalokAsserts.*;
import static com.fintech.erp.domain.MunkavallalokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MunkavallalokMapperTest {

    private MunkavallalokMapper munkavallalokMapper;

    @BeforeEach
    void setUp() {
        munkavallalokMapper = new MunkavallalokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMunkavallalokSample1();
        var actual = munkavallalokMapper.toEntity(munkavallalokMapper.toDto(expected));
        assertMunkavallalokAllPropertiesEquals(expected, actual);
    }
}

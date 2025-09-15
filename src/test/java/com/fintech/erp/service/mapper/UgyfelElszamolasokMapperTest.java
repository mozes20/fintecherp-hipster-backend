package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.UgyfelElszamolasokAsserts.*;
import static com.fintech.erp.domain.UgyfelElszamolasokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UgyfelElszamolasokMapperTest {

    private UgyfelElszamolasokMapper ugyfelElszamolasokMapper;

    @BeforeEach
    void setUp() {
        ugyfelElszamolasokMapper = new UgyfelElszamolasokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUgyfelElszamolasokSample1();
        var actual = ugyfelElszamolasokMapper.toEntity(ugyfelElszamolasokMapper.toDto(expected));
        assertUgyfelElszamolasokAllPropertiesEquals(expected, actual);
    }
}

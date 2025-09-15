package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.CegAlapadatokAsserts.*;
import static com.fintech.erp.domain.CegAlapadatokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CegAlapadatokMapperTest {

    private CegAlapadatokMapper cegAlapadatokMapper;

    @BeforeEach
    void setUp() {
        cegAlapadatokMapper = new CegAlapadatokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCegAlapadatokSample1();
        var actual = cegAlapadatokMapper.toEntity(cegAlapadatokMapper.toDto(expected));
        assertCegAlapadatokAllPropertiesEquals(expected, actual);
    }
}

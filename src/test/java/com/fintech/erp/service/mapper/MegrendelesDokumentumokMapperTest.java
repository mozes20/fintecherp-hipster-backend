package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.MegrendelesDokumentumokAsserts.*;
import static com.fintech.erp.domain.MegrendelesDokumentumokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MegrendelesDokumentumokMapperTest {

    private MegrendelesDokumentumokMapper megrendelesDokumentumokMapper;

    @BeforeEach
    void setUp() {
        megrendelesDokumentumokMapper = new MegrendelesDokumentumokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMegrendelesDokumentumokSample1();
        var actual = megrendelesDokumentumokMapper.toEntity(megrendelesDokumentumokMapper.toDto(expected));
        assertMegrendelesDokumentumokAllPropertiesEquals(expected, actual);
    }
}

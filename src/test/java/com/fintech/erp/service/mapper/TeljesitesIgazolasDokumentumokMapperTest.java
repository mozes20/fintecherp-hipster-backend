package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.TeljesitesIgazolasDokumentumokAsserts.*;
import static com.fintech.erp.domain.TeljesitesIgazolasDokumentumokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeljesitesIgazolasDokumentumokMapperTest {

    private TeljesitesIgazolasDokumentumokMapper teljesitesIgazolasDokumentumokMapper;

    @BeforeEach
    void setUp() {
        teljesitesIgazolasDokumentumokMapper = new TeljesitesIgazolasDokumentumokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTeljesitesIgazolasDokumentumokSample1();
        var actual = teljesitesIgazolasDokumentumokMapper.toEntity(teljesitesIgazolasDokumentumokMapper.toDto(expected));
        assertTeljesitesIgazolasDokumentumokAllPropertiesEquals(expected, actual);
    }
}

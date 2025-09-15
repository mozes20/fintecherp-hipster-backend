package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.PartnerCegMunkavallalokAsserts.*;
import static com.fintech.erp.domain.PartnerCegMunkavallalokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartnerCegMunkavallalokMapperTest {

    private PartnerCegMunkavallalokMapper partnerCegMunkavallalokMapper;

    @BeforeEach
    void setUp() {
        partnerCegMunkavallalokMapper = new PartnerCegMunkavallalokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartnerCegMunkavallalokSample1();
        var actual = partnerCegMunkavallalokMapper.toEntity(partnerCegMunkavallalokMapper.toDto(expected));
        assertPartnerCegMunkavallalokAllPropertiesEquals(expected, actual);
    }
}

package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.PartnerCegAdatokAsserts.*;
import static com.fintech.erp.domain.PartnerCegAdatokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartnerCegAdatokMapperTest {

    private PartnerCegAdatokMapper partnerCegAdatokMapper;

    @BeforeEach
    void setUp() {
        partnerCegAdatokMapper = new PartnerCegAdatokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartnerCegAdatokSample1();
        var actual = partnerCegAdatokMapper.toEntity(partnerCegAdatokMapper.toDto(expected));
        assertPartnerCegAdatokAllPropertiesEquals(expected, actual);
    }
}

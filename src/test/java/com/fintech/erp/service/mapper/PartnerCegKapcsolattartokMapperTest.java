package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.PartnerCegKapcsolattartokAsserts.*;
import static com.fintech.erp.domain.PartnerCegKapcsolattartokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartnerCegKapcsolattartokMapperTest {

    private PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper;

    @BeforeEach
    void setUp() {
        partnerCegKapcsolattartokMapper = new PartnerCegKapcsolattartokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartnerCegKapcsolattartokSample1();
        var actual = partnerCegKapcsolattartokMapper.toEntity(partnerCegKapcsolattartokMapper.toDto(expected));
        assertPartnerCegKapcsolattartokAllPropertiesEquals(expected, actual);
    }
}

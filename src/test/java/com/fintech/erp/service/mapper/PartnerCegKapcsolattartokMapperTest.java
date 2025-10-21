package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.PartnerCegKapcsolattartokAsserts.assertPartnerCegKapcsolattartokAllPropertiesEquals;
import static com.fintech.erp.domain.PartnerCegKapcsolattartokTestSamples.getPartnerCegKapcsolattartokSample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class PartnerCegKapcsolattartokMapperTest {

    private PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper;

    @BeforeEach
    void setUp() {
        partnerCegKapcsolattartokMapper = new PartnerCegKapcsolattartokMapperImpl();
        ReflectionTestUtils.setField(partnerCegKapcsolattartokMapper, "partnerCegAdatokMapper", new PartnerCegAdatokMapperImpl());
        ReflectionTestUtils.setField(partnerCegKapcsolattartokMapper, "maganszemelyekMapper", new MaganszemelyekMapperImpl());
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartnerCegKapcsolattartokSample1();
        var actual = partnerCegKapcsolattartokMapper.toEntity(partnerCegKapcsolattartokMapper.toDto(expected));
        assertPartnerCegKapcsolattartokAllPropertiesEquals(expected, actual);
    }
}

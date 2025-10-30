package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.BerekAsserts.assertBerekAllPropertiesEquals;
import static com.fintech.erp.domain.BerekTestSamples.getBerekSample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class BerekMapperTest {

    private BerekMapper berekMapper;

    @BeforeEach
    void setUp() {
        berekMapper = new BerekMapperImpl();
        var munkavallalokMapper = new MunkavallalokMapperImpl();
        ReflectionTestUtils.setField(munkavallalokMapper, "sajatCegAlapadatokMapper", new SajatCegAlapadatokMapperImpl());
        ReflectionTestUtils.setField(munkavallalokMapper, "maganszemelyekMapper", new MaganszemelyekMapperImpl());
        ReflectionTestUtils.setField(berekMapper, "munkavallalokMapper", munkavallalokMapper);
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBerekSample1();
        var actual = berekMapper.toEntity(berekMapper.toDto(expected));
        assertBerekAllPropertiesEquals(expected, actual);
    }
}

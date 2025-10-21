package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.MunkavallalokAsserts.assertMunkavallalokAllPropertiesEquals;
import static com.fintech.erp.domain.MunkavallalokTestSamples.getMunkavallalokSample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class MunkavallalokMapperTest {

    private MunkavallalokMapper munkavallalokMapper;

    @BeforeEach
    void setUp() {
        munkavallalokMapper = new MunkavallalokMapperImpl();
        ReflectionTestUtils.setField(munkavallalokMapper, "sajatCegAlapadatokMapper", new SajatCegAlapadatokMapperImpl());
        ReflectionTestUtils.setField(munkavallalokMapper, "maganszemelyekMapper", new MaganszemelyekMapperImpl());
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMunkavallalokSample1();
        var actual = munkavallalokMapper.toEntity(munkavallalokMapper.toDto(expected));
        assertMunkavallalokAllPropertiesEquals(expected, actual);
    }
}

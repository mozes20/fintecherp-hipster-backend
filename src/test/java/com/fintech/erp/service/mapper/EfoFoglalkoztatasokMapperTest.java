package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.EfoFoglalkoztatasokAsserts.*;
import static com.fintech.erp.domain.EfoFoglalkoztatasokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EfoFoglalkoztatasokMapperTest {

    private EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper;

    @BeforeEach
    void setUp() {
        efoFoglalkoztatasokMapper = new EfoFoglalkoztatasokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEfoFoglalkoztatasokSample1();
        var actual = efoFoglalkoztatasokMapper.toEntity(efoFoglalkoztatasokMapper.toDto(expected));
        assertEfoFoglalkoztatasokAllPropertiesEquals(expected, actual);
    }
}

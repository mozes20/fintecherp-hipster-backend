package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.BankszamlaszamokAsserts.*;
import static com.fintech.erp.domain.BankszamlaszamokTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankszamlaszamokMapperTest {

    private BankszamlaszamokMapper bankszamlaszamokMapper;

    @BeforeEach
    void setUp() {
        bankszamlaszamokMapper = new BankszamlaszamokMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBankszamlaszamokSample1();
        var actual = bankszamlaszamokMapper.toEntity(bankszamlaszamokMapper.toDto(expected));
        assertBankszamlaszamokAllPropertiesEquals(expected, actual);
    }
}

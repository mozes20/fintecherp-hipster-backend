package com.fintech.erp.service.mapper;

import static com.fintech.erp.domain.BankszamlaszamokAsserts.assertBankszamlaszamokAllPropertiesEquals;
import static com.fintech.erp.domain.BankszamlaszamokTestSamples.getBankszamlaszamokSample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class BankszamlaszamokMapperTest {

    private BankszamlaszamokMapper bankszamlaszamokMapper;

    @BeforeEach
    void setUp() {
        bankszamlaszamokMapper = new BankszamlaszamokMapperImpl();
        ReflectionTestUtils.setField(bankszamlaszamokMapper, "cegAlapadatokMapper", new CegAlapadatokMapperImpl());
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBankszamlaszamokSample1();
        var actual = bankszamlaszamokMapper.toEntity(bankszamlaszamokMapper.toDto(expected));
        assertBankszamlaszamokAllPropertiesEquals(expected, actual);
    }
}

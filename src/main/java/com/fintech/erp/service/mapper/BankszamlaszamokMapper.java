package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Bankszamlaszamok;
import com.fintech.erp.service.dto.BankszamlaszamokDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Bankszamlaszamok} and its DTO {@link BankszamlaszamokDTO}.
 */
@Mapper(componentModel = "spring", uses = { CegAlapadatokMapper.class })
public interface BankszamlaszamokMapper extends EntityMapper<BankszamlaszamokDTO, Bankszamlaszamok> {
    @Mapping(target = "ceg", source = "ceg")
    BankszamlaszamokDTO toDto(Bankszamlaszamok s);
}

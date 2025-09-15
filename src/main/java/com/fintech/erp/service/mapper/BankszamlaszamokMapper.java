package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Bankszamlaszamok;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.service.dto.BankszamlaszamokDTO;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bankszamlaszamok} and its DTO {@link BankszamlaszamokDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankszamlaszamokMapper extends EntityMapper<BankszamlaszamokDTO, Bankszamlaszamok> {
    @Mapping(target = "ceg", source = "ceg", qualifiedByName = "cegAlapadatokId")
    BankszamlaszamokDTO toDto(Bankszamlaszamok s);

    @Named("cegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CegAlapadatokDTO toDtoCegAlapadatokId(CegAlapadatok cegAlapadatok);
}

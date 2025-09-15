package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SajatCegAlapadatok} and its DTO {@link SajatCegAlapadatokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SajatCegAlapadatokMapper extends EntityMapper<SajatCegAlapadatokDTO, SajatCegAlapadatok> {
    @Mapping(target = "ceg", source = "ceg", qualifiedByName = "cegAlapadatokId")
    SajatCegAlapadatokDTO toDto(SajatCegAlapadatok s);

    @Named("cegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CegAlapadatokDTO toDtoCegAlapadatokId(CegAlapadatok cegAlapadatok);
}

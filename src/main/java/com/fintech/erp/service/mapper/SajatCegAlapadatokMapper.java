package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link SajatCegAlapadatok} and its DTO {@link SajatCegAlapadatokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SajatCegAlapadatokMapper extends EntityMapper<SajatCegAlapadatokDTO, SajatCegAlapadatok> {
    @Mapping(target = "ceg", source = "ceg")
    @Mapping(target = "cegId", source = "ceg.id")
    SajatCegAlapadatokDTO toDto(SajatCegAlapadatok s);

    @Mapping(
        target = "ceg",
        expression = "java(dto.getCegId() != null ? new com.fintech.erp.domain.CegAlapadatok().id(dto.getCegId()) : null)"
    )
    SajatCegAlapadatok toEntity(SajatCegAlapadatokDTO dto);

    @Named("cegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CegAlapadatokDTO toDtoCegAlapadatokId(CegAlapadatok cegAlapadatok);
}

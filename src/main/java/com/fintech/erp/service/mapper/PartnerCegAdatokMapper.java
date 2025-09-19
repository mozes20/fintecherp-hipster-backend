package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link PartnerCegAdatok} and its DTO {@link PartnerCegAdatokDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartnerCegAdatokMapper extends EntityMapper<PartnerCegAdatokDTO, PartnerCegAdatok> {
    @Mapping(target = "ceg", source = "ceg")
    @Mapping(target = "cegId", source = "ceg.id")
    PartnerCegAdatokDTO toDto(PartnerCegAdatok s);

    @Mapping(
        target = "ceg",
        expression = "java(dto.getCegId() != null ? new com.fintech.erp.domain.CegAlapadatok().id(dto.getCegId()) : null)"
    )
    PartnerCegAdatok toEntity(PartnerCegAdatokDTO dto);

    @Named("cegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CegAlapadatokDTO toDtoCegAlapadatokId(CegAlapadatok cegAlapadatok);
}

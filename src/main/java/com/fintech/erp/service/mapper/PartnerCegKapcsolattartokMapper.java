package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PartnerCegKapcsolattartok} and its DTO {@link PartnerCegKapcsolattartokDTO}.
 */
@Mapper(componentModel = "spring", uses = { PartnerCegAdatokMapper.class, MaganszemelyekMapper.class })
public interface PartnerCegKapcsolattartokMapper extends EntityMapper<PartnerCegKapcsolattartokDTO, PartnerCegKapcsolattartok> {
    @Mapping(target = "partnerCeg", source = "partnerCeg")
    @Mapping(target = "maganszemely", source = "maganszemely")
    @Mapping(target = "partnerCegId", source = "partnerCeg.id")
    @Mapping(target = "maganSzemelyId", source = "maganszemely.id")
    @Mapping(target = "kapcsolattartoTitulus", source = "kapcsolattartoTitulus")
    PartnerCegKapcsolattartokDTO toDto(PartnerCegKapcsolattartok s);

    @Mapping(
        target = "partnerCeg",
        expression = "java(dto.getPartnerCeg() != null && dto.getPartnerCeg().getId() != null ? new com.fintech.erp.domain.PartnerCegAdatok().id(dto.getPartnerCeg().getId()) : (dto.getPartnerCegId() != null ? new com.fintech.erp.domain.PartnerCegAdatok().id(dto.getPartnerCegId()) : null))"
    )
    @Mapping(
        target = "maganszemely",
        expression = "java(dto.getMaganszemely() != null && dto.getMaganszemely().getId() != null ? new com.fintech.erp.domain.Maganszemelyek().id(dto.getMaganszemely().getId()) : (dto.getMaganSzemelyId() != null ? new com.fintech.erp.domain.Maganszemelyek().id(dto.getMaganSzemelyId()) : null))"
    )
    @Mapping(target = "kapcsolattartoTitulus", source = "kapcsolattartoTitulus")
    PartnerCegKapcsolattartok toEntity(PartnerCegKapcsolattartokDTO dto);
}

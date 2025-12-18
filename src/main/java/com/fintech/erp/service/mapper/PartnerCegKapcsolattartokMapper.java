package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

    @Mapping(target = "partnerCeg", source = "partnerCeg")
    @Mapping(target = "maganszemely", source = "maganszemely")
    @Mapping(target = "kapcsolattartoTitulus", source = "kapcsolattartoTitulus")
    PartnerCegKapcsolattartok toEntity(PartnerCegKapcsolattartokDTO dto);

    @AfterMapping
    default void mapFallbackAssociations(PartnerCegKapcsolattartokDTO dto, @MappingTarget PartnerCegKapcsolattartok entity) {
        if (entity.getPartnerCeg() == null && dto.getPartnerCegId() != null) {
            PartnerCegAdatok partnerCeg = new PartnerCegAdatok();
            partnerCeg.setId(dto.getPartnerCegId());
            entity.setPartnerCeg(partnerCeg);
        }
        if (entity.getMaganszemely() == null && dto.getMaganSzemelyId() != null) {
            Maganszemelyek maganszemely = new Maganszemelyek();
            maganszemely.setId(dto.getMaganSzemelyId());
            entity.setMaganszemely(maganszemely);
        }
    }
}

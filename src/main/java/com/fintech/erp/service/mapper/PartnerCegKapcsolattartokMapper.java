package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartnerCegKapcsolattartok} and its DTO {@link PartnerCegKapcsolattartokDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartnerCegKapcsolattartokMapper extends EntityMapper<PartnerCegKapcsolattartokDTO, PartnerCegKapcsolattartok> {
    @Mapping(target = "partnerCeg", source = "partnerCeg", qualifiedByName = "partnerCegAdatokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    PartnerCegKapcsolattartokDTO toDto(PartnerCegKapcsolattartok s);

    @Named("partnerCegAdatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PartnerCegAdatokDTO toDtoPartnerCegAdatokId(PartnerCegAdatok partnerCegAdatok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

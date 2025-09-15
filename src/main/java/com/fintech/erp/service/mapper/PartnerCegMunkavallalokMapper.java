package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.domain.PartnerCegMunkavallalok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import com.fintech.erp.service.dto.PartnerCegMunkavallalokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartnerCegMunkavallalok} and its DTO {@link PartnerCegMunkavallalokDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartnerCegMunkavallalokMapper extends EntityMapper<PartnerCegMunkavallalokDTO, PartnerCegMunkavallalok> {
    @Mapping(target = "partnerCeg", source = "partnerCeg", qualifiedByName = "partnerCegAdatokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    PartnerCegMunkavallalokDTO toDto(PartnerCegMunkavallalok s);

    @Named("partnerCegAdatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PartnerCegAdatokDTO toDtoPartnerCegAdatokId(PartnerCegAdatok partnerCegAdatok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartnerCegAdatok} and its DTO {@link PartnerCegAdatokDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartnerCegAdatokMapper extends EntityMapper<PartnerCegAdatokDTO, PartnerCegAdatok> {
    @Mapping(target = "ceg", source = "ceg", qualifiedByName = "cegAlapadatokId")
    PartnerCegAdatokDTO toDto(PartnerCegAdatok s);

    @Named("cegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CegAlapadatokDTO toDtoCegAlapadatokId(CegAlapadatok cegAlapadatok);
}

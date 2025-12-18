package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.domain.PartnerCegMunkavallalok;
import com.fintech.erp.service.dto.PartnerCegMunkavallalokDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PartnerCegMunkavallalok} and its DTO {@link PartnerCegMunkavallalokDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartnerCegMunkavallalokMapper extends EntityMapper<PartnerCegMunkavallalokDTO, PartnerCegMunkavallalok> {
    @Override
    @Mapping(target = "partnerCegId", source = "partnerCeg.id")
    @Mapping(target = "maganszemelyId", source = "maganszemely.id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusz", source = "statusz")
    PartnerCegMunkavallalokDTO toDto(PartnerCegMunkavallalok entity);

    @Override
    @Mapping(target = "partnerCeg", expression = "java(dto.getPartnerCegId() != null ? fromPartnerCegId(dto.getPartnerCegId()) : null)")
    @Mapping(
        target = "maganszemely",
        expression = "java(dto.getMaganszemelyId() != null ? fromMaganszemelyId(dto.getMaganszemelyId()) : null)"
    )
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusz", source = "statusz")
    PartnerCegMunkavallalok toEntity(PartnerCegMunkavallalokDTO dto);

    default PartnerCegAdatok fromPartnerCegId(Long id) {
        if (id == null) return null;
        PartnerCegAdatok obj = new PartnerCegAdatok();
        obj.setId(id);
        return obj;
    }

    default Maganszemelyek fromMaganszemelyId(Long id) {
        if (id == null) return null;
        Maganszemelyek obj = new Maganszemelyek();
        obj.setId(id);
        return obj;
    }
}

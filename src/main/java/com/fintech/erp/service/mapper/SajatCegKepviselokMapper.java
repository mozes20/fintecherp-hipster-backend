package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegKepviselok;
import com.fintech.erp.service.dto.SajatCegKepviselokDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link SajatCegKepviselok} and its DTO {@link SajatCegKepviselokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SajatCegKepviselokMapper extends EntityMapper<SajatCegKepviselokDTO, SajatCegKepviselok> {
    @Override
    @Mapping(target = "sajatCegId", source = "sajatCeg.id")
    @Mapping(target = "maganSzemelyId", source = "maganszemely.id")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusz", source = "statusz")
    SajatCegKepviselokDTO toDto(SajatCegKepviselok entity);

    @Override
    @Mapping(target = "sajatCeg", expression = "java(dto.getSajatCegId() != null ? fromSajatCegId(dto.getSajatCegId()) : null)")
    @Mapping(
        target = "maganszemely",
        expression = "java(dto.getMaganSzemelyId() != null ? fromMaganSzemelyId(dto.getMaganSzemelyId()) : null)"
    )
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusz", source = "statusz")
    SajatCegKepviselok toEntity(SajatCegKepviselokDTO dto);

    default SajatCegAlapadatok fromSajatCegId(Long id) {
        if (id == null) return null;
        SajatCegAlapadatok obj = new SajatCegAlapadatok();
        obj.setId(id);
        return obj;
    }

    default Maganszemelyek fromMaganSzemelyId(Long id) {
        if (id == null) return null;
        Maganszemelyek obj = new Maganszemelyek();
        obj.setId(id);
        return obj;
    }
}

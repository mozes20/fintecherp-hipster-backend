package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link SzerzodesesJogviszonyok} and its DTO {@link SzerzodesesJogviszonyokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SzerzodesesJogviszonyokMapper extends EntityMapper<SzerzodesesJogviszonyokDTO, SzerzodesesJogviszonyok> {
    @Override
    @Mapping(target = "megrendeloCegId", source = "megrendeloCeg.id")
    @Mapping(target = "vallalkozoCegId", source = "vallalkozoCeg.id")
    @Mapping(target = "megrendeloCeg", source = "megrendeloCeg")
    @Mapping(target = "vallalkozoCeg", source = "vallalkozoCeg")
    SzerzodesesJogviszonyokDTO toDto(SzerzodesesJogviszonyok entity);

    @Override
    @Mapping(
        target = "megrendeloCeg",
        expression = "java(dto.getMegrendeloCegId() != null ? fromCegAlapadatokId(dto.getMegrendeloCegId()) : null)"
    )
    @Mapping(
        target = "vallalkozoCeg",
        expression = "java(dto.getVallalkozoCegId() != null ? fromCegAlapadatokId(dto.getVallalkozoCegId()) : null)"
    )
    SzerzodesesJogviszonyok toEntity(SzerzodesesJogviszonyokDTO dto);

    default CegAlapadatok fromCegAlapadatokId(Long id) {
        if (id == null) return null;
        CegAlapadatok obj = new CegAlapadatok();
        obj.setId(id);
        return obj;
    }
}

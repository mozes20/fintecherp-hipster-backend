package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Munkavallalok} and its DTO {@link MunkavallalokDTO}.
 */
@Mapper(componentModel = "spring", uses = { SajatCegAlapadatokMapper.class, MaganszemelyekMapper.class })
public interface MunkavallalokMapper extends EntityMapper<MunkavallalokDTO, Munkavallalok> {
    @Mapping(target = "sajatCeg", source = "sajatCeg")
    @Mapping(target = "maganszemely", source = "maganszemely")
    MunkavallalokDTO toDto(Munkavallalok s);
}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EfoFoglalkoztatasok} and its DTO {@link EfoFoglalkoztatasokDTO}.
 */
@Mapper(componentModel = "spring", uses = { MunkavallalokMapper.class, MunkakorokMapper.class })
public interface EfoFoglalkoztatasokMapper extends EntityMapper<EfoFoglalkoztatasokDTO, EfoFoglalkoztatasok> {
    @Mapping(target = "munkavallalo", source = "munkavallalo")
    @Mapping(target = "munkakor", source = "munkakor")
    EfoFoglalkoztatasokDTO toDto(EfoFoglalkoztatasok s);
}

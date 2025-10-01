package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Berek;
import com.fintech.erp.service.dto.BerekDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Berek} and its DTO {@link BerekDTO}.
 */
@Mapper(componentModel = "spring", uses = { MunkavallalokMapper.class })
public interface BerekMapper extends EntityMapper<BerekDTO, Berek> {
    @Mapping(target = "munkavallalo", source = "munkavallalo")
    @Mapping(target = "maganszemelyNeve", source = "munkavallalo.maganszemely.maganszemelyNeve")
    BerekDTO toDto(Berek s);
}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Berek;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.service.dto.BerekDTO;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Berek} and its DTO {@link BerekDTO}.
 */
@Mapper(componentModel = "spring")
public interface BerekMapper extends EntityMapper<BerekDTO, Berek> {
    @Mapping(target = "munkavallalo", source = "munkavallalo", qualifiedByName = "munkavallalokId")
    BerekDTO toDto(Berek s);

    @Named("munkavallalokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MunkavallalokDTO toDtoMunkavallalokId(Munkavallalok munkavallalok);
}

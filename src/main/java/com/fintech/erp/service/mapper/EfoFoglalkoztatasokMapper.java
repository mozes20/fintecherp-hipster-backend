package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import com.fintech.erp.service.dto.MunkakorokDTO;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EfoFoglalkoztatasok} and its DTO {@link EfoFoglalkoztatasokDTO}.
 */
@Mapper(componentModel = "spring", uses = { MunkavallalokMapper.class, MunkakorokMapper.class })
public interface EfoFoglalkoztatasokMapper extends EntityMapper<EfoFoglalkoztatasokDTO, EfoFoglalkoztatasok> {
    @Mapping(target = "munkavallalo", source = "munkavallalo", qualifiedByName = "munkavallalokId")
    @Mapping(target = "munkakor", source = "munkakor", qualifiedByName = "munkakorokId")
    EfoFoglalkoztatasokDTO toDto(EfoFoglalkoztatasok s);

    @Named("munkavallalokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MunkavallalokDTO toDtoMunkavallalokId(Munkavallalok munkavallalok);

    @Named("munkakorokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MunkakorokDTO toDtoMunkakorokId(Munkakorok munkakorok);
}

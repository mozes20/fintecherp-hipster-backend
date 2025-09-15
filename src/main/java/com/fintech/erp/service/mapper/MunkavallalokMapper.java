package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Munkavallalok} and its DTO {@link MunkavallalokDTO}.
 */
@Mapper(componentModel = "spring")
public interface MunkavallalokMapper extends EntityMapper<MunkavallalokDTO, Munkavallalok> {
    @Mapping(target = "sajatCeg", source = "sajatCeg", qualifiedByName = "sajatCegAlapadatokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    MunkavallalokDTO toDto(Munkavallalok s);

    @Named("sajatCegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SajatCegAlapadatokDTO toDtoSajatCegAlapadatokId(SajatCegAlapadatok sajatCegAlapadatok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

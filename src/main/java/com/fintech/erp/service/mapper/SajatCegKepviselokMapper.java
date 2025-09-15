package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegKepviselok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import com.fintech.erp.service.dto.SajatCegKepviselokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SajatCegKepviselok} and its DTO {@link SajatCegKepviselokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SajatCegKepviselokMapper extends EntityMapper<SajatCegKepviselokDTO, SajatCegKepviselok> {
    @Mapping(target = "sajatCeg", source = "sajatCeg", qualifiedByName = "sajatCegAlapadatokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    SajatCegKepviselokDTO toDto(SajatCegKepviselok s);

    @Named("sajatCegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SajatCegAlapadatokDTO toDtoSajatCegAlapadatokId(SajatCegAlapadatok sajatCegAlapadatok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

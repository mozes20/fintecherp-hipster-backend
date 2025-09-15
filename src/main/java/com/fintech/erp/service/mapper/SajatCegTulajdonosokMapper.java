package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegTulajdonosok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import com.fintech.erp.service.dto.SajatCegTulajdonosokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SajatCegTulajdonosok} and its DTO {@link SajatCegTulajdonosokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SajatCegTulajdonosokMapper extends EntityMapper<SajatCegTulajdonosokDTO, SajatCegTulajdonosok> {
    @Mapping(target = "sajatCeg", source = "sajatCeg", qualifiedByName = "sajatCegAlapadatokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    SajatCegTulajdonosokDTO toDto(SajatCegTulajdonosok s);

    @Named("sajatCegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SajatCegAlapadatokDTO toDtoSajatCegAlapadatokId(SajatCegAlapadatok sajatCegAlapadatok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OsztalekfizetesiKozgyulesek} and its DTO {@link OsztalekfizetesiKozgyulesekDTO}.
 */
@Mapper(componentModel = "spring")
public interface OsztalekfizetesiKozgyulesekMapper extends EntityMapper<OsztalekfizetesiKozgyulesekDTO, OsztalekfizetesiKozgyulesek> {
    @Mapping(target = "sajatCeg", source = "sajatCeg", qualifiedByName = "sajatCegAlapadatokId")
    OsztalekfizetesiKozgyulesekDTO toDto(OsztalekfizetesiKozgyulesek s);

    @Named("sajatCegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SajatCegAlapadatokDTO toDtoSajatCegAlapadatokId(SajatCegAlapadatok sajatCegAlapadatok);
}

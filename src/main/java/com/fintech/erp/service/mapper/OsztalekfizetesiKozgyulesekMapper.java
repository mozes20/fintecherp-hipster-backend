package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OsztalekfizetesiKozgyulesek} and its DTO {@link OsztalekfizetesiKozgyulesekDTO}.
 */
@Mapper(componentModel = "spring")
public interface OsztalekfizetesiKozgyulesekMapper extends EntityMapper<OsztalekfizetesiKozgyulesekDTO, OsztalekfizetesiKozgyulesek> {
    @Mapping(target = "sajatCeg", source = "sajatCeg", qualifiedByName = "sajatCegAlapadatokWithCeg")
    OsztalekfizetesiKozgyulesekDTO toDto(OsztalekfizetesiKozgyulesek s);

    @Named("sajatCegAlapadatokWithCeg")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ceg", source = "ceg", qualifiedByName = "cegAlapadatokNev")
    SajatCegAlapadatokDTO toDtoSajatCegAlapadatokWithCeg(SajatCegAlapadatok sajatCegAlapadatok);

    @Named("cegAlapadatokNev")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cegNev", source = "cegNev")
    @Mapping(target = "cegRovidAzonosito", source = "cegRovidAzonosito")
    CegAlapadatokDTO toDtoCegAlapadatokNev(CegAlapadatok cegAlapadatok);
}

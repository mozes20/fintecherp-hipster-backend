package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OsztalekfizetesiKozgyulesekDokumentum}
 * and its DTO {@link OsztalekfizetesiKozgyulesekDokumentumDTO}.
 */
@Mapper(componentModel = "spring")
public interface OsztalekfizetesiKozgyulesekDokumentumMapper
    extends EntityMapper<OsztalekfizetesiKozgyulesekDokumentumDTO, OsztalekfizetesiKozgyulesekDokumentum> {
    @Mapping(target = "kozgyules", source = "kozgyules", qualifiedByName = "kozgyulesId")
    OsztalekfizetesiKozgyulesekDokumentumDTO toDto(OsztalekfizetesiKozgyulesekDokumentum s);

    @Named("kozgyulesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OsztalekfizetesiKozgyulesekDTO toDtoKozgyulesId(OsztalekfizetesiKozgyulesek kozgyules);
}

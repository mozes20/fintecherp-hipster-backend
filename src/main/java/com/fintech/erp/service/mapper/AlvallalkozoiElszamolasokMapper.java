package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link AlvallalkozoiElszamolasok} and its DTO {@link AlvallalkozoiElszamolasokDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlvallalkozoiElszamolasokMapper extends EntityMapper<AlvallalkozoiElszamolasokDTO, AlvallalkozoiElszamolasok> {

    @Mapping(target = "megrendeles", source = "megrendeles", qualifiedByName = "megrendelesekId")
    AlvallalkozoiElszamolasokDTO toDto(AlvallalkozoiElszamolasok s);

    @Mapping(target = "megrendeles", ignore = true)
    AlvallalkozoiElszamolasok toEntity(AlvallalkozoiElszamolasokDTO dto);

    @Named("megrendelesekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "megrendelesSzam", source = "megrendelesSzam")
    @Mapping(target = "feladatRovidLeirasa", source = "feladatRovidLeirasa")
    @Mapping(target = "dijazasTipusa", source = "dijazasTipusa")
    @Mapping(target = "dijOsszege", source = "dijOsszege")
    @Mapping(target = "devizanem", source = "devizanem")
    MegrendelesekDTO toDtoMegrendelesekId(Megrendelesek megrendelesek);

    @AfterMapping
    default void applyMegrendelesReference(AlvallalkozoiElszamolasokDTO dto, @MappingTarget AlvallalkozoiElszamolasok entity) {
        if (dto == null) return;
        MegrendelesekDTO megrendelesDto = dto.getMegrendeles();
        if (megrendelesDto != null && megrendelesDto.getId() != null) {
            Megrendelesek megrendeles = new Megrendelesek();
            megrendeles.setId(megrendelesDto.getId());
            entity.setMegrendeles(megrendeles);
        }
    }
}

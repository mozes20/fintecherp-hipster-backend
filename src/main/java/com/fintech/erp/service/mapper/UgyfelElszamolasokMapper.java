package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link UgyfelElszamolasok} and its DTO {@link UgyfelElszamolasokDTO}.
 */
@Mapper(componentModel = "spring")
public interface UgyfelElszamolasokMapper extends EntityMapper<UgyfelElszamolasokDTO, UgyfelElszamolasok> {
    @Mapping(target = "megrendeles", source = "megrendeles", qualifiedByName = "megrendelesekId")
    UgyfelElszamolasokDTO toDto(UgyfelElszamolasok s);

    @Mapping(target = "megrendeles", ignore = true)
    UgyfelElszamolasok toEntity(UgyfelElszamolasokDTO dto);

    @Named("megrendelesekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MegrendelesekDTO toDtoMegrendelesekId(Megrendelesek megrendelesek);

    @AfterMapping
    default void applyMegrendelesReference(UgyfelElszamolasokDTO dto, @MappingTarget UgyfelElszamolasok entity) {
        if (dto == null) {
            return;
        }
        MegrendelesekDTO megrendelesDto = dto.getMegrendeles();
        if (megrendelesDto != null && megrendelesDto.getId() != null) {
            Megrendelesek megrendeles = new Megrendelesek();
            megrendeles.setId(megrendelesDto.getId());
            entity.setMegrendeles(megrendeles);
        }
    }
}

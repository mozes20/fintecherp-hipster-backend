package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UgyfelElszamolasok} and its DTO {@link UgyfelElszamolasokDTO}.
 */
@Mapper(componentModel = "spring")
public interface UgyfelElszamolasokMapper extends EntityMapper<UgyfelElszamolasokDTO, UgyfelElszamolasok> {
    @Mapping(target = "megrendeles", source = "megrendeles", qualifiedByName = "megrendelesekId")
    UgyfelElszamolasokDTO toDto(UgyfelElszamolasok s);

    @Named("megrendelesekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MegrendelesekDTO toDtoMegrendelesekId(Megrendelesek megrendelesek);
}

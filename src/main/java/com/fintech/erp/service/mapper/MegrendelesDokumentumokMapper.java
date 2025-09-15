package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.MegrendelesDokumentumok;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MegrendelesDokumentumok} and its DTO {@link MegrendelesDokumentumokDTO}.
 */
@Mapper(componentModel = "spring")
public interface MegrendelesDokumentumokMapper extends EntityMapper<MegrendelesDokumentumokDTO, MegrendelesDokumentumok> {
    @Mapping(target = "megrendeles", source = "megrendeles", qualifiedByName = "megrendelesekId")
    MegrendelesDokumentumokDTO toDto(MegrendelesDokumentumok s);

    @Named("megrendelesekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MegrendelesekDTO toDtoMegrendelesekId(Megrendelesek megrendelesek);
}

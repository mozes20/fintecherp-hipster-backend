package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.SzerzodesDokumentumTipus;
import com.fintech.erp.service.dto.SzerzodesDokumentumTipusDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SzerzodesDokumentumTipus} and its DTO {@link SzerzodesDokumentumTipusDTO}.
 */
@Mapper(componentModel = "spring")
public interface SzerzodesDokumentumTipusMapper extends EntityMapper<SzerzodesDokumentumTipusDTO, SzerzodesDokumentumTipus> {
    @Override
    @org.mapstruct.Mapping(target = "templatek", ignore = true)
    @org.mapstruct.Mapping(target = "dokumentumok", ignore = true)
    SzerzodesDokumentumTipus toEntity(SzerzodesDokumentumTipusDTO dto);

    @Override
    @org.mapstruct.BeanMapping(ignoreByDefault = true)
    @org.mapstruct.Mapping(target = "id", source = "id")
    @org.mapstruct.Mapping(target = "nev", source = "nev")
    @org.mapstruct.Mapping(target = "leiras", source = "leiras")
    void partialUpdate(@org.mapstruct.MappingTarget SzerzodesDokumentumTipus entity, SzerzodesDokumentumTipusDTO dto);
}

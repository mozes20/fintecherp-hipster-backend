package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumTemplateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity {@link SzerzodesesJogviszonyDokumentumTemplate} and its DTO {@link SzerzodesesJogviszonyDokumentumTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface SzerzodesesJogviszonyDokumentumTemplateMapper
    extends EntityMapper<SzerzodesesJogviszonyDokumentumTemplateDTO, SzerzodesesJogviszonyDokumentumTemplate> {
    @Override
    SzerzodesesJogviszonyDokumentumTemplateDTO toDto(SzerzodesesJogviszonyDokumentumTemplate entity);

    @Override
    SzerzodesesJogviszonyDokumentumTemplate toEntity(SzerzodesesJogviszonyDokumentumTemplateDTO dto);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "templateNev", source = "templateNev")
    @Mapping(target = "templateLeiras", source = "templateLeiras")
    @Mapping(target = "fajlUtvonal", source = "fajlUtvonal")
    @Mapping(target = "utolsoModositas", source = "utolsoModositas")
    @Mapping(target = "dokumentumTipus", source = "dokumentumTipus")
    void partialUpdate(@MappingTarget SzerzodesesJogviszonyDokumentumTemplate entity, SzerzodesesJogviszonyDokumentumTemplateDTO dto);
}

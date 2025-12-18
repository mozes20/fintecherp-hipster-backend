package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.EfoDokumentumTemplate;
import com.fintech.erp.service.dto.EfoDokumentumTemplateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for {@link com.fintech.erp.domain.EfoDokumentumTemplate}.
 */
@Mapper(componentModel = "spring")
public interface EfoDokumentumTemplateMapper extends EntityMapper<EfoDokumentumTemplateDTO, EfoDokumentumTemplate> {
    @Override
    EfoDokumentumTemplateDTO toDto(EfoDokumentumTemplate entity);

    @Override
    EfoDokumentumTemplate toEntity(EfoDokumentumTemplateDTO dto);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "templateNev", source = "templateNev")
    @Mapping(target = "templateLeiras", source = "templateLeiras")
    @Mapping(target = "fajlUtvonal", source = "fajlUtvonal")
    @Mapping(target = "utolsoModositas", source = "utolsoModositas")
    @Mapping(target = "dokumentumTipus", source = "dokumentumTipus")
    void partialUpdate(@MappingTarget EfoDokumentumTemplate entity, EfoDokumentumTemplateDTO dto);
}

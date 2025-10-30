package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.MegrendelesDokumentumTemplate;
import com.fintech.erp.service.dto.MegrendelesDokumentumTemplateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link MegrendelesDokumentumTemplate} and its DTO {@link MegrendelesDokumentumTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface MegrendelesDokumentumTemplateMapper
    extends EntityMapper<MegrendelesDokumentumTemplateDTO, MegrendelesDokumentumTemplate> {}

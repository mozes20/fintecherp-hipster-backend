package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentumTemplate;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDokumentumTemplateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OsztalekfizetesiKozgyulesekDokumentumTemplate}
 * and its DTO {@link OsztalekfizetesiKozgyulesekDokumentumTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface OsztalekfizetesiKozgyulesekDokumentumTemplateMapper
    extends EntityMapper<OsztalekfizetesiKozgyulesekDokumentumTemplateDTO, OsztalekfizetesiKozgyulesekDokumentumTemplate> {}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.WorkingDayTemplate;
import com.fintech.erp.service.dto.WorkingDayTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingDayTemplate} and its DTO
 * {@link WorkingDayTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkingDayTemplateMapper extends EntityMapper<WorkingDayTemplateDTO, WorkingDayTemplate> {
}

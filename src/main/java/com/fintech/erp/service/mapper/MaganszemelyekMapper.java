package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Maganszemelyek} and its DTO {@link MaganszemelyekDTO}.
 */
@Mapper(componentModel = "spring")
public interface MaganszemelyekMapper extends EntityMapper<MaganszemelyekDTO, Maganszemelyek> {}

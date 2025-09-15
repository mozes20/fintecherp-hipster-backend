package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CegAlapadatok} and its DTO {@link CegAlapadatokDTO}.
 */
@Mapper(componentModel = "spring")
public interface CegAlapadatokMapper extends EntityMapper<CegAlapadatokDTO, CegAlapadatok> {}

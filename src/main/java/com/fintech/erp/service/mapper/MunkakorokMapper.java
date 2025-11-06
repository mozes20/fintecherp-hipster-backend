package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.service.dto.MunkakorokDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Munkakorok} and its DTO {@link MunkakorokDTO}.
 */
@Mapper(componentModel = "spring")
public interface MunkakorokMapper extends EntityMapper<MunkakorokDTO, Munkakorok> {}

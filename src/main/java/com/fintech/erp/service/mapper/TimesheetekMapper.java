package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.Timesheetek;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import com.fintech.erp.service.dto.TimesheetekDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Timesheetek} and its DTO {@link TimesheetekDTO}.
 */
@Mapper(componentModel = "spring")
public interface TimesheetekMapper extends EntityMapper<TimesheetekDTO, Timesheetek> {
    @Mapping(target = "munkavallalo", source = "munkavallalo", qualifiedByName = "munkavallalokId")
    TimesheetekDTO toDto(Timesheetek s);

    @Named("munkavallalokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MunkavallalokDTO toDtoMunkavallalokId(Munkavallalok munkavallalok);
}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SzerzodesesJogviszonyok} and its DTO {@link SzerzodesesJogviszonyokDTO}.
 */
@Mapper(componentModel = "spring")
public interface SzerzodesesJogviszonyokMapper extends EntityMapper<SzerzodesesJogviszonyokDTO, SzerzodesesJogviszonyok> {
    @Mapping(target = "megrendeloCeg", source = "megrendeloCeg", qualifiedByName = "cegAlapadatokId")
    @Mapping(target = "vallalkozoCeg", source = "vallalkozoCeg", qualifiedByName = "cegAlapadatokId")
    SzerzodesesJogviszonyokDTO toDto(SzerzodesesJogviszonyok s);

    @Named("cegAlapadatokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CegAlapadatokDTO toDtoCegAlapadatokId(CegAlapadatok cegAlapadatok);
}

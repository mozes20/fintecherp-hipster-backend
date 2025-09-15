package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Megrendelesek} and its DTO {@link MegrendelesekDTO}.
 */
@Mapper(componentModel = "spring")
public interface MegrendelesekMapper extends EntityMapper<MegrendelesekDTO, Megrendelesek> {
    @Mapping(target = "szerzodesesJogviszony", source = "szerzodesesJogviszony", qualifiedByName = "szerzodesesJogviszonyokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    MegrendelesekDTO toDto(Megrendelesek s);

    @Named("szerzodesesJogviszonyokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SzerzodesesJogviszonyokDTO toDtoSzerzodesesJogviszonyokId(SzerzodesesJogviszonyok szerzodesesJogviszonyok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper for the entity {@link Megrendelesek} and its DTO {@link MegrendelesekDTO}.
 */
@Mapper(componentModel = "spring", uses = { MunkakorokMapper.class })
public interface MegrendelesekMapper extends EntityMapper<MegrendelesekDTO, Megrendelesek> {
    @Mapping(target = "szerzodesesJogviszony", source = "szerzodesesJogviszony", qualifiedByName = "szerzodesesJogviszonyokId")
    @Mapping(target = "maganszemely", source = "maganszemely", qualifiedByName = "maganszemelyekId")
    @Mapping(target = "munkakor", source = "munkakor")
    MegrendelesekDTO toDto(Megrendelesek s);

    @Override
    @Mapping(target = "munkakor", ignore = true)
    Megrendelesek toEntity(MegrendelesekDTO dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "munkakor", ignore = true)
    void partialUpdate(@MappingTarget Megrendelesek entity, MegrendelesekDTO dto);

    @Named("szerzodesesJogviszonyokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SzerzodesesJogviszonyokDTO toDtoSzerzodesesJogviszonyokId(SzerzodesesJogviszonyok szerzodesesJogviszonyok);

    @Named("maganszemelyekId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MaganszemelyekDTO toDtoMaganszemelyekId(Maganszemelyek maganszemelyek);
}

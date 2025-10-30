package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumDTO;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link SzerzodesesJogviszonyDokumentum} and its DTO {@link SzerzodesesJogviszonyDokumentumDTO}.
 */
@Mapper(componentModel = "spring")
public interface SzerzodesesJogviszonyDokumentumMapper
    extends EntityMapper<SzerzodesesJogviszonyDokumentumDTO, SzerzodesesJogviszonyDokumentum> {
    @Override
    @Mapping(target = "szerzodesesJogviszony", source = "szerzodesesJogviszony", qualifiedByName = "szerzodesesJogviszonyokForDocument")
    @Mapping(target = "szerzodesesJogviszonyId", source = "szerzodesesJogviszony.id")
    SzerzodesesJogviszonyDokumentumDTO toDto(SzerzodesesJogviszonyDokumentum entity);

    @Override
    @Mapping(target = "szerzodesesJogviszony", source = "szerzodesesJogviszonyId", qualifiedByName = "szerzodesesJogviszonyFromId")
    SzerzodesesJogviszonyDokumentum toEntity(SzerzodesesJogviszonyDokumentumDTO dto);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "dokumentumNev", source = "dokumentumNev")
    @Mapping(target = "leiras", source = "leiras")
    @Mapping(target = "fajlUtvonal", source = "fajlUtvonal")
    @Mapping(target = "contentType", source = "contentType")
    @Mapping(target = "feltoltesIdeje", source = "feltoltesIdeje")
    @Mapping(target = "dokumentumTipus", source = "dokumentumTipus")
    @Mapping(target = "szerzodesesJogviszony", source = "szerzodesesJogviszonyId", qualifiedByName = "szerzodesesJogviszonyFromId")
    void partialUpdate(@MappingTarget SzerzodesesJogviszonyDokumentum entity, SzerzodesesJogviszonyDokumentumDTO dto);

    @Named("szerzodesesJogviszonyokForDocument")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "szerzodesAzonosito", source = "szerzodesAzonosito")
    @Mapping(target = "jogviszonyKezdete", source = "jogviszonyKezdete")
    @Mapping(target = "jogviszonyLejarata", source = "jogviszonyLejarata")
    SzerzodesesJogviszonyokDTO toDtoSzerzodesesJogviszonyokForDocument(SzerzodesesJogviszonyok szerzodesesJogviszony);

    @Named("szerzodesesJogviszonyFromId")
    default SzerzodesesJogviszonyok mapToSzerzodesesJogviszony(Long id) {
        if (id == null) {
            return null;
        }
        SzerzodesesJogviszonyok jogviszony = new SzerzodesesJogviszonyok();
        jogviszony.setId(id);
        return jogviszony;
    }
}

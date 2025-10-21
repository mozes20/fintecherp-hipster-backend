package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.SzerzodesDokumentumTipus;
import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate;
import com.fintech.erp.service.dto.SzerzodesDokumentumTipusDTO;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SzerzodesesJogviszonyDokumentumTemplate} and its DTO {@link SzerzodesesJogviszonyDokumentumTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface SzerzodesesJogviszonyDokumentumTemplateMapper
    extends EntityMapper<SzerzodesesJogviszonyDokumentumTemplateDTO, SzerzodesesJogviszonyDokumentumTemplate> {
    @Override
    @Mapping(target = "dokumentumTipus", source = "dokumentumTipus", qualifiedByName = "szerzodesDokumentumTipusId")
    @Mapping(target = "dokumentumTipusId", source = "dokumentumTipus.id")
    SzerzodesesJogviszonyDokumentumTemplateDTO toDto(SzerzodesesJogviszonyDokumentumTemplate entity);

    @Override
    @Mapping(target = "dokumentumTipus", source = "dokumentumTipusId", qualifiedByName = "szerzodesDokumentumTipusFromId")
    SzerzodesesJogviszonyDokumentumTemplate toEntity(SzerzodesesJogviszonyDokumentumTemplateDTO dto);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "templateNev", source = "templateNev")
    @Mapping(target = "templateLeiras", source = "templateLeiras")
    @Mapping(target = "fajlUtvonal", source = "fajlUtvonal")
    @Mapping(target = "utolsoModositas", source = "utolsoModositas")
    @Mapping(target = "dokumentumTipus", source = "dokumentumTipusId", qualifiedByName = "szerzodesDokumentumTipusFromId")
    void partialUpdate(@MappingTarget SzerzodesesJogviszonyDokumentumTemplate entity, SzerzodesesJogviszonyDokumentumTemplateDTO dto);

    @Named("szerzodesDokumentumTipusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nev", source = "nev")
    SzerzodesDokumentumTipusDTO toDtoSzerzodesDokumentumTipusId(SzerzodesDokumentumTipus dokumentumTipus);

    @Named("szerzodesDokumentumTipusFromId")
    default SzerzodesDokumentumTipus mapToSzerzodesDokumentumTipus(Long id) {
        if (id == null) {
            return null;
        }
        SzerzodesDokumentumTipus dokumentumTipus = new SzerzodesDokumentumTipus();
        dokumentumTipus.setId(id);
        return dokumentumTipus;
    }
}

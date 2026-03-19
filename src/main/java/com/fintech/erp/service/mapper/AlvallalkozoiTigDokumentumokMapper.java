package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import com.fintech.erp.domain.AlvallalkozoiTigDokumentumok;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
import com.fintech.erp.service.dto.AlvallalkozoiTigDokumentumokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlvallalkozoiTigDokumentumok} and its DTO {@link AlvallalkozoiTigDokumentumokDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlvallalkozoiTigDokumentumokMapper
    extends EntityMapper<AlvallalkozoiTigDokumentumokDTO, AlvallalkozoiTigDokumentumok> {

    @Mapping(target = "elszamolas", source = "elszamolas", qualifiedByName = "alvallalkozoiElszamolasokId")
    AlvallalkozoiTigDokumentumokDTO toDto(AlvallalkozoiTigDokumentumok s);

    @Named("alvallalkozoiElszamolasokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlvallalkozoiElszamolasokDTO toDtoAlvallalkozoiElszamolasokId(AlvallalkozoiElszamolasok alvallalkozoiElszamolasok);
}

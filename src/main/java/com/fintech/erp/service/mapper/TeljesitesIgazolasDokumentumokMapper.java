package com.fintech.erp.service.mapper;

import com.fintech.erp.domain.TeljesitesIgazolasDokumentumok;
import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.service.dto.TeljesitesIgazolasDokumentumokDTO;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeljesitesIgazolasDokumentumok} and its DTO {@link TeljesitesIgazolasDokumentumokDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeljesitesIgazolasDokumentumokMapper
    extends EntityMapper<TeljesitesIgazolasDokumentumokDTO, TeljesitesIgazolasDokumentumok> {
    @Mapping(target = "teljesitesIgazolas", source = "teljesitesIgazolas", qualifiedByName = "ugyfelElszamolasokId")
    TeljesitesIgazolasDokumentumokDTO toDto(TeljesitesIgazolasDokumentumok s);

    @Named("ugyfelElszamolasokId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UgyfelElszamolasokDTO toDtoUgyfelElszamolasokId(UgyfelElszamolasok ugyfelElszamolasok);
}

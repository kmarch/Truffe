package com.audensiel.truffe.service.mapper;

import com.audensiel.truffe.domain.Agence;
import com.audensiel.truffe.domain.Commercial;
import com.audensiel.truffe.service.dto.AgenceDTO;
import com.audensiel.truffe.service.dto.CommercialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commercial} and its DTO {@link CommercialDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommercialMapper extends EntityMapper<CommercialDTO, Commercial> {
    @Mapping(target = "nomAgence", source = "nomAgence", qualifiedByName = "agenceId")
    CommercialDTO toDto(Commercial s);

    @Named("agenceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgenceDTO toDtoAgenceId(Agence agence);
}

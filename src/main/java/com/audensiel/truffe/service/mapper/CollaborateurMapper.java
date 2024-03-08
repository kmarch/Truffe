package com.audensiel.truffe.service.mapper;

import com.audensiel.truffe.domain.Agence;
import com.audensiel.truffe.domain.Collaborateur;
import com.audensiel.truffe.domain.Competence;
import com.audensiel.truffe.service.dto.AgenceDTO;
import com.audensiel.truffe.service.dto.CollaborateurDTO;
import com.audensiel.truffe.service.dto.CompetenceDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collaborateur} and its DTO {@link CollaborateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface CollaborateurMapper extends EntityMapper<CollaborateurDTO, Collaborateur> {
    @Mapping(target = "competences", source = "competences", qualifiedByName = "competenceIdSet")
    @Mapping(target = "nomAgence", source = "nomAgence", qualifiedByName = "agenceId")
    CollaborateurDTO toDto(Collaborateur s);

    @Mapping(target = "removeCompetence", ignore = true)
    Collaborateur toEntity(CollaborateurDTO collaborateurDTO);

    @Named("competenceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompetenceDTO toDtoCompetenceId(Competence competence);

    @Named("competenceIdSet")
    default Set<CompetenceDTO> toDtoCompetenceIdSet(Set<Competence> competence) {
        return competence.stream().map(this::toDtoCompetenceId).collect(Collectors.toSet());
    }

    @Named("agenceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgenceDTO toDtoAgenceId(Agence agence);
}

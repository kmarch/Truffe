package com.audensiel.truffe.service.mapper;

import com.audensiel.truffe.domain.Competence;
import com.audensiel.truffe.service.dto.CompetenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competence} and its DTO {@link CompetenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetenceMapper extends EntityMapper<CompetenceDTO, Competence> {}

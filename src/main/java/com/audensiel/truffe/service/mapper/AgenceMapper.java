package com.audensiel.truffe.service.mapper;

import com.audensiel.truffe.domain.Agence;
import com.audensiel.truffe.service.dto.AgenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agence} and its DTO {@link AgenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgenceMapper extends EntityMapper<AgenceDTO, Agence> {}

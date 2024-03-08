package com.audensiel.truffe.service.impl;

import com.audensiel.truffe.domain.Competence;
import com.audensiel.truffe.repository.CompetenceRepository;
import com.audensiel.truffe.service.CompetenceService;
import com.audensiel.truffe.service.dto.CompetenceDTO;
import com.audensiel.truffe.service.mapper.CompetenceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.audensiel.truffe.domain.Competence}.
 */
@Service
public class CompetenceServiceImpl implements CompetenceService {

    private final Logger log = LoggerFactory.getLogger(CompetenceServiceImpl.class);

    private final CompetenceRepository competenceRepository;

    private final CompetenceMapper competenceMapper;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CompetenceMapper competenceMapper) {
        this.competenceRepository = competenceRepository;
        this.competenceMapper = competenceMapper;
    }

    @Override
    public CompetenceDTO save(CompetenceDTO competenceDTO) {
        log.debug("Request to save Competence : {}", competenceDTO);
        Competence competence = competenceMapper.toEntity(competenceDTO);
        competence = competenceRepository.save(competence);
        return competenceMapper.toDto(competence);
    }

    @Override
    public CompetenceDTO update(CompetenceDTO competenceDTO) {
        log.debug("Request to update Competence : {}", competenceDTO);
        Competence competence = competenceMapper.toEntity(competenceDTO);
        competence = competenceRepository.save(competence);
        return competenceMapper.toDto(competence);
    }

    @Override
    public Optional<CompetenceDTO> partialUpdate(CompetenceDTO competenceDTO) {
        log.debug("Request to partially update Competence : {}", competenceDTO);

        return competenceRepository
            .findById(competenceDTO.getId())
            .map(existingCompetence -> {
                competenceMapper.partialUpdate(existingCompetence, competenceDTO);

                return existingCompetence;
            })
            .map(competenceRepository::save)
            .map(competenceMapper::toDto);
    }

    @Override
    public List<CompetenceDTO> findAll() {
        log.debug("Request to get all Competences");
        return competenceRepository.findAll().stream().map(competenceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CompetenceDTO> findOne(String id) {
        log.debug("Request to get Competence : {}", id);
        return competenceRepository.findById(id).map(competenceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Competence : {}", id);
        competenceRepository.deleteById(id);
    }
}

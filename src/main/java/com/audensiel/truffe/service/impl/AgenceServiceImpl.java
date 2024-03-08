package com.audensiel.truffe.service.impl;

import com.audensiel.truffe.domain.Agence;
import com.audensiel.truffe.repository.AgenceRepository;
import com.audensiel.truffe.service.AgenceService;
import com.audensiel.truffe.service.dto.AgenceDTO;
import com.audensiel.truffe.service.mapper.AgenceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.audensiel.truffe.domain.Agence}.
 */
@Service
public class AgenceServiceImpl implements AgenceService {

    private final Logger log = LoggerFactory.getLogger(AgenceServiceImpl.class);

    private final AgenceRepository agenceRepository;

    private final AgenceMapper agenceMapper;

    public AgenceServiceImpl(AgenceRepository agenceRepository, AgenceMapper agenceMapper) {
        this.agenceRepository = agenceRepository;
        this.agenceMapper = agenceMapper;
    }

    @Override
    public AgenceDTO save(AgenceDTO agenceDTO) {
        log.debug("Request to save Agence : {}", agenceDTO);
        Agence agence = agenceMapper.toEntity(agenceDTO);
        agence = agenceRepository.save(agence);
        return agenceMapper.toDto(agence);
    }

    @Override
    public AgenceDTO update(AgenceDTO agenceDTO) {
        log.debug("Request to update Agence : {}", agenceDTO);
        Agence agence = agenceMapper.toEntity(agenceDTO);
        agence = agenceRepository.save(agence);
        return agenceMapper.toDto(agence);
    }

    @Override
    public Optional<AgenceDTO> partialUpdate(AgenceDTO agenceDTO) {
        log.debug("Request to partially update Agence : {}", agenceDTO);

        return agenceRepository
            .findById(agenceDTO.getId())
            .map(existingAgence -> {
                agenceMapper.partialUpdate(existingAgence, agenceDTO);

                return existingAgence;
            })
            .map(agenceRepository::save)
            .map(agenceMapper::toDto);
    }

    @Override
    public List<AgenceDTO> findAll() {
        log.debug("Request to get all Agences");
        return agenceRepository.findAll().stream().map(agenceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<AgenceDTO> findOne(String id) {
        log.debug("Request to get Agence : {}", id);
        return agenceRepository.findById(id).map(agenceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Agence : {}", id);
        agenceRepository.deleteById(id);
    }
}

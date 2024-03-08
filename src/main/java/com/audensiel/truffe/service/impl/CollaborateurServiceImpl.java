package com.audensiel.truffe.service.impl;

import com.audensiel.truffe.domain.Collaborateur;
import com.audensiel.truffe.repository.CollaborateurRepository;
import com.audensiel.truffe.service.CollaborateurService;
import com.audensiel.truffe.service.dto.CollaborateurDTO;
import com.audensiel.truffe.service.mapper.CollaborateurMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.audensiel.truffe.domain.Collaborateur}.
 */
@Service
public class CollaborateurServiceImpl implements CollaborateurService {

    private final Logger log = LoggerFactory.getLogger(CollaborateurServiceImpl.class);

    private final CollaborateurRepository collaborateurRepository;

    private final CollaborateurMapper collaborateurMapper;

    public CollaborateurServiceImpl(CollaborateurRepository collaborateurRepository, CollaborateurMapper collaborateurMapper) {
        this.collaborateurRepository = collaborateurRepository;
        this.collaborateurMapper = collaborateurMapper;
    }

    @Override
    public CollaborateurDTO save(CollaborateurDTO collaborateurDTO) {
        log.debug("Request to save Collaborateur : {}", collaborateurDTO);
        Collaborateur collaborateur = collaborateurMapper.toEntity(collaborateurDTO);
        collaborateur = collaborateurRepository.save(collaborateur);
        return collaborateurMapper.toDto(collaborateur);
    }

    @Override
    public CollaborateurDTO update(CollaborateurDTO collaborateurDTO) {
        log.debug("Request to update Collaborateur : {}", collaborateurDTO);
        Collaborateur collaborateur = collaborateurMapper.toEntity(collaborateurDTO);
        collaborateur = collaborateurRepository.save(collaborateur);
        return collaborateurMapper.toDto(collaborateur);
    }

    @Override
    public Optional<CollaborateurDTO> partialUpdate(CollaborateurDTO collaborateurDTO) {
        log.debug("Request to partially update Collaborateur : {}", collaborateurDTO);

        return collaborateurRepository
            .findById(collaborateurDTO.getId())
            .map(existingCollaborateur -> {
                collaborateurMapper.partialUpdate(existingCollaborateur, collaborateurDTO);

                return existingCollaborateur;
            })
            .map(collaborateurRepository::save)
            .map(collaborateurMapper::toDto);
    }

    @Override
    public List<CollaborateurDTO> findAll() {
        log.debug("Request to get all Collaborateurs");
        return collaborateurRepository.findAll().stream().map(collaborateurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<CollaborateurDTO> findAllWithEagerRelationships(Pageable pageable) {
        return collaborateurRepository.findAllWithEagerRelationships(pageable).map(collaborateurMapper::toDto);
    }

    @Override
    public Optional<CollaborateurDTO> findOne(String id) {
        log.debug("Request to get Collaborateur : {}", id);
        return collaborateurRepository.findOneWithEagerRelationships(id).map(collaborateurMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Collaborateur : {}", id);
        collaborateurRepository.deleteById(id);
    }
}

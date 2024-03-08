package com.audensiel.truffe.service;

import com.audensiel.truffe.service.dto.CollaborateurDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.audensiel.truffe.domain.Collaborateur}.
 */
public interface CollaborateurService {
    /**
     * Save a collaborateur.
     *
     * @param collaborateurDTO the entity to save.
     * @return the persisted entity.
     */
    CollaborateurDTO save(CollaborateurDTO collaborateurDTO);

    /**
     * Updates a collaborateur.
     *
     * @param collaborateurDTO the entity to update.
     * @return the persisted entity.
     */
    CollaborateurDTO update(CollaborateurDTO collaborateurDTO);

    /**
     * Partially updates a collaborateur.
     *
     * @param collaborateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollaborateurDTO> partialUpdate(CollaborateurDTO collaborateurDTO);

    /**
     * Get all the collaborateurs.
     *
     * @return the list of entities.
     */
    List<CollaborateurDTO> findAll();

    /**
     * Get all the collaborateurs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollaborateurDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" collaborateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollaborateurDTO> findOne(String id);

    /**
     * Delete the "id" collaborateur.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

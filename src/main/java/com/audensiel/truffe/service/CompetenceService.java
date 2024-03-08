package com.audensiel.truffe.service;

import com.audensiel.truffe.service.dto.CompetenceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.audensiel.truffe.domain.Competence}.
 */
public interface CompetenceService {
    /**
     * Save a competence.
     *
     * @param competenceDTO the entity to save.
     * @return the persisted entity.
     */
    CompetenceDTO save(CompetenceDTO competenceDTO);

    /**
     * Updates a competence.
     *
     * @param competenceDTO the entity to update.
     * @return the persisted entity.
     */
    CompetenceDTO update(CompetenceDTO competenceDTO);

    /**
     * Partially updates a competence.
     *
     * @param competenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetenceDTO> partialUpdate(CompetenceDTO competenceDTO);

    /**
     * Get all the competences.
     *
     * @return the list of entities.
     */
    List<CompetenceDTO> findAll();

    /**
     * Get the "id" competence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetenceDTO> findOne(String id);

    /**
     * Delete the "id" competence.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

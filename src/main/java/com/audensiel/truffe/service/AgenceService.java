package com.audensiel.truffe.service;

import com.audensiel.truffe.service.dto.AgenceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.audensiel.truffe.domain.Agence}.
 */
public interface AgenceService {
    /**
     * Save a agence.
     *
     * @param agenceDTO the entity to save.
     * @return the persisted entity.
     */
    AgenceDTO save(AgenceDTO agenceDTO);

    /**
     * Updates a agence.
     *
     * @param agenceDTO the entity to update.
     * @return the persisted entity.
     */
    AgenceDTO update(AgenceDTO agenceDTO);

    /**
     * Partially updates a agence.
     *
     * @param agenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgenceDTO> partialUpdate(AgenceDTO agenceDTO);

    /**
     * Get all the agences.
     *
     * @return the list of entities.
     */
    List<AgenceDTO> findAll();

    /**
     * Get the "id" agence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgenceDTO> findOne(String id);

    /**
     * Delete the "id" agence.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

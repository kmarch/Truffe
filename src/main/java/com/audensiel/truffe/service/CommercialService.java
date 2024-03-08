package com.audensiel.truffe.service;

import com.audensiel.truffe.service.dto.CommercialDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.audensiel.truffe.domain.Commercial}.
 */
public interface CommercialService {
    /**
     * Save a commercial.
     *
     * @param commercialDTO the entity to save.
     * @return the persisted entity.
     */
    CommercialDTO save(CommercialDTO commercialDTO);

    /**
     * Updates a commercial.
     *
     * @param commercialDTO the entity to update.
     * @return the persisted entity.
     */
    CommercialDTO update(CommercialDTO commercialDTO);

    /**
     * Partially updates a commercial.
     *
     * @param commercialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommercialDTO> partialUpdate(CommercialDTO commercialDTO);

    /**
     * Get all the commercials.
     *
     * @return the list of entities.
     */
    List<CommercialDTO> findAll();

    /**
     * Get the "id" commercial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommercialDTO> findOne(String id);

    /**
     * Delete the "id" commercial.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

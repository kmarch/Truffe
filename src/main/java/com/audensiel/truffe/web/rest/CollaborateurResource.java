package com.audensiel.truffe.web.rest;

import com.audensiel.truffe.repository.CollaborateurRepository;
import com.audensiel.truffe.service.CollaborateurService;
import com.audensiel.truffe.service.dto.CollaborateurDTO;
import com.audensiel.truffe.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.audensiel.truffe.domain.Collaborateur}.
 */
@RestController
@RequestMapping("/api/collaborateurs")
public class CollaborateurResource {

    private final Logger log = LoggerFactory.getLogger(CollaborateurResource.class);

    private static final String ENTITY_NAME = "collaborateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaborateurService collaborateurService;

    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurResource(CollaborateurService collaborateurService, CollaborateurRepository collaborateurRepository) {
        this.collaborateurService = collaborateurService;
        this.collaborateurRepository = collaborateurRepository;
    }

    /**
     * {@code POST  /collaborateurs} : Create a new collaborateur.
     *
     * @param collaborateurDTO the collaborateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaborateurDTO, or with status {@code 400 (Bad Request)} if the collaborateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CollaborateurDTO> createCollaborateur(@RequestBody CollaborateurDTO collaborateurDTO) throws URISyntaxException {
        log.debug("REST request to save Collaborateur : {}", collaborateurDTO);
        if (collaborateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new collaborateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollaborateurDTO result = collaborateurService.save(collaborateurDTO);
        return ResponseEntity
            .created(new URI("/api/collaborateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /collaborateurs/:id} : Updates an existing collaborateur.
     *
     * @param id the id of the collaborateurDTO to save.
     * @param collaborateurDTO the collaborateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborateurDTO,
     * or with status {@code 400 (Bad Request)} if the collaborateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaborateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CollaborateurDTO> updateCollaborateur(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CollaborateurDTO collaborateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Collaborateur : {}, {}", id, collaborateurDTO);
        if (collaborateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaborateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collaborateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollaborateurDTO result = collaborateurService.update(collaborateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collaborateurDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /collaborateurs/:id} : Partial updates given fields of an existing collaborateur, field will ignore if it is null
     *
     * @param id the id of the collaborateurDTO to save.
     * @param collaborateurDTO the collaborateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborateurDTO,
     * or with status {@code 400 (Bad Request)} if the collaborateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the collaborateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the collaborateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CollaborateurDTO> partialUpdateCollaborateur(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CollaborateurDTO collaborateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collaborateur partially : {}, {}", id, collaborateurDTO);
        if (collaborateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaborateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collaborateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollaborateurDTO> result = collaborateurService.partialUpdate(collaborateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collaborateurDTO.getId())
        );
    }

    /**
     * {@code GET  /collaborateurs} : get all the collaborateurs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaborateurs in body.
     */
    @GetMapping("")
    public List<CollaborateurDTO> getAllCollaborateurs(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Collaborateurs");
        return collaborateurService.findAll();
    }

    /**
     * {@code GET  /collaborateurs/:id} : get the "id" collaborateur.
     *
     * @param id the id of the collaborateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaborateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CollaborateurDTO> getCollaborateur(@PathVariable("id") String id) {
        log.debug("REST request to get Collaborateur : {}", id);
        Optional<CollaborateurDTO> collaborateurDTO = collaborateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collaborateurDTO);
    }

    /**
     * {@code DELETE  /collaborateurs/:id} : delete the "id" collaborateur.
     *
     * @param id the id of the collaborateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable("id") String id) {
        log.debug("REST request to delete Collaborateur : {}", id);
        collaborateurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}

package com.audensiel.truffe.web.rest;

import com.audensiel.truffe.repository.CommercialRepository;
import com.audensiel.truffe.service.CommercialService;
import com.audensiel.truffe.service.dto.CommercialDTO;
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
 * REST controller for managing {@link com.audensiel.truffe.domain.Commercial}.
 */
@RestController
@RequestMapping("/api/commercials")
public class CommercialResource {

    private final Logger log = LoggerFactory.getLogger(CommercialResource.class);

    private static final String ENTITY_NAME = "commercial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommercialService commercialService;

    private final CommercialRepository commercialRepository;

    public CommercialResource(CommercialService commercialService, CommercialRepository commercialRepository) {
        this.commercialService = commercialService;
        this.commercialRepository = commercialRepository;
    }

    /**
     * {@code POST  /commercials} : Create a new commercial.
     *
     * @param commercialDTO the commercialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commercialDTO, or with status {@code 400 (Bad Request)} if the commercial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CommercialDTO> createCommercial(@RequestBody CommercialDTO commercialDTO) throws URISyntaxException {
        log.debug("REST request to save Commercial : {}", commercialDTO);
        if (commercialDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialDTO result = commercialService.save(commercialDTO);
        return ResponseEntity
            .created(new URI("/api/commercials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /commercials/:id} : Updates an existing commercial.
     *
     * @param id the id of the commercialDTO to save.
     * @param commercialDTO the commercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commercialDTO,
     * or with status {@code 400 (Bad Request)} if the commercialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommercialDTO> updateCommercial(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CommercialDTO commercialDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Commercial : {}, {}", id, commercialDTO);
        if (commercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commercialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commercialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommercialDTO result = commercialService.update(commercialDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commercialDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /commercials/:id} : Partial updates given fields of an existing commercial, field will ignore if it is null
     *
     * @param id the id of the commercialDTO to save.
     * @param commercialDTO the commercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commercialDTO,
     * or with status {@code 400 (Bad Request)} if the commercialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commercialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommercialDTO> partialUpdateCommercial(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CommercialDTO commercialDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Commercial partially : {}, {}", id, commercialDTO);
        if (commercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commercialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commercialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommercialDTO> result = commercialService.partialUpdate(commercialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commercialDTO.getId())
        );
    }

    /**
     * {@code GET  /commercials} : get all the commercials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commercials in body.
     */
    @GetMapping("")
    public List<CommercialDTO> getAllCommercials() {
        log.debug("REST request to get all Commercials");
        return commercialService.findAll();
    }

    /**
     * {@code GET  /commercials/:id} : get the "id" commercial.
     *
     * @param id the id of the commercialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commercialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommercialDTO> getCommercial(@PathVariable("id") String id) {
        log.debug("REST request to get Commercial : {}", id);
        Optional<CommercialDTO> commercialDTO = commercialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialDTO);
    }

    /**
     * {@code DELETE  /commercials/:id} : delete the "id" commercial.
     *
     * @param id the id of the commercialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommercial(@PathVariable("id") String id) {
        log.debug("REST request to delete Commercial : {}", id);
        commercialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}

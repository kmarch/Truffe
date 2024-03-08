package com.audensiel.truffe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.audensiel.truffe.IntegrationTest;
import com.audensiel.truffe.domain.Competence;
import com.audensiel.truffe.repository.CompetenceRepository;
import com.audensiel.truffe.service.dto.CompetenceDTO;
import com.audensiel.truffe.service.mapper.CompetenceMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CompetenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetenceResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/competences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private CompetenceMapper competenceMapper;

    @Autowired
    private MockMvc restCompetenceMockMvc;

    private Competence competence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competence createEntity() {
        Competence competence = new Competence().nom(DEFAULT_NOM);
        return competence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competence createUpdatedEntity() {
        Competence competence = new Competence().nom(UPDATED_NOM);
        return competence;
    }

    @BeforeEach
    public void initTest() {
        competenceRepository.deleteAll();
        competence = createEntity();
    }

    @Test
    void createCompetence() throws Exception {
        int databaseSizeBeforeCreate = competenceRepository.findAll().size();
        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);
        restCompetenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate + 1);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    void createCompetenceWithExistingId() throws Exception {
        // Create the Competence with an existing ID
        competence.setId("existing_id");
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        int databaseSizeBeforeCreate = competenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCompetences() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        // Get all the competenceList
        restCompetenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competence.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    void getCompetence() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        // Get the competence
        restCompetenceMockMvc
            .perform(get(ENTITY_API_URL_ID, competence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competence.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    void getNonExistingCompetence() throws Exception {
        // Get the competence
        restCompetenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCompetence() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence
        Competence updatedCompetence = competenceRepository.findById(competence.getId()).orElseThrow();
        updatedCompetence.nom(UPDATED_NOM);
        CompetenceDTO competenceDTO = competenceMapper.toDto(updatedCompetence);

        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competenceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    void putNonExistingCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competenceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCompetenceWithPatch() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence using partial update
        Competence partialUpdatedCompetence = new Competence();
        partialUpdatedCompetence.setId(competence.getId());

        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetence.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetence))
            )
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    void fullUpdateCompetenceWithPatch() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence using partial update
        Competence partialUpdatedCompetence = new Competence();
        partialUpdatedCompetence.setId(competence.getId());

        partialUpdatedCompetence.nom(UPDATED_NOM);

        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetence.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetence))
            )
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    void patchNonExistingCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competenceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();
        competence.setId(UUID.randomUUID().toString());

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCompetence() throws Exception {
        // Initialize the database
        competenceRepository.save(competence);

        int databaseSizeBeforeDelete = competenceRepository.findAll().size();

        // Delete the competence
        restCompetenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, competence.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

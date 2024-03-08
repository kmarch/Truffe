package com.audensiel.truffe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.audensiel.truffe.IntegrationTest;
import com.audensiel.truffe.domain.Collaborateur;
import com.audensiel.truffe.repository.CollaborateurRepository;
import com.audensiel.truffe.service.CollaborateurService;
import com.audensiel.truffe.service.dto.CollaborateurDTO;
import com.audensiel.truffe.service.mapper.CollaborateurMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CollaborateurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CollaborateurResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DISPO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DISPO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ANGLAIS = "AAAAAAAAAA";
    private static final String UPDATED_ANGLAIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collaborateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Mock
    private CollaborateurRepository collaborateurRepositoryMock;

    @Autowired
    private CollaborateurMapper collaborateurMapper;

    @Mock
    private CollaborateurService collaborateurServiceMock;

    @Autowired
    private MockMvc restCollaborateurMockMvc;

    private Collaborateur collaborateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateur createEntity() {
        Collaborateur collaborateur = new Collaborateur()
            .matricule(DEFAULT_MATRICULE)
            .diplome(DEFAULT_DIPLOME)
            .dateDispo(DEFAULT_DATE_DISPO)
            .anglais(DEFAULT_ANGLAIS);
        return collaborateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaborateur createUpdatedEntity() {
        Collaborateur collaborateur = new Collaborateur()
            .matricule(UPDATED_MATRICULE)
            .diplome(UPDATED_DIPLOME)
            .dateDispo(UPDATED_DATE_DISPO)
            .anglais(UPDATED_ANGLAIS);
        return collaborateur;
    }

    @BeforeEach
    public void initTest() {
        collaborateurRepository.deleteAll();
        collaborateur = createEntity();
    }

    @Test
    void createCollaborateur() throws Exception {
        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();
        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);
        restCollaborateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate + 1);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testCollaborateur.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testCollaborateur.getDateDispo()).isEqualTo(DEFAULT_DATE_DISPO);
        assertThat(testCollaborateur.getAnglais()).isEqualTo(DEFAULT_ANGLAIS);
    }

    @Test
    void createCollaborateurWithExistingId() throws Exception {
        // Create the Collaborateur with an existing ID
        collaborateur.setId("existing_id");
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        int databaseSizeBeforeCreate = collaborateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaborateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCollaborateurs() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        // Get all the collaborateurList
        restCollaborateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaborateur.getId())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME)))
            .andExpect(jsonPath("$.[*].dateDispo").value(hasItem(DEFAULT_DATE_DISPO.toString())))
            .andExpect(jsonPath("$.[*].anglais").value(hasItem(DEFAULT_ANGLAIS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollaborateursWithEagerRelationshipsIsEnabled() throws Exception {
        when(collaborateurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaborateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(collaborateurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollaborateursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(collaborateurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaborateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(collaborateurRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        // Get the collaborateur
        restCollaborateurMockMvc
            .perform(get(ENTITY_API_URL_ID, collaborateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collaborateur.getId()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME))
            .andExpect(jsonPath("$.dateDispo").value(DEFAULT_DATE_DISPO.toString()))
            .andExpect(jsonPath("$.anglais").value(DEFAULT_ANGLAIS));
    }

    @Test
    void getNonExistingCollaborateur() throws Exception {
        // Get the collaborateur
        restCollaborateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur
        Collaborateur updatedCollaborateur = collaborateurRepository.findById(collaborateur.getId()).orElseThrow();
        updatedCollaborateur.matricule(UPDATED_MATRICULE).diplome(UPDATED_DIPLOME).dateDispo(UPDATED_DATE_DISPO).anglais(UPDATED_ANGLAIS);
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(updatedCollaborateur);

        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collaborateurDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testCollaborateur.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testCollaborateur.getDateDispo()).isEqualTo(UPDATED_DATE_DISPO);
        assertThat(testCollaborateur.getAnglais()).isEqualTo(UPDATED_ANGLAIS);
    }

    @Test
    void putNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collaborateurDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCollaborateurWithPatch() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur using partial update
        Collaborateur partialUpdatedCollaborateur = new Collaborateur();
        partialUpdatedCollaborateur.setId(collaborateur.getId());

        partialUpdatedCollaborateur.dateDispo(UPDATED_DATE_DISPO).anglais(UPDATED_ANGLAIS);

        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborateur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testCollaborateur.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testCollaborateur.getDateDispo()).isEqualTo(UPDATED_DATE_DISPO);
        assertThat(testCollaborateur.getAnglais()).isEqualTo(UPDATED_ANGLAIS);
    }

    @Test
    void fullUpdateCollaborateurWithPatch() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();

        // Update the collaborateur using partial update
        Collaborateur partialUpdatedCollaborateur = new Collaborateur();
        partialUpdatedCollaborateur.setId(collaborateur.getId());

        partialUpdatedCollaborateur
            .matricule(UPDATED_MATRICULE)
            .diplome(UPDATED_DIPLOME)
            .dateDispo(UPDATED_DATE_DISPO)
            .anglais(UPDATED_ANGLAIS);

        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollaborateur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollaborateur))
            )
            .andExpect(status().isOk());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
        Collaborateur testCollaborateur = collaborateurList.get(collaborateurList.size() - 1);
        assertThat(testCollaborateur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testCollaborateur.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testCollaborateur.getDateDispo()).isEqualTo(UPDATED_DATE_DISPO);
        assertThat(testCollaborateur.getAnglais()).isEqualTo(UPDATED_ANGLAIS);
    }

    @Test
    void patchNonExistingCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collaborateurDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCollaborateur() throws Exception {
        int databaseSizeBeforeUpdate = collaborateurRepository.findAll().size();
        collaborateur.setId(UUID.randomUUID().toString());

        // Create the Collaborateur
        CollaborateurDTO collaborateurDTO = collaborateurMapper.toDto(collaborateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollaborateurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collaborateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collaborateur in the database
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCollaborateur() throws Exception {
        // Initialize the database
        collaborateurRepository.save(collaborateur);

        int databaseSizeBeforeDelete = collaborateurRepository.findAll().size();

        // Delete the collaborateur
        restCollaborateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, collaborateur.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collaborateur> collaborateurList = collaborateurRepository.findAll();
        assertThat(collaborateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

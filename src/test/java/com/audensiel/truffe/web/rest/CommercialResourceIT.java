package com.audensiel.truffe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.audensiel.truffe.IntegrationTest;
import com.audensiel.truffe.domain.Commercial;
import com.audensiel.truffe.repository.CommercialRepository;
import com.audensiel.truffe.service.dto.CommercialDTO;
import com.audensiel.truffe.service.mapper.CommercialMapper;
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
 * Integration tests for the {@link CommercialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommercialResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/commercials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CommercialRepository commercialRepository;

    @Autowired
    private CommercialMapper commercialMapper;

    @Autowired
    private MockMvc restCommercialMockMvc;

    private Commercial commercial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercial createEntity() {
        Commercial commercial = new Commercial().matricule(DEFAULT_MATRICULE);
        return commercial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercial createUpdatedEntity() {
        Commercial commercial = new Commercial().matricule(UPDATED_MATRICULE);
        return commercial;
    }

    @BeforeEach
    public void initTest() {
        commercialRepository.deleteAll();
        commercial = createEntity();
    }

    @Test
    void createCommercial() throws Exception {
        int databaseSizeBeforeCreate = commercialRepository.findAll().size();
        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);
        restCommercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeCreate + 1);
        Commercial testCommercial = commercialList.get(commercialList.size() - 1);
        assertThat(testCommercial.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    void createCommercialWithExistingId() throws Exception {
        // Create the Commercial with an existing ID
        commercial.setId("existing_id");
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        int databaseSizeBeforeCreate = commercialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCommercials() throws Exception {
        // Initialize the database
        commercialRepository.save(commercial);

        // Get all the commercialList
        restCommercialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercial.getId())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)));
    }

    @Test
    void getCommercial() throws Exception {
        // Initialize the database
        commercialRepository.save(commercial);

        // Get the commercial
        restCommercialMockMvc
            .perform(get(ENTITY_API_URL_ID, commercial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commercial.getId()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE));
    }

    @Test
    void getNonExistingCommercial() throws Exception {
        // Get the commercial
        restCommercialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCommercial() throws Exception {
        // Initialize the database
        commercialRepository.save(commercial);

        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();

        // Update the commercial
        Commercial updatedCommercial = commercialRepository.findById(commercial.getId()).orElseThrow();
        updatedCommercial.matricule(UPDATED_MATRICULE);
        CommercialDTO commercialDTO = commercialMapper.toDto(updatedCommercial);

        restCommercialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commercialDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isOk());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
        Commercial testCommercial = commercialList.get(commercialList.size() - 1);
        assertThat(testCommercial.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    void putNonExistingCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();
        commercial.setId(UUID.randomUUID().toString());

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commercialDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();
        commercial.setId(UUID.randomUUID().toString());

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();
        commercial.setId(UUID.randomUUID().toString());

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercialMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCommercialWithPatch() throws Exception {
        // Initialize the database
        commercialRepository.save(commercial);

        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();

        // Update the commercial using partial update
        Commercial partialUpdatedCommercial = new Commercial();
        partialUpdatedCommercial.setId(commercial.getId());

        restCommercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommercial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommercial))
            )
            .andExpect(status().isOk());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
        Commercial testCommercial = commercialList.get(commercialList.size() - 1);
        assertThat(testCommercial.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    void fullUpdateCommercialWithPatch() throws Exception {
        // Initialize the database
        commercialRepository.save(commercial);

        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();

        // Update the commercial using partial update
        Commercial partialUpdatedCommercial = new Commercial();
        partialUpdatedCommercial.setId(commercial.getId());

        partialUpdatedCommercial.matricule(UPDATED_MATRICULE);

        restCommercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommercial.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommercial))
            )
            .andExpect(status().isOk());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
        Commercial testCommercial = commercialList.get(commercialList.size() - 1);
        assertThat(testCommercial.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    void patchNonExistingCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();
        commercial.setId(UUID.randomUUID().toString());

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commercialDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();
        commercial.setId(UUID.randomUUID().toString());

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();
        commercial.setId(UUID.randomUUID().toString());

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commercialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCommercial() throws Exception {
        // Initialize the database
        commercialRepository.save(commercial);

        int databaseSizeBeforeDelete = commercialRepository.findAll().size();

        // Delete the commercial
        restCommercialMockMvc
            .perform(delete(ENTITY_API_URL_ID, commercial.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

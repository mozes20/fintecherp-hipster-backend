package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.SajatCegKepviselokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegKepviselok;
import com.fintech.erp.repository.SajatCegKepviselokRepository;
import com.fintech.erp.service.dto.SajatCegKepviselokDTO;
import com.fintech.erp.service.mapper.SajatCegKepviselokMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SajatCegKepviselokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SajatCegKepviselokResourceIT {

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sajat-ceg-kepviseloks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SajatCegKepviselokRepository sajatCegKepviselokRepository;

    @Autowired
    private SajatCegKepviselokMapper sajatCegKepviselokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSajatCegKepviselokMockMvc;

    private SajatCegKepviselok sajatCegKepviselok;

    private SajatCegKepviselok insertedSajatCegKepviselok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SajatCegKepviselok createEntity() {
        return new SajatCegKepviselok().statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SajatCegKepviselok createUpdatedEntity() {
        return new SajatCegKepviselok().statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        sajatCegKepviselok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSajatCegKepviselok != null) {
            sajatCegKepviselokRepository.delete(insertedSajatCegKepviselok);
            insertedSajatCegKepviselok = null;
        }
    }

    @Test
    @Transactional
    void createSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);
        var returnedSajatCegKepviselokDTO = om.readValue(
            restSajatCegKepviselokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SajatCegKepviselokDTO.class
        );

        // Validate the SajatCegKepviselok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSajatCegKepviselok = sajatCegKepviselokMapper.toEntity(returnedSajatCegKepviselokDTO);
        assertSajatCegKepviselokUpdatableFieldsEquals(
            returnedSajatCegKepviselok,
            getPersistedSajatCegKepviselok(returnedSajatCegKepviselok)
        );

        insertedSajatCegKepviselok = returnedSajatCegKepviselok;
    }

    @Test
    @Transactional
    void createSajatCegKepviselokWithExistingId() throws Exception {
        // Create the SajatCegKepviselok with an existing ID
        sajatCegKepviselok.setId(1L);
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSajatCegKepviselokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloks() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get all the sajatCegKepviselokList
        restSajatCegKepviselokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sajatCegKepviselok.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getSajatCegKepviselok() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get the sajatCegKepviselok
        restSajatCegKepviselokMockMvc
            .perform(get(ENTITY_API_URL_ID, sajatCegKepviselok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sajatCegKepviselok.getId().intValue()))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getSajatCegKepviseloksByIdFiltering() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        Long id = sajatCegKepviselok.getId();

        defaultSajatCegKepviselokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSajatCegKepviselokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSajatCegKepviselokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get all the sajatCegKepviselokList where statusz equals to
        defaultSajatCegKepviselokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get all the sajatCegKepviselokList where statusz in
        defaultSajatCegKepviselokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get all the sajatCegKepviselokList where statusz is not null
        defaultSajatCegKepviselokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get all the sajatCegKepviselokList where statusz contains
        defaultSajatCegKepviselokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        // Get all the sajatCegKepviselokList where statusz does not contain
        defaultSajatCegKepviselokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksBySajatCegIsEqualToSomething() throws Exception {
        SajatCegAlapadatok sajatCeg;
        if (TestUtil.findAll(em, SajatCegAlapadatok.class).isEmpty()) {
            sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);
            sajatCeg = SajatCegAlapadatokResourceIT.createEntity();
        } else {
            sajatCeg = TestUtil.findAll(em, SajatCegAlapadatok.class).get(0);
        }
        em.persist(sajatCeg);
        em.flush();
        sajatCegKepviselok.setSajatCeg(sajatCeg);
        sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);
        Long sajatCegId = sajatCeg.getId();
        // Get all the sajatCegKepviselokList where sajatCeg equals to sajatCegId
        defaultSajatCegKepviselokShouldBeFound("sajatCegId.equals=" + sajatCegId);

        // Get all the sajatCegKepviselokList where sajatCeg equals to (sajatCegId + 1)
        defaultSajatCegKepviselokShouldNotBeFound("sajatCegId.equals=" + (sajatCegId + 1));
    }

    @Test
    @Transactional
    void getAllSajatCegKepviseloksByMaganszemelyIsEqualToSomething() throws Exception {
        Maganszemelyek maganszemely;
        if (TestUtil.findAll(em, Maganszemelyek.class).isEmpty()) {
            sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);
            maganszemely = MaganszemelyekResourceIT.createEntity();
        } else {
            maganszemely = TestUtil.findAll(em, Maganszemelyek.class).get(0);
        }
        em.persist(maganszemely);
        em.flush();
        sajatCegKepviselok.setMaganszemely(maganszemely);
        sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);
        Long maganszemelyId = maganszemely.getId();
        // Get all the sajatCegKepviselokList where maganszemely equals to maganszemelyId
        defaultSajatCegKepviselokShouldBeFound("maganszemelyId.equals=" + maganszemelyId);

        // Get all the sajatCegKepviselokList where maganszemely equals to (maganszemelyId + 1)
        defaultSajatCegKepviselokShouldNotBeFound("maganszemelyId.equals=" + (maganszemelyId + 1));
    }

    private void defaultSajatCegKepviselokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSajatCegKepviselokShouldBeFound(shouldBeFound);
        defaultSajatCegKepviselokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSajatCegKepviselokShouldBeFound(String filter) throws Exception {
        restSajatCegKepviselokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sajatCegKepviselok.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restSajatCegKepviselokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSajatCegKepviselokShouldNotBeFound(String filter) throws Exception {
        restSajatCegKepviselokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSajatCegKepviselokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSajatCegKepviselok() throws Exception {
        // Get the sajatCegKepviselok
        restSajatCegKepviselokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSajatCegKepviselok() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegKepviselok
        SajatCegKepviselok updatedSajatCegKepviselok = sajatCegKepviselokRepository.findById(sajatCegKepviselok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSajatCegKepviselok are not directly saved in db
        em.detach(updatedSajatCegKepviselok);
        updatedSajatCegKepviselok.statusz(UPDATED_STATUSZ);
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(updatedSajatCegKepviselok);

        restSajatCegKepviselokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sajatCegKepviselokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSajatCegKepviselokToMatchAllProperties(updatedSajatCegKepviselok);
    }

    @Test
    @Transactional
    void putNonExistingSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegKepviselok.setId(longCount.incrementAndGet());

        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSajatCegKepviselokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sajatCegKepviselokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegKepviselok.setId(longCount.incrementAndGet());

        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegKepviselokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegKepviselok.setId(longCount.incrementAndGet());

        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegKepviselokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSajatCegKepviselokWithPatch() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegKepviselok using partial update
        SajatCegKepviselok partialUpdatedSajatCegKepviselok = new SajatCegKepviselok();
        partialUpdatedSajatCegKepviselok.setId(sajatCegKepviselok.getId());

        partialUpdatedSajatCegKepviselok.statusz(UPDATED_STATUSZ);

        restSajatCegKepviselokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSajatCegKepviselok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSajatCegKepviselok))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegKepviselok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSajatCegKepviselokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSajatCegKepviselok, sajatCegKepviselok),
            getPersistedSajatCegKepviselok(sajatCegKepviselok)
        );
    }

    @Test
    @Transactional
    void fullUpdateSajatCegKepviselokWithPatch() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegKepviselok using partial update
        SajatCegKepviselok partialUpdatedSajatCegKepviselok = new SajatCegKepviselok();
        partialUpdatedSajatCegKepviselok.setId(sajatCegKepviselok.getId());

        partialUpdatedSajatCegKepviselok.statusz(UPDATED_STATUSZ);

        restSajatCegKepviselokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSajatCegKepviselok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSajatCegKepviselok))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegKepviselok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSajatCegKepviselokUpdatableFieldsEquals(
            partialUpdatedSajatCegKepviselok,
            getPersistedSajatCegKepviselok(partialUpdatedSajatCegKepviselok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegKepviselok.setId(longCount.incrementAndGet());

        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSajatCegKepviselokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sajatCegKepviselokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegKepviselok.setId(longCount.incrementAndGet());

        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegKepviselokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSajatCegKepviselok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegKepviselok.setId(longCount.incrementAndGet());

        // Create the SajatCegKepviselok
        SajatCegKepviselokDTO sajatCegKepviselokDTO = sajatCegKepviselokMapper.toDto(sajatCegKepviselok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegKepviselokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegKepviselokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SajatCegKepviselok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSajatCegKepviselok() throws Exception {
        // Initialize the database
        insertedSajatCegKepviselok = sajatCegKepviselokRepository.saveAndFlush(sajatCegKepviselok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sajatCegKepviselok
        restSajatCegKepviselokMockMvc
            .perform(delete(ENTITY_API_URL_ID, sajatCegKepviselok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sajatCegKepviselokRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SajatCegKepviselok getPersistedSajatCegKepviselok(SajatCegKepviselok sajatCegKepviselok) {
        return sajatCegKepviselokRepository.findById(sajatCegKepviselok.getId()).orElseThrow();
    }

    protected void assertPersistedSajatCegKepviselokToMatchAllProperties(SajatCegKepviselok expectedSajatCegKepviselok) {
        assertSajatCegKepviselokAllPropertiesEquals(expectedSajatCegKepviselok, getPersistedSajatCegKepviselok(expectedSajatCegKepviselok));
    }

    protected void assertPersistedSajatCegKepviselokToMatchUpdatableProperties(SajatCegKepviselok expectedSajatCegKepviselok) {
        assertSajatCegKepviselokAllUpdatablePropertiesEquals(
            expectedSajatCegKepviselok,
            getPersistedSajatCegKepviselok(expectedSajatCegKepviselok)
        );
    }
}

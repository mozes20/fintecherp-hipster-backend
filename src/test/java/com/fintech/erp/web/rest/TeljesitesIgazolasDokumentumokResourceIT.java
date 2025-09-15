package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.TeljesitesIgazolasDokumentumokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.TeljesitesIgazolasDokumentumok;
import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.repository.TeljesitesIgazolasDokumentumokRepository;
import com.fintech.erp.service.dto.TeljesitesIgazolasDokumentumokDTO;
import com.fintech.erp.service.mapper.TeljesitesIgazolasDokumentumokMapper;
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
 * Integration tests for the {@link TeljesitesIgazolasDokumentumokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeljesitesIgazolasDokumentumokResourceIT {

    private static final String DEFAULT_DOKUMENTUM_TIPUSA = "AAAAAAAAAA";
    private static final String UPDATED_DOKUMENTUM_TIPUSA = "BBBBBBBBBB";

    private static final String DEFAULT_DOKUMENTUM = "AAAAAAAAAA";
    private static final String UPDATED_DOKUMENTUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/teljesites-igazolas-dokumentumoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TeljesitesIgazolasDokumentumokRepository teljesitesIgazolasDokumentumokRepository;

    @Autowired
    private TeljesitesIgazolasDokumentumokMapper teljesitesIgazolasDokumentumokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeljesitesIgazolasDokumentumokMockMvc;

    private TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok;

    private TeljesitesIgazolasDokumentumok insertedTeljesitesIgazolasDokumentumok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeljesitesIgazolasDokumentumok createEntity() {
        return new TeljesitesIgazolasDokumentumok().dokumentumTipusa(DEFAULT_DOKUMENTUM_TIPUSA).dokumentum(DEFAULT_DOKUMENTUM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeljesitesIgazolasDokumentumok createUpdatedEntity() {
        return new TeljesitesIgazolasDokumentumok().dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA).dokumentum(UPDATED_DOKUMENTUM);
    }

    @BeforeEach
    void initTest() {
        teljesitesIgazolasDokumentumok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTeljesitesIgazolasDokumentumok != null) {
            teljesitesIgazolasDokumentumokRepository.delete(insertedTeljesitesIgazolasDokumentumok);
            insertedTeljesitesIgazolasDokumentumok = null;
        }
    }

    @Test
    @Transactional
    void createTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );
        var returnedTeljesitesIgazolasDokumentumokDTO = om.readValue(
            restTeljesitesIgazolasDokumentumokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TeljesitesIgazolasDokumentumokDTO.class
        );

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokMapper.toEntity(
            returnedTeljesitesIgazolasDokumentumokDTO
        );
        assertTeljesitesIgazolasDokumentumokUpdatableFieldsEquals(
            returnedTeljesitesIgazolasDokumentumok,
            getPersistedTeljesitesIgazolasDokumentumok(returnedTeljesitesIgazolasDokumentumok)
        );

        insertedTeljesitesIgazolasDokumentumok = returnedTeljesitesIgazolasDokumentumok;
    }

    @Test
    @Transactional
    void createTeljesitesIgazolasDokumentumokWithExistingId() throws Exception {
        // Create the TeljesitesIgazolasDokumentumok with an existing ID
        teljesitesIgazolasDokumentumok.setId(1L);
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoks() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teljesitesIgazolasDokumentumok.getId().intValue())))
            .andExpect(jsonPath("$.[*].dokumentumTipusa").value(hasItem(DEFAULT_DOKUMENTUM_TIPUSA)))
            .andExpect(jsonPath("$.[*].dokumentum").value(hasItem(DEFAULT_DOKUMENTUM)));
    }

    @Test
    @Transactional
    void getTeljesitesIgazolasDokumentumok() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get the teljesitesIgazolasDokumentumok
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(get(ENTITY_API_URL_ID, teljesitesIgazolasDokumentumok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teljesitesIgazolasDokumentumok.getId().intValue()))
            .andExpect(jsonPath("$.dokumentumTipusa").value(DEFAULT_DOKUMENTUM_TIPUSA))
            .andExpect(jsonPath("$.dokumentum").value(DEFAULT_DOKUMENTUM));
    }

    @Test
    @Transactional
    void getTeljesitesIgazolasDokumentumoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        Long id = teljesitesIgazolasDokumentumok.getId();

        defaultTeljesitesIgazolasDokumentumokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTeljesitesIgazolasDokumentumokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTeljesitesIgazolasDokumentumokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumTipusaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentumTipusa equals to
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentumTipusa.equals=" + DEFAULT_DOKUMENTUM_TIPUSA,
            "dokumentumTipusa.equals=" + UPDATED_DOKUMENTUM_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumTipusaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentumTipusa in
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentumTipusa.in=" + DEFAULT_DOKUMENTUM_TIPUSA + "," + UPDATED_DOKUMENTUM_TIPUSA,
            "dokumentumTipusa.in=" + UPDATED_DOKUMENTUM_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumTipusaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentumTipusa is not null
        defaultTeljesitesIgazolasDokumentumokFiltering("dokumentumTipusa.specified=true", "dokumentumTipusa.specified=false");
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumTipusaContainsSomething() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentumTipusa contains
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentumTipusa.contains=" + DEFAULT_DOKUMENTUM_TIPUSA,
            "dokumentumTipusa.contains=" + UPDATED_DOKUMENTUM_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumTipusaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentumTipusa does not contain
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentumTipusa.doesNotContain=" + UPDATED_DOKUMENTUM_TIPUSA,
            "dokumentumTipusa.doesNotContain=" + DEFAULT_DOKUMENTUM_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentum equals to
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentum.equals=" + DEFAULT_DOKUMENTUM,
            "dokumentum.equals=" + UPDATED_DOKUMENTUM
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentum in
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentum.in=" + DEFAULT_DOKUMENTUM + "," + UPDATED_DOKUMENTUM,
            "dokumentum.in=" + UPDATED_DOKUMENTUM
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentum is not null
        defaultTeljesitesIgazolasDokumentumokFiltering("dokumentum.specified=true", "dokumentum.specified=false");
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumContainsSomething() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentum contains
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentum.contains=" + DEFAULT_DOKUMENTUM,
            "dokumentum.contains=" + UPDATED_DOKUMENTUM
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByDokumentumNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        // Get all the teljesitesIgazolasDokumentumokList where dokumentum does not contain
        defaultTeljesitesIgazolasDokumentumokFiltering(
            "dokumentum.doesNotContain=" + UPDATED_DOKUMENTUM,
            "dokumentum.doesNotContain=" + DEFAULT_DOKUMENTUM
        );
    }

    @Test
    @Transactional
    void getAllTeljesitesIgazolasDokumentumoksByTeljesitesIgazolasIsEqualToSomething() throws Exception {
        UgyfelElszamolasok teljesitesIgazolas;
        if (TestUtil.findAll(em, UgyfelElszamolasok.class).isEmpty()) {
            teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);
            teljesitesIgazolas = UgyfelElszamolasokResourceIT.createEntity();
        } else {
            teljesitesIgazolas = TestUtil.findAll(em, UgyfelElszamolasok.class).get(0);
        }
        em.persist(teljesitesIgazolas);
        em.flush();
        teljesitesIgazolasDokumentumok.setTeljesitesIgazolas(teljesitesIgazolas);
        teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);
        Long teljesitesIgazolasId = teljesitesIgazolas.getId();
        // Get all the teljesitesIgazolasDokumentumokList where teljesitesIgazolas equals to teljesitesIgazolasId
        defaultTeljesitesIgazolasDokumentumokShouldBeFound("teljesitesIgazolasId.equals=" + teljesitesIgazolasId);

        // Get all the teljesitesIgazolasDokumentumokList where teljesitesIgazolas equals to (teljesitesIgazolasId + 1)
        defaultTeljesitesIgazolasDokumentumokShouldNotBeFound("teljesitesIgazolasId.equals=" + (teljesitesIgazolasId + 1));
    }

    private void defaultTeljesitesIgazolasDokumentumokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTeljesitesIgazolasDokumentumokShouldBeFound(shouldBeFound);
        defaultTeljesitesIgazolasDokumentumokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeljesitesIgazolasDokumentumokShouldBeFound(String filter) throws Exception {
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teljesitesIgazolasDokumentumok.getId().intValue())))
            .andExpect(jsonPath("$.[*].dokumentumTipusa").value(hasItem(DEFAULT_DOKUMENTUM_TIPUSA)))
            .andExpect(jsonPath("$.[*].dokumentum").value(hasItem(DEFAULT_DOKUMENTUM)));

        // Check, that the count call also returns 1
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeljesitesIgazolasDokumentumokShouldNotBeFound(String filter) throws Exception {
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeljesitesIgazolasDokumentumok() throws Exception {
        // Get the teljesitesIgazolasDokumentumok
        restTeljesitesIgazolasDokumentumokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeljesitesIgazolasDokumentumok() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the teljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumok updatedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository
            .findById(teljesitesIgazolasDokumentumok.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTeljesitesIgazolasDokumentumok are not directly saved in db
        em.detach(updatedTeljesitesIgazolasDokumentumok);
        updatedTeljesitesIgazolasDokumentumok.dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA).dokumentum(UPDATED_DOKUMENTUM);
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            updatedTeljesitesIgazolasDokumentumok
        );

        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teljesitesIgazolasDokumentumokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTeljesitesIgazolasDokumentumokToMatchAllProperties(updatedTeljesitesIgazolasDokumentumok);
    }

    @Test
    @Transactional
    void putNonExistingTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teljesitesIgazolasDokumentumok.setId(longCount.incrementAndGet());

        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teljesitesIgazolasDokumentumokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teljesitesIgazolasDokumentumok.setId(longCount.incrementAndGet());

        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teljesitesIgazolasDokumentumok.setId(longCount.incrementAndGet());

        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeljesitesIgazolasDokumentumokWithPatch() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the teljesitesIgazolasDokumentumok using partial update
        TeljesitesIgazolasDokumentumok partialUpdatedTeljesitesIgazolasDokumentumok = new TeljesitesIgazolasDokumentumok();
        partialUpdatedTeljesitesIgazolasDokumentumok.setId(teljesitesIgazolasDokumentumok.getId());

        partialUpdatedTeljesitesIgazolasDokumentumok.dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA).dokumentum(UPDATED_DOKUMENTUM);

        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeljesitesIgazolasDokumentumok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTeljesitesIgazolasDokumentumok))
            )
            .andExpect(status().isOk());

        // Validate the TeljesitesIgazolasDokumentumok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTeljesitesIgazolasDokumentumokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTeljesitesIgazolasDokumentumok, teljesitesIgazolasDokumentumok),
            getPersistedTeljesitesIgazolasDokumentumok(teljesitesIgazolasDokumentumok)
        );
    }

    @Test
    @Transactional
    void fullUpdateTeljesitesIgazolasDokumentumokWithPatch() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the teljesitesIgazolasDokumentumok using partial update
        TeljesitesIgazolasDokumentumok partialUpdatedTeljesitesIgazolasDokumentumok = new TeljesitesIgazolasDokumentumok();
        partialUpdatedTeljesitesIgazolasDokumentumok.setId(teljesitesIgazolasDokumentumok.getId());

        partialUpdatedTeljesitesIgazolasDokumentumok.dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA).dokumentum(UPDATED_DOKUMENTUM);

        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeljesitesIgazolasDokumentumok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTeljesitesIgazolasDokumentumok))
            )
            .andExpect(status().isOk());

        // Validate the TeljesitesIgazolasDokumentumok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTeljesitesIgazolasDokumentumokUpdatableFieldsEquals(
            partialUpdatedTeljesitesIgazolasDokumentumok,
            getPersistedTeljesitesIgazolasDokumentumok(partialUpdatedTeljesitesIgazolasDokumentumok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teljesitesIgazolasDokumentumok.setId(longCount.incrementAndGet());

        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teljesitesIgazolasDokumentumokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teljesitesIgazolasDokumentumok.setId(longCount.incrementAndGet());

        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeljesitesIgazolasDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        teljesitesIgazolasDokumentumok.setId(longCount.incrementAndGet());

        // Create the TeljesitesIgazolasDokumentumok
        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = teljesitesIgazolasDokumentumokMapper.toDto(
            teljesitesIgazolasDokumentumok
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(teljesitesIgazolasDokumentumokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeljesitesIgazolasDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeljesitesIgazolasDokumentumok() throws Exception {
        // Initialize the database
        insertedTeljesitesIgazolasDokumentumok = teljesitesIgazolasDokumentumokRepository.saveAndFlush(teljesitesIgazolasDokumentumok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the teljesitesIgazolasDokumentumok
        restTeljesitesIgazolasDokumentumokMockMvc
            .perform(delete(ENTITY_API_URL_ID, teljesitesIgazolasDokumentumok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return teljesitesIgazolasDokumentumokRepository.count();
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

    protected TeljesitesIgazolasDokumentumok getPersistedTeljesitesIgazolasDokumentumok(
        TeljesitesIgazolasDokumentumok teljesitesIgazolasDokumentumok
    ) {
        return teljesitesIgazolasDokumentumokRepository.findById(teljesitesIgazolasDokumentumok.getId()).orElseThrow();
    }

    protected void assertPersistedTeljesitesIgazolasDokumentumokToMatchAllProperties(
        TeljesitesIgazolasDokumentumok expectedTeljesitesIgazolasDokumentumok
    ) {
        assertTeljesitesIgazolasDokumentumokAllPropertiesEquals(
            expectedTeljesitesIgazolasDokumentumok,
            getPersistedTeljesitesIgazolasDokumentumok(expectedTeljesitesIgazolasDokumentumok)
        );
    }

    protected void assertPersistedTeljesitesIgazolasDokumentumokToMatchUpdatableProperties(
        TeljesitesIgazolasDokumentumok expectedTeljesitesIgazolasDokumentumok
    ) {
        assertTeljesitesIgazolasDokumentumokAllUpdatablePropertiesEquals(
            expectedTeljesitesIgazolasDokumentumok,
            getPersistedTeljesitesIgazolasDokumentumok(expectedTeljesitesIgazolasDokumentumok)
        );
    }
}

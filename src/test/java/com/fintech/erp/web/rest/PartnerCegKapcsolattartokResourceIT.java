package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.PartnerCegKapcsolattartokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.domain.PartnerCegKapcsolattartok;
import com.fintech.erp.repository.PartnerCegKapcsolattartokRepository;
import com.fintech.erp.service.dto.PartnerCegKapcsolattartokDTO;
import com.fintech.erp.service.mapper.PartnerCegKapcsolattartokMapper;
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
 * Integration tests for the {@link PartnerCegKapcsolattartokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartnerCegKapcsolattartokResourceIT {

    private static final String DEFAULT_KAPCSOLATTARTO_TITULUS = "AAAAAAAAAA";
    private static final String UPDATED_KAPCSOLATTARTO_TITULUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partner-ceg-kapcsolattartoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartnerCegKapcsolattartokRepository partnerCegKapcsolattartokRepository;

    @Autowired
    private PartnerCegKapcsolattartokMapper partnerCegKapcsolattartokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnerCegKapcsolattartokMockMvc;

    private PartnerCegKapcsolattartok partnerCegKapcsolattartok;

    private PartnerCegKapcsolattartok insertedPartnerCegKapcsolattartok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerCegKapcsolattartok createEntity() {
        return new PartnerCegKapcsolattartok().kapcsolattartoTitulus(DEFAULT_KAPCSOLATTARTO_TITULUS).statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerCegKapcsolattartok createUpdatedEntity() {
        return new PartnerCegKapcsolattartok().kapcsolattartoTitulus(UPDATED_KAPCSOLATTARTO_TITULUS).statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        partnerCegKapcsolattartok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPartnerCegKapcsolattartok != null) {
            partnerCegKapcsolattartokRepository.delete(insertedPartnerCegKapcsolattartok);
            insertedPartnerCegKapcsolattartok = null;
        }
    }

    @Test
    @Transactional
    void createPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);
        var returnedPartnerCegKapcsolattartokDTO = om.readValue(
            restPartnerCegKapcsolattartokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartnerCegKapcsolattartokDTO.class
        );

        // Validate the PartnerCegKapcsolattartok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartnerCegKapcsolattartok = partnerCegKapcsolattartokMapper.toEntity(returnedPartnerCegKapcsolattartokDTO);
        assertPartnerCegKapcsolattartokUpdatableFieldsEquals(
            returnedPartnerCegKapcsolattartok,
            getPersistedPartnerCegKapcsolattartok(returnedPartnerCegKapcsolattartok)
        );

        insertedPartnerCegKapcsolattartok = returnedPartnerCegKapcsolattartok;
    }

    @Test
    @Transactional
    void createPartnerCegKapcsolattartokWithExistingId() throws Exception {
        // Create the PartnerCegKapcsolattartok with an existing ID
        partnerCegKapcsolattartok.setId(1L);
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoks() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList
        restPartnerCegKapcsolattartokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerCegKapcsolattartok.getId().intValue())))
            .andExpect(jsonPath("$.[*].kapcsolattartoTitulus").value(hasItem(DEFAULT_KAPCSOLATTARTO_TITULUS)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getPartnerCegKapcsolattartok() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get the partnerCegKapcsolattartok
        restPartnerCegKapcsolattartokMockMvc
            .perform(get(ENTITY_API_URL_ID, partnerCegKapcsolattartok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partnerCegKapcsolattartok.getId().intValue()))
            .andExpect(jsonPath("$.kapcsolattartoTitulus").value(DEFAULT_KAPCSOLATTARTO_TITULUS))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getPartnerCegKapcsolattartoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        Long id = partnerCegKapcsolattartok.getId();

        defaultPartnerCegKapcsolattartokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPartnerCegKapcsolattartokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPartnerCegKapcsolattartokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByKapcsolattartoTitulusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where kapcsolattartoTitulus equals to
        defaultPartnerCegKapcsolattartokFiltering(
            "kapcsolattartoTitulus.equals=" + DEFAULT_KAPCSOLATTARTO_TITULUS,
            "kapcsolattartoTitulus.equals=" + UPDATED_KAPCSOLATTARTO_TITULUS
        );
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByKapcsolattartoTitulusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where kapcsolattartoTitulus in
        defaultPartnerCegKapcsolattartokFiltering(
            "kapcsolattartoTitulus.in=" + DEFAULT_KAPCSOLATTARTO_TITULUS + "," + UPDATED_KAPCSOLATTARTO_TITULUS,
            "kapcsolattartoTitulus.in=" + UPDATED_KAPCSOLATTARTO_TITULUS
        );
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByKapcsolattartoTitulusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where kapcsolattartoTitulus is not null
        defaultPartnerCegKapcsolattartokFiltering("kapcsolattartoTitulus.specified=true", "kapcsolattartoTitulus.specified=false");
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByKapcsolattartoTitulusContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where kapcsolattartoTitulus contains
        defaultPartnerCegKapcsolattartokFiltering(
            "kapcsolattartoTitulus.contains=" + DEFAULT_KAPCSOLATTARTO_TITULUS,
            "kapcsolattartoTitulus.contains=" + UPDATED_KAPCSOLATTARTO_TITULUS
        );
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByKapcsolattartoTitulusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where kapcsolattartoTitulus does not contain
        defaultPartnerCegKapcsolattartokFiltering(
            "kapcsolattartoTitulus.doesNotContain=" + UPDATED_KAPCSOLATTARTO_TITULUS,
            "kapcsolattartoTitulus.doesNotContain=" + DEFAULT_KAPCSOLATTARTO_TITULUS
        );
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where statusz equals to
        defaultPartnerCegKapcsolattartokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where statusz in
        defaultPartnerCegKapcsolattartokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where statusz is not null
        defaultPartnerCegKapcsolattartokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where statusz contains
        defaultPartnerCegKapcsolattartokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        // Get all the partnerCegKapcsolattartokList where statusz does not contain
        defaultPartnerCegKapcsolattartokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByPartnerCegIsEqualToSomething() throws Exception {
        PartnerCegAdatok partnerCeg;
        if (TestUtil.findAll(em, PartnerCegAdatok.class).isEmpty()) {
            partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);
            partnerCeg = PartnerCegAdatokResourceIT.createEntity();
        } else {
            partnerCeg = TestUtil.findAll(em, PartnerCegAdatok.class).get(0);
        }
        em.persist(partnerCeg);
        em.flush();
        partnerCegKapcsolattartok.setPartnerCeg(partnerCeg);
        partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);
        Long partnerCegId = partnerCeg.getId();
        // Get all the partnerCegKapcsolattartokList where partnerCeg equals to partnerCegId
        defaultPartnerCegKapcsolattartokShouldBeFound("partnerCegId.equals=" + partnerCegId);

        // Get all the partnerCegKapcsolattartokList where partnerCeg equals to (partnerCegId + 1)
        defaultPartnerCegKapcsolattartokShouldNotBeFound("partnerCegId.equals=" + (partnerCegId + 1));
    }

    @Test
    @Transactional
    void getAllPartnerCegKapcsolattartoksByMaganszemelyIsEqualToSomething() throws Exception {
        Maganszemelyek maganszemely;
        if (TestUtil.findAll(em, Maganszemelyek.class).isEmpty()) {
            partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);
            maganszemely = MaganszemelyekResourceIT.createEntity();
        } else {
            maganszemely = TestUtil.findAll(em, Maganszemelyek.class).get(0);
        }
        em.persist(maganszemely);
        em.flush();
        partnerCegKapcsolattartok.setMaganszemely(maganszemely);
        partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);
        Long maganszemelyId = maganszemely.getId();
        // Get all the partnerCegKapcsolattartokList where maganszemely equals to maganszemelyId
        defaultPartnerCegKapcsolattartokShouldBeFound("maganszemelyId.equals=" + maganszemelyId);

        // Get all the partnerCegKapcsolattartokList where maganszemely equals to (maganszemelyId + 1)
        defaultPartnerCegKapcsolattartokShouldNotBeFound("maganszemelyId.equals=" + (maganszemelyId + 1));
    }

    private void defaultPartnerCegKapcsolattartokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPartnerCegKapcsolattartokShouldBeFound(shouldBeFound);
        defaultPartnerCegKapcsolattartokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartnerCegKapcsolattartokShouldBeFound(String filter) throws Exception {
        restPartnerCegKapcsolattartokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerCegKapcsolattartok.getId().intValue())))
            .andExpect(jsonPath("$.[*].kapcsolattartoTitulus").value(hasItem(DEFAULT_KAPCSOLATTARTO_TITULUS)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restPartnerCegKapcsolattartokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartnerCegKapcsolattartokShouldNotBeFound(String filter) throws Exception {
        restPartnerCegKapcsolattartokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartnerCegKapcsolattartokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPartnerCegKapcsolattartok() throws Exception {
        // Get the partnerCegKapcsolattartok
        restPartnerCegKapcsolattartokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartnerCegKapcsolattartok() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegKapcsolattartok
        PartnerCegKapcsolattartok updatedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository
            .findById(partnerCegKapcsolattartok.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPartnerCegKapcsolattartok are not directly saved in db
        em.detach(updatedPartnerCegKapcsolattartok);
        updatedPartnerCegKapcsolattartok.kapcsolattartoTitulus(UPDATED_KAPCSOLATTARTO_TITULUS).statusz(UPDATED_STATUSZ);
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(updatedPartnerCegKapcsolattartok);

        restPartnerCegKapcsolattartokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerCegKapcsolattartokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartnerCegKapcsolattartokToMatchAllProperties(updatedPartnerCegKapcsolattartok);
    }

    @Test
    @Transactional
    void putNonExistingPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegKapcsolattartok.setId(longCount.incrementAndGet());

        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerCegKapcsolattartokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegKapcsolattartok.setId(longCount.incrementAndGet());

        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegKapcsolattartok.setId(longCount.incrementAndGet());

        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartnerCegKapcsolattartokWithPatch() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegKapcsolattartok using partial update
        PartnerCegKapcsolattartok partialUpdatedPartnerCegKapcsolattartok = new PartnerCegKapcsolattartok();
        partialUpdatedPartnerCegKapcsolattartok.setId(partnerCegKapcsolattartok.getId());

        restPartnerCegKapcsolattartokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerCegKapcsolattartok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartnerCegKapcsolattartok))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegKapcsolattartok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartnerCegKapcsolattartokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPartnerCegKapcsolattartok, partnerCegKapcsolattartok),
            getPersistedPartnerCegKapcsolattartok(partnerCegKapcsolattartok)
        );
    }

    @Test
    @Transactional
    void fullUpdatePartnerCegKapcsolattartokWithPatch() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegKapcsolattartok using partial update
        PartnerCegKapcsolattartok partialUpdatedPartnerCegKapcsolattartok = new PartnerCegKapcsolattartok();
        partialUpdatedPartnerCegKapcsolattartok.setId(partnerCegKapcsolattartok.getId());

        partialUpdatedPartnerCegKapcsolattartok.kapcsolattartoTitulus(UPDATED_KAPCSOLATTARTO_TITULUS).statusz(UPDATED_STATUSZ);

        restPartnerCegKapcsolattartokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerCegKapcsolattartok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartnerCegKapcsolattartok))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegKapcsolattartok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartnerCegKapcsolattartokUpdatableFieldsEquals(
            partialUpdatedPartnerCegKapcsolattartok,
            getPersistedPartnerCegKapcsolattartok(partialUpdatedPartnerCegKapcsolattartok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegKapcsolattartok.setId(longCount.incrementAndGet());

        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partnerCegKapcsolattartokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegKapcsolattartok.setId(longCount.incrementAndGet());

        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartnerCegKapcsolattartok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegKapcsolattartok.setId(longCount.incrementAndGet());

        // Create the PartnerCegKapcsolattartok
        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = partnerCegKapcsolattartokMapper.toDto(partnerCegKapcsolattartok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegKapcsolattartokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegKapcsolattartokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerCegKapcsolattartok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartnerCegKapcsolattartok() throws Exception {
        // Initialize the database
        insertedPartnerCegKapcsolattartok = partnerCegKapcsolattartokRepository.saveAndFlush(partnerCegKapcsolattartok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partnerCegKapcsolattartok
        restPartnerCegKapcsolattartokMockMvc
            .perform(delete(ENTITY_API_URL_ID, partnerCegKapcsolattartok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partnerCegKapcsolattartokRepository.count();
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

    protected PartnerCegKapcsolattartok getPersistedPartnerCegKapcsolattartok(PartnerCegKapcsolattartok partnerCegKapcsolattartok) {
        return partnerCegKapcsolattartokRepository.findById(partnerCegKapcsolattartok.getId()).orElseThrow();
    }

    protected void assertPersistedPartnerCegKapcsolattartokToMatchAllProperties(
        PartnerCegKapcsolattartok expectedPartnerCegKapcsolattartok
    ) {
        assertPartnerCegKapcsolattartokAllPropertiesEquals(
            expectedPartnerCegKapcsolattartok,
            getPersistedPartnerCegKapcsolattartok(expectedPartnerCegKapcsolattartok)
        );
    }

    protected void assertPersistedPartnerCegKapcsolattartokToMatchUpdatableProperties(
        PartnerCegKapcsolattartok expectedPartnerCegKapcsolattartok
    ) {
        assertPartnerCegKapcsolattartokAllUpdatablePropertiesEquals(
            expectedPartnerCegKapcsolattartok,
            getPersistedPartnerCegKapcsolattartok(expectedPartnerCegKapcsolattartok)
        );
    }
}

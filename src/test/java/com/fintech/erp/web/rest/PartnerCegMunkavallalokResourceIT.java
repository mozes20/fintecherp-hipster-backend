package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.PartnerCegMunkavallalokAsserts.*;
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
import com.fintech.erp.domain.PartnerCegMunkavallalok;
import com.fintech.erp.repository.PartnerCegMunkavallalokRepository;
import com.fintech.erp.service.dto.PartnerCegMunkavallalokDTO;
import com.fintech.erp.service.mapper.PartnerCegMunkavallalokMapper;
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
 * Integration tests for the {@link PartnerCegMunkavallalokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartnerCegMunkavallalokResourceIT {

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partner-ceg-munkavallaloks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartnerCegMunkavallalokRepository partnerCegMunkavallalokRepository;

    @Autowired
    private PartnerCegMunkavallalokMapper partnerCegMunkavallalokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnerCegMunkavallalokMockMvc;

    private PartnerCegMunkavallalok partnerCegMunkavallalok;

    private PartnerCegMunkavallalok insertedPartnerCegMunkavallalok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerCegMunkavallalok createEntity() {
        return new PartnerCegMunkavallalok().statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerCegMunkavallalok createUpdatedEntity() {
        return new PartnerCegMunkavallalok().statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        partnerCegMunkavallalok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPartnerCegMunkavallalok != null) {
            partnerCegMunkavallalokRepository.delete(insertedPartnerCegMunkavallalok);
            insertedPartnerCegMunkavallalok = null;
        }
    }

    @Test
    @Transactional
    void createPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);
        var returnedPartnerCegMunkavallalokDTO = om.readValue(
            restPartnerCegMunkavallalokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartnerCegMunkavallalokDTO.class
        );

        // Validate the PartnerCegMunkavallalok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartnerCegMunkavallalok = partnerCegMunkavallalokMapper.toEntity(returnedPartnerCegMunkavallalokDTO);
        assertPartnerCegMunkavallalokUpdatableFieldsEquals(
            returnedPartnerCegMunkavallalok,
            getPersistedPartnerCegMunkavallalok(returnedPartnerCegMunkavallalok)
        );

        insertedPartnerCegMunkavallalok = returnedPartnerCegMunkavallalok;
    }

    @Test
    @Transactional
    void createPartnerCegMunkavallalokWithExistingId() throws Exception {
        // Create the PartnerCegMunkavallalok with an existing ID
        partnerCegMunkavallalok.setId(1L);
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerCegMunkavallalokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloks() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get all the partnerCegMunkavallalokList
        restPartnerCegMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerCegMunkavallalok.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getPartnerCegMunkavallalok() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get the partnerCegMunkavallalok
        restPartnerCegMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL_ID, partnerCegMunkavallalok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partnerCegMunkavallalok.getId().intValue()))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getPartnerCegMunkavallaloksByIdFiltering() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        Long id = partnerCegMunkavallalok.getId();

        defaultPartnerCegMunkavallalokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPartnerCegMunkavallalokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPartnerCegMunkavallalokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get all the partnerCegMunkavallalokList where statusz equals to
        defaultPartnerCegMunkavallalokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get all the partnerCegMunkavallalokList where statusz in
        defaultPartnerCegMunkavallalokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get all the partnerCegMunkavallalokList where statusz is not null
        defaultPartnerCegMunkavallalokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get all the partnerCegMunkavallalokList where statusz contains
        defaultPartnerCegMunkavallalokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        // Get all the partnerCegMunkavallalokList where statusz does not contain
        defaultPartnerCegMunkavallalokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByPartnerCegIsEqualToSomething() throws Exception {
        PartnerCegAdatok partnerCeg;
        if (TestUtil.findAll(em, PartnerCegAdatok.class).isEmpty()) {
            partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);
            partnerCeg = PartnerCegAdatokResourceIT.createEntity();
        } else {
            partnerCeg = TestUtil.findAll(em, PartnerCegAdatok.class).get(0);
        }
        em.persist(partnerCeg);
        em.flush();
        partnerCegMunkavallalok.setPartnerCeg(partnerCeg);
        partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);
        Long partnerCegId = partnerCeg.getId();
        // Get all the partnerCegMunkavallalokList where partnerCeg equals to partnerCegId
        defaultPartnerCegMunkavallalokShouldBeFound("partnerCegId.equals=" + partnerCegId);

        // Get all the partnerCegMunkavallalokList where partnerCeg equals to (partnerCegId + 1)
        defaultPartnerCegMunkavallalokShouldNotBeFound("partnerCegId.equals=" + (partnerCegId + 1));
    }

    @Test
    @Transactional
    void getAllPartnerCegMunkavallaloksByMaganszemelyIsEqualToSomething() throws Exception {
        Maganszemelyek maganszemely;
        if (TestUtil.findAll(em, Maganszemelyek.class).isEmpty()) {
            partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);
            maganszemely = MaganszemelyekResourceIT.createEntity();
        } else {
            maganszemely = TestUtil.findAll(em, Maganszemelyek.class).get(0);
        }
        em.persist(maganszemely);
        em.flush();
        partnerCegMunkavallalok.setMaganszemely(maganszemely);
        partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);
        Long maganszemelyId = maganszemely.getId();
        // Get all the partnerCegMunkavallalokList where maganszemely equals to maganszemelyId
        defaultPartnerCegMunkavallalokShouldBeFound("maganszemelyId.equals=" + maganszemelyId);

        // Get all the partnerCegMunkavallalokList where maganszemely equals to (maganszemelyId + 1)
        defaultPartnerCegMunkavallalokShouldNotBeFound("maganszemelyId.equals=" + (maganszemelyId + 1));
    }

    private void defaultPartnerCegMunkavallalokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPartnerCegMunkavallalokShouldBeFound(shouldBeFound);
        defaultPartnerCegMunkavallalokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartnerCegMunkavallalokShouldBeFound(String filter) throws Exception {
        restPartnerCegMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerCegMunkavallalok.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restPartnerCegMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartnerCegMunkavallalokShouldNotBeFound(String filter) throws Exception {
        restPartnerCegMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartnerCegMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPartnerCegMunkavallalok() throws Exception {
        // Get the partnerCegMunkavallalok
        restPartnerCegMunkavallalokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartnerCegMunkavallalok() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegMunkavallalok
        PartnerCegMunkavallalok updatedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository
            .findById(partnerCegMunkavallalok.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPartnerCegMunkavallalok are not directly saved in db
        em.detach(updatedPartnerCegMunkavallalok);
        updatedPartnerCegMunkavallalok.statusz(UPDATED_STATUSZ);
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(updatedPartnerCegMunkavallalok);

        restPartnerCegMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerCegMunkavallalokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartnerCegMunkavallalokToMatchAllProperties(updatedPartnerCegMunkavallalok);
    }

    @Test
    @Transactional
    void putNonExistingPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegMunkavallalok.setId(longCount.incrementAndGet());

        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerCegMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerCegMunkavallalokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegMunkavallalok.setId(longCount.incrementAndGet());

        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegMunkavallalok.setId(longCount.incrementAndGet());

        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartnerCegMunkavallalokWithPatch() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegMunkavallalok using partial update
        PartnerCegMunkavallalok partialUpdatedPartnerCegMunkavallalok = new PartnerCegMunkavallalok();
        partialUpdatedPartnerCegMunkavallalok.setId(partnerCegMunkavallalok.getId());

        restPartnerCegMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerCegMunkavallalok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartnerCegMunkavallalok))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegMunkavallalok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartnerCegMunkavallalokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPartnerCegMunkavallalok, partnerCegMunkavallalok),
            getPersistedPartnerCegMunkavallalok(partnerCegMunkavallalok)
        );
    }

    @Test
    @Transactional
    void fullUpdatePartnerCegMunkavallalokWithPatch() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegMunkavallalok using partial update
        PartnerCegMunkavallalok partialUpdatedPartnerCegMunkavallalok = new PartnerCegMunkavallalok();
        partialUpdatedPartnerCegMunkavallalok.setId(partnerCegMunkavallalok.getId());

        partialUpdatedPartnerCegMunkavallalok.statusz(UPDATED_STATUSZ);

        restPartnerCegMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerCegMunkavallalok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartnerCegMunkavallalok))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegMunkavallalok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartnerCegMunkavallalokUpdatableFieldsEquals(
            partialUpdatedPartnerCegMunkavallalok,
            getPersistedPartnerCegMunkavallalok(partialUpdatedPartnerCegMunkavallalok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegMunkavallalok.setId(longCount.incrementAndGet());

        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerCegMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partnerCegMunkavallalokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegMunkavallalok.setId(longCount.incrementAndGet());

        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartnerCegMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegMunkavallalok.setId(longCount.incrementAndGet());

        // Create the PartnerCegMunkavallalok
        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = partnerCegMunkavallalokMapper.toDto(partnerCegMunkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegMunkavallalokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerCegMunkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartnerCegMunkavallalok() throws Exception {
        // Initialize the database
        insertedPartnerCegMunkavallalok = partnerCegMunkavallalokRepository.saveAndFlush(partnerCegMunkavallalok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partnerCegMunkavallalok
        restPartnerCegMunkavallalokMockMvc
            .perform(delete(ENTITY_API_URL_ID, partnerCegMunkavallalok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partnerCegMunkavallalokRepository.count();
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

    protected PartnerCegMunkavallalok getPersistedPartnerCegMunkavallalok(PartnerCegMunkavallalok partnerCegMunkavallalok) {
        return partnerCegMunkavallalokRepository.findById(partnerCegMunkavallalok.getId()).orElseThrow();
    }

    protected void assertPersistedPartnerCegMunkavallalokToMatchAllProperties(PartnerCegMunkavallalok expectedPartnerCegMunkavallalok) {
        assertPartnerCegMunkavallalokAllPropertiesEquals(
            expectedPartnerCegMunkavallalok,
            getPersistedPartnerCegMunkavallalok(expectedPartnerCegMunkavallalok)
        );
    }

    protected void assertPersistedPartnerCegMunkavallalokToMatchUpdatableProperties(
        PartnerCegMunkavallalok expectedPartnerCegMunkavallalok
    ) {
        assertPartnerCegMunkavallalokAllUpdatablePropertiesEquals(
            expectedPartnerCegMunkavallalok,
            getPersistedPartnerCegMunkavallalok(expectedPartnerCegMunkavallalok)
        );
    }
}

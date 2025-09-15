package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.SajatCegAlapadatokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.fintech.erp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.SajatCegAlapadatokRepository;
import com.fintech.erp.service.dto.SajatCegAlapadatokDTO;
import com.fintech.erp.service.mapper.SajatCegAlapadatokMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SajatCegAlapadatokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SajatCegAlapadatokResourceIT {

    private static final BigDecimal DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG = new BigDecimal(1);
    private static final BigDecimal UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG = new BigDecimal(2);
    private static final BigDecimal SMALLER_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG = new BigDecimal(1 - 1);

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sajat-ceg-alapadatoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SajatCegAlapadatokRepository sajatCegAlapadatokRepository;

    @Autowired
    private SajatCegAlapadatokMapper sajatCegAlapadatokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSajatCegAlapadatokMockMvc;

    private SajatCegAlapadatok sajatCegAlapadatok;

    private SajatCegAlapadatok insertedSajatCegAlapadatok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SajatCegAlapadatok createEntity() {
        return new SajatCegAlapadatok().cegAdminisztraciosHaviKoltseg(DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG).statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SajatCegAlapadatok createUpdatedEntity() {
        return new SajatCegAlapadatok().cegAdminisztraciosHaviKoltseg(UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG).statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        sajatCegAlapadatok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSajatCegAlapadatok != null) {
            sajatCegAlapadatokRepository.delete(insertedSajatCegAlapadatok);
            insertedSajatCegAlapadatok = null;
        }
    }

    @Test
    @Transactional
    void createSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);
        var returnedSajatCegAlapadatokDTO = om.readValue(
            restSajatCegAlapadatokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SajatCegAlapadatokDTO.class
        );

        // Validate the SajatCegAlapadatok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSajatCegAlapadatok = sajatCegAlapadatokMapper.toEntity(returnedSajatCegAlapadatokDTO);
        assertSajatCegAlapadatokUpdatableFieldsEquals(
            returnedSajatCegAlapadatok,
            getPersistedSajatCegAlapadatok(returnedSajatCegAlapadatok)
        );

        insertedSajatCegAlapadatok = returnedSajatCegAlapadatok;
    }

    @Test
    @Transactional
    void createSajatCegAlapadatokWithExistingId() throws Exception {
        // Create the SajatCegAlapadatok with an existing ID
        sajatCegAlapadatok.setId(1L);
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSajatCegAlapadatokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoks() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList
        restSajatCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sajatCegAlapadatok.getId().intValue())))
            .andExpect(jsonPath("$.[*].cegAdminisztraciosHaviKoltseg").value(hasItem(sameNumber(DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG))))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getSajatCegAlapadatok() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get the sajatCegAlapadatok
        restSajatCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL_ID, sajatCegAlapadatok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sajatCegAlapadatok.getId().intValue()))
            .andExpect(jsonPath("$.cegAdminisztraciosHaviKoltseg").value(sameNumber(DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG)))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getSajatCegAlapadatoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        Long id = sajatCegAlapadatok.getId();

        defaultSajatCegAlapadatokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSajatCegAlapadatokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSajatCegAlapadatokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg equals to
        defaultSajatCegAlapadatokFiltering(
            "cegAdminisztraciosHaviKoltseg.equals=" + DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG,
            "cegAdminisztraciosHaviKoltseg.equals=" + UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg in
        defaultSajatCegAlapadatokFiltering(
            "cegAdminisztraciosHaviKoltseg.in=" + DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG + "," + UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG,
            "cegAdminisztraciosHaviKoltseg.in=" + UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg is not null
        defaultSajatCegAlapadatokFiltering("cegAdminisztraciosHaviKoltseg.specified=true", "cegAdminisztraciosHaviKoltseg.specified=false");
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg is greater than or equal to
        defaultSajatCegAlapadatokFiltering(
            "cegAdminisztraciosHaviKoltseg.greaterThanOrEqual=" + DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG,
            "cegAdminisztraciosHaviKoltseg.greaterThanOrEqual=" + UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg is less than or equal to
        defaultSajatCegAlapadatokFiltering(
            "cegAdminisztraciosHaviKoltseg.lessThanOrEqual=" + DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG,
            "cegAdminisztraciosHaviKoltseg.lessThanOrEqual=" + SMALLER_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg is less than
        defaultSajatCegAlapadatokFiltering(
            "cegAdminisztraciosHaviKoltseg.lessThan=" + UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG,
            "cegAdminisztraciosHaviKoltseg.lessThan=" + DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegAdminisztraciosHaviKoltsegIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where cegAdminisztraciosHaviKoltseg is greater than
        defaultSajatCegAlapadatokFiltering(
            "cegAdminisztraciosHaviKoltseg.greaterThan=" + SMALLER_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG,
            "cegAdminisztraciosHaviKoltseg.greaterThan=" + DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where statusz equals to
        defaultSajatCegAlapadatokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where statusz in
        defaultSajatCegAlapadatokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where statusz is not null
        defaultSajatCegAlapadatokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where statusz contains
        defaultSajatCegAlapadatokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        // Get all the sajatCegAlapadatokList where statusz does not contain
        defaultSajatCegAlapadatokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegAlapadatoksByCegIsEqualToSomething() throws Exception {
        CegAlapadatok ceg;
        if (TestUtil.findAll(em, CegAlapadatok.class).isEmpty()) {
            sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);
            ceg = CegAlapadatokResourceIT.createEntity();
        } else {
            ceg = TestUtil.findAll(em, CegAlapadatok.class).get(0);
        }
        em.persist(ceg);
        em.flush();
        sajatCegAlapadatok.setCeg(ceg);
        sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);
        Long cegId = ceg.getId();
        // Get all the sajatCegAlapadatokList where ceg equals to cegId
        defaultSajatCegAlapadatokShouldBeFound("cegId.equals=" + cegId);

        // Get all the sajatCegAlapadatokList where ceg equals to (cegId + 1)
        defaultSajatCegAlapadatokShouldNotBeFound("cegId.equals=" + (cegId + 1));
    }

    private void defaultSajatCegAlapadatokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSajatCegAlapadatokShouldBeFound(shouldBeFound);
        defaultSajatCegAlapadatokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSajatCegAlapadatokShouldBeFound(String filter) throws Exception {
        restSajatCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sajatCegAlapadatok.getId().intValue())))
            .andExpect(jsonPath("$.[*].cegAdminisztraciosHaviKoltseg").value(hasItem(sameNumber(DEFAULT_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG))))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restSajatCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSajatCegAlapadatokShouldNotBeFound(String filter) throws Exception {
        restSajatCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSajatCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSajatCegAlapadatok() throws Exception {
        // Get the sajatCegAlapadatok
        restSajatCegAlapadatokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSajatCegAlapadatok() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegAlapadatok
        SajatCegAlapadatok updatedSajatCegAlapadatok = sajatCegAlapadatokRepository.findById(sajatCegAlapadatok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSajatCegAlapadatok are not directly saved in db
        em.detach(updatedSajatCegAlapadatok);
        updatedSajatCegAlapadatok.cegAdminisztraciosHaviKoltseg(UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG).statusz(UPDATED_STATUSZ);
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(updatedSajatCegAlapadatok);

        restSajatCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sajatCegAlapadatokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSajatCegAlapadatokToMatchAllProperties(updatedSajatCegAlapadatok);
    }

    @Test
    @Transactional
    void putNonExistingSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegAlapadatok.setId(longCount.incrementAndGet());

        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSajatCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sajatCegAlapadatokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegAlapadatok.setId(longCount.incrementAndGet());

        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegAlapadatok.setId(longCount.incrementAndGet());

        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSajatCegAlapadatokWithPatch() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegAlapadatok using partial update
        SajatCegAlapadatok partialUpdatedSajatCegAlapadatok = new SajatCegAlapadatok();
        partialUpdatedSajatCegAlapadatok.setId(sajatCegAlapadatok.getId());

        partialUpdatedSajatCegAlapadatok.cegAdminisztraciosHaviKoltseg(UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG).statusz(UPDATED_STATUSZ);

        restSajatCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSajatCegAlapadatok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSajatCegAlapadatok))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegAlapadatok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSajatCegAlapadatokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSajatCegAlapadatok, sajatCegAlapadatok),
            getPersistedSajatCegAlapadatok(sajatCegAlapadatok)
        );
    }

    @Test
    @Transactional
    void fullUpdateSajatCegAlapadatokWithPatch() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegAlapadatok using partial update
        SajatCegAlapadatok partialUpdatedSajatCegAlapadatok = new SajatCegAlapadatok();
        partialUpdatedSajatCegAlapadatok.setId(sajatCegAlapadatok.getId());

        partialUpdatedSajatCegAlapadatok.cegAdminisztraciosHaviKoltseg(UPDATED_CEG_ADMINISZTRACIOS_HAVI_KOLTSEG).statusz(UPDATED_STATUSZ);

        restSajatCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSajatCegAlapadatok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSajatCegAlapadatok))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegAlapadatok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSajatCegAlapadatokUpdatableFieldsEquals(
            partialUpdatedSajatCegAlapadatok,
            getPersistedSajatCegAlapadatok(partialUpdatedSajatCegAlapadatok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegAlapadatok.setId(longCount.incrementAndGet());

        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSajatCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sajatCegAlapadatokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegAlapadatok.setId(longCount.incrementAndGet());

        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSajatCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegAlapadatok.setId(longCount.incrementAndGet());

        // Create the SajatCegAlapadatok
        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = sajatCegAlapadatokMapper.toDto(sajatCegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegAlapadatokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SajatCegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSajatCegAlapadatok() throws Exception {
        // Initialize the database
        insertedSajatCegAlapadatok = sajatCegAlapadatokRepository.saveAndFlush(sajatCegAlapadatok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sajatCegAlapadatok
        restSajatCegAlapadatokMockMvc
            .perform(delete(ENTITY_API_URL_ID, sajatCegAlapadatok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sajatCegAlapadatokRepository.count();
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

    protected SajatCegAlapadatok getPersistedSajatCegAlapadatok(SajatCegAlapadatok sajatCegAlapadatok) {
        return sajatCegAlapadatokRepository.findById(sajatCegAlapadatok.getId()).orElseThrow();
    }

    protected void assertPersistedSajatCegAlapadatokToMatchAllProperties(SajatCegAlapadatok expectedSajatCegAlapadatok) {
        assertSajatCegAlapadatokAllPropertiesEquals(expectedSajatCegAlapadatok, getPersistedSajatCegAlapadatok(expectedSajatCegAlapadatok));
    }

    protected void assertPersistedSajatCegAlapadatokToMatchUpdatableProperties(SajatCegAlapadatok expectedSajatCegAlapadatok) {
        assertSajatCegAlapadatokAllUpdatablePropertiesEquals(
            expectedSajatCegAlapadatok,
            getPersistedSajatCegAlapadatok(expectedSajatCegAlapadatok)
        );
    }
}

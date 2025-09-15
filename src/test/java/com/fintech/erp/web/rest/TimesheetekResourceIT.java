package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.TimesheetekAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.Timesheetek;
import com.fintech.erp.repository.TimesheetekRepository;
import com.fintech.erp.service.dto.TimesheetekDTO;
import com.fintech.erp.service.mapper.TimesheetekMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TimesheetekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimesheetekResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATUM = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MUNKANAP_STATUSZA = "AAAAAAAAAA";
    private static final String UPDATED_MUNKANAP_STATUSZA = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/timesheeteks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TimesheetekRepository timesheetekRepository;

    @Autowired
    private TimesheetekMapper timesheetekMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimesheetekMockMvc;

    private Timesheetek timesheetek;

    private Timesheetek insertedTimesheetek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timesheetek createEntity() {
        return new Timesheetek().datum(DEFAULT_DATUM).munkanapStatusza(DEFAULT_MUNKANAP_STATUSZA).statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timesheetek createUpdatedEntity() {
        return new Timesheetek().datum(UPDATED_DATUM).munkanapStatusza(UPDATED_MUNKANAP_STATUSZA).statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        timesheetek = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTimesheetek != null) {
            timesheetekRepository.delete(insertedTimesheetek);
            insertedTimesheetek = null;
        }
    }

    @Test
    @Transactional
    void createTimesheetek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);
        var returnedTimesheetekDTO = om.readValue(
            restTimesheetekMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timesheetekDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TimesheetekDTO.class
        );

        // Validate the Timesheetek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTimesheetek = timesheetekMapper.toEntity(returnedTimesheetekDTO);
        assertTimesheetekUpdatableFieldsEquals(returnedTimesheetek, getPersistedTimesheetek(returnedTimesheetek));

        insertedTimesheetek = returnedTimesheetek;
    }

    @Test
    @Transactional
    void createTimesheetekWithExistingId() throws Exception {
        // Create the Timesheetek with an existing ID
        timesheetek.setId(1L);
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetekMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDatumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        timesheetek.setDatum(null);

        // Create the Timesheetek, which fails.
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        restTimesheetekMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTimesheeteks() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList
        restTimesheetekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheetek.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].munkanapStatusza").value(hasItem(DEFAULT_MUNKANAP_STATUSZA)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getTimesheetek() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get the timesheetek
        restTimesheetekMockMvc
            .perform(get(ENTITY_API_URL_ID, timesheetek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timesheetek.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.munkanapStatusza").value(DEFAULT_MUNKANAP_STATUSZA))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getTimesheeteksByIdFiltering() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        Long id = timesheetek.getId();

        defaultTimesheetekFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTimesheetekFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTimesheetekFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum equals to
        defaultTimesheetekFiltering("datum.equals=" + DEFAULT_DATUM, "datum.equals=" + UPDATED_DATUM);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum in
        defaultTimesheetekFiltering("datum.in=" + DEFAULT_DATUM + "," + UPDATED_DATUM, "datum.in=" + UPDATED_DATUM);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum is not null
        defaultTimesheetekFiltering("datum.specified=true", "datum.specified=false");
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum is greater than or equal to
        defaultTimesheetekFiltering("datum.greaterThanOrEqual=" + DEFAULT_DATUM, "datum.greaterThanOrEqual=" + UPDATED_DATUM);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum is less than or equal to
        defaultTimesheetekFiltering("datum.lessThanOrEqual=" + DEFAULT_DATUM, "datum.lessThanOrEqual=" + SMALLER_DATUM);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum is less than
        defaultTimesheetekFiltering("datum.lessThan=" + UPDATED_DATUM, "datum.lessThan=" + DEFAULT_DATUM);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByDatumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where datum is greater than
        defaultTimesheetekFiltering("datum.greaterThan=" + SMALLER_DATUM, "datum.greaterThan=" + DEFAULT_DATUM);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByMunkanapStatuszaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where munkanapStatusza equals to
        defaultTimesheetekFiltering(
            "munkanapStatusza.equals=" + DEFAULT_MUNKANAP_STATUSZA,
            "munkanapStatusza.equals=" + UPDATED_MUNKANAP_STATUSZA
        );
    }

    @Test
    @Transactional
    void getAllTimesheeteksByMunkanapStatuszaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where munkanapStatusza in
        defaultTimesheetekFiltering(
            "munkanapStatusza.in=" + DEFAULT_MUNKANAP_STATUSZA + "," + UPDATED_MUNKANAP_STATUSZA,
            "munkanapStatusza.in=" + UPDATED_MUNKANAP_STATUSZA
        );
    }

    @Test
    @Transactional
    void getAllTimesheeteksByMunkanapStatuszaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where munkanapStatusza is not null
        defaultTimesheetekFiltering("munkanapStatusza.specified=true", "munkanapStatusza.specified=false");
    }

    @Test
    @Transactional
    void getAllTimesheeteksByMunkanapStatuszaContainsSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where munkanapStatusza contains
        defaultTimesheetekFiltering(
            "munkanapStatusza.contains=" + DEFAULT_MUNKANAP_STATUSZA,
            "munkanapStatusza.contains=" + UPDATED_MUNKANAP_STATUSZA
        );
    }

    @Test
    @Transactional
    void getAllTimesheeteksByMunkanapStatuszaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where munkanapStatusza does not contain
        defaultTimesheetekFiltering(
            "munkanapStatusza.doesNotContain=" + UPDATED_MUNKANAP_STATUSZA,
            "munkanapStatusza.doesNotContain=" + DEFAULT_MUNKANAP_STATUSZA
        );
    }

    @Test
    @Transactional
    void getAllTimesheeteksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where statusz equals to
        defaultTimesheetekFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where statusz in
        defaultTimesheetekFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where statusz is not null
        defaultTimesheetekFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllTimesheeteksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where statusz contains
        defaultTimesheetekFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        // Get all the timesheetekList where statusz does not contain
        defaultTimesheetekFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllTimesheeteksByMunkavallaloIsEqualToSomething() throws Exception {
        Munkavallalok munkavallalo;
        if (TestUtil.findAll(em, Munkavallalok.class).isEmpty()) {
            timesheetekRepository.saveAndFlush(timesheetek);
            munkavallalo = MunkavallalokResourceIT.createEntity();
        } else {
            munkavallalo = TestUtil.findAll(em, Munkavallalok.class).get(0);
        }
        em.persist(munkavallalo);
        em.flush();
        timesheetek.setMunkavallalo(munkavallalo);
        timesheetekRepository.saveAndFlush(timesheetek);
        Long munkavallaloId = munkavallalo.getId();
        // Get all the timesheetekList where munkavallalo equals to munkavallaloId
        defaultTimesheetekShouldBeFound("munkavallaloId.equals=" + munkavallaloId);

        // Get all the timesheetekList where munkavallalo equals to (munkavallaloId + 1)
        defaultTimesheetekShouldNotBeFound("munkavallaloId.equals=" + (munkavallaloId + 1));
    }

    private void defaultTimesheetekFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTimesheetekShouldBeFound(shouldBeFound);
        defaultTimesheetekShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTimesheetekShouldBeFound(String filter) throws Exception {
        restTimesheetekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheetek.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].munkanapStatusza").value(hasItem(DEFAULT_MUNKANAP_STATUSZA)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restTimesheetekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTimesheetekShouldNotBeFound(String filter) throws Exception {
        restTimesheetekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTimesheetekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTimesheetek() throws Exception {
        // Get the timesheetek
        restTimesheetekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTimesheetek() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the timesheetek
        Timesheetek updatedTimesheetek = timesheetekRepository.findById(timesheetek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTimesheetek are not directly saved in db
        em.detach(updatedTimesheetek);
        updatedTimesheetek.datum(UPDATED_DATUM).munkanapStatusza(UPDATED_MUNKANAP_STATUSZA).statusz(UPDATED_STATUSZ);
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(updatedTimesheetek);

        restTimesheetekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timesheetekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isOk());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTimesheetekToMatchAllProperties(updatedTimesheetek);
    }

    @Test
    @Transactional
    void putNonExistingTimesheetek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timesheetek.setId(longCount.incrementAndGet());

        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timesheetekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimesheetek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timesheetek.setId(longCount.incrementAndGet());

        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimesheetekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimesheetek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timesheetek.setId(longCount.incrementAndGet());

        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimesheetekMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timesheetekDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimesheetekWithPatch() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the timesheetek using partial update
        Timesheetek partialUpdatedTimesheetek = new Timesheetek();
        partialUpdatedTimesheetek.setId(timesheetek.getId());

        partialUpdatedTimesheetek.datum(UPDATED_DATUM).munkanapStatusza(UPDATED_MUNKANAP_STATUSZA).statusz(UPDATED_STATUSZ);

        restTimesheetekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimesheetek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTimesheetek))
            )
            .andExpect(status().isOk());

        // Validate the Timesheetek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTimesheetekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTimesheetek, timesheetek),
            getPersistedTimesheetek(timesheetek)
        );
    }

    @Test
    @Transactional
    void fullUpdateTimesheetekWithPatch() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the timesheetek using partial update
        Timesheetek partialUpdatedTimesheetek = new Timesheetek();
        partialUpdatedTimesheetek.setId(timesheetek.getId());

        partialUpdatedTimesheetek.datum(UPDATED_DATUM).munkanapStatusza(UPDATED_MUNKANAP_STATUSZA).statusz(UPDATED_STATUSZ);

        restTimesheetekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimesheetek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTimesheetek))
            )
            .andExpect(status().isOk());

        // Validate the Timesheetek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTimesheetekUpdatableFieldsEquals(partialUpdatedTimesheetek, getPersistedTimesheetek(partialUpdatedTimesheetek));
    }

    @Test
    @Transactional
    void patchNonExistingTimesheetek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timesheetek.setId(longCount.incrementAndGet());

        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timesheetekDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimesheetek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timesheetek.setId(longCount.incrementAndGet());

        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimesheetekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimesheetek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timesheetek.setId(longCount.incrementAndGet());

        // Create the Timesheetek
        TimesheetekDTO timesheetekDTO = timesheetekMapper.toDto(timesheetek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimesheetekMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(timesheetekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Timesheetek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimesheetek() throws Exception {
        // Initialize the database
        insertedTimesheetek = timesheetekRepository.saveAndFlush(timesheetek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the timesheetek
        restTimesheetekMockMvc
            .perform(delete(ENTITY_API_URL_ID, timesheetek.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return timesheetekRepository.count();
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

    protected Timesheetek getPersistedTimesheetek(Timesheetek timesheetek) {
        return timesheetekRepository.findById(timesheetek.getId()).orElseThrow();
    }

    protected void assertPersistedTimesheetekToMatchAllProperties(Timesheetek expectedTimesheetek) {
        assertTimesheetekAllPropertiesEquals(expectedTimesheetek, getPersistedTimesheetek(expectedTimesheetek));
    }

    protected void assertPersistedTimesheetekToMatchUpdatableProperties(Timesheetek expectedTimesheetek) {
        assertTimesheetekAllUpdatablePropertiesEquals(expectedTimesheetek, getPersistedTimesheetek(expectedTimesheetek));
    }
}

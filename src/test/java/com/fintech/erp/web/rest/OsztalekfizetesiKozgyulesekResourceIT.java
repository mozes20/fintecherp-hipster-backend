package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.OsztalekfizetesiKozgyulesekAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.mapper.OsztalekfizetesiKozgyulesekMapper;
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
 * Integration tests for the {@link OsztalekfizetesiKozgyulesekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OsztalekfizetesiKozgyulesekResourceIT {

    private static final LocalDate DEFAULT_KOZGYULES_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_KOZGYULES_DATUM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_KOZGYULES_DATUM = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA = false;
    private static final Boolean UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA = true;

    private static final Boolean DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT = false;
    private static final Boolean UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT = true;

    private static final String ENTITY_API_URL = "/api/osztalekfizetesi-kozgyuleseks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository;

    @Autowired
    private OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOsztalekfizetesiKozgyulesekMockMvc;

    private OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek;

    private OsztalekfizetesiKozgyulesek insertedOsztalekfizetesiKozgyulesek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OsztalekfizetesiKozgyulesek createEntity() {
        return new OsztalekfizetesiKozgyulesek()
            .kozgyulesDatum(DEFAULT_KOZGYULES_DATUM)
            .kozgyulesiJegyzokonyvGeneralta(DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA)
            .kozgyulesiJegyzokonyvAlairt(DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OsztalekfizetesiKozgyulesek createUpdatedEntity() {
        return new OsztalekfizetesiKozgyulesek()
            .kozgyulesDatum(UPDATED_KOZGYULES_DATUM)
            .kozgyulesiJegyzokonyvGeneralta(UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA)
            .kozgyulesiJegyzokonyvAlairt(UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT);
    }

    @BeforeEach
    void initTest() {
        osztalekfizetesiKozgyulesek = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOsztalekfizetesiKozgyulesek != null) {
            osztalekfizetesiKozgyulesekRepository.delete(insertedOsztalekfizetesiKozgyulesek);
            insertedOsztalekfizetesiKozgyulesek = null;
        }
    }

    @Test
    @Transactional
    void createOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );
        var returnedOsztalekfizetesiKozgyulesekDTO = om.readValue(
            restOsztalekfizetesiKozgyulesekMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OsztalekfizetesiKozgyulesekDTO.class
        );

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekMapper.toEntity(returnedOsztalekfizetesiKozgyulesekDTO);
        assertOsztalekfizetesiKozgyulesekUpdatableFieldsEquals(
            returnedOsztalekfizetesiKozgyulesek,
            getPersistedOsztalekfizetesiKozgyulesek(returnedOsztalekfizetesiKozgyulesek)
        );

        insertedOsztalekfizetesiKozgyulesek = returnedOsztalekfizetesiKozgyulesek;
    }

    @Test
    @Transactional
    void createOsztalekfizetesiKozgyulesekWithExistingId() throws Exception {
        // Create the OsztalekfizetesiKozgyulesek with an existing ID
        osztalekfizetesiKozgyulesek.setId(1L);
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKozgyulesDatumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        osztalekfizetesiKozgyulesek.setKozgyulesDatum(null);

        // Create the OsztalekfizetesiKozgyulesek, which fails.
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseks() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osztalekfizetesiKozgyulesek.getId().intValue())))
            .andExpect(jsonPath("$.[*].kozgyulesDatum").value(hasItem(DEFAULT_KOZGYULES_DATUM.toString())))
            .andExpect(jsonPath("$.[*].kozgyulesiJegyzokonyvGeneralta").value(hasItem(DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA)))
            .andExpect(jsonPath("$.[*].kozgyulesiJegyzokonyvAlairt").value(hasItem(DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT)));
    }

    @Test
    @Transactional
    void getOsztalekfizetesiKozgyulesek() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get the osztalekfizetesiKozgyulesek
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(get(ENTITY_API_URL_ID, osztalekfizetesiKozgyulesek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(osztalekfizetesiKozgyulesek.getId().intValue()))
            .andExpect(jsonPath("$.kozgyulesDatum").value(DEFAULT_KOZGYULES_DATUM.toString()))
            .andExpect(jsonPath("$.kozgyulesiJegyzokonyvGeneralta").value(DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA))
            .andExpect(jsonPath("$.kozgyulesiJegyzokonyvAlairt").value(DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT));
    }

    @Test
    @Transactional
    void getOsztalekfizetesiKozgyuleseksByIdFiltering() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        Long id = osztalekfizetesiKozgyulesek.getId();

        defaultOsztalekfizetesiKozgyulesekFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultOsztalekfizetesiKozgyulesekFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultOsztalekfizetesiKozgyulesekFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum equals to
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesDatum.equals=" + DEFAULT_KOZGYULES_DATUM,
            "kozgyulesDatum.equals=" + UPDATED_KOZGYULES_DATUM
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum in
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesDatum.in=" + DEFAULT_KOZGYULES_DATUM + "," + UPDATED_KOZGYULES_DATUM,
            "kozgyulesDatum.in=" + UPDATED_KOZGYULES_DATUM
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum is not null
        defaultOsztalekfizetesiKozgyulesekFiltering("kozgyulesDatum.specified=true", "kozgyulesDatum.specified=false");
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum is greater than or equal to
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesDatum.greaterThanOrEqual=" + DEFAULT_KOZGYULES_DATUM,
            "kozgyulesDatum.greaterThanOrEqual=" + UPDATED_KOZGYULES_DATUM
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum is less than or equal to
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesDatum.lessThanOrEqual=" + DEFAULT_KOZGYULES_DATUM,
            "kozgyulesDatum.lessThanOrEqual=" + SMALLER_KOZGYULES_DATUM
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum is less than
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesDatum.lessThan=" + UPDATED_KOZGYULES_DATUM,
            "kozgyulesDatum.lessThan=" + DEFAULT_KOZGYULES_DATUM
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesDatumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesDatum is greater than
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesDatum.greaterThan=" + SMALLER_KOZGYULES_DATUM,
            "kozgyulesDatum.greaterThan=" + DEFAULT_KOZGYULES_DATUM
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesiJegyzokonyvGeneraltaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesiJegyzokonyvGeneralta equals to
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesiJegyzokonyvGeneralta.equals=" + DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA,
            "kozgyulesiJegyzokonyvGeneralta.equals=" + UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesiJegyzokonyvGeneraltaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesiJegyzokonyvGeneralta in
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesiJegyzokonyvGeneralta.in=" +
            DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA +
            "," +
            UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA,
            "kozgyulesiJegyzokonyvGeneralta.in=" + UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesiJegyzokonyvGeneraltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesiJegyzokonyvGeneralta is not null
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesiJegyzokonyvGeneralta.specified=true",
            "kozgyulesiJegyzokonyvGeneralta.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesiJegyzokonyvAlairtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesiJegyzokonyvAlairt equals to
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesiJegyzokonyvAlairt.equals=" + DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT,
            "kozgyulesiJegyzokonyvAlairt.equals=" + UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesiJegyzokonyvAlairtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesiJegyzokonyvAlairt in
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesiJegyzokonyvAlairt.in=" + DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT + "," + UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT,
            "kozgyulesiJegyzokonyvAlairt.in=" + UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksByKozgyulesiJegyzokonyvAlairtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        // Get all the osztalekfizetesiKozgyulesekList where kozgyulesiJegyzokonyvAlairt is not null
        defaultOsztalekfizetesiKozgyulesekFiltering(
            "kozgyulesiJegyzokonyvAlairt.specified=true",
            "kozgyulesiJegyzokonyvAlairt.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllOsztalekfizetesiKozgyuleseksBySajatCegIsEqualToSomething() throws Exception {
        SajatCegAlapadatok sajatCeg;
        if (TestUtil.findAll(em, SajatCegAlapadatok.class).isEmpty()) {
            osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);
            sajatCeg = SajatCegAlapadatokResourceIT.createEntity();
        } else {
            sajatCeg = TestUtil.findAll(em, SajatCegAlapadatok.class).get(0);
        }
        em.persist(sajatCeg);
        em.flush();
        osztalekfizetesiKozgyulesek.setSajatCeg(sajatCeg);
        osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);
        Long sajatCegId = sajatCeg.getId();
        // Get all the osztalekfizetesiKozgyulesekList where sajatCeg equals to sajatCegId
        defaultOsztalekfizetesiKozgyulesekShouldBeFound("sajatCegId.equals=" + sajatCegId);

        // Get all the osztalekfizetesiKozgyulesekList where sajatCeg equals to (sajatCegId + 1)
        defaultOsztalekfizetesiKozgyulesekShouldNotBeFound("sajatCegId.equals=" + (sajatCegId + 1));
    }

    private void defaultOsztalekfizetesiKozgyulesekFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultOsztalekfizetesiKozgyulesekShouldBeFound(shouldBeFound);
        defaultOsztalekfizetesiKozgyulesekShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOsztalekfizetesiKozgyulesekShouldBeFound(String filter) throws Exception {
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osztalekfizetesiKozgyulesek.getId().intValue())))
            .andExpect(jsonPath("$.[*].kozgyulesDatum").value(hasItem(DEFAULT_KOZGYULES_DATUM.toString())))
            .andExpect(jsonPath("$.[*].kozgyulesiJegyzokonyvGeneralta").value(hasItem(DEFAULT_KOZGYULESI_JEGYZOKONYV_GENERALTA)))
            .andExpect(jsonPath("$.[*].kozgyulesiJegyzokonyvAlairt").value(hasItem(DEFAULT_KOZGYULESI_JEGYZOKONYV_ALAIRT)));

        // Check, that the count call also returns 1
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOsztalekfizetesiKozgyulesekShouldNotBeFound(String filter) throws Exception {
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOsztalekfizetesiKozgyulesek() throws Exception {
        // Get the osztalekfizetesiKozgyulesek
        restOsztalekfizetesiKozgyulesekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOsztalekfizetesiKozgyulesek() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the osztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesek updatedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository
            .findById(osztalekfizetesiKozgyulesek.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOsztalekfizetesiKozgyulesek are not directly saved in db
        em.detach(updatedOsztalekfizetesiKozgyulesek);
        updatedOsztalekfizetesiKozgyulesek
            .kozgyulesDatum(UPDATED_KOZGYULES_DATUM)
            .kozgyulesiJegyzokonyvGeneralta(UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA)
            .kozgyulesiJegyzokonyvAlairt(UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT);
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            updatedOsztalekfizetesiKozgyulesek
        );

        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, osztalekfizetesiKozgyulesekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isOk());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOsztalekfizetesiKozgyulesekToMatchAllProperties(updatedOsztalekfizetesiKozgyulesek);
    }

    @Test
    @Transactional
    void putNonExistingOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        osztalekfizetesiKozgyulesek.setId(longCount.incrementAndGet());

        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, osztalekfizetesiKozgyulesekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        osztalekfizetesiKozgyulesek.setId(longCount.incrementAndGet());

        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        osztalekfizetesiKozgyulesek.setId(longCount.incrementAndGet());

        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOsztalekfizetesiKozgyulesekWithPatch() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the osztalekfizetesiKozgyulesek using partial update
        OsztalekfizetesiKozgyulesek partialUpdatedOsztalekfizetesiKozgyulesek = new OsztalekfizetesiKozgyulesek();
        partialUpdatedOsztalekfizetesiKozgyulesek.setId(osztalekfizetesiKozgyulesek.getId());

        partialUpdatedOsztalekfizetesiKozgyulesek.kozgyulesDatum(UPDATED_KOZGYULES_DATUM);

        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOsztalekfizetesiKozgyulesek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOsztalekfizetesiKozgyulesek))
            )
            .andExpect(status().isOk());

        // Validate the OsztalekfizetesiKozgyulesek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOsztalekfizetesiKozgyulesekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOsztalekfizetesiKozgyulesek, osztalekfizetesiKozgyulesek),
            getPersistedOsztalekfizetesiKozgyulesek(osztalekfizetesiKozgyulesek)
        );
    }

    @Test
    @Transactional
    void fullUpdateOsztalekfizetesiKozgyulesekWithPatch() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the osztalekfizetesiKozgyulesek using partial update
        OsztalekfizetesiKozgyulesek partialUpdatedOsztalekfizetesiKozgyulesek = new OsztalekfizetesiKozgyulesek();
        partialUpdatedOsztalekfizetesiKozgyulesek.setId(osztalekfizetesiKozgyulesek.getId());

        partialUpdatedOsztalekfizetesiKozgyulesek
            .kozgyulesDatum(UPDATED_KOZGYULES_DATUM)
            .kozgyulesiJegyzokonyvGeneralta(UPDATED_KOZGYULESI_JEGYZOKONYV_GENERALTA)
            .kozgyulesiJegyzokonyvAlairt(UPDATED_KOZGYULESI_JEGYZOKONYV_ALAIRT);

        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOsztalekfizetesiKozgyulesek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOsztalekfizetesiKozgyulesek))
            )
            .andExpect(status().isOk());

        // Validate the OsztalekfizetesiKozgyulesek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOsztalekfizetesiKozgyulesekUpdatableFieldsEquals(
            partialUpdatedOsztalekfizetesiKozgyulesek,
            getPersistedOsztalekfizetesiKozgyulesek(partialUpdatedOsztalekfizetesiKozgyulesek)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        osztalekfizetesiKozgyulesek.setId(longCount.incrementAndGet());

        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, osztalekfizetesiKozgyulesekDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        osztalekfizetesiKozgyulesek.setId(longCount.incrementAndGet());

        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOsztalekfizetesiKozgyulesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        osztalekfizetesiKozgyulesek.setId(longCount.incrementAndGet());

        // Create the OsztalekfizetesiKozgyulesek
        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = osztalekfizetesiKozgyulesekMapper.toDto(
            osztalekfizetesiKozgyulesek
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(osztalekfizetesiKozgyulesekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OsztalekfizetesiKozgyulesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOsztalekfizetesiKozgyulesek() throws Exception {
        // Initialize the database
        insertedOsztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.saveAndFlush(osztalekfizetesiKozgyulesek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the osztalekfizetesiKozgyulesek
        restOsztalekfizetesiKozgyulesekMockMvc
            .perform(delete(ENTITY_API_URL_ID, osztalekfizetesiKozgyulesek.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return osztalekfizetesiKozgyulesekRepository.count();
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

    protected OsztalekfizetesiKozgyulesek getPersistedOsztalekfizetesiKozgyulesek(OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek) {
        return osztalekfizetesiKozgyulesekRepository.findById(osztalekfizetesiKozgyulesek.getId()).orElseThrow();
    }

    protected void assertPersistedOsztalekfizetesiKozgyulesekToMatchAllProperties(
        OsztalekfizetesiKozgyulesek expectedOsztalekfizetesiKozgyulesek
    ) {
        assertOsztalekfizetesiKozgyulesekAllPropertiesEquals(
            expectedOsztalekfizetesiKozgyulesek,
            getPersistedOsztalekfizetesiKozgyulesek(expectedOsztalekfizetesiKozgyulesek)
        );
    }

    protected void assertPersistedOsztalekfizetesiKozgyulesekToMatchUpdatableProperties(
        OsztalekfizetesiKozgyulesek expectedOsztalekfizetesiKozgyulesek
    ) {
        assertOsztalekfizetesiKozgyulesekAllUpdatablePropertiesEquals(
            expectedOsztalekfizetesiKozgyulesek,
            getPersistedOsztalekfizetesiKozgyulesek(expectedOsztalekfizetesiKozgyulesek)
        );
    }
}

package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.EfoFoglalkoztatasokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.fintech.erp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import com.fintech.erp.service.mapper.EfoFoglalkoztatasokMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EfoFoglalkoztatasokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EfoFoglalkoztatasokResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATUM = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_OSSZEG = new BigDecimal(1);
    private static final BigDecimal UPDATED_OSSZEG = new BigDecimal(2);
    private static final BigDecimal SMALLER_OSSZEG = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_GENERALT_EFO_SZERZODES = false;
    private static final Boolean UPDATED_GENERALT_EFO_SZERZODES = true;

    private static final Boolean DEFAULT_ALAIRT_EFO_SZERZODES = false;
    private static final Boolean UPDATED_ALAIRT_EFO_SZERZODES = true;

    private static final String ENTITY_API_URL = "/api/efo-foglalkoztatasoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;

    @Autowired
    private EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEfoFoglalkoztatasokMockMvc;

    private EfoFoglalkoztatasok efoFoglalkoztatasok;

    private EfoFoglalkoztatasok insertedEfoFoglalkoztatasok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EfoFoglalkoztatasok createEntity() {
        return new EfoFoglalkoztatasok()
            .datum(DEFAULT_DATUM)
            .osszeg(DEFAULT_OSSZEG)
            .generaltEfoSzerzodes(DEFAULT_GENERALT_EFO_SZERZODES)
            .alairtEfoSzerzodes(DEFAULT_ALAIRT_EFO_SZERZODES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EfoFoglalkoztatasok createUpdatedEntity() {
        return new EfoFoglalkoztatasok()
            .datum(UPDATED_DATUM)
            .osszeg(UPDATED_OSSZEG)
            .generaltEfoSzerzodes(UPDATED_GENERALT_EFO_SZERZODES)
            .alairtEfoSzerzodes(UPDATED_ALAIRT_EFO_SZERZODES);
    }

    @BeforeEach
    void initTest() {
        efoFoglalkoztatasok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEfoFoglalkoztatasok != null) {
            efoFoglalkoztatasokRepository.delete(insertedEfoFoglalkoztatasok);
            insertedEfoFoglalkoztatasok = null;
        }
    }

    @Test
    @Transactional
    void createEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);
        var returnedEfoFoglalkoztatasokDTO = om.readValue(
            restEfoFoglalkoztatasokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EfoFoglalkoztatasokDTO.class
        );

        // Validate the EfoFoglalkoztatasok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEfoFoglalkoztatasok = efoFoglalkoztatasokMapper.toEntity(returnedEfoFoglalkoztatasokDTO);
        assertEfoFoglalkoztatasokUpdatableFieldsEquals(
            returnedEfoFoglalkoztatasok,
            getPersistedEfoFoglalkoztatasok(returnedEfoFoglalkoztatasok)
        );

        insertedEfoFoglalkoztatasok = returnedEfoFoglalkoztatasok;
    }

    @Test
    @Transactional
    void createEfoFoglalkoztatasokWithExistingId() throws Exception {
        // Create the EfoFoglalkoztatasok with an existing ID
        efoFoglalkoztatasok.setId(1L);
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEfoFoglalkoztatasokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDatumIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        efoFoglalkoztatasok.setDatum(null);

        // Create the EfoFoglalkoztatasok, which fails.
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        restEfoFoglalkoztatasokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoks() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList
        restEfoFoglalkoztatasokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(efoFoglalkoztatasok.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].osszeg").value(hasItem(sameNumber(DEFAULT_OSSZEG))))
            .andExpect(jsonPath("$.[*].generaltEfoSzerzodes").value(hasItem(DEFAULT_GENERALT_EFO_SZERZODES)))
            .andExpect(jsonPath("$.[*].alairtEfoSzerzodes").value(hasItem(DEFAULT_ALAIRT_EFO_SZERZODES)));
    }

    @Test
    @Transactional
    void getEfoFoglalkoztatasok() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get the efoFoglalkoztatasok
        restEfoFoglalkoztatasokMockMvc
            .perform(get(ENTITY_API_URL_ID, efoFoglalkoztatasok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(efoFoglalkoztatasok.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.osszeg").value(sameNumber(DEFAULT_OSSZEG)))
            .andExpect(jsonPath("$.generaltEfoSzerzodes").value(DEFAULT_GENERALT_EFO_SZERZODES))
            .andExpect(jsonPath("$.alairtEfoSzerzodes").value(DEFAULT_ALAIRT_EFO_SZERZODES));
    }

    @Test
    @Transactional
    void getEfoFoglalkoztatasoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        Long id = efoFoglalkoztatasok.getId();

        defaultEfoFoglalkoztatasokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEfoFoglalkoztatasokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEfoFoglalkoztatasokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum equals to
        defaultEfoFoglalkoztatasokFiltering("datum.equals=" + DEFAULT_DATUM, "datum.equals=" + UPDATED_DATUM);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum in
        defaultEfoFoglalkoztatasokFiltering("datum.in=" + DEFAULT_DATUM + "," + UPDATED_DATUM, "datum.in=" + UPDATED_DATUM);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum is not null
        defaultEfoFoglalkoztatasokFiltering("datum.specified=true", "datum.specified=false");
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum is greater than or equal to
        defaultEfoFoglalkoztatasokFiltering("datum.greaterThanOrEqual=" + DEFAULT_DATUM, "datum.greaterThanOrEqual=" + UPDATED_DATUM);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum is less than or equal to
        defaultEfoFoglalkoztatasokFiltering("datum.lessThanOrEqual=" + DEFAULT_DATUM, "datum.lessThanOrEqual=" + SMALLER_DATUM);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum is less than
        defaultEfoFoglalkoztatasokFiltering("datum.lessThan=" + UPDATED_DATUM, "datum.lessThan=" + DEFAULT_DATUM);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByDatumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where datum is greater than
        defaultEfoFoglalkoztatasokFiltering("datum.greaterThan=" + SMALLER_DATUM, "datum.greaterThan=" + DEFAULT_DATUM);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg equals to
        defaultEfoFoglalkoztatasokFiltering("osszeg.equals=" + DEFAULT_OSSZEG, "osszeg.equals=" + UPDATED_OSSZEG);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg in
        defaultEfoFoglalkoztatasokFiltering("osszeg.in=" + DEFAULT_OSSZEG + "," + UPDATED_OSSZEG, "osszeg.in=" + UPDATED_OSSZEG);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg is not null
        defaultEfoFoglalkoztatasokFiltering("osszeg.specified=true", "osszeg.specified=false");
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg is greater than or equal to
        defaultEfoFoglalkoztatasokFiltering("osszeg.greaterThanOrEqual=" + DEFAULT_OSSZEG, "osszeg.greaterThanOrEqual=" + UPDATED_OSSZEG);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg is less than or equal to
        defaultEfoFoglalkoztatasokFiltering("osszeg.lessThanOrEqual=" + DEFAULT_OSSZEG, "osszeg.lessThanOrEqual=" + SMALLER_OSSZEG);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg is less than
        defaultEfoFoglalkoztatasokFiltering("osszeg.lessThan=" + UPDATED_OSSZEG, "osszeg.lessThan=" + DEFAULT_OSSZEG);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByOsszegIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where osszeg is greater than
        defaultEfoFoglalkoztatasokFiltering("osszeg.greaterThan=" + SMALLER_OSSZEG, "osszeg.greaterThan=" + DEFAULT_OSSZEG);
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByGeneraltEfoSzerzodesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where generaltEfoSzerzodes equals to
        defaultEfoFoglalkoztatasokFiltering(
            "generaltEfoSzerzodes.equals=" + DEFAULT_GENERALT_EFO_SZERZODES,
            "generaltEfoSzerzodes.equals=" + UPDATED_GENERALT_EFO_SZERZODES
        );
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByGeneraltEfoSzerzodesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where generaltEfoSzerzodes in
        defaultEfoFoglalkoztatasokFiltering(
            "generaltEfoSzerzodes.in=" + DEFAULT_GENERALT_EFO_SZERZODES + "," + UPDATED_GENERALT_EFO_SZERZODES,
            "generaltEfoSzerzodes.in=" + UPDATED_GENERALT_EFO_SZERZODES
        );
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByGeneraltEfoSzerzodesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where generaltEfoSzerzodes is not null
        defaultEfoFoglalkoztatasokFiltering("generaltEfoSzerzodes.specified=true", "generaltEfoSzerzodes.specified=false");
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByAlairtEfoSzerzodesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where alairtEfoSzerzodes equals to
        defaultEfoFoglalkoztatasokFiltering(
            "alairtEfoSzerzodes.equals=" + DEFAULT_ALAIRT_EFO_SZERZODES,
            "alairtEfoSzerzodes.equals=" + UPDATED_ALAIRT_EFO_SZERZODES
        );
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByAlairtEfoSzerzodesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where alairtEfoSzerzodes in
        defaultEfoFoglalkoztatasokFiltering(
            "alairtEfoSzerzodes.in=" + DEFAULT_ALAIRT_EFO_SZERZODES + "," + UPDATED_ALAIRT_EFO_SZERZODES,
            "alairtEfoSzerzodes.in=" + UPDATED_ALAIRT_EFO_SZERZODES
        );
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByAlairtEfoSzerzodesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        // Get all the efoFoglalkoztatasokList where alairtEfoSzerzodes is not null
        defaultEfoFoglalkoztatasokFiltering("alairtEfoSzerzodes.specified=true", "alairtEfoSzerzodes.specified=false");
    }

    @Test
    @Transactional
    void getAllEfoFoglalkoztatasoksByMunkavallaloIsEqualToSomething() throws Exception {
        Munkavallalok munkavallalo;
        if (TestUtil.findAll(em, Munkavallalok.class).isEmpty()) {
            efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);
            munkavallalo = MunkavallalokResourceIT.createEntity();
        } else {
            munkavallalo = TestUtil.findAll(em, Munkavallalok.class).get(0);
        }
        em.persist(munkavallalo);
        em.flush();
        efoFoglalkoztatasok.setMunkavallalo(munkavallalo);
        efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);
        Long munkavallaloId = munkavallalo.getId();
        // Get all the efoFoglalkoztatasokList where munkavallalo equals to munkavallaloId
        defaultEfoFoglalkoztatasokShouldBeFound("munkavallaloId.equals=" + munkavallaloId);

        // Get all the efoFoglalkoztatasokList where munkavallalo equals to (munkavallaloId + 1)
        defaultEfoFoglalkoztatasokShouldNotBeFound("munkavallaloId.equals=" + (munkavallaloId + 1));
    }

    private void defaultEfoFoglalkoztatasokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEfoFoglalkoztatasokShouldBeFound(shouldBeFound);
        defaultEfoFoglalkoztatasokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEfoFoglalkoztatasokShouldBeFound(String filter) throws Exception {
        restEfoFoglalkoztatasokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(efoFoglalkoztatasok.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].osszeg").value(hasItem(sameNumber(DEFAULT_OSSZEG))))
            .andExpect(jsonPath("$.[*].generaltEfoSzerzodes").value(hasItem(DEFAULT_GENERALT_EFO_SZERZODES)))
            .andExpect(jsonPath("$.[*].alairtEfoSzerzodes").value(hasItem(DEFAULT_ALAIRT_EFO_SZERZODES)));

        // Check, that the count call also returns 1
        restEfoFoglalkoztatasokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEfoFoglalkoztatasokShouldNotBeFound(String filter) throws Exception {
        restEfoFoglalkoztatasokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEfoFoglalkoztatasokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEfoFoglalkoztatasok() throws Exception {
        // Get the efoFoglalkoztatasok
        restEfoFoglalkoztatasokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEfoFoglalkoztatasok() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the efoFoglalkoztatasok
        EfoFoglalkoztatasok updatedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.findById(efoFoglalkoztatasok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEfoFoglalkoztatasok are not directly saved in db
        em.detach(updatedEfoFoglalkoztatasok);
        updatedEfoFoglalkoztatasok
            .datum(UPDATED_DATUM)
            .osszeg(UPDATED_OSSZEG)
            .generaltEfoSzerzodes(UPDATED_GENERALT_EFO_SZERZODES)
            .alairtEfoSzerzodes(UPDATED_ALAIRT_EFO_SZERZODES);
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(updatedEfoFoglalkoztatasok);

        restEfoFoglalkoztatasokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, efoFoglalkoztatasokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isOk());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEfoFoglalkoztatasokToMatchAllProperties(updatedEfoFoglalkoztatasok);
    }

    @Test
    @Transactional
    void putNonExistingEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        efoFoglalkoztatasok.setId(longCount.incrementAndGet());

        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEfoFoglalkoztatasokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, efoFoglalkoztatasokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        efoFoglalkoztatasok.setId(longCount.incrementAndGet());

        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEfoFoglalkoztatasokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        efoFoglalkoztatasok.setId(longCount.incrementAndGet());

        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEfoFoglalkoztatasokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEfoFoglalkoztatasokWithPatch() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the efoFoglalkoztatasok using partial update
        EfoFoglalkoztatasok partialUpdatedEfoFoglalkoztatasok = new EfoFoglalkoztatasok();
        partialUpdatedEfoFoglalkoztatasok.setId(efoFoglalkoztatasok.getId());

        partialUpdatedEfoFoglalkoztatasok.osszeg(UPDATED_OSSZEG);

        restEfoFoglalkoztatasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEfoFoglalkoztatasok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEfoFoglalkoztatasok))
            )
            .andExpect(status().isOk());

        // Validate the EfoFoglalkoztatasok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEfoFoglalkoztatasokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEfoFoglalkoztatasok, efoFoglalkoztatasok),
            getPersistedEfoFoglalkoztatasok(efoFoglalkoztatasok)
        );
    }

    @Test
    @Transactional
    void fullUpdateEfoFoglalkoztatasokWithPatch() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the efoFoglalkoztatasok using partial update
        EfoFoglalkoztatasok partialUpdatedEfoFoglalkoztatasok = new EfoFoglalkoztatasok();
        partialUpdatedEfoFoglalkoztatasok.setId(efoFoglalkoztatasok.getId());

        partialUpdatedEfoFoglalkoztatasok
            .datum(UPDATED_DATUM)
            .osszeg(UPDATED_OSSZEG)
            .generaltEfoSzerzodes(UPDATED_GENERALT_EFO_SZERZODES)
            .alairtEfoSzerzodes(UPDATED_ALAIRT_EFO_SZERZODES);

        restEfoFoglalkoztatasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEfoFoglalkoztatasok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEfoFoglalkoztatasok))
            )
            .andExpect(status().isOk());

        // Validate the EfoFoglalkoztatasok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEfoFoglalkoztatasokUpdatableFieldsEquals(
            partialUpdatedEfoFoglalkoztatasok,
            getPersistedEfoFoglalkoztatasok(partialUpdatedEfoFoglalkoztatasok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        efoFoglalkoztatasok.setId(longCount.incrementAndGet());

        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEfoFoglalkoztatasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, efoFoglalkoztatasokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        efoFoglalkoztatasok.setId(longCount.incrementAndGet());

        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEfoFoglalkoztatasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEfoFoglalkoztatasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        efoFoglalkoztatasok.setId(longCount.incrementAndGet());

        // Create the EfoFoglalkoztatasok
        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = efoFoglalkoztatasokMapper.toDto(efoFoglalkoztatasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEfoFoglalkoztatasokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(efoFoglalkoztatasokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EfoFoglalkoztatasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEfoFoglalkoztatasok() throws Exception {
        // Initialize the database
        insertedEfoFoglalkoztatasok = efoFoglalkoztatasokRepository.saveAndFlush(efoFoglalkoztatasok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the efoFoglalkoztatasok
        restEfoFoglalkoztatasokMockMvc
            .perform(delete(ENTITY_API_URL_ID, efoFoglalkoztatasok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return efoFoglalkoztatasokRepository.count();
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

    protected EfoFoglalkoztatasok getPersistedEfoFoglalkoztatasok(EfoFoglalkoztatasok efoFoglalkoztatasok) {
        return efoFoglalkoztatasokRepository.findById(efoFoglalkoztatasok.getId()).orElseThrow();
    }

    protected void assertPersistedEfoFoglalkoztatasokToMatchAllProperties(EfoFoglalkoztatasok expectedEfoFoglalkoztatasok) {
        assertEfoFoglalkoztatasokAllPropertiesEquals(
            expectedEfoFoglalkoztatasok,
            getPersistedEfoFoglalkoztatasok(expectedEfoFoglalkoztatasok)
        );
    }

    protected void assertPersistedEfoFoglalkoztatasokToMatchUpdatableProperties(EfoFoglalkoztatasok expectedEfoFoglalkoztatasok) {
        assertEfoFoglalkoztatasokAllUpdatablePropertiesEquals(
            expectedEfoFoglalkoztatasok,
            getPersistedEfoFoglalkoztatasok(expectedEfoFoglalkoztatasok)
        );
    }
}

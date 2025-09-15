package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.MunkavallalokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import com.fintech.erp.service.mapper.MunkavallalokMapper;
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
 * Integration tests for the {@link MunkavallalokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MunkavallalokResourceIT {

    private static final String DEFAULT_FOGLALKOZTATAS_TIPUSA = "AAAAAAAAAA";
    private static final String UPDATED_FOGLALKOZTATAS_TIPUSA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FOGLALKOZTATAS_KEZDETE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FOGLALKOZTATAS_KEZDETE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FOGLALKOZTATAS_KEZDETE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FOGLALKOZTATAS_VEGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FOGLALKOZTATAS_VEGE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FOGLALKOZTATAS_VEGE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/munkavallaloks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MunkavallalokRepository munkavallalokRepository;

    @Autowired
    private MunkavallalokMapper munkavallalokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMunkavallalokMockMvc;

    private Munkavallalok munkavallalok;

    private Munkavallalok insertedMunkavallalok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Munkavallalok createEntity() {
        return new Munkavallalok()
            .foglalkoztatasTipusa(DEFAULT_FOGLALKOZTATAS_TIPUSA)
            .foglalkoztatasKezdete(DEFAULT_FOGLALKOZTATAS_KEZDETE)
            .foglalkoztatasVege(DEFAULT_FOGLALKOZTATAS_VEGE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Munkavallalok createUpdatedEntity() {
        return new Munkavallalok()
            .foglalkoztatasTipusa(UPDATED_FOGLALKOZTATAS_TIPUSA)
            .foglalkoztatasKezdete(UPDATED_FOGLALKOZTATAS_KEZDETE)
            .foglalkoztatasVege(UPDATED_FOGLALKOZTATAS_VEGE);
    }

    @BeforeEach
    void initTest() {
        munkavallalok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMunkavallalok != null) {
            munkavallalokRepository.delete(insertedMunkavallalok);
            insertedMunkavallalok = null;
        }
    }

    @Test
    @Transactional
    void createMunkavallalok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);
        var returnedMunkavallalokDTO = om.readValue(
            restMunkavallalokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(munkavallalokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MunkavallalokDTO.class
        );

        // Validate the Munkavallalok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMunkavallalok = munkavallalokMapper.toEntity(returnedMunkavallalokDTO);
        assertMunkavallalokUpdatableFieldsEquals(returnedMunkavallalok, getPersistedMunkavallalok(returnedMunkavallalok));

        insertedMunkavallalok = returnedMunkavallalok;
    }

    @Test
    @Transactional
    void createMunkavallalokWithExistingId() throws Exception {
        // Create the Munkavallalok with an existing ID
        munkavallalok.setId(1L);
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunkavallalokMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMunkavallaloks() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList
        restMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(munkavallalok.getId().intValue())))
            .andExpect(jsonPath("$.[*].foglalkoztatasTipusa").value(hasItem(DEFAULT_FOGLALKOZTATAS_TIPUSA)))
            .andExpect(jsonPath("$.[*].foglalkoztatasKezdete").value(hasItem(DEFAULT_FOGLALKOZTATAS_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].foglalkoztatasVege").value(hasItem(DEFAULT_FOGLALKOZTATAS_VEGE.toString())));
    }

    @Test
    @Transactional
    void getMunkavallalok() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get the munkavallalok
        restMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL_ID, munkavallalok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(munkavallalok.getId().intValue()))
            .andExpect(jsonPath("$.foglalkoztatasTipusa").value(DEFAULT_FOGLALKOZTATAS_TIPUSA))
            .andExpect(jsonPath("$.foglalkoztatasKezdete").value(DEFAULT_FOGLALKOZTATAS_KEZDETE.toString()))
            .andExpect(jsonPath("$.foglalkoztatasVege").value(DEFAULT_FOGLALKOZTATAS_VEGE.toString()));
    }

    @Test
    @Transactional
    void getMunkavallaloksByIdFiltering() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        Long id = munkavallalok.getId();

        defaultMunkavallalokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMunkavallalokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMunkavallalokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasTipusaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasTipusa equals to
        defaultMunkavallalokFiltering(
            "foglalkoztatasTipusa.equals=" + DEFAULT_FOGLALKOZTATAS_TIPUSA,
            "foglalkoztatasTipusa.equals=" + UPDATED_FOGLALKOZTATAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasTipusaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasTipusa in
        defaultMunkavallalokFiltering(
            "foglalkoztatasTipusa.in=" + DEFAULT_FOGLALKOZTATAS_TIPUSA + "," + UPDATED_FOGLALKOZTATAS_TIPUSA,
            "foglalkoztatasTipusa.in=" + UPDATED_FOGLALKOZTATAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasTipusaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasTipusa is not null
        defaultMunkavallalokFiltering("foglalkoztatasTipusa.specified=true", "foglalkoztatasTipusa.specified=false");
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasTipusaContainsSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasTipusa contains
        defaultMunkavallalokFiltering(
            "foglalkoztatasTipusa.contains=" + DEFAULT_FOGLALKOZTATAS_TIPUSA,
            "foglalkoztatasTipusa.contains=" + UPDATED_FOGLALKOZTATAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasTipusaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasTipusa does not contain
        defaultMunkavallalokFiltering(
            "foglalkoztatasTipusa.doesNotContain=" + UPDATED_FOGLALKOZTATAS_TIPUSA,
            "foglalkoztatasTipusa.doesNotContain=" + DEFAULT_FOGLALKOZTATAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete equals to
        defaultMunkavallalokFiltering(
            "foglalkoztatasKezdete.equals=" + DEFAULT_FOGLALKOZTATAS_KEZDETE,
            "foglalkoztatasKezdete.equals=" + UPDATED_FOGLALKOZTATAS_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete in
        defaultMunkavallalokFiltering(
            "foglalkoztatasKezdete.in=" + DEFAULT_FOGLALKOZTATAS_KEZDETE + "," + UPDATED_FOGLALKOZTATAS_KEZDETE,
            "foglalkoztatasKezdete.in=" + UPDATED_FOGLALKOZTATAS_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete is not null
        defaultMunkavallalokFiltering("foglalkoztatasKezdete.specified=true", "foglalkoztatasKezdete.specified=false");
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete is greater than or equal to
        defaultMunkavallalokFiltering(
            "foglalkoztatasKezdete.greaterThanOrEqual=" + DEFAULT_FOGLALKOZTATAS_KEZDETE,
            "foglalkoztatasKezdete.greaterThanOrEqual=" + UPDATED_FOGLALKOZTATAS_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete is less than or equal to
        defaultMunkavallalokFiltering(
            "foglalkoztatasKezdete.lessThanOrEqual=" + DEFAULT_FOGLALKOZTATAS_KEZDETE,
            "foglalkoztatasKezdete.lessThanOrEqual=" + SMALLER_FOGLALKOZTATAS_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete is less than
        defaultMunkavallalokFiltering(
            "foglalkoztatasKezdete.lessThan=" + UPDATED_FOGLALKOZTATAS_KEZDETE,
            "foglalkoztatasKezdete.lessThan=" + DEFAULT_FOGLALKOZTATAS_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasKezdeteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasKezdete is greater than
        defaultMunkavallalokFiltering(
            "foglalkoztatasKezdete.greaterThan=" + SMALLER_FOGLALKOZTATAS_KEZDETE,
            "foglalkoztatasKezdete.greaterThan=" + DEFAULT_FOGLALKOZTATAS_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege equals to
        defaultMunkavallalokFiltering(
            "foglalkoztatasVege.equals=" + DEFAULT_FOGLALKOZTATAS_VEGE,
            "foglalkoztatasVege.equals=" + UPDATED_FOGLALKOZTATAS_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege in
        defaultMunkavallalokFiltering(
            "foglalkoztatasVege.in=" + DEFAULT_FOGLALKOZTATAS_VEGE + "," + UPDATED_FOGLALKOZTATAS_VEGE,
            "foglalkoztatasVege.in=" + UPDATED_FOGLALKOZTATAS_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege is not null
        defaultMunkavallalokFiltering("foglalkoztatasVege.specified=true", "foglalkoztatasVege.specified=false");
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege is greater than or equal to
        defaultMunkavallalokFiltering(
            "foglalkoztatasVege.greaterThanOrEqual=" + DEFAULT_FOGLALKOZTATAS_VEGE,
            "foglalkoztatasVege.greaterThanOrEqual=" + UPDATED_FOGLALKOZTATAS_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege is less than or equal to
        defaultMunkavallalokFiltering(
            "foglalkoztatasVege.lessThanOrEqual=" + DEFAULT_FOGLALKOZTATAS_VEGE,
            "foglalkoztatasVege.lessThanOrEqual=" + SMALLER_FOGLALKOZTATAS_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege is less than
        defaultMunkavallalokFiltering(
            "foglalkoztatasVege.lessThan=" + UPDATED_FOGLALKOZTATAS_VEGE,
            "foglalkoztatasVege.lessThan=" + DEFAULT_FOGLALKOZTATAS_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByFoglalkoztatasVegeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        // Get all the munkavallalokList where foglalkoztatasVege is greater than
        defaultMunkavallalokFiltering(
            "foglalkoztatasVege.greaterThan=" + SMALLER_FOGLALKOZTATAS_VEGE,
            "foglalkoztatasVege.greaterThan=" + DEFAULT_FOGLALKOZTATAS_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMunkavallaloksBySajatCegIsEqualToSomething() throws Exception {
        SajatCegAlapadatok sajatCeg;
        if (TestUtil.findAll(em, SajatCegAlapadatok.class).isEmpty()) {
            munkavallalokRepository.saveAndFlush(munkavallalok);
            sajatCeg = SajatCegAlapadatokResourceIT.createEntity();
        } else {
            sajatCeg = TestUtil.findAll(em, SajatCegAlapadatok.class).get(0);
        }
        em.persist(sajatCeg);
        em.flush();
        munkavallalok.setSajatCeg(sajatCeg);
        munkavallalokRepository.saveAndFlush(munkavallalok);
        Long sajatCegId = sajatCeg.getId();
        // Get all the munkavallalokList where sajatCeg equals to sajatCegId
        defaultMunkavallalokShouldBeFound("sajatCegId.equals=" + sajatCegId);

        // Get all the munkavallalokList where sajatCeg equals to (sajatCegId + 1)
        defaultMunkavallalokShouldNotBeFound("sajatCegId.equals=" + (sajatCegId + 1));
    }

    @Test
    @Transactional
    void getAllMunkavallaloksByMaganszemelyIsEqualToSomething() throws Exception {
        Maganszemelyek maganszemely;
        if (TestUtil.findAll(em, Maganszemelyek.class).isEmpty()) {
            munkavallalokRepository.saveAndFlush(munkavallalok);
            maganszemely = MaganszemelyekResourceIT.createEntity();
        } else {
            maganszemely = TestUtil.findAll(em, Maganszemelyek.class).get(0);
        }
        em.persist(maganszemely);
        em.flush();
        munkavallalok.setMaganszemely(maganszemely);
        munkavallalokRepository.saveAndFlush(munkavallalok);
        Long maganszemelyId = maganszemely.getId();
        // Get all the munkavallalokList where maganszemely equals to maganszemelyId
        defaultMunkavallalokShouldBeFound("maganszemelyId.equals=" + maganszemelyId);

        // Get all the munkavallalokList where maganszemely equals to (maganszemelyId + 1)
        defaultMunkavallalokShouldNotBeFound("maganszemelyId.equals=" + (maganszemelyId + 1));
    }

    private void defaultMunkavallalokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMunkavallalokShouldBeFound(shouldBeFound);
        defaultMunkavallalokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMunkavallalokShouldBeFound(String filter) throws Exception {
        restMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(munkavallalok.getId().intValue())))
            .andExpect(jsonPath("$.[*].foglalkoztatasTipusa").value(hasItem(DEFAULT_FOGLALKOZTATAS_TIPUSA)))
            .andExpect(jsonPath("$.[*].foglalkoztatasKezdete").value(hasItem(DEFAULT_FOGLALKOZTATAS_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].foglalkoztatasVege").value(hasItem(DEFAULT_FOGLALKOZTATAS_VEGE.toString())));

        // Check, that the count call also returns 1
        restMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMunkavallalokShouldNotBeFound(String filter) throws Exception {
        restMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMunkavallalokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMunkavallalok() throws Exception {
        // Get the munkavallalok
        restMunkavallalokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMunkavallalok() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the munkavallalok
        Munkavallalok updatedMunkavallalok = munkavallalokRepository.findById(munkavallalok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMunkavallalok are not directly saved in db
        em.detach(updatedMunkavallalok);
        updatedMunkavallalok
            .foglalkoztatasTipusa(UPDATED_FOGLALKOZTATAS_TIPUSA)
            .foglalkoztatasKezdete(UPDATED_FOGLALKOZTATAS_KEZDETE)
            .foglalkoztatasVege(UPDATED_FOGLALKOZTATAS_VEGE);
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(updatedMunkavallalok);

        restMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, munkavallalokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isOk());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMunkavallalokToMatchAllProperties(updatedMunkavallalok);
    }

    @Test
    @Transactional
    void putNonExistingMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        munkavallalok.setId(longCount.incrementAndGet());

        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, munkavallalokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        munkavallalok.setId(longCount.incrementAndGet());

        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        munkavallalok.setId(longCount.incrementAndGet());

        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMunkavallalokMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMunkavallalokWithPatch() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the munkavallalok using partial update
        Munkavallalok partialUpdatedMunkavallalok = new Munkavallalok();
        partialUpdatedMunkavallalok.setId(munkavallalok.getId());

        partialUpdatedMunkavallalok
            .foglalkoztatasTipusa(UPDATED_FOGLALKOZTATAS_TIPUSA)
            .foglalkoztatasKezdete(UPDATED_FOGLALKOZTATAS_KEZDETE)
            .foglalkoztatasVege(UPDATED_FOGLALKOZTATAS_VEGE);

        restMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMunkavallalok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMunkavallalok))
            )
            .andExpect(status().isOk());

        // Validate the Munkavallalok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMunkavallalokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMunkavallalok, munkavallalok),
            getPersistedMunkavallalok(munkavallalok)
        );
    }

    @Test
    @Transactional
    void fullUpdateMunkavallalokWithPatch() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the munkavallalok using partial update
        Munkavallalok partialUpdatedMunkavallalok = new Munkavallalok();
        partialUpdatedMunkavallalok.setId(munkavallalok.getId());

        partialUpdatedMunkavallalok
            .foglalkoztatasTipusa(UPDATED_FOGLALKOZTATAS_TIPUSA)
            .foglalkoztatasKezdete(UPDATED_FOGLALKOZTATAS_KEZDETE)
            .foglalkoztatasVege(UPDATED_FOGLALKOZTATAS_VEGE);

        restMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMunkavallalok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMunkavallalok))
            )
            .andExpect(status().isOk());

        // Validate the Munkavallalok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMunkavallalokUpdatableFieldsEquals(partialUpdatedMunkavallalok, getPersistedMunkavallalok(partialUpdatedMunkavallalok));
    }

    @Test
    @Transactional
    void patchNonExistingMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        munkavallalok.setId(longCount.incrementAndGet());

        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, munkavallalokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        munkavallalok.setId(longCount.incrementAndGet());

        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMunkavallalok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        munkavallalok.setId(longCount.incrementAndGet());

        // Create the Munkavallalok
        MunkavallalokDTO munkavallalokDTO = munkavallalokMapper.toDto(munkavallalok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMunkavallalokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(munkavallalokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Munkavallalok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMunkavallalok() throws Exception {
        // Initialize the database
        insertedMunkavallalok = munkavallalokRepository.saveAndFlush(munkavallalok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the munkavallalok
        restMunkavallalokMockMvc
            .perform(delete(ENTITY_API_URL_ID, munkavallalok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return munkavallalokRepository.count();
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

    protected Munkavallalok getPersistedMunkavallalok(Munkavallalok munkavallalok) {
        return munkavallalokRepository.findById(munkavallalok.getId()).orElseThrow();
    }

    protected void assertPersistedMunkavallalokToMatchAllProperties(Munkavallalok expectedMunkavallalok) {
        assertMunkavallalokAllPropertiesEquals(expectedMunkavallalok, getPersistedMunkavallalok(expectedMunkavallalok));
    }

    protected void assertPersistedMunkavallalokToMatchUpdatableProperties(Munkavallalok expectedMunkavallalok) {
        assertMunkavallalokAllUpdatablePropertiesEquals(expectedMunkavallalok, getPersistedMunkavallalok(expectedMunkavallalok));
    }
}

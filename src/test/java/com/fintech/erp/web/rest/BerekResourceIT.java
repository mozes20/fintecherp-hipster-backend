package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.BerekAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.fintech.erp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Berek;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.repository.BerekRepository;
import com.fintech.erp.service.dto.BerekDTO;
import com.fintech.erp.service.mapper.BerekMapper;
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
 * Integration tests for the {@link BerekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BerekResourceIT {

    private static final LocalDate DEFAULT_ERVENYESSEG_KEZDETE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ERVENYESSEG_KEZDETE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ERVENYESSEG_KEZDETE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ = new BigDecimal(1 - 1);

    private static final String DEFAULT_MUNKASZERZODES = "AAAAAAAAAA";
    private static final String UPDATED_MUNKASZERZODES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TELJES_KOLTSEG = new BigDecimal(1);
    private static final BigDecimal UPDATED_TELJES_KOLTSEG = new BigDecimal(2);
    private static final BigDecimal SMALLER_TELJES_KOLTSEG = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/bereks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BerekRepository berekRepository;

    @Autowired
    private BerekMapper berekMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBerekMockMvc;

    private Berek berek;

    private Berek insertedBerek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Berek createEntity() {
        return new Berek()
            .ervenyessegKezdete(DEFAULT_ERVENYESSEG_KEZDETE)
            .bruttoHaviMunkaberVagyNapdij(DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ)
            .munkaszerzodes(DEFAULT_MUNKASZERZODES)
            .teljesKoltseg(DEFAULT_TELJES_KOLTSEG);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Berek createUpdatedEntity() {
        return new Berek()
            .ervenyessegKezdete(UPDATED_ERVENYESSEG_KEZDETE)
            .bruttoHaviMunkaberVagyNapdij(UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ)
            .munkaszerzodes(UPDATED_MUNKASZERZODES)
            .teljesKoltseg(UPDATED_TELJES_KOLTSEG);
    }

    @BeforeEach
    void initTest() {
        berek = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBerek != null) {
            berekRepository.delete(insertedBerek);
            insertedBerek = null;
        }
    }

    @Test
    @Transactional
    void createBerek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);
        var returnedBerekDTO = om.readValue(
            restBerekMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(berekDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BerekDTO.class
        );

        // Validate the Berek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBerek = berekMapper.toEntity(returnedBerekDTO);
        assertBerekUpdatableFieldsEquals(returnedBerek, getPersistedBerek(returnedBerek));

        insertedBerek = returnedBerek;
    }

    @Test
    @Transactional
    void createBerekWithExistingId() throws Exception {
        // Create the Berek with an existing ID
        berek.setId(1L);
        BerekDTO berekDTO = berekMapper.toDto(berek);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBerekMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(berekDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkErvenyessegKezdeteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        berek.setErvenyessegKezdete(null);

        // Create the Berek, which fails.
        BerekDTO berekDTO = berekMapper.toDto(berek);

        restBerekMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(berekDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBruttoHaviMunkaberVagyNapdijIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        berek.setBruttoHaviMunkaberVagyNapdij(null);

        // Create the Berek, which fails.
        BerekDTO berekDTO = berekMapper.toDto(berek);

        restBerekMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(berekDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBereks() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList
        restBerekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(berek.getId().intValue())))
            .andExpect(jsonPath("$.[*].ervenyessegKezdete").value(hasItem(DEFAULT_ERVENYESSEG_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].bruttoHaviMunkaberVagyNapdij").value(hasItem(sameNumber(DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ))))
            .andExpect(jsonPath("$.[*].munkaszerzodes").value(hasItem(DEFAULT_MUNKASZERZODES)))
            .andExpect(jsonPath("$.[*].teljesKoltseg").value(hasItem(sameNumber(DEFAULT_TELJES_KOLTSEG))));
    }

    @Test
    @Transactional
    void getBerek() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get the berek
        restBerekMockMvc
            .perform(get(ENTITY_API_URL_ID, berek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(berek.getId().intValue()))
            .andExpect(jsonPath("$.ervenyessegKezdete").value(DEFAULT_ERVENYESSEG_KEZDETE.toString()))
            .andExpect(jsonPath("$.bruttoHaviMunkaberVagyNapdij").value(sameNumber(DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ)))
            .andExpect(jsonPath("$.munkaszerzodes").value(DEFAULT_MUNKASZERZODES))
            .andExpect(jsonPath("$.teljesKoltseg").value(sameNumber(DEFAULT_TELJES_KOLTSEG)));
    }

    @Test
    @Transactional
    void getBereksByIdFiltering() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        Long id = berek.getId();

        defaultBerekFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBerekFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBerekFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete equals to
        defaultBerekFiltering(
            "ervenyessegKezdete.equals=" + DEFAULT_ERVENYESSEG_KEZDETE,
            "ervenyessegKezdete.equals=" + UPDATED_ERVENYESSEG_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete in
        defaultBerekFiltering(
            "ervenyessegKezdete.in=" + DEFAULT_ERVENYESSEG_KEZDETE + "," + UPDATED_ERVENYESSEG_KEZDETE,
            "ervenyessegKezdete.in=" + UPDATED_ERVENYESSEG_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete is not null
        defaultBerekFiltering("ervenyessegKezdete.specified=true", "ervenyessegKezdete.specified=false");
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete is greater than or equal to
        defaultBerekFiltering(
            "ervenyessegKezdete.greaterThanOrEqual=" + DEFAULT_ERVENYESSEG_KEZDETE,
            "ervenyessegKezdete.greaterThanOrEqual=" + UPDATED_ERVENYESSEG_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete is less than or equal to
        defaultBerekFiltering(
            "ervenyessegKezdete.lessThanOrEqual=" + DEFAULT_ERVENYESSEG_KEZDETE,
            "ervenyessegKezdete.lessThanOrEqual=" + SMALLER_ERVENYESSEG_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete is less than
        defaultBerekFiltering(
            "ervenyessegKezdete.lessThan=" + UPDATED_ERVENYESSEG_KEZDETE,
            "ervenyessegKezdete.lessThan=" + DEFAULT_ERVENYESSEG_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllBereksByErvenyessegKezdeteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where ervenyessegKezdete is greater than
        defaultBerekFiltering(
            "ervenyessegKezdete.greaterThan=" + SMALLER_ERVENYESSEG_KEZDETE,
            "ervenyessegKezdete.greaterThan=" + DEFAULT_ERVENYESSEG_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij equals to
        defaultBerekFiltering(
            "bruttoHaviMunkaberVagyNapdij.equals=" + DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ,
            "bruttoHaviMunkaberVagyNapdij.equals=" + UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ
        );
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij in
        defaultBerekFiltering(
            "bruttoHaviMunkaberVagyNapdij.in=" + DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ + "," + UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ,
            "bruttoHaviMunkaberVagyNapdij.in=" + UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ
        );
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij is not null
        defaultBerekFiltering("bruttoHaviMunkaberVagyNapdij.specified=true", "bruttoHaviMunkaberVagyNapdij.specified=false");
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij is greater than or equal to
        defaultBerekFiltering(
            "bruttoHaviMunkaberVagyNapdij.greaterThanOrEqual=" + DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ,
            "bruttoHaviMunkaberVagyNapdij.greaterThanOrEqual=" + UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ
        );
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij is less than or equal to
        defaultBerekFiltering(
            "bruttoHaviMunkaberVagyNapdij.lessThanOrEqual=" + DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ,
            "bruttoHaviMunkaberVagyNapdij.lessThanOrEqual=" + SMALLER_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ
        );
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij is less than
        defaultBerekFiltering(
            "bruttoHaviMunkaberVagyNapdij.lessThan=" + UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ,
            "bruttoHaviMunkaberVagyNapdij.lessThan=" + DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ
        );
    }

    @Test
    @Transactional
    void getAllBereksByBruttoHaviMunkaberVagyNapdijIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where bruttoHaviMunkaberVagyNapdij is greater than
        defaultBerekFiltering(
            "bruttoHaviMunkaberVagyNapdij.greaterThan=" + SMALLER_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ,
            "bruttoHaviMunkaberVagyNapdij.greaterThan=" + DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ
        );
    }

    @Test
    @Transactional
    void getAllBereksByMunkaszerzodesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where munkaszerzodes equals to
        defaultBerekFiltering("munkaszerzodes.equals=" + DEFAULT_MUNKASZERZODES, "munkaszerzodes.equals=" + UPDATED_MUNKASZERZODES);
    }

    @Test
    @Transactional
    void getAllBereksByMunkaszerzodesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where munkaszerzodes in
        defaultBerekFiltering(
            "munkaszerzodes.in=" + DEFAULT_MUNKASZERZODES + "," + UPDATED_MUNKASZERZODES,
            "munkaszerzodes.in=" + UPDATED_MUNKASZERZODES
        );
    }

    @Test
    @Transactional
    void getAllBereksByMunkaszerzodesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where munkaszerzodes is not null
        defaultBerekFiltering("munkaszerzodes.specified=true", "munkaszerzodes.specified=false");
    }

    @Test
    @Transactional
    void getAllBereksByMunkaszerzodesContainsSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where munkaszerzodes contains
        defaultBerekFiltering("munkaszerzodes.contains=" + DEFAULT_MUNKASZERZODES, "munkaszerzodes.contains=" + UPDATED_MUNKASZERZODES);
    }

    @Test
    @Transactional
    void getAllBereksByMunkaszerzodesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where munkaszerzodes does not contain
        defaultBerekFiltering(
            "munkaszerzodes.doesNotContain=" + UPDATED_MUNKASZERZODES,
            "munkaszerzodes.doesNotContain=" + DEFAULT_MUNKASZERZODES
        );
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg equals to
        defaultBerekFiltering("teljesKoltseg.equals=" + DEFAULT_TELJES_KOLTSEG, "teljesKoltseg.equals=" + UPDATED_TELJES_KOLTSEG);
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg in
        defaultBerekFiltering(
            "teljesKoltseg.in=" + DEFAULT_TELJES_KOLTSEG + "," + UPDATED_TELJES_KOLTSEG,
            "teljesKoltseg.in=" + UPDATED_TELJES_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg is not null
        defaultBerekFiltering("teljesKoltseg.specified=true", "teljesKoltseg.specified=false");
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg is greater than or equal to
        defaultBerekFiltering(
            "teljesKoltseg.greaterThanOrEqual=" + DEFAULT_TELJES_KOLTSEG,
            "teljesKoltseg.greaterThanOrEqual=" + UPDATED_TELJES_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg is less than or equal to
        defaultBerekFiltering(
            "teljesKoltseg.lessThanOrEqual=" + DEFAULT_TELJES_KOLTSEG,
            "teljesKoltseg.lessThanOrEqual=" + SMALLER_TELJES_KOLTSEG
        );
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg is less than
        defaultBerekFiltering("teljesKoltseg.lessThan=" + UPDATED_TELJES_KOLTSEG, "teljesKoltseg.lessThan=" + DEFAULT_TELJES_KOLTSEG);
    }

    @Test
    @Transactional
    void getAllBereksByTeljesKoltsegIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        // Get all the berekList where teljesKoltseg is greater than
        defaultBerekFiltering("teljesKoltseg.greaterThan=" + SMALLER_TELJES_KOLTSEG, "teljesKoltseg.greaterThan=" + DEFAULT_TELJES_KOLTSEG);
    }

    @Test
    @Transactional
    void getAllBereksByMunkavallaloIsEqualToSomething() throws Exception {
        Munkavallalok munkavallalo;
        if (TestUtil.findAll(em, Munkavallalok.class).isEmpty()) {
            berekRepository.saveAndFlush(berek);
            munkavallalo = MunkavallalokResourceIT.createEntity();
        } else {
            munkavallalo = TestUtil.findAll(em, Munkavallalok.class).get(0);
        }
        em.persist(munkavallalo);
        em.flush();
        berek.setMunkavallalo(munkavallalo);
        berekRepository.saveAndFlush(berek);
        Long munkavallaloId = munkavallalo.getId();
        // Get all the berekList where munkavallalo equals to munkavallaloId
        defaultBerekShouldBeFound("munkavallaloId.equals=" + munkavallaloId);

        // Get all the berekList where munkavallalo equals to (munkavallaloId + 1)
        defaultBerekShouldNotBeFound("munkavallaloId.equals=" + (munkavallaloId + 1));
    }

    private void defaultBerekFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBerekShouldBeFound(shouldBeFound);
        defaultBerekShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBerekShouldBeFound(String filter) throws Exception {
        restBerekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(berek.getId().intValue())))
            .andExpect(jsonPath("$.[*].ervenyessegKezdete").value(hasItem(DEFAULT_ERVENYESSEG_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].bruttoHaviMunkaberVagyNapdij").value(hasItem(sameNumber(DEFAULT_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ))))
            .andExpect(jsonPath("$.[*].munkaszerzodes").value(hasItem(DEFAULT_MUNKASZERZODES)))
            .andExpect(jsonPath("$.[*].teljesKoltseg").value(hasItem(sameNumber(DEFAULT_TELJES_KOLTSEG))));

        // Check, that the count call also returns 1
        restBerekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBerekShouldNotBeFound(String filter) throws Exception {
        restBerekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBerekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBerek() throws Exception {
        // Get the berek
        restBerekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBerek() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the berek
        Berek updatedBerek = berekRepository.findById(berek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBerek are not directly saved in db
        em.detach(updatedBerek);
        updatedBerek
            .ervenyessegKezdete(UPDATED_ERVENYESSEG_KEZDETE)
            .bruttoHaviMunkaberVagyNapdij(UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ)
            .munkaszerzodes(UPDATED_MUNKASZERZODES)
            .teljesKoltseg(UPDATED_TELJES_KOLTSEG);
        BerekDTO berekDTO = berekMapper.toDto(updatedBerek);

        restBerekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, berekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(berekDTO))
            )
            .andExpect(status().isOk());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBerekToMatchAllProperties(updatedBerek);
    }

    @Test
    @Transactional
    void putNonExistingBerek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        berek.setId(longCount.incrementAndGet());

        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBerekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, berekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(berekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBerek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        berek.setId(longCount.incrementAndGet());

        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBerekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(berekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBerek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        berek.setId(longCount.incrementAndGet());

        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBerekMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(berekDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBerekWithPatch() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the berek using partial update
        Berek partialUpdatedBerek = new Berek();
        partialUpdatedBerek.setId(berek.getId());

        partialUpdatedBerek
            .ervenyessegKezdete(UPDATED_ERVENYESSEG_KEZDETE)
            .bruttoHaviMunkaberVagyNapdij(UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ);

        restBerekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBerek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBerek))
            )
            .andExpect(status().isOk());

        // Validate the Berek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBerekUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBerek, berek), getPersistedBerek(berek));
    }

    @Test
    @Transactional
    void fullUpdateBerekWithPatch() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the berek using partial update
        Berek partialUpdatedBerek = new Berek();
        partialUpdatedBerek.setId(berek.getId());

        partialUpdatedBerek
            .ervenyessegKezdete(UPDATED_ERVENYESSEG_KEZDETE)
            .bruttoHaviMunkaberVagyNapdij(UPDATED_BRUTTO_HAVI_MUNKABER_VAGY_NAPDIJ)
            .munkaszerzodes(UPDATED_MUNKASZERZODES)
            .teljesKoltseg(UPDATED_TELJES_KOLTSEG);

        restBerekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBerek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBerek))
            )
            .andExpect(status().isOk());

        // Validate the Berek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBerekUpdatableFieldsEquals(partialUpdatedBerek, getPersistedBerek(partialUpdatedBerek));
    }

    @Test
    @Transactional
    void patchNonExistingBerek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        berek.setId(longCount.incrementAndGet());

        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBerekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, berekDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(berekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBerek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        berek.setId(longCount.incrementAndGet());

        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBerekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(berekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBerek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        berek.setId(longCount.incrementAndGet());

        // Create the Berek
        BerekDTO berekDTO = berekMapper.toDto(berek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBerekMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(berekDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Berek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBerek() throws Exception {
        // Initialize the database
        insertedBerek = berekRepository.saveAndFlush(berek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the berek
        restBerekMockMvc
            .perform(delete(ENTITY_API_URL_ID, berek.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return berekRepository.count();
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

    protected Berek getPersistedBerek(Berek berek) {
        return berekRepository.findById(berek.getId()).orElseThrow();
    }

    protected void assertPersistedBerekToMatchAllProperties(Berek expectedBerek) {
        assertBerekAllPropertiesEquals(expectedBerek, getPersistedBerek(expectedBerek));
    }

    protected void assertPersistedBerekToMatchUpdatableProperties(Berek expectedBerek) {
        assertBerekAllUpdatablePropertiesEquals(expectedBerek, getPersistedBerek(expectedBerek));
    }
}

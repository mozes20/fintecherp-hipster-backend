package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.UgyfelElszamolasokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.fintech.erp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.UgyfelElszamolasok;
import com.fintech.erp.repository.UgyfelElszamolasokRepository;
import com.fintech.erp.service.dto.UgyfelElszamolasokDTO;
import com.fintech.erp.service.mapper.UgyfelElszamolasokMapper;
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
 * Integration tests for the {@link UgyfelElszamolasokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UgyfelElszamolasokResourceIT {

    private static final LocalDate DEFAULT_TELJESITESI_IDOSZAK_KEZDETE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TELJESITESI_IDOSZAK_KEZDETE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TELJESITESI_IDOSZAK_KEZDETE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TELJESITESI_IDOSZAK_VEGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TELJESITESI_IDOSZAK_VEGE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TELJESITESI_IDOSZAK_VEGE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_NAPOK_SZAMA = 1;
    private static final Integer UPDATED_NAPOK_SZAMA = 2;
    private static final Integer SMALLER_NAPOK_SZAMA = 1 - 1;

    private static final BigDecimal DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG = new BigDecimal(1);
    private static final BigDecimal UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG = new BigDecimal(2);
    private static final BigDecimal SMALLER_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE = false;
    private static final Boolean UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE = true;

    private static final String ENTITY_API_URL = "/api/ugyfel-elszamolasoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UgyfelElszamolasokRepository ugyfelElszamolasokRepository;

    @Autowired
    private UgyfelElszamolasokMapper ugyfelElszamolasokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUgyfelElszamolasokMockMvc;

    private UgyfelElszamolasok ugyfelElszamolasok;

    private UgyfelElszamolasok insertedUgyfelElszamolasok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UgyfelElszamolasok createEntity() {
        return new UgyfelElszamolasok()
            .teljesitesiIdoszakKezdete(DEFAULT_TELJESITESI_IDOSZAK_KEZDETE)
            .teljesitesiIdoszakVege(DEFAULT_TELJESITESI_IDOSZAK_VEGE)
            .napokSzama(DEFAULT_NAPOK_SZAMA)
            .teljesitesIgazolasonSzereploOsszeg(DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG)
            .kapcsolodoSzamlaSorszamRogzitve(DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UgyfelElszamolasok createUpdatedEntity() {
        return new UgyfelElszamolasok()
            .teljesitesiIdoszakKezdete(UPDATED_TELJESITESI_IDOSZAK_KEZDETE)
            .teljesitesiIdoszakVege(UPDATED_TELJESITESI_IDOSZAK_VEGE)
            .napokSzama(UPDATED_NAPOK_SZAMA)
            .teljesitesIgazolasonSzereploOsszeg(UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG)
            .kapcsolodoSzamlaSorszamRogzitve(UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE);
    }

    @BeforeEach
    void initTest() {
        ugyfelElszamolasok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUgyfelElszamolasok != null) {
            ugyfelElszamolasokRepository.delete(insertedUgyfelElszamolasok);
            insertedUgyfelElszamolasok = null;
        }
    }

    @Test
    @Transactional
    void createUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);
        var returnedUgyfelElszamolasokDTO = om.readValue(
            restUgyfelElszamolasokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UgyfelElszamolasokDTO.class
        );

        // Validate the UgyfelElszamolasok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUgyfelElszamolasok = ugyfelElszamolasokMapper.toEntity(returnedUgyfelElszamolasokDTO);
        assertUgyfelElszamolasokUpdatableFieldsEquals(
            returnedUgyfelElszamolasok,
            getPersistedUgyfelElszamolasok(returnedUgyfelElszamolasok)
        );

        insertedUgyfelElszamolasok = returnedUgyfelElszamolasok;
    }

    @Test
    @Transactional
    void createUgyfelElszamolasokWithExistingId() throws Exception {
        // Create the UgyfelElszamolasok with an existing ID
        ugyfelElszamolasok.setId(1L);
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUgyfelElszamolasokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoks() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList
        restUgyfelElszamolasokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ugyfelElszamolasok.getId().intValue())))
            .andExpect(jsonPath("$.[*].teljesitesiIdoszakKezdete").value(hasItem(DEFAULT_TELJESITESI_IDOSZAK_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].teljesitesiIdoszakVege").value(hasItem(DEFAULT_TELJESITESI_IDOSZAK_VEGE.toString())))
            .andExpect(jsonPath("$.[*].napokSzama").value(hasItem(DEFAULT_NAPOK_SZAMA)))
            .andExpect(
                jsonPath("$.[*].teljesitesIgazolasonSzereploOsszeg").value(
                    hasItem(sameNumber(DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG))
                )
            )
            .andExpect(jsonPath("$.[*].kapcsolodoSzamlaSorszamRogzitve").value(hasItem(DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE)));
    }

    @Test
    @Transactional
    void getUgyfelElszamolasok() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get the ugyfelElszamolasok
        restUgyfelElszamolasokMockMvc
            .perform(get(ENTITY_API_URL_ID, ugyfelElszamolasok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ugyfelElszamolasok.getId().intValue()))
            .andExpect(jsonPath("$.teljesitesiIdoszakKezdete").value(DEFAULT_TELJESITESI_IDOSZAK_KEZDETE.toString()))
            .andExpect(jsonPath("$.teljesitesiIdoszakVege").value(DEFAULT_TELJESITESI_IDOSZAK_VEGE.toString()))
            .andExpect(jsonPath("$.napokSzama").value(DEFAULT_NAPOK_SZAMA))
            .andExpect(jsonPath("$.teljesitesIgazolasonSzereploOsszeg").value(sameNumber(DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG)))
            .andExpect(jsonPath("$.kapcsolodoSzamlaSorszamRogzitve").value(DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE));
    }

    @Test
    @Transactional
    void getUgyfelElszamolasoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        Long id = ugyfelElszamolasok.getId();

        defaultUgyfelElszamolasokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUgyfelElszamolasokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUgyfelElszamolasokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete equals to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakKezdete.equals=" + DEFAULT_TELJESITESI_IDOSZAK_KEZDETE,
            "teljesitesiIdoszakKezdete.equals=" + UPDATED_TELJESITESI_IDOSZAK_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete in
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakKezdete.in=" + DEFAULT_TELJESITESI_IDOSZAK_KEZDETE + "," + UPDATED_TELJESITESI_IDOSZAK_KEZDETE,
            "teljesitesiIdoszakKezdete.in=" + UPDATED_TELJESITESI_IDOSZAK_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete is not null
        defaultUgyfelElszamolasokFiltering("teljesitesiIdoszakKezdete.specified=true", "teljesitesiIdoszakKezdete.specified=false");
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete is greater than or equal to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakKezdete.greaterThanOrEqual=" + DEFAULT_TELJESITESI_IDOSZAK_KEZDETE,
            "teljesitesiIdoszakKezdete.greaterThanOrEqual=" + UPDATED_TELJESITESI_IDOSZAK_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete is less than or equal to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakKezdete.lessThanOrEqual=" + DEFAULT_TELJESITESI_IDOSZAK_KEZDETE,
            "teljesitesiIdoszakKezdete.lessThanOrEqual=" + SMALLER_TELJESITESI_IDOSZAK_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete is less than
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakKezdete.lessThan=" + UPDATED_TELJESITESI_IDOSZAK_KEZDETE,
            "teljesitesiIdoszakKezdete.lessThan=" + DEFAULT_TELJESITESI_IDOSZAK_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakKezdeteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakKezdete is greater than
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakKezdete.greaterThan=" + SMALLER_TELJESITESI_IDOSZAK_KEZDETE,
            "teljesitesiIdoszakKezdete.greaterThan=" + DEFAULT_TELJESITESI_IDOSZAK_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege equals to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakVege.equals=" + DEFAULT_TELJESITESI_IDOSZAK_VEGE,
            "teljesitesiIdoszakVege.equals=" + UPDATED_TELJESITESI_IDOSZAK_VEGE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege in
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakVege.in=" + DEFAULT_TELJESITESI_IDOSZAK_VEGE + "," + UPDATED_TELJESITESI_IDOSZAK_VEGE,
            "teljesitesiIdoszakVege.in=" + UPDATED_TELJESITESI_IDOSZAK_VEGE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege is not null
        defaultUgyfelElszamolasokFiltering("teljesitesiIdoszakVege.specified=true", "teljesitesiIdoszakVege.specified=false");
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege is greater than or equal to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakVege.greaterThanOrEqual=" + DEFAULT_TELJESITESI_IDOSZAK_VEGE,
            "teljesitesiIdoszakVege.greaterThanOrEqual=" + UPDATED_TELJESITESI_IDOSZAK_VEGE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege is less than or equal to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakVege.lessThanOrEqual=" + DEFAULT_TELJESITESI_IDOSZAK_VEGE,
            "teljesitesiIdoszakVege.lessThanOrEqual=" + SMALLER_TELJESITESI_IDOSZAK_VEGE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege is less than
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakVege.lessThan=" + UPDATED_TELJESITESI_IDOSZAK_VEGE,
            "teljesitesiIdoszakVege.lessThan=" + DEFAULT_TELJESITESI_IDOSZAK_VEGE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesiIdoszakVegeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesiIdoszakVege is greater than
        defaultUgyfelElszamolasokFiltering(
            "teljesitesiIdoszakVege.greaterThan=" + SMALLER_TELJESITESI_IDOSZAK_VEGE,
            "teljesitesiIdoszakVege.greaterThan=" + DEFAULT_TELJESITESI_IDOSZAK_VEGE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama equals to
        defaultUgyfelElszamolasokFiltering("napokSzama.equals=" + DEFAULT_NAPOK_SZAMA, "napokSzama.equals=" + UPDATED_NAPOK_SZAMA);
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama in
        defaultUgyfelElszamolasokFiltering(
            "napokSzama.in=" + DEFAULT_NAPOK_SZAMA + "," + UPDATED_NAPOK_SZAMA,
            "napokSzama.in=" + UPDATED_NAPOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama is not null
        defaultUgyfelElszamolasokFiltering("napokSzama.specified=true", "napokSzama.specified=false");
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama is greater than or equal to
        defaultUgyfelElszamolasokFiltering(
            "napokSzama.greaterThanOrEqual=" + DEFAULT_NAPOK_SZAMA,
            "napokSzama.greaterThanOrEqual=" + UPDATED_NAPOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama is less than or equal to
        defaultUgyfelElszamolasokFiltering(
            "napokSzama.lessThanOrEqual=" + DEFAULT_NAPOK_SZAMA,
            "napokSzama.lessThanOrEqual=" + SMALLER_NAPOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama is less than
        defaultUgyfelElszamolasokFiltering("napokSzama.lessThan=" + UPDATED_NAPOK_SZAMA, "napokSzama.lessThan=" + DEFAULT_NAPOK_SZAMA);
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByNapokSzamaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where napokSzama is greater than
        defaultUgyfelElszamolasokFiltering(
            "napokSzama.greaterThan=" + SMALLER_NAPOK_SZAMA,
            "napokSzama.greaterThan=" + DEFAULT_NAPOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg equals to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.equals=" + DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG,
            "teljesitesIgazolasonSzereploOsszeg.equals=" + UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg in
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.in=" +
            DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG +
            "," +
            UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG,
            "teljesitesIgazolasonSzereploOsszeg.in=" + UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg is not null
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.specified=true",
            "teljesitesIgazolasonSzereploOsszeg.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg is greater than or equal to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.greaterThanOrEqual=" + DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG,
            "teljesitesIgazolasonSzereploOsszeg.greaterThanOrEqual=" + UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg is less than or equal to
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.lessThanOrEqual=" + DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG,
            "teljesitesIgazolasonSzereploOsszeg.lessThanOrEqual=" + SMALLER_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg is less than
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.lessThan=" + UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG,
            "teljesitesIgazolasonSzereploOsszeg.lessThan=" + DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByTeljesitesIgazolasonSzereploOsszegIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where teljesitesIgazolasonSzereploOsszeg is greater than
        defaultUgyfelElszamolasokFiltering(
            "teljesitesIgazolasonSzereploOsszeg.greaterThan=" + SMALLER_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG,
            "teljesitesIgazolasonSzereploOsszeg.greaterThan=" + DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByKapcsolodoSzamlaSorszamRogzitveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where kapcsolodoSzamlaSorszamRogzitve equals to
        defaultUgyfelElszamolasokFiltering(
            "kapcsolodoSzamlaSorszamRogzitve.equals=" + DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE,
            "kapcsolodoSzamlaSorszamRogzitve.equals=" + UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByKapcsolodoSzamlaSorszamRogzitveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where kapcsolodoSzamlaSorszamRogzitve in
        defaultUgyfelElszamolasokFiltering(
            "kapcsolodoSzamlaSorszamRogzitve.in=" +
            DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE +
            "," +
            UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE,
            "kapcsolodoSzamlaSorszamRogzitve.in=" + UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByKapcsolodoSzamlaSorszamRogzitveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        // Get all the ugyfelElszamolasokList where kapcsolodoSzamlaSorszamRogzitve is not null
        defaultUgyfelElszamolasokFiltering(
            "kapcsolodoSzamlaSorszamRogzitve.specified=true",
            "kapcsolodoSzamlaSorszamRogzitve.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllUgyfelElszamolasoksByMegrendelesIsEqualToSomething() throws Exception {
        Megrendelesek megrendeles;
        if (TestUtil.findAll(em, Megrendelesek.class).isEmpty()) {
            ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);
            megrendeles = MegrendelesekResourceIT.createEntity();
        } else {
            megrendeles = TestUtil.findAll(em, Megrendelesek.class).get(0);
        }
        em.persist(megrendeles);
        em.flush();
        ugyfelElszamolasok.setMegrendeles(megrendeles);
        ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);
        Long megrendelesId = megrendeles.getId();
        // Get all the ugyfelElszamolasokList where megrendeles equals to megrendelesId
        defaultUgyfelElszamolasokShouldBeFound("megrendelesId.equals=" + megrendelesId);

        // Get all the ugyfelElszamolasokList where megrendeles equals to (megrendelesId + 1)
        defaultUgyfelElszamolasokShouldNotBeFound("megrendelesId.equals=" + (megrendelesId + 1));
    }

    private void defaultUgyfelElszamolasokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUgyfelElszamolasokShouldBeFound(shouldBeFound);
        defaultUgyfelElszamolasokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUgyfelElszamolasokShouldBeFound(String filter) throws Exception {
        restUgyfelElszamolasokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ugyfelElszamolasok.getId().intValue())))
            .andExpect(jsonPath("$.[*].teljesitesiIdoszakKezdete").value(hasItem(DEFAULT_TELJESITESI_IDOSZAK_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].teljesitesiIdoszakVege").value(hasItem(DEFAULT_TELJESITESI_IDOSZAK_VEGE.toString())))
            .andExpect(jsonPath("$.[*].napokSzama").value(hasItem(DEFAULT_NAPOK_SZAMA)))
            .andExpect(
                jsonPath("$.[*].teljesitesIgazolasonSzereploOsszeg").value(
                    hasItem(sameNumber(DEFAULT_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG))
                )
            )
            .andExpect(jsonPath("$.[*].kapcsolodoSzamlaSorszamRogzitve").value(hasItem(DEFAULT_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE)));

        // Check, that the count call also returns 1
        restUgyfelElszamolasokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUgyfelElszamolasokShouldNotBeFound(String filter) throws Exception {
        restUgyfelElszamolasokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUgyfelElszamolasokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUgyfelElszamolasok() throws Exception {
        // Get the ugyfelElszamolasok
        restUgyfelElszamolasokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUgyfelElszamolasok() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ugyfelElszamolasok
        UgyfelElszamolasok updatedUgyfelElszamolasok = ugyfelElszamolasokRepository.findById(ugyfelElszamolasok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUgyfelElszamolasok are not directly saved in db
        em.detach(updatedUgyfelElszamolasok);
        updatedUgyfelElszamolasok
            .teljesitesiIdoszakKezdete(UPDATED_TELJESITESI_IDOSZAK_KEZDETE)
            .teljesitesiIdoszakVege(UPDATED_TELJESITESI_IDOSZAK_VEGE)
            .napokSzama(UPDATED_NAPOK_SZAMA)
            .teljesitesIgazolasonSzereploOsszeg(UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG)
            .kapcsolodoSzamlaSorszamRogzitve(UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE);
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(updatedUgyfelElszamolasok);

        restUgyfelElszamolasokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ugyfelElszamolasokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isOk());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUgyfelElszamolasokToMatchAllProperties(updatedUgyfelElszamolasok);
    }

    @Test
    @Transactional
    void putNonExistingUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ugyfelElszamolasok.setId(longCount.incrementAndGet());

        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUgyfelElszamolasokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ugyfelElszamolasokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ugyfelElszamolasok.setId(longCount.incrementAndGet());

        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUgyfelElszamolasokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ugyfelElszamolasok.setId(longCount.incrementAndGet());

        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUgyfelElszamolasokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUgyfelElszamolasokWithPatch() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ugyfelElszamolasok using partial update
        UgyfelElszamolasok partialUpdatedUgyfelElszamolasok = new UgyfelElszamolasok();
        partialUpdatedUgyfelElszamolasok.setId(ugyfelElszamolasok.getId());

        partialUpdatedUgyfelElszamolasok
            .teljesitesiIdoszakVege(UPDATED_TELJESITESI_IDOSZAK_VEGE)
            .teljesitesIgazolasonSzereploOsszeg(UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG)
            .kapcsolodoSzamlaSorszamRogzitve(UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE);

        restUgyfelElszamolasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUgyfelElszamolasok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUgyfelElszamolasok))
            )
            .andExpect(status().isOk());

        // Validate the UgyfelElszamolasok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUgyfelElszamolasokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUgyfelElszamolasok, ugyfelElszamolasok),
            getPersistedUgyfelElszamolasok(ugyfelElszamolasok)
        );
    }

    @Test
    @Transactional
    void fullUpdateUgyfelElszamolasokWithPatch() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ugyfelElszamolasok using partial update
        UgyfelElszamolasok partialUpdatedUgyfelElszamolasok = new UgyfelElszamolasok();
        partialUpdatedUgyfelElszamolasok.setId(ugyfelElszamolasok.getId());

        partialUpdatedUgyfelElszamolasok
            .teljesitesiIdoszakKezdete(UPDATED_TELJESITESI_IDOSZAK_KEZDETE)
            .teljesitesiIdoszakVege(UPDATED_TELJESITESI_IDOSZAK_VEGE)
            .napokSzama(UPDATED_NAPOK_SZAMA)
            .teljesitesIgazolasonSzereploOsszeg(UPDATED_TELJESITES_IGAZOLASON_SZEREPLO_OSSZEG)
            .kapcsolodoSzamlaSorszamRogzitve(UPDATED_KAPCSOLODO_SZAMLA_SORSZAM_ROGZITVE);

        restUgyfelElszamolasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUgyfelElszamolasok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUgyfelElszamolasok))
            )
            .andExpect(status().isOk());

        // Validate the UgyfelElszamolasok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUgyfelElszamolasokUpdatableFieldsEquals(
            partialUpdatedUgyfelElszamolasok,
            getPersistedUgyfelElszamolasok(partialUpdatedUgyfelElszamolasok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ugyfelElszamolasok.setId(longCount.incrementAndGet());

        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUgyfelElszamolasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ugyfelElszamolasokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ugyfelElszamolasok.setId(longCount.incrementAndGet());

        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUgyfelElszamolasokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUgyfelElszamolasok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ugyfelElszamolasok.setId(longCount.incrementAndGet());

        // Create the UgyfelElszamolasok
        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = ugyfelElszamolasokMapper.toDto(ugyfelElszamolasok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUgyfelElszamolasokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ugyfelElszamolasokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UgyfelElszamolasok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUgyfelElszamolasok() throws Exception {
        // Initialize the database
        insertedUgyfelElszamolasok = ugyfelElszamolasokRepository.saveAndFlush(ugyfelElszamolasok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ugyfelElszamolasok
        restUgyfelElszamolasokMockMvc
            .perform(delete(ENTITY_API_URL_ID, ugyfelElszamolasok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ugyfelElszamolasokRepository.count();
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

    protected UgyfelElszamolasok getPersistedUgyfelElszamolasok(UgyfelElszamolasok ugyfelElszamolasok) {
        return ugyfelElszamolasokRepository.findById(ugyfelElszamolasok.getId()).orElseThrow();
    }

    protected void assertPersistedUgyfelElszamolasokToMatchAllProperties(UgyfelElszamolasok expectedUgyfelElszamolasok) {
        assertUgyfelElszamolasokAllPropertiesEquals(expectedUgyfelElszamolasok, getPersistedUgyfelElszamolasok(expectedUgyfelElszamolasok));
    }

    protected void assertPersistedUgyfelElszamolasokToMatchUpdatableProperties(UgyfelElszamolasok expectedUgyfelElszamolasok) {
        assertUgyfelElszamolasokAllUpdatablePropertiesEquals(
            expectedUgyfelElszamolasok,
            getPersistedUgyfelElszamolasok(expectedUgyfelElszamolasok)
        );
    }
}

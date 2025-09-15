package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.CegAlapadatokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.repository.CegAlapadatokRepository;
import com.fintech.erp.service.dto.CegAlapadatokDTO;
import com.fintech.erp.service.mapper.CegAlapadatokMapper;
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
 * Integration tests for the {@link CegAlapadatokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CegAlapadatokResourceIT {

    private static final String DEFAULT_CEG_NEV = "AAAAAAAAAA";
    private static final String UPDATED_CEG_NEV = "BBBBBBBBBB";

    private static final String DEFAULT_CEG_ROVID_AZONOSITO = "AAAAAAAAAA";
    private static final String UPDATED_CEG_ROVID_AZONOSITO = "BBBBBBBBBB";

    private static final String DEFAULT_CEG_SZEKHELY = "AAAAAAAAAA";
    private static final String UPDATED_CEG_SZEKHELY = "BBBBBBBBBB";

    private static final String DEFAULT_ADOSZAM = "AAAAAAAAAA";
    private static final String UPDATED_ADOSZAM = "BBBBBBBBBB";

    private static final String DEFAULT_CEGJEGYZEKSZAM = "AAAAAAAAAA";
    private static final String UPDATED_CEGJEGYZEKSZAM = "BBBBBBBBBB";

    private static final String DEFAULT_CEG_KOZPONTI_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CEG_KOZPONTI_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CEG_KOZPONTI_TEL = "AAAAAAAAAA";
    private static final String UPDATED_CEG_KOZPONTI_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ceg-alapadatoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CegAlapadatokRepository cegAlapadatokRepository;

    @Autowired
    private CegAlapadatokMapper cegAlapadatokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCegAlapadatokMockMvc;

    private CegAlapadatok cegAlapadatok;

    private CegAlapadatok insertedCegAlapadatok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CegAlapadatok createEntity() {
        return new CegAlapadatok()
            .cegNev(DEFAULT_CEG_NEV)
            .cegRovidAzonosito(DEFAULT_CEG_ROVID_AZONOSITO)
            .cegSzekhely(DEFAULT_CEG_SZEKHELY)
            .adoszam(DEFAULT_ADOSZAM)
            .cegjegyzekszam(DEFAULT_CEGJEGYZEKSZAM)
            .cegKozpontiEmail(DEFAULT_CEG_KOZPONTI_EMAIL)
            .cegKozpontiTel(DEFAULT_CEG_KOZPONTI_TEL)
            .statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CegAlapadatok createUpdatedEntity() {
        return new CegAlapadatok()
            .cegNev(UPDATED_CEG_NEV)
            .cegRovidAzonosito(UPDATED_CEG_ROVID_AZONOSITO)
            .cegSzekhely(UPDATED_CEG_SZEKHELY)
            .adoszam(UPDATED_ADOSZAM)
            .cegjegyzekszam(UPDATED_CEGJEGYZEKSZAM)
            .cegKozpontiEmail(UPDATED_CEG_KOZPONTI_EMAIL)
            .cegKozpontiTel(UPDATED_CEG_KOZPONTI_TEL)
            .statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        cegAlapadatok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCegAlapadatok != null) {
            cegAlapadatokRepository.delete(insertedCegAlapadatok);
            insertedCegAlapadatok = null;
        }
    }

    @Test
    @Transactional
    void createCegAlapadatok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);
        var returnedCegAlapadatokDTO = om.readValue(
            restCegAlapadatokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cegAlapadatokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CegAlapadatokDTO.class
        );

        // Validate the CegAlapadatok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCegAlapadatok = cegAlapadatokMapper.toEntity(returnedCegAlapadatokDTO);
        assertCegAlapadatokUpdatableFieldsEquals(returnedCegAlapadatok, getPersistedCegAlapadatok(returnedCegAlapadatok));

        insertedCegAlapadatok = returnedCegAlapadatok;
    }

    @Test
    @Transactional
    void createCegAlapadatokWithExistingId() throws Exception {
        // Create the CegAlapadatok with an existing ID
        cegAlapadatok.setId(1L);
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCegAlapadatokMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCegNevIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cegAlapadatok.setCegNev(null);

        // Create the CegAlapadatok, which fails.
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        restCegAlapadatokMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoks() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList
        restCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cegAlapadatok.getId().intValue())))
            .andExpect(jsonPath("$.[*].cegNev").value(hasItem(DEFAULT_CEG_NEV)))
            .andExpect(jsonPath("$.[*].cegRovidAzonosito").value(hasItem(DEFAULT_CEG_ROVID_AZONOSITO)))
            .andExpect(jsonPath("$.[*].cegSzekhely").value(hasItem(DEFAULT_CEG_SZEKHELY)))
            .andExpect(jsonPath("$.[*].adoszam").value(hasItem(DEFAULT_ADOSZAM)))
            .andExpect(jsonPath("$.[*].cegjegyzekszam").value(hasItem(DEFAULT_CEGJEGYZEKSZAM)))
            .andExpect(jsonPath("$.[*].cegKozpontiEmail").value(hasItem(DEFAULT_CEG_KOZPONTI_EMAIL)))
            .andExpect(jsonPath("$.[*].cegKozpontiTel").value(hasItem(DEFAULT_CEG_KOZPONTI_TEL)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getCegAlapadatok() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get the cegAlapadatok
        restCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL_ID, cegAlapadatok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cegAlapadatok.getId().intValue()))
            .andExpect(jsonPath("$.cegNev").value(DEFAULT_CEG_NEV))
            .andExpect(jsonPath("$.cegRovidAzonosito").value(DEFAULT_CEG_ROVID_AZONOSITO))
            .andExpect(jsonPath("$.cegSzekhely").value(DEFAULT_CEG_SZEKHELY))
            .andExpect(jsonPath("$.adoszam").value(DEFAULT_ADOSZAM))
            .andExpect(jsonPath("$.cegjegyzekszam").value(DEFAULT_CEGJEGYZEKSZAM))
            .andExpect(jsonPath("$.cegKozpontiEmail").value(DEFAULT_CEG_KOZPONTI_EMAIL))
            .andExpect(jsonPath("$.cegKozpontiTel").value(DEFAULT_CEG_KOZPONTI_TEL))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getCegAlapadatoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        Long id = cegAlapadatok.getId();

        defaultCegAlapadatokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCegAlapadatokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCegAlapadatokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegNevIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegNev equals to
        defaultCegAlapadatokFiltering("cegNev.equals=" + DEFAULT_CEG_NEV, "cegNev.equals=" + UPDATED_CEG_NEV);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegNevIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegNev in
        defaultCegAlapadatokFiltering("cegNev.in=" + DEFAULT_CEG_NEV + "," + UPDATED_CEG_NEV, "cegNev.in=" + UPDATED_CEG_NEV);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegNevIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegNev is not null
        defaultCegAlapadatokFiltering("cegNev.specified=true", "cegNev.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegNevContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegNev contains
        defaultCegAlapadatokFiltering("cegNev.contains=" + DEFAULT_CEG_NEV, "cegNev.contains=" + UPDATED_CEG_NEV);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegNevNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegNev does not contain
        defaultCegAlapadatokFiltering("cegNev.doesNotContain=" + UPDATED_CEG_NEV, "cegNev.doesNotContain=" + DEFAULT_CEG_NEV);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegRovidAzonositoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegRovidAzonosito equals to
        defaultCegAlapadatokFiltering(
            "cegRovidAzonosito.equals=" + DEFAULT_CEG_ROVID_AZONOSITO,
            "cegRovidAzonosito.equals=" + UPDATED_CEG_ROVID_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegRovidAzonositoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegRovidAzonosito in
        defaultCegAlapadatokFiltering(
            "cegRovidAzonosito.in=" + DEFAULT_CEG_ROVID_AZONOSITO + "," + UPDATED_CEG_ROVID_AZONOSITO,
            "cegRovidAzonosito.in=" + UPDATED_CEG_ROVID_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegRovidAzonositoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegRovidAzonosito is not null
        defaultCegAlapadatokFiltering("cegRovidAzonosito.specified=true", "cegRovidAzonosito.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegRovidAzonositoContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegRovidAzonosito contains
        defaultCegAlapadatokFiltering(
            "cegRovidAzonosito.contains=" + DEFAULT_CEG_ROVID_AZONOSITO,
            "cegRovidAzonosito.contains=" + UPDATED_CEG_ROVID_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegRovidAzonositoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegRovidAzonosito does not contain
        defaultCegAlapadatokFiltering(
            "cegRovidAzonosito.doesNotContain=" + UPDATED_CEG_ROVID_AZONOSITO,
            "cegRovidAzonosito.doesNotContain=" + DEFAULT_CEG_ROVID_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegSzekhelyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegSzekhely equals to
        defaultCegAlapadatokFiltering("cegSzekhely.equals=" + DEFAULT_CEG_SZEKHELY, "cegSzekhely.equals=" + UPDATED_CEG_SZEKHELY);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegSzekhelyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegSzekhely in
        defaultCegAlapadatokFiltering(
            "cegSzekhely.in=" + DEFAULT_CEG_SZEKHELY + "," + UPDATED_CEG_SZEKHELY,
            "cegSzekhely.in=" + UPDATED_CEG_SZEKHELY
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegSzekhelyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegSzekhely is not null
        defaultCegAlapadatokFiltering("cegSzekhely.specified=true", "cegSzekhely.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegSzekhelyContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegSzekhely contains
        defaultCegAlapadatokFiltering("cegSzekhely.contains=" + DEFAULT_CEG_SZEKHELY, "cegSzekhely.contains=" + UPDATED_CEG_SZEKHELY);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegSzekhelyNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegSzekhely does not contain
        defaultCegAlapadatokFiltering(
            "cegSzekhely.doesNotContain=" + UPDATED_CEG_SZEKHELY,
            "cegSzekhely.doesNotContain=" + DEFAULT_CEG_SZEKHELY
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByAdoszamIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where adoszam equals to
        defaultCegAlapadatokFiltering("adoszam.equals=" + DEFAULT_ADOSZAM, "adoszam.equals=" + UPDATED_ADOSZAM);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByAdoszamIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where adoszam in
        defaultCegAlapadatokFiltering("adoszam.in=" + DEFAULT_ADOSZAM + "," + UPDATED_ADOSZAM, "adoszam.in=" + UPDATED_ADOSZAM);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByAdoszamIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where adoszam is not null
        defaultCegAlapadatokFiltering("adoszam.specified=true", "adoszam.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByAdoszamContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where adoszam contains
        defaultCegAlapadatokFiltering("adoszam.contains=" + DEFAULT_ADOSZAM, "adoszam.contains=" + UPDATED_ADOSZAM);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByAdoszamNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where adoszam does not contain
        defaultCegAlapadatokFiltering("adoszam.doesNotContain=" + UPDATED_ADOSZAM, "adoszam.doesNotContain=" + DEFAULT_ADOSZAM);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegjegyzekszamIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegjegyzekszam equals to
        defaultCegAlapadatokFiltering("cegjegyzekszam.equals=" + DEFAULT_CEGJEGYZEKSZAM, "cegjegyzekszam.equals=" + UPDATED_CEGJEGYZEKSZAM);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegjegyzekszamIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegjegyzekszam in
        defaultCegAlapadatokFiltering(
            "cegjegyzekszam.in=" + DEFAULT_CEGJEGYZEKSZAM + "," + UPDATED_CEGJEGYZEKSZAM,
            "cegjegyzekszam.in=" + UPDATED_CEGJEGYZEKSZAM
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegjegyzekszamIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegjegyzekszam is not null
        defaultCegAlapadatokFiltering("cegjegyzekszam.specified=true", "cegjegyzekszam.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegjegyzekszamContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegjegyzekszam contains
        defaultCegAlapadatokFiltering(
            "cegjegyzekszam.contains=" + DEFAULT_CEGJEGYZEKSZAM,
            "cegjegyzekszam.contains=" + UPDATED_CEGJEGYZEKSZAM
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegjegyzekszamNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegjegyzekszam does not contain
        defaultCegAlapadatokFiltering(
            "cegjegyzekszam.doesNotContain=" + UPDATED_CEGJEGYZEKSZAM,
            "cegjegyzekszam.doesNotContain=" + DEFAULT_CEGJEGYZEKSZAM
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiEmail equals to
        defaultCegAlapadatokFiltering(
            "cegKozpontiEmail.equals=" + DEFAULT_CEG_KOZPONTI_EMAIL,
            "cegKozpontiEmail.equals=" + UPDATED_CEG_KOZPONTI_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiEmail in
        defaultCegAlapadatokFiltering(
            "cegKozpontiEmail.in=" + DEFAULT_CEG_KOZPONTI_EMAIL + "," + UPDATED_CEG_KOZPONTI_EMAIL,
            "cegKozpontiEmail.in=" + UPDATED_CEG_KOZPONTI_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiEmail is not null
        defaultCegAlapadatokFiltering("cegKozpontiEmail.specified=true", "cegKozpontiEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiEmail contains
        defaultCegAlapadatokFiltering(
            "cegKozpontiEmail.contains=" + DEFAULT_CEG_KOZPONTI_EMAIL,
            "cegKozpontiEmail.contains=" + UPDATED_CEG_KOZPONTI_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiEmail does not contain
        defaultCegAlapadatokFiltering(
            "cegKozpontiEmail.doesNotContain=" + UPDATED_CEG_KOZPONTI_EMAIL,
            "cegKozpontiEmail.doesNotContain=" + DEFAULT_CEG_KOZPONTI_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiTelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiTel equals to
        defaultCegAlapadatokFiltering(
            "cegKozpontiTel.equals=" + DEFAULT_CEG_KOZPONTI_TEL,
            "cegKozpontiTel.equals=" + UPDATED_CEG_KOZPONTI_TEL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiTelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiTel in
        defaultCegAlapadatokFiltering(
            "cegKozpontiTel.in=" + DEFAULT_CEG_KOZPONTI_TEL + "," + UPDATED_CEG_KOZPONTI_TEL,
            "cegKozpontiTel.in=" + UPDATED_CEG_KOZPONTI_TEL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiTel is not null
        defaultCegAlapadatokFiltering("cegKozpontiTel.specified=true", "cegKozpontiTel.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiTelContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiTel contains
        defaultCegAlapadatokFiltering(
            "cegKozpontiTel.contains=" + DEFAULT_CEG_KOZPONTI_TEL,
            "cegKozpontiTel.contains=" + UPDATED_CEG_KOZPONTI_TEL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByCegKozpontiTelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where cegKozpontiTel does not contain
        defaultCegAlapadatokFiltering(
            "cegKozpontiTel.doesNotContain=" + UPDATED_CEG_KOZPONTI_TEL,
            "cegKozpontiTel.doesNotContain=" + DEFAULT_CEG_KOZPONTI_TEL
        );
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where statusz equals to
        defaultCegAlapadatokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where statusz in
        defaultCegAlapadatokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where statusz is not null
        defaultCegAlapadatokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where statusz contains
        defaultCegAlapadatokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllCegAlapadatoksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        // Get all the cegAlapadatokList where statusz does not contain
        defaultCegAlapadatokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    private void defaultCegAlapadatokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCegAlapadatokShouldBeFound(shouldBeFound);
        defaultCegAlapadatokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCegAlapadatokShouldBeFound(String filter) throws Exception {
        restCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cegAlapadatok.getId().intValue())))
            .andExpect(jsonPath("$.[*].cegNev").value(hasItem(DEFAULT_CEG_NEV)))
            .andExpect(jsonPath("$.[*].cegRovidAzonosito").value(hasItem(DEFAULT_CEG_ROVID_AZONOSITO)))
            .andExpect(jsonPath("$.[*].cegSzekhely").value(hasItem(DEFAULT_CEG_SZEKHELY)))
            .andExpect(jsonPath("$.[*].adoszam").value(hasItem(DEFAULT_ADOSZAM)))
            .andExpect(jsonPath("$.[*].cegjegyzekszam").value(hasItem(DEFAULT_CEGJEGYZEKSZAM)))
            .andExpect(jsonPath("$.[*].cegKozpontiEmail").value(hasItem(DEFAULT_CEG_KOZPONTI_EMAIL)))
            .andExpect(jsonPath("$.[*].cegKozpontiTel").value(hasItem(DEFAULT_CEG_KOZPONTI_TEL)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCegAlapadatokShouldNotBeFound(String filter) throws Exception {
        restCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCegAlapadatokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCegAlapadatok() throws Exception {
        // Get the cegAlapadatok
        restCegAlapadatokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCegAlapadatok() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cegAlapadatok
        CegAlapadatok updatedCegAlapadatok = cegAlapadatokRepository.findById(cegAlapadatok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCegAlapadatok are not directly saved in db
        em.detach(updatedCegAlapadatok);
        updatedCegAlapadatok
            .cegNev(UPDATED_CEG_NEV)
            .cegRovidAzonosito(UPDATED_CEG_ROVID_AZONOSITO)
            .cegSzekhely(UPDATED_CEG_SZEKHELY)
            .adoszam(UPDATED_ADOSZAM)
            .cegjegyzekszam(UPDATED_CEGJEGYZEKSZAM)
            .cegKozpontiEmail(UPDATED_CEG_KOZPONTI_EMAIL)
            .cegKozpontiTel(UPDATED_CEG_KOZPONTI_TEL)
            .statusz(UPDATED_STATUSZ);
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(updatedCegAlapadatok);

        restCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cegAlapadatokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isOk());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCegAlapadatokToMatchAllProperties(updatedCegAlapadatok);
    }

    @Test
    @Transactional
    void putNonExistingCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cegAlapadatok.setId(longCount.incrementAndGet());

        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cegAlapadatokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cegAlapadatok.setId(longCount.incrementAndGet());

        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cegAlapadatok.setId(longCount.incrementAndGet());

        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCegAlapadatokMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCegAlapadatokWithPatch() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cegAlapadatok using partial update
        CegAlapadatok partialUpdatedCegAlapadatok = new CegAlapadatok();
        partialUpdatedCegAlapadatok.setId(cegAlapadatok.getId());

        partialUpdatedCegAlapadatok.cegNev(UPDATED_CEG_NEV).cegKozpontiTel(UPDATED_CEG_KOZPONTI_TEL).statusz(UPDATED_STATUSZ);

        restCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCegAlapadatok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCegAlapadatok))
            )
            .andExpect(status().isOk());

        // Validate the CegAlapadatok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCegAlapadatokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCegAlapadatok, cegAlapadatok),
            getPersistedCegAlapadatok(cegAlapadatok)
        );
    }

    @Test
    @Transactional
    void fullUpdateCegAlapadatokWithPatch() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cegAlapadatok using partial update
        CegAlapadatok partialUpdatedCegAlapadatok = new CegAlapadatok();
        partialUpdatedCegAlapadatok.setId(cegAlapadatok.getId());

        partialUpdatedCegAlapadatok
            .cegNev(UPDATED_CEG_NEV)
            .cegRovidAzonosito(UPDATED_CEG_ROVID_AZONOSITO)
            .cegSzekhely(UPDATED_CEG_SZEKHELY)
            .adoszam(UPDATED_ADOSZAM)
            .cegjegyzekszam(UPDATED_CEGJEGYZEKSZAM)
            .cegKozpontiEmail(UPDATED_CEG_KOZPONTI_EMAIL)
            .cegKozpontiTel(UPDATED_CEG_KOZPONTI_TEL)
            .statusz(UPDATED_STATUSZ);

        restCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCegAlapadatok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCegAlapadatok))
            )
            .andExpect(status().isOk());

        // Validate the CegAlapadatok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCegAlapadatokUpdatableFieldsEquals(partialUpdatedCegAlapadatok, getPersistedCegAlapadatok(partialUpdatedCegAlapadatok));
    }

    @Test
    @Transactional
    void patchNonExistingCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cegAlapadatok.setId(longCount.incrementAndGet());

        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cegAlapadatokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cegAlapadatok.setId(longCount.incrementAndGet());

        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCegAlapadatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cegAlapadatok.setId(longCount.incrementAndGet());

        // Create the CegAlapadatok
        CegAlapadatokDTO cegAlapadatokDTO = cegAlapadatokMapper.toDto(cegAlapadatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCegAlapadatokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cegAlapadatokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CegAlapadatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCegAlapadatok() throws Exception {
        // Initialize the database
        insertedCegAlapadatok = cegAlapadatokRepository.saveAndFlush(cegAlapadatok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cegAlapadatok
        restCegAlapadatokMockMvc
            .perform(delete(ENTITY_API_URL_ID, cegAlapadatok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cegAlapadatokRepository.count();
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

    protected CegAlapadatok getPersistedCegAlapadatok(CegAlapadatok cegAlapadatok) {
        return cegAlapadatokRepository.findById(cegAlapadatok.getId()).orElseThrow();
    }

    protected void assertPersistedCegAlapadatokToMatchAllProperties(CegAlapadatok expectedCegAlapadatok) {
        assertCegAlapadatokAllPropertiesEquals(expectedCegAlapadatok, getPersistedCegAlapadatok(expectedCegAlapadatok));
    }

    protected void assertPersistedCegAlapadatokToMatchUpdatableProperties(CegAlapadatok expectedCegAlapadatok) {
        assertCegAlapadatokAllUpdatablePropertiesEquals(expectedCegAlapadatok, getPersistedCegAlapadatok(expectedCegAlapadatok));
    }
}

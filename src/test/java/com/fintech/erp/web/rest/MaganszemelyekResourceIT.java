package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.MaganszemelyekAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.repository.MaganszemelyekRepository;
import com.fintech.erp.service.dto.MaganszemelyekDTO;
import com.fintech.erp.service.mapper.MaganszemelyekMapper;
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
 * Integration tests for the {@link MaganszemelyekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaganszemelyekResourceIT {

    private static final String DEFAULT_MAGANSZEMELY_NEVE = "AAAAAAAAAA";
    private static final String UPDATED_MAGANSZEMELY_NEVE = "BBBBBBBBBB";

    private static final String DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA = "AAAAAAAAAA";
    private static final String UPDATED_SZEMELYI_IGAZOLVANY_SZAMA = "BBBBBBBBBB";

    private static final String DEFAULT_ADO_AZONOSITO_JEL = "AAAAAAAAAA";
    private static final String UPDATED_ADO_AZONOSITO_JEL = "BBBBBBBBBB";

    private static final String DEFAULT_TB_AZONOSITO = "AAAAAAAAAA";
    private static final String UPDATED_TB_AZONOSITO = "BBBBBBBBBB";

    private static final String DEFAULT_BANKSZAMLASZAM = "AAAAAAAAAA";
    private static final String UPDATED_BANKSZAMLASZAM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONSZAM = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONSZAM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILCIM = "AAAAAAAAAA";
    private static final String UPDATED_EMAILCIM = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/maganszemelyeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaganszemelyekRepository maganszemelyekRepository;

    @Autowired
    private MaganszemelyekMapper maganszemelyekMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaganszemelyekMockMvc;

    private Maganszemelyek maganszemelyek;

    private Maganszemelyek insertedMaganszemelyek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maganszemelyek createEntity() {
        return new Maganszemelyek()
            .maganszemelyNeve(DEFAULT_MAGANSZEMELY_NEVE)
            .szemelyiIgazolvanySzama(DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA)
            .adoAzonositoJel(DEFAULT_ADO_AZONOSITO_JEL)
            .tbAzonosito(DEFAULT_TB_AZONOSITO)
            .bankszamlaszam(DEFAULT_BANKSZAMLASZAM)
            .telefonszam(DEFAULT_TELEFONSZAM)
            .emailcim(DEFAULT_EMAILCIM)
            .statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maganszemelyek createUpdatedEntity() {
        return new Maganszemelyek()
            .maganszemelyNeve(UPDATED_MAGANSZEMELY_NEVE)
            .szemelyiIgazolvanySzama(UPDATED_SZEMELYI_IGAZOLVANY_SZAMA)
            .adoAzonositoJel(UPDATED_ADO_AZONOSITO_JEL)
            .tbAzonosito(UPDATED_TB_AZONOSITO)
            .bankszamlaszam(UPDATED_BANKSZAMLASZAM)
            .telefonszam(UPDATED_TELEFONSZAM)
            .emailcim(UPDATED_EMAILCIM)
            .statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        maganszemelyek = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMaganszemelyek != null) {
            maganszemelyekRepository.delete(insertedMaganszemelyek);
            insertedMaganszemelyek = null;
        }
    }

    @Test
    @Transactional
    void createMaganszemelyek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);
        var returnedMaganszemelyekDTO = om.readValue(
            restMaganszemelyekMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(maganszemelyekDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MaganszemelyekDTO.class
        );

        // Validate the Maganszemelyek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMaganszemelyek = maganszemelyekMapper.toEntity(returnedMaganszemelyekDTO);
        assertMaganszemelyekUpdatableFieldsEquals(returnedMaganszemelyek, getPersistedMaganszemelyek(returnedMaganszemelyek));

        insertedMaganszemelyek = returnedMaganszemelyek;
    }

    @Test
    @Transactional
    void createMaganszemelyekWithExistingId() throws Exception {
        // Create the Maganszemelyek with an existing ID
        maganszemelyek.setId(1L);
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaganszemelyekMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaganszemelyNeveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        maganszemelyek.setMaganszemelyNeve(null);

        // Create the Maganszemelyek, which fails.
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        restMaganszemelyekMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeks() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList
        restMaganszemelyekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maganszemelyek.getId().intValue())))
            .andExpect(jsonPath("$.[*].maganszemelyNeve").value(hasItem(DEFAULT_MAGANSZEMELY_NEVE)))
            .andExpect(jsonPath("$.[*].szemelyiIgazolvanySzama").value(hasItem(DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA)))
            .andExpect(jsonPath("$.[*].adoAzonositoJel").value(hasItem(DEFAULT_ADO_AZONOSITO_JEL)))
            .andExpect(jsonPath("$.[*].tbAzonosito").value(hasItem(DEFAULT_TB_AZONOSITO)))
            .andExpect(jsonPath("$.[*].bankszamlaszam").value(hasItem(DEFAULT_BANKSZAMLASZAM)))
            .andExpect(jsonPath("$.[*].telefonszam").value(hasItem(DEFAULT_TELEFONSZAM)))
            .andExpect(jsonPath("$.[*].emailcim").value(hasItem(DEFAULT_EMAILCIM)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getMaganszemelyek() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get the maganszemelyek
        restMaganszemelyekMockMvc
            .perform(get(ENTITY_API_URL_ID, maganszemelyek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maganszemelyek.getId().intValue()))
            .andExpect(jsonPath("$.maganszemelyNeve").value(DEFAULT_MAGANSZEMELY_NEVE))
            .andExpect(jsonPath("$.szemelyiIgazolvanySzama").value(DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA))
            .andExpect(jsonPath("$.adoAzonositoJel").value(DEFAULT_ADO_AZONOSITO_JEL))
            .andExpect(jsonPath("$.tbAzonosito").value(DEFAULT_TB_AZONOSITO))
            .andExpect(jsonPath("$.bankszamlaszam").value(DEFAULT_BANKSZAMLASZAM))
            .andExpect(jsonPath("$.telefonszam").value(DEFAULT_TELEFONSZAM))
            .andExpect(jsonPath("$.emailcim").value(DEFAULT_EMAILCIM))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getMaganszemelyeksByIdFiltering() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        Long id = maganszemelyek.getId();

        defaultMaganszemelyekFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMaganszemelyekFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMaganszemelyekFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByMaganszemelyNeveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where maganszemelyNeve equals to
        defaultMaganszemelyekFiltering(
            "maganszemelyNeve.equals=" + DEFAULT_MAGANSZEMELY_NEVE,
            "maganszemelyNeve.equals=" + UPDATED_MAGANSZEMELY_NEVE
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByMaganszemelyNeveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where maganszemelyNeve in
        defaultMaganszemelyekFiltering(
            "maganszemelyNeve.in=" + DEFAULT_MAGANSZEMELY_NEVE + "," + UPDATED_MAGANSZEMELY_NEVE,
            "maganszemelyNeve.in=" + UPDATED_MAGANSZEMELY_NEVE
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByMaganszemelyNeveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where maganszemelyNeve is not null
        defaultMaganszemelyekFiltering("maganszemelyNeve.specified=true", "maganszemelyNeve.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByMaganszemelyNeveContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where maganszemelyNeve contains
        defaultMaganszemelyekFiltering(
            "maganszemelyNeve.contains=" + DEFAULT_MAGANSZEMELY_NEVE,
            "maganszemelyNeve.contains=" + UPDATED_MAGANSZEMELY_NEVE
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByMaganszemelyNeveNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where maganszemelyNeve does not contain
        defaultMaganszemelyekFiltering(
            "maganszemelyNeve.doesNotContain=" + UPDATED_MAGANSZEMELY_NEVE,
            "maganszemelyNeve.doesNotContain=" + DEFAULT_MAGANSZEMELY_NEVE
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksBySzemelyiIgazolvanySzamaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where szemelyiIgazolvanySzama equals to
        defaultMaganszemelyekFiltering(
            "szemelyiIgazolvanySzama.equals=" + DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA,
            "szemelyiIgazolvanySzama.equals=" + UPDATED_SZEMELYI_IGAZOLVANY_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksBySzemelyiIgazolvanySzamaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where szemelyiIgazolvanySzama in
        defaultMaganszemelyekFiltering(
            "szemelyiIgazolvanySzama.in=" + DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA + "," + UPDATED_SZEMELYI_IGAZOLVANY_SZAMA,
            "szemelyiIgazolvanySzama.in=" + UPDATED_SZEMELYI_IGAZOLVANY_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksBySzemelyiIgazolvanySzamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where szemelyiIgazolvanySzama is not null
        defaultMaganszemelyekFiltering("szemelyiIgazolvanySzama.specified=true", "szemelyiIgazolvanySzama.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksBySzemelyiIgazolvanySzamaContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where szemelyiIgazolvanySzama contains
        defaultMaganszemelyekFiltering(
            "szemelyiIgazolvanySzama.contains=" + DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA,
            "szemelyiIgazolvanySzama.contains=" + UPDATED_SZEMELYI_IGAZOLVANY_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksBySzemelyiIgazolvanySzamaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where szemelyiIgazolvanySzama does not contain
        defaultMaganszemelyekFiltering(
            "szemelyiIgazolvanySzama.doesNotContain=" + UPDATED_SZEMELYI_IGAZOLVANY_SZAMA,
            "szemelyiIgazolvanySzama.doesNotContain=" + DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByAdoAzonositoJelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where adoAzonositoJel equals to
        defaultMaganszemelyekFiltering(
            "adoAzonositoJel.equals=" + DEFAULT_ADO_AZONOSITO_JEL,
            "adoAzonositoJel.equals=" + UPDATED_ADO_AZONOSITO_JEL
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByAdoAzonositoJelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where adoAzonositoJel in
        defaultMaganszemelyekFiltering(
            "adoAzonositoJel.in=" + DEFAULT_ADO_AZONOSITO_JEL + "," + UPDATED_ADO_AZONOSITO_JEL,
            "adoAzonositoJel.in=" + UPDATED_ADO_AZONOSITO_JEL
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByAdoAzonositoJelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where adoAzonositoJel is not null
        defaultMaganszemelyekFiltering("adoAzonositoJel.specified=true", "adoAzonositoJel.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByAdoAzonositoJelContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where adoAzonositoJel contains
        defaultMaganszemelyekFiltering(
            "adoAzonositoJel.contains=" + DEFAULT_ADO_AZONOSITO_JEL,
            "adoAzonositoJel.contains=" + UPDATED_ADO_AZONOSITO_JEL
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByAdoAzonositoJelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where adoAzonositoJel does not contain
        defaultMaganszemelyekFiltering(
            "adoAzonositoJel.doesNotContain=" + UPDATED_ADO_AZONOSITO_JEL,
            "adoAzonositoJel.doesNotContain=" + DEFAULT_ADO_AZONOSITO_JEL
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTbAzonositoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where tbAzonosito equals to
        defaultMaganszemelyekFiltering("tbAzonosito.equals=" + DEFAULT_TB_AZONOSITO, "tbAzonosito.equals=" + UPDATED_TB_AZONOSITO);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTbAzonositoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where tbAzonosito in
        defaultMaganszemelyekFiltering(
            "tbAzonosito.in=" + DEFAULT_TB_AZONOSITO + "," + UPDATED_TB_AZONOSITO,
            "tbAzonosito.in=" + UPDATED_TB_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTbAzonositoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where tbAzonosito is not null
        defaultMaganszemelyekFiltering("tbAzonosito.specified=true", "tbAzonosito.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTbAzonositoContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where tbAzonosito contains
        defaultMaganszemelyekFiltering("tbAzonosito.contains=" + DEFAULT_TB_AZONOSITO, "tbAzonosito.contains=" + UPDATED_TB_AZONOSITO);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTbAzonositoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where tbAzonosito does not contain
        defaultMaganszemelyekFiltering(
            "tbAzonosito.doesNotContain=" + UPDATED_TB_AZONOSITO,
            "tbAzonosito.doesNotContain=" + DEFAULT_TB_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByBankszamlaszamIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where bankszamlaszam equals to
        defaultMaganszemelyekFiltering(
            "bankszamlaszam.equals=" + DEFAULT_BANKSZAMLASZAM,
            "bankszamlaszam.equals=" + UPDATED_BANKSZAMLASZAM
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByBankszamlaszamIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where bankszamlaszam in
        defaultMaganszemelyekFiltering(
            "bankszamlaszam.in=" + DEFAULT_BANKSZAMLASZAM + "," + UPDATED_BANKSZAMLASZAM,
            "bankszamlaszam.in=" + UPDATED_BANKSZAMLASZAM
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByBankszamlaszamIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where bankszamlaszam is not null
        defaultMaganszemelyekFiltering("bankszamlaszam.specified=true", "bankszamlaszam.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByBankszamlaszamContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where bankszamlaszam contains
        defaultMaganszemelyekFiltering(
            "bankszamlaszam.contains=" + DEFAULT_BANKSZAMLASZAM,
            "bankszamlaszam.contains=" + UPDATED_BANKSZAMLASZAM
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByBankszamlaszamNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where bankszamlaszam does not contain
        defaultMaganszemelyekFiltering(
            "bankszamlaszam.doesNotContain=" + UPDATED_BANKSZAMLASZAM,
            "bankszamlaszam.doesNotContain=" + DEFAULT_BANKSZAMLASZAM
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTelefonszamIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where telefonszam equals to
        defaultMaganszemelyekFiltering("telefonszam.equals=" + DEFAULT_TELEFONSZAM, "telefonszam.equals=" + UPDATED_TELEFONSZAM);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTelefonszamIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where telefonszam in
        defaultMaganszemelyekFiltering(
            "telefonszam.in=" + DEFAULT_TELEFONSZAM + "," + UPDATED_TELEFONSZAM,
            "telefonszam.in=" + UPDATED_TELEFONSZAM
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTelefonszamIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where telefonszam is not null
        defaultMaganszemelyekFiltering("telefonszam.specified=true", "telefonszam.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTelefonszamContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where telefonszam contains
        defaultMaganszemelyekFiltering("telefonszam.contains=" + DEFAULT_TELEFONSZAM, "telefonszam.contains=" + UPDATED_TELEFONSZAM);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByTelefonszamNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where telefonszam does not contain
        defaultMaganszemelyekFiltering(
            "telefonszam.doesNotContain=" + UPDATED_TELEFONSZAM,
            "telefonszam.doesNotContain=" + DEFAULT_TELEFONSZAM
        );
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByEmailcimIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where emailcim equals to
        defaultMaganszemelyekFiltering("emailcim.equals=" + DEFAULT_EMAILCIM, "emailcim.equals=" + UPDATED_EMAILCIM);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByEmailcimIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where emailcim in
        defaultMaganszemelyekFiltering("emailcim.in=" + DEFAULT_EMAILCIM + "," + UPDATED_EMAILCIM, "emailcim.in=" + UPDATED_EMAILCIM);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByEmailcimIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where emailcim is not null
        defaultMaganszemelyekFiltering("emailcim.specified=true", "emailcim.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByEmailcimContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where emailcim contains
        defaultMaganszemelyekFiltering("emailcim.contains=" + DEFAULT_EMAILCIM, "emailcim.contains=" + UPDATED_EMAILCIM);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByEmailcimNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where emailcim does not contain
        defaultMaganszemelyekFiltering("emailcim.doesNotContain=" + UPDATED_EMAILCIM, "emailcim.doesNotContain=" + DEFAULT_EMAILCIM);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where statusz equals to
        defaultMaganszemelyekFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where statusz in
        defaultMaganszemelyekFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where statusz is not null
        defaultMaganszemelyekFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where statusz contains
        defaultMaganszemelyekFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllMaganszemelyeksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        // Get all the maganszemelyekList where statusz does not contain
        defaultMaganszemelyekFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    private void defaultMaganszemelyekFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMaganszemelyekShouldBeFound(shouldBeFound);
        defaultMaganszemelyekShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaganszemelyekShouldBeFound(String filter) throws Exception {
        restMaganszemelyekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maganszemelyek.getId().intValue())))
            .andExpect(jsonPath("$.[*].maganszemelyNeve").value(hasItem(DEFAULT_MAGANSZEMELY_NEVE)))
            .andExpect(jsonPath("$.[*].szemelyiIgazolvanySzama").value(hasItem(DEFAULT_SZEMELYI_IGAZOLVANY_SZAMA)))
            .andExpect(jsonPath("$.[*].adoAzonositoJel").value(hasItem(DEFAULT_ADO_AZONOSITO_JEL)))
            .andExpect(jsonPath("$.[*].tbAzonosito").value(hasItem(DEFAULT_TB_AZONOSITO)))
            .andExpect(jsonPath("$.[*].bankszamlaszam").value(hasItem(DEFAULT_BANKSZAMLASZAM)))
            .andExpect(jsonPath("$.[*].telefonszam").value(hasItem(DEFAULT_TELEFONSZAM)))
            .andExpect(jsonPath("$.[*].emailcim").value(hasItem(DEFAULT_EMAILCIM)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restMaganszemelyekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaganszemelyekShouldNotBeFound(String filter) throws Exception {
        restMaganszemelyekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaganszemelyekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaganszemelyek() throws Exception {
        // Get the maganszemelyek
        restMaganszemelyekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaganszemelyek() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maganszemelyek
        Maganszemelyek updatedMaganszemelyek = maganszemelyekRepository.findById(maganszemelyek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMaganszemelyek are not directly saved in db
        em.detach(updatedMaganszemelyek);
        updatedMaganszemelyek
            .maganszemelyNeve(UPDATED_MAGANSZEMELY_NEVE)
            .szemelyiIgazolvanySzama(UPDATED_SZEMELYI_IGAZOLVANY_SZAMA)
            .adoAzonositoJel(UPDATED_ADO_AZONOSITO_JEL)
            .tbAzonosito(UPDATED_TB_AZONOSITO)
            .bankszamlaszam(UPDATED_BANKSZAMLASZAM)
            .telefonszam(UPDATED_TELEFONSZAM)
            .emailcim(UPDATED_EMAILCIM)
            .statusz(UPDATED_STATUSZ);
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(updatedMaganszemelyek);

        restMaganszemelyekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maganszemelyekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isOk());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMaganszemelyekToMatchAllProperties(updatedMaganszemelyek);
    }

    @Test
    @Transactional
    void putNonExistingMaganszemelyek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maganszemelyek.setId(longCount.incrementAndGet());

        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaganszemelyekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maganszemelyekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaganszemelyek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maganszemelyek.setId(longCount.incrementAndGet());

        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaganszemelyekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaganszemelyek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maganszemelyek.setId(longCount.incrementAndGet());

        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaganszemelyekMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaganszemelyekWithPatch() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maganszemelyek using partial update
        Maganszemelyek partialUpdatedMaganszemelyek = new Maganszemelyek();
        partialUpdatedMaganszemelyek.setId(maganszemelyek.getId());

        partialUpdatedMaganszemelyek.telefonszam(UPDATED_TELEFONSZAM).emailcim(UPDATED_EMAILCIM);

        restMaganszemelyekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaganszemelyek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaganszemelyek))
            )
            .andExpect(status().isOk());

        // Validate the Maganszemelyek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaganszemelyekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMaganszemelyek, maganszemelyek),
            getPersistedMaganszemelyek(maganszemelyek)
        );
    }

    @Test
    @Transactional
    void fullUpdateMaganszemelyekWithPatch() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maganszemelyek using partial update
        Maganszemelyek partialUpdatedMaganszemelyek = new Maganszemelyek();
        partialUpdatedMaganszemelyek.setId(maganszemelyek.getId());

        partialUpdatedMaganszemelyek
            .maganszemelyNeve(UPDATED_MAGANSZEMELY_NEVE)
            .szemelyiIgazolvanySzama(UPDATED_SZEMELYI_IGAZOLVANY_SZAMA)
            .adoAzonositoJel(UPDATED_ADO_AZONOSITO_JEL)
            .tbAzonosito(UPDATED_TB_AZONOSITO)
            .bankszamlaszam(UPDATED_BANKSZAMLASZAM)
            .telefonszam(UPDATED_TELEFONSZAM)
            .emailcim(UPDATED_EMAILCIM)
            .statusz(UPDATED_STATUSZ);

        restMaganszemelyekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaganszemelyek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaganszemelyek))
            )
            .andExpect(status().isOk());

        // Validate the Maganszemelyek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaganszemelyekUpdatableFieldsEquals(partialUpdatedMaganszemelyek, getPersistedMaganszemelyek(partialUpdatedMaganszemelyek));
    }

    @Test
    @Transactional
    void patchNonExistingMaganszemelyek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maganszemelyek.setId(longCount.incrementAndGet());

        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaganszemelyekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maganszemelyekDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaganszemelyek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maganszemelyek.setId(longCount.incrementAndGet());

        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaganszemelyekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaganszemelyek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maganszemelyek.setId(longCount.incrementAndGet());

        // Create the Maganszemelyek
        MaganszemelyekDTO maganszemelyekDTO = maganszemelyekMapper.toDto(maganszemelyek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaganszemelyekMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maganszemelyekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Maganszemelyek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaganszemelyek() throws Exception {
        // Initialize the database
        insertedMaganszemelyek = maganszemelyekRepository.saveAndFlush(maganszemelyek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the maganszemelyek
        restMaganszemelyekMockMvc
            .perform(delete(ENTITY_API_URL_ID, maganszemelyek.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return maganszemelyekRepository.count();
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

    protected Maganszemelyek getPersistedMaganszemelyek(Maganszemelyek maganszemelyek) {
        return maganszemelyekRepository.findById(maganszemelyek.getId()).orElseThrow();
    }

    protected void assertPersistedMaganszemelyekToMatchAllProperties(Maganszemelyek expectedMaganszemelyek) {
        assertMaganszemelyekAllPropertiesEquals(expectedMaganszemelyek, getPersistedMaganszemelyek(expectedMaganszemelyek));
    }

    protected void assertPersistedMaganszemelyekToMatchUpdatableProperties(Maganszemelyek expectedMaganszemelyek) {
        assertMaganszemelyekAllUpdatablePropertiesEquals(expectedMaganszemelyek, getPersistedMaganszemelyek(expectedMaganszemelyek));
    }
}

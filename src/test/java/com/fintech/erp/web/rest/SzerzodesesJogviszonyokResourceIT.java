package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.SzerzodesesJogviszonyokAsserts.assertSzerzodesesJogviszonyokAllPropertiesEquals;
import static com.fintech.erp.domain.SzerzodesesJogviszonyokAsserts.assertSzerzodesesJogviszonyokAllUpdatablePropertiesEquals;
import static com.fintech.erp.domain.SzerzodesesJogviszonyokAsserts.assertSzerzodesesJogviszonyokUpdatableFieldsEquals;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.SzerzodesesJogviszonyDokumentumRepository;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import com.fintech.erp.service.mapper.SzerzodesesJogviszonyokMapper;
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
 * Integration tests for the {@link SzerzodesesJogviszonyokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SzerzodesesJogviszonyokResourceIT {

    private static final String DEFAULT_SZERZODES_AZONOSITO = "AAAAAAAAAA";
    private static final String UPDATED_SZERZODES_AZONOSITO = "BBBBBBBBBB";

    private static final String DEFAULT_DOKUMENTUM_TIPUS = "GENERALT_WORD";

    private static final LocalDate DEFAULT_JOGVISZONY_KEZDETE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOGVISZONY_KEZDETE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_JOGVISZONY_KEZDETE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_JOGVISZONY_LEJARATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOGVISZONY_LEJARATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_JOGVISZONY_LEJARATA = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/szerzodeses-jogviszonyoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    @Autowired
    private SzerzodesesJogviszonyokMapper szerzodesesJogviszonyokMapper;

    @Autowired
    private SzerzodesesJogviszonyDokumentumRepository szerzodesesJogviszonyDokumentumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSzerzodesesJogviszonyokMockMvc;

    private SzerzodesesJogviszonyok szerzodesesJogviszonyok;

    private SzerzodesesJogviszonyok insertedSzerzodesesJogviszonyok;
    private SzerzodesesJogviszonyDokumentum insertedSzerzodesesJogviszonyDokumentum;
    private CegAlapadatok insertedMegrendeloCeg;
    private CegAlapadatok insertedVallalkozoCeg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SzerzodesesJogviszonyok createEntity() {
        return new SzerzodesesJogviszonyok()
            .szerzodesAzonosito(DEFAULT_SZERZODES_AZONOSITO)
            .jogviszonyKezdete(DEFAULT_JOGVISZONY_KEZDETE)
            .jogviszonyLejarata(DEFAULT_JOGVISZONY_LEJARATA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SzerzodesesJogviszonyok createUpdatedEntity() {
        return new SzerzodesesJogviszonyok()
            .szerzodesAzonosito(UPDATED_SZERZODES_AZONOSITO)
            .jogviszonyKezdete(UPDATED_JOGVISZONY_KEZDETE)
            .jogviszonyLejarata(UPDATED_JOGVISZONY_LEJARATA);
    }

    @BeforeEach
    void initTest() {
        szerzodesesJogviszonyok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSzerzodesesJogviszonyDokumentum != null && insertedSzerzodesesJogviszonyDokumentum.getId() != null) {
            szerzodesesJogviszonyDokumentumRepository.deleteById(insertedSzerzodesesJogviszonyDokumentum.getId());
            insertedSzerzodesesJogviszonyDokumentum = null;
        }
        if (insertedSzerzodesesJogviszonyok != null) {
            szerzodesesJogviszonyokRepository.delete(insertedSzerzodesesJogviszonyok);
            insertedSzerzodesesJogviszonyok = null;
        }
        if (insertedMegrendeloCeg != null && insertedMegrendeloCeg.getId() != null) {
            CegAlapadatok managed = em.contains(insertedMegrendeloCeg) ? insertedMegrendeloCeg : em.merge(insertedMegrendeloCeg);
            em.remove(managed);
            insertedMegrendeloCeg = null;
        }
        if (insertedVallalkozoCeg != null && insertedVallalkozoCeg.getId() != null) {
            CegAlapadatok managed = em.contains(insertedVallalkozoCeg) ? insertedVallalkozoCeg : em.merge(insertedVallalkozoCeg);
            em.remove(managed);
            insertedVallalkozoCeg = null;
        }
    }

    @Test
    @Transactional
    void createSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);
        var returnedSzerzodesesJogviszonyokDTO = om.readValue(
            restSzerzodesesJogviszonyokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SzerzodesesJogviszonyokDTO.class
        );

        // Validate the SzerzodesesJogviszonyok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSzerzodesesJogviszonyok = szerzodesesJogviszonyokMapper.toEntity(returnedSzerzodesesJogviszonyokDTO);
        assertSzerzodesesJogviszonyokUpdatableFieldsEquals(
            returnedSzerzodesesJogviszonyok,
            getPersistedSzerzodesesJogviszonyok(returnedSzerzodesesJogviszonyok)
        );

        insertedSzerzodesesJogviszonyok = returnedSzerzodesesJogviszonyok;
    }

    @Test
    @Transactional
    void createSzerzodesesJogviszonyokWithExistingId() throws Exception {
        // Create the SzerzodesesJogviszonyok with an existing ID
        szerzodesesJogviszonyok.setId(1L);
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoks() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList
        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(szerzodesesJogviszonyok.getId().intValue())))
            .andExpect(jsonPath("$.[*].szerzodesAzonosito").value(hasItem(DEFAULT_SZERZODES_AZONOSITO)))
            .andExpect(jsonPath("$.[*].jogviszonyKezdete").value(hasItem(DEFAULT_JOGVISZONY_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].jogviszonyLejarata").value(hasItem(DEFAULT_JOGVISZONY_LEJARATA.toString())));
    }

    @Test
    @Transactional
    void getSzerzodesesJogviszonyok() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get the szerzodesesJogviszonyok
        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL_ID, szerzodesesJogviszonyok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(szerzodesesJogviszonyok.getId().intValue()))
            .andExpect(jsonPath("$.szerzodesAzonosito").value(DEFAULT_SZERZODES_AZONOSITO))
            .andExpect(jsonPath("$.jogviszonyKezdete").value(DEFAULT_JOGVISZONY_KEZDETE.toString()))
            .andExpect(jsonPath("$.jogviszonyLejarata").value(DEFAULT_JOGVISZONY_LEJARATA.toString()));
    }

    @Test
    @Transactional
    void getDocumentsForSzerzodesesJogviszonyok() throws Exception {
        CegAlapadatok megrendelo = CegAlapadatokResourceIT.createEntity();
        CegAlapadatok vallalkozo = CegAlapadatokResourceIT.createEntity().cegNev("Masik Ceg");
        em.persist(megrendelo);
        em.persist(vallalkozo);
        em.flush();
        insertedMegrendeloCeg = megrendelo;
        insertedVallalkozoCeg = vallalkozo;

        szerzodesesJogviszonyok.megrendeloCeg(megrendelo);
        szerzodesesJogviszonyok.vallalkozoCeg(vallalkozo);
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        SzerzodesesJogviszonyDokumentum dokumentum = new SzerzodesesJogviszonyDokumentum()
            .dokumentumNev("generated.docx")
            .fajlUtvonal("generated.docx")
            .contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            .dokumentumTipus(DEFAULT_DOKUMENTUM_TIPUS)
            .szerzodesesJogviszony(insertedSzerzodesesJogviszonyok);
        insertedSzerzodesesJogviszonyDokumentum = szerzodesesJogviszonyDokumentumRepository.saveAndFlush(dokumentum);

        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL_ID + "/dokumentumok", insertedSzerzodesesJogviszonyok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].id").value(insertedSzerzodesesJogviszonyDokumentum.getId().intValue()))
            .andExpect(jsonPath("$[0].dokumentumNev").value("generated.docx"))
            .andExpect(jsonPath("$[0].dokumentumTipus").value(DEFAULT_DOKUMENTUM_TIPUS));
    }

    @Test
    @Transactional
    void getTemplatePlaceholdersForJogviszony() throws Exception {
        CegAlapadatok megrendelo = CegAlapadatokResourceIT.createEntity();
        CegAlapadatok vallalkozo = CegAlapadatokResourceIT.createEntity().cegNev("Masik Kft");
        em.persist(megrendelo);
        em.persist(vallalkozo);
        em.flush();
        insertedMegrendeloCeg = megrendelo;
        insertedVallalkozoCeg = vallalkozo;

        szerzodesesJogviszonyok.megrendeloCeg(megrendelo);
        szerzodesesJogviszonyok.vallalkozoCeg(vallalkozo);
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        restSzerzodesesJogviszonyokMockMvc
            .perform(get("/api/szerzodeses-jogviszony-dokumentumoks/jogviszony/{id}/placeholders", insertedSzerzodesesJogviszonyok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.entityId").value(insertedSzerzodesesJogviszonyok.getId().intValue()))
            .andExpect(jsonPath("$.values['jogviszony.azonosito']").value(DEFAULT_SZERZODES_AZONOSITO))
            .andExpect(jsonPath("$.definitions").isArray());
    }

    @Test
    @Transactional
    void getSzerzodesesJogviszonyoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        Long id = szerzodesesJogviszonyok.getId();

        defaultSzerzodesesJogviszonyokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSzerzodesesJogviszonyokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSzerzodesesJogviszonyokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksBySzerzodesAzonositoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where szerzodesAzonosito equals to
        defaultSzerzodesesJogviszonyokFiltering(
            "szerzodesAzonosito.equals=" + DEFAULT_SZERZODES_AZONOSITO,
            "szerzodesAzonosito.equals=" + UPDATED_SZERZODES_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksBySzerzodesAzonositoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where szerzodesAzonosito in
        defaultSzerzodesesJogviszonyokFiltering(
            "szerzodesAzonosito.in=" + DEFAULT_SZERZODES_AZONOSITO + "," + UPDATED_SZERZODES_AZONOSITO,
            "szerzodesAzonosito.in=" + UPDATED_SZERZODES_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksBySzerzodesAzonositoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where szerzodesAzonosito is not null
        defaultSzerzodesesJogviszonyokFiltering("szerzodesAzonosito.specified=true", "szerzodesAzonosito.specified=false");
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksBySzerzodesAzonositoContainsSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where szerzodesAzonosito contains
        defaultSzerzodesesJogviszonyokFiltering(
            "szerzodesAzonosito.contains=" + DEFAULT_SZERZODES_AZONOSITO,
            "szerzodesAzonosito.contains=" + UPDATED_SZERZODES_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksBySzerzodesAzonositoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where szerzodesAzonosito does not contain
        defaultSzerzodesesJogviszonyokFiltering(
            "szerzodesAzonosito.doesNotContain=" + UPDATED_SZERZODES_AZONOSITO,
            "szerzodesAzonosito.doesNotContain=" + DEFAULT_SZERZODES_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete equals to
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyKezdete.equals=" + DEFAULT_JOGVISZONY_KEZDETE,
            "jogviszonyKezdete.equals=" + UPDATED_JOGVISZONY_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete in
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyKezdete.in=" + DEFAULT_JOGVISZONY_KEZDETE + "," + UPDATED_JOGVISZONY_KEZDETE,
            "jogviszonyKezdete.in=" + UPDATED_JOGVISZONY_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete is not null
        defaultSzerzodesesJogviszonyokFiltering("jogviszonyKezdete.specified=true", "jogviszonyKezdete.specified=false");
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete is greater than or equal to
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyKezdete.greaterThanOrEqual=" + DEFAULT_JOGVISZONY_KEZDETE,
            "jogviszonyKezdete.greaterThanOrEqual=" + UPDATED_JOGVISZONY_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete is less than or equal to
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyKezdete.lessThanOrEqual=" + DEFAULT_JOGVISZONY_KEZDETE,
            "jogviszonyKezdete.lessThanOrEqual=" + SMALLER_JOGVISZONY_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete is less than
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyKezdete.lessThan=" + UPDATED_JOGVISZONY_KEZDETE,
            "jogviszonyKezdete.lessThan=" + DEFAULT_JOGVISZONY_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyKezdeteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyKezdete is greater than
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyKezdete.greaterThan=" + SMALLER_JOGVISZONY_KEZDETE,
            "jogviszonyKezdete.greaterThan=" + DEFAULT_JOGVISZONY_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata equals to
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyLejarata.equals=" + DEFAULT_JOGVISZONY_LEJARATA,
            "jogviszonyLejarata.equals=" + UPDATED_JOGVISZONY_LEJARATA
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata in
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyLejarata.in=" + DEFAULT_JOGVISZONY_LEJARATA + "," + UPDATED_JOGVISZONY_LEJARATA,
            "jogviszonyLejarata.in=" + UPDATED_JOGVISZONY_LEJARATA
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata is not null
        defaultSzerzodesesJogviszonyokFiltering("jogviszonyLejarata.specified=true", "jogviszonyLejarata.specified=false");
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata is greater than or equal to
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyLejarata.greaterThanOrEqual=" + DEFAULT_JOGVISZONY_LEJARATA,
            "jogviszonyLejarata.greaterThanOrEqual=" + UPDATED_JOGVISZONY_LEJARATA
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata is less than or equal to
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyLejarata.lessThanOrEqual=" + DEFAULT_JOGVISZONY_LEJARATA,
            "jogviszonyLejarata.lessThanOrEqual=" + SMALLER_JOGVISZONY_LEJARATA
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata is less than
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyLejarata.lessThan=" + UPDATED_JOGVISZONY_LEJARATA,
            "jogviszonyLejarata.lessThan=" + DEFAULT_JOGVISZONY_LEJARATA
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByJogviszonyLejarataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        // Get all the szerzodesesJogviszonyokList where jogviszonyLejarata is greater than
        defaultSzerzodesesJogviszonyokFiltering(
            "jogviszonyLejarata.greaterThan=" + SMALLER_JOGVISZONY_LEJARATA,
            "jogviszonyLejarata.greaterThan=" + DEFAULT_JOGVISZONY_LEJARATA
        );
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByMegrendeloCegIsEqualToSomething() throws Exception {
        CegAlapadatok megrendeloCeg;
        if (TestUtil.findAll(em, CegAlapadatok.class).isEmpty()) {
            szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);
            megrendeloCeg = CegAlapadatokResourceIT.createEntity();
        } else {
            megrendeloCeg = TestUtil.findAll(em, CegAlapadatok.class).get(0);
        }
        em.persist(megrendeloCeg);
        em.flush();
        szerzodesesJogviszonyok.setMegrendeloCeg(megrendeloCeg);
        szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);
        Long megrendeloCegId = megrendeloCeg.getId();
        // Get all the szerzodesesJogviszonyokList where megrendeloCeg equals to megrendeloCegId
        defaultSzerzodesesJogviszonyokShouldBeFound("megrendeloCegId.equals=" + megrendeloCegId);

        // Get all the szerzodesesJogviszonyokList where megrendeloCeg equals to (megrendeloCegId + 1)
        defaultSzerzodesesJogviszonyokShouldNotBeFound("megrendeloCegId.equals=" + (megrendeloCegId + 1));
    }

    @Test
    @Transactional
    void getAllSzerzodesesJogviszonyoksByVallalkozoCegIsEqualToSomething() throws Exception {
        CegAlapadatok vallalkozoCeg;
        if (TestUtil.findAll(em, CegAlapadatok.class).isEmpty()) {
            szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);
            vallalkozoCeg = CegAlapadatokResourceIT.createEntity();
        } else {
            vallalkozoCeg = TestUtil.findAll(em, CegAlapadatok.class).get(0);
        }
        em.persist(vallalkozoCeg);
        em.flush();
        szerzodesesJogviszonyok.setVallalkozoCeg(vallalkozoCeg);
        szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);
        Long vallalkozoCegId = vallalkozoCeg.getId();
        // Get all the szerzodesesJogviszonyokList where vallalkozoCeg equals to vallalkozoCegId
        defaultSzerzodesesJogviszonyokShouldBeFound("vallalkozoCegId.equals=" + vallalkozoCegId);

        // Get all the szerzodesesJogviszonyokList where vallalkozoCeg equals to (vallalkozoCegId + 1)
        defaultSzerzodesesJogviszonyokShouldNotBeFound("vallalkozoCegId.equals=" + (vallalkozoCegId + 1));
    }

    private void defaultSzerzodesesJogviszonyokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSzerzodesesJogviszonyokShouldBeFound(shouldBeFound);
        defaultSzerzodesesJogviszonyokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSzerzodesesJogviszonyokShouldBeFound(String filter) throws Exception {
        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(szerzodesesJogviszonyok.getId().intValue())))
            .andExpect(jsonPath("$.[*].szerzodesAzonosito").value(hasItem(DEFAULT_SZERZODES_AZONOSITO)))
            .andExpect(jsonPath("$.[*].jogviszonyKezdete").value(hasItem(DEFAULT_JOGVISZONY_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].jogviszonyLejarata").value(hasItem(DEFAULT_JOGVISZONY_LEJARATA.toString())));

        // Check, that the count call also returns 1
        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSzerzodesesJogviszonyokShouldNotBeFound(String filter) throws Exception {
        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSzerzodesesJogviszonyokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSzerzodesesJogviszonyok() throws Exception {
        // Get the szerzodesesJogviszonyok
        restSzerzodesesJogviszonyokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSzerzodesesJogviszonyok() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the szerzodesesJogviszonyok
        SzerzodesesJogviszonyok updatedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository
            .findById(szerzodesesJogviszonyok.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSzerzodesesJogviszonyok are not directly saved in db
        em.detach(updatedSzerzodesesJogviszonyok);
        updatedSzerzodesesJogviszonyok
            .szerzodesAzonosito(UPDATED_SZERZODES_AZONOSITO)
            .jogviszonyKezdete(UPDATED_JOGVISZONY_KEZDETE)
            .jogviszonyLejarata(UPDATED_JOGVISZONY_LEJARATA);
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(updatedSzerzodesesJogviszonyok);

        restSzerzodesesJogviszonyokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, szerzodesesJogviszonyokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isOk());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSzerzodesesJogviszonyokToMatchAllProperties(updatedSzerzodesesJogviszonyok);
    }

    @Test
    @Transactional
    void putNonExistingSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        szerzodesesJogviszonyok.setId(longCount.incrementAndGet());

        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, szerzodesesJogviszonyokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        szerzodesesJogviszonyok.setId(longCount.incrementAndGet());

        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        szerzodesesJogviszonyok.setId(longCount.incrementAndGet());

        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSzerzodesesJogviszonyokWithPatch() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the szerzodesesJogviszonyok using partial update
        SzerzodesesJogviszonyok partialUpdatedSzerzodesesJogviszonyok = new SzerzodesesJogviszonyok();
        partialUpdatedSzerzodesesJogviszonyok.setId(szerzodesesJogviszonyok.getId());

        partialUpdatedSzerzodesesJogviszonyok
            .szerzodesAzonosito(UPDATED_SZERZODES_AZONOSITO)
            .jogviszonyLejarata(UPDATED_JOGVISZONY_LEJARATA);

        restSzerzodesesJogviszonyokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSzerzodesesJogviszonyok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSzerzodesesJogviszonyok))
            )
            .andExpect(status().isOk());

        // Validate the SzerzodesesJogviszonyok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSzerzodesesJogviszonyokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSzerzodesesJogviszonyok, szerzodesesJogviszonyok),
            getPersistedSzerzodesesJogviszonyok(szerzodesesJogviszonyok)
        );
    }

    @Test
    @Transactional
    void fullUpdateSzerzodesesJogviszonyokWithPatch() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the szerzodesesJogviszonyok using partial update
        SzerzodesesJogviszonyok partialUpdatedSzerzodesesJogviszonyok = new SzerzodesesJogviszonyok();
        partialUpdatedSzerzodesesJogviszonyok.setId(szerzodesesJogviszonyok.getId());

        partialUpdatedSzerzodesesJogviszonyok
            .szerzodesAzonosito(UPDATED_SZERZODES_AZONOSITO)
            .jogviszonyKezdete(UPDATED_JOGVISZONY_KEZDETE)
            .jogviszonyLejarata(UPDATED_JOGVISZONY_LEJARATA);

        restSzerzodesesJogviszonyokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSzerzodesesJogviszonyok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSzerzodesesJogviszonyok))
            )
            .andExpect(status().isOk());

        // Validate the SzerzodesesJogviszonyok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSzerzodesesJogviszonyokUpdatableFieldsEquals(
            partialUpdatedSzerzodesesJogviszonyok,
            getPersistedSzerzodesesJogviszonyok(partialUpdatedSzerzodesesJogviszonyok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        szerzodesesJogviszonyok.setId(longCount.incrementAndGet());

        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, szerzodesesJogviszonyokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        szerzodesesJogviszonyok.setId(longCount.incrementAndGet());

        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSzerzodesesJogviszonyok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        szerzodesesJogviszonyok.setId(longCount.incrementAndGet());

        // Create the SzerzodesesJogviszonyok
        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = szerzodesesJogviszonyokMapper.toDto(szerzodesesJogviszonyok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSzerzodesesJogviszonyokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(szerzodesesJogviszonyokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SzerzodesesJogviszonyok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSzerzodesesJogviszonyok() throws Exception {
        // Initialize the database
        insertedSzerzodesesJogviszonyok = szerzodesesJogviszonyokRepository.saveAndFlush(szerzodesesJogviszonyok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the szerzodesesJogviszonyok
        restSzerzodesesJogviszonyokMockMvc
            .perform(delete(ENTITY_API_URL_ID, szerzodesesJogviszonyok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return szerzodesesJogviszonyokRepository.count();
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

    protected SzerzodesesJogviszonyok getPersistedSzerzodesesJogviszonyok(SzerzodesesJogviszonyok szerzodesesJogviszonyok) {
        return szerzodesesJogviszonyokRepository.findById(szerzodesesJogviszonyok.getId()).orElseThrow();
    }

    protected void assertPersistedSzerzodesesJogviszonyokToMatchAllProperties(SzerzodesesJogviszonyok expectedSzerzodesesJogviszonyok) {
        assertSzerzodesesJogviszonyokAllPropertiesEquals(
            expectedSzerzodesesJogviszonyok,
            getPersistedSzerzodesesJogviszonyok(expectedSzerzodesesJogviszonyok)
        );
    }

    protected void assertPersistedSzerzodesesJogviszonyokToMatchUpdatableProperties(
        SzerzodesesJogviszonyok expectedSzerzodesesJogviszonyok
    ) {
        assertSzerzodesesJogviszonyokAllUpdatablePropertiesEquals(
            expectedSzerzodesesJogviszonyok,
            getPersistedSzerzodesesJogviszonyok(expectedSzerzodesesJogviszonyok)
        );
    }
}

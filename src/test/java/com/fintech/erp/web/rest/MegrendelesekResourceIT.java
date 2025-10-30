package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.MegrendelesekAsserts.assertMegrendelesekAllPropertiesEquals;
import static com.fintech.erp.domain.MegrendelesekAsserts.assertMegrendelesekAllUpdatablePropertiesEquals;
import static com.fintech.erp.domain.MegrendelesekAsserts.assertMegrendelesekUpdatableFieldsEquals;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.fintech.erp.web.rest.TestUtil.sameNumber;
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
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesForras;
import com.fintech.erp.domain.enumeration.MegrendelesStatusz;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import com.fintech.erp.domain.enumeration.ResztvevoTipus;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.mapper.MegrendelesekMapper;
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
 * Integration tests for the {@link MegrendelesekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MegrendelesekResourceIT {

    private static final MegrendelesTipus DEFAULT_MEGRENDELES_TIPUSA = MegrendelesTipus.FOLYAMATOS_TELESTITES;
    private static final MegrendelesTipus UPDATED_MEGRENDELES_TIPUSA = MegrendelesTipus.EGYSZERI;

    private static final String DEFAULT_FELADAT_ROVID_LEIRASA = "AAAAAAAAAA";
    private static final String UPDATED_FELADAT_ROVID_LEIRASA = "BBBBBBBBBB";

    private static final String DEFAULT_FELADAT_RESZLETES_LEIRASA = "AAAAAAAAAA";
    private static final String UPDATED_FELADAT_RESZLETES_LEIRASA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MEGRENDELES_KEZDETE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEGRENDELES_KEZDETE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MEGRENDELES_KEZDETE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_MEGRENDELES_VEGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEGRENDELES_VEGE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MEGRENDELES_VEGE = LocalDate.ofEpochDay(-1L);

    private static final ResztvevoKollagaTipus DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA = ResztvevoKollagaTipus.MUNKAVALLALO;
    private static final ResztvevoKollagaTipus UPDATED_RESZTVEVO_KOLLAGA_TIPUSA = ResztvevoKollagaTipus.ALVALLALKOZO;

    private static final Devizanem DEFAULT_DEVIZANEM = Devizanem.HUF;
    private static final Devizanem UPDATED_DEVIZANEM = Devizanem.EUR;

    private static final DijazasTipusa DEFAULT_DIJAZAS_TIPUSA = DijazasTipusa.NAPIDIJ;
    private static final DijazasTipusa UPDATED_DIJAZAS_TIPUSA = DijazasTipusa.EGYSZERI;

    private static final BigDecimal DEFAULT_DIJ_OSSZEGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DIJ_OSSZEGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_DIJ_OSSZEGE = new BigDecimal(1 - 1);

    private static final MegrendelesDokumentumEredet DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA = MegrendelesDokumentumEredet.KEZI;
    private static final MegrendelesDokumentumEredet UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA = MegrendelesDokumentumEredet.GENERALTA;

    private static final Long DEFAULT_UGYFEL_MEGRENDELES_ID = 1L;
    private static final Long UPDATED_UGYFEL_MEGRENDELES_ID = 2L;
    private static final Long SMALLER_UGYFEL_MEGRENDELES_ID = 1L - 1L;

    private static final ResztvevoTipus DEFAULT_RESZTVEVO_TIPUS = ResztvevoTipus.CEG;
    private static final ResztvevoTipus UPDATED_RESZTVEVO_TIPUS = ResztvevoTipus.ALVALLALKOZO;

    private static final String DEFAULT_MEGRENDELES_SZAM = "MEGR-TEST-00001";
    private static final String UPDATED_MEGRENDELES_SZAM = "MEGR-TEST-00002";

    private static final MegrendelesStatusz DEFAULT_MEGRENDELES_STATUSZ = MegrendelesStatusz.DRAFT;
    private static final MegrendelesStatusz UPDATED_MEGRENDELES_STATUSZ = MegrendelesStatusz.SIGNED;

    private static final MegrendelesForras DEFAULT_MEGRENDELES_FORRASA = MegrendelesForras.UGYFEL;
    private static final MegrendelesForras UPDATED_MEGRENDELES_FORRASA = MegrendelesForras.SAJAT;

    private static final Integer DEFAULT_PELDANYOK_SZAMA = 1;
    private static final Integer UPDATED_PELDANYOK_SZAMA = 5;
    private static final Integer SMALLER_PELDANYOK_SZAMA = 0;

    private static final Boolean DEFAULT_SZAMLAZANDO = false;
    private static final Boolean UPDATED_SZAMLAZANDO = true;

    private static final String ENTITY_API_URL = "/api/megrendeleseks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MegrendelesekRepository megrendelesekRepository;

    @Autowired
    private MegrendelesekMapper megrendelesekMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMegrendelesekMockMvc;

    private Megrendelesek megrendelesek;

    private Megrendelesek insertedMegrendelesek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Megrendelesek createEntity() {
        return new Megrendelesek()
            .megrendelesTipusa(DEFAULT_MEGRENDELES_TIPUSA)
            .feladatRovidLeirasa(DEFAULT_FELADAT_ROVID_LEIRASA)
            .feladatReszletesLeirasa(DEFAULT_FELADAT_RESZLETES_LEIRASA)
            .megrendelesKezdete(DEFAULT_MEGRENDELES_KEZDETE)
            .megrendelesVege(DEFAULT_MEGRENDELES_VEGE)
            .resztvevoKollagaTipusa(DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA)
            .resztvevoTipus(DEFAULT_RESZTVEVO_TIPUS)
            .devizanem(DEFAULT_DEVIZANEM)
            .dijazasTipusa(DEFAULT_DIJAZAS_TIPUSA)
            .dijOsszege(DEFAULT_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(DEFAULT_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(DEFAULT_MEGRENDELES_SZAM)
            .megrendelesStatusz(DEFAULT_MEGRENDELES_STATUSZ)
            .megrendelesForrasa(DEFAULT_MEGRENDELES_FORRASA)
            .peldanyokSzama(DEFAULT_PELDANYOK_SZAMA)
            .szamlazando(DEFAULT_SZAMLAZANDO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Megrendelesek createUpdatedEntity() {
        return new Megrendelesek()
            .megrendelesTipusa(UPDATED_MEGRENDELES_TIPUSA)
            .feladatRovidLeirasa(UPDATED_FELADAT_ROVID_LEIRASA)
            .feladatReszletesLeirasa(UPDATED_FELADAT_RESZLETES_LEIRASA)
            .megrendelesKezdete(UPDATED_MEGRENDELES_KEZDETE)
            .megrendelesVege(UPDATED_MEGRENDELES_VEGE)
            .resztvevoKollagaTipusa(UPDATED_RESZTVEVO_KOLLAGA_TIPUSA)
            .resztvevoTipus(UPDATED_RESZTVEVO_TIPUS)
            .devizanem(UPDATED_DEVIZANEM)
            .dijazasTipusa(UPDATED_DIJAZAS_TIPUSA)
            .dijOsszege(UPDATED_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(UPDATED_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(UPDATED_MEGRENDELES_SZAM)
            .megrendelesStatusz(UPDATED_MEGRENDELES_STATUSZ)
            .megrendelesForrasa(UPDATED_MEGRENDELES_FORRASA)
            .peldanyokSzama(UPDATED_PELDANYOK_SZAMA)
            .szamlazando(UPDATED_SZAMLAZANDO);
    }

    @BeforeEach
    void initTest() {
        megrendelesek = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMegrendelesek != null) {
            megrendelesekRepository.delete(insertedMegrendelesek);
            insertedMegrendelesek = null;
        }
    }

    @Test
    @Transactional
    void createMegrendelesek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);
        var returnedMegrendelesekDTO = om.readValue(
            restMegrendelesekMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(megrendelesekDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MegrendelesekDTO.class
        );

        // Validate the Megrendelesek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMegrendelesek = megrendelesekMapper.toEntity(returnedMegrendelesekDTO);
        assertMegrendelesekUpdatableFieldsEquals(returnedMegrendelesek, getPersistedMegrendelesek(returnedMegrendelesek));

        insertedMegrendelesek = returnedMegrendelesek;
    }

    @Test
    @Transactional
    void createMegrendelesekWithExistingId() throws Exception {
        // Create the Megrendelesek with an existing ID
        megrendelesek.setId(1L);
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMegrendelesekMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMegrendeleseks() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList
        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(megrendelesek.getId().intValue())))
            .andExpect(jsonPath("$.[*].megrendelesTipusa").value(hasItem(DEFAULT_MEGRENDELES_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].feladatRovidLeirasa").value(hasItem(DEFAULT_FELADAT_ROVID_LEIRASA)))
            .andExpect(jsonPath("$.[*].feladatReszletesLeirasa").value(hasItem(DEFAULT_FELADAT_RESZLETES_LEIRASA)))
            .andExpect(jsonPath("$.[*].megrendelesKezdete").value(hasItem(DEFAULT_MEGRENDELES_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].megrendelesVege").value(hasItem(DEFAULT_MEGRENDELES_VEGE.toString())))
            .andExpect(jsonPath("$.[*].resztvevoKollagaTipusa").value(hasItem(DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].resztvevoTipus").value(hasItem(DEFAULT_RESZTVEVO_TIPUS.toString())))
            .andExpect(jsonPath("$.[*].devizanem").value(hasItem(DEFAULT_DEVIZANEM.toString())))
            .andExpect(jsonPath("$.[*].dijazasTipusa").value(hasItem(DEFAULT_DIJAZAS_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].dijOsszege").value(hasItem(sameNumber(DEFAULT_DIJ_OSSZEGE))))
            .andExpect(jsonPath("$.[*].megrendelesDokumentumGeneralta").value(hasItem(DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA.toString())))
            .andExpect(jsonPath("$.[*].ugyfelMegrendelesId").value(hasItem(DEFAULT_UGYFEL_MEGRENDELES_ID.intValue())))
            .andExpect(jsonPath("$.[*].megrendelesSzam").value(hasItem(DEFAULT_MEGRENDELES_SZAM)))
            .andExpect(jsonPath("$.[*].megrendelesStatusz").value(hasItem(DEFAULT_MEGRENDELES_STATUSZ.toString())))
            .andExpect(jsonPath("$.[*].megrendelesForrasa").value(hasItem(DEFAULT_MEGRENDELES_FORRASA.toString())))
            .andExpect(jsonPath("$.[*].peldanyokSzama").value(hasItem(DEFAULT_PELDANYOK_SZAMA)))
            .andExpect(jsonPath("$.[*].szamlazando").value(hasItem(DEFAULT_SZAMLAZANDO)));
    }

    @Test
    @Transactional
    void getMegrendelesek() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get the megrendelesek
        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL_ID, megrendelesek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(megrendelesek.getId().intValue()))
            .andExpect(jsonPath("$.megrendelesTipusa").value(DEFAULT_MEGRENDELES_TIPUSA.toString()))
            .andExpect(jsonPath("$.feladatRovidLeirasa").value(DEFAULT_FELADAT_ROVID_LEIRASA))
            .andExpect(jsonPath("$.feladatReszletesLeirasa").value(DEFAULT_FELADAT_RESZLETES_LEIRASA))
            .andExpect(jsonPath("$.megrendelesKezdete").value(DEFAULT_MEGRENDELES_KEZDETE.toString()))
            .andExpect(jsonPath("$.megrendelesVege").value(DEFAULT_MEGRENDELES_VEGE.toString()))
            .andExpect(jsonPath("$.resztvevoKollagaTipusa").value(DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA.toString()))
            .andExpect(jsonPath("$.resztvevoTipus").value(DEFAULT_RESZTVEVO_TIPUS.toString()))
            .andExpect(jsonPath("$.devizanem").value(DEFAULT_DEVIZANEM.toString()))
            .andExpect(jsonPath("$.dijazasTipusa").value(DEFAULT_DIJAZAS_TIPUSA.toString()))
            .andExpect(jsonPath("$.dijOsszege").value(sameNumber(DEFAULT_DIJ_OSSZEGE)))
            .andExpect(jsonPath("$.megrendelesDokumentumGeneralta").value(DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA.toString()))
            .andExpect(jsonPath("$.ugyfelMegrendelesId").value(DEFAULT_UGYFEL_MEGRENDELES_ID.intValue()))
            .andExpect(jsonPath("$.megrendelesSzam").value(DEFAULT_MEGRENDELES_SZAM))
            .andExpect(jsonPath("$.megrendelesStatusz").value(DEFAULT_MEGRENDELES_STATUSZ.toString()))
            .andExpect(jsonPath("$.megrendelesForrasa").value(DEFAULT_MEGRENDELES_FORRASA.toString()))
            .andExpect(jsonPath("$.peldanyokSzama").value(DEFAULT_PELDANYOK_SZAMA))
            .andExpect(jsonPath("$.szamlazando").value(DEFAULT_SZAMLAZANDO));
    }

    @Test
    @Transactional
    void getMegrendeleseksByIdFiltering() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        Long id = megrendelesek.getId();

        defaultMegrendelesekFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMegrendelesekFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMegrendelesekFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesTipusaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesTipusa equals to
        defaultMegrendelesekFiltering(
            "megrendelesTipusa.equals=" + DEFAULT_MEGRENDELES_TIPUSA,
            "megrendelesTipusa.equals=" + UPDATED_MEGRENDELES_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesTipusaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesTipusa in
        defaultMegrendelesekFiltering(
            "megrendelesTipusa.in=" + DEFAULT_MEGRENDELES_TIPUSA + "," + UPDATED_MEGRENDELES_TIPUSA,
            "megrendelesTipusa.in=" + UPDATED_MEGRENDELES_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesTipusaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesTipusa is not null
        defaultMegrendelesekFiltering("megrendelesTipusa.specified=true", "megrendelesTipusa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatRovidLeirasaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatRovidLeirasa equals to
        defaultMegrendelesekFiltering(
            "feladatRovidLeirasa.equals=" + DEFAULT_FELADAT_ROVID_LEIRASA,
            "feladatRovidLeirasa.equals=" + UPDATED_FELADAT_ROVID_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatRovidLeirasaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatRovidLeirasa in
        defaultMegrendelesekFiltering(
            "feladatRovidLeirasa.in=" + DEFAULT_FELADAT_ROVID_LEIRASA + "," + UPDATED_FELADAT_ROVID_LEIRASA,
            "feladatRovidLeirasa.in=" + UPDATED_FELADAT_ROVID_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatRovidLeirasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatRovidLeirasa is not null
        defaultMegrendelesekFiltering("feladatRovidLeirasa.specified=true", "feladatRovidLeirasa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatRovidLeirasaContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatRovidLeirasa contains
        defaultMegrendelesekFiltering(
            "feladatRovidLeirasa.contains=" + DEFAULT_FELADAT_ROVID_LEIRASA,
            "feladatRovidLeirasa.contains=" + UPDATED_FELADAT_ROVID_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatRovidLeirasaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatRovidLeirasa does not contain
        defaultMegrendelesekFiltering(
            "feladatRovidLeirasa.doesNotContain=" + UPDATED_FELADAT_ROVID_LEIRASA,
            "feladatRovidLeirasa.doesNotContain=" + DEFAULT_FELADAT_ROVID_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatReszletesLeirasaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatReszletesLeirasa equals to
        defaultMegrendelesekFiltering(
            "feladatReszletesLeirasa.equals=" + DEFAULT_FELADAT_RESZLETES_LEIRASA,
            "feladatReszletesLeirasa.equals=" + UPDATED_FELADAT_RESZLETES_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatReszletesLeirasaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatReszletesLeirasa in
        defaultMegrendelesekFiltering(
            "feladatReszletesLeirasa.in=" + DEFAULT_FELADAT_RESZLETES_LEIRASA + "," + UPDATED_FELADAT_RESZLETES_LEIRASA,
            "feladatReszletesLeirasa.in=" + UPDATED_FELADAT_RESZLETES_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatReszletesLeirasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatReszletesLeirasa is not null
        defaultMegrendelesekFiltering("feladatReszletesLeirasa.specified=true", "feladatReszletesLeirasa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatReszletesLeirasaContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatReszletesLeirasa contains
        defaultMegrendelesekFiltering(
            "feladatReszletesLeirasa.contains=" + DEFAULT_FELADAT_RESZLETES_LEIRASA,
            "feladatReszletesLeirasa.contains=" + UPDATED_FELADAT_RESZLETES_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByFeladatReszletesLeirasaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where feladatReszletesLeirasa does not contain
        defaultMegrendelesekFiltering(
            "feladatReszletesLeirasa.doesNotContain=" + UPDATED_FELADAT_RESZLETES_LEIRASA,
            "feladatReszletesLeirasa.doesNotContain=" + DEFAULT_FELADAT_RESZLETES_LEIRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete equals to
        defaultMegrendelesekFiltering(
            "megrendelesKezdete.equals=" + DEFAULT_MEGRENDELES_KEZDETE,
            "megrendelesKezdete.equals=" + UPDATED_MEGRENDELES_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete in
        defaultMegrendelesekFiltering(
            "megrendelesKezdete.in=" + DEFAULT_MEGRENDELES_KEZDETE + "," + UPDATED_MEGRENDELES_KEZDETE,
            "megrendelesKezdete.in=" + UPDATED_MEGRENDELES_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete is not null
        defaultMegrendelesekFiltering("megrendelesKezdete.specified=true", "megrendelesKezdete.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete is greater than or equal to
        defaultMegrendelesekFiltering(
            "megrendelesKezdete.greaterThanOrEqual=" + DEFAULT_MEGRENDELES_KEZDETE,
            "megrendelesKezdete.greaterThanOrEqual=" + UPDATED_MEGRENDELES_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete is less than or equal to
        defaultMegrendelesekFiltering(
            "megrendelesKezdete.lessThanOrEqual=" + DEFAULT_MEGRENDELES_KEZDETE,
            "megrendelesKezdete.lessThanOrEqual=" + SMALLER_MEGRENDELES_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete is less than
        defaultMegrendelesekFiltering(
            "megrendelesKezdete.lessThan=" + UPDATED_MEGRENDELES_KEZDETE,
            "megrendelesKezdete.lessThan=" + DEFAULT_MEGRENDELES_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesKezdeteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesKezdete is greater than
        defaultMegrendelesekFiltering(
            "megrendelesKezdete.greaterThan=" + SMALLER_MEGRENDELES_KEZDETE,
            "megrendelesKezdete.greaterThan=" + DEFAULT_MEGRENDELES_KEZDETE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege equals to
        defaultMegrendelesekFiltering(
            "megrendelesVege.equals=" + DEFAULT_MEGRENDELES_VEGE,
            "megrendelesVege.equals=" + UPDATED_MEGRENDELES_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege in
        defaultMegrendelesekFiltering(
            "megrendelesVege.in=" + DEFAULT_MEGRENDELES_VEGE + "," + UPDATED_MEGRENDELES_VEGE,
            "megrendelesVege.in=" + UPDATED_MEGRENDELES_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege is not null
        defaultMegrendelesekFiltering("megrendelesVege.specified=true", "megrendelesVege.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege is greater than or equal to
        defaultMegrendelesekFiltering(
            "megrendelesVege.greaterThanOrEqual=" + DEFAULT_MEGRENDELES_VEGE,
            "megrendelesVege.greaterThanOrEqual=" + UPDATED_MEGRENDELES_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege is less than or equal to
        defaultMegrendelesekFiltering(
            "megrendelesVege.lessThanOrEqual=" + DEFAULT_MEGRENDELES_VEGE,
            "megrendelesVege.lessThanOrEqual=" + SMALLER_MEGRENDELES_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege is less than
        defaultMegrendelesekFiltering(
            "megrendelesVege.lessThan=" + UPDATED_MEGRENDELES_VEGE,
            "megrendelesVege.lessThan=" + DEFAULT_MEGRENDELES_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesVegeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesVege is greater than
        defaultMegrendelesekFiltering(
            "megrendelesVege.greaterThan=" + SMALLER_MEGRENDELES_VEGE,
            "megrendelesVege.greaterThan=" + DEFAULT_MEGRENDELES_VEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoKollagaTipusaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoKollagaTipusa equals to
        defaultMegrendelesekFiltering(
            "resztvevoKollagaTipusa.equals=" + DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA,
            "resztvevoKollagaTipusa.equals=" + UPDATED_RESZTVEVO_KOLLAGA_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoKollagaTipusaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoKollagaTipusa in
        defaultMegrendelesekFiltering(
            "resztvevoKollagaTipusa.in=" + DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA + "," + UPDATED_RESZTVEVO_KOLLAGA_TIPUSA,
            "resztvevoKollagaTipusa.in=" + UPDATED_RESZTVEVO_KOLLAGA_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoKollagaTipusaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoKollagaTipusa is not null
        defaultMegrendelesekFiltering("resztvevoKollagaTipusa.specified=true", "resztvevoKollagaTipusa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoKollagaTipusaContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoKollagaTipusa contains
        defaultMegrendelesekFiltering(
            "resztvevoKollagaTipusa.contains=" + DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA,
            "resztvevoKollagaTipusa.contains=" + UPDATED_RESZTVEVO_KOLLAGA_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoKollagaTipusaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoKollagaTipusa does not contain
        defaultMegrendelesekFiltering(
            "resztvevoKollagaTipusa.doesNotContain=" + UPDATED_RESZTVEVO_KOLLAGA_TIPUSA,
            "resztvevoKollagaTipusa.doesNotContain=" + DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoTipusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoTipus equals to
        defaultMegrendelesekFiltering(
            "resztvevoTipus.equals=" + DEFAULT_RESZTVEVO_TIPUS,
            "resztvevoTipus.equals=" + UPDATED_RESZTVEVO_TIPUS
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoTipusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoTipus in
        defaultMegrendelesekFiltering(
            "resztvevoTipus.in=" + DEFAULT_RESZTVEVO_TIPUS + "," + UPDATED_RESZTVEVO_TIPUS,
            "resztvevoTipus.in=" + UPDATED_RESZTVEVO_TIPUS
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByResztvevoTipusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where resztvevoTipus is not null
        defaultMegrendelesekFiltering("resztvevoTipus.specified=true", "resztvevoTipus.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDevizanemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where devizanem equals to
        defaultMegrendelesekFiltering("devizanem.equals=" + DEFAULT_DEVIZANEM, "devizanem.equals=" + UPDATED_DEVIZANEM);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDevizanemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where devizanem in
        defaultMegrendelesekFiltering("devizanem.in=" + DEFAULT_DEVIZANEM + "," + UPDATED_DEVIZANEM, "devizanem.in=" + UPDATED_DEVIZANEM);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDevizanemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where devizanem is not null
        defaultMegrendelesekFiltering("devizanem.specified=true", "devizanem.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDevizanemContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where devizanem contains
        defaultMegrendelesekFiltering("devizanem.contains=" + DEFAULT_DEVIZANEM, "devizanem.contains=" + UPDATED_DEVIZANEM);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDevizanemNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where devizanem does not contain
        defaultMegrendelesekFiltering("devizanem.doesNotContain=" + UPDATED_DEVIZANEM, "devizanem.doesNotContain=" + DEFAULT_DEVIZANEM);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijazasTipusaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijazasTipusa equals to
        defaultMegrendelesekFiltering("dijazasTipusa.equals=" + DEFAULT_DIJAZAS_TIPUSA, "dijazasTipusa.equals=" + UPDATED_DIJAZAS_TIPUSA);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijazasTipusaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijazasTipusa in
        defaultMegrendelesekFiltering(
            "dijazasTipusa.in=" + DEFAULT_DIJAZAS_TIPUSA + "," + UPDATED_DIJAZAS_TIPUSA,
            "dijazasTipusa.in=" + UPDATED_DIJAZAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijazasTipusaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijazasTipusa is not null
        defaultMegrendelesekFiltering("dijazasTipusa.specified=true", "dijazasTipusa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijazasTipusaContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijazasTipusa contains
        defaultMegrendelesekFiltering(
            "dijazasTipusa.contains=" + DEFAULT_DIJAZAS_TIPUSA,
            "dijazasTipusa.contains=" + UPDATED_DIJAZAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijazasTipusaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijazasTipusa does not contain
        defaultMegrendelesekFiltering(
            "dijazasTipusa.doesNotContain=" + UPDATED_DIJAZAS_TIPUSA,
            "dijazasTipusa.doesNotContain=" + DEFAULT_DIJAZAS_TIPUSA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege equals to
        defaultMegrendelesekFiltering("dijOsszege.equals=" + DEFAULT_DIJ_OSSZEGE, "dijOsszege.equals=" + UPDATED_DIJ_OSSZEGE);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege in
        defaultMegrendelesekFiltering(
            "dijOsszege.in=" + DEFAULT_DIJ_OSSZEGE + "," + UPDATED_DIJ_OSSZEGE,
            "dijOsszege.in=" + UPDATED_DIJ_OSSZEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege is not null
        defaultMegrendelesekFiltering("dijOsszege.specified=true", "dijOsszege.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege is greater than or equal to
        defaultMegrendelesekFiltering(
            "dijOsszege.greaterThanOrEqual=" + DEFAULT_DIJ_OSSZEGE,
            "dijOsszege.greaterThanOrEqual=" + UPDATED_DIJ_OSSZEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege is less than or equal to
        defaultMegrendelesekFiltering(
            "dijOsszege.lessThanOrEqual=" + DEFAULT_DIJ_OSSZEGE,
            "dijOsszege.lessThanOrEqual=" + SMALLER_DIJ_OSSZEGE
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege is less than
        defaultMegrendelesekFiltering("dijOsszege.lessThan=" + UPDATED_DIJ_OSSZEGE, "dijOsszege.lessThan=" + DEFAULT_DIJ_OSSZEGE);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByDijOsszegeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where dijOsszege is greater than
        defaultMegrendelesekFiltering("dijOsszege.greaterThan=" + SMALLER_DIJ_OSSZEGE, "dijOsszege.greaterThan=" + DEFAULT_DIJ_OSSZEGE);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesDokumentumGeneraltaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesDokumentumGeneralta equals to
        defaultMegrendelesekFiltering(
            "megrendelesDokumentumGeneralta.equals=" + DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA,
            "megrendelesDokumentumGeneralta.equals=" + UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesDokumentumGeneraltaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesDokumentumGeneralta in
        defaultMegrendelesekFiltering(
            "megrendelesDokumentumGeneralta.in=" +
            DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA +
            "," +
            UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA,
            "megrendelesDokumentumGeneralta.in=" + UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesDokumentumGeneraltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesDokumentumGeneralta is not null
        defaultMegrendelesekFiltering("megrendelesDokumentumGeneralta.specified=true", "megrendelesDokumentumGeneralta.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesSzamIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesSzam equals to
        defaultMegrendelesekFiltering(
            "megrendelesSzam.equals=" + DEFAULT_MEGRENDELES_SZAM,
            "megrendelesSzam.equals=" + UPDATED_MEGRENDELES_SZAM
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesSzamIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesSzam in
        defaultMegrendelesekFiltering(
            "megrendelesSzam.in=" + DEFAULT_MEGRENDELES_SZAM + "," + UPDATED_MEGRENDELES_SZAM,
            "megrendelesSzam.in=" + UPDATED_MEGRENDELES_SZAM
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesSzamIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesSzam is not null
        defaultMegrendelesekFiltering("megrendelesSzam.specified=true", "megrendelesSzam.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesSzamContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesSzam contains
        defaultMegrendelesekFiltering(
            "megrendelesSzam.contains=" + DEFAULT_MEGRENDELES_SZAM,
            "megrendelesSzam.contains=" + UPDATED_MEGRENDELES_SZAM
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesSzamNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesSzam does not contain
        defaultMegrendelesekFiltering(
            "megrendelesSzam.doesNotContain=" + UPDATED_MEGRENDELES_SZAM,
            "megrendelesSzam.doesNotContain=" + DEFAULT_MEGRENDELES_SZAM
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesStatusz equals to
        defaultMegrendelesekFiltering(
            "megrendelesStatusz.equals=" + DEFAULT_MEGRENDELES_STATUSZ,
            "megrendelesStatusz.equals=" + UPDATED_MEGRENDELES_STATUSZ
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesStatusz in
        defaultMegrendelesekFiltering(
            "megrendelesStatusz.in=" + DEFAULT_MEGRENDELES_STATUSZ + "," + UPDATED_MEGRENDELES_STATUSZ,
            "megrendelesStatusz.in=" + UPDATED_MEGRENDELES_STATUSZ
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesStatusz is not null
        defaultMegrendelesekFiltering("megrendelesStatusz.specified=true", "megrendelesStatusz.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesForrasaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesForrasa equals to
        defaultMegrendelesekFiltering(
            "megrendelesForrasa.equals=" + DEFAULT_MEGRENDELES_FORRASA,
            "megrendelesForrasa.equals=" + UPDATED_MEGRENDELES_FORRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesForrasaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesForrasa in
        defaultMegrendelesekFiltering(
            "megrendelesForrasa.in=" + DEFAULT_MEGRENDELES_FORRASA + "," + UPDATED_MEGRENDELES_FORRASA,
            "megrendelesForrasa.in=" + UPDATED_MEGRENDELES_FORRASA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMegrendelesForrasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where megrendelesForrasa is not null
        defaultMegrendelesekFiltering("megrendelesForrasa.specified=true", "megrendelesForrasa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama equals to
        defaultMegrendelesekFiltering(
            "peldanyokSzama.equals=" + DEFAULT_PELDANYOK_SZAMA,
            "peldanyokSzama.equals=" + UPDATED_PELDANYOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama in
        defaultMegrendelesekFiltering(
            "peldanyokSzama.in=" + DEFAULT_PELDANYOK_SZAMA + "," + UPDATED_PELDANYOK_SZAMA,
            "peldanyokSzama.in=" + UPDATED_PELDANYOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama is not null
        defaultMegrendelesekFiltering("peldanyokSzama.specified=true", "peldanyokSzama.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama is greater than or equal to
        defaultMegrendelesekFiltering(
            "peldanyokSzama.greaterThanOrEqual=" + DEFAULT_PELDANYOK_SZAMA,
            "peldanyokSzama.greaterThanOrEqual=" + UPDATED_PELDANYOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama is less than or equal to
        defaultMegrendelesekFiltering(
            "peldanyokSzama.lessThanOrEqual=" + DEFAULT_PELDANYOK_SZAMA,
            "peldanyokSzama.lessThanOrEqual=" + SMALLER_PELDANYOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama is less than
        defaultMegrendelesekFiltering(
            "peldanyokSzama.lessThan=" + UPDATED_PELDANYOK_SZAMA,
            "peldanyokSzama.lessThan=" + DEFAULT_PELDANYOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByPeldanyokSzamaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where peldanyokSzama is greater than
        defaultMegrendelesekFiltering(
            "peldanyokSzama.greaterThan=" + SMALLER_PELDANYOK_SZAMA,
            "peldanyokSzama.greaterThan=" + DEFAULT_PELDANYOK_SZAMA
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksBySzamlazandoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where szamlazando equals to
        defaultMegrendelesekFiltering("szamlazando.equals=" + DEFAULT_SZAMLAZANDO, "szamlazando.equals=" + UPDATED_SZAMLAZANDO);
    }

    @Test
    @Transactional
    void getAllMegrendeleseksBySzamlazandoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where szamlazando in
        defaultMegrendelesekFiltering(
            "szamlazando.in=" + DEFAULT_SZAMLAZANDO + "," + UPDATED_SZAMLAZANDO,
            "szamlazando.in=" + UPDATED_SZAMLAZANDO
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksBySzamlazandoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where szamlazando is not null
        defaultMegrendelesekFiltering("szamlazando.specified=true", "szamlazando.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId equals to
        defaultMegrendelesekFiltering(
            "ugyfelMegrendelesId.equals=" + DEFAULT_UGYFEL_MEGRENDELES_ID,
            "ugyfelMegrendelesId.equals=" + UPDATED_UGYFEL_MEGRENDELES_ID
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId in
        defaultMegrendelesekFiltering(
            "ugyfelMegrendelesId.in=" + DEFAULT_UGYFEL_MEGRENDELES_ID + "," + UPDATED_UGYFEL_MEGRENDELES_ID,
            "ugyfelMegrendelesId.in=" + UPDATED_UGYFEL_MEGRENDELES_ID
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId is not null
        defaultMegrendelesekFiltering("ugyfelMegrendelesId.specified=true", "ugyfelMegrendelesId.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId is greater than or equal to
        defaultMegrendelesekFiltering(
            "ugyfelMegrendelesId.greaterThanOrEqual=" + DEFAULT_UGYFEL_MEGRENDELES_ID,
            "ugyfelMegrendelesId.greaterThanOrEqual=" + UPDATED_UGYFEL_MEGRENDELES_ID
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId is less than or equal to
        defaultMegrendelesekFiltering(
            "ugyfelMegrendelesId.lessThanOrEqual=" + DEFAULT_UGYFEL_MEGRENDELES_ID,
            "ugyfelMegrendelesId.lessThanOrEqual=" + SMALLER_UGYFEL_MEGRENDELES_ID
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId is less than
        defaultMegrendelesekFiltering(
            "ugyfelMegrendelesId.lessThan=" + UPDATED_UGYFEL_MEGRENDELES_ID,
            "ugyfelMegrendelesId.lessThan=" + DEFAULT_UGYFEL_MEGRENDELES_ID
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByUgyfelMegrendelesIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        // Get all the megrendelesekList where ugyfelMegrendelesId is greater than
        defaultMegrendelesekFiltering(
            "ugyfelMegrendelesId.greaterThan=" + SMALLER_UGYFEL_MEGRENDELES_ID,
            "ugyfelMegrendelesId.greaterThan=" + DEFAULT_UGYFEL_MEGRENDELES_ID
        );
    }

    @Test
    @Transactional
    void getAllMegrendeleseksBySzerzodesesJogviszonyIsEqualToSomething() throws Exception {
        megrendelesekRepository.saveAndFlush(megrendelesek);
        SzerzodesesJogviszonyok szerzodesesJogviszony = SzerzodesesJogviszonyokResourceIT.createEntity();
        CegAlapadatok megrendeloCeg = CegAlapadatokResourceIT.createEntity();
        em.persist(megrendeloCeg);
        CegAlapadatok vallalkozoCeg = CegAlapadatokResourceIT.createUpdatedEntity();
        em.persist(vallalkozoCeg);
        szerzodesesJogviszony.megrendeloCeg(megrendeloCeg);
        szerzodesesJogviszony.vallalkozoCeg(vallalkozoCeg);
        em.persist(szerzodesesJogviszony);
        em.flush();
        megrendelesek.setSzerzodesesJogviszony(szerzodesesJogviszony);
        megrendelesekRepository.saveAndFlush(megrendelesek);
        Long szerzodesesJogviszonyId = szerzodesesJogviszony.getId();
        // Get all the megrendelesekList where szerzodesesJogviszony equals to szerzodesesJogviszonyId
        defaultMegrendelesekShouldBeFound("szerzodesesJogviszonyId.equals=" + szerzodesesJogviszonyId);

        // Get all the megrendelesekList where szerzodesesJogviszony equals to (szerzodesesJogviszonyId + 1)
        defaultMegrendelesekShouldNotBeFound("szerzodesesJogviszonyId.equals=" + (szerzodesesJogviszonyId + 1));
    }

    @Test
    @Transactional
    void getAllMegrendeleseksByMaganszemelyIsEqualToSomething() throws Exception {
        Maganszemelyek maganszemely;
        if (TestUtil.findAll(em, Maganszemelyek.class).isEmpty()) {
            megrendelesekRepository.saveAndFlush(megrendelesek);
            maganszemely = MaganszemelyekResourceIT.createEntity();
        } else {
            maganszemely = TestUtil.findAll(em, Maganszemelyek.class).get(0);
        }
        em.persist(maganszemely);
        em.flush();
        megrendelesek.setMaganszemely(maganszemely);
        megrendelesekRepository.saveAndFlush(megrendelesek);
        Long maganszemelyId = maganszemely.getId();
        // Get all the megrendelesekList where maganszemely equals to maganszemelyId
        defaultMegrendelesekShouldBeFound("maganszemelyId.equals=" + maganszemelyId);

        // Get all the megrendelesekList where maganszemely equals to (maganszemelyId + 1)
        defaultMegrendelesekShouldNotBeFound("maganszemelyId.equals=" + (maganszemelyId + 1));
    }

    private void defaultMegrendelesekFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMegrendelesekShouldBeFound(shouldBeFound);
        defaultMegrendelesekShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMegrendelesekShouldBeFound(String filter) throws Exception {
        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(megrendelesek.getId().intValue())))
            .andExpect(jsonPath("$.[*].megrendelesTipusa").value(hasItem(DEFAULT_MEGRENDELES_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].feladatRovidLeirasa").value(hasItem(DEFAULT_FELADAT_ROVID_LEIRASA)))
            .andExpect(jsonPath("$.[*].feladatReszletesLeirasa").value(hasItem(DEFAULT_FELADAT_RESZLETES_LEIRASA)))
            .andExpect(jsonPath("$.[*].megrendelesKezdete").value(hasItem(DEFAULT_MEGRENDELES_KEZDETE.toString())))
            .andExpect(jsonPath("$.[*].megrendelesVege").value(hasItem(DEFAULT_MEGRENDELES_VEGE.toString())))
            .andExpect(jsonPath("$.[*].resztvevoKollagaTipusa").value(hasItem(DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].resztvevoTipus").value(hasItem(DEFAULT_RESZTVEVO_TIPUS.toString())))
            .andExpect(jsonPath("$.[*].devizanem").value(hasItem(DEFAULT_DEVIZANEM.toString())))
            .andExpect(jsonPath("$.[*].dijazasTipusa").value(hasItem(DEFAULT_DIJAZAS_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].dijOsszege").value(hasItem(sameNumber(DEFAULT_DIJ_OSSZEGE))))
            .andExpect(jsonPath("$.[*].megrendelesDokumentumGeneralta").value(hasItem(DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA.toString())))
            .andExpect(jsonPath("$.[*].ugyfelMegrendelesId").value(hasItem(DEFAULT_UGYFEL_MEGRENDELES_ID.intValue())))
            .andExpect(jsonPath("$.[*].megrendelesSzam").value(hasItem(DEFAULT_MEGRENDELES_SZAM)))
            .andExpect(jsonPath("$.[*].megrendelesStatusz").value(hasItem(DEFAULT_MEGRENDELES_STATUSZ.toString())))
            .andExpect(jsonPath("$.[*].megrendelesForrasa").value(hasItem(DEFAULT_MEGRENDELES_FORRASA.toString())))
            .andExpect(jsonPath("$.[*].peldanyokSzama").value(hasItem(DEFAULT_PELDANYOK_SZAMA)))
            .andExpect(jsonPath("$.[*].szamlazando").value(hasItem(DEFAULT_SZAMLAZANDO)));

        // Check, that the count call also returns 1
        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMegrendelesekShouldNotBeFound(String filter) throws Exception {
        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMegrendelesek() throws Exception {
        // Get the megrendelesek
        restMegrendelesekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMegrendelesek() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the megrendelesek
        Megrendelesek updatedMegrendelesek = megrendelesekRepository.findById(megrendelesek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMegrendelesek are not directly saved in db
        em.detach(updatedMegrendelesek);
        updatedMegrendelesek
            .megrendelesTipusa(UPDATED_MEGRENDELES_TIPUSA)
            .feladatRovidLeirasa(UPDATED_FELADAT_ROVID_LEIRASA)
            .feladatReszletesLeirasa(UPDATED_FELADAT_RESZLETES_LEIRASA)
            .megrendelesKezdete(UPDATED_MEGRENDELES_KEZDETE)
            .megrendelesVege(UPDATED_MEGRENDELES_VEGE)
            .resztvevoKollagaTipusa(UPDATED_RESZTVEVO_KOLLAGA_TIPUSA)
            .resztvevoTipus(UPDATED_RESZTVEVO_TIPUS)
            .devizanem(UPDATED_DEVIZANEM)
            .dijazasTipusa(UPDATED_DIJAZAS_TIPUSA)
            .dijOsszege(UPDATED_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(UPDATED_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(UPDATED_MEGRENDELES_SZAM)
            .megrendelesStatusz(UPDATED_MEGRENDELES_STATUSZ)
            .megrendelesForrasa(UPDATED_MEGRENDELES_FORRASA)
            .peldanyokSzama(UPDATED_PELDANYOK_SZAMA)
            .szamlazando(UPDATED_SZAMLAZANDO);
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(updatedMegrendelesek);

        restMegrendelesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, megrendelesekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isOk());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMegrendelesekToMatchAllProperties(updatedMegrendelesek);
    }

    @Test
    @Transactional
    void putNonExistingMegrendelesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesek.setId(longCount.incrementAndGet());

        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMegrendelesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, megrendelesekDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMegrendelesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesek.setId(longCount.incrementAndGet());

        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMegrendelesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesek.setId(longCount.incrementAndGet());

        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesekMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMegrendelesekWithPatch() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the megrendelesek using partial update
        Megrendelesek partialUpdatedMegrendelesek = new Megrendelesek();
        partialUpdatedMegrendelesek.setId(megrendelesek.getId());

        partialUpdatedMegrendelesek
            .feladatRovidLeirasa(UPDATED_FELADAT_ROVID_LEIRASA)
            .megrendelesKezdete(UPDATED_MEGRENDELES_KEZDETE)
            .megrendelesVege(UPDATED_MEGRENDELES_VEGE)
            .resztvevoKollagaTipusa(UPDATED_RESZTVEVO_KOLLAGA_TIPUSA)
            .dijOsszege(UPDATED_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA);

        restMegrendelesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMegrendelesek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMegrendelesek))
            )
            .andExpect(status().isOk());

        // Validate the Megrendelesek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMegrendelesekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMegrendelesek, megrendelesek),
            getPersistedMegrendelesek(megrendelesek)
        );
    }

    @Test
    @Transactional
    void fullUpdateMegrendelesekWithPatch() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the megrendelesek using partial update
        Megrendelesek partialUpdatedMegrendelesek = new Megrendelesek();
        partialUpdatedMegrendelesek.setId(megrendelesek.getId());

        partialUpdatedMegrendelesek
            .megrendelesTipusa(UPDATED_MEGRENDELES_TIPUSA)
            .feladatRovidLeirasa(UPDATED_FELADAT_ROVID_LEIRASA)
            .feladatReszletesLeirasa(UPDATED_FELADAT_RESZLETES_LEIRASA)
            .megrendelesKezdete(UPDATED_MEGRENDELES_KEZDETE)
            .megrendelesVege(UPDATED_MEGRENDELES_VEGE)
            .resztvevoKollagaTipusa(UPDATED_RESZTVEVO_KOLLAGA_TIPUSA)
            .resztvevoTipus(UPDATED_RESZTVEVO_TIPUS)
            .devizanem(UPDATED_DEVIZANEM)
            .dijazasTipusa(UPDATED_DIJAZAS_TIPUSA)
            .dijOsszege(UPDATED_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(UPDATED_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(UPDATED_MEGRENDELES_SZAM)
            .megrendelesStatusz(UPDATED_MEGRENDELES_STATUSZ)
            .megrendelesForrasa(UPDATED_MEGRENDELES_FORRASA)
            .peldanyokSzama(UPDATED_PELDANYOK_SZAMA)
            .szamlazando(UPDATED_SZAMLAZANDO);

        restMegrendelesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMegrendelesek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMegrendelesek))
            )
            .andExpect(status().isOk());

        // Validate the Megrendelesek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMegrendelesekUpdatableFieldsEquals(partialUpdatedMegrendelesek, getPersistedMegrendelesek(partialUpdatedMegrendelesek));
    }

    @Test
    @Transactional
    void patchNonExistingMegrendelesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesek.setId(longCount.incrementAndGet());

        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMegrendelesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, megrendelesekDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMegrendelesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesek.setId(longCount.incrementAndGet());

        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMegrendelesek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesek.setId(longCount.incrementAndGet());

        // Create the Megrendelesek
        MegrendelesekDTO megrendelesekDTO = megrendelesekMapper.toDto(megrendelesek);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesekMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(megrendelesekDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Megrendelesek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMegrendelesek() throws Exception {
        // Initialize the database
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the megrendelesek
        restMegrendelesekMockMvc
            .perform(delete(ENTITY_API_URL_ID, megrendelesek.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return megrendelesekRepository.count();
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

    protected Megrendelesek getPersistedMegrendelesek(Megrendelesek megrendelesek) {
        return megrendelesekRepository.findById(megrendelesek.getId()).orElseThrow();
    }

    protected void assertPersistedMegrendelesekToMatchAllProperties(Megrendelesek expectedMegrendelesek) {
        assertMegrendelesekAllPropertiesEquals(expectedMegrendelesek, getPersistedMegrendelesek(expectedMegrendelesek));
    }

    protected void assertPersistedMegrendelesekToMatchUpdatableProperties(Megrendelesek expectedMegrendelesek) {
        assertMegrendelesekAllUpdatablePropertiesEquals(expectedMegrendelesek, getPersistedMegrendelesek(expectedMegrendelesek));
    }
}

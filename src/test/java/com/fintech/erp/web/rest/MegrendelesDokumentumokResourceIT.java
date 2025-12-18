package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.MegrendelesDokumentumokAsserts.assertMegrendelesDokumentumokAllPropertiesEquals;
import static com.fintech.erp.domain.MegrendelesDokumentumokAsserts.assertMegrendelesDokumentumokAllUpdatablePropertiesEquals;
import static com.fintech.erp.domain.MegrendelesDokumentumokAsserts.assertMegrendelesDokumentumokUpdatableFieldsEquals;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.MegrendelesDokumentumok;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import com.fintech.erp.repository.MegrendelesDokumentumokRepository;
import com.fintech.erp.service.dto.MegrendelesDokumentumokDTO;
import com.fintech.erp.service.mapper.MegrendelesDokumentumokMapper;
import jakarta.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MegrendelesDokumentumokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MegrendelesDokumentumokResourceIT {

    private static final MegrendelesDokumentumTipus DEFAULT_DOKUMENTUM_TIPUSA = MegrendelesDokumentumTipus.GENERALTA_WORD;
    private static final MegrendelesDokumentumTipus UPDATED_DOKUMENTUM_TIPUSA = MegrendelesDokumentumTipus.GENERALTA_PDF;

    private static final String DEFAULT_DOKUMENTUM = "AAAAAAAAAA";
    private static final String UPDATED_DOKUMENTUM = "BBBBBBBBBB";

    private static final String DEFAULT_DOKUMENTUM_URL = "uploads/megrendelesek/1/AAAAAAAAAA.docx";
    private static final String UPDATED_DOKUMENTUM_URL = "uploads/megrendelesek/1/BBBBBBBBBB.docx";

    private static final String DEFAULT_DOKUMENTUM_AZONOSITO = "001";
    private static final String UPDATED_DOKUMENTUM_AZONOSITO = "002";

    private static final String ENTITY_API_URL = "/api/megrendeles-dokumentumoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MegrendelesDokumentumokRepository megrendelesDokumentumokRepository;

    @Autowired
    private MegrendelesDokumentumokMapper megrendelesDokumentumokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMegrendelesDokumentumokMockMvc;

    private MegrendelesDokumentumok megrendelesDokumentumok;

    private MegrendelesDokumentumok insertedMegrendelesDokumentumok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MegrendelesDokumentumok createEntity() {
        return new MegrendelesDokumentumok()
            .dokumentumTipusa(DEFAULT_DOKUMENTUM_TIPUSA)
            .dokumentum(DEFAULT_DOKUMENTUM)
            .dokumentumUrl(DEFAULT_DOKUMENTUM_URL)
            .dokumentumAzonosito(DEFAULT_DOKUMENTUM_AZONOSITO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MegrendelesDokumentumok createUpdatedEntity() {
        return new MegrendelesDokumentumok()
            .dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA)
            .dokumentum(UPDATED_DOKUMENTUM)
            .dokumentumUrl(UPDATED_DOKUMENTUM_URL)
            .dokumentumAzonosito(UPDATED_DOKUMENTUM_AZONOSITO);
    }

    @BeforeEach
    void initTest() {
        megrendelesDokumentumok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMegrendelesDokumentumok != null) {
            megrendelesDokumentumokRepository.delete(insertedMegrendelesDokumentumok);
            insertedMegrendelesDokumentumok = null;
        }
    }

    @Test
    @Transactional
    void createMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);
        var returnedMegrendelesDokumentumokDTO = om.readValue(
            restMegrendelesDokumentumokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MegrendelesDokumentumokDTO.class
        );

        // Validate the MegrendelesDokumentumok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMegrendelesDokumentumok = megrendelesDokumentumokMapper.toEntity(returnedMegrendelesDokumentumokDTO);
        assertMegrendelesDokumentumokUpdatableFieldsEquals(
            returnedMegrendelesDokumentumok,
            getPersistedMegrendelesDokumentumok(returnedMegrendelesDokumentumok)
        );

        insertedMegrendelesDokumentumok = returnedMegrendelesDokumentumok;
    }

    @Test
    @Transactional
    void createMegrendelesDokumentumokWithExistingId() throws Exception {
        // Create the MegrendelesDokumentumok with an existing ID
        megrendelesDokumentumok.setId(1L);
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMegrendelesDokumentumokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoks() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList
        restMegrendelesDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(megrendelesDokumentumok.getId().intValue())))
            .andExpect(jsonPath("$.[*].dokumentumTipusa").value(hasItem(DEFAULT_DOKUMENTUM_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].dokumentum").value(hasItem(DEFAULT_DOKUMENTUM)))
            .andExpect(jsonPath("$.[*].dokumentumUrl").value(hasItem(DEFAULT_DOKUMENTUM_URL)))
            .andExpect(jsonPath("$.[*].dokumentumAzonosito").value(hasItem(DEFAULT_DOKUMENTUM_AZONOSITO)));
    }

    @Test
    @Transactional
    void getMegrendelesDokumentumok() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get the megrendelesDokumentumok
        restMegrendelesDokumentumokMockMvc
            .perform(get(ENTITY_API_URL_ID, megrendelesDokumentumok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(megrendelesDokumentumok.getId().intValue()))
            .andExpect(jsonPath("$.dokumentumTipusa").value(DEFAULT_DOKUMENTUM_TIPUSA.toString()))
            .andExpect(jsonPath("$.dokumentum").value(DEFAULT_DOKUMENTUM))
            .andExpect(jsonPath("$.dokumentumUrl").value(DEFAULT_DOKUMENTUM_URL))
            .andExpect(jsonPath("$.dokumentumAzonosito").value(DEFAULT_DOKUMENTUM_AZONOSITO));
    }

    @Test
    @Transactional
    void getMegrendelesDokumentumoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        Long id = megrendelesDokumentumok.getId();

        defaultMegrendelesDokumentumokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMegrendelesDokumentumokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMegrendelesDokumentumokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumTipusaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentumTipusa equals to
        defaultMegrendelesDokumentumokFiltering(
            "dokumentumTipusa.equals=" + DEFAULT_DOKUMENTUM_TIPUSA.name(),
            "dokumentumTipusa.equals=" + UPDATED_DOKUMENTUM_TIPUSA.name()
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumTipusaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentumTipusa in
        defaultMegrendelesDokumentumokFiltering(
            "dokumentumTipusa.in=" + DEFAULT_DOKUMENTUM_TIPUSA.name() + "," + UPDATED_DOKUMENTUM_TIPUSA.name(),
            "dokumentumTipusa.in=" + UPDATED_DOKUMENTUM_TIPUSA.name()
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumTipusaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentumTipusa is not null
        defaultMegrendelesDokumentumokFiltering("dokumentumTipusa.specified=true", "dokumentumTipusa.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentum equals to
        defaultMegrendelesDokumentumokFiltering("dokumentum.equals=" + DEFAULT_DOKUMENTUM, "dokumentum.equals=" + UPDATED_DOKUMENTUM);
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentum in
        defaultMegrendelesDokumentumokFiltering(
            "dokumentum.in=" + DEFAULT_DOKUMENTUM + "," + UPDATED_DOKUMENTUM,
            "dokumentum.in=" + UPDATED_DOKUMENTUM
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentum is not null
        defaultMegrendelesDokumentumokFiltering("dokumentum.specified=true", "dokumentum.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentum contains
        defaultMegrendelesDokumentumokFiltering("dokumentum.contains=" + DEFAULT_DOKUMENTUM, "dokumentum.contains=" + UPDATED_DOKUMENTUM);
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        // Get all the megrendelesDokumentumokList where dokumentum does not contain
        defaultMegrendelesDokumentumokFiltering(
            "dokumentum.doesNotContain=" + UPDATED_DOKUMENTUM,
            "dokumentum.doesNotContain=" + DEFAULT_DOKUMENTUM
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumUrlIsEqualToSomething() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumUrl.equals=" + DEFAULT_DOKUMENTUM_URL,
            "dokumentumUrl.equals=" + UPDATED_DOKUMENTUM_URL
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumUrlIsInShouldWork() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumUrl.in=" + DEFAULT_DOKUMENTUM_URL + "," + UPDATED_DOKUMENTUM_URL,
            "dokumentumUrl.in=" + UPDATED_DOKUMENTUM_URL
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumUrlIsNullOrNotNull() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering("dokumentumUrl.specified=true", "dokumentumUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumUrlContainsSomething() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering("dokumentumUrl.contains=uploads/megrendelesek/1", "dokumentumUrl.contains=irrelevant");
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumUrlNotContainsSomething() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumUrl.doesNotContain=" + UPDATED_DOKUMENTUM_URL,
            "dokumentumUrl.doesNotContain=" + DEFAULT_DOKUMENTUM_URL
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumAzonositoIsEqualToSomething() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumAzonosito.equals=" + DEFAULT_DOKUMENTUM_AZONOSITO,
            "dokumentumAzonosito.equals=" + UPDATED_DOKUMENTUM_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumAzonositoIsInShouldWork() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumAzonosito.in=" + DEFAULT_DOKUMENTUM_AZONOSITO + "," + UPDATED_DOKUMENTUM_AZONOSITO,
            "dokumentumAzonosito.in=" + UPDATED_DOKUMENTUM_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumAzonositoIsNullOrNotNull() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering("dokumentumAzonosito.specified=true", "dokumentumAzonosito.specified=false");
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumAzonositoContainsSomething() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumAzonosito.contains=" + DEFAULT_DOKUMENTUM_AZONOSITO,
            "dokumentumAzonosito.contains=999"
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByDokumentumAzonositoNotContainsSomething() throws Exception {
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        defaultMegrendelesDokumentumokFiltering(
            "dokumentumAzonosito.doesNotContain=" + UPDATED_DOKUMENTUM_AZONOSITO,
            "dokumentumAzonosito.doesNotContain=" + DEFAULT_DOKUMENTUM_AZONOSITO
        );
    }

    @Test
    @Transactional
    void getAllMegrendelesDokumentumoksByMegrendelesIsEqualToSomething() throws Exception {
        Megrendelesek megrendeles;
        if (TestUtil.findAll(em, Megrendelesek.class).isEmpty()) {
            megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);
            megrendeles = MegrendelesekResourceIT.createEntity();
        } else {
            megrendeles = TestUtil.findAll(em, Megrendelesek.class).get(0);
        }
        em.persist(megrendeles);
        em.flush();
        megrendelesDokumentumok.setMegrendeles(megrendeles);
        megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);
        Long megrendelesId = megrendeles.getId();
        // Get all the megrendelesDokumentumokList where megrendeles equals to megrendelesId
        defaultMegrendelesDokumentumokShouldBeFound("megrendelesId.equals=" + megrendelesId);

        // Get all the megrendelesDokumentumokList where megrendeles equals to (megrendelesId + 1)
        defaultMegrendelesDokumentumokShouldNotBeFound("megrendelesId.equals=" + (megrendelesId + 1));
    }

    private void defaultMegrendelesDokumentumokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMegrendelesDokumentumokShouldBeFound(shouldBeFound);
        defaultMegrendelesDokumentumokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMegrendelesDokumentumokShouldBeFound(String filter) throws Exception {
        restMegrendelesDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(megrendelesDokumentumok.getId().intValue())))
            .andExpect(jsonPath("$.[*].dokumentumTipusa").value(hasItem(DEFAULT_DOKUMENTUM_TIPUSA.toString())))
            .andExpect(jsonPath("$.[*].dokumentum").value(hasItem(DEFAULT_DOKUMENTUM)))
            .andExpect(jsonPath("$.[*].dokumentumUrl").value(hasItem(DEFAULT_DOKUMENTUM_URL)))
            .andExpect(jsonPath("$.[*].dokumentumAzonosito").value(hasItem(DEFAULT_DOKUMENTUM_AZONOSITO)));

        // Check, that the count call also returns 1
        restMegrendelesDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMegrendelesDokumentumokShouldNotBeFound(String filter) throws Exception {
        restMegrendelesDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMegrendelesDokumentumokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMegrendelesDokumentumok() throws Exception {
        // Get the megrendelesDokumentumok
        restMegrendelesDokumentumokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMegrendelesDokumentumok() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the megrendelesDokumentumok
        MegrendelesDokumentumok updatedMegrendelesDokumentumok = megrendelesDokumentumokRepository
            .findById(megrendelesDokumentumok.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMegrendelesDokumentumok are not directly saved in db
        em.detach(updatedMegrendelesDokumentumok);
        updatedMegrendelesDokumentumok
            .dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA)
            .dokumentum(UPDATED_DOKUMENTUM)
            .dokumentumUrl(UPDATED_DOKUMENTUM_URL)
            .dokumentumAzonosito(UPDATED_DOKUMENTUM_AZONOSITO);
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(updatedMegrendelesDokumentumok);

        restMegrendelesDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, megrendelesDokumentumokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isOk());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMegrendelesDokumentumokToMatchAllProperties(updatedMegrendelesDokumentumok);
    }

    @Test
    @Transactional
    void putNonExistingMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesDokumentumok.setId(longCount.incrementAndGet());

        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMegrendelesDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, megrendelesDokumentumokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesDokumentumok.setId(longCount.incrementAndGet());

        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesDokumentumok.setId(longCount.incrementAndGet());

        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesDokumentumokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMegrendelesDokumentumokWithPatch() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the megrendelesDokumentumok using partial update
        MegrendelesDokumentumok partialUpdatedMegrendelesDokumentumok = new MegrendelesDokumentumok();
        partialUpdatedMegrendelesDokumentumok.setId(megrendelesDokumentumok.getId());

        partialUpdatedMegrendelesDokumentumok.dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA);

        restMegrendelesDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMegrendelesDokumentumok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMegrendelesDokumentumok))
            )
            .andExpect(status().isOk());

        // Validate the MegrendelesDokumentumok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMegrendelesDokumentumokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMegrendelesDokumentumok, megrendelesDokumentumok),
            getPersistedMegrendelesDokumentumok(megrendelesDokumentumok)
        );
    }

    @Test
    @Transactional
    void fullUpdateMegrendelesDokumentumokWithPatch() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the megrendelesDokumentumok using partial update
        MegrendelesDokumentumok partialUpdatedMegrendelesDokumentumok = new MegrendelesDokumentumok();
        partialUpdatedMegrendelesDokumentumok.setId(megrendelesDokumentumok.getId());

        partialUpdatedMegrendelesDokumentumok
            .dokumentumTipusa(UPDATED_DOKUMENTUM_TIPUSA)
            .dokumentum(UPDATED_DOKUMENTUM)
            .dokumentumUrl(UPDATED_DOKUMENTUM_URL)
            .dokumentumAzonosito(UPDATED_DOKUMENTUM_AZONOSITO);

        restMegrendelesDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMegrendelesDokumentumok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMegrendelesDokumentumok))
            )
            .andExpect(status().isOk());

        // Validate the MegrendelesDokumentumok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMegrendelesDokumentumokUpdatableFieldsEquals(
            partialUpdatedMegrendelesDokumentumok,
            getPersistedMegrendelesDokumentumok(partialUpdatedMegrendelesDokumentumok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesDokumentumok.setId(longCount.incrementAndGet());

        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMegrendelesDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, megrendelesDokumentumokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesDokumentumok.setId(longCount.incrementAndGet());

        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMegrendelesDokumentumok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        megrendelesDokumentumok.setId(longCount.incrementAndGet());

        // Create the MegrendelesDokumentumok
        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = megrendelesDokumentumokMapper.toDto(megrendelesDokumentumok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMegrendelesDokumentumokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(megrendelesDokumentumokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MegrendelesDokumentumok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMegrendelesDokumentumok() throws Exception {
        // Initialize the database
        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.saveAndFlush(megrendelesDokumentumok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the megrendelesDokumentumok
        restMegrendelesDokumentumokMockMvc
            .perform(delete(ENTITY_API_URL_ID, megrendelesDokumentumok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    void uploadDokumentumCreatesRecordAndFile() throws Exception {
        Megrendelesek megrendeles = MegrendelesekResourceIT.createEntity();
        em.persist(megrendeles);
        em.flush();

        MockMultipartFile multipartFile = new MockMultipartFile(
            "file",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "sample".getBytes(StandardCharsets.UTF_8)
        );

        long databaseSizeBefore = getRepositoryCount();

        String response = restMegrendelesDokumentumokMockMvc
            .perform(
                multipart(ENTITY_API_URL + "/upload")
                    .file(multipartFile)
                    .param("dokumentumTipusa", DEFAULT_DOKUMENTUM_TIPUSA.name())
                    .param("megrendelesId", megrendeles.getId().toString())
                    .with(csrf())
            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        MegrendelesDokumentumokDTO resultDto = om.readValue(response, MegrendelesDokumentumokDTO.class);

        assertIncrementedRepositoryCount(databaseSizeBefore);

        assertThat(resultDto.getDokumentumTipusa()).isEqualTo(DEFAULT_DOKUMENTUM_TIPUSA);
        assertThat(resultDto.getDokumentumAzonosito()).isNotBlank();
        assertThat(resultDto.getDokumentumUrl()).isEqualTo(
            "uploads/megrendelesek/" + megrendeles.getId() + "/" + resultDto.getDokumentum()
        );

        Path uploadDir = Path.of("uploads", "megrendelesek", megrendeles.getId().toString());
        Path storedPath = uploadDir.resolve(resultDto.getDokumentum()).normalize();
        assertThat(Files.exists(storedPath)).isTrue();

        insertedMegrendelesDokumentumok = megrendelesDokumentumokRepository.findById(resultDto.getId()).orElse(null);

        Files.deleteIfExists(storedPath);
        if (Files.exists(uploadDir)) {
            try (Stream<Path> entries = Files.list(uploadDir)) {
                if (entries.findAny().isEmpty()) {
                    Files.delete(uploadDir);
                }
            }
        }
    }

    protected long getRepositoryCount() {
        return megrendelesDokumentumokRepository.count();
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

    protected MegrendelesDokumentumok getPersistedMegrendelesDokumentumok(MegrendelesDokumentumok megrendelesDokumentumok) {
        return megrendelesDokumentumokRepository.findById(megrendelesDokumentumok.getId()).orElseThrow();
    }

    protected void assertPersistedMegrendelesDokumentumokToMatchAllProperties(MegrendelesDokumentumok expectedMegrendelesDokumentumok) {
        assertMegrendelesDokumentumokAllPropertiesEquals(
            expectedMegrendelesDokumentumok,
            getPersistedMegrendelesDokumentumok(expectedMegrendelesDokumentumok)
        );
    }

    protected void assertPersistedMegrendelesDokumentumokToMatchUpdatableProperties(
        MegrendelesDokumentumok expectedMegrendelesDokumentumok
    ) {
        assertMegrendelesDokumentumokAllUpdatablePropertiesEquals(
            expectedMegrendelesDokumentumok,
            getPersistedMegrendelesDokumentumok(expectedMegrendelesDokumentumok)
        );
    }
}

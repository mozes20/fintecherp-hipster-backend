package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.BankszamlaszamokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Bankszamlaszamok;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.repository.BankszamlaszamokRepository;
import com.fintech.erp.service.dto.BankszamlaszamokDTO;
import com.fintech.erp.service.mapper.BankszamlaszamokMapper;
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
 * Integration tests for the {@link BankszamlaszamokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankszamlaszamokResourceIT {

    private static final String DEFAULT_BANKSZAMLA_DEVIZANEM = "AAAAAAAAAA";
    private static final String UPDATED_BANKSZAMLA_DEVIZANEM = "BBBBBBBBBB";

    private static final String DEFAULT_BANKSZAMLA_GIRO = "AAAAAAAAAA";
    private static final String UPDATED_BANKSZAMLA_GIRO = "BBBBBBBBBB";

    private static final String DEFAULT_BANKSZAMLA_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_BANKSZAMLA_IBAN = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bankszamlaszamoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankszamlaszamokRepository bankszamlaszamokRepository;

    @Autowired
    private BankszamlaszamokMapper bankszamlaszamokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankszamlaszamokMockMvc;

    private Bankszamlaszamok bankszamlaszamok;

    private Bankszamlaszamok insertedBankszamlaszamok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankszamlaszamok createEntity() {
        return new Bankszamlaszamok()
            .bankszamlaDevizanem(DEFAULT_BANKSZAMLA_DEVIZANEM)
            .bankszamlaGIRO(DEFAULT_BANKSZAMLA_GIRO)
            .bankszamlaIBAN(DEFAULT_BANKSZAMLA_IBAN)
            .statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankszamlaszamok createUpdatedEntity() {
        return new Bankszamlaszamok()
            .bankszamlaDevizanem(UPDATED_BANKSZAMLA_DEVIZANEM)
            .bankszamlaGIRO(UPDATED_BANKSZAMLA_GIRO)
            .bankszamlaIBAN(UPDATED_BANKSZAMLA_IBAN)
            .statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        bankszamlaszamok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBankszamlaszamok != null) {
            bankszamlaszamokRepository.delete(insertedBankszamlaszamok);
            insertedBankszamlaszamok = null;
        }
    }

    @Test
    @Transactional
    void createBankszamlaszamok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);
        var returnedBankszamlaszamokDTO = om.readValue(
            restBankszamlaszamokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(bankszamlaszamokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BankszamlaszamokDTO.class
        );

        // Validate the Bankszamlaszamok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBankszamlaszamok = bankszamlaszamokMapper.toEntity(returnedBankszamlaszamokDTO);
        assertBankszamlaszamokUpdatableFieldsEquals(returnedBankszamlaszamok, getPersistedBankszamlaszamok(returnedBankszamlaszamok));

        insertedBankszamlaszamok = returnedBankszamlaszamok;
    }

    @Test
    @Transactional
    void createBankszamlaszamokWithExistingId() throws Exception {
        // Create the Bankszamlaszamok with an existing ID
        bankszamlaszamok.setId(1L);
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankszamlaszamokMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoks() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList
        restBankszamlaszamokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankszamlaszamok.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankszamlaDevizanem").value(hasItem(DEFAULT_BANKSZAMLA_DEVIZANEM)))
            .andExpect(jsonPath("$.[*].bankszamlaGIRO").value(hasItem(DEFAULT_BANKSZAMLA_GIRO)))
            .andExpect(jsonPath("$.[*].bankszamlaIBAN").value(hasItem(DEFAULT_BANKSZAMLA_IBAN)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getBankszamlaszamok() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get the bankszamlaszamok
        restBankszamlaszamokMockMvc
            .perform(get(ENTITY_API_URL_ID, bankszamlaszamok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankszamlaszamok.getId().intValue()))
            .andExpect(jsonPath("$.bankszamlaDevizanem").value(DEFAULT_BANKSZAMLA_DEVIZANEM))
            .andExpect(jsonPath("$.bankszamlaGIRO").value(DEFAULT_BANKSZAMLA_GIRO))
            .andExpect(jsonPath("$.bankszamlaIBAN").value(DEFAULT_BANKSZAMLA_IBAN))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getBankszamlaszamoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        Long id = bankszamlaszamok.getId();

        defaultBankszamlaszamokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBankszamlaszamokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBankszamlaszamokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaDevizanemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaDevizanem equals to
        defaultBankszamlaszamokFiltering(
            "bankszamlaDevizanem.equals=" + DEFAULT_BANKSZAMLA_DEVIZANEM,
            "bankszamlaDevizanem.equals=" + UPDATED_BANKSZAMLA_DEVIZANEM
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaDevizanemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaDevizanem in
        defaultBankszamlaszamokFiltering(
            "bankszamlaDevizanem.in=" + DEFAULT_BANKSZAMLA_DEVIZANEM + "," + UPDATED_BANKSZAMLA_DEVIZANEM,
            "bankszamlaDevizanem.in=" + UPDATED_BANKSZAMLA_DEVIZANEM
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaDevizanemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaDevizanem is not null
        defaultBankszamlaszamokFiltering("bankszamlaDevizanem.specified=true", "bankszamlaDevizanem.specified=false");
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaDevizanemContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaDevizanem contains
        defaultBankszamlaszamokFiltering(
            "bankszamlaDevizanem.contains=" + DEFAULT_BANKSZAMLA_DEVIZANEM,
            "bankszamlaDevizanem.contains=" + UPDATED_BANKSZAMLA_DEVIZANEM
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaDevizanemNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaDevizanem does not contain
        defaultBankszamlaszamokFiltering(
            "bankszamlaDevizanem.doesNotContain=" + UPDATED_BANKSZAMLA_DEVIZANEM,
            "bankszamlaDevizanem.doesNotContain=" + DEFAULT_BANKSZAMLA_DEVIZANEM
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaGIROIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaGIRO equals to
        defaultBankszamlaszamokFiltering(
            "bankszamlaGIRO.equals=" + DEFAULT_BANKSZAMLA_GIRO,
            "bankszamlaGIRO.equals=" + UPDATED_BANKSZAMLA_GIRO
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaGIROIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaGIRO in
        defaultBankszamlaszamokFiltering(
            "bankszamlaGIRO.in=" + DEFAULT_BANKSZAMLA_GIRO + "," + UPDATED_BANKSZAMLA_GIRO,
            "bankszamlaGIRO.in=" + UPDATED_BANKSZAMLA_GIRO
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaGIROIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaGIRO is not null
        defaultBankszamlaszamokFiltering("bankszamlaGIRO.specified=true", "bankszamlaGIRO.specified=false");
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaGIROContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaGIRO contains
        defaultBankszamlaszamokFiltering(
            "bankszamlaGIRO.contains=" + DEFAULT_BANKSZAMLA_GIRO,
            "bankszamlaGIRO.contains=" + UPDATED_BANKSZAMLA_GIRO
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaGIRONotContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaGIRO does not contain
        defaultBankszamlaszamokFiltering(
            "bankszamlaGIRO.doesNotContain=" + UPDATED_BANKSZAMLA_GIRO,
            "bankszamlaGIRO.doesNotContain=" + DEFAULT_BANKSZAMLA_GIRO
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaIBANIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaIBAN equals to
        defaultBankszamlaszamokFiltering(
            "bankszamlaIBAN.equals=" + DEFAULT_BANKSZAMLA_IBAN,
            "bankszamlaIBAN.equals=" + UPDATED_BANKSZAMLA_IBAN
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaIBANIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaIBAN in
        defaultBankszamlaszamokFiltering(
            "bankszamlaIBAN.in=" + DEFAULT_BANKSZAMLA_IBAN + "," + UPDATED_BANKSZAMLA_IBAN,
            "bankszamlaIBAN.in=" + UPDATED_BANKSZAMLA_IBAN
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaIBANIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaIBAN is not null
        defaultBankszamlaszamokFiltering("bankszamlaIBAN.specified=true", "bankszamlaIBAN.specified=false");
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaIBANContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaIBAN contains
        defaultBankszamlaszamokFiltering(
            "bankszamlaIBAN.contains=" + DEFAULT_BANKSZAMLA_IBAN,
            "bankszamlaIBAN.contains=" + UPDATED_BANKSZAMLA_IBAN
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByBankszamlaIBANNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where bankszamlaIBAN does not contain
        defaultBankszamlaszamokFiltering(
            "bankszamlaIBAN.doesNotContain=" + UPDATED_BANKSZAMLA_IBAN,
            "bankszamlaIBAN.doesNotContain=" + DEFAULT_BANKSZAMLA_IBAN
        );
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where statusz equals to
        defaultBankszamlaszamokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where statusz in
        defaultBankszamlaszamokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where statusz is not null
        defaultBankszamlaszamokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where statusz contains
        defaultBankszamlaszamokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        // Get all the bankszamlaszamokList where statusz does not contain
        defaultBankszamlaszamokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllBankszamlaszamoksByCegIsEqualToSomething() throws Exception {
        CegAlapadatok ceg;
        if (TestUtil.findAll(em, CegAlapadatok.class).isEmpty()) {
            bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);
            ceg = CegAlapadatokResourceIT.createEntity();
        } else {
            ceg = TestUtil.findAll(em, CegAlapadatok.class).get(0);
        }
        em.persist(ceg);
        em.flush();
        bankszamlaszamok.setCeg(ceg);
        bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);
        Long cegId = ceg.getId();
        // Get all the bankszamlaszamokList where ceg equals to cegId
        defaultBankszamlaszamokShouldBeFound("cegId.equals=" + cegId);

        // Get all the bankszamlaszamokList where ceg equals to (cegId + 1)
        defaultBankszamlaszamokShouldNotBeFound("cegId.equals=" + (cegId + 1));
    }

    private void defaultBankszamlaszamokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBankszamlaszamokShouldBeFound(shouldBeFound);
        defaultBankszamlaszamokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankszamlaszamokShouldBeFound(String filter) throws Exception {
        restBankszamlaszamokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankszamlaszamok.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankszamlaDevizanem").value(hasItem(DEFAULT_BANKSZAMLA_DEVIZANEM)))
            .andExpect(jsonPath("$.[*].bankszamlaGIRO").value(hasItem(DEFAULT_BANKSZAMLA_GIRO)))
            .andExpect(jsonPath("$.[*].bankszamlaIBAN").value(hasItem(DEFAULT_BANKSZAMLA_IBAN)))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restBankszamlaszamokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankszamlaszamokShouldNotBeFound(String filter) throws Exception {
        restBankszamlaszamokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankszamlaszamokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBankszamlaszamok() throws Exception {
        // Get the bankszamlaszamok
        restBankszamlaszamokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankszamlaszamok() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankszamlaszamok
        Bankszamlaszamok updatedBankszamlaszamok = bankszamlaszamokRepository.findById(bankszamlaszamok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankszamlaszamok are not directly saved in db
        em.detach(updatedBankszamlaszamok);
        updatedBankszamlaszamok
            .bankszamlaDevizanem(UPDATED_BANKSZAMLA_DEVIZANEM)
            .bankszamlaGIRO(UPDATED_BANKSZAMLA_GIRO)
            .bankszamlaIBAN(UPDATED_BANKSZAMLA_IBAN)
            .statusz(UPDATED_STATUSZ);
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(updatedBankszamlaszamok);

        restBankszamlaszamokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankszamlaszamokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankszamlaszamokToMatchAllProperties(updatedBankszamlaszamok);
    }

    @Test
    @Transactional
    void putNonExistingBankszamlaszamok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankszamlaszamok.setId(longCount.incrementAndGet());

        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankszamlaszamokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankszamlaszamokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankszamlaszamok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankszamlaszamok.setId(longCount.incrementAndGet());

        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankszamlaszamokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankszamlaszamok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankszamlaszamok.setId(longCount.incrementAndGet());

        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankszamlaszamokMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankszamlaszamokWithPatch() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankszamlaszamok using partial update
        Bankszamlaszamok partialUpdatedBankszamlaszamok = new Bankszamlaszamok();
        partialUpdatedBankszamlaszamok.setId(bankszamlaszamok.getId());

        restBankszamlaszamokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankszamlaszamok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankszamlaszamok))
            )
            .andExpect(status().isOk());

        // Validate the Bankszamlaszamok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankszamlaszamokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankszamlaszamok, bankszamlaszamok),
            getPersistedBankszamlaszamok(bankszamlaszamok)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankszamlaszamokWithPatch() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankszamlaszamok using partial update
        Bankszamlaszamok partialUpdatedBankszamlaszamok = new Bankszamlaszamok();
        partialUpdatedBankszamlaszamok.setId(bankszamlaszamok.getId());

        partialUpdatedBankszamlaszamok
            .bankszamlaDevizanem(UPDATED_BANKSZAMLA_DEVIZANEM)
            .bankszamlaGIRO(UPDATED_BANKSZAMLA_GIRO)
            .bankszamlaIBAN(UPDATED_BANKSZAMLA_IBAN)
            .statusz(UPDATED_STATUSZ);

        restBankszamlaszamokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankszamlaszamok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankszamlaszamok))
            )
            .andExpect(status().isOk());

        // Validate the Bankszamlaszamok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankszamlaszamokUpdatableFieldsEquals(
            partialUpdatedBankszamlaszamok,
            getPersistedBankszamlaszamok(partialUpdatedBankszamlaszamok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBankszamlaszamok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankszamlaszamok.setId(longCount.incrementAndGet());

        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankszamlaszamokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankszamlaszamokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankszamlaszamok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankszamlaszamok.setId(longCount.incrementAndGet());

        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankszamlaszamokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankszamlaszamok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankszamlaszamok.setId(longCount.incrementAndGet());

        // Create the Bankszamlaszamok
        BankszamlaszamokDTO bankszamlaszamokDTO = bankszamlaszamokMapper.toDto(bankszamlaszamok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankszamlaszamokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankszamlaszamokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankszamlaszamok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankszamlaszamok() throws Exception {
        // Initialize the database
        insertedBankszamlaszamok = bankszamlaszamokRepository.saveAndFlush(bankszamlaszamok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankszamlaszamok
        restBankszamlaszamokMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankszamlaszamok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankszamlaszamokRepository.count();
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

    protected Bankszamlaszamok getPersistedBankszamlaszamok(Bankszamlaszamok bankszamlaszamok) {
        return bankszamlaszamokRepository.findById(bankszamlaszamok.getId()).orElseThrow();
    }

    protected void assertPersistedBankszamlaszamokToMatchAllProperties(Bankszamlaszamok expectedBankszamlaszamok) {
        assertBankszamlaszamokAllPropertiesEquals(expectedBankszamlaszamok, getPersistedBankszamlaszamok(expectedBankszamlaszamok));
    }

    protected void assertPersistedBankszamlaszamokToMatchUpdatableProperties(Bankszamlaszamok expectedBankszamlaszamok) {
        assertBankszamlaszamokAllUpdatablePropertiesEquals(
            expectedBankszamlaszamok,
            getPersistedBankszamlaszamok(expectedBankszamlaszamok)
        );
    }
}

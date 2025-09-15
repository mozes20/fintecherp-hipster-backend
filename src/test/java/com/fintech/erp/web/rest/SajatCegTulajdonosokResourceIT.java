package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.SajatCegTulajdonosokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.fintech.erp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegTulajdonosok;
import com.fintech.erp.repository.SajatCegTulajdonosokRepository;
import com.fintech.erp.service.dto.SajatCegTulajdonosokDTO;
import com.fintech.erp.service.mapper.SajatCegTulajdonosokMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SajatCegTulajdonosokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SajatCegTulajdonosokResourceIT {

    private static final BigDecimal DEFAULT_BRUTTO_OSZTALEK = new BigDecimal(1);
    private static final BigDecimal UPDATED_BRUTTO_OSZTALEK = new BigDecimal(2);
    private static final BigDecimal SMALLER_BRUTTO_OSZTALEK = new BigDecimal(1 - 1);

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sajat-ceg-tulajdonosoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SajatCegTulajdonosokRepository sajatCegTulajdonosokRepository;

    @Autowired
    private SajatCegTulajdonosokMapper sajatCegTulajdonosokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSajatCegTulajdonosokMockMvc;

    private SajatCegTulajdonosok sajatCegTulajdonosok;

    private SajatCegTulajdonosok insertedSajatCegTulajdonosok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SajatCegTulajdonosok createEntity() {
        return new SajatCegTulajdonosok().bruttoOsztalek(DEFAULT_BRUTTO_OSZTALEK).statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SajatCegTulajdonosok createUpdatedEntity() {
        return new SajatCegTulajdonosok().bruttoOsztalek(UPDATED_BRUTTO_OSZTALEK).statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        sajatCegTulajdonosok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSajatCegTulajdonosok != null) {
            sajatCegTulajdonosokRepository.delete(insertedSajatCegTulajdonosok);
            insertedSajatCegTulajdonosok = null;
        }
    }

    @Test
    @Transactional
    void createSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);
        var returnedSajatCegTulajdonosokDTO = om.readValue(
            restSajatCegTulajdonosokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SajatCegTulajdonosokDTO.class
        );

        // Validate the SajatCegTulajdonosok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSajatCegTulajdonosok = sajatCegTulajdonosokMapper.toEntity(returnedSajatCegTulajdonosokDTO);
        assertSajatCegTulajdonosokUpdatableFieldsEquals(
            returnedSajatCegTulajdonosok,
            getPersistedSajatCegTulajdonosok(returnedSajatCegTulajdonosok)
        );

        insertedSajatCegTulajdonosok = returnedSajatCegTulajdonosok;
    }

    @Test
    @Transactional
    void createSajatCegTulajdonosokWithExistingId() throws Exception {
        // Create the SajatCegTulajdonosok with an existing ID
        sajatCegTulajdonosok.setId(1L);
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSajatCegTulajdonosokMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoks() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList
        restSajatCegTulajdonosokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sajatCegTulajdonosok.getId().intValue())))
            .andExpect(jsonPath("$.[*].bruttoOsztalek").value(hasItem(sameNumber(DEFAULT_BRUTTO_OSZTALEK))))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getSajatCegTulajdonosok() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get the sajatCegTulajdonosok
        restSajatCegTulajdonosokMockMvc
            .perform(get(ENTITY_API_URL_ID, sajatCegTulajdonosok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sajatCegTulajdonosok.getId().intValue()))
            .andExpect(jsonPath("$.bruttoOsztalek").value(sameNumber(DEFAULT_BRUTTO_OSZTALEK)))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getSajatCegTulajdonosoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        Long id = sajatCegTulajdonosok.getId();

        defaultSajatCegTulajdonosokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSajatCegTulajdonosokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSajatCegTulajdonosokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek equals to
        defaultSajatCegTulajdonosokFiltering(
            "bruttoOsztalek.equals=" + DEFAULT_BRUTTO_OSZTALEK,
            "bruttoOsztalek.equals=" + UPDATED_BRUTTO_OSZTALEK
        );
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek in
        defaultSajatCegTulajdonosokFiltering(
            "bruttoOsztalek.in=" + DEFAULT_BRUTTO_OSZTALEK + "," + UPDATED_BRUTTO_OSZTALEK,
            "bruttoOsztalek.in=" + UPDATED_BRUTTO_OSZTALEK
        );
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek is not null
        defaultSajatCegTulajdonosokFiltering("bruttoOsztalek.specified=true", "bruttoOsztalek.specified=false");
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek is greater than or equal to
        defaultSajatCegTulajdonosokFiltering(
            "bruttoOsztalek.greaterThanOrEqual=" + DEFAULT_BRUTTO_OSZTALEK,
            "bruttoOsztalek.greaterThanOrEqual=" + UPDATED_BRUTTO_OSZTALEK
        );
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek is less than or equal to
        defaultSajatCegTulajdonosokFiltering(
            "bruttoOsztalek.lessThanOrEqual=" + DEFAULT_BRUTTO_OSZTALEK,
            "bruttoOsztalek.lessThanOrEqual=" + SMALLER_BRUTTO_OSZTALEK
        );
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek is less than
        defaultSajatCegTulajdonosokFiltering(
            "bruttoOsztalek.lessThan=" + UPDATED_BRUTTO_OSZTALEK,
            "bruttoOsztalek.lessThan=" + DEFAULT_BRUTTO_OSZTALEK
        );
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByBruttoOsztalekIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where bruttoOsztalek is greater than
        defaultSajatCegTulajdonosokFiltering(
            "bruttoOsztalek.greaterThan=" + SMALLER_BRUTTO_OSZTALEK,
            "bruttoOsztalek.greaterThan=" + DEFAULT_BRUTTO_OSZTALEK
        );
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where statusz equals to
        defaultSajatCegTulajdonosokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where statusz in
        defaultSajatCegTulajdonosokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where statusz is not null
        defaultSajatCegTulajdonosokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where statusz contains
        defaultSajatCegTulajdonosokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        // Get all the sajatCegTulajdonosokList where statusz does not contain
        defaultSajatCegTulajdonosokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksBySajatCegIsEqualToSomething() throws Exception {
        SajatCegAlapadatok sajatCeg;
        if (TestUtil.findAll(em, SajatCegAlapadatok.class).isEmpty()) {
            sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);
            sajatCeg = SajatCegAlapadatokResourceIT.createEntity();
        } else {
            sajatCeg = TestUtil.findAll(em, SajatCegAlapadatok.class).get(0);
        }
        em.persist(sajatCeg);
        em.flush();
        sajatCegTulajdonosok.setSajatCeg(sajatCeg);
        sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);
        Long sajatCegId = sajatCeg.getId();
        // Get all the sajatCegTulajdonosokList where sajatCeg equals to sajatCegId
        defaultSajatCegTulajdonosokShouldBeFound("sajatCegId.equals=" + sajatCegId);

        // Get all the sajatCegTulajdonosokList where sajatCeg equals to (sajatCegId + 1)
        defaultSajatCegTulajdonosokShouldNotBeFound("sajatCegId.equals=" + (sajatCegId + 1));
    }

    @Test
    @Transactional
    void getAllSajatCegTulajdonosoksByMaganszemelyIsEqualToSomething() throws Exception {
        Maganszemelyek maganszemely;
        if (TestUtil.findAll(em, Maganszemelyek.class).isEmpty()) {
            sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);
            maganszemely = MaganszemelyekResourceIT.createEntity();
        } else {
            maganszemely = TestUtil.findAll(em, Maganszemelyek.class).get(0);
        }
        em.persist(maganszemely);
        em.flush();
        sajatCegTulajdonosok.setMaganszemely(maganszemely);
        sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);
        Long maganszemelyId = maganszemely.getId();
        // Get all the sajatCegTulajdonosokList where maganszemely equals to maganszemelyId
        defaultSajatCegTulajdonosokShouldBeFound("maganszemelyId.equals=" + maganszemelyId);

        // Get all the sajatCegTulajdonosokList where maganszemely equals to (maganszemelyId + 1)
        defaultSajatCegTulajdonosokShouldNotBeFound("maganszemelyId.equals=" + (maganszemelyId + 1));
    }

    private void defaultSajatCegTulajdonosokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSajatCegTulajdonosokShouldBeFound(shouldBeFound);
        defaultSajatCegTulajdonosokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSajatCegTulajdonosokShouldBeFound(String filter) throws Exception {
        restSajatCegTulajdonosokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sajatCegTulajdonosok.getId().intValue())))
            .andExpect(jsonPath("$.[*].bruttoOsztalek").value(hasItem(sameNumber(DEFAULT_BRUTTO_OSZTALEK))))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restSajatCegTulajdonosokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSajatCegTulajdonosokShouldNotBeFound(String filter) throws Exception {
        restSajatCegTulajdonosokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSajatCegTulajdonosokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSajatCegTulajdonosok() throws Exception {
        // Get the sajatCegTulajdonosok
        restSajatCegTulajdonosokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSajatCegTulajdonosok() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegTulajdonosok
        SajatCegTulajdonosok updatedSajatCegTulajdonosok = sajatCegTulajdonosokRepository
            .findById(sajatCegTulajdonosok.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSajatCegTulajdonosok are not directly saved in db
        em.detach(updatedSajatCegTulajdonosok);
        updatedSajatCegTulajdonosok.bruttoOsztalek(UPDATED_BRUTTO_OSZTALEK).statusz(UPDATED_STATUSZ);
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(updatedSajatCegTulajdonosok);

        restSajatCegTulajdonosokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sajatCegTulajdonosokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSajatCegTulajdonosokToMatchAllProperties(updatedSajatCegTulajdonosok);
    }

    @Test
    @Transactional
    void putNonExistingSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegTulajdonosok.setId(longCount.incrementAndGet());

        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSajatCegTulajdonosokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sajatCegTulajdonosokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegTulajdonosok.setId(longCount.incrementAndGet());

        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegTulajdonosokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegTulajdonosok.setId(longCount.incrementAndGet());

        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegTulajdonosokMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSajatCegTulajdonosokWithPatch() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegTulajdonosok using partial update
        SajatCegTulajdonosok partialUpdatedSajatCegTulajdonosok = new SajatCegTulajdonosok();
        partialUpdatedSajatCegTulajdonosok.setId(sajatCegTulajdonosok.getId());

        restSajatCegTulajdonosokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSajatCegTulajdonosok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSajatCegTulajdonosok))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegTulajdonosok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSajatCegTulajdonosokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSajatCegTulajdonosok, sajatCegTulajdonosok),
            getPersistedSajatCegTulajdonosok(sajatCegTulajdonosok)
        );
    }

    @Test
    @Transactional
    void fullUpdateSajatCegTulajdonosokWithPatch() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sajatCegTulajdonosok using partial update
        SajatCegTulajdonosok partialUpdatedSajatCegTulajdonosok = new SajatCegTulajdonosok();
        partialUpdatedSajatCegTulajdonosok.setId(sajatCegTulajdonosok.getId());

        partialUpdatedSajatCegTulajdonosok.bruttoOsztalek(UPDATED_BRUTTO_OSZTALEK).statusz(UPDATED_STATUSZ);

        restSajatCegTulajdonosokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSajatCegTulajdonosok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSajatCegTulajdonosok))
            )
            .andExpect(status().isOk());

        // Validate the SajatCegTulajdonosok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSajatCegTulajdonosokUpdatableFieldsEquals(
            partialUpdatedSajatCegTulajdonosok,
            getPersistedSajatCegTulajdonosok(partialUpdatedSajatCegTulajdonosok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegTulajdonosok.setId(longCount.incrementAndGet());

        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSajatCegTulajdonosokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sajatCegTulajdonosokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegTulajdonosok.setId(longCount.incrementAndGet());

        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegTulajdonosokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSajatCegTulajdonosok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sajatCegTulajdonosok.setId(longCount.incrementAndGet());

        // Create the SajatCegTulajdonosok
        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = sajatCegTulajdonosokMapper.toDto(sajatCegTulajdonosok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSajatCegTulajdonosokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sajatCegTulajdonosokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SajatCegTulajdonosok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSajatCegTulajdonosok() throws Exception {
        // Initialize the database
        insertedSajatCegTulajdonosok = sajatCegTulajdonosokRepository.saveAndFlush(sajatCegTulajdonosok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sajatCegTulajdonosok
        restSajatCegTulajdonosokMockMvc
            .perform(delete(ENTITY_API_URL_ID, sajatCegTulajdonosok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sajatCegTulajdonosokRepository.count();
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

    protected SajatCegTulajdonosok getPersistedSajatCegTulajdonosok(SajatCegTulajdonosok sajatCegTulajdonosok) {
        return sajatCegTulajdonosokRepository.findById(sajatCegTulajdonosok.getId()).orElseThrow();
    }

    protected void assertPersistedSajatCegTulajdonosokToMatchAllProperties(SajatCegTulajdonosok expectedSajatCegTulajdonosok) {
        assertSajatCegTulajdonosokAllPropertiesEquals(
            expectedSajatCegTulajdonosok,
            getPersistedSajatCegTulajdonosok(expectedSajatCegTulajdonosok)
        );
    }

    protected void assertPersistedSajatCegTulajdonosokToMatchUpdatableProperties(SajatCegTulajdonosok expectedSajatCegTulajdonosok) {
        assertSajatCegTulajdonosokAllUpdatablePropertiesEquals(
            expectedSajatCegTulajdonosok,
            getPersistedSajatCegTulajdonosok(expectedSajatCegTulajdonosok)
        );
    }
}

package com.fintech.erp.web.rest;

import static com.fintech.erp.domain.PartnerCegAdatokAsserts.*;
import static com.fintech.erp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.erp.IntegrationTest;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.PartnerCegAdatok;
import com.fintech.erp.repository.PartnerCegAdatokRepository;
import com.fintech.erp.service.dto.PartnerCegAdatokDTO;
import com.fintech.erp.service.mapper.PartnerCegAdatokMapper;
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
 * Integration tests for the {@link PartnerCegAdatokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartnerCegAdatokResourceIT {

    private static final String DEFAULT_STATUSZ = "AAAAAAAAAA";
    private static final String UPDATED_STATUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partner-ceg-adatoks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartnerCegAdatokRepository partnerCegAdatokRepository;

    @Autowired
    private PartnerCegAdatokMapper partnerCegAdatokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnerCegAdatokMockMvc;

    private PartnerCegAdatok partnerCegAdatok;

    private PartnerCegAdatok insertedPartnerCegAdatok;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerCegAdatok createEntity() {
        return new PartnerCegAdatok().statusz(DEFAULT_STATUSZ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerCegAdatok createUpdatedEntity() {
        return new PartnerCegAdatok().statusz(UPDATED_STATUSZ);
    }

    @BeforeEach
    void initTest() {
        partnerCegAdatok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPartnerCegAdatok != null) {
            partnerCegAdatokRepository.delete(insertedPartnerCegAdatok);
            insertedPartnerCegAdatok = null;
        }
    }

    @Test
    @Transactional
    void createPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);
        var returnedPartnerCegAdatokDTO = om.readValue(
            restPartnerCegAdatokMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(partnerCegAdatokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartnerCegAdatokDTO.class
        );

        // Validate the PartnerCegAdatok in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartnerCegAdatok = partnerCegAdatokMapper.toEntity(returnedPartnerCegAdatokDTO);
        assertPartnerCegAdatokUpdatableFieldsEquals(returnedPartnerCegAdatok, getPersistedPartnerCegAdatok(returnedPartnerCegAdatok));

        insertedPartnerCegAdatok = returnedPartnerCegAdatok;
    }

    @Test
    @Transactional
    void createPartnerCegAdatokWithExistingId() throws Exception {
        // Create the PartnerCegAdatok with an existing ID
        partnerCegAdatok.setId(1L);
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerCegAdatokMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoks() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get all the partnerCegAdatokList
        restPartnerCegAdatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerCegAdatok.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));
    }

    @Test
    @Transactional
    void getPartnerCegAdatok() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get the partnerCegAdatok
        restPartnerCegAdatokMockMvc
            .perform(get(ENTITY_API_URL_ID, partnerCegAdatok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partnerCegAdatok.getId().intValue()))
            .andExpect(jsonPath("$.statusz").value(DEFAULT_STATUSZ));
    }

    @Test
    @Transactional
    void getPartnerCegAdatoksByIdFiltering() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        Long id = partnerCegAdatok.getId();

        defaultPartnerCegAdatokFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPartnerCegAdatokFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPartnerCegAdatokFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoksByStatuszIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get all the partnerCegAdatokList where statusz equals to
        defaultPartnerCegAdatokFiltering("statusz.equals=" + DEFAULT_STATUSZ, "statusz.equals=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoksByStatuszIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get all the partnerCegAdatokList where statusz in
        defaultPartnerCegAdatokFiltering("statusz.in=" + DEFAULT_STATUSZ + "," + UPDATED_STATUSZ, "statusz.in=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoksByStatuszIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get all the partnerCegAdatokList where statusz is not null
        defaultPartnerCegAdatokFiltering("statusz.specified=true", "statusz.specified=false");
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoksByStatuszContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get all the partnerCegAdatokList where statusz contains
        defaultPartnerCegAdatokFiltering("statusz.contains=" + DEFAULT_STATUSZ, "statusz.contains=" + UPDATED_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoksByStatuszNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        // Get all the partnerCegAdatokList where statusz does not contain
        defaultPartnerCegAdatokFiltering("statusz.doesNotContain=" + UPDATED_STATUSZ, "statusz.doesNotContain=" + DEFAULT_STATUSZ);
    }

    @Test
    @Transactional
    void getAllPartnerCegAdatoksByCegIsEqualToSomething() throws Exception {
        CegAlapadatok ceg;
        if (TestUtil.findAll(em, CegAlapadatok.class).isEmpty()) {
            partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);
            ceg = CegAlapadatokResourceIT.createEntity();
        } else {
            ceg = TestUtil.findAll(em, CegAlapadatok.class).get(0);
        }
        em.persist(ceg);
        em.flush();
        partnerCegAdatok.setCeg(ceg);
        partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);
        Long cegId = ceg.getId();
        // Get all the partnerCegAdatokList where ceg equals to cegId
        defaultPartnerCegAdatokShouldBeFound("cegId.equals=" + cegId);

        // Get all the partnerCegAdatokList where ceg equals to (cegId + 1)
        defaultPartnerCegAdatokShouldNotBeFound("cegId.equals=" + (cegId + 1));
    }

    private void defaultPartnerCegAdatokFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPartnerCegAdatokShouldBeFound(shouldBeFound);
        defaultPartnerCegAdatokShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartnerCegAdatokShouldBeFound(String filter) throws Exception {
        restPartnerCegAdatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerCegAdatok.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusz").value(hasItem(DEFAULT_STATUSZ)));

        // Check, that the count call also returns 1
        restPartnerCegAdatokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartnerCegAdatokShouldNotBeFound(String filter) throws Exception {
        restPartnerCegAdatokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartnerCegAdatokMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPartnerCegAdatok() throws Exception {
        // Get the partnerCegAdatok
        restPartnerCegAdatokMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartnerCegAdatok() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegAdatok
        PartnerCegAdatok updatedPartnerCegAdatok = partnerCegAdatokRepository.findById(partnerCegAdatok.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPartnerCegAdatok are not directly saved in db
        em.detach(updatedPartnerCegAdatok);
        updatedPartnerCegAdatok.statusz(UPDATED_STATUSZ);
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(updatedPartnerCegAdatok);

        restPartnerCegAdatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerCegAdatokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartnerCegAdatokToMatchAllProperties(updatedPartnerCegAdatok);
    }

    @Test
    @Transactional
    void putNonExistingPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegAdatok.setId(longCount.incrementAndGet());

        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerCegAdatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerCegAdatokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegAdatok.setId(longCount.incrementAndGet());

        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegAdatokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegAdatok.setId(longCount.incrementAndGet());

        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegAdatokMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartnerCegAdatokWithPatch() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegAdatok using partial update
        PartnerCegAdatok partialUpdatedPartnerCegAdatok = new PartnerCegAdatok();
        partialUpdatedPartnerCegAdatok.setId(partnerCegAdatok.getId());

        partialUpdatedPartnerCegAdatok.statusz(UPDATED_STATUSZ);

        restPartnerCegAdatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerCegAdatok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartnerCegAdatok))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegAdatok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartnerCegAdatokUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPartnerCegAdatok, partnerCegAdatok),
            getPersistedPartnerCegAdatok(partnerCegAdatok)
        );
    }

    @Test
    @Transactional
    void fullUpdatePartnerCegAdatokWithPatch() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partnerCegAdatok using partial update
        PartnerCegAdatok partialUpdatedPartnerCegAdatok = new PartnerCegAdatok();
        partialUpdatedPartnerCegAdatok.setId(partnerCegAdatok.getId());

        partialUpdatedPartnerCegAdatok.statusz(UPDATED_STATUSZ);

        restPartnerCegAdatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerCegAdatok.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartnerCegAdatok))
            )
            .andExpect(status().isOk());

        // Validate the PartnerCegAdatok in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartnerCegAdatokUpdatableFieldsEquals(
            partialUpdatedPartnerCegAdatok,
            getPersistedPartnerCegAdatok(partialUpdatedPartnerCegAdatok)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegAdatok.setId(longCount.incrementAndGet());

        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerCegAdatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partnerCegAdatokDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegAdatok.setId(longCount.incrementAndGet());

        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegAdatokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartnerCegAdatok() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partnerCegAdatok.setId(longCount.incrementAndGet());

        // Create the PartnerCegAdatok
        PartnerCegAdatokDTO partnerCegAdatokDTO = partnerCegAdatokMapper.toDto(partnerCegAdatok);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerCegAdatokMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partnerCegAdatokDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerCegAdatok in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartnerCegAdatok() throws Exception {
        // Initialize the database
        insertedPartnerCegAdatok = partnerCegAdatokRepository.saveAndFlush(partnerCegAdatok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partnerCegAdatok
        restPartnerCegAdatokMockMvc
            .perform(delete(ENTITY_API_URL_ID, partnerCegAdatok.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partnerCegAdatokRepository.count();
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

    protected PartnerCegAdatok getPersistedPartnerCegAdatok(PartnerCegAdatok partnerCegAdatok) {
        return partnerCegAdatokRepository.findById(partnerCegAdatok.getId()).orElseThrow();
    }

    protected void assertPersistedPartnerCegAdatokToMatchAllProperties(PartnerCegAdatok expectedPartnerCegAdatok) {
        assertPartnerCegAdatokAllPropertiesEquals(expectedPartnerCegAdatok, getPersistedPartnerCegAdatok(expectedPartnerCegAdatok));
    }

    protected void assertPersistedPartnerCegAdatokToMatchUpdatableProperties(PartnerCegAdatok expectedPartnerCegAdatok) {
        assertPartnerCegAdatokAllUpdatablePropertiesEquals(
            expectedPartnerCegAdatok,
            getPersistedPartnerCegAdatok(expectedPartnerCegAdatok)
        );
    }
}

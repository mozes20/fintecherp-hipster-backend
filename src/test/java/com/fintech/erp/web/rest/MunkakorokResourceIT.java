package com.fintech.erp.web.rest;

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
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.service.dto.MunkakorokDTO;
import com.fintech.erp.service.mapper.MunkakorokMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link MunkakorokResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MunkakorokResourceIT {

    private static final String DEFAULT_MUNKAKOR_NEVE = "Fejlesztő";
    private static final String UPDATED_MUNKAKOR_NEVE = "Projektmenedzser";

    private static final String DEFAULT_MUNKAKOR_FELADATAI = "Fejlesztési feladatok ellátása";
    private static final String UPDATED_MUNKAKOR_FELADATAI = "Projekt koordináció";

    private static final String DEFAULT_MUNKAKOR_SZAKTUDASAI = "Java, Spring";
    private static final String UPDATED_MUNKAKOR_SZAKTUDASAI = "Agilis módszertan";

    private static final Boolean DEFAULT_EFO_MUNKAKOR = Boolean.FALSE;
    private static final Boolean UPDATED_EFO_MUNKAKOR = Boolean.TRUE;

    private static final String ENTITY_API_URL = "/api/munkakoroks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MunkakorokRepository munkakorokRepository;

    @Autowired
    private MunkakorokMapper munkakorokMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMunkakorokMockMvc;

    private Munkakorok munkakorok;
    private Munkakorok insertedMunkakorok;

    public static Munkakorok createEntity() {
        return new Munkakorok()
            .munkakorKod("TEST-001")
            .munkakorNeve(DEFAULT_MUNKAKOR_NEVE)
            .munkakorFeladatai(DEFAULT_MUNKAKOR_FELADATAI)
            .munkakorSzaktudasai(DEFAULT_MUNKAKOR_SZAKTUDASAI)
            .efoMunkakor(DEFAULT_EFO_MUNKAKOR);
    }

    public static Munkakorok createUpdatedEntity() {
        return new Munkakorok()
            .munkakorKod("TEST-002")
            .munkakorNeve(UPDATED_MUNKAKOR_NEVE)
            .munkakorFeladatai(UPDATED_MUNKAKOR_FELADATAI)
            .munkakorSzaktudasai(UPDATED_MUNKAKOR_SZAKTUDASAI)
            .efoMunkakor(UPDATED_EFO_MUNKAKOR);
    }

    @BeforeEach
    void initTest() {
        munkakorok = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMunkakorok != null) {
            munkakorokRepository.delete(insertedMunkakorok);
            insertedMunkakorok = null;
        }
    }

    @Test
    @Transactional
    void createMunkakorok() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        MunkakorokDTO munkakorokDTO = munkakorokMapper.toDto(munkakorok);
        munkakorokDTO.setMunkakorKod(null);

        var returnedDTO = om.readValue(
            restMunkakorokMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(munkakorokDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MunkakorokDTO.class
        );

        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThat(returnedDTO.getMunkakorKod()).isEqualTo("001");
        insertedMunkakorok = munkakorokMapper.toEntity(returnedDTO);
    }

    @Test
    @Transactional
    void createMunkakorokWithExistingId() throws Exception {
        munkakorok.setId(1L);
        MunkakorokDTO munkakorokDTO = munkakorokMapper.toDto(munkakorok);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restMunkakorokMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(munkakorokDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMunkakoroks() throws Exception {
        insertedMunkakorok = munkakorokRepository.saveAndFlush(munkakorok);

        restMunkakorokMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(munkakorok.getId().intValue())))
            .andExpect(jsonPath("$.[*].munkakorNeve").value(hasItem(DEFAULT_MUNKAKOR_NEVE)))
            .andExpect(jsonPath("$.[*].efoMunkakor").value(hasItem(DEFAULT_EFO_MUNKAKOR)));
    }

    @Test
    @Transactional
    void getMunkakorok() throws Exception {
        insertedMunkakorok = munkakorokRepository.saveAndFlush(munkakorok);

        restMunkakorokMockMvc
            .perform(get(ENTITY_API_URL_ID, munkakorok.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(munkakorok.getId().intValue()))
            .andExpect(jsonPath("$.munkakorNeve").value(DEFAULT_MUNKAKOR_NEVE))
            .andExpect(jsonPath("$.efoMunkakor").value(DEFAULT_EFO_MUNKAKOR));
    }

    @Test
    @Transactional
    void putExistingMunkakorok() throws Exception {
        insertedMunkakorok = munkakorokRepository.saveAndFlush(munkakorok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        Munkakorok updatedMunkakorok = munkakorokRepository.findById(munkakorok.getId()).orElseThrow();
        em.detach(updatedMunkakorok);
        updatedMunkakorok
            .munkakorNeve(UPDATED_MUNKAKOR_NEVE)
            .munkakorFeladatai(UPDATED_MUNKAKOR_FELADATAI)
            .munkakorSzaktudasai(UPDATED_MUNKAKOR_SZAKTUDASAI)
            .efoMunkakor(UPDATED_EFO_MUNKAKOR);

        MunkakorokDTO munkakorokDTO = munkakorokMapper.toDto(updatedMunkakorok);

        restMunkakorokMockMvc
            .perform(
                put(ENTITY_API_URL_ID, munkakorokDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(munkakorokDTO))
            )
            .andExpect(status().isOk());

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        Munkakorok persisted = getPersistedMunkakorok(updatedMunkakorok);
        assertThat(persisted.getMunkakorNeve()).isEqualTo(UPDATED_MUNKAKOR_NEVE);
        assertThat(persisted.getMunkakorFeladatai()).isEqualTo(UPDATED_MUNKAKOR_FELADATAI);
        assertThat(persisted.getMunkakorSzaktudasai()).isEqualTo(UPDATED_MUNKAKOR_SZAKTUDASAI);
        assertThat(persisted.getEfoMunkakor()).isEqualTo(UPDATED_EFO_MUNKAKOR);
    }

    @Test
    @Transactional
    void partialUpdateMunkakorokWithPatch() throws Exception {
        insertedMunkakorok = munkakorokRepository.saveAndFlush(munkakorok);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        Munkakorok partialUpdated = new Munkakorok();
        partialUpdated.setId(munkakorok.getId());
        partialUpdated.munkakorFeladatai(UPDATED_MUNKAKOR_FELADATAI).efoMunkakor(UPDATED_EFO_MUNKAKOR);

        restMunkakorokMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdated.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdated))
            )
            .andExpect(status().isOk());

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        Munkakorok persisted = getPersistedMunkakorok(munkakorok);
        assertThat(persisted.getMunkakorFeladatai()).isEqualTo(UPDATED_MUNKAKOR_FELADATAI);
        assertThat(persisted.getMunkakorNeve()).isEqualTo(DEFAULT_MUNKAKOR_NEVE);
        assertThat(persisted.getEfoMunkakor()).isEqualTo(UPDATED_EFO_MUNKAKOR);
    }

    @Test
    @Transactional
    void deleteMunkakorok() throws Exception {
        insertedMunkakorok = munkakorokRepository.saveAndFlush(munkakorok);

        long databaseSizeBeforeDelete = getRepositoryCount();

        restMunkakorokMockMvc.perform(delete(ENTITY_API_URL_ID, munkakorok.getId()).with(csrf())).andExpect(status().isNoContent());

        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    private long getRepositoryCount() {
        return munkakorokRepository.count();
    }

    private void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(getRepositoryCount()).isEqualTo(countBefore + 1);
    }

    private void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(getRepositoryCount()).isEqualTo(countBefore - 1);
    }

    private void assertSameRepositoryCount(long countBefore) {
        assertThat(getRepositoryCount()).isEqualTo(countBefore);
    }

    private Munkakorok getPersistedMunkakorok(Munkakorok munkakorok) {
        return munkakorokRepository.findById(munkakorok.getId()).orElseThrow();
    }
}

package com.fintech.erp.web.rest;

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
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.mapper.MegrendelesekMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Minimal integration tests for {@link MegrendelesekResource} covering the refined field set.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MegrendelesekResourceIT {

    private static final MegrendelesTipus DEFAULT_MEGRENDELES_TIPUSA = MegrendelesTipus.FOLYAMATOS_TELESTITES;
    private static final MegrendelesTipus UPDATED_MEGRENDELES_TIPUSA = MegrendelesTipus.EGYSZERI;

    private static final String DEFAULT_FELADAT_ROVID_LEIRASA = "Alap rovid leiras";
    private static final String UPDATED_FELADAT_ROVID_LEIRASA = "Frissitett rovid leiras";

    private static final String DEFAULT_FELADAT_RESZLETES_LEIRASA = "Alap reszletes leiras";
    private static final String UPDATED_FELADAT_RESZLETES_LEIRASA = "Frissitett reszletes leiras";

    private static final String DEFAULT_SZALLITASRA_KERULO_TETELEK = "Alap tetelek";
    private static final String UPDATED_SZALLITASRA_KERULO_TETELEK = "Frissitett tetelek";

    private static final LocalDate DEFAULT_MEGRENDELES_KEZDETE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEGRENDELES_KEZDETE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MEGRENDELES_VEGE = LocalDate.ofEpochDay(10L);
    private static final LocalDate UPDATED_MEGRENDELES_VEGE = LocalDate.now(ZoneId.systemDefault()).plusDays(5);

    private static final LocalDate DEFAULT_MEGRENDELES_DATUMA = LocalDate.ofEpochDay(1L);
    private static final LocalDate UPDATED_MEGRENDELES_DATUMA = LocalDate.now(ZoneId.systemDefault());

    private static final ResztvevoKollagaTipus DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA = ResztvevoKollagaTipus.MUNKAVALLALO;
    private static final ResztvevoKollagaTipus UPDATED_RESZTVEVO_KOLLAGA_TIPUSA = ResztvevoKollagaTipus.ALVALLALKOZO;

    private static final Devizanem DEFAULT_DEVIZANEM = Devizanem.HUF;
    private static final Devizanem UPDATED_DEVIZANEM = Devizanem.EUR;

    private static final DijazasTipusa DEFAULT_DIJAZAS_TIPUSA = DijazasTipusa.NAPIDIJ;
    private static final DijazasTipusa UPDATED_DIJAZAS_TIPUSA = DijazasTipusa.EGYSZERI;

    private static final BigDecimal DEFAULT_DIJ_OSSZEGE = new BigDecimal("1000.00");
    private static final BigDecimal UPDATED_DIJ_OSSZEGE = new BigDecimal("2000.50");

    private static final MegrendelesDokumentumEredet DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA = MegrendelesDokumentumEredet.KEZI;
    private static final MegrendelesDokumentumEredet UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA = MegrendelesDokumentumEredet.GENERALTA;

    private static final Long DEFAULT_UGYFEL_MEGRENDELES_ID = 100L;
    private static final Long UPDATED_UGYFEL_MEGRENDELES_ID = 200L;

    private static final String DEFAULT_MEGRENDELES_SZAM = "MEGR-0001";
    private static final String UPDATED_MEGRENDELES_SZAM = "MEGR-9999";

    private static final String ENTITY_API_URL = "/api/megrendeleseks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final AtomicLong JOGVISZONY_SEQUENCE = new AtomicLong();
    private static final AtomicLong MUNKAKOR_SEQUENCE = new AtomicLong();

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MegrendelesekRepository megrendelesekRepository;

    @Autowired
    private MegrendelesekMapper megrendelesekMapper;

    @Autowired
    private SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMegrendelesekMockMvc;

    private Megrendelesek megrendelesek;
    private Megrendelesek insertedMegrendelesek;
    private SzerzodesesJogviszonyok insertedSzerzodesesJogviszony;
    private CegAlapadatok insertedMegrendeloCeg;
    private CegAlapadatok insertedVallalkozoCeg;
    private Munkakorok insertedMunkakor;

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
            .szallitasraKeruloTetelek(DEFAULT_SZALLITASRA_KERULO_TETELEK)
            .megrendelesKezdete(DEFAULT_MEGRENDELES_KEZDETE)
            .megrendelesVege(DEFAULT_MEGRENDELES_VEGE)
            .megrendelesDatuma(DEFAULT_MEGRENDELES_DATUMA)
            .resztvevoKollagaTipusa(DEFAULT_RESZTVEVO_KOLLAGA_TIPUSA)
            .devizanem(DEFAULT_DEVIZANEM)
            .dijazasTipusa(DEFAULT_DIJAZAS_TIPUSA)
            .dijOsszege(DEFAULT_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(DEFAULT_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(DEFAULT_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(DEFAULT_MEGRENDELES_SZAM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Megrendelesek createUpdatedEntity(Long munkakorId) {
        return new Megrendelesek()
            .megrendelesTipusa(UPDATED_MEGRENDELES_TIPUSA)
            .feladatRovidLeirasa(UPDATED_FELADAT_ROVID_LEIRASA)
            .feladatReszletesLeirasa(UPDATED_FELADAT_RESZLETES_LEIRASA)
            .szallitasraKeruloTetelek(UPDATED_SZALLITASRA_KERULO_TETELEK)
            .megrendelesKezdete(UPDATED_MEGRENDELES_KEZDETE)
            .megrendelesVege(UPDATED_MEGRENDELES_VEGE)
            .megrendelesDatuma(UPDATED_MEGRENDELES_DATUMA)
            .resztvevoKollagaTipusa(UPDATED_RESZTVEVO_KOLLAGA_TIPUSA)
            .devizanem(UPDATED_DEVIZANEM)
            .dijazasTipusa(UPDATED_DIJAZAS_TIPUSA)
            .dijOsszege(UPDATED_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(UPDATED_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(UPDATED_MEGRENDELES_SZAM)
            .munkakorId(munkakorId);
    }

    public static Megrendelesek createEntityWithMunkakor(Long munkakorId) {
        return createEntity().munkakorId(munkakorId);
    }

    @BeforeEach
    void initTest() {
        insertedMunkakor = createPersistedMunkakor();
        megrendelesek = createEntityWithMunkakor(insertedMunkakor.getId());
        megrendelesek.setSzerzodesesJogviszony(createPersistedJogviszony());
    }

    @AfterEach
    void cleanup() {
        if (insertedMegrendelesek != null) {
            megrendelesekRepository.delete(insertedMegrendelesek);
            insertedMegrendelesek = null;
        }
        if (insertedSzerzodesesJogviszony != null && insertedSzerzodesesJogviszony.getId() != null) {
            szerzodesesJogviszonyokRepository.deleteById(insertedSzerzodesesJogviszony.getId());
            em.flush();
            insertedSzerzodesesJogviszony = null;
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
        if (insertedMunkakor != null && insertedMunkakor.getId() != null) {
            Munkakorok managed = em.contains(insertedMunkakor) ? insertedMunkakor : em.merge(insertedMunkakor);
            em.remove(managed);
            insertedMunkakor = null;
            em.flush();
        }
    }

    @Test
    @Transactional
    void createMegrendelesek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        MegrendelesekDTO dto = megrendelesekMapper.toDto(megrendelesek);

        var returnedDto = om.readValue(
            restMegrendelesekMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MegrendelesekDTO.class
        );

        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        Megrendelesek returnedEntity = megrendelesekMapper.toEntity(returnedDto);
        assertMegrendelesekUpdatableFieldsEquals(returnedEntity, getPersistedMegrendelesek(returnedEntity));

        insertedMegrendelesek = returnedEntity;
    }

    @Test
    @Transactional
    void createMegrendelesekWithExistingId() throws Exception {
        megrendelesek.setId(1L);
        MegrendelesekDTO dto = megrendelesekMapper.toDto(megrendelesek);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restMegrendelesekMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dto)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMegrendeleseks() throws Exception {
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(megrendelesek.getId().intValue())))
            .andExpect(jsonPath("$.[*].szallitasraKeruloTetelek").value(hasItem(DEFAULT_SZALLITASRA_KERULO_TETELEK)))
            .andExpect(jsonPath("$.[*].megrendelesDatuma").value(hasItem(DEFAULT_MEGRENDELES_DATUMA.toString())))
            .andExpect(jsonPath("$.[*].dijOsszege").value(hasItem(sameNumber(DEFAULT_DIJ_OSSZEGE))))
            .andExpect(jsonPath("$.[*].munkakorId").value(hasItem(insertedMunkakor.getId().intValue())));
    }

    @Test
    @Transactional
    void getMegrendelesek() throws Exception {
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        restMegrendelesekMockMvc
            .perform(get(ENTITY_API_URL_ID, megrendelesek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(megrendelesek.getId().intValue()))
            .andExpect(jsonPath("$.szallitasraKeruloTetelek").value(DEFAULT_SZALLITASRA_KERULO_TETELEK))
            .andExpect(jsonPath("$.megrendelesDatuma").value(DEFAULT_MEGRENDELES_DATUMA.toString()))
            .andExpect(jsonPath("$.dijOsszege").value(sameNumber(DEFAULT_DIJ_OSSZEGE)))
            .andExpect(jsonPath("$.munkakorId").value(insertedMunkakor.getId().intValue()));
    }

    @Test
    @Transactional
    void putExistingMegrendelesek() throws Exception {
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        Megrendelesek updatedMegrendelesek = megrendelesekRepository.findById(megrendelesek.getId()).orElseThrow();
        em.detach(updatedMegrendelesek);
        Munkakorok masikMunkakor = createPersistedMunkakor();
        updatedMegrendelesek
            .megrendelesTipusa(UPDATED_MEGRENDELES_TIPUSA)
            .feladatRovidLeirasa(UPDATED_FELADAT_ROVID_LEIRASA)
            .feladatReszletesLeirasa(UPDATED_FELADAT_RESZLETES_LEIRASA)
            .szallitasraKeruloTetelek(UPDATED_SZALLITASRA_KERULO_TETELEK)
            .megrendelesKezdete(UPDATED_MEGRENDELES_KEZDETE)
            .megrendelesVege(UPDATED_MEGRENDELES_VEGE)
            .megrendelesDatuma(UPDATED_MEGRENDELES_DATUMA)
            .resztvevoKollagaTipusa(UPDATED_RESZTVEVO_KOLLAGA_TIPUSA)
            .devizanem(UPDATED_DEVIZANEM)
            .dijazasTipusa(UPDATED_DIJAZAS_TIPUSA)
            .dijOsszege(UPDATED_DIJ_OSSZEGE)
            .megrendelesDokumentumGeneralta(UPDATED_MEGRENDELES_DOKUMENTUM_GENERALTA)
            .ugyfelMegrendelesId(UPDATED_UGYFEL_MEGRENDELES_ID)
            .megrendelesSzam(UPDATED_MEGRENDELES_SZAM)
            .munkakorId(masikMunkakor.getId());

        MegrendelesekDTO dto = megrendelesekMapper.toDto(updatedMegrendelesek);

        restMegrendelesekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId()).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dto))
            )
            .andExpect(status().isOk());

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMegrendelesekUpdatableFieldsEquals(updatedMegrendelesek, getPersistedMegrendelesek(updatedMegrendelesek));
    }

    @Test
    @Transactional
    void partialUpdateMegrendelesekWithPatch() throws Exception {
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        Megrendelesek partialUpdatedMegrendelesek = new Megrendelesek();
        partialUpdatedMegrendelesek.setId(megrendelesek.getId());
        Munkakorok ujMunkakor = createPersistedMunkakor();
        partialUpdatedMegrendelesek
            .szallitasraKeruloTetelek(UPDATED_SZALLITASRA_KERULO_TETELEK)
            .megrendelesDatuma(UPDATED_MEGRENDELES_DATUMA)
            .munkakorId(ujMunkakor.getId());

        restMegrendelesekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMegrendelesek.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMegrendelesek))
            )
            .andExpect(status().isOk());

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMegrendelesekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMegrendelesek, megrendelesek),
            getPersistedMegrendelesek(megrendelesek)
        );
    }

    @Test
    @Transactional
    void deleteMegrendelesek() throws Exception {
        insertedMegrendelesek = megrendelesekRepository.saveAndFlush(megrendelesek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        restMegrendelesekMockMvc
            .perform(delete(ENTITY_API_URL_ID, megrendelesek.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    private long getRepositoryCount() {
        return megrendelesekRepository.count();
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

    private Megrendelesek getPersistedMegrendelesek(Megrendelesek megrendelesek) {
        return megrendelesekRepository.findById(megrendelesek.getId()).orElseThrow();
    }

    private Munkakorok createPersistedMunkakor() {
        long sequence = MUNKAKOR_SEQUENCE.incrementAndGet();
        Munkakorok munkakor = new Munkakorok()
            .munkakorKod("JOB-" + sequence)
            .munkakorNeve("Teszt munkakor " + sequence)
            .munkakorFeladatai("Feladatok " + sequence)
            .munkakorSzaktudasai("Szaktudas " + sequence)
            .efoMunkakor(Boolean.FALSE);
        em.persist(munkakor);
        em.flush();
        return munkakor;
    }

    private SzerzodesesJogviszonyok createPersistedJogviszony() {
        long sequence = JOGVISZONY_SEQUENCE.incrementAndGet();
        CegAlapadatok megrendelo = new CegAlapadatok()
            .cegNev("Teszt Megrendelo " + sequence)
            .cegRovidAzonosito("MR-" + sequence)
            .cegSzekhely("Teszt Szekhely " + sequence);
        em.persist(megrendelo);

        CegAlapadatok vallalkozo = new CegAlapadatok()
            .cegNev("Teszt Vallalkozo " + sequence)
            .cegRovidAzonosito("VR-" + sequence)
            .cegSzekhely("Teszt Telephely " + sequence);
        em.persist(vallalkozo);

        em.flush();

        SzerzodesesJogviszonyok jogviszony = new SzerzodesesJogviszonyok()
            .szerzodesAzonosito("SZERZ-" + sequence)
            .jogviszonyKezdete(DEFAULT_MEGRENDELES_KEZDETE)
            .jogviszonyLejarata(DEFAULT_MEGRENDELES_VEGE.plusDays(30))
            .megrendeloCeg(megrendelo)
            .vallalkozoCeg(vallalkozo);
        em.persist(jogviszony);
        em.flush();

        insertedMegrendeloCeg = megrendelo;
        insertedVallalkozoCeg = vallalkozo;
        insertedSzerzodesesJogviszony = jogviszony;
        return jogviszony;
    }
}

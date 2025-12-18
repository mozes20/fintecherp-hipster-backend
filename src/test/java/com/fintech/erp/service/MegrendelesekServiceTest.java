package com.fintech.erp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.repository.MegrendelesekRepository;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import com.fintech.erp.service.dto.MegrendelesekDTO;
import com.fintech.erp.service.dto.SzerzodesesJogviszonyokDTO;
import com.fintech.erp.service.mapper.MegrendelesekMapper;
import com.fintech.erp.service.mapper.MegrendelesekMapperImpl;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MegrendelesekServiceTest {

    private static final Long JOGVISZONY_ID = 42L;
    private static final String SZERZODES_AZONOSITO = "SZERZ-001";

    @Mock
    private MegrendelesekRepository megrendelesekRepository;

    @Mock
    private SzerzodesesJogviszonyokRepository szerzodesesJogviszonyokRepository;

    @Mock
    private MunkakorokRepository munkakorokRepository;

    private MegrendelesekMapper megrendelesekMapper;

    private MegrendelesekService megrendelesekService;

    @BeforeEach
    void setUp() {
        megrendelesekMapper = new MegrendelesekMapperImpl();
        megrendelesekService = new MegrendelesekService(
            megrendelesekRepository,
            megrendelesekMapper,
            szerzodesesJogviszonyokRepository,
            munkakorokRepository
        );
    }

    @Test
    void saveShouldGenerateOrderNumberWhenMissing() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 1, 10), LocalDate.of(2025, 2, 28));
        dto.setMegrendelesDatuma(null);

        when(munkakorokRepository.existsById(5L)).thenReturn(true);

        when(
            megrendelesekRepository.findFirstBySzerzodesesJogviszony_IdAndMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(
                JOGVISZONY_ID,
                "SZERZ-001/2025/"
            )
        ).thenReturn(Optional.empty());
        when(megrendelesekRepository.save(any(Megrendelesek.class))).thenAnswer(invocation -> {
            Megrendelesek entity = invocation.getArgument(0, Megrendelesek.class);
            entity.setId(1L);
            return entity;
        });

        LocalDate today = LocalDate.now();
        MegrendelesekDTO result = megrendelesekService.save(dto);

        assertThat(result.getMegrendelesSzam()).isEqualTo("SZERZ-001/2025/001");
        assertThat(result.getMegrendelesDatuma()).isEqualTo(today);
        assertThat(result.getMegrendelesDokumentumGeneralta()).isEqualTo(MegrendelesDokumentumEredet.KEZI);
        verify(szerzodesesJogviszonyokRepository, never()).findById(anyLong());
    }

    @Test
    void saveShouldIncrementSequenceBasedOnExistingOrders() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 30));

        when(munkakorokRepository.existsById(5L)).thenReturn(true);

        Megrendelesek existing = new Megrendelesek();
        existing.setMegrendelesSzam("SZERZ-001/2025/009");

        when(
            megrendelesekRepository.findFirstBySzerzodesesJogviszony_IdAndMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(
                JOGVISZONY_ID,
                "SZERZ-001/2025/"
            )
        ).thenReturn(Optional.of(existing));
        when(megrendelesekRepository.save(any(Megrendelesek.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MegrendelesekDTO result = megrendelesekService.save(dto);

        assertThat(result.getMegrendelesSzam()).isEqualTo("SZERZ-001/2025/010");
    }

    @Test
    void saveShouldRejectWhenEndBeforeStart() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 1));

        assertThatThrownBy(() -> megrendelesekService.save(dto)).isInstanceOf(BadRequestAlertException.class).hasMessageContaining("vége");

        verify(megrendelesekRepository, never()).save(any(Megrendelesek.class));
        verify(
            megrendelesekRepository,
            never()
        ).findFirstBySzerzodesesJogviszony_IdAndMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(anyLong(), anyString());
    }

    @Test
    void saveShouldRejectWhenInvoiceAmountMissingForBillableOrder() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 4, 5), LocalDate.of(2025, 5, 5));
        dto.setDijOsszege(BigDecimal.ZERO);

        assertThatThrownBy(() -> megrendelesekService.save(dto))
            .isInstanceOf(BadRequestAlertException.class)
            .hasMessageContaining("díj összegének");

        verify(megrendelesekRepository, never()).save(any(Megrendelesek.class));
    }

    @Test
    void saveShouldFailWhenContractMissing() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 31));
        dto.setSzerzodesesJogviszony(null);

        assertThatThrownBy(() -> megrendelesekService.save(dto))
            .isInstanceOf(BadRequestAlertException.class)
            .hasMessageContaining("jogviszony");

        verify(szerzodesesJogviszonyokRepository, never()).findById(anyLong());
        verify(megrendelesekRepository, never()).save(any(Megrendelesek.class));
    }

    @Test
    void saveShouldLoadContractFromRepositoryWhenIdentifierMissing() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 20));
        dto.getSzerzodesesJogviszony().setSzerzodesAzonosito(null);

        when(munkakorokRepository.existsById(5L)).thenReturn(true);

        SzerzodesesJogviszonyok persisted = new SzerzodesesJogviszonyok();
        persisted.setId(JOGVISZONY_ID);
        persisted.setSzerzodesAzonosito(SZERZODES_AZONOSITO);

        when(szerzodesesJogviszonyokRepository.findById(JOGVISZONY_ID)).thenReturn(Optional.of(persisted));
        when(
            megrendelesekRepository.findFirstBySzerzodesesJogviszony_IdAndMegrendelesSzamStartingWithOrderByMegrendelesSzamDesc(
                JOGVISZONY_ID,
                "SZERZ-001/2025/"
            )
        ).thenReturn(Optional.empty());
        when(megrendelesekRepository.save(any(Megrendelesek.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MegrendelesekDTO result = megrendelesekService.save(dto);

        assertThat(result.getMegrendelesSzam()).isEqualTo("SZERZ-001/2025/001");
        verify(szerzodesesJogviszonyokRepository).findById(JOGVISZONY_ID);
    }

    @Test
    void saveShouldRejectWhenReferencedMunkakorMissing() {
        MegrendelesekDTO dto = createValidMegrendelesDto(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 30));
        when(munkakorokRepository.existsById(5L)).thenReturn(false);

        assertThatThrownBy(() -> megrendelesekService.save(dto))
            .isInstanceOf(BadRequestAlertException.class)
            .hasMessageContaining("munkakör");

        verify(megrendelesekRepository, never()).save(any(Megrendelesek.class));
    }

    private MegrendelesekDTO createValidMegrendelesDto(LocalDate start, LocalDate end) {
        MegrendelesekDTO dto = new MegrendelesekDTO();
        dto.setMegrendelesTipusa(MegrendelesTipus.EGYSZERI);
        dto.setMegrendelesKezdete(start);
        dto.setMegrendelesVege(end);
        dto.setSzallitasraKeruloTetelek("Laptop, monitor");
        dto.setDevizanem(Devizanem.HUF);
        dto.setDijazasTipusa(DijazasTipusa.EGYSZERI);
        dto.setDijOsszege(BigDecimal.valueOf(150_000));
        dto.setMegrendelesDokumentumGeneralta(null);
        dto.setMegrendelesDatuma(LocalDate.of(2025, 1, 5));
        dto.setMunkakorId(5L);
        dto.setSzerzodesesJogviszony(createValidJogviszonyDto());
        return dto;
    }

    private SzerzodesesJogviszonyokDTO createValidJogviszonyDto() {
        SzerzodesesJogviszonyokDTO jogviszony = new SzerzodesesJogviszonyokDTO();
        jogviszony.setId(JOGVISZONY_ID);
        jogviszony.setSzerzodesAzonosito(SZERZODES_AZONOSITO);
        jogviszony.setJogviszonyKezdete(LocalDate.of(2024, 1, 1));
        jogviszony.setJogviszonyLejarata(LocalDate.of(2025, 12, 31));
        jogviszony.setMegrendeloCegId(10L);
        jogviszony.setVallalkozoCegId(20L);
        return jogviszony;
    }
}

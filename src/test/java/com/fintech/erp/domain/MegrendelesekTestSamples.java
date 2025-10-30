package com.fintech.erp.domain;

import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MegrendelesekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Megrendelesek getMegrendelesekSample1() {
        return new Megrendelesek()
            .id(1L)
            .megrendelesTipusa(MegrendelesTipus.FOLYAMATOS_TELESTITES)
            .feladatRovidLeirasa("feladatRovidLeirasa1")
            .feladatReszletesLeirasa("feladatReszletesLeirasa1")
            .resztvevoKollagaTipusa(ResztvevoKollagaTipus.MUNKAVALLALO)
            .devizanem(Devizanem.HUF)
            .dijazasTipusa(DijazasTipusa.NAPIDIJ)
            .ugyfelMegrendelesId(1L);
    }

    public static Megrendelesek getMegrendelesekSample2() {
        return new Megrendelesek()
            .id(2L)
            .megrendelesTipusa(MegrendelesTipus.EGYSZERI)
            .feladatRovidLeirasa("feladatRovidLeirasa2")
            .feladatReszletesLeirasa("feladatReszletesLeirasa2")
            .resztvevoKollagaTipusa(ResztvevoKollagaTipus.ALVALLALKOZO)
            .devizanem(Devizanem.EUR)
            .dijazasTipusa(DijazasTipusa.EGYSZERI)
            .ugyfelMegrendelesId(2L);
    }

    public static Megrendelesek getMegrendelesekRandomSampleGenerator() {
        Megrendelesek sample = new Megrendelesek()
            .id(longCount.incrementAndGet())
            .megrendelesTipusa(MegrendelesTipus.values()[random.nextInt(MegrendelesTipus.values().length)])
            .feladatRovidLeirasa("feladatRovidLeirasa" + longCount.incrementAndGet())
            .feladatReszletesLeirasa("feladatReszletesLeirasa" + longCount.incrementAndGet())
            .resztvevoKollagaTipusa(ResztvevoKollagaTipus.values()[random.nextInt(ResztvevoKollagaTipus.values().length)])
            .devizanem(Devizanem.values()[random.nextInt(Devizanem.values().length)])
            .dijazasTipusa(DijazasTipusa.values()[random.nextInt(DijazasTipusa.values().length)])
            .ugyfelMegrendelesId(longCount.incrementAndGet());
        return sample;
    }
}

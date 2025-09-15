package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MegrendelesekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Megrendelesek getMegrendelesekSample1() {
        return new Megrendelesek()
            .id(1L)
            .megrendelesTipusa("megrendelesTipusa1")
            .feladatRovidLeirasa("feladatRovidLeirasa1")
            .feladatReszletesLeirasa("feladatReszletesLeirasa1")
            .resztvevoKollagaTipusa("resztvevoKollagaTipusa1")
            .devizanem("devizanem1")
            .dijazasTipusa("dijazasTipusa1")
            .ugyfelMegrendelesId(1L);
    }

    public static Megrendelesek getMegrendelesekSample2() {
        return new Megrendelesek()
            .id(2L)
            .megrendelesTipusa("megrendelesTipusa2")
            .feladatRovidLeirasa("feladatRovidLeirasa2")
            .feladatReszletesLeirasa("feladatReszletesLeirasa2")
            .resztvevoKollagaTipusa("resztvevoKollagaTipusa2")
            .devizanem("devizanem2")
            .dijazasTipusa("dijazasTipusa2")
            .ugyfelMegrendelesId(2L);
    }

    public static Megrendelesek getMegrendelesekRandomSampleGenerator() {
        return new Megrendelesek()
            .id(longCount.incrementAndGet())
            .megrendelesTipusa(UUID.randomUUID().toString())
            .feladatRovidLeirasa(UUID.randomUUID().toString())
            .feladatReszletesLeirasa(UUID.randomUUID().toString())
            .resztvevoKollagaTipusa(UUID.randomUUID().toString())
            .devizanem(UUID.randomUUID().toString())
            .dijazasTipusa(UUID.randomUUID().toString())
            .ugyfelMegrendelesId(longCount.incrementAndGet());
    }
}

package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TeljesitesIgazolasDokumentumokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TeljesitesIgazolasDokumentumok getTeljesitesIgazolasDokumentumokSample1() {
        return new TeljesitesIgazolasDokumentumok().id(1L).dokumentumTipusa("dokumentumTipusa1").dokumentum("dokumentum1");
    }

    public static TeljesitesIgazolasDokumentumok getTeljesitesIgazolasDokumentumokSample2() {
        return new TeljesitesIgazolasDokumentumok().id(2L).dokumentumTipusa("dokumentumTipusa2").dokumentum("dokumentum2");
    }

    public static TeljesitesIgazolasDokumentumok getTeljesitesIgazolasDokumentumokRandomSampleGenerator() {
        return new TeljesitesIgazolasDokumentumok()
            .id(longCount.incrementAndGet())
            .dokumentumTipusa(UUID.randomUUID().toString())
            .dokumentum(UUID.randomUUID().toString());
    }
}

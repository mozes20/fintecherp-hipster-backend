package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MegrendelesDokumentumokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MegrendelesDokumentumok getMegrendelesDokumentumokSample1() {
        return new MegrendelesDokumentumok().id(1L).dokumentumTipusa("dokumentumTipusa1").dokumentum("dokumentum1");
    }

    public static MegrendelesDokumentumok getMegrendelesDokumentumokSample2() {
        return new MegrendelesDokumentumok().id(2L).dokumentumTipusa("dokumentumTipusa2").dokumentum("dokumentum2");
    }

    public static MegrendelesDokumentumok getMegrendelesDokumentumokRandomSampleGenerator() {
        return new MegrendelesDokumentumok()
            .id(longCount.incrementAndGet())
            .dokumentumTipusa(UUID.randomUUID().toString())
            .dokumentum(UUID.randomUUID().toString());
    }
}

package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MunkavallalokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Munkavallalok getMunkavallalokSample1() {
        return new Munkavallalok().id(1L).foglalkoztatasTipusa("foglalkoztatasTipusa1");
    }

    public static Munkavallalok getMunkavallalokSample2() {
        return new Munkavallalok().id(2L).foglalkoztatasTipusa("foglalkoztatasTipusa2");
    }

    public static Munkavallalok getMunkavallalokRandomSampleGenerator() {
        return new Munkavallalok().id(longCount.incrementAndGet()).foglalkoztatasTipusa(UUID.randomUUID().toString());
    }
}

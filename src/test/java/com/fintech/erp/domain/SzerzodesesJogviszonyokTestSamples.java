package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SzerzodesesJogviszonyokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SzerzodesesJogviszonyok getSzerzodesesJogviszonyokSample1() {
        return new SzerzodesesJogviszonyok().id(1L).szerzodesAzonosito("szerzodesAzonosito1");
    }

    public static SzerzodesesJogviszonyok getSzerzodesesJogviszonyokSample2() {
        return new SzerzodesesJogviszonyok().id(2L).szerzodesAzonosito("szerzodesAzonosito2");
    }

    public static SzerzodesesJogviszonyok getSzerzodesesJogviszonyokRandomSampleGenerator() {
        return new SzerzodesesJogviszonyok().id(longCount.incrementAndGet()).szerzodesAzonosito(UUID.randomUUID().toString());
    }
}

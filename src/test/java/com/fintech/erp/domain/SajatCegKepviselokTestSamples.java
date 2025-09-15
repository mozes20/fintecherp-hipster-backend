package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SajatCegKepviselokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SajatCegKepviselok getSajatCegKepviselokSample1() {
        return new SajatCegKepviselok().id(1L).statusz("statusz1");
    }

    public static SajatCegKepviselok getSajatCegKepviselokSample2() {
        return new SajatCegKepviselok().id(2L).statusz("statusz2");
    }

    public static SajatCegKepviselok getSajatCegKepviselokRandomSampleGenerator() {
        return new SajatCegKepviselok().id(longCount.incrementAndGet()).statusz(UUID.randomUUID().toString());
    }
}

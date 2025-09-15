package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SajatCegAlapadatokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SajatCegAlapadatok getSajatCegAlapadatokSample1() {
        return new SajatCegAlapadatok().id(1L).statusz("statusz1");
    }

    public static SajatCegAlapadatok getSajatCegAlapadatokSample2() {
        return new SajatCegAlapadatok().id(2L).statusz("statusz2");
    }

    public static SajatCegAlapadatok getSajatCegAlapadatokRandomSampleGenerator() {
        return new SajatCegAlapadatok().id(longCount.incrementAndGet()).statusz(UUID.randomUUID().toString());
    }
}

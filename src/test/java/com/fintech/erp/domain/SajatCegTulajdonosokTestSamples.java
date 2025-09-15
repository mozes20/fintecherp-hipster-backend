package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SajatCegTulajdonosokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SajatCegTulajdonosok getSajatCegTulajdonosokSample1() {
        return new SajatCegTulajdonosok().id(1L).statusz("statusz1");
    }

    public static SajatCegTulajdonosok getSajatCegTulajdonosokSample2() {
        return new SajatCegTulajdonosok().id(2L).statusz("statusz2");
    }

    public static SajatCegTulajdonosok getSajatCegTulajdonosokRandomSampleGenerator() {
        return new SajatCegTulajdonosok().id(longCount.incrementAndGet()).statusz(UUID.randomUUID().toString());
    }
}

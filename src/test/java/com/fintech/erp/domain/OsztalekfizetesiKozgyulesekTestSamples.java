package com.fintech.erp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OsztalekfizetesiKozgyulesekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OsztalekfizetesiKozgyulesek getOsztalekfizetesiKozgyulesekSample1() {
        return new OsztalekfizetesiKozgyulesek().id(1L);
    }

    public static OsztalekfizetesiKozgyulesek getOsztalekfizetesiKozgyulesekSample2() {
        return new OsztalekfizetesiKozgyulesek().id(2L);
    }

    public static OsztalekfizetesiKozgyulesek getOsztalekfizetesiKozgyulesekRandomSampleGenerator() {
        return new OsztalekfizetesiKozgyulesek().id(longCount.incrementAndGet());
    }
}

package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartnerCegMunkavallalokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PartnerCegMunkavallalok getPartnerCegMunkavallalokSample1() {
        return new PartnerCegMunkavallalok().id(1L).statusz("statusz1");
    }

    public static PartnerCegMunkavallalok getPartnerCegMunkavallalokSample2() {
        return new PartnerCegMunkavallalok().id(2L).statusz("statusz2");
    }

    public static PartnerCegMunkavallalok getPartnerCegMunkavallalokRandomSampleGenerator() {
        return new PartnerCegMunkavallalok().id(longCount.incrementAndGet()).statusz(UUID.randomUUID().toString());
    }
}

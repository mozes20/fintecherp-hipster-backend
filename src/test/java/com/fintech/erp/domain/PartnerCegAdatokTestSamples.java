package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartnerCegAdatokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PartnerCegAdatok getPartnerCegAdatokSample1() {
        return new PartnerCegAdatok().id(1L).statusz("statusz1");
    }

    public static PartnerCegAdatok getPartnerCegAdatokSample2() {
        return new PartnerCegAdatok().id(2L).statusz("statusz2");
    }

    public static PartnerCegAdatok getPartnerCegAdatokRandomSampleGenerator() {
        return new PartnerCegAdatok().id(longCount.incrementAndGet()).statusz(UUID.randomUUID().toString());
    }
}

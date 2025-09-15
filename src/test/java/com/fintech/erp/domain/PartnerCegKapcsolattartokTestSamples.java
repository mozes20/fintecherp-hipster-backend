package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartnerCegKapcsolattartokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PartnerCegKapcsolattartok getPartnerCegKapcsolattartokSample1() {
        return new PartnerCegKapcsolattartok().id(1L).kapcsolattartoTitulus("kapcsolattartoTitulus1").statusz("statusz1");
    }

    public static PartnerCegKapcsolattartok getPartnerCegKapcsolattartokSample2() {
        return new PartnerCegKapcsolattartok().id(2L).kapcsolattartoTitulus("kapcsolattartoTitulus2").statusz("statusz2");
    }

    public static PartnerCegKapcsolattartok getPartnerCegKapcsolattartokRandomSampleGenerator() {
        return new PartnerCegKapcsolattartok()
            .id(longCount.incrementAndGet())
            .kapcsolattartoTitulus(UUID.randomUUID().toString())
            .statusz(UUID.randomUUID().toString());
    }
}

package com.fintech.erp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UgyfelElszamolasokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static UgyfelElszamolasok getUgyfelElszamolasokSample1() {
        return new UgyfelElszamolasok().id(1L).napokSzama(1);
    }

    public static UgyfelElszamolasok getUgyfelElszamolasokSample2() {
        return new UgyfelElszamolasok().id(2L).napokSzama(2);
    }

    public static UgyfelElszamolasok getUgyfelElszamolasokRandomSampleGenerator() {
        return new UgyfelElszamolasok().id(longCount.incrementAndGet()).napokSzama(intCount.incrementAndGet());
    }
}

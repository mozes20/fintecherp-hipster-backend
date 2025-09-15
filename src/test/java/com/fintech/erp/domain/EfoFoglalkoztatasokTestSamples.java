package com.fintech.erp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EfoFoglalkoztatasokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EfoFoglalkoztatasok getEfoFoglalkoztatasokSample1() {
        return new EfoFoglalkoztatasok().id(1L);
    }

    public static EfoFoglalkoztatasok getEfoFoglalkoztatasokSample2() {
        return new EfoFoglalkoztatasok().id(2L);
    }

    public static EfoFoglalkoztatasok getEfoFoglalkoztatasokRandomSampleGenerator() {
        return new EfoFoglalkoztatasok().id(longCount.incrementAndGet());
    }
}

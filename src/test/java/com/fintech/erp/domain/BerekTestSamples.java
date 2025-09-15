package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BerekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Berek getBerekSample1() {
        return new Berek().id(1L).munkaszerzodes("munkaszerzodes1");
    }

    public static Berek getBerekSample2() {
        return new Berek().id(2L).munkaszerzodes("munkaszerzodes2");
    }

    public static Berek getBerekRandomSampleGenerator() {
        return new Berek().id(longCount.incrementAndGet()).munkaszerzodes(UUID.randomUUID().toString());
    }
}

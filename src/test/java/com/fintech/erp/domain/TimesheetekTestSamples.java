package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TimesheetekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Timesheetek getTimesheetekSample1() {
        return new Timesheetek().id(1L).munkanapStatusza("munkanapStatusza1").statusz("statusz1");
    }

    public static Timesheetek getTimesheetekSample2() {
        return new Timesheetek().id(2L).munkanapStatusza("munkanapStatusza2").statusz("statusz2");
    }

    public static Timesheetek getTimesheetekRandomSampleGenerator() {
        return new Timesheetek()
            .id(longCount.incrementAndGet())
            .munkanapStatusza(UUID.randomUUID().toString())
            .statusz(UUID.randomUUID().toString());
    }
}

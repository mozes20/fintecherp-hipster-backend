package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankszamlaszamokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bankszamlaszamok getBankszamlaszamokSample1() {
        return new Bankszamlaszamok()
            .id(1L)
            .bankszamlaDevizanem("bankszamlaDevizanem1")
            .bankszamlaGIRO("bankszamlaGIRO1")
            .bankszamlaIBAN("bankszamlaIBAN1")
            .statusz("statusz1");
    }

    public static Bankszamlaszamok getBankszamlaszamokSample2() {
        return new Bankszamlaszamok()
            .id(2L)
            .bankszamlaDevizanem("bankszamlaDevizanem2")
            .bankszamlaGIRO("bankszamlaGIRO2")
            .bankszamlaIBAN("bankszamlaIBAN2")
            .statusz("statusz2");
    }

    public static Bankszamlaszamok getBankszamlaszamokRandomSampleGenerator() {
        return new Bankszamlaszamok()
            .id(longCount.incrementAndGet())
            .bankszamlaDevizanem(UUID.randomUUID().toString())
            .bankszamlaGIRO(UUID.randomUUID().toString())
            .bankszamlaIBAN(UUID.randomUUID().toString())
            .statusz(UUID.randomUUID().toString());
    }
}

package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CegAlapadatokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CegAlapadatok getCegAlapadatokSample1() {
        return new CegAlapadatok()
            .id(1L)
            .cegNev("cegNev1")
            .cegRovidAzonosito("cegRovidAzonosito1")
            .cegSzekhely("cegSzekhely1")
            .adoszam("adoszam1")
            .cegjegyzekszam("cegjegyzekszam1")
            .cegKozpontiEmail("cegKozpontiEmail1")
            .cegKozpontiTel("cegKozpontiTel1")
            .statusz("statusz1");
    }

    public static CegAlapadatok getCegAlapadatokSample2() {
        return new CegAlapadatok()
            .id(2L)
            .cegNev("cegNev2")
            .cegRovidAzonosito("cegRovidAzonosito2")
            .cegSzekhely("cegSzekhely2")
            .adoszam("adoszam2")
            .cegjegyzekszam("cegjegyzekszam2")
            .cegKozpontiEmail("cegKozpontiEmail2")
            .cegKozpontiTel("cegKozpontiTel2")
            .statusz("statusz2");
    }

    public static CegAlapadatok getCegAlapadatokRandomSampleGenerator() {
        return new CegAlapadatok()
            .id(longCount.incrementAndGet())
            .cegNev(UUID.randomUUID().toString())
            .cegRovidAzonosito(UUID.randomUUID().toString())
            .cegSzekhely(UUID.randomUUID().toString())
            .adoszam(UUID.randomUUID().toString())
            .cegjegyzekszam(UUID.randomUUID().toString())
            .cegKozpontiEmail(UUID.randomUUID().toString())
            .cegKozpontiTel(UUID.randomUUID().toString())
            .statusz(UUID.randomUUID().toString());
    }
}

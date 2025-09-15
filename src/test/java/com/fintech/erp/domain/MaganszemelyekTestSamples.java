package com.fintech.erp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MaganszemelyekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Maganszemelyek getMaganszemelyekSample1() {
        return new Maganszemelyek()
            .id(1L)
            .maganszemelyNeve("maganszemelyNeve1")
            .szemelyiIgazolvanySzama("szemelyiIgazolvanySzama1")
            .adoAzonositoJel("adoAzonositoJel1")
            .tbAzonosito("tbAzonosito1")
            .bankszamlaszam("bankszamlaszam1")
            .telefonszam("telefonszam1")
            .emailcim("emailcim1")
            .statusz("statusz1");
    }

    public static Maganszemelyek getMaganszemelyekSample2() {
        return new Maganszemelyek()
            .id(2L)
            .maganszemelyNeve("maganszemelyNeve2")
            .szemelyiIgazolvanySzama("szemelyiIgazolvanySzama2")
            .adoAzonositoJel("adoAzonositoJel2")
            .tbAzonosito("tbAzonosito2")
            .bankszamlaszam("bankszamlaszam2")
            .telefonszam("telefonszam2")
            .emailcim("emailcim2")
            .statusz("statusz2");
    }

    public static Maganszemelyek getMaganszemelyekRandomSampleGenerator() {
        return new Maganszemelyek()
            .id(longCount.incrementAndGet())
            .maganszemelyNeve(UUID.randomUUID().toString())
            .szemelyiIgazolvanySzama(UUID.randomUUID().toString())
            .adoAzonositoJel(UUID.randomUUID().toString())
            .tbAzonosito(UUID.randomUUID().toString())
            .bankszamlaszam(UUID.randomUUID().toString())
            .telefonszam(UUID.randomUUID().toString())
            .emailcim(UUID.randomUUID().toString())
            .statusz(UUID.randomUUID().toString());
    }
}

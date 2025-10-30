package com.fintech.erp.domain;

import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MegrendelesDokumentumokTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MegrendelesDokumentumok getMegrendelesDokumentumokSample1() {
        return new MegrendelesDokumentumok()
            .id(1L)
            .dokumentumTipusa(MegrendelesDokumentumTipus.GENERALTA_WORD)
            .dokumentum("dokumentum1")
            .dokumentumUrl("uploads/megrendelesek/1/dokumentum1.docx")
            .dokumentumAzonosito("001");
    }

    public static MegrendelesDokumentumok getMegrendelesDokumentumokSample2() {
        return new MegrendelesDokumentumok()
            .id(2L)
            .dokumentumTipusa(MegrendelesDokumentumTipus.GENERALTA_PDF)
            .dokumentum("dokumentum2")
            .dokumentumUrl("uploads/megrendelesek/2/dokumentum2.pdf")
            .dokumentumAzonosito("002");
    }

    public static MegrendelesDokumentumok getMegrendelesDokumentumokRandomSampleGenerator() {
        MegrendelesDokumentumTipus[] values = MegrendelesDokumentumTipus.values();
        return new MegrendelesDokumentumok()
            .id(longCount.incrementAndGet())
            .dokumentumTipusa(values[random.nextInt(values.length)])
            .dokumentum(UUID.randomUUID().toString())
            .dokumentumUrl("uploads/megrendelesek/" + longCount.get() + "/" + UUID.randomUUID() + ".docx")
            .dokumentumAzonosito(String.format("%03d", random.nextInt(999)));
    }
}

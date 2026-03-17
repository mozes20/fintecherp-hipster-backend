package com.fintech.erp.web.rest.vm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Request body for the OsztalekfizetesiKozgyulesek Excel export.
 * Contains optional overhead cost items to include in the second section.
 */
public class OsztalekfizetesiElszamolasExcelRequest implements Serializable {

    /**
     * Korrigált napidíj cap in e HUF per day. Defaults to 100 (UIM EFO maximum).
     */
    private BigDecimal korigaltNapdij = BigDecimal.valueOf(100);

    /**
     * Optional overhead/cost items displayed below the worker section.
     * E.g. Könyvelő, Bank, Osztalék - FinTech Services, etc.
     */
    private List<EgyebKoltsegSor> egyebKoltsegek = new ArrayList<>();

    public BigDecimal getKorigaltNapdij() {
        return korigaltNapdij != null ? korigaltNapdij : BigDecimal.valueOf(100);
    }

    public void setKorigaltNapdij(BigDecimal korigaltNapdij) {
        this.korigaltNapdij = korigaltNapdij;
    }

    /**
     * Per-person row overrides. If non-empty, the Excel service uses these values
     * directly instead of re-fetching timesheets and bérek from the database.
     */
    private List<PersonSor> personSorok = new ArrayList<>();

    public List<EgyebKoltsegSor> getEgyebKoltsegek() {
        return egyebKoltsegek;
    }

    public void setEgyebKoltsegek(List<EgyebKoltsegSor> egyebKoltsegek) {
        this.egyebKoltsegek = egyebKoltsegek != null ? egyebKoltsegek : new ArrayList<>();
    }

    public List<PersonSor> getPersonSorok() {
        return personSorok;
    }

    public void setPersonSorok(List<PersonSor> personSorok) {
        this.personSorok = personSorok != null ? personSorok : new ArrayList<>();
    }

    // -------------------------------------------------------------------------

    public static class PersonSor implements Serializable {

        /** Display name in column A */
        private String nev;

        /** Teljes költség in e HUF */
        private BigDecimal teljesKoltseg;

        /** Number of worked days */
        private Integer ledolgozottNapok;

        /** Korrigált napidíj in e HUF/day for this specific person */
        private BigDecimal korigaltNapdij;

        public String getNev() { return nev; }
        public void setNev(String nev) { this.nev = nev; }

        public BigDecimal getTeljesKoltseg() { return teljesKoltseg; }
        public void setTeljesKoltseg(BigDecimal teljesKoltseg) { this.teljesKoltseg = teljesKoltseg; }

        public Integer getLedolgozottNapok() { return ledolgozottNapok; }
        public void setLedolgozottNapok(Integer ledolgozottNapok) { this.ledolgozottNapok = ledolgozottNapok; }

        public BigDecimal getKorigaltNapdij() { return korigaltNapdij != null ? korigaltNapdij : BigDecimal.valueOf(100); }
        public void setKorigaltNapdij(BigDecimal korigaltNapdij) { this.korigaltNapdij = korigaltNapdij; }
    }

    // -------------------------------------------------------------------------

    public static class EgyebKoltsegSor implements Serializable {

        /** Label shown in column A, e.g. "Könyvelő", "Bank", "Osztalék - FinTech Services" */
        private String megnevezes;

        /** Amount in e HUF */
        private BigDecimal osszeg;

        public String getMegnevezes() {
            return megnevezes;
        }

        public void setMegnevezes(String megnevezes) {
            this.megnevezes = megnevezes;
        }

        public BigDecimal getOsszeg() {
            return osszeg;
        }

        public void setOsszeg(BigDecimal osszeg) {
            this.osszeg = osszeg;
        }
    }
}

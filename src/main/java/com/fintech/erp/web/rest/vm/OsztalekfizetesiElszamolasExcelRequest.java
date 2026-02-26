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
     * Optional overhead/cost items displayed below the worker section.
     * E.g. Könyvelő, Bank, Osztalék - FinTech Services, etc.
     */
    private List<EgyebKoltsegSor> egyebKoltsegek = new ArrayList<>();

    public List<EgyebKoltsegSor> getEgyebKoltsegek() {
        return egyebKoltsegek;
    }

    public void setEgyebKoltsegek(List<EgyebKoltsegSor> egyebKoltsegek) {
        this.egyebKoltsegek = egyebKoltsegek != null ? egyebKoltsegek : new ArrayList<>();
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

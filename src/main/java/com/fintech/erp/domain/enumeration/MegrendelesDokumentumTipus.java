package com.fintech.erp.domain.enumeration;

/**
 * A megrendeléshez tartozó dokumentumok típusa.
 */
public enum MegrendelesDokumentumTipus {
    /**
     * Generált szerkeszthető (Word) állomány.
     */
    GENERALTA_WORD,

    /**
     * Kézileg feltöltött Word dokumentum.
     */
    KEZI_WORD,

    /**
     * Rendszer által generált PDF.
     */
    GENERALTA_PDF,

    /**
     * Aláírt, végleges PDF.
     */
    ALAIRT_PDF,
}

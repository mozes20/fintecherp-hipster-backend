package com.fintech.erp.service.dto;

import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.MegrendelesDokumentumok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesDokumentumokDTO implements Serializable {

    private Long id;

    private MegrendelesDokumentumTipus dokumentumTipusa;

    private String dokumentum;

    private String dokumentumUrl;

    private String dokumentumAzonosito;

    private MegrendelesekDTO megrendeles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MegrendelesDokumentumTipus getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public void setDokumentumTipusa(MegrendelesDokumentumTipus dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentum() {
        return dokumentum;
    }

    public void setDokumentum(String dokumentum) {
        this.dokumentum = dokumentum;
    }

    public String getDokumentumUrl() {
        return dokumentumUrl;
    }

    public void setDokumentumUrl(String dokumentumUrl) {
        this.dokumentumUrl = dokumentumUrl;
    }

    public String getDokumentumAzonosito() {
        return dokumentumAzonosito;
    }

    public void setDokumentumAzonosito(String dokumentumAzonosito) {
        this.dokumentumAzonosito = dokumentumAzonosito;
    }

    public MegrendelesekDTO getMegrendeles() {
        return megrendeles;
    }

    public void setMegrendeles(MegrendelesekDTO megrendeles) {
        this.megrendeles = megrendeles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MegrendelesDokumentumokDTO)) {
            return false;
        }

        MegrendelesDokumentumokDTO megrendelesDokumentumokDTO = (MegrendelesDokumentumokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, megrendelesDokumentumokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MegrendelesDokumentumokDTO{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentum='" + getDokumentum() + "'" +
            ", dokumentumUrl='" + getDokumentumUrl() + "'" +
            ", dokumentumAzonosito='" + getDokumentumAzonosito() + "'" +
            ", megrendeles=" + getMegrendeles() +
            "}";
    }
}

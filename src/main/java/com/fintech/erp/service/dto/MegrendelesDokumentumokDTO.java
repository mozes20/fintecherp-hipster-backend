package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.MegrendelesDokumentumok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesDokumentumokDTO implements Serializable {

    private Long id;

    private String dokumentumTipusa;

    private String dokumentum;

    private MegrendelesekDTO megrendeles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentum() {
        return dokumentum;
    }

    public void setDokumentum(String dokumentum) {
        this.dokumentum = dokumentum;
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
            ", megrendeles=" + getMegrendeles() +
            "}";
    }
}

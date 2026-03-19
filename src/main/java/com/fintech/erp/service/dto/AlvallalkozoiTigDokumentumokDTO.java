package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.AlvallalkozoiTigDokumentumok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlvallalkozoiTigDokumentumokDTO implements Serializable {

    private Long id;

    private String dokumentumTipusa;

    private String dokumentum;

    private AlvallalkozoiElszamolasokDTO elszamolas;

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

    public AlvallalkozoiElszamolasokDTO getElszamolas() {
        return elszamolas;
    }

    public void setElszamolas(AlvallalkozoiElszamolasokDTO elszamolas) {
        this.elszamolas = elszamolas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlvallalkozoiTigDokumentumokDTO)) return false;
        AlvallalkozoiTigDokumentumokDTO that = (AlvallalkozoiTigDokumentumokDTO) o;
        if (this.id == null) return false;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "AlvallalkozoiTigDokumentumokDTO{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentum='" + getDokumentum() + "'" +
            ", elszamolas=" + getElszamolas() +
            "}";
    }
}

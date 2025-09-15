package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.TeljesitesIgazolasDokumentumok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeljesitesIgazolasDokumentumokDTO implements Serializable {

    private Long id;

    private String dokumentumTipusa;

    private String dokumentum;

    private UgyfelElszamolasokDTO teljesitesIgazolas;

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

    public UgyfelElszamolasokDTO getTeljesitesIgazolas() {
        return teljesitesIgazolas;
    }

    public void setTeljesitesIgazolas(UgyfelElszamolasokDTO teljesitesIgazolas) {
        this.teljesitesIgazolas = teljesitesIgazolas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeljesitesIgazolasDokumentumokDTO)) {
            return false;
        }

        TeljesitesIgazolasDokumentumokDTO teljesitesIgazolasDokumentumokDTO = (TeljesitesIgazolasDokumentumokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teljesitesIgazolasDokumentumokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeljesitesIgazolasDokumentumokDTO{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentum='" + getDokumentum() + "'" +
            ", teljesitesIgazolas=" + getTeljesitesIgazolas() +
            "}";
    }
}

package com.fintech.erp.service.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SzerzodesDokumentumTipus} entity.
 */
public class SzerzodesDokumentumTipusDTO implements Serializable {

    private Long id;

    @NotBlank
    private String nev;

    private String leiras;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesDokumentumTipusDTO)) {
            return false;
        }
        SzerzodesDokumentumTipusDTO that = (SzerzodesDokumentumTipusDTO) o;
        if (id == null) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SzerzodesDokumentumTipusDTO{" + "id=" + id + ", nev='" + nev + '\'' + ", leiras='" + leiras + '\'' + '}';
    }
}

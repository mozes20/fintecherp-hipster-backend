package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Berek} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BerekDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate ervenyessegKezdete;

    @NotNull
    private BigDecimal bruttoHaviMunkaberVagyNapdij;

    private String munkaszerzodes;

    private BigDecimal teljesKoltseg;

    private MunkavallalokDTO munkavallalo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getErvenyessegKezdete() {
        return ervenyessegKezdete;
    }

    public void setErvenyessegKezdete(LocalDate ervenyessegKezdete) {
        this.ervenyessegKezdete = ervenyessegKezdete;
    }

    public BigDecimal getBruttoHaviMunkaberVagyNapdij() {
        return bruttoHaviMunkaberVagyNapdij;
    }

    public void setBruttoHaviMunkaberVagyNapdij(BigDecimal bruttoHaviMunkaberVagyNapdij) {
        this.bruttoHaviMunkaberVagyNapdij = bruttoHaviMunkaberVagyNapdij;
    }

    public String getMunkaszerzodes() {
        return munkaszerzodes;
    }

    public void setMunkaszerzodes(String munkaszerzodes) {
        this.munkaszerzodes = munkaszerzodes;
    }

    public BigDecimal getTeljesKoltseg() {
        return teljesKoltseg;
    }

    public void setTeljesKoltseg(BigDecimal teljesKoltseg) {
        this.teljesKoltseg = teljesKoltseg;
    }

    public MunkavallalokDTO getMunkavallalo() {
        return munkavallalo;
    }

    public void setMunkavallalo(MunkavallalokDTO munkavallalo) {
        this.munkavallalo = munkavallalo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BerekDTO)) {
            return false;
        }

        BerekDTO berekDTO = (BerekDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, berekDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BerekDTO{" +
            "id=" + getId() +
            ", ervenyessegKezdete='" + getErvenyessegKezdete() + "'" +
            ", bruttoHaviMunkaberVagyNapdij=" + getBruttoHaviMunkaberVagyNapdij() +
            ", munkaszerzodes='" + getMunkaszerzodes() + "'" +
            ", teljesKoltseg=" + getTeljesKoltseg() +
            ", munkavallalo=" + getMunkavallalo() +
            "}";
    }
}

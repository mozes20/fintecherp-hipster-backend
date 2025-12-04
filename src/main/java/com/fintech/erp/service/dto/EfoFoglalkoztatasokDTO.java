package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.EfoFoglalkoztatasok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EfoFoglalkoztatasokDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate datum;

    private BigDecimal osszeg;

    private Boolean generaltEfoSzerzodes;

    private Boolean alairtEfoSzerzodes;

    private MunkavallalokDTO munkavallalo;

    private MunkakorokDTO munkakor;

    private String generaltDokumentumNev;

    private String generaltDokumentumUrl;

    private String alairtDokumentumUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public BigDecimal getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(BigDecimal osszeg) {
        this.osszeg = osszeg;
    }

    public Boolean getGeneraltEfoSzerzodes() {
        return generaltEfoSzerzodes;
    }

    public void setGeneraltEfoSzerzodes(Boolean generaltEfoSzerzodes) {
        this.generaltEfoSzerzodes = generaltEfoSzerzodes;
    }

    public Boolean getAlairtEfoSzerzodes() {
        return alairtEfoSzerzodes;
    }

    public void setAlairtEfoSzerzodes(Boolean alairtEfoSzerzodes) {
        this.alairtEfoSzerzodes = alairtEfoSzerzodes;
    }

    public MunkavallalokDTO getMunkavallalo() {
        return munkavallalo;
    }

    public void setMunkavallalo(MunkavallalokDTO munkavallalo) {
        this.munkavallalo = munkavallalo;
    }

    public MunkakorokDTO getMunkakor() {
        return munkakor;
    }

    public void setMunkakor(MunkakorokDTO munkakor) {
        this.munkakor = munkakor;
    }

    public String getGeneraltDokumentumNev() {
        return generaltDokumentumNev;
    }

    public void setGeneraltDokumentumNev(String generaltDokumentumNev) {
        this.generaltDokumentumNev = generaltDokumentumNev;
    }

    public String getGeneraltDokumentumUrl() {
        return generaltDokumentumUrl;
    }

    public void setGeneraltDokumentumUrl(String generaltDokumentumUrl) {
        this.generaltDokumentumUrl = generaltDokumentumUrl;
    }

    public String getAlairtDokumentumUrl() {
        return alairtDokumentumUrl;
    }

    public void setAlairtDokumentumUrl(String alairtDokumentumUrl) {
        this.alairtDokumentumUrl = alairtDokumentumUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EfoFoglalkoztatasokDTO)) {
            return false;
        }

        EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO = (EfoFoglalkoztatasokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, efoFoglalkoztatasokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EfoFoglalkoztatasokDTO{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", osszeg=" + getOsszeg() +
            ", generaltEfoSzerzodes='" + getGeneraltEfoSzerzodes() + "'" +
            ", alairtEfoSzerzodes='" + getAlairtEfoSzerzodes() + "'" +
            ", munkavallalo=" + getMunkavallalo() +
            ", munkakor=" + getMunkakor() +
            ", generaltDokumentumNev='" + getGeneraltDokumentumNev() + "'" +
            ", generaltDokumentumUrl='" + getGeneraltDokumentumUrl() + "'" +
            ", alairtDokumentumUrl='" + getAlairtDokumentumUrl() + "'" +
            "}";
    }
}

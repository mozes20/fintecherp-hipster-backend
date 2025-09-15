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
            "}";
    }
}

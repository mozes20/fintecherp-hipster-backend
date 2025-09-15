package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Munkavallalok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MunkavallalokDTO implements Serializable {

    private Long id;

    private String foglalkoztatasTipusa;

    private LocalDate foglalkoztatasKezdete;

    private LocalDate foglalkoztatasVege;

    private SajatCegAlapadatokDTO sajatCeg;

    private MaganszemelyekDTO maganszemely;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoglalkoztatasTipusa() {
        return foglalkoztatasTipusa;
    }

    public void setFoglalkoztatasTipusa(String foglalkoztatasTipusa) {
        this.foglalkoztatasTipusa = foglalkoztatasTipusa;
    }

    public LocalDate getFoglalkoztatasKezdete() {
        return foglalkoztatasKezdete;
    }

    public void setFoglalkoztatasKezdete(LocalDate foglalkoztatasKezdete) {
        this.foglalkoztatasKezdete = foglalkoztatasKezdete;
    }

    public LocalDate getFoglalkoztatasVege() {
        return foglalkoztatasVege;
    }

    public void setFoglalkoztatasVege(LocalDate foglalkoztatasVege) {
        this.foglalkoztatasVege = foglalkoztatasVege;
    }

    public SajatCegAlapadatokDTO getSajatCeg() {
        return sajatCeg;
    }

    public void setSajatCeg(SajatCegAlapadatokDTO sajatCeg) {
        this.sajatCeg = sajatCeg;
    }

    public MaganszemelyekDTO getMaganszemely() {
        return maganszemely;
    }

    public void setMaganszemely(MaganszemelyekDTO maganszemely) {
        this.maganszemely = maganszemely;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MunkavallalokDTO)) {
            return false;
        }

        MunkavallalokDTO munkavallalokDTO = (MunkavallalokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, munkavallalokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MunkavallalokDTO{" +
            "id=" + getId() +
            ", foglalkoztatasTipusa='" + getFoglalkoztatasTipusa() + "'" +
            ", foglalkoztatasKezdete='" + getFoglalkoztatasKezdete() + "'" +
            ", foglalkoztatasVege='" + getFoglalkoztatasVege() + "'" +
            ", sajatCeg=" + getSajatCeg() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

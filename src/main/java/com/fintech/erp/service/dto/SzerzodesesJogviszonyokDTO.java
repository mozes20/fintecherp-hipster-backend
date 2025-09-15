package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SzerzodesesJogviszonyok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SzerzodesesJogviszonyokDTO implements Serializable {

    private Long id;

    private String szerzodesAzonosito;

    private LocalDate jogviszonyKezdete;

    private LocalDate jogviszonyLejarata;

    private CegAlapadatokDTO megrendeloCeg;

    private CegAlapadatokDTO vallalkozoCeg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSzerzodesAzonosito() {
        return szerzodesAzonosito;
    }

    public void setSzerzodesAzonosito(String szerzodesAzonosito) {
        this.szerzodesAzonosito = szerzodesAzonosito;
    }

    public LocalDate getJogviszonyKezdete() {
        return jogviszonyKezdete;
    }

    public void setJogviszonyKezdete(LocalDate jogviszonyKezdete) {
        this.jogviszonyKezdete = jogviszonyKezdete;
    }

    public LocalDate getJogviszonyLejarata() {
        return jogviszonyLejarata;
    }

    public void setJogviszonyLejarata(LocalDate jogviszonyLejarata) {
        this.jogviszonyLejarata = jogviszonyLejarata;
    }

    public CegAlapadatokDTO getMegrendeloCeg() {
        return megrendeloCeg;
    }

    public void setMegrendeloCeg(CegAlapadatokDTO megrendeloCeg) {
        this.megrendeloCeg = megrendeloCeg;
    }

    public CegAlapadatokDTO getVallalkozoCeg() {
        return vallalkozoCeg;
    }

    public void setVallalkozoCeg(CegAlapadatokDTO vallalkozoCeg) {
        this.vallalkozoCeg = vallalkozoCeg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesesJogviszonyokDTO)) {
            return false;
        }

        SzerzodesesJogviszonyokDTO szerzodesesJogviszonyokDTO = (SzerzodesesJogviszonyokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, szerzodesesJogviszonyokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SzerzodesesJogviszonyokDTO{" +
            "id=" + getId() +
            ", szerzodesAzonosito='" + getSzerzodesAzonosito() + "'" +
            ", jogviszonyKezdete='" + getJogviszonyKezdete() + "'" +
            ", jogviszonyLejarata='" + getJogviszonyLejarata() + "'" +
            ", megrendeloCeg=" + getMegrendeloCeg() +
            ", vallalkozoCeg=" + getVallalkozoCeg() +
            "}";
    }
}

package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.PartnerCegKapcsolattartok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegKapcsolattartokDTO implements Serializable {

    private Long id;

    private String kapcsolattartoTitulus;

    private String statusz;

    private PartnerCegAdatokDTO partnerCeg;

    private MaganszemelyekDTO maganszemely;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKapcsolattartoTitulus() {
        return kapcsolattartoTitulus;
    }

    public void setKapcsolattartoTitulus(String kapcsolattartoTitulus) {
        this.kapcsolattartoTitulus = kapcsolattartoTitulus;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public PartnerCegAdatokDTO getPartnerCeg() {
        return partnerCeg;
    }

    public void setPartnerCeg(PartnerCegAdatokDTO partnerCeg) {
        this.partnerCeg = partnerCeg;
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
        if (!(o instanceof PartnerCegKapcsolattartokDTO)) {
            return false;
        }

        PartnerCegKapcsolattartokDTO partnerCegKapcsolattartokDTO = (PartnerCegKapcsolattartokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partnerCegKapcsolattartokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerCegKapcsolattartokDTO{" +
            "id=" + getId() +
            ", kapcsolattartoTitulus='" + getKapcsolattartoTitulus() + "'" +
            ", statusz='" + getStatusz() + "'" +
            ", partnerCeg=" + getPartnerCeg() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

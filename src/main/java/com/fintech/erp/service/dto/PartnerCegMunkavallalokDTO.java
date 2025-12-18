package com.fintech.erp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.PartnerCegMunkavallalok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegMunkavallalokDTO implements Serializable {

    private Long id;

    private String statusz;

    private PartnerCegAdatokDTO partnerCeg;

    private MaganszemelyekDTO maganszemely;

    @JsonProperty("partnerCegId")
    private Long partnerCegId;

    @JsonProperty("maganszemelyId")
    private Long maganszemelyId;

    public Long getPartnerCegId() {
        return partnerCegId;
    }

    public void setPartnerCegId(Long partnerCegId) {
        this.partnerCegId = partnerCegId;
    }

    public Long getMaganszemelyId() {
        return maganszemelyId;
    }

    public void setMaganszemelyId(Long maganszemelyId) {
        this.maganszemelyId = maganszemelyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof PartnerCegMunkavallalokDTO)) {
            return false;
        }

        PartnerCegMunkavallalokDTO partnerCegMunkavallalokDTO = (PartnerCegMunkavallalokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partnerCegMunkavallalokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerCegMunkavallalokDTO{" +
            "id=" + getId() +
            ", statusz='" + getStatusz() + '\'' +
            ", partnerCeg=" + getPartnerCeg() +
            ", maganszemely=" + getMaganszemely() +
            ", partnerCegId=" + getPartnerCegId() +
            ", maganszemelyId=" + getMaganszemelyId() +
            '}';
    }
}

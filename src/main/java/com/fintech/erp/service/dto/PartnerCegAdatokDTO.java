package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.PartnerCegAdatok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegAdatokDTO implements Serializable {

    private Long id;

    private String statusz;

    private CegAlapadatokDTO ceg;

    private Long cegId;

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

    public CegAlapadatokDTO getCeg() {
        return ceg;
    }

    public void setCeg(CegAlapadatokDTO ceg) {
        this.ceg = ceg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnerCegAdatokDTO)) {
            return false;
        }

        PartnerCegAdatokDTO partnerCegAdatokDTO = (PartnerCegAdatokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partnerCegAdatokDTO.id);
    }

    public Long getCegId() {
        return cegId;
    }

    public void setCegId(Long cegId) {
        this.cegId = cegId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override

    public String toString() {
        return "PartnerCegAdatokDTO{" +
            "id=" + getId() +
            ", statusz='" + getStatusz() + "'" +
            ", ceg=" + getCeg() +
            ", cegId=" + getCegId() +
            "}";
    }
}

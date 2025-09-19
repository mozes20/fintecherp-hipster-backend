package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SajatCegAlapadatok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegAlapadatokDTO implements Serializable {

    private Long id;

    private BigDecimal cegAdminisztraciosHaviKoltseg;

    private String statusz;

    private Long cegId;

    private CegAlapadatokDTO ceg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCegAdminisztraciosHaviKoltseg() {
        return cegAdminisztraciosHaviKoltseg;
    }

    public void setCegAdminisztraciosHaviKoltseg(BigDecimal cegAdminisztraciosHaviKoltseg) {
        this.cegAdminisztraciosHaviKoltseg = cegAdminisztraciosHaviKoltseg;
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

    public Long getCegId() {
        return cegId;
    }

    public void setCegId(Long cegId) {
        this.cegId = cegId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SajatCegAlapadatokDTO)) {
            return false;
        }

        SajatCegAlapadatokDTO sajatCegAlapadatokDTO = (SajatCegAlapadatokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sajatCegAlapadatokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegAlapadatokDTO{" +
            "id=" + getId() +
            ", cegAdminisztraciosHaviKoltseg=" + getCegAdminisztraciosHaviKoltseg() +
            ", statusz='" + getStatusz() + "'" +
            ", cegId=" + getCegId() +
            ", ceg=" + getCeg() +
            "}";
    }
}

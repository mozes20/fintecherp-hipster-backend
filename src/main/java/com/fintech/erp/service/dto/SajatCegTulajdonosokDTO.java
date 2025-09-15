package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SajatCegTulajdonosok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegTulajdonosokDTO implements Serializable {

    private Long id;

    private BigDecimal bruttoOsztalek;

    private String statusz;

    private SajatCegAlapadatokDTO sajatCeg;

    private MaganszemelyekDTO maganszemely;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBruttoOsztalek() {
        return bruttoOsztalek;
    }

    public void setBruttoOsztalek(BigDecimal bruttoOsztalek) {
        this.bruttoOsztalek = bruttoOsztalek;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
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
        if (!(o instanceof SajatCegTulajdonosokDTO)) {
            return false;
        }

        SajatCegTulajdonosokDTO sajatCegTulajdonosokDTO = (SajatCegTulajdonosokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sajatCegTulajdonosokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegTulajdonosokDTO{" +
            "id=" + getId() +
            ", bruttoOsztalek=" + getBruttoOsztalek() +
            ", statusz='" + getStatusz() + "'" +
            ", sajatCeg=" + getSajatCeg() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

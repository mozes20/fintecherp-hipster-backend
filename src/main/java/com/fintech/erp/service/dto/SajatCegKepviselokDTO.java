package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SajatCegKepviselok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegKepviselokDTO implements Serializable {

    private Long id;

    private String statusz;

    private SajatCegAlapadatokDTO sajatCeg;

    private MaganszemelyekDTO maganszemely;

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
        if (!(o instanceof SajatCegKepviselokDTO)) {
            return false;
        }

        SajatCegKepviselokDTO sajatCegKepviselokDTO = (SajatCegKepviselokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sajatCegKepviselokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegKepviselokDTO{" +
            "id=" + getId() +
            ", statusz='" + getStatusz() + "'" +
            ", sajatCeg=" + getSajatCeg() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

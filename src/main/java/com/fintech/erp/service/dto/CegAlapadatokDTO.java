package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.CegAlapadatok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CegAlapadatokDTO implements Serializable {

    private Long id;

    @NotNull
    private String cegNev;

    private String cegRovidAzonosito;

    private String cegSzekhely;

    private String adoszam;

    private String cegjegyzekszam;

    private String cegKozpontiEmail;

    private String cegKozpontiTel;

    private String statusz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCegNev() {
        return cegNev;
    }

    public void setCegNev(String cegNev) {
        this.cegNev = cegNev;
    }

    public String getCegRovidAzonosito() {
        return cegRovidAzonosito;
    }

    public void setCegRovidAzonosito(String cegRovidAzonosito) {
        this.cegRovidAzonosito = cegRovidAzonosito;
    }

    public String getCegSzekhely() {
        return cegSzekhely;
    }

    public void setCegSzekhely(String cegSzekhely) {
        this.cegSzekhely = cegSzekhely;
    }

    public String getAdoszam() {
        return adoszam;
    }

    public void setAdoszam(String adoszam) {
        this.adoszam = adoszam;
    }

    public String getCegjegyzekszam() {
        return cegjegyzekszam;
    }

    public void setCegjegyzekszam(String cegjegyzekszam) {
        this.cegjegyzekszam = cegjegyzekszam;
    }

    public String getCegKozpontiEmail() {
        return cegKozpontiEmail;
    }

    public void setCegKozpontiEmail(String cegKozpontiEmail) {
        this.cegKozpontiEmail = cegKozpontiEmail;
    }

    public String getCegKozpontiTel() {
        return cegKozpontiTel;
    }

    public void setCegKozpontiTel(String cegKozpontiTel) {
        this.cegKozpontiTel = cegKozpontiTel;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CegAlapadatokDTO)) {
            return false;
        }

        CegAlapadatokDTO cegAlapadatokDTO = (CegAlapadatokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cegAlapadatokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CegAlapadatokDTO{" +
            "id=" + getId() +
            ", cegNev='" + getCegNev() + "'" +
            ", cegRovidAzonosito='" + getCegRovidAzonosito() + "'" +
            ", cegSzekhely='" + getCegSzekhely() + "'" +
            ", adoszam='" + getAdoszam() + "'" +
            ", cegjegyzekszam='" + getCegjegyzekszam() + "'" +
            ", cegKozpontiEmail='" + getCegKozpontiEmail() + "'" +
            ", cegKozpontiTel='" + getCegKozpontiTel() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

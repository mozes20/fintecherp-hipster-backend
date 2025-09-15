package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Maganszemelyek} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaganszemelyekDTO implements Serializable {

    private Long id;

    @NotNull
    private String maganszemelyNeve;

    private String szemelyiIgazolvanySzama;

    private String adoAzonositoJel;

    private String tbAzonosito;

    private String bankszamlaszam;

    private String telefonszam;

    private String emailcim;

    private String statusz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaganszemelyNeve() {
        return maganszemelyNeve;
    }

    public void setMaganszemelyNeve(String maganszemelyNeve) {
        this.maganszemelyNeve = maganszemelyNeve;
    }

    public String getSzemelyiIgazolvanySzama() {
        return szemelyiIgazolvanySzama;
    }

    public void setSzemelyiIgazolvanySzama(String szemelyiIgazolvanySzama) {
        this.szemelyiIgazolvanySzama = szemelyiIgazolvanySzama;
    }

    public String getAdoAzonositoJel() {
        return adoAzonositoJel;
    }

    public void setAdoAzonositoJel(String adoAzonositoJel) {
        this.adoAzonositoJel = adoAzonositoJel;
    }

    public String getTbAzonosito() {
        return tbAzonosito;
    }

    public void setTbAzonosito(String tbAzonosito) {
        this.tbAzonosito = tbAzonosito;
    }

    public String getBankszamlaszam() {
        return bankszamlaszam;
    }

    public void setBankszamlaszam(String bankszamlaszam) {
        this.bankszamlaszam = bankszamlaszam;
    }

    public String getTelefonszam() {
        return telefonszam;
    }

    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

    public String getEmailcim() {
        return emailcim;
    }

    public void setEmailcim(String emailcim) {
        this.emailcim = emailcim;
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
        if (!(o instanceof MaganszemelyekDTO)) {
            return false;
        }

        MaganszemelyekDTO maganszemelyekDTO = (MaganszemelyekDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, maganszemelyekDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaganszemelyekDTO{" +
            "id=" + getId() +
            ", maganszemelyNeve='" + getMaganszemelyNeve() + "'" +
            ", szemelyiIgazolvanySzama='" + getSzemelyiIgazolvanySzama() + "'" +
            ", adoAzonositoJel='" + getAdoAzonositoJel() + "'" +
            ", tbAzonosito='" + getTbAzonosito() + "'" +
            ", bankszamlaszam='" + getBankszamlaszam() + "'" +
            ", telefonszam='" + getTelefonszam() + "'" +
            ", emailcim='" + getEmailcim() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

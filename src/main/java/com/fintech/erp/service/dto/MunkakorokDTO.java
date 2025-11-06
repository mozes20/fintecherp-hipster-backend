package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Munkakorok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MunkakorokDTO implements Serializable {

    private Long id;

    private String munkakorKod;

    private String munkakorNeve;

    private String munkakorFeladatai;

    private String munkakorSzaktudasai;

    private Boolean efoMunkakor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunkakorKod() {
        return munkakorKod;
    }

    public void setMunkakorKod(String munkakorKod) {
        this.munkakorKod = munkakorKod;
    }

    public String getMunkakorNeve() {
        return munkakorNeve;
    }

    public void setMunkakorNeve(String munkakorNeve) {
        this.munkakorNeve = munkakorNeve;
    }

    public String getMunkakorFeladatai() {
        return munkakorFeladatai;
    }

    public void setMunkakorFeladatai(String munkakorFeladatai) {
        this.munkakorFeladatai = munkakorFeladatai;
    }

    public String getMunkakorSzaktudasai() {
        return munkakorSzaktudasai;
    }

    public void setMunkakorSzaktudasai(String munkakorSzaktudasai) {
        this.munkakorSzaktudasai = munkakorSzaktudasai;
    }

    public Boolean getEfoMunkakor() {
        return efoMunkakor;
    }

    public void setEfoMunkakor(Boolean efoMunkakor) {
        this.efoMunkakor = efoMunkakor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MunkakorokDTO)) {
            return false;
        }
        MunkakorokDTO munkakorokDTO = (MunkakorokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, munkakorokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "MunkakorokDTO{" +
            "id=" +
            getId() +
            ", munkakorKod='" +
            getMunkakorKod() +
            "'" +
            ", munkakorNeve='" +
            getMunkakorNeve() +
            "'" +
            ", munkakorFeladatai='" +
            getMunkakorFeladatai() +
            "'" +
            ", munkakorSzaktudasai='" +
            getMunkakorSzaktudasai() +
            "'" +
            ", efoMunkakor=" +
            getEfoMunkakor() +
            '}'
        );
    }
}

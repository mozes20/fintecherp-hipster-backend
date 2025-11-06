package com.fintech.erp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Munkakorok.
 */
@Entity
@Table(name = "munkakorok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Munkakorok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "munkakor_kod", nullable = false, unique = true)
    private String munkakorKod;

    @Column(name = "munkakor_neve", columnDefinition = "text")
    private String munkakorNeve;

    @Column(name = "munkakor_feladatai", columnDefinition = "text")
    private String munkakorFeladatai;

    @Column(name = "munkakor_szaktudasai", columnDefinition = "text")
    private String munkakorSzaktudasai;

    @Column(name = "efo_munkakor")
    private Boolean efoMunkakor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Munkakorok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunkakorKod() {
        return this.munkakorKod;
    }

    public Munkakorok munkakorKod(String munkakorKod) {
        this.setMunkakorKod(munkakorKod);
        return this;
    }

    public void setMunkakorKod(String munkakorKod) {
        this.munkakorKod = munkakorKod;
    }

    public String getMunkakorNeve() {
        return this.munkakorNeve;
    }

    public Munkakorok munkakorNeve(String munkakorNeve) {
        this.setMunkakorNeve(munkakorNeve);
        return this;
    }

    public void setMunkakorNeve(String munkakorNeve) {
        this.munkakorNeve = munkakorNeve;
    }

    public String getMunkakorFeladatai() {
        return this.munkakorFeladatai;
    }

    public Munkakorok munkakorFeladatai(String munkakorFeladatai) {
        this.setMunkakorFeladatai(munkakorFeladatai);
        return this;
    }

    public void setMunkakorFeladatai(String munkakorFeladatai) {
        this.munkakorFeladatai = munkakorFeladatai;
    }

    public String getMunkakorSzaktudasai() {
        return this.munkakorSzaktudasai;
    }

    public Munkakorok munkakorSzaktudasai(String munkakorSzaktudasai) {
        this.setMunkakorSzaktudasai(munkakorSzaktudasai);
        return this;
    }

    public void setMunkakorSzaktudasai(String munkakorSzaktudasai) {
        this.munkakorSzaktudasai = munkakorSzaktudasai;
    }

    public Boolean getEfoMunkakor() {
        return this.efoMunkakor;
    }

    public Munkakorok efoMunkakor(Boolean efoMunkakor) {
        this.setEfoMunkakor(efoMunkakor);
        return this;
    }

    public void setEfoMunkakor(Boolean efoMunkakor) {
        this.efoMunkakor = efoMunkakor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Munkakorok)) {
            return false;
        }
        return getId() != null && getId().equals(((Munkakorok) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Munkakorok{" +
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

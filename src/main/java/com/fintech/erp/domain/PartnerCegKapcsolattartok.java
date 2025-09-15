package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartnerCegKapcsolattartok.
 */
@Entity
@Table(name = "partner_ceg_kapcsolattartok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegKapcsolattartok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kapcsolattarto_titulus")
    private String kapcsolattartoTitulus;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ceg" }, allowSetters = true)
    private PartnerCegAdatok partnerCeg;

    @ManyToOne(fetch = FetchType.LAZY)
    private Maganszemelyek maganszemely;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartnerCegKapcsolattartok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKapcsolattartoTitulus() {
        return this.kapcsolattartoTitulus;
    }

    public PartnerCegKapcsolattartok kapcsolattartoTitulus(String kapcsolattartoTitulus) {
        this.setKapcsolattartoTitulus(kapcsolattartoTitulus);
        return this;
    }

    public void setKapcsolattartoTitulus(String kapcsolattartoTitulus) {
        this.kapcsolattartoTitulus = kapcsolattartoTitulus;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public PartnerCegKapcsolattartok statusz(String statusz) {
        this.setStatusz(statusz);
        return this;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public PartnerCegAdatok getPartnerCeg() {
        return this.partnerCeg;
    }

    public void setPartnerCeg(PartnerCegAdatok partnerCegAdatok) {
        this.partnerCeg = partnerCegAdatok;
    }

    public PartnerCegKapcsolattartok partnerCeg(PartnerCegAdatok partnerCegAdatok) {
        this.setPartnerCeg(partnerCegAdatok);
        return this;
    }

    public Maganszemelyek getMaganszemely() {
        return this.maganszemely;
    }

    public void setMaganszemely(Maganszemelyek maganszemelyek) {
        this.maganszemely = maganszemelyek;
    }

    public PartnerCegKapcsolattartok maganszemely(Maganszemelyek maganszemelyek) {
        this.setMaganszemely(maganszemelyek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnerCegKapcsolattartok)) {
            return false;
        }
        return getId() != null && getId().equals(((PartnerCegKapcsolattartok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerCegKapcsolattartok{" +
            "id=" + getId() +
            ", kapcsolattartoTitulus='" + getKapcsolattartoTitulus() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SajatCegTulajdonosok.
 */
@Entity
@Table(name = "sajat_ceg_tulajdonosok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegTulajdonosok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "brutto_osztalek", precision = 21, scale = 2)
    private BigDecimal bruttoOsztalek;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ceg" }, allowSetters = true)
    private SajatCegAlapadatok sajatCeg;

    @ManyToOne(fetch = FetchType.LAZY)
    private Maganszemelyek maganszemely;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SajatCegTulajdonosok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBruttoOsztalek() {
        return this.bruttoOsztalek;
    }

    public SajatCegTulajdonosok bruttoOsztalek(BigDecimal bruttoOsztalek) {
        this.setBruttoOsztalek(bruttoOsztalek);
        return this;
    }

    public void setBruttoOsztalek(BigDecimal bruttoOsztalek) {
        this.bruttoOsztalek = bruttoOsztalek;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public SajatCegTulajdonosok statusz(String statusz) {
        this.setStatusz(statusz);
        return this;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public SajatCegAlapadatok getSajatCeg() {
        return this.sajatCeg;
    }

    public void setSajatCeg(SajatCegAlapadatok sajatCegAlapadatok) {
        this.sajatCeg = sajatCegAlapadatok;
    }

    public SajatCegTulajdonosok sajatCeg(SajatCegAlapadatok sajatCegAlapadatok) {
        this.setSajatCeg(sajatCegAlapadatok);
        return this;
    }

    public Maganszemelyek getMaganszemely() {
        return this.maganszemely;
    }

    public void setMaganszemely(Maganszemelyek maganszemelyek) {
        this.maganszemely = maganszemelyek;
    }

    public SajatCegTulajdonosok maganszemely(Maganszemelyek maganszemelyek) {
        this.setMaganszemely(maganszemelyek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SajatCegTulajdonosok)) {
            return false;
        }
        return getId() != null && getId().equals(((SajatCegTulajdonosok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegTulajdonosok{" +
            "id=" + getId() +
            ", bruttoOsztalek=" + getBruttoOsztalek() +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

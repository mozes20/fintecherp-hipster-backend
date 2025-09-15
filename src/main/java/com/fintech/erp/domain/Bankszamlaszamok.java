package com.fintech.erp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bankszamlaszamok.
 */
@Entity
@Table(name = "bankszamlaszamok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bankszamlaszamok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bankszamla_devizanem")
    private String bankszamlaDevizanem;

    @Column(name = "bankszamla_giro")
    private String bankszamlaGIRO;

    @Column(name = "bankszamla_iban")
    private String bankszamlaIBAN;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    private CegAlapadatok ceg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bankszamlaszamok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankszamlaDevizanem() {
        return this.bankszamlaDevizanem;
    }

    public Bankszamlaszamok bankszamlaDevizanem(String bankszamlaDevizanem) {
        this.setBankszamlaDevizanem(bankszamlaDevizanem);
        return this;
    }

    public void setBankszamlaDevizanem(String bankszamlaDevizanem) {
        this.bankszamlaDevizanem = bankszamlaDevizanem;
    }

    public String getBankszamlaGIRO() {
        return this.bankszamlaGIRO;
    }

    public Bankszamlaszamok bankszamlaGIRO(String bankszamlaGIRO) {
        this.setBankszamlaGIRO(bankszamlaGIRO);
        return this;
    }

    public void setBankszamlaGIRO(String bankszamlaGIRO) {
        this.bankszamlaGIRO = bankszamlaGIRO;
    }

    public String getBankszamlaIBAN() {
        return this.bankszamlaIBAN;
    }

    public Bankszamlaszamok bankszamlaIBAN(String bankszamlaIBAN) {
        this.setBankszamlaIBAN(bankszamlaIBAN);
        return this;
    }

    public void setBankszamlaIBAN(String bankszamlaIBAN) {
        this.bankszamlaIBAN = bankszamlaIBAN;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public Bankszamlaszamok statusz(String statusz) {
        this.setStatusz(statusz);
        return this;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public CegAlapadatok getCeg() {
        return this.ceg;
    }

    public void setCeg(CegAlapadatok cegAlapadatok) {
        this.ceg = cegAlapadatok;
    }

    public Bankszamlaszamok ceg(CegAlapadatok cegAlapadatok) {
        this.setCeg(cegAlapadatok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bankszamlaszamok)) {
            return false;
        }
        return getId() != null && getId().equals(((Bankszamlaszamok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bankszamlaszamok{" +
            "id=" + getId() +
            ", bankszamlaDevizanem='" + getBankszamlaDevizanem() + "'" +
            ", bankszamlaGIRO='" + getBankszamlaGIRO() + "'" +
            ", bankszamlaIBAN='" + getBankszamlaIBAN() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

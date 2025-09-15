package com.fintech.erp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartnerCegAdatok.
 */
@Entity
@Table(name = "partner_ceg_adatok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerCegAdatok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    private CegAlapadatok ceg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartnerCegAdatok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public PartnerCegAdatok statusz(String statusz) {
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

    public PartnerCegAdatok ceg(CegAlapadatok cegAlapadatok) {
        this.setCeg(cegAlapadatok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnerCegAdatok)) {
            return false;
        }
        return getId() != null && getId().equals(((PartnerCegAdatok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerCegAdatok{" +
            "id=" + getId() +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

package com.fintech.erp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SajatCegAlapadatok.
 */
@Entity
@Table(name = "sajat_ceg_alapadatok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SajatCegAlapadatok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ceg_adminisztracios_havi_koltseg", precision = 21, scale = 2)
    private BigDecimal cegAdminisztraciosHaviKoltseg;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    private CegAlapadatok ceg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SajatCegAlapadatok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCegAdminisztraciosHaviKoltseg() {
        return this.cegAdminisztraciosHaviKoltseg;
    }

    public SajatCegAlapadatok cegAdminisztraciosHaviKoltseg(BigDecimal cegAdminisztraciosHaviKoltseg) {
        this.setCegAdminisztraciosHaviKoltseg(cegAdminisztraciosHaviKoltseg);
        return this;
    }

    public void setCegAdminisztraciosHaviKoltseg(BigDecimal cegAdminisztraciosHaviKoltseg) {
        this.cegAdminisztraciosHaviKoltseg = cegAdminisztraciosHaviKoltseg;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public SajatCegAlapadatok statusz(String statusz) {
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

    public SajatCegAlapadatok ceg(CegAlapadatok cegAlapadatok) {
        this.setCeg(cegAlapadatok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SajatCegAlapadatok)) {
            return false;
        }
        return getId() != null && getId().equals(((SajatCegAlapadatok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SajatCegAlapadatok{" +
            "id=" + getId() +
            ", cegAdminisztraciosHaviKoltseg=" + getCegAdminisztraciosHaviKoltseg() +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

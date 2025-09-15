package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Berek.
 */
@Entity
@Table(name = "berek")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Berek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ervenyesseg_kezdete", nullable = false)
    private LocalDate ervenyessegKezdete;

    @NotNull
    @Column(name = "brutto_havi_munkaber_vagy_napdij", precision = 21, scale = 2, nullable = false)
    private BigDecimal bruttoHaviMunkaberVagyNapdij;

    @Column(name = "munkaszerzodes")
    private String munkaszerzodes;

    @Column(name = "teljes_koltseg", precision = 21, scale = 2)
    private BigDecimal teljesKoltseg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sajatCeg", "maganszemely" }, allowSetters = true)
    private Munkavallalok munkavallalo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Berek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getErvenyessegKezdete() {
        return this.ervenyessegKezdete;
    }

    public Berek ervenyessegKezdete(LocalDate ervenyessegKezdete) {
        this.setErvenyessegKezdete(ervenyessegKezdete);
        return this;
    }

    public void setErvenyessegKezdete(LocalDate ervenyessegKezdete) {
        this.ervenyessegKezdete = ervenyessegKezdete;
    }

    public BigDecimal getBruttoHaviMunkaberVagyNapdij() {
        return this.bruttoHaviMunkaberVagyNapdij;
    }

    public Berek bruttoHaviMunkaberVagyNapdij(BigDecimal bruttoHaviMunkaberVagyNapdij) {
        this.setBruttoHaviMunkaberVagyNapdij(bruttoHaviMunkaberVagyNapdij);
        return this;
    }

    public void setBruttoHaviMunkaberVagyNapdij(BigDecimal bruttoHaviMunkaberVagyNapdij) {
        this.bruttoHaviMunkaberVagyNapdij = bruttoHaviMunkaberVagyNapdij;
    }

    public String getMunkaszerzodes() {
        return this.munkaszerzodes;
    }

    public Berek munkaszerzodes(String munkaszerzodes) {
        this.setMunkaszerzodes(munkaszerzodes);
        return this;
    }

    public void setMunkaszerzodes(String munkaszerzodes) {
        this.munkaszerzodes = munkaszerzodes;
    }

    public BigDecimal getTeljesKoltseg() {
        return this.teljesKoltseg;
    }

    public Berek teljesKoltseg(BigDecimal teljesKoltseg) {
        this.setTeljesKoltseg(teljesKoltseg);
        return this;
    }

    public void setTeljesKoltseg(BigDecimal teljesKoltseg) {
        this.teljesKoltseg = teljesKoltseg;
    }

    public Munkavallalok getMunkavallalo() {
        return this.munkavallalo;
    }

    public void setMunkavallalo(Munkavallalok munkavallalok) {
        this.munkavallalo = munkavallalok;
    }

    public Berek munkavallalo(Munkavallalok munkavallalok) {
        this.setMunkavallalo(munkavallalok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Berek)) {
            return false;
        }
        return getId() != null && getId().equals(((Berek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Berek{" +
            "id=" + getId() +
            ", ervenyessegKezdete='" + getErvenyessegKezdete() + "'" +
            ", bruttoHaviMunkaberVagyNapdij=" + getBruttoHaviMunkaberVagyNapdij() +
            ", munkaszerzodes='" + getMunkaszerzodes() + "'" +
            ", teljesKoltseg=" + getTeljesKoltseg() +
            "}";
    }
}

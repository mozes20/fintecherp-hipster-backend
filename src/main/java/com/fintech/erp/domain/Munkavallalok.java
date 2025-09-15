package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Munkavallalok.
 */
@Entity
@Table(name = "munkavallalok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Munkavallalok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "foglalkoztatas_tipusa")
    private String foglalkoztatasTipusa;

    @Column(name = "foglalkoztatas_kezdete")
    private LocalDate foglalkoztatasKezdete;

    @Column(name = "foglalkoztatas_vege")
    private LocalDate foglalkoztatasVege;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ceg" }, allowSetters = true)
    private SajatCegAlapadatok sajatCeg;

    @ManyToOne(fetch = FetchType.LAZY)
    private Maganszemelyek maganszemely;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Munkavallalok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoglalkoztatasTipusa() {
        return this.foglalkoztatasTipusa;
    }

    public Munkavallalok foglalkoztatasTipusa(String foglalkoztatasTipusa) {
        this.setFoglalkoztatasTipusa(foglalkoztatasTipusa);
        return this;
    }

    public void setFoglalkoztatasTipusa(String foglalkoztatasTipusa) {
        this.foglalkoztatasTipusa = foglalkoztatasTipusa;
    }

    public LocalDate getFoglalkoztatasKezdete() {
        return this.foglalkoztatasKezdete;
    }

    public Munkavallalok foglalkoztatasKezdete(LocalDate foglalkoztatasKezdete) {
        this.setFoglalkoztatasKezdete(foglalkoztatasKezdete);
        return this;
    }

    public void setFoglalkoztatasKezdete(LocalDate foglalkoztatasKezdete) {
        this.foglalkoztatasKezdete = foglalkoztatasKezdete;
    }

    public LocalDate getFoglalkoztatasVege() {
        return this.foglalkoztatasVege;
    }

    public Munkavallalok foglalkoztatasVege(LocalDate foglalkoztatasVege) {
        this.setFoglalkoztatasVege(foglalkoztatasVege);
        return this;
    }

    public void setFoglalkoztatasVege(LocalDate foglalkoztatasVege) {
        this.foglalkoztatasVege = foglalkoztatasVege;
    }

    public SajatCegAlapadatok getSajatCeg() {
        return this.sajatCeg;
    }

    public void setSajatCeg(SajatCegAlapadatok sajatCegAlapadatok) {
        this.sajatCeg = sajatCegAlapadatok;
    }

    public Munkavallalok sajatCeg(SajatCegAlapadatok sajatCegAlapadatok) {
        this.setSajatCeg(sajatCegAlapadatok);
        return this;
    }

    public Maganszemelyek getMaganszemely() {
        return this.maganszemely;
    }

    public void setMaganszemely(Maganszemelyek maganszemelyek) {
        this.maganszemely = maganszemelyek;
    }

    public Munkavallalok maganszemely(Maganszemelyek maganszemelyek) {
        this.setMaganszemely(maganszemelyek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Munkavallalok)) {
            return false;
        }
        return getId() != null && getId().equals(((Munkavallalok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Munkavallalok{" +
            "id=" + getId() +
            ", foglalkoztatasTipusa='" + getFoglalkoztatasTipusa() + "'" +
            ", foglalkoztatasKezdete='" + getFoglalkoztatasKezdete() + "'" +
            ", foglalkoztatasVege='" + getFoglalkoztatasVege() + "'" +
            "}";
    }
}

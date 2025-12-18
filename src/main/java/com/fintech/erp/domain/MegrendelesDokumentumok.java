package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumTipus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MegrendelesDokumentumok.
 */
@Entity
@Table(name = "megrendeles_dokumentumok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesDokumentumok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dokumentum_tipusa")
    private MegrendelesDokumentumTipus dokumentumTipusa;

    @Column(name = "dokumentum")
    private String dokumentum;

    @Column(name = "dokumentum_url")
    private String dokumentumUrl;

    @Column(name = "dokumentum_azonosito", length = 50)
    private String dokumentumAzonosito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "szerzodesesJogviszony", "maganszemely" }, allowSetters = true)
    private Megrendelesek megrendeles;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MegrendelesDokumentumok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MegrendelesDokumentumTipus getDokumentumTipusa() {
        return this.dokumentumTipusa;
    }

    public MegrendelesDokumentumok dokumentumTipusa(MegrendelesDokumentumTipus dokumentumTipusa) {
        this.setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public void setDokumentumTipusa(MegrendelesDokumentumTipus dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentum() {
        return this.dokumentum;
    }

    public MegrendelesDokumentumok dokumentum(String dokumentum) {
        this.setDokumentum(dokumentum);
        return this;
    }

    public void setDokumentum(String dokumentum) {
        this.dokumentum = dokumentum;
    }

    public String getDokumentumUrl() {
        return this.dokumentumUrl;
    }

    public void setDokumentumUrl(String dokumentumUrl) {
        this.dokumentumUrl = dokumentumUrl;
    }

    public MegrendelesDokumentumok dokumentumUrl(String dokumentumUrl) {
        this.setDokumentumUrl(dokumentumUrl);
        return this;
    }

    public String getDokumentumAzonosito() {
        return this.dokumentumAzonosito;
    }

    public void setDokumentumAzonosito(String dokumentumAzonosito) {
        this.dokumentumAzonosito = dokumentumAzonosito;
    }

    public MegrendelesDokumentumok dokumentumAzonosito(String dokumentumAzonosito) {
        this.setDokumentumAzonosito(dokumentumAzonosito);
        return this;
    }

    public Megrendelesek getMegrendeles() {
        return this.megrendeles;
    }

    public void setMegrendeles(Megrendelesek megrendelesek) {
        this.megrendeles = megrendelesek;
    }

    public MegrendelesDokumentumok megrendeles(Megrendelesek megrendelesek) {
        this.setMegrendeles(megrendelesek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MegrendelesDokumentumok)) {
            return false;
        }
        return getId() != null && getId().equals(((MegrendelesDokumentumok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MegrendelesDokumentumok{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentum='" + getDokumentum() + "'" +
            ", dokumentumUrl='" + getDokumentumUrl() + "'" +
            ", dokumentumAzonosito='" + getDokumentumAzonosito() + "'" +
            "}";
    }
}

package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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

    @Column(name = "dokumentum_tipusa")
    private String dokumentumTipusa;

    @Column(name = "dokumentum")
    private String dokumentum;

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

    public String getDokumentumTipusa() {
        return this.dokumentumTipusa;
    }

    public MegrendelesDokumentumok dokumentumTipusa(String dokumentumTipusa) {
        this.setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
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
            "}";
    }
}

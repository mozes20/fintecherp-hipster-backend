package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeljesitesIgazolasDokumentumok.
 */
@Entity
@Table(name = "teljesites_igazolas_dokumentumok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeljesitesIgazolasDokumentumok implements Serializable {

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
    @JsonIgnoreProperties(value = { "megrendeles" }, allowSetters = true)
    private UgyfelElszamolasok teljesitesIgazolas;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeljesitesIgazolasDokumentumok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumTipusa() {
        return this.dokumentumTipusa;
    }

    public TeljesitesIgazolasDokumentumok dokumentumTipusa(String dokumentumTipusa) {
        this.setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentum() {
        return this.dokumentum;
    }

    public TeljesitesIgazolasDokumentumok dokumentum(String dokumentum) {
        this.setDokumentum(dokumentum);
        return this;
    }

    public void setDokumentum(String dokumentum) {
        this.dokumentum = dokumentum;
    }

    public UgyfelElszamolasok getTeljesitesIgazolas() {
        return this.teljesitesIgazolas;
    }

    public void setTeljesitesIgazolas(UgyfelElszamolasok ugyfelElszamolasok) {
        this.teljesitesIgazolas = ugyfelElszamolasok;
    }

    public TeljesitesIgazolasDokumentumok teljesitesIgazolas(UgyfelElszamolasok ugyfelElszamolasok) {
        this.setTeljesitesIgazolas(ugyfelElszamolasok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeljesitesIgazolasDokumentumok)) {
            return false;
        }
        return getId() != null && getId().equals(((TeljesitesIgazolasDokumentumok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeljesitesIgazolasDokumentumok{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentum='" + getDokumentum() + "'" +
            "}";
    }
}

package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlvallalkozoiTigDokumentumok.
 */
@Entity
@Table(name = "alvallalkozoi_tig_dokumentumok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlvallalkozoiTigDokumentumok implements Serializable {

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
    private AlvallalkozoiElszamolasok elszamolas;

    public Long getId() {
        return this.id;
    }

    public AlvallalkozoiTigDokumentumok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumTipusa() {
        return this.dokumentumTipusa;
    }

    public AlvallalkozoiTigDokumentumok dokumentumTipusa(String dokumentumTipusa) {
        this.setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentum() {
        return this.dokumentum;
    }

    public AlvallalkozoiTigDokumentumok dokumentum(String dokumentum) {
        this.setDokumentum(dokumentum);
        return this;
    }

    public void setDokumentum(String dokumentum) {
        this.dokumentum = dokumentum;
    }

    public AlvallalkozoiElszamolasok getElszamolas() {
        return this.elszamolas;
    }

    public void setElszamolas(AlvallalkozoiElszamolasok alvallalkozoiElszamolasok) {
        this.elszamolas = alvallalkozoiElszamolasok;
    }

    public AlvallalkozoiTigDokumentumok elszamolas(AlvallalkozoiElszamolasok alvallalkozoiElszamolasok) {
        this.setElszamolas(alvallalkozoiElszamolasok);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlvallalkozoiTigDokumentumok)) return false;
        return getId() != null && getId().equals(((AlvallalkozoiTigDokumentumok) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "AlvallalkozoiTigDokumentumok{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentum='" + getDokumentum() + "'" +
            "}";
    }
}

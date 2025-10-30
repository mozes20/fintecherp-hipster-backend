package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SzerzodesesJogviszonyDokumentum.
 */
@Entity
@Table(name = "szerzodeses_jogviszony_dokumentum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SzerzodesesJogviszonyDokumentum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "dokumentum_nev", nullable = false)
    private String dokumentumNev;

    @Column(name = "leiras", length = 1024)
    private String leiras;

    @NotBlank
    @Column(name = "fajl_utvonal", nullable = false)
    private String fajlUtvonal;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "feltoltes_ideje", nullable = false)
    private Instant feltoltesIdeje = Instant.now();

    @NotBlank
    @Column(name = "dokumentum_tipus", nullable = false)
    private String dokumentumTipus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties(value = { "megrendeloCeg", "vallalkozoCeg" }, allowSetters = true)
    private SzerzodesesJogviszonyok szerzodesesJogviszony;

    public Long getId() {
        return this.id;
    }

    public SzerzodesesJogviszonyDokumentum id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumNev() {
        return this.dokumentumNev;
    }

    public SzerzodesesJogviszonyDokumentum dokumentumNev(String dokumentumNev) {
        this.setDokumentumNev(dokumentumNev);
        return this;
    }

    public void setDokumentumNev(String dokumentumNev) {
        this.dokumentumNev = dokumentumNev;
    }

    public String getLeiras() {
        return this.leiras;
    }

    public SzerzodesesJogviszonyDokumentum leiras(String leiras) {
        this.setLeiras(leiras);
        return this;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getFajlUtvonal() {
        return this.fajlUtvonal;
    }

    public SzerzodesesJogviszonyDokumentum fajlUtvonal(String fajlUtvonal) {
        this.setFajlUtvonal(fajlUtvonal);
        return this;
    }

    public void setFajlUtvonal(String fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public SzerzodesesJogviszonyDokumentum contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public Instant getFeltoltesIdeje() {
        return this.feltoltesIdeje;
    }

    public void setFeltoltesIdeje(Instant feltoltesIdeje) {
        this.feltoltesIdeje = feltoltesIdeje;
    }

    public SzerzodesesJogviszonyDokumentum feltoltesIdeje(Instant feltoltesIdeje) {
        this.setFeltoltesIdeje(feltoltesIdeje);
        return this;
    }

    public String getDokumentumTipus() {
        return this.dokumentumTipus;
    }

    public void setDokumentumTipus(String dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }

    public SzerzodesesJogviszonyDokumentum dokumentumTipus(String dokumentumTipus) {
        this.setDokumentumTipus(dokumentumTipus);
        return this;
    }

    public SzerzodesesJogviszonyok getSzerzodesesJogviszony() {
        return this.szerzodesesJogviszony;
    }

    public void setSzerzodesesJogviszony(SzerzodesesJogviszonyok szerzodesesJogviszony) {
        this.szerzodesesJogviszony = szerzodesesJogviszony;
    }

    public SzerzodesesJogviszonyDokumentum szerzodesesJogviszony(SzerzodesesJogviszonyok szerzodesesJogviszony) {
        this.setSzerzodesesJogviszony(szerzodesesJogviszony);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesesJogviszonyDokumentum)) {
            return false;
        }
        return getId() != null && getId().equals(((SzerzodesesJogviszonyDokumentum) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "SzerzodesesJogviszonyDokumentum{" +
            "id=" +
            getId() +
            ", dokumentumNev='" +
            getDokumentumNev() +
            '\'' +
            ", fajlUtvonal='" +
            getFajlUtvonal() +
            '\'' +
            ", contentType='" +
            getContentType() +
            '\'' +
            ", feltoltesIdeje=" +
            getFeltoltesIdeje() +
            '}'
        );
    }
}

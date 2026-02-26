package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Tárolja az egyes közgyűlési ülések generált és aláírt dokumentumait.
 */
@Entity
@Table(name = "osztalekfizetesi_kozgyulesek_dokumentum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OsztalekfizetesiKozgyulesekDokumentum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /** GENERALT_JEGYZOKONYV | ALAIRT_JEGYZOKONYV */
    @Column(name = "dokumentum_tipusa", length = 64, nullable = false)
    private String dokumentumTipusa;

    @Column(name = "dokumentum_nev", nullable = false)
    private String dokumentumNev;

    @Column(name = "fajl_nev", nullable = false)
    private String fajlNev;

    @Column(name = "dokumentum_url", length = 1000)
    private String dokumentumUrl;

    @Column(name = "dokumentum_azonosito", length = 50)
    private String dokumentumAzonosito;

    @Column(name = "feltoltes_ideje", nullable = false)
    private Instant feltoltesIdeje;

    @PrePersist
    protected void onCreate() {
        if (feltoltesIdeje == null) {
            feltoltesIdeje = Instant.now();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sajatCeg" }, allowSetters = true)
    private OsztalekfizetesiKozgyulesek kozgyules;

    // -------------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public OsztalekfizetesiKozgyulesekDokumentum id(Long id) {
        setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public OsztalekfizetesiKozgyulesekDokumentum dokumentumTipusa(String dokumentumTipusa) {
        setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentumNev() {
        return dokumentumNev;
    }

    public OsztalekfizetesiKozgyulesekDokumentum dokumentumNev(String dokumentumNev) {
        setDokumentumNev(dokumentumNev);
        return this;
    }

    public void setDokumentumNev(String dokumentumNev) {
        this.dokumentumNev = dokumentumNev;
    }

    public String getFajlNev() {
        return fajlNev;
    }

    public OsztalekfizetesiKozgyulesekDokumentum fajlNev(String fajlNev) {
        setFajlNev(fajlNev);
        return this;
    }

    public void setFajlNev(String fajlNev) {
        this.fajlNev = fajlNev;
    }

    public String getDokumentumUrl() {
        return dokumentumUrl;
    }

    public OsztalekfizetesiKozgyulesekDokumentum dokumentumUrl(String dokumentumUrl) {
        setDokumentumUrl(dokumentumUrl);
        return this;
    }

    public void setDokumentumUrl(String dokumentumUrl) {
        this.dokumentumUrl = dokumentumUrl;
    }

    public String getDokumentumAzonosito() {
        return dokumentumAzonosito;
    }

    public OsztalekfizetesiKozgyulesekDokumentum dokumentumAzonosito(String dokumentumAzonosito) {
        setDokumentumAzonosito(dokumentumAzonosito);
        return this;
    }

    public void setDokumentumAzonosito(String dokumentumAzonosito) {
        this.dokumentumAzonosito = dokumentumAzonosito;
    }

    public Instant getFeltoltesIdeje() {
        return feltoltesIdeje;
    }

    public OsztalekfizetesiKozgyulesekDokumentum feltoltesIdeje(Instant feltoltesIdeje) {
        setFeltoltesIdeje(feltoltesIdeje);
        return this;
    }

    public void setFeltoltesIdeje(Instant feltoltesIdeje) {
        this.feltoltesIdeje = feltoltesIdeje;
    }

    public OsztalekfizetesiKozgyulesek getKozgyules() {
        return kozgyules;
    }

    public OsztalekfizetesiKozgyulesekDokumentum kozgyules(OsztalekfizetesiKozgyulesek kozgyules) {
        setKozgyules(kozgyules);
        return this;
    }

    public void setKozgyules(OsztalekfizetesiKozgyulesek kozgyules) {
        this.kozgyules = kozgyules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OsztalekfizetesiKozgyulesekDokumentum)) return false;
        return getId() != null && getId().equals(((OsztalekfizetesiKozgyulesekDokumentum) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsztalekfizetesiKozgyulesekDokumentum{" +
            "id=" + getId() +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", dokumentumNev='" + getDokumentumNev() + "'" +
            ", fajlNev='" + getFajlNev() + "'" +
            ", dokumentumAzonosito='" + getDokumentumAzonosito() + "'" +
            ", feltoltesIdeje='" + getFeltoltesIdeje() + "'" +
            "}";
    }
}

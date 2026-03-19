package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlvallalkozoiElszamolasok.
 */
@Entity
@Table(name = "alvallalkozoi_elszamolasok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlvallalkozoiElszamolasok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "teljesitesi_idoszak_kezdete")
    private LocalDate teljesitesiIdoszakKezdete;

    @Column(name = "teljesitesi_idoszak_vege")
    private LocalDate teljesitesiIdoszakVege;

    @Column(name = "napok_szama", precision = 10, scale = 2)
    private BigDecimal napokSzama;

    @Column(name = "teljesites_igazolason_szereplo_osszeg", precision = 21, scale = 2)
    private BigDecimal teljesitesIgazolasonSzereploOsszeg;

    @Column(name = "bejovo_szamla_sorszam_rogzitve")
    private Boolean bejovoSzamlaSorszamRogzitve;

    @Column(name = "bejovo_szamla_sorszam")
    private String bejovoSzamlaSorszam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "szerzodesesJogviszony", "maganszemely" }, allowSetters = true)
    private Megrendelesek megrendeles;

    public Long getId() {
        return this.id;
    }

    public AlvallalkozoiElszamolasok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTeljesitesiIdoszakKezdete() {
        return this.teljesitesiIdoszakKezdete;
    }

    public AlvallalkozoiElszamolasok teljesitesiIdoszakKezdete(LocalDate teljesitesiIdoszakKezdete) {
        this.setTeljesitesiIdoszakKezdete(teljesitesiIdoszakKezdete);
        return this;
    }

    public void setTeljesitesiIdoszakKezdete(LocalDate teljesitesiIdoszakKezdete) {
        this.teljesitesiIdoszakKezdete = teljesitesiIdoszakKezdete;
    }

    public LocalDate getTeljesitesiIdoszakVege() {
        return this.teljesitesiIdoszakVege;
    }

    public AlvallalkozoiElszamolasok teljesitesiIdoszakVege(LocalDate teljesitesiIdoszakVege) {
        this.setTeljesitesiIdoszakVege(teljesitesiIdoszakVege);
        return this;
    }

    public void setTeljesitesiIdoszakVege(LocalDate teljesitesiIdoszakVege) {
        this.teljesitesiIdoszakVege = teljesitesiIdoszakVege;
    }

    public BigDecimal getNapokSzama() {
        return this.napokSzama;
    }

    public AlvallalkozoiElszamolasok napokSzama(BigDecimal napokSzama) {
        this.setNapokSzama(napokSzama);
        return this;
    }

    public void setNapokSzama(BigDecimal napokSzama) {
        this.napokSzama = napokSzama;
    }

    public BigDecimal getTeljesitesIgazolasonSzereploOsszeg() {
        return this.teljesitesIgazolasonSzereploOsszeg;
    }

    public AlvallalkozoiElszamolasok teljesitesIgazolasonSzereploOsszeg(BigDecimal teljesitesIgazolasonSzereploOsszeg) {
        this.setTeljesitesIgazolasonSzereploOsszeg(teljesitesIgazolasonSzereploOsszeg);
        return this;
    }

    public void setTeljesitesIgazolasonSzereploOsszeg(BigDecimal teljesitesIgazolasonSzereploOsszeg) {
        this.teljesitesIgazolasonSzereploOsszeg = teljesitesIgazolasonSzereploOsszeg;
    }

    public Boolean getBejovoSzamlaSorszamRogzitve() {
        return this.bejovoSzamlaSorszamRogzitve;
    }

    public AlvallalkozoiElszamolasok bejovoSzamlaSorszamRogzitve(Boolean bejovoSzamlaSorszamRogzitve) {
        this.setBejovoSzamlaSorszamRogzitve(bejovoSzamlaSorszamRogzitve);
        return this;
    }

    public void setBejovoSzamlaSorszamRogzitve(Boolean bejovoSzamlaSorszamRogzitve) {
        this.bejovoSzamlaSorszamRogzitve = bejovoSzamlaSorszamRogzitve;
    }

    public String getBejovoSzamlaSorszam() {
        return this.bejovoSzamlaSorszam;
    }

    public AlvallalkozoiElszamolasok bejovoSzamlaSorszam(String bejovoSzamlaSorszam) {
        this.setBejovoSzamlaSorszam(bejovoSzamlaSorszam);
        return this;
    }

    public void setBejovoSzamlaSorszam(String bejovoSzamlaSorszam) {
        this.bejovoSzamlaSorszam = bejovoSzamlaSorszam;
    }

    public Megrendelesek getMegrendeles() {
        return this.megrendeles;
    }

    public void setMegrendeles(Megrendelesek megrendelesek) {
        this.megrendeles = megrendelesek;
    }

    public AlvallalkozoiElszamolasok megrendeles(Megrendelesek megrendelesek) {
        this.setMegrendeles(megrendelesek);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlvallalkozoiElszamolasok)) return false;
        return getId() != null && getId().equals(((AlvallalkozoiElszamolasok) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "AlvallalkozoiElszamolasok{" +
            "id=" + getId() +
            ", teljesitesiIdoszakKezdete='" + getTeljesitesiIdoszakKezdete() + "'" +
            ", teljesitesiIdoszakVege='" + getTeljesitesiIdoszakVege() + "'" +
            ", napokSzama=" + getNapokSzama() +
            ", teljesitesIgazolasonSzereploOsszeg=" + getTeljesitesIgazolasonSzereploOsszeg() +
            ", bejovoSzamlaSorszam='" + getBejovoSzamlaSorszam() + "'" +
            "}";
    }
}

package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UgyfelElszamolasok.
 */
@Entity
@Table(name = "ugyfel_elszamolasok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UgyfelElszamolasok implements Serializable {

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

    @Column(name = "napok_szama")
    private Integer napokSzama;

    @Column(name = "teljesites_igazolason_szereplo_osszeg", precision = 21, scale = 2)
    private BigDecimal teljesitesIgazolasonSzereploOsszeg;

    @Column(name = "kapcsolodo_szamla_sorszam_rogzitve")
    private Boolean kapcsolodoSzamlaSorszamRogzitve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "szerzodesesJogviszony", "maganszemely" }, allowSetters = true)
    private Megrendelesek megrendeles;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UgyfelElszamolasok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTeljesitesiIdoszakKezdete() {
        return this.teljesitesiIdoszakKezdete;
    }

    public UgyfelElszamolasok teljesitesiIdoszakKezdete(LocalDate teljesitesiIdoszakKezdete) {
        this.setTeljesitesiIdoszakKezdete(teljesitesiIdoszakKezdete);
        return this;
    }

    public void setTeljesitesiIdoszakKezdete(LocalDate teljesitesiIdoszakKezdete) {
        this.teljesitesiIdoszakKezdete = teljesitesiIdoszakKezdete;
    }

    public LocalDate getTeljesitesiIdoszakVege() {
        return this.teljesitesiIdoszakVege;
    }

    public UgyfelElszamolasok teljesitesiIdoszakVege(LocalDate teljesitesiIdoszakVege) {
        this.setTeljesitesiIdoszakVege(teljesitesiIdoszakVege);
        return this;
    }

    public void setTeljesitesiIdoszakVege(LocalDate teljesitesiIdoszakVege) {
        this.teljesitesiIdoszakVege = teljesitesiIdoszakVege;
    }

    public Integer getNapokSzama() {
        return this.napokSzama;
    }

    public UgyfelElszamolasok napokSzama(Integer napokSzama) {
        this.setNapokSzama(napokSzama);
        return this;
    }

    public void setNapokSzama(Integer napokSzama) {
        this.napokSzama = napokSzama;
    }

    public BigDecimal getTeljesitesIgazolasonSzereploOsszeg() {
        return this.teljesitesIgazolasonSzereploOsszeg;
    }

    public UgyfelElszamolasok teljesitesIgazolasonSzereploOsszeg(BigDecimal teljesitesIgazolasonSzereploOsszeg) {
        this.setTeljesitesIgazolasonSzereploOsszeg(teljesitesIgazolasonSzereploOsszeg);
        return this;
    }

    public void setTeljesitesIgazolasonSzereploOsszeg(BigDecimal teljesitesIgazolasonSzereploOsszeg) {
        this.teljesitesIgazolasonSzereploOsszeg = teljesitesIgazolasonSzereploOsszeg;
    }

    public Boolean getKapcsolodoSzamlaSorszamRogzitve() {
        return this.kapcsolodoSzamlaSorszamRogzitve;
    }

    public UgyfelElszamolasok kapcsolodoSzamlaSorszamRogzitve(Boolean kapcsolodoSzamlaSorszamRogzitve) {
        this.setKapcsolodoSzamlaSorszamRogzitve(kapcsolodoSzamlaSorszamRogzitve);
        return this;
    }

    public void setKapcsolodoSzamlaSorszamRogzitve(Boolean kapcsolodoSzamlaSorszamRogzitve) {
        this.kapcsolodoSzamlaSorszamRogzitve = kapcsolodoSzamlaSorszamRogzitve;
    }

    public Megrendelesek getMegrendeles() {
        return this.megrendeles;
    }

    public void setMegrendeles(Megrendelesek megrendelesek) {
        this.megrendeles = megrendelesek;
    }

    public UgyfelElszamolasok megrendeles(Megrendelesek megrendelesek) {
        this.setMegrendeles(megrendelesek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UgyfelElszamolasok)) {
            return false;
        }
        return getId() != null && getId().equals(((UgyfelElszamolasok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UgyfelElszamolasok{" +
            "id=" + getId() +
            ", teljesitesiIdoszakKezdete='" + getTeljesitesiIdoszakKezdete() + "'" +
            ", teljesitesiIdoszakVege='" + getTeljesitesiIdoszakVege() + "'" +
            ", napokSzama=" + getNapokSzama() +
            ", teljesitesIgazolasonSzereploOsszeg=" + getTeljesitesIgazolasonSzereploOsszeg() +
            ", kapcsolodoSzamlaSorszamRogzitve='" + getKapcsolodoSzamlaSorszamRogzitve() + "'" +
            "}";
    }
}

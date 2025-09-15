package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Megrendelesek.
 */
@Entity
@Table(name = "megrendelesek")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Megrendelesek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "megrendeles_tipusa")
    private String megrendelesTipusa;

    @Column(name = "feladat_rovid_leirasa")
    private String feladatRovidLeirasa;

    @Column(name = "feladat_reszletes_leirasa")
    private String feladatReszletesLeirasa;

    @Column(name = "megrendeles_kezdete")
    private LocalDate megrendelesKezdete;

    @Column(name = "megrendeles_vege")
    private LocalDate megrendelesVege;

    @Column(name = "resztvevo_kollaga_tipusa")
    private String resztvevoKollagaTipusa;

    @Column(name = "devizanem")
    private String devizanem;

    @Column(name = "dijazas_tipusa")
    private String dijazasTipusa;

    @Column(name = "dij_osszege", precision = 21, scale = 2)
    private BigDecimal dijOsszege;

    @Column(name = "megrendeles_dokumentum_generalta")
    private Boolean megrendelesDokumentumGeneralta;

    @Column(name = "ugyfel_megrendeles_id")
    private Long ugyfelMegrendelesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "megrendeloCeg", "vallalkozoCeg" }, allowSetters = true)
    private SzerzodesesJogviszonyok szerzodesesJogviszony;

    @ManyToOne(fetch = FetchType.LAZY)
    private Maganszemelyek maganszemely;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Megrendelesek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMegrendelesTipusa() {
        return this.megrendelesTipusa;
    }

    public Megrendelesek megrendelesTipusa(String megrendelesTipusa) {
        this.setMegrendelesTipusa(megrendelesTipusa);
        return this;
    }

    public void setMegrendelesTipusa(String megrendelesTipusa) {
        this.megrendelesTipusa = megrendelesTipusa;
    }

    public String getFeladatRovidLeirasa() {
        return this.feladatRovidLeirasa;
    }

    public Megrendelesek feladatRovidLeirasa(String feladatRovidLeirasa) {
        this.setFeladatRovidLeirasa(feladatRovidLeirasa);
        return this;
    }

    public void setFeladatRovidLeirasa(String feladatRovidLeirasa) {
        this.feladatRovidLeirasa = feladatRovidLeirasa;
    }

    public String getFeladatReszletesLeirasa() {
        return this.feladatReszletesLeirasa;
    }

    public Megrendelesek feladatReszletesLeirasa(String feladatReszletesLeirasa) {
        this.setFeladatReszletesLeirasa(feladatReszletesLeirasa);
        return this;
    }

    public void setFeladatReszletesLeirasa(String feladatReszletesLeirasa) {
        this.feladatReszletesLeirasa = feladatReszletesLeirasa;
    }

    public LocalDate getMegrendelesKezdete() {
        return this.megrendelesKezdete;
    }

    public Megrendelesek megrendelesKezdete(LocalDate megrendelesKezdete) {
        this.setMegrendelesKezdete(megrendelesKezdete);
        return this;
    }

    public void setMegrendelesKezdete(LocalDate megrendelesKezdete) {
        this.megrendelesKezdete = megrendelesKezdete;
    }

    public LocalDate getMegrendelesVege() {
        return this.megrendelesVege;
    }

    public Megrendelesek megrendelesVege(LocalDate megrendelesVege) {
        this.setMegrendelesVege(megrendelesVege);
        return this;
    }

    public void setMegrendelesVege(LocalDate megrendelesVege) {
        this.megrendelesVege = megrendelesVege;
    }

    public String getResztvevoKollagaTipusa() {
        return this.resztvevoKollagaTipusa;
    }

    public Megrendelesek resztvevoKollagaTipusa(String resztvevoKollagaTipusa) {
        this.setResztvevoKollagaTipusa(resztvevoKollagaTipusa);
        return this;
    }

    public void setResztvevoKollagaTipusa(String resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
    }

    public String getDevizanem() {
        return this.devizanem;
    }

    public Megrendelesek devizanem(String devizanem) {
        this.setDevizanem(devizanem);
        return this;
    }

    public void setDevizanem(String devizanem) {
        this.devizanem = devizanem;
    }

    public String getDijazasTipusa() {
        return this.dijazasTipusa;
    }

    public Megrendelesek dijazasTipusa(String dijazasTipusa) {
        this.setDijazasTipusa(dijazasTipusa);
        return this;
    }

    public void setDijazasTipusa(String dijazasTipusa) {
        this.dijazasTipusa = dijazasTipusa;
    }

    public BigDecimal getDijOsszege() {
        return this.dijOsszege;
    }

    public Megrendelesek dijOsszege(BigDecimal dijOsszege) {
        this.setDijOsszege(dijOsszege);
        return this;
    }

    public void setDijOsszege(BigDecimal dijOsszege) {
        this.dijOsszege = dijOsszege;
    }

    public Boolean getMegrendelesDokumentumGeneralta() {
        return this.megrendelesDokumentumGeneralta;
    }

    public Megrendelesek megrendelesDokumentumGeneralta(Boolean megrendelesDokumentumGeneralta) {
        this.setMegrendelesDokumentumGeneralta(megrendelesDokumentumGeneralta);
        return this;
    }

    public void setMegrendelesDokumentumGeneralta(Boolean megrendelesDokumentumGeneralta) {
        this.megrendelesDokumentumGeneralta = megrendelesDokumentumGeneralta;
    }

    public Long getUgyfelMegrendelesId() {
        return this.ugyfelMegrendelesId;
    }

    public Megrendelesek ugyfelMegrendelesId(Long ugyfelMegrendelesId) {
        this.setUgyfelMegrendelesId(ugyfelMegrendelesId);
        return this;
    }

    public void setUgyfelMegrendelesId(Long ugyfelMegrendelesId) {
        this.ugyfelMegrendelesId = ugyfelMegrendelesId;
    }

    public SzerzodesesJogviszonyok getSzerzodesesJogviszony() {
        return this.szerzodesesJogviszony;
    }

    public void setSzerzodesesJogviszony(SzerzodesesJogviszonyok szerzodesesJogviszonyok) {
        this.szerzodesesJogviszony = szerzodesesJogviszonyok;
    }

    public Megrendelesek szerzodesesJogviszony(SzerzodesesJogviszonyok szerzodesesJogviszonyok) {
        this.setSzerzodesesJogviszony(szerzodesesJogviszonyok);
        return this;
    }

    public Maganszemelyek getMaganszemely() {
        return this.maganszemely;
    }

    public void setMaganszemely(Maganszemelyek maganszemelyek) {
        this.maganszemely = maganszemelyek;
    }

    public Megrendelesek maganszemely(Maganszemelyek maganszemelyek) {
        this.setMaganszemely(maganszemelyek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Megrendelesek)) {
            return false;
        }
        return getId() != null && getId().equals(((Megrendelesek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Megrendelesek{" +
            "id=" + getId() +
            ", megrendelesTipusa='" + getMegrendelesTipusa() + "'" +
            ", feladatRovidLeirasa='" + getFeladatRovidLeirasa() + "'" +
            ", feladatReszletesLeirasa='" + getFeladatReszletesLeirasa() + "'" +
            ", megrendelesKezdete='" + getMegrendelesKezdete() + "'" +
            ", megrendelesVege='" + getMegrendelesVege() + "'" +
            ", resztvevoKollagaTipusa='" + getResztvevoKollagaTipusa() + "'" +
            ", devizanem='" + getDevizanem() + "'" +
            ", dijazasTipusa='" + getDijazasTipusa() + "'" +
            ", dijOsszege=" + getDijOsszege() +
            ", megrendelesDokumentumGeneralta='" + getMegrendelesDokumentumGeneralta() + "'" +
            ", ugyfelMegrendelesId=" + getUgyfelMegrendelesId() +
            "}";
    }
}

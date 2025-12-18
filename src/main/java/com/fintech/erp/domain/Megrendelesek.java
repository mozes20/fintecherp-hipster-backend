package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "megrendeles_tipusa")
    private MegrendelesTipus megrendelesTipusa;

    @Column(name = "feladat_rovid_leirasa")
    private String feladatRovidLeirasa;

    @Column(name = "feladat_reszletes_leirasa")
    private String feladatReszletesLeirasa;

    @Column(name = "szallitasra_kerulo_tetelek", columnDefinition = "text")
    private String szallitasraKeruloTetelek;

    @Column(name = "megrendeles_kezdete")
    private LocalDate megrendelesKezdete;

    @Column(name = "megrendeles_vege")
    private LocalDate megrendelesVege;

    @Column(name = "megrendeles_datuma")
    private LocalDate megrendelesDatuma;

    @Enumerated(EnumType.STRING)
    @Column(name = "resztvevo_kollaga_tipusa")
    private ResztvevoKollagaTipus resztvevoKollagaTipusa;

    @Enumerated(EnumType.STRING)
    @Column(name = "devizanem")
    private Devizanem devizanem;

    @Enumerated(EnumType.STRING)
    @Column(name = "dijazas_tipusa")
    private DijazasTipusa dijazasTipusa;

    @Column(name = "dij_osszege", precision = 21, scale = 2)
    private BigDecimal dijOsszege;

    @Enumerated(EnumType.STRING)
    @Column(name = "megrendeles_dokumentum_generalta")
    private MegrendelesDokumentumEredet megrendelesDokumentumGeneralta;

    @Column(name = "ugyfel_megrendeles_id")
    private Long ugyfelMegrendelesId;

    @Column(name = "megrendeles_szam", unique = true)
    private String megrendelesSzam;

    @Column(name = "munkakor_id")
    private Long munkakorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "munkakor_id", insertable = false, updatable = false)
    private Munkakorok munkakor;

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

    public MegrendelesTipus getMegrendelesTipusa() {
        return this.megrendelesTipusa;
    }

    public Megrendelesek megrendelesTipusa(MegrendelesTipus megrendelesTipusa) {
        this.setMegrendelesTipusa(megrendelesTipusa);
        return this;
    }

    public void setMegrendelesTipusa(MegrendelesTipus megrendelesTipusa) {
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

    public String getSzallitasraKeruloTetelek() {
        return this.szallitasraKeruloTetelek;
    }

    public Megrendelesek szallitasraKeruloTetelek(String szallitasraKeruloTetelek) {
        this.setSzallitasraKeruloTetelek(szallitasraKeruloTetelek);
        return this;
    }

    public void setSzallitasraKeruloTetelek(String szallitasraKeruloTetelek) {
        this.szallitasraKeruloTetelek = szallitasraKeruloTetelek;
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

    public LocalDate getMegrendelesDatuma() {
        return this.megrendelesDatuma;
    }

    public Megrendelesek megrendelesDatuma(LocalDate megrendelesDatuma) {
        this.setMegrendelesDatuma(megrendelesDatuma);
        return this;
    }

    public void setMegrendelesDatuma(LocalDate megrendelesDatuma) {
        this.megrendelesDatuma = megrendelesDatuma;
    }

    public ResztvevoKollagaTipus getResztvevoKollagaTipusa() {
        return this.resztvevoKollagaTipusa;
    }

    public Megrendelesek resztvevoKollagaTipusa(ResztvevoKollagaTipus resztvevoKollagaTipusa) {
        this.setResztvevoKollagaTipusa(resztvevoKollagaTipusa);
        return this;
    }

    public void setResztvevoKollagaTipusa(ResztvevoKollagaTipus resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
    }

    public Devizanem getDevizanem() {
        return this.devizanem;
    }

    public Megrendelesek devizanem(Devizanem devizanem) {
        this.setDevizanem(devizanem);
        return this;
    }

    public void setDevizanem(Devizanem devizanem) {
        this.devizanem = devizanem;
    }

    public DijazasTipusa getDijazasTipusa() {
        return this.dijazasTipusa;
    }

    public Megrendelesek dijazasTipusa(DijazasTipusa dijazasTipusa) {
        this.setDijazasTipusa(dijazasTipusa);
        return this;
    }

    public void setDijazasTipusa(DijazasTipusa dijazasTipusa) {
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

    public MegrendelesDokumentumEredet getMegrendelesDokumentumGeneralta() {
        return this.megrendelesDokumentumGeneralta;
    }

    public Megrendelesek megrendelesDokumentumGeneralta(MegrendelesDokumentumEredet megrendelesDokumentumGeneralta) {
        this.setMegrendelesDokumentumGeneralta(megrendelesDokumentumGeneralta);
        return this;
    }

    public void setMegrendelesDokumentumGeneralta(MegrendelesDokumentumEredet megrendelesDokumentumGeneralta) {
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

    public String getMegrendelesSzam() {
        return this.megrendelesSzam;
    }

    public Megrendelesek megrendelesSzam(String megrendelesSzam) {
        this.setMegrendelesSzam(megrendelesSzam);
        return this;
    }

    public void setMegrendelesSzam(String megrendelesSzam) {
        this.megrendelesSzam = megrendelesSzam;
    }

    public Long getMunkakorId() {
        return this.munkakorId;
    }

    public Megrendelesek munkakorId(Long munkakorId) {
        this.setMunkakorId(munkakorId);
        return this;
    }

    public void setMunkakorId(Long munkakorId) {
        this.munkakorId = munkakorId;
    }

    public Munkakorok getMunkakor() {
        return this.munkakor;
    }

    public void setMunkakor(Munkakorok munkakor) {
        this.munkakor = munkakor;
    }

    public Megrendelesek munkakor(Munkakorok munkakor) {
        this.setMunkakor(munkakor);
        return this;
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
            ", szallitasraKeruloTetelek='" + getSzallitasraKeruloTetelek() + "'" +
            ", megrendelesKezdete='" + getMegrendelesKezdete() + "'" +
            ", megrendelesVege='" + getMegrendelesVege() + "'" +
            ", megrendelesDatuma='" + getMegrendelesDatuma() + "'" +
            ", resztvevoKollagaTipusa='" + getResztvevoKollagaTipusa() + "'" +
            ", devizanem='" + getDevizanem() + "'" +
            ", dijazasTipusa='" + getDijazasTipusa() + "'" +
            ", dijOsszege=" + getDijOsszege() +
            ", megrendelesDokumentumGeneralta='" + getMegrendelesDokumentumGeneralta() + "'" +
            ", ugyfelMegrendelesId=" + getUgyfelMegrendelesId() +
            ", megrendelesSzam='" + getMegrendelesSzam() + "'" +
            ", munkakorId=" + getMunkakorId() +
            "}";
    }
}

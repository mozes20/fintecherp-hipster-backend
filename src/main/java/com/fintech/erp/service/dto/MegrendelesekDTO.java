package com.fintech.erp.service.dto;

import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesForras;
import com.fintech.erp.domain.enumeration.MegrendelesStatusz;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
import com.fintech.erp.domain.enumeration.ResztvevoTipus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Megrendelesek} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesekDTO implements Serializable {

    private Long id;

    private MegrendelesTipus megrendelesTipusa;

    private String feladatRovidLeirasa;

    private String feladatReszletesLeirasa;

    private LocalDate megrendelesKezdete;

    private LocalDate megrendelesVege;

    private ResztvevoKollagaTipus resztvevoKollagaTipusa;

    private ResztvevoTipus resztvevoTipus;

    private Devizanem devizanem;

    private DijazasTipusa dijazasTipusa;

    private BigDecimal dijOsszege;

    private MegrendelesDokumentumEredet megrendelesDokumentumGeneralta;

    private Long ugyfelMegrendelesId;

    private String megrendelesSzam;

    private MegrendelesStatusz megrendelesStatusz;

    private MegrendelesForras megrendelesForrasa;

    private Integer peldanyokSzama;

    private Boolean szamlazando;

    private SzerzodesesJogviszonyokDTO szerzodesesJogviszony;

    private MaganszemelyekDTO maganszemely;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MegrendelesTipus getMegrendelesTipusa() {
        return megrendelesTipusa;
    }

    public void setMegrendelesTipusa(MegrendelesTipus megrendelesTipusa) {
        this.megrendelesTipusa = megrendelesTipusa;
    }

    public String getFeladatRovidLeirasa() {
        return feladatRovidLeirasa;
    }

    public void setFeladatRovidLeirasa(String feladatRovidLeirasa) {
        this.feladatRovidLeirasa = feladatRovidLeirasa;
    }

    public String getFeladatReszletesLeirasa() {
        return feladatReszletesLeirasa;
    }

    public void setFeladatReszletesLeirasa(String feladatReszletesLeirasa) {
        this.feladatReszletesLeirasa = feladatReszletesLeirasa;
    }

    public LocalDate getMegrendelesKezdete() {
        return megrendelesKezdete;
    }

    public void setMegrendelesKezdete(LocalDate megrendelesKezdete) {
        this.megrendelesKezdete = megrendelesKezdete;
    }

    public LocalDate getMegrendelesVege() {
        return megrendelesVege;
    }

    public void setMegrendelesVege(LocalDate megrendelesVege) {
        this.megrendelesVege = megrendelesVege;
    }

    public ResztvevoKollagaTipus getResztvevoKollagaTipusa() {
        return resztvevoKollagaTipusa;
    }

    public void setResztvevoKollagaTipusa(ResztvevoKollagaTipus resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
    }

    public ResztvevoTipus getResztvevoTipus() {
        return resztvevoTipus;
    }

    public void setResztvevoTipus(ResztvevoTipus resztvevoTipus) {
        this.resztvevoTipus = resztvevoTipus;
    }

    public Devizanem getDevizanem() {
        return devizanem;
    }

    public void setDevizanem(Devizanem devizanem) {
        this.devizanem = devizanem;
    }

    public DijazasTipusa getDijazasTipusa() {
        return dijazasTipusa;
    }

    public void setDijazasTipusa(DijazasTipusa dijazasTipusa) {
        this.dijazasTipusa = dijazasTipusa;
    }

    public BigDecimal getDijOsszege() {
        return dijOsszege;
    }

    public void setDijOsszege(BigDecimal dijOsszege) {
        this.dijOsszege = dijOsszege;
    }

    public MegrendelesDokumentumEredet getMegrendelesDokumentumGeneralta() {
        return megrendelesDokumentumGeneralta;
    }

    public void setMegrendelesDokumentumGeneralta(MegrendelesDokumentumEredet megrendelesDokumentumGeneralta) {
        this.megrendelesDokumentumGeneralta = megrendelesDokumentumGeneralta;
    }

    public Long getUgyfelMegrendelesId() {
        return ugyfelMegrendelesId;
    }

    public void setUgyfelMegrendelesId(Long ugyfelMegrendelesId) {
        this.ugyfelMegrendelesId = ugyfelMegrendelesId;
    }

    public String getMegrendelesSzam() {
        return megrendelesSzam;
    }

    public void setMegrendelesSzam(String megrendelesSzam) {
        this.megrendelesSzam = megrendelesSzam;
    }

    public MegrendelesStatusz getMegrendelesStatusz() {
        return megrendelesStatusz;
    }

    public void setMegrendelesStatusz(MegrendelesStatusz megrendelesStatusz) {
        this.megrendelesStatusz = megrendelesStatusz;
    }

    public MegrendelesForras getMegrendelesForrasa() {
        return megrendelesForrasa;
    }

    public void setMegrendelesForrasa(MegrendelesForras megrendelesForrasa) {
        this.megrendelesForrasa = megrendelesForrasa;
    }

    public Integer getPeldanyokSzama() {
        return peldanyokSzama;
    }

    public void setPeldanyokSzama(Integer peldanyokSzama) {
        this.peldanyokSzama = peldanyokSzama;
    }

    public Boolean getSzamlazando() {
        return szamlazando;
    }

    public void setSzamlazando(Boolean szamlazando) {
        this.szamlazando = szamlazando;
    }

    public SzerzodesesJogviszonyokDTO getSzerzodesesJogviszony() {
        return szerzodesesJogviszony;
    }

    public void setSzerzodesesJogviszony(SzerzodesesJogviszonyokDTO szerzodesesJogviszony) {
        this.szerzodesesJogviszony = szerzodesesJogviszony;
    }

    public MaganszemelyekDTO getMaganszemely() {
        return maganszemely;
    }

    public void setMaganszemely(MaganszemelyekDTO maganszemely) {
        this.maganszemely = maganszemely;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MegrendelesekDTO)) {
            return false;
        }

        MegrendelesekDTO megrendelesekDTO = (MegrendelesekDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, megrendelesekDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MegrendelesekDTO{" +
            "id=" + getId() +
            ", megrendelesTipusa='" + getMegrendelesTipusa() + "'" +
            ", feladatRovidLeirasa='" + getFeladatRovidLeirasa() + "'" +
            ", feladatReszletesLeirasa='" + getFeladatReszletesLeirasa() + "'" +
            ", megrendelesKezdete='" + getMegrendelesKezdete() + "'" +
            ", megrendelesVege='" + getMegrendelesVege() + "'" +
            ", resztvevoKollagaTipusa='" + getResztvevoKollagaTipusa() + "'" +
            ", resztvevoTipus='" + getResztvevoTipus() + "'" +
            ", devizanem='" + getDevizanem() + "'" +
            ", dijazasTipusa='" + getDijazasTipusa() + "'" +
            ", dijOsszege=" + getDijOsszege() +
            ", megrendelesDokumentumGeneralta='" + getMegrendelesDokumentumGeneralta() + "'" +
            ", ugyfelMegrendelesId=" + getUgyfelMegrendelesId() +
            ", megrendelesSzam='" + getMegrendelesSzam() + "'" +
            ", megrendelesStatusz='" + getMegrendelesStatusz() + "'" +
            ", megrendelesForrasa='" + getMegrendelesForrasa() + "'" +
            ", peldanyokSzama=" + getPeldanyokSzama() +
            ", szamlazando='" + getSzamlazando() + "'" +
            ", szerzodesesJogviszony=" + getSzerzodesesJogviszony() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

package com.fintech.erp.service.dto;

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

    private String megrendelesTipusa;

    private String feladatRovidLeirasa;

    private String feladatReszletesLeirasa;

    private LocalDate megrendelesKezdete;

    private LocalDate megrendelesVege;

    private String resztvevoKollagaTipusa;

    private String devizanem;

    private String dijazasTipusa;

    private BigDecimal dijOsszege;

    private Boolean megrendelesDokumentumGeneralta;

    private Long ugyfelMegrendelesId;

    private SzerzodesesJogviszonyokDTO szerzodesesJogviszony;

    private MaganszemelyekDTO maganszemely;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMegrendelesTipusa() {
        return megrendelesTipusa;
    }

    public void setMegrendelesTipusa(String megrendelesTipusa) {
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

    public String getResztvevoKollagaTipusa() {
        return resztvevoKollagaTipusa;
    }

    public void setResztvevoKollagaTipusa(String resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
    }

    public String getDevizanem() {
        return devizanem;
    }

    public void setDevizanem(String devizanem) {
        this.devizanem = devizanem;
    }

    public String getDijazasTipusa() {
        return dijazasTipusa;
    }

    public void setDijazasTipusa(String dijazasTipusa) {
        this.dijazasTipusa = dijazasTipusa;
    }

    public BigDecimal getDijOsszege() {
        return dijOsszege;
    }

    public void setDijOsszege(BigDecimal dijOsszege) {
        this.dijOsszege = dijOsszege;
    }

    public Boolean getMegrendelesDokumentumGeneralta() {
        return megrendelesDokumentumGeneralta;
    }

    public void setMegrendelesDokumentumGeneralta(Boolean megrendelesDokumentumGeneralta) {
        this.megrendelesDokumentumGeneralta = megrendelesDokumentumGeneralta;
    }

    public Long getUgyfelMegrendelesId() {
        return ugyfelMegrendelesId;
    }

    public void setUgyfelMegrendelesId(Long ugyfelMegrendelesId) {
        this.ugyfelMegrendelesId = ugyfelMegrendelesId;
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
            ", devizanem='" + getDevizanem() + "'" +
            ", dijazasTipusa='" + getDijazasTipusa() + "'" +
            ", dijOsszege=" + getDijOsszege() +
            ", megrendelesDokumentumGeneralta='" + getMegrendelesDokumentumGeneralta() + "'" +
            ", ugyfelMegrendelesId=" + getUgyfelMegrendelesId() +
            ", szerzodesesJogviszony=" + getSzerzodesesJogviszony() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

package com.fintech.erp.service.dto;

import com.fintech.erp.domain.enumeration.Devizanem;
import com.fintech.erp.domain.enumeration.DijazasTipusa;
import com.fintech.erp.domain.enumeration.MegrendelesDokumentumEredet;
import com.fintech.erp.domain.enumeration.MegrendelesTipus;
import com.fintech.erp.domain.enumeration.ResztvevoKollagaTipus;
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

    private String szallitasraKeruloTetelek;

    private LocalDate megrendelesKezdete;

    private LocalDate megrendelesVege;

    private LocalDate megrendelesDatuma;

    private ResztvevoKollagaTipus resztvevoKollagaTipusa;

    private Devizanem devizanem;

    private DijazasTipusa dijazasTipusa;

    private BigDecimal dijOsszege;

    private MegrendelesDokumentumEredet megrendelesDokumentumGeneralta;

    private Long ugyfelMegrendelesId;

    private String megrendelesSzam;

    private MunkakorokDTO munkakor;

    private Long munkakorId;

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

    public String getSzallitasraKeruloTetelek() {
        return szallitasraKeruloTetelek;
    }

    public void setSzallitasraKeruloTetelek(String szallitasraKeruloTetelek) {
        this.szallitasraKeruloTetelek = szallitasraKeruloTetelek;
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

    public LocalDate getMegrendelesDatuma() {
        return megrendelesDatuma;
    }

    public void setMegrendelesDatuma(LocalDate megrendelesDatuma) {
        this.megrendelesDatuma = megrendelesDatuma;
    }

    public ResztvevoKollagaTipus getResztvevoKollagaTipusa() {
        return resztvevoKollagaTipusa;
    }

    public void setResztvevoKollagaTipusa(ResztvevoKollagaTipus resztvevoKollagaTipusa) {
        this.resztvevoKollagaTipusa = resztvevoKollagaTipusa;
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

    public Long getMunkakorId() {
        return munkakorId;
    }

    public void setMunkakorId(Long munkakorId) {
        this.munkakorId = munkakorId;
    }

    public MunkakorokDTO getMunkakor() {
        return munkakor;
    }

    public void setMunkakor(MunkakorokDTO munkakor) {
        this.munkakor = munkakor;
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
            ", munkakor=" + getMunkakor() +
            ", szerzodesesJogviszony=" + getSzerzodesesJogviszony() +
            ", maganszemely=" + getMaganszemely() +
            "}";
    }
}

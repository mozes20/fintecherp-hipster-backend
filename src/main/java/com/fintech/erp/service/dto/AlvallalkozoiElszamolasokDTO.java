package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.AlvallalkozoiElszamolasok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlvallalkozoiElszamolasokDTO implements Serializable {

    private Long id;

    private LocalDate teljesitesiIdoszakKezdete;

    private LocalDate teljesitesiIdoszakVege;

    private BigDecimal napokSzama;

    private BigDecimal teljesitesIgazolasonSzereploOsszeg;

    private Boolean bejovoSzamlaSorszamRogzitve;

    private String bejovoSzamlaSorszam;

    private MegrendelesekDTO megrendeles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTeljesitesiIdoszakKezdete() {
        return teljesitesiIdoszakKezdete;
    }

    public void setTeljesitesiIdoszakKezdete(LocalDate teljesitesiIdoszakKezdete) {
        this.teljesitesiIdoszakKezdete = teljesitesiIdoszakKezdete;
    }

    public LocalDate getTeljesitesiIdoszakVege() {
        return teljesitesiIdoszakVege;
    }

    public void setTeljesitesiIdoszakVege(LocalDate teljesitesiIdoszakVege) {
        this.teljesitesiIdoszakVege = teljesitesiIdoszakVege;
    }

    public BigDecimal getNapokSzama() {
        return napokSzama;
    }

    public void setNapokSzama(BigDecimal napokSzama) {
        this.napokSzama = napokSzama;
    }

    public BigDecimal getTeljesitesIgazolasonSzereploOsszeg() {
        return teljesitesIgazolasonSzereploOsszeg;
    }

    public void setTeljesitesIgazolasonSzereploOsszeg(BigDecimal teljesitesIgazolasonSzereploOsszeg) {
        this.teljesitesIgazolasonSzereploOsszeg = teljesitesIgazolasonSzereploOsszeg;
    }

    public Boolean getBejovoSzamlaSorszamRogzitve() {
        return bejovoSzamlaSorszamRogzitve;
    }

    public void setBejovoSzamlaSorszamRogzitve(Boolean bejovoSzamlaSorszamRogzitve) {
        this.bejovoSzamlaSorszamRogzitve = bejovoSzamlaSorszamRogzitve;
    }

    public String getBejovoSzamlaSorszam() {
        return bejovoSzamlaSorszam;
    }

    public void setBejovoSzamlaSorszam(String bejovoSzamlaSorszam) {
        this.bejovoSzamlaSorszam = bejovoSzamlaSorszam;
    }

    public MegrendelesekDTO getMegrendeles() {
        return megrendeles;
    }

    public void setMegrendeles(MegrendelesekDTO megrendeles) {
        this.megrendeles = megrendeles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlvallalkozoiElszamolasokDTO)) return false;
        AlvallalkozoiElszamolasokDTO that = (AlvallalkozoiElszamolasokDTO) o;
        if (this.id == null) return false;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "AlvallalkozoiElszamolasokDTO{" +
            "id=" + getId() +
            ", teljesitesiIdoszakKezdete='" + getTeljesitesiIdoszakKezdete() + "'" +
            ", teljesitesiIdoszakVege='" + getTeljesitesiIdoszakVege() + "'" +
            ", napokSzama=" + getNapokSzama() +
            ", teljesitesIgazolasonSzereploOsszeg=" + getTeljesitesIgazolasonSzereploOsszeg() +
            ", bejovoSzamlaSorszam='" + getBejovoSzamlaSorszam() + "'" +
            ", megrendeles=" + getMegrendeles() +
            "}";
    }
}

package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.UgyfelElszamolasok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UgyfelElszamolasokDTO implements Serializable {

    private Long id;

    private LocalDate teljesitesiIdoszakKezdete;

    private LocalDate teljesitesiIdoszakVege;

    private Integer napokSzama;

    private BigDecimal teljesitesIgazolasonSzereploOsszeg;

    private Boolean kapcsolodoSzamlaSorszamRogzitve;

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

    public Integer getNapokSzama() {
        return napokSzama;
    }

    public void setNapokSzama(Integer napokSzama) {
        this.napokSzama = napokSzama;
    }

    public BigDecimal getTeljesitesIgazolasonSzereploOsszeg() {
        return teljesitesIgazolasonSzereploOsszeg;
    }

    public void setTeljesitesIgazolasonSzereploOsszeg(BigDecimal teljesitesIgazolasonSzereploOsszeg) {
        this.teljesitesIgazolasonSzereploOsszeg = teljesitesIgazolasonSzereploOsszeg;
    }

    public Boolean getKapcsolodoSzamlaSorszamRogzitve() {
        return kapcsolodoSzamlaSorszamRogzitve;
    }

    public void setKapcsolodoSzamlaSorszamRogzitve(Boolean kapcsolodoSzamlaSorszamRogzitve) {
        this.kapcsolodoSzamlaSorszamRogzitve = kapcsolodoSzamlaSorszamRogzitve;
    }

    public MegrendelesekDTO getMegrendeles() {
        return megrendeles;
    }

    public void setMegrendeles(MegrendelesekDTO megrendeles) {
        this.megrendeles = megrendeles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UgyfelElszamolasokDTO)) {
            return false;
        }

        UgyfelElszamolasokDTO ugyfelElszamolasokDTO = (UgyfelElszamolasokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ugyfelElszamolasokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UgyfelElszamolasokDTO{" +
            "id=" + getId() +
            ", teljesitesiIdoszakKezdete='" + getTeljesitesiIdoszakKezdete() + "'" +
            ", teljesitesiIdoszakVege='" + getTeljesitesiIdoszakVege() + "'" +
            ", napokSzama=" + getNapokSzama() +
            ", teljesitesIgazolasonSzereploOsszeg=" + getTeljesitesIgazolasonSzereploOsszeg() +
            ", kapcsolodoSzamlaSorszamRogzitve='" + getKapcsolodoSzamlaSorszamRogzitve() + "'" +
            ", megrendeles=" + getMegrendeles() +
            "}";
    }
}

package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OsztalekfizetesiKozgyulesek.
 */
@Entity
@Table(name = "osztalekfizetesi_kozgyulesek")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OsztalekfizetesiKozgyulesek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "kozgyules_datum", nullable = false)
    private LocalDate kozgyulesDatum;

    @Column(name = "kozgyulesi_jegyzokonyv_generalta")
    private Boolean kozgyulesiJegyzokonyvGeneralta;

    @Column(name = "kozgyulesi_jegyzokonyv_alairt")
    private Boolean kozgyulesiJegyzokonyvAlairt;

    @Column(name = "merleg_foosszeg", precision = 21, scale = 2)
    private BigDecimal merlegFoosszeg;

    @Column(name = "adozott_eredmeny", precision = 21, scale = 2)
    private BigDecimal adozottEredmeny;

    @Column(name = "generalt_dokumentum_nev")
    private String generaltDokumentumNev;

    @Column(name = "generalt_dokumentum_url")
    private String generaltDokumentumUrl;

    @Column(name = "alairt_dokumentum_nev")
    private String alairtDokumentumNev;

    @Column(name = "alairt_dokumentum_url")
    private String alairtDokumentumUrl;

    /** Grand total of ALL costs (sum of B column: worker costs + overhead). Persisted on Excel export for document generation. */
    @Column(name = "elszamolas_grand_total", precision = 21, scale = 2)
    private BigDecimal elszamolasGrandTotal;

    /** Total invoiced amount (sum of F column: Korrigalt napdij * days per person). Persisted on Excel export for document generation. */
    @Column(name = "elszamolas_szamlazott_total", precision = 21, scale = 2)
    private BigDecimal elszamolasNapidijakOsszesen;

    @Column(name = "kozgyules_helyszine")
    private String kozgyulesHelyszine;

    @Column(name = "hatarozat_szama")
    private String hatarozatSzama;

    @Column(name = "megjegyzes", columnDefinition = "TEXT")
    private String megjegyzes;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ceg" }, allowSetters = true)
    private SajatCegAlapadatok sajatCeg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OsztalekfizetesiKozgyulesek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getKozgyulesDatum() {
        return this.kozgyulesDatum;
    }

    public OsztalekfizetesiKozgyulesek kozgyulesDatum(LocalDate kozgyulesDatum) {
        this.setKozgyulesDatum(kozgyulesDatum);
        return this;
    }

    public void setKozgyulesDatum(LocalDate kozgyulesDatum) {
        this.kozgyulesDatum = kozgyulesDatum;
    }

    public Boolean getKozgyulesiJegyzokonyvGeneralta() {
        return this.kozgyulesiJegyzokonyvGeneralta;
    }

    public OsztalekfizetesiKozgyulesek kozgyulesiJegyzokonyvGeneralta(Boolean kozgyulesiJegyzokonyvGeneralta) {
        this.setKozgyulesiJegyzokonyvGeneralta(kozgyulesiJegyzokonyvGeneralta);
        return this;
    }

    public void setKozgyulesiJegyzokonyvGeneralta(Boolean kozgyulesiJegyzokonyvGeneralta) {
        this.kozgyulesiJegyzokonyvGeneralta = kozgyulesiJegyzokonyvGeneralta;
    }

    public Boolean getKozgyulesiJegyzokonyvAlairt() {
        return this.kozgyulesiJegyzokonyvAlairt;
    }

    public OsztalekfizetesiKozgyulesek kozgyulesiJegyzokonyvAlairt(Boolean kozgyulesiJegyzokonyvAlairt) {
        this.setKozgyulesiJegyzokonyvAlairt(kozgyulesiJegyzokonyvAlairt);
        return this;
    }

    public void setKozgyulesiJegyzokonyvAlairt(Boolean kozgyulesiJegyzokonyvAlairt) {
        this.kozgyulesiJegyzokonyvAlairt = kozgyulesiJegyzokonyvAlairt;
    }

    public BigDecimal getMerlegFoosszeg() {
        return this.merlegFoosszeg;
    }

    public OsztalekfizetesiKozgyulesek merlegFoosszeg(BigDecimal merlegFoosszeg) {
        this.setMerlegFoosszeg(merlegFoosszeg);
        return this;
    }

    public void setMerlegFoosszeg(BigDecimal merlegFoosszeg) {
        this.merlegFoosszeg = merlegFoosszeg;
    }

    public BigDecimal getAdozottEredmeny() {
        return this.adozottEredmeny;
    }

    public OsztalekfizetesiKozgyulesek adozottEredmeny(BigDecimal adozottEredmeny) {
        this.setAdozottEredmeny(adozottEredmeny);
        return this;
    }

    public void setAdozottEredmeny(BigDecimal adozottEredmeny) {
        this.adozottEredmeny = adozottEredmeny;
    }

    public String getGeneraltDokumentumNev() {
        return this.generaltDokumentumNev;
    }

    public OsztalekfizetesiKozgyulesek generaltDokumentumNev(String generaltDokumentumNev) {
        this.setGeneraltDokumentumNev(generaltDokumentumNev);
        return this;
    }

    public void setGeneraltDokumentumNev(String generaltDokumentumNev) {
        this.generaltDokumentumNev = generaltDokumentumNev;
    }

    public String getGeneraltDokumentumUrl() {
        return this.generaltDokumentumUrl;
    }

    public OsztalekfizetesiKozgyulesek generaltDokumentumUrl(String generaltDokumentumUrl) {
        this.setGeneraltDokumentumUrl(generaltDokumentumUrl);
        return this;
    }

    public void setGeneraltDokumentumUrl(String generaltDokumentumUrl) {
        this.generaltDokumentumUrl = generaltDokumentumUrl;
    }

    public String getAlairtDokumentumNev() {
        return this.alairtDokumentumNev;
    }

    public OsztalekfizetesiKozgyulesek alairtDokumentumNev(String alairtDokumentumNev) {
        this.setAlairtDokumentumNev(alairtDokumentumNev);
        return this;
    }

    public void setAlairtDokumentumNev(String alairtDokumentumNev) {
        this.alairtDokumentumNev = alairtDokumentumNev;
    }

    public String getAlairtDokumentumUrl() {
        return this.alairtDokumentumUrl;
    }

    public OsztalekfizetesiKozgyulesek alairtDokumentumUrl(String alairtDokumentumUrl) {
        this.setAlairtDokumentumUrl(alairtDokumentumUrl);
        return this;
    }

    public void setAlairtDokumentumUrl(String alairtDokumentumUrl) {
        this.alairtDokumentumUrl = alairtDokumentumUrl;
    }

    public BigDecimal getElszamolasGrandTotal() {
        return this.elszamolasGrandTotal;
    }

    public OsztalekfizetesiKozgyulesek elszamolasGrandTotal(BigDecimal elszamolasGrandTotal) {
        this.setElszamolasGrandTotal(elszamolasGrandTotal);
        return this;
    }

    public void setElszamolasGrandTotal(BigDecimal elszamolasGrandTotal) {
        this.elszamolasGrandTotal = elszamolasGrandTotal;
    }

    public BigDecimal getElszamolasNapidijakOsszesen() {
        return this.elszamolasNapidijakOsszesen;
    }

    public OsztalekfizetesiKozgyulesek elszamolasNapidijakOsszesen(BigDecimal elszamolasNapidijakOsszesen) {
        this.setElszamolasNapidijakOsszesen(elszamolasNapidijakOsszesen);
        return this;
    }

    public void setElszamolasNapidijakOsszesen(BigDecimal elszamolasNapidijakOsszesen) {
        this.elszamolasNapidijakOsszesen = elszamolasNapidijakOsszesen;
    }

    public SajatCegAlapadatok getSajatCeg() {
        return this.sajatCeg;
    }

    public void setSajatCeg(SajatCegAlapadatok sajatCegAlapadatok) {
        this.sajatCeg = sajatCegAlapadatok;
    }

    public OsztalekfizetesiKozgyulesek sajatCeg(SajatCegAlapadatok sajatCegAlapadatok) {
        this.setSajatCeg(sajatCegAlapadatok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getKozgyulesHelyszine() { return this.kozgyulesHelyszine; }
    public void setKozgyulesHelyszine(String kozgyulesHelyszine) { this.kozgyulesHelyszine = kozgyulesHelyszine; }

    public String getHatarozatSzama() { return this.hatarozatSzama; }
    public void setHatarozatSzama(String hatarozatSzama) { this.hatarozatSzama = hatarozatSzama; }

    public String getMegjegyzes() { return this.megjegyzes; }
    public void setMegjegyzes(String megjegyzes) { this.megjegyzes = megjegyzes; }

    public String getStatusz() { return this.statusz; }
    public void setStatusz(String statusz) { this.statusz = statusz; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsztalekfizetesiKozgyulesek)) {
            return false;
        }
        return getId() != null && getId().equals(((OsztalekfizetesiKozgyulesek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsztalekfizetesiKozgyulesek{" +
            "id=" + getId() +
            ", kozgyulesDatum='" + getKozgyulesDatum() + "'" +
            ", kozgyulesiJegyzokonyvGeneralta='" + getKozgyulesiJegyzokonyvGeneralta() + "'" +
            ", kozgyulesiJegyzokonyvAlairt='" + getKozgyulesiJegyzokonyvAlairt() + "'" +
            ", merlegFoosszeg=" + getMerlegFoosszeg() +
            ", adozottEredmeny=" + getAdozottEredmeny() +
            ", generaltDokumentumNev='" + getGeneraltDokumentumNev() + "'" +
            ", generaltDokumentumUrl='" + getGeneraltDokumentumUrl() + "'" +
            ", alairtDokumentumNev='" + getAlairtDokumentumNev() + "'" +
            ", alairtDokumentumUrl='" + getAlairtDokumentumUrl() + "'" +
            ", elszamolasGrandTotal=" + getElszamolasGrandTotal() +
            ", elszamolasNapidijakOsszesen=" + getElszamolasNapidijakOsszesen() +
            ", kozgyulesHelyszine='" + getKozgyulesHelyszine() + "'" +
            ", hatarozatSzama='" + getHatarozatSzama() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

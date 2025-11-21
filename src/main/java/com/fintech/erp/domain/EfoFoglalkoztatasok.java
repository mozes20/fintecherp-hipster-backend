package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EfoFoglalkoztatasok.
 */
@Entity
@Table(name = "efo_foglalkoztatasok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EfoFoglalkoztatasok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "osszeg", precision = 21, scale = 2)
    private BigDecimal osszeg;

    @Column(name = "generalt_efo_szerzodes")
    private Boolean generaltEfoSzerzodes;

    @Column(name = "alairt_efo_szerzodes")
    private Boolean alairtEfoSzerzodes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sajatCeg", "maganszemely" }, allowSetters = true)
    private Munkavallalok munkavallalo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Munkakorok munkakor;

    @Column(name = "generalt_dokumentum_nev")
    private String generaltDokumentumNev;

    @Column(name = "generalt_dokumentum_url")
    private String generaltDokumentumUrl;

    @Column(name = "alairt_dokumentum_url")
    private String alairtDokumentumUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EfoFoglalkoztatasok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public EfoFoglalkoztatasok datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public BigDecimal getOsszeg() {
        return this.osszeg;
    }

    public EfoFoglalkoztatasok osszeg(BigDecimal osszeg) {
        this.setOsszeg(osszeg);
        return this;
    }

    public void setOsszeg(BigDecimal osszeg) {
        this.osszeg = osszeg;
    }

    public Boolean getGeneraltEfoSzerzodes() {
        return this.generaltEfoSzerzodes;
    }

    public EfoFoglalkoztatasok generaltEfoSzerzodes(Boolean generaltEfoSzerzodes) {
        this.setGeneraltEfoSzerzodes(generaltEfoSzerzodes);
        return this;
    }

    public void setGeneraltEfoSzerzodes(Boolean generaltEfoSzerzodes) {
        this.generaltEfoSzerzodes = generaltEfoSzerzodes;
    }

    public Boolean getAlairtEfoSzerzodes() {
        return this.alairtEfoSzerzodes;
    }

    public EfoFoglalkoztatasok alairtEfoSzerzodes(Boolean alairtEfoSzerzodes) {
        this.setAlairtEfoSzerzodes(alairtEfoSzerzodes);
        return this;
    }

    public void setAlairtEfoSzerzodes(Boolean alairtEfoSzerzodes) {
        this.alairtEfoSzerzodes = alairtEfoSzerzodes;
    }

    public Munkavallalok getMunkavallalo() {
        return this.munkavallalo;
    }

    public void setMunkavallalo(Munkavallalok munkavallalok) {
        this.munkavallalo = munkavallalok;
    }

    public EfoFoglalkoztatasok munkavallalo(Munkavallalok munkavallalok) {
        this.setMunkavallalo(munkavallalok);
        return this;
    }

    public Munkakorok getMunkakor() {
        return this.munkakor;
    }

    public void setMunkakor(Munkakorok munkakor) {
        this.munkakor = munkakor;
    }

    public EfoFoglalkoztatasok munkakor(Munkakorok munkakor) {
        this.setMunkakor(munkakor);
        return this;
    }

    public String getGeneraltDokumentumNev() {
        return this.generaltDokumentumNev;
    }

    public void setGeneraltDokumentumNev(String generaltDokumentumNev) {
        this.generaltDokumentumNev = generaltDokumentumNev;
    }

    public EfoFoglalkoztatasok generaltDokumentumNev(String generaltDokumentumNev) {
        this.setGeneraltDokumentumNev(generaltDokumentumNev);
        return this;
    }

    public String getGeneraltDokumentumUrl() {
        return this.generaltDokumentumUrl;
    }

    public void setGeneraltDokumentumUrl(String generaltDokumentumUrl) {
        this.generaltDokumentumUrl = generaltDokumentumUrl;
    }

    public EfoFoglalkoztatasok generaltDokumentumUrl(String generaltDokumentumUrl) {
        this.setGeneraltDokumentumUrl(generaltDokumentumUrl);
        return this;
    }

    public String getAlairtDokumentumUrl() {
        return this.alairtDokumentumUrl;
    }

    public void setAlairtDokumentumUrl(String alairtDokumentumUrl) {
        this.alairtDokumentumUrl = alairtDokumentumUrl;
    }

    public EfoFoglalkoztatasok alairtDokumentumUrl(String alairtDokumentumUrl) {
        this.setAlairtDokumentumUrl(alairtDokumentumUrl);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EfoFoglalkoztatasok)) {
            return false;
        }
        return getId() != null && getId().equals(((EfoFoglalkoztatasok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EfoFoglalkoztatasok{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", osszeg=" + getOsszeg() +
            ", generaltEfoSzerzodes='" + getGeneraltEfoSzerzodes() + "'" +
            ", alairtEfoSzerzodes='" + getAlairtEfoSzerzodes() + "'" +
            ", generaltDokumentumNev='" + getGeneraltDokumentumNev() + "'" +
            ", generaltDokumentumUrl='" + getGeneraltDokumentumUrl() + "'" +
            ", alairtDokumentumUrl='" + getAlairtDokumentumUrl() + "'" +
            "}";
    }
}

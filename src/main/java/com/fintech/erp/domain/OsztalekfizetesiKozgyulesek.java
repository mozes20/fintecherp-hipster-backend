package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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
            "}";
    }
}

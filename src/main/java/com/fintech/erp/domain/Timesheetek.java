package com.fintech.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Timesheetek.
 */
@Entity
@Table(name = "timesheetek")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Timesheetek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "munkanap_statusza")
    private String munkanapStatusza;

    @Column(name = "statusz")
    private String statusz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sajatCeg", "maganszemely" }, allowSetters = true)
    private Munkavallalok munkavallalo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Timesheetek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Timesheetek datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getMunkanapStatusza() {
        return this.munkanapStatusza;
    }

    public Timesheetek munkanapStatusza(String munkanapStatusza) {
        this.setMunkanapStatusza(munkanapStatusza);
        return this;
    }

    public void setMunkanapStatusza(String munkanapStatusza) {
        this.munkanapStatusza = munkanapStatusza;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public Timesheetek statusz(String statusz) {
        this.setStatusz(statusz);
        return this;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public Munkavallalok getMunkavallalo() {
        return this.munkavallalo;
    }

    public void setMunkavallalo(Munkavallalok munkavallalok) {
        this.munkavallalo = munkavallalok;
    }

    public Timesheetek munkavallalo(Munkavallalok munkavallalok) {
        this.setMunkavallalo(munkavallalok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timesheetek)) {
            return false;
        }
        return getId() != null && getId().equals(((Timesheetek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Timesheetek{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", munkanapStatusza='" + getMunkanapStatusza() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

package com.fintech.erp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SzerzodesesJogviszonyok.
 */
@Entity
@Table(name = "szerzodeses_jogviszonyok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SzerzodesesJogviszonyok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "szerzodes_azonosito", nullable = false, unique = true)
    private String szerzodesAzonosito;

    @NotNull
    @Column(name = "jogviszony_kezdete", nullable = false)
    private LocalDate jogviszonyKezdete;

    @NotNull
    @Column(name = "jogviszony_lejarata", nullable = false)
    private LocalDate jogviszonyLejarata;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CegAlapadatok megrendeloCeg;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CegAlapadatok vallalkozoCeg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SzerzodesesJogviszonyok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSzerzodesAzonosito() {
        return this.szerzodesAzonosito;
    }

    public SzerzodesesJogviszonyok szerzodesAzonosito(String szerzodesAzonosito) {
        this.setSzerzodesAzonosito(szerzodesAzonosito);
        return this;
    }

    public void setSzerzodesAzonosito(String szerzodesAzonosito) {
        this.szerzodesAzonosito = szerzodesAzonosito;
    }

    public LocalDate getJogviszonyKezdete() {
        return this.jogviszonyKezdete;
    }

    public SzerzodesesJogviszonyok jogviszonyKezdete(LocalDate jogviszonyKezdete) {
        this.setJogviszonyKezdete(jogviszonyKezdete);
        return this;
    }

    public void setJogviszonyKezdete(LocalDate jogviszonyKezdete) {
        this.jogviszonyKezdete = jogviszonyKezdete;
    }

    public LocalDate getJogviszonyLejarata() {
        return this.jogviszonyLejarata;
    }

    public SzerzodesesJogviszonyok jogviszonyLejarata(LocalDate jogviszonyLejarata) {
        this.setJogviszonyLejarata(jogviszonyLejarata);
        return this;
    }

    public void setJogviszonyLejarata(LocalDate jogviszonyLejarata) {
        this.jogviszonyLejarata = jogviszonyLejarata;
    }

    public CegAlapadatok getMegrendeloCeg() {
        return this.megrendeloCeg;
    }

    public void setMegrendeloCeg(CegAlapadatok cegAlapadatok) {
        this.megrendeloCeg = cegAlapadatok;
    }

    public SzerzodesesJogviszonyok megrendeloCeg(CegAlapadatok cegAlapadatok) {
        this.setMegrendeloCeg(cegAlapadatok);
        return this;
    }

    public CegAlapadatok getVallalkozoCeg() {
        return this.vallalkozoCeg;
    }

    public void setVallalkozoCeg(CegAlapadatok cegAlapadatok) {
        this.vallalkozoCeg = cegAlapadatok;
    }

    public SzerzodesesJogviszonyok vallalkozoCeg(CegAlapadatok cegAlapadatok) {
        this.setVallalkozoCeg(cegAlapadatok);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesesJogviszonyok)) {
            return false;
        }
        return getId() != null && getId().equals(((SzerzodesesJogviszonyok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SzerzodesesJogviszonyok{" +
            "id=" + getId() +
            ", szerzodesAzonosito='" + getSzerzodesAzonosito() + "'" +
            ", jogviszonyKezdete='" + getJogviszonyKezdete() + "'" +
            ", jogviszonyLejarata='" + getJogviszonyLejarata() + "'" +
            "}";
    }
}

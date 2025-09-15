package com.fintech.erp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CegAlapadatok.
 */
@Entity
@Table(name = "ceg_alapadatok")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CegAlapadatok implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ceg_nev", nullable = false)
    private String cegNev;

    @Column(name = "ceg_rovid_azonosito")
    private String cegRovidAzonosito;

    @Column(name = "ceg_szekhely")
    private String cegSzekhely;

    @Column(name = "adoszam")
    private String adoszam;

    @Column(name = "cegjegyzekszam")
    private String cegjegyzekszam;

    @Column(name = "ceg_kozponti_email")
    private String cegKozpontiEmail;

    @Column(name = "ceg_kozponti_tel")
    private String cegKozpontiTel;

    @Column(name = "statusz")
    private String statusz;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CegAlapadatok id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCegNev() {
        return this.cegNev;
    }

    public CegAlapadatok cegNev(String cegNev) {
        this.setCegNev(cegNev);
        return this;
    }

    public void setCegNev(String cegNev) {
        this.cegNev = cegNev;
    }

    public String getCegRovidAzonosito() {
        return this.cegRovidAzonosito;
    }

    public CegAlapadatok cegRovidAzonosito(String cegRovidAzonosito) {
        this.setCegRovidAzonosito(cegRovidAzonosito);
        return this;
    }

    public void setCegRovidAzonosito(String cegRovidAzonosito) {
        this.cegRovidAzonosito = cegRovidAzonosito;
    }

    public String getCegSzekhely() {
        return this.cegSzekhely;
    }

    public CegAlapadatok cegSzekhely(String cegSzekhely) {
        this.setCegSzekhely(cegSzekhely);
        return this;
    }

    public void setCegSzekhely(String cegSzekhely) {
        this.cegSzekhely = cegSzekhely;
    }

    public String getAdoszam() {
        return this.adoszam;
    }

    public CegAlapadatok adoszam(String adoszam) {
        this.setAdoszam(adoszam);
        return this;
    }

    public void setAdoszam(String adoszam) {
        this.adoszam = adoszam;
    }

    public String getCegjegyzekszam() {
        return this.cegjegyzekszam;
    }

    public CegAlapadatok cegjegyzekszam(String cegjegyzekszam) {
        this.setCegjegyzekszam(cegjegyzekszam);
        return this;
    }

    public void setCegjegyzekszam(String cegjegyzekszam) {
        this.cegjegyzekszam = cegjegyzekszam;
    }

    public String getCegKozpontiEmail() {
        return this.cegKozpontiEmail;
    }

    public CegAlapadatok cegKozpontiEmail(String cegKozpontiEmail) {
        this.setCegKozpontiEmail(cegKozpontiEmail);
        return this;
    }

    public void setCegKozpontiEmail(String cegKozpontiEmail) {
        this.cegKozpontiEmail = cegKozpontiEmail;
    }

    public String getCegKozpontiTel() {
        return this.cegKozpontiTel;
    }

    public CegAlapadatok cegKozpontiTel(String cegKozpontiTel) {
        this.setCegKozpontiTel(cegKozpontiTel);
        return this;
    }

    public void setCegKozpontiTel(String cegKozpontiTel) {
        this.cegKozpontiTel = cegKozpontiTel;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public CegAlapadatok statusz(String statusz) {
        this.setStatusz(statusz);
        return this;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CegAlapadatok)) {
            return false;
        }
        return getId() != null && getId().equals(((CegAlapadatok) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CegAlapadatok{" +
            "id=" + getId() +
            ", cegNev='" + getCegNev() + "'" +
            ", cegRovidAzonosito='" + getCegRovidAzonosito() + "'" +
            ", cegSzekhely='" + getCegSzekhely() + "'" +
            ", adoszam='" + getAdoszam() + "'" +
            ", cegjegyzekszam='" + getCegjegyzekszam() + "'" +
            ", cegKozpontiEmail='" + getCegKozpontiEmail() + "'" +
            ", cegKozpontiTel='" + getCegKozpontiTel() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

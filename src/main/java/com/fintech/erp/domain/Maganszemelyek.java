package com.fintech.erp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Maganszemelyek.
 */
@Entity
@Table(name = "maganszemelyek")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Maganszemelyek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "maganszemely_neve", nullable = false)
    private String maganszemelyNeve;

    @Column(name = "szemelyi_igazolvany_szama")
    private String szemelyiIgazolvanySzama;

    @Column(name = "ado_azonosito_jel")
    private String adoAzonositoJel;

    @Column(name = "tb_azonosito")
    private String tbAzonosito;

    @Column(name = "bankszamlaszam")
    private String bankszamlaszam;

    @Column(name = "telefonszam")
    private String telefonszam;

    @Column(name = "emailcim")
    private String emailcim;

    @Column(name = "statusz")
    private String statusz;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Maganszemelyek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaganszemelyNeve() {
        return this.maganszemelyNeve;
    }

    public Maganszemelyek maganszemelyNeve(String maganszemelyNeve) {
        this.setMaganszemelyNeve(maganszemelyNeve);
        return this;
    }

    public void setMaganszemelyNeve(String maganszemelyNeve) {
        this.maganszemelyNeve = maganszemelyNeve;
    }

    public String getSzemelyiIgazolvanySzama() {
        return this.szemelyiIgazolvanySzama;
    }

    public Maganszemelyek szemelyiIgazolvanySzama(String szemelyiIgazolvanySzama) {
        this.setSzemelyiIgazolvanySzama(szemelyiIgazolvanySzama);
        return this;
    }

    public void setSzemelyiIgazolvanySzama(String szemelyiIgazolvanySzama) {
        this.szemelyiIgazolvanySzama = szemelyiIgazolvanySzama;
    }

    public String getAdoAzonositoJel() {
        return this.adoAzonositoJel;
    }

    public Maganszemelyek adoAzonositoJel(String adoAzonositoJel) {
        this.setAdoAzonositoJel(adoAzonositoJel);
        return this;
    }

    public void setAdoAzonositoJel(String adoAzonositoJel) {
        this.adoAzonositoJel = adoAzonositoJel;
    }

    public String getTbAzonosito() {
        return this.tbAzonosito;
    }

    public Maganszemelyek tbAzonosito(String tbAzonosito) {
        this.setTbAzonosito(tbAzonosito);
        return this;
    }

    public void setTbAzonosito(String tbAzonosito) {
        this.tbAzonosito = tbAzonosito;
    }

    public String getBankszamlaszam() {
        return this.bankszamlaszam;
    }

    public Maganszemelyek bankszamlaszam(String bankszamlaszam) {
        this.setBankszamlaszam(bankszamlaszam);
        return this;
    }

    public void setBankszamlaszam(String bankszamlaszam) {
        this.bankszamlaszam = bankszamlaszam;
    }

    public String getTelefonszam() {
        return this.telefonszam;
    }

    public Maganszemelyek telefonszam(String telefonszam) {
        this.setTelefonszam(telefonszam);
        return this;
    }

    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

    public String getEmailcim() {
        return this.emailcim;
    }

    public Maganszemelyek emailcim(String emailcim) {
        this.setEmailcim(emailcim);
        return this;
    }

    public void setEmailcim(String emailcim) {
        this.emailcim = emailcim;
    }

    public String getStatusz() {
        return this.statusz;
    }

    public Maganszemelyek statusz(String statusz) {
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
        if (!(o instanceof Maganszemelyek)) {
            return false;
        }
        return getId() != null && getId().equals(((Maganszemelyek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Maganszemelyek{" +
            "id=" + getId() +
            ", maganszemelyNeve='" + getMaganszemelyNeve() + "'" +
            ", szemelyiIgazolvanySzama='" + getSzemelyiIgazolvanySzama() + "'" +
            ", adoAzonositoJel='" + getAdoAzonositoJel() + "'" +
            ", tbAzonosito='" + getTbAzonosito() + "'" +
            ", bankszamlaszam='" + getBankszamlaszam() + "'" +
            ", telefonszam='" + getTelefonszam() + "'" +
            ", emailcim='" + getEmailcim() + "'" +
            ", statusz='" + getStatusz() + "'" +
            "}";
    }
}

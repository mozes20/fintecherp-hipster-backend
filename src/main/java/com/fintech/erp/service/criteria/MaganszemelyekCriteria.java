package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.Maganszemelyek} entity. This class is used
 * in {@link com.fintech.erp.web.rest.MaganszemelyekResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /maganszemelyeks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaganszemelyekCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maganszemelyNeve;

    private StringFilter szemelyiIgazolvanySzama;

    private StringFilter adoAzonositoJel;

    private StringFilter tbAzonosito;

    private StringFilter bankszamlaszam;

    private StringFilter telefonszam;

    private StringFilter emailcim;

    private StringFilter statusz;

    private Boolean distinct;

    public MaganszemelyekCriteria() {}

    public MaganszemelyekCriteria(MaganszemelyekCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.maganszemelyNeve = other.optionalMaganszemelyNeve().map(StringFilter::copy).orElse(null);
        this.szemelyiIgazolvanySzama = other.optionalSzemelyiIgazolvanySzama().map(StringFilter::copy).orElse(null);
        this.adoAzonositoJel = other.optionalAdoAzonositoJel().map(StringFilter::copy).orElse(null);
        this.tbAzonosito = other.optionalTbAzonosito().map(StringFilter::copy).orElse(null);
        this.bankszamlaszam = other.optionalBankszamlaszam().map(StringFilter::copy).orElse(null);
        this.telefonszam = other.optionalTelefonszam().map(StringFilter::copy).orElse(null);
        this.emailcim = other.optionalEmailcim().map(StringFilter::copy).orElse(null);
        this.statusz = other.optionalStatusz().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MaganszemelyekCriteria copy() {
        return new MaganszemelyekCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMaganszemelyNeve() {
        return maganszemelyNeve;
    }

    public Optional<StringFilter> optionalMaganszemelyNeve() {
        return Optional.ofNullable(maganszemelyNeve);
    }

    public StringFilter maganszemelyNeve() {
        if (maganszemelyNeve == null) {
            setMaganszemelyNeve(new StringFilter());
        }
        return maganszemelyNeve;
    }

    public void setMaganszemelyNeve(StringFilter maganszemelyNeve) {
        this.maganszemelyNeve = maganszemelyNeve;
    }

    public StringFilter getSzemelyiIgazolvanySzama() {
        return szemelyiIgazolvanySzama;
    }

    public Optional<StringFilter> optionalSzemelyiIgazolvanySzama() {
        return Optional.ofNullable(szemelyiIgazolvanySzama);
    }

    public StringFilter szemelyiIgazolvanySzama() {
        if (szemelyiIgazolvanySzama == null) {
            setSzemelyiIgazolvanySzama(new StringFilter());
        }
        return szemelyiIgazolvanySzama;
    }

    public void setSzemelyiIgazolvanySzama(StringFilter szemelyiIgazolvanySzama) {
        this.szemelyiIgazolvanySzama = szemelyiIgazolvanySzama;
    }

    public StringFilter getAdoAzonositoJel() {
        return adoAzonositoJel;
    }

    public Optional<StringFilter> optionalAdoAzonositoJel() {
        return Optional.ofNullable(adoAzonositoJel);
    }

    public StringFilter adoAzonositoJel() {
        if (adoAzonositoJel == null) {
            setAdoAzonositoJel(new StringFilter());
        }
        return adoAzonositoJel;
    }

    public void setAdoAzonositoJel(StringFilter adoAzonositoJel) {
        this.adoAzonositoJel = adoAzonositoJel;
    }

    public StringFilter getTbAzonosito() {
        return tbAzonosito;
    }

    public Optional<StringFilter> optionalTbAzonosito() {
        return Optional.ofNullable(tbAzonosito);
    }

    public StringFilter tbAzonosito() {
        if (tbAzonosito == null) {
            setTbAzonosito(new StringFilter());
        }
        return tbAzonosito;
    }

    public void setTbAzonosito(StringFilter tbAzonosito) {
        this.tbAzonosito = tbAzonosito;
    }

    public StringFilter getBankszamlaszam() {
        return bankszamlaszam;
    }

    public Optional<StringFilter> optionalBankszamlaszam() {
        return Optional.ofNullable(bankszamlaszam);
    }

    public StringFilter bankszamlaszam() {
        if (bankszamlaszam == null) {
            setBankszamlaszam(new StringFilter());
        }
        return bankszamlaszam;
    }

    public void setBankszamlaszam(StringFilter bankszamlaszam) {
        this.bankszamlaszam = bankszamlaszam;
    }

    public StringFilter getTelefonszam() {
        return telefonszam;
    }

    public Optional<StringFilter> optionalTelefonszam() {
        return Optional.ofNullable(telefonszam);
    }

    public StringFilter telefonszam() {
        if (telefonszam == null) {
            setTelefonszam(new StringFilter());
        }
        return telefonszam;
    }

    public void setTelefonszam(StringFilter telefonszam) {
        this.telefonszam = telefonszam;
    }

    public StringFilter getEmailcim() {
        return emailcim;
    }

    public Optional<StringFilter> optionalEmailcim() {
        return Optional.ofNullable(emailcim);
    }

    public StringFilter emailcim() {
        if (emailcim == null) {
            setEmailcim(new StringFilter());
        }
        return emailcim;
    }

    public void setEmailcim(StringFilter emailcim) {
        this.emailcim = emailcim;
    }

    public StringFilter getStatusz() {
        return statusz;
    }

    public Optional<StringFilter> optionalStatusz() {
        return Optional.ofNullable(statusz);
    }

    public StringFilter statusz() {
        if (statusz == null) {
            setStatusz(new StringFilter());
        }
        return statusz;
    }

    public void setStatusz(StringFilter statusz) {
        this.statusz = statusz;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MaganszemelyekCriteria that = (MaganszemelyekCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maganszemelyNeve, that.maganszemelyNeve) &&
            Objects.equals(szemelyiIgazolvanySzama, that.szemelyiIgazolvanySzama) &&
            Objects.equals(adoAzonositoJel, that.adoAzonositoJel) &&
            Objects.equals(tbAzonosito, that.tbAzonosito) &&
            Objects.equals(bankszamlaszam, that.bankszamlaszam) &&
            Objects.equals(telefonszam, that.telefonszam) &&
            Objects.equals(emailcim, that.emailcim) &&
            Objects.equals(statusz, that.statusz) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            maganszemelyNeve,
            szemelyiIgazolvanySzama,
            adoAzonositoJel,
            tbAzonosito,
            bankszamlaszam,
            telefonszam,
            emailcim,
            statusz,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaganszemelyekCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMaganszemelyNeve().map(f -> "maganszemelyNeve=" + f + ", ").orElse("") +
            optionalSzemelyiIgazolvanySzama().map(f -> "szemelyiIgazolvanySzama=" + f + ", ").orElse("") +
            optionalAdoAzonositoJel().map(f -> "adoAzonositoJel=" + f + ", ").orElse("") +
            optionalTbAzonosito().map(f -> "tbAzonosito=" + f + ", ").orElse("") +
            optionalBankszamlaszam().map(f -> "bankszamlaszam=" + f + ", ").orElse("") +
            optionalTelefonszam().map(f -> "telefonszam=" + f + ", ").orElse("") +
            optionalEmailcim().map(f -> "emailcim=" + f + ", ").orElse("") +
            optionalStatusz().map(f -> "statusz=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

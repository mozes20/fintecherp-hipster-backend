package com.fintech.erp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.fintech.erp.domain.AlvallalkozoiElszamolasok} entity.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlvallalkozoiElszamolasokCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private LocalDateFilter teljesitesiIdoszakKezdete;
    private LocalDateFilter teljesitesiIdoszakVege;
    private BigDecimalFilter napokSzama;
    private BigDecimalFilter teljesitesIgazolasonSzereploOsszeg;
    private BooleanFilter bejovoSzamlaSorszamRogzitve;
    private StringFilter bejovoSzamlaSorszam;
    private LongFilter megrendelesId;
    private Boolean distinct;

    public AlvallalkozoiElszamolasokCriteria() {}

    public AlvallalkozoiElszamolasokCriteria(AlvallalkozoiElszamolasokCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.teljesitesiIdoszakKezdete = other.optionalTeljesitesiIdoszakKezdete().map(LocalDateFilter::copy).orElse(null);
        this.teljesitesiIdoszakVege = other.optionalTeljesitesiIdoszakVege().map(LocalDateFilter::copy).orElse(null);
        this.napokSzama = other.optionalNapokSzama().map(BigDecimalFilter::copy).orElse(null);
        this.teljesitesIgazolasonSzereploOsszeg = other.optionalTeljesitesIgazolasonSzereploOsszeg().map(BigDecimalFilter::copy).orElse(null);
        this.bejovoSzamlaSorszamRogzitve = other.optionalBejovoSzamlaSorszamRogzitve().map(BooleanFilter::copy).orElse(null);
        this.bejovoSzamlaSorszam = other.optionalBejovoSzamlaSorszam().map(StringFilter::copy).orElse(null);
        this.megrendelesId = other.optionalMegrendelesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AlvallalkozoiElszamolasokCriteria copy() {
        return new AlvallalkozoiElszamolasokCriteria(this);
    }

    public LongFilter getId() { return id; }
    public Optional<LongFilter> optionalId() { return Optional.ofNullable(id); }
    public LongFilter id() { if (id == null) setId(new LongFilter()); return id; }
    public void setId(LongFilter id) { this.id = id; }

    public LocalDateFilter getTeljesitesiIdoszakKezdete() { return teljesitesiIdoszakKezdete; }
    public Optional<LocalDateFilter> optionalTeljesitesiIdoszakKezdete() { return Optional.ofNullable(teljesitesiIdoszakKezdete); }
    public LocalDateFilter teljesitesiIdoszakKezdete() { if (teljesitesiIdoszakKezdete == null) setTeljesitesiIdoszakKezdete(new LocalDateFilter()); return teljesitesiIdoszakKezdete; }
    public void setTeljesitesiIdoszakKezdete(LocalDateFilter teljesitesiIdoszakKezdete) { this.teljesitesiIdoszakKezdete = teljesitesiIdoszakKezdete; }

    public LocalDateFilter getTeljesitesiIdoszakVege() { return teljesitesiIdoszakVege; }
    public Optional<LocalDateFilter> optionalTeljesitesiIdoszakVege() { return Optional.ofNullable(teljesitesiIdoszakVege); }
    public LocalDateFilter teljesitesiIdoszakVege() { if (teljesitesiIdoszakVege == null) setTeljesitesiIdoszakVege(new LocalDateFilter()); return teljesitesiIdoszakVege; }
    public void setTeljesitesiIdoszakVege(LocalDateFilter teljesitesiIdoszakVege) { this.teljesitesiIdoszakVege = teljesitesiIdoszakVege; }

    public BigDecimalFilter getNapokSzama() { return napokSzama; }
    public Optional<BigDecimalFilter> optionalNapokSzama() { return Optional.ofNullable(napokSzama); }
    public BigDecimalFilter napokSzama() { if (napokSzama == null) setNapokSzama(new BigDecimalFilter()); return napokSzama; }
    public void setNapokSzama(BigDecimalFilter napokSzama) { this.napokSzama = napokSzama; }

    public BigDecimalFilter getTeljesitesIgazolasonSzereploOsszeg() { return teljesitesIgazolasonSzereploOsszeg; }
    public Optional<BigDecimalFilter> optionalTeljesitesIgazolasonSzereploOsszeg() { return Optional.ofNullable(teljesitesIgazolasonSzereploOsszeg); }
    public BigDecimalFilter teljesitesIgazolasonSzereploOsszeg() { if (teljesitesIgazolasonSzereploOsszeg == null) setTeljesitesIgazolasonSzereploOsszeg(new BigDecimalFilter()); return teljesitesIgazolasonSzereploOsszeg; }
    public void setTeljesitesIgazolasonSzereploOsszeg(BigDecimalFilter teljesitesIgazolasonSzereploOsszeg) { this.teljesitesIgazolasonSzereploOsszeg = teljesitesIgazolasonSzereploOsszeg; }

    public BooleanFilter getBejovoSzamlaSorszamRogzitve() { return bejovoSzamlaSorszamRogzitve; }
    public Optional<BooleanFilter> optionalBejovoSzamlaSorszamRogzitve() { return Optional.ofNullable(bejovoSzamlaSorszamRogzitve); }
    public BooleanFilter bejovoSzamlaSorszamRogzitve() { if (bejovoSzamlaSorszamRogzitve == null) setBejovoSzamlaSorszamRogzitve(new BooleanFilter()); return bejovoSzamlaSorszamRogzitve; }
    public void setBejovoSzamlaSorszamRogzitve(BooleanFilter bejovoSzamlaSorszamRogzitve) { this.bejovoSzamlaSorszamRogzitve = bejovoSzamlaSorszamRogzitve; }

    public StringFilter getBejovoSzamlaSorszam() { return bejovoSzamlaSorszam; }
    public Optional<StringFilter> optionalBejovoSzamlaSorszam() { return Optional.ofNullable(bejovoSzamlaSorszam); }
    public StringFilter bejovoSzamlaSorszam() { if (bejovoSzamlaSorszam == null) setBejovoSzamlaSorszam(new StringFilter()); return bejovoSzamlaSorszam; }
    public void setBejovoSzamlaSorszam(StringFilter bejovoSzamlaSorszam) { this.bejovoSzamlaSorszam = bejovoSzamlaSorszam; }

    public LongFilter getMegrendelesId() { return megrendelesId; }
    public Optional<LongFilter> optionalMegrendelesId() { return Optional.ofNullable(megrendelesId); }
    public LongFilter megrendelesId() { if (megrendelesId == null) setMegrendelesId(new LongFilter()); return megrendelesId; }
    public void setMegrendelesId(LongFilter megrendelesId) { this.megrendelesId = megrendelesId; }

    public Boolean getDistinct() { return distinct; }
    public Optional<Boolean> optionalDistinct() { return Optional.ofNullable(distinct); }
    public Boolean distinct() { if (distinct == null) setDistinct(true); return distinct; }
    public void setDistinct(Boolean distinct) { this.distinct = distinct; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlvallalkozoiElszamolasokCriteria)) return false;
        AlvallalkozoiElszamolasokCriteria that = (AlvallalkozoiElszamolasokCriteria) o;
        return Objects.equals(id, that.id)
            && Objects.equals(teljesitesiIdoszakKezdete, that.teljesitesiIdoszakKezdete)
            && Objects.equals(teljesitesiIdoszakVege, that.teljesitesiIdoszakVege)
            && Objects.equals(napokSzama, that.napokSzama)
            && Objects.equals(teljesitesIgazolasonSzereploOsszeg, that.teljesitesIgazolasonSzereploOsszeg)
            && Objects.equals(bejovoSzamlaSorszamRogzitve, that.bejovoSzamlaSorszamRogzitve)
            && Objects.equals(bejovoSzamlaSorszam, that.bejovoSzamlaSorszam)
            && Objects.equals(megrendelesId, that.megrendelesId)
            && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teljesitesiIdoszakKezdete, teljesitesiIdoszakVege, napokSzama,
            teljesitesIgazolasonSzereploOsszeg, bejovoSzamlaSorszamRogzitve, bejovoSzamlaSorszam, megrendelesId, distinct);
    }

    @Override
    public String toString() {
        return "AlvallalkozoiElszamolasokCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMegrendelesId().map(f -> "megrendelesId=" + f + ", ").orElse("") +
            "}";
    }
}

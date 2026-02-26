package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesekDokumentum} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OsztalekfizetesiKozgyulesekDokumentumDTO implements Serializable {

    private Long id;

    private String dokumentumTipusa;

    private String dokumentumNev;

    private String fajlNev;

    private String dokumentumUrl;

    private String dokumentumAzonosito;

    private Instant feltoltesIdeje;

    /** Only id is populated (to avoid over-fetching). */
    private OsztalekfizetesiKozgyulesekDTO kozgyules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public String getDokumentumNev() {
        return dokumentumNev;
    }

    public void setDokumentumNev(String dokumentumNev) {
        this.dokumentumNev = dokumentumNev;
    }

    public String getFajlNev() {
        return fajlNev;
    }

    public void setFajlNev(String fajlNev) {
        this.fajlNev = fajlNev;
    }

    public String getDokumentumUrl() {
        return dokumentumUrl;
    }

    public void setDokumentumUrl(String dokumentumUrl) {
        this.dokumentumUrl = dokumentumUrl;
    }

    public String getDokumentumAzonosito() {
        return dokumentumAzonosito;
    }

    public void setDokumentumAzonosito(String dokumentumAzonosito) {
        this.dokumentumAzonosito = dokumentumAzonosito;
    }

    public Instant getFeltoltesIdeje() {
        return feltoltesIdeje;
    }

    public void setFeltoltesIdeje(Instant feltoltesIdeje) {
        this.feltoltesIdeje = feltoltesIdeje;
    }

    public OsztalekfizetesiKozgyulesekDTO getKozgyules() {
        return kozgyules;
    }

    public void setKozgyules(OsztalekfizetesiKozgyulesekDTO kozgyules) {
        this.kozgyules = kozgyules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OsztalekfizetesiKozgyulesekDokumentumDTO)) return false;
        OsztalekfizetesiKozgyulesekDokumentumDTO other = (OsztalekfizetesiKozgyulesekDokumentumDTO) o;
        if (this.id == null) return false;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "OsztalekfizetesiKozgyulesekDokumentumDTO{" +
            "id=" +
            getId() +
            ", dokumentumTipusa='" +
            getDokumentumTipusa() +
            "'" +
            ", dokumentumNev='" +
            getDokumentumNev() +
            "'" +
            ", dokumentumAzonosito='" +
            getDokumentumAzonosito() +
            "'" +
            ", feltoltesIdeje='" +
            getFeltoltesIdeje() +
            "'" +
            ", kozgyulesId=" +
            (kozgyules != null ? kozgyules.getId() : null) +
            "}"
        );
    }
}

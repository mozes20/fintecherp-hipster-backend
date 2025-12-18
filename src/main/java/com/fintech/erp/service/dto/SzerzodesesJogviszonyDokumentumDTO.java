package com.fintech.erp.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SzerzodesesJogviszonyDokumentum} entity.
 */
public class SzerzodesesJogviszonyDokumentumDTO implements Serializable {

    private Long id;

    @NotBlank
    private String dokumentumNev;

    private String leiras;

    @NotBlank
    private String fajlUtvonal;

    private String contentType;

    private Instant feltoltesIdeje;

    @NotBlank
    private String dokumentumTipus;

    private SzerzodesesJogviszonyokDTO szerzodesesJogviszony;

    @NotNull
    private Long szerzodesesJogviszonyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumentumNev() {
        return dokumentumNev;
    }

    public void setDokumentumNev(String dokumentumNev) {
        this.dokumentumNev = dokumentumNev;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getFajlUtvonal() {
        return fajlUtvonal;
    }

    public void setFajlUtvonal(String fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Instant getFeltoltesIdeje() {
        return feltoltesIdeje;
    }

    public void setFeltoltesIdeje(Instant feltoltesIdeje) {
        this.feltoltesIdeje = feltoltesIdeje;
    }

    public String getDokumentumTipus() {
        return dokumentumTipus;
    }

    public void setDokumentumTipus(String dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }

    public SzerzodesesJogviszonyokDTO getSzerzodesesJogviszony() {
        return szerzodesesJogviszony;
    }

    public void setSzerzodesesJogviszony(SzerzodesesJogviszonyokDTO szerzodesesJogviszony) {
        this.szerzodesesJogviszony = szerzodesesJogviszony;
    }

    public Long getSzerzodesesJogviszonyId() {
        return szerzodesesJogviszonyId;
    }

    public void setSzerzodesesJogviszonyId(Long szerzodesesJogviszonyId) {
        this.szerzodesesJogviszonyId = szerzodesesJogviszonyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesesJogviszonyDokumentumDTO)) {
            return false;
        }
        SzerzodesesJogviszonyDokumentumDTO that = (SzerzodesesJogviszonyDokumentumDTO) o;
        if (id == null) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return (
            "SzerzodesesJogviszonyDokumentumDTO{" +
            "id=" +
            id +
            ", dokumentumNev='" +
            dokumentumNev +
            '\'' +
            ", fajlUtvonal='" +
            fajlUtvonal +
            '\'' +
            ", dokumentumTipus='" +
            dokumentumTipus +
            '\'' +
            ", szerzodesesJogviszonyId=" +
            szerzodesesJogviszonyId +
            '}'
        );
    }
}

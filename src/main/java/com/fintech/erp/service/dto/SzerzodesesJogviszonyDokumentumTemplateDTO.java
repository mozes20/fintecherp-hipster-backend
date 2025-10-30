package com.fintech.erp.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.SzerzodesesJogviszonyDokumentumTemplate} entity.
 */
public class SzerzodesesJogviszonyDokumentumTemplateDTO implements Serializable {

    private Long id;

    @NotBlank
    private String templateNev;

    private String templateLeiras;

    @NotBlank
    private String fajlUtvonal;

    private Instant utolsoModositas;

    private SzerzodesDokumentumTipusDTO dokumentumTipus;

    @NotNull
    private Long dokumentumTipusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateNev() {
        return templateNev;
    }

    public void setTemplateNev(String templateNev) {
        this.templateNev = templateNev;
    }

    public String getTemplateLeiras() {
        return templateLeiras;
    }

    public void setTemplateLeiras(String templateLeiras) {
        this.templateLeiras = templateLeiras;
    }

    public String getFajlUtvonal() {
        return fajlUtvonal;
    }

    public void setFajlUtvonal(String fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    public Instant getUtolsoModositas() {
        return utolsoModositas;
    }

    public void setUtolsoModositas(Instant utolsoModositas) {
        this.utolsoModositas = utolsoModositas;
    }

    public SzerzodesDokumentumTipusDTO getDokumentumTipus() {
        return dokumentumTipus;
    }

    public void setDokumentumTipus(SzerzodesDokumentumTipusDTO dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }

    public Long getDokumentumTipusId() {
        return dokumentumTipusId;
    }

    public void setDokumentumTipusId(Long dokumentumTipusId) {
        this.dokumentumTipusId = dokumentumTipusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesesJogviszonyDokumentumTemplateDTO)) {
            return false;
        }
        SzerzodesesJogviszonyDokumentumTemplateDTO that = (SzerzodesesJogviszonyDokumentumTemplateDTO) o;
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
            "SzerzodesesJogviszonyDokumentumTemplateDTO{" +
            "id=" +
            id +
            ", templateNev='" +
            templateNev +
            '\'' +
            ", fajlUtvonal='" +
            fajlUtvonal +
            '\'' +
            ", dokumentumTipusId=" +
            dokumentumTipusId +
            '}'
        );
    }
}

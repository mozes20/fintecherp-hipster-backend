package com.fintech.erp.service.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.fintech.erp.domain.EfoDokumentumTemplate}.
 */
public class EfoDokumentumTemplateDTO implements Serializable {

    private Long id;

    @NotBlank
    private String templateNev;

    private String templateLeiras;

    @NotBlank
    private String fajlUtvonal;

    private Instant utolsoModositas;

    @NotBlank
    private String dokumentumTipus;

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

    public String getDokumentumTipus() {
        return dokumentumTipus;
    }

    public void setDokumentumTipus(String dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EfoDokumentumTemplateDTO other)) {
            return false;
        }
        if (id == null) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return (
            "EfoDokumentumTemplateDTO{" +
            "id=" +
            id +
            ", templateNev='" +
            templateNev +
            '\'' +
            ", dokumentumTipus='" +
            dokumentumTipus +
            '\'' +
            "}"
        );
    }
}

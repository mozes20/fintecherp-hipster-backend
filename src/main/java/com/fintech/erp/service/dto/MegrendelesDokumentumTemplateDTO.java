package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.MegrendelesDokumentumTemplate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesDokumentumTemplateDTO implements Serializable {

    private Long id;

    private String templateNev;

    private String templateLeiras;

    private String fajlUtvonal;

    private String dokumentumTipusa;

    private Instant utolsoModositas;

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

    public String getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public Instant getUtolsoModositas() {
        return utolsoModositas;
    }

    public void setUtolsoModositas(Instant utolsoModositas) {
        this.utolsoModositas = utolsoModositas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MegrendelesDokumentumTemplateDTO)) {
            return false;
        }

        MegrendelesDokumentumTemplateDTO other = (MegrendelesDokumentumTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "MegrendelesDokumentumTemplateDTO{" +
            "id=" +
            getId() +
            ", templateNev='" +
            getTemplateNev() +
            '\'' +
            ", templateLeiras='" +
            getTemplateLeiras() +
            '\'' +
            ", fajlUtvonal='" +
            getFajlUtvonal() +
            '\'' +
            ", dokumentumTipusa='" +
            getDokumentumTipusa() +
            '\'' +
            ", utolsoModositas=" +
            getUtolsoModositas() +
            '}'
        );
    }
}

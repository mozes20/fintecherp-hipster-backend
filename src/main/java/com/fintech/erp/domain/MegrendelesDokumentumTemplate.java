package com.fintech.erp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MegrendelesDokumentumTemplate.
 */
@Entity
@Table(name = "megrendeles_dokumentum_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MegrendelesDokumentumTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "template_nev", nullable = false)
    private String templateNev;

    @Column(name = "template_leiras", length = 1024)
    private String templateLeiras;

    @NotBlank
    @Column(name = "fajl_utvonal", nullable = false, length = 1024)
    private String fajlUtvonal;

    @Column(name = "dokumentum_tipusa", length = 128)
    private String dokumentumTipusa;

    @Column(name = "utolso_modositas")
    private Instant utolsoModositas = Instant.now();

    @PrePersist
    @PreUpdate
    protected void onPersistOrUpdate() {
        if (utolsoModositas == null) {
            utolsoModositas = Instant.now();
        } else {
            utolsoModositas = Instant.now();
        }
    }

    public Long getId() {
        return this.id;
    }

    public MegrendelesDokumentumTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateNev() {
        return this.templateNev;
    }

    public MegrendelesDokumentumTemplate templateNev(String templateNev) {
        this.setTemplateNev(templateNev);
        return this;
    }

    public void setTemplateNev(String templateNev) {
        this.templateNev = templateNev;
    }

    public String getTemplateLeiras() {
        return this.templateLeiras;
    }

    public MegrendelesDokumentumTemplate templateLeiras(String templateLeiras) {
        this.setTemplateLeiras(templateLeiras);
        return this;
    }

    public void setTemplateLeiras(String templateLeiras) {
        this.templateLeiras = templateLeiras;
    }

    public String getFajlUtvonal() {
        return this.fajlUtvonal;
    }

    public MegrendelesDokumentumTemplate fajlUtvonal(String fajlUtvonal) {
        this.setFajlUtvonal(fajlUtvonal);
        return this;
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

    public MegrendelesDokumentumTemplate dokumentumTipusa(String dokumentumTipusa) {
        this.setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public Instant getUtolsoModositas() {
        return this.utolsoModositas;
    }

    public void setUtolsoModositas(Instant utolsoModositas) {
        this.utolsoModositas = utolsoModositas;
    }

    public MegrendelesDokumentumTemplate utolsoModositas(Instant utolsoModositas) {
        this.setUtolsoModositas(utolsoModositas);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MegrendelesDokumentumTemplate)) {
            return false;
        }
        return getId() != null && getId().equals(((MegrendelesDokumentumTemplate) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "MegrendelesDokumentumTemplate{" +
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

package com.fintech.erp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * EFO specific document template metadata.
 */
@Entity
@Table(name = "efo_dokumentum_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EfoDokumentumTemplate implements Serializable {

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
    @Column(name = "fajl_utvonal", nullable = false)
    private String fajlUtvonal;

    @Column(name = "utolso_modositas")
    private Instant utolsoModositas = Instant.now();

    @NotBlank
    @Column(name = "dokumentum_tipus", nullable = false)
    private String dokumentumTipus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EfoDokumentumTemplate id(Long id) {
        setId(id);
        return this;
    }

    public String getTemplateNev() {
        return templateNev;
    }

    public void setTemplateNev(String templateNev) {
        this.templateNev = templateNev;
    }

    public EfoDokumentumTemplate templateNev(String templateNev) {
        setTemplateNev(templateNev);
        return this;
    }

    public String getTemplateLeiras() {
        return templateLeiras;
    }

    public void setTemplateLeiras(String templateLeiras) {
        this.templateLeiras = templateLeiras;
    }

    public EfoDokumentumTemplate templateLeiras(String templateLeiras) {
        setTemplateLeiras(templateLeiras);
        return this;
    }

    public String getFajlUtvonal() {
        return fajlUtvonal;
    }

    public void setFajlUtvonal(String fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    public EfoDokumentumTemplate fajlUtvonal(String fajlUtvonal) {
        setFajlUtvonal(fajlUtvonal);
        return this;
    }

    public Instant getUtolsoModositas() {
        return utolsoModositas;
    }

    public void setUtolsoModositas(Instant utolsoModositas) {
        this.utolsoModositas = utolsoModositas;
    }

    public EfoDokumentumTemplate utolsoModositas(Instant utolsoModositas) {
        setUtolsoModositas(utolsoModositas);
        return this;
    }

    public String getDokumentumTipus() {
        return dokumentumTipus;
    }

    public void setDokumentumTipus(String dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }

    public EfoDokumentumTemplate dokumentumTipus(String dokumentumTipus) {
        setDokumentumTipus(dokumentumTipus);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EfoDokumentumTemplate other)) {
            return false;
        }
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "EfoDokumentumTemplate{" +
            "id=" +
            id +
            ", templateNev='" +
            templateNev +
            '\'' +
            ", fajlUtvonal='" +
            fajlUtvonal +
            '\'' +
            ", dokumentumTipus='" +
            dokumentumTipus +
            '\'' +
            "}"
        );
    }
}

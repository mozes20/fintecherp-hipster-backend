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
 * A SzerzodesesJogviszonyDokumentumTemplate.
 */
@Entity
@Table(name = "szerzodeses_jogviszony_dokumentum_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SzerzodesesJogviszonyDokumentumTemplate implements Serializable {

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
        return this.id;
    }

    public SzerzodesesJogviszonyDokumentumTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateNev() {
        return this.templateNev;
    }

    public SzerzodesesJogviszonyDokumentumTemplate templateNev(String templateNev) {
        this.setTemplateNev(templateNev);
        return this;
    }

    public void setTemplateNev(String templateNev) {
        this.templateNev = templateNev;
    }

    public String getTemplateLeiras() {
        return this.templateLeiras;
    }

    public SzerzodesesJogviszonyDokumentumTemplate templateLeiras(String templateLeiras) {
        this.setTemplateLeiras(templateLeiras);
        return this;
    }

    public void setTemplateLeiras(String templateLeiras) {
        this.templateLeiras = templateLeiras;
    }

    public String getFajlUtvonal() {
        return this.fajlUtvonal;
    }

    public SzerzodesesJogviszonyDokumentumTemplate fajlUtvonal(String fajlUtvonal) {
        this.setFajlUtvonal(fajlUtvonal);
        return this;
    }

    public void setFajlUtvonal(String fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    public Instant getUtolsoModositas() {
        return this.utolsoModositas;
    }

    public void setUtolsoModositas(Instant utolsoModositas) {
        this.utolsoModositas = utolsoModositas;
    }

    public SzerzodesesJogviszonyDokumentumTemplate utolsoModositas(Instant utolsoModositas) {
        this.setUtolsoModositas(utolsoModositas);
        return this;
    }

    public String getDokumentumTipus() {
        return this.dokumentumTipus;
    }

    public void setDokumentumTipus(String dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }

    public SzerzodesesJogviszonyDokumentumTemplate dokumentumTipus(String dokumentumTipus) {
        this.setDokumentumTipus(dokumentumTipus);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesesJogviszonyDokumentumTemplate)) {
            return false;
        }
        return getId() != null && getId().equals(((SzerzodesesJogviszonyDokumentumTemplate) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "SzerzodesesJogviszonyDokumentumTemplate{" +
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
            ", utolsoModositas=" +
            getUtolsoModositas() +
            '}'
        );
    }
}

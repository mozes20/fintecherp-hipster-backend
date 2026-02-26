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
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Document template for OsztalekfizetesiKozgyulesek (assembly meeting minutes).
 */
@Entity
@Table(name = "osztalekfizetesi_kozgyulesek_dokumentum_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OsztalekfizetesiKozgyulesekDokumentumTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "template_nev", nullable = false)
    private String templateNev;

    @Column(name = "template_leiras", length = 1024)
    private String templateLeiras;

    @Column(name = "fajl_utvonal", nullable = false, length = 1024)
    private String fajlUtvonal;

    @Column(name = "dokumentum_tipusa", length = 128)
    private String dokumentumTipusa;

    @Column(name = "utolso_modositas")
    private Instant utolsoModositas = Instant.now();

    @PrePersist
    @PreUpdate
    protected void onPersistOrUpdate() {
        utolsoModositas = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplate id(Long id) {
        setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateNev() {
        return templateNev;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplate templateNev(String templateNev) {
        setTemplateNev(templateNev);
        return this;
    }

    public void setTemplateNev(String templateNev) {
        this.templateNev = templateNev;
    }

    public String getTemplateLeiras() {
        return templateLeiras;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplate templateLeiras(String templateLeiras) {
        setTemplateLeiras(templateLeiras);
        return this;
    }

    public void setTemplateLeiras(String templateLeiras) {
        this.templateLeiras = templateLeiras;
    }

    public String getFajlUtvonal() {
        return fajlUtvonal;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplate fajlUtvonal(String fajlUtvonal) {
        setFajlUtvonal(fajlUtvonal);
        return this;
    }

    public void setFajlUtvonal(String fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    public String getDokumentumTipusa() {
        return dokumentumTipusa;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplate dokumentumTipusa(String dokumentumTipusa) {
        setDokumentumTipusa(dokumentumTipusa);
        return this;
    }

    public void setDokumentumTipusa(String dokumentumTipusa) {
        this.dokumentumTipusa = dokumentumTipusa;
    }

    public Instant getUtolsoModositas() {
        return utolsoModositas;
    }

    public OsztalekfizetesiKozgyulesekDokumentumTemplate utolsoModositas(Instant utolsoModositas) {
        setUtolsoModositas(utolsoModositas);
        return this;
    }

    public void setUtolsoModositas(Instant utolsoModositas) {
        this.utolsoModositas = utolsoModositas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsztalekfizetesiKozgyulesekDokumentumTemplate)) {
            return false;
        }
        return getId() != null && getId().equals(((OsztalekfizetesiKozgyulesekDokumentumTemplate) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsztalekfizetesiKozgyulesekDokumentumTemplate{" +
            "id=" + getId() +
            ", templateNev='" + getTemplateNev() + "'" +
            ", dokumentumTipusa='" + getDokumentumTipusa() + "'" +
            ", utolsoModositas='" + getUtolsoModositas() + "'" +
            "}";
    }
}

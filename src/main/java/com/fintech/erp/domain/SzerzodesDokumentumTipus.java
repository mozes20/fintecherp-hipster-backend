package com.fintech.erp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SzerzodesDokumentumTipus.
 */
@Entity
@Table(name = "szerzodes_dokumentum_tipus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SzerzodesDokumentumTipus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nev", nullable = false, unique = true)
    private String nev;

    @Column(name = "leiras", length = 1024)
    private String leiras;

    @OneToMany(mappedBy = "dokumentumTipus", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SzerzodesesJogviszonyDokumentumTemplate> templatek = new HashSet<>();

    @OneToMany(mappedBy = "dokumentumTipus", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SzerzodesesJogviszonyDokumentum> dokumentumok = new HashSet<>();

    public Long getId() {
        return this.id;
    }

    public SzerzodesDokumentumTipus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return this.nev;
    }

    public SzerzodesDokumentumTipus nev(String nev) {
        this.setNev(nev);
        return this;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return this.leiras;
    }

    public SzerzodesDokumentumTipus leiras(String leiras) {
        this.setLeiras(leiras);
        return this;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public Set<SzerzodesesJogviszonyDokumentumTemplate> getTemplatek() {
        return this.templatek;
    }

    public void setTemplatek(Set<SzerzodesesJogviszonyDokumentumTemplate> templatek) {
        if (this.templatek != null) {
            this.templatek.forEach(template -> template.setDokumentumTipus(null));
        }
        if (templatek != null) {
            templatek.forEach(template -> template.setDokumentumTipus(this));
        }
        this.templatek = templatek;
    }

    public SzerzodesDokumentumTipus templatek(Set<SzerzodesesJogviszonyDokumentumTemplate> templatek) {
        this.setTemplatek(templatek);
        return this;
    }

    public Set<SzerzodesesJogviszonyDokumentum> getDokumentumok() {
        return this.dokumentumok;
    }

    public void setDokumentumok(Set<SzerzodesesJogviszonyDokumentum> dokumentumok) {
        if (this.dokumentumok != null) {
            this.dokumentumok.forEach(dokumentum -> dokumentum.setDokumentumTipus(null));
        }
        if (dokumentumok != null) {
            dokumentumok.forEach(dokumentum -> dokumentum.setDokumentumTipus(this));
        }
        this.dokumentumok = dokumentumok;
    }

    public SzerzodesDokumentumTipus dokumentumok(Set<SzerzodesesJogviszonyDokumentum> dokumentumok) {
        this.setDokumentumok(dokumentumok);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SzerzodesDokumentumTipus)) {
            return false;
        }
        return getId() != null && getId().equals(((SzerzodesDokumentumTipus) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "SzerzodesDokumentumTipus{" + "id=" + getId() + ", nev='" + getNev() + '\'' + ", leiras='" + getLeiras() + '\'' + '}';
    }
}

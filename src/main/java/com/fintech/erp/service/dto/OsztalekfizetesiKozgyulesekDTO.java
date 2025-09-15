package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesek} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OsztalekfizetesiKozgyulesekDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate kozgyulesDatum;

    private Boolean kozgyulesiJegyzokonyvGeneralta;

    private Boolean kozgyulesiJegyzokonyvAlairt;

    private SajatCegAlapadatokDTO sajatCeg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getKozgyulesDatum() {
        return kozgyulesDatum;
    }

    public void setKozgyulesDatum(LocalDate kozgyulesDatum) {
        this.kozgyulesDatum = kozgyulesDatum;
    }

    public Boolean getKozgyulesiJegyzokonyvGeneralta() {
        return kozgyulesiJegyzokonyvGeneralta;
    }

    public void setKozgyulesiJegyzokonyvGeneralta(Boolean kozgyulesiJegyzokonyvGeneralta) {
        this.kozgyulesiJegyzokonyvGeneralta = kozgyulesiJegyzokonyvGeneralta;
    }

    public Boolean getKozgyulesiJegyzokonyvAlairt() {
        return kozgyulesiJegyzokonyvAlairt;
    }

    public void setKozgyulesiJegyzokonyvAlairt(Boolean kozgyulesiJegyzokonyvAlairt) {
        this.kozgyulesiJegyzokonyvAlairt = kozgyulesiJegyzokonyvAlairt;
    }

    public SajatCegAlapadatokDTO getSajatCeg() {
        return sajatCeg;
    }

    public void setSajatCeg(SajatCegAlapadatokDTO sajatCeg) {
        this.sajatCeg = sajatCeg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsztalekfizetesiKozgyulesekDTO)) {
            return false;
        }

        OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO = (OsztalekfizetesiKozgyulesekDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, osztalekfizetesiKozgyulesekDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsztalekfizetesiKozgyulesekDTO{" +
            "id=" + getId() +
            ", kozgyulesDatum='" + getKozgyulesDatum() + "'" +
            ", kozgyulesiJegyzokonyvGeneralta='" + getKozgyulesiJegyzokonyvGeneralta() + "'" +
            ", kozgyulesiJegyzokonyvAlairt='" + getKozgyulesiJegyzokonyvAlairt() + "'" +
            ", sajatCeg=" + getSajatCeg() +
            "}";
    }
}

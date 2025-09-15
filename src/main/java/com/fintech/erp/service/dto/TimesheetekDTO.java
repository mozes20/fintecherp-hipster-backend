package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Timesheetek} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimesheetekDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate datum;

    private String munkanapStatusza;

    private String statusz;

    private MunkavallalokDTO munkavallalo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getMunkanapStatusza() {
        return munkanapStatusza;
    }

    public void setMunkanapStatusza(String munkanapStatusza) {
        this.munkanapStatusza = munkanapStatusza;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public MunkavallalokDTO getMunkavallalo() {
        return munkavallalo;
    }

    public void setMunkavallalo(MunkavallalokDTO munkavallalo) {
        this.munkavallalo = munkavallalo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimesheetekDTO)) {
            return false;
        }

        TimesheetekDTO timesheetekDTO = (TimesheetekDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timesheetekDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimesheetekDTO{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", munkanapStatusza='" + getMunkanapStatusza() + "'" +
            ", statusz='" + getStatusz() + "'" +
            ", munkavallalo=" + getMunkavallalo() +
            "}";
    }
}

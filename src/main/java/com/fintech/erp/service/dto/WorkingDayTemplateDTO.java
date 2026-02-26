package com.fintech.erp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.WorkingDayTemplate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingDayTemplateDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 7)
    private String yearMonth;

    @NotNull
    private String workingDaysConfig;

    @Size(max = 20)
    private String status;

    private String notes;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getWorkingDaysConfig() {
        return workingDaysConfig;
    }

    public void setWorkingDaysConfig(String workingDaysConfig) {
        this.workingDaysConfig = workingDaysConfig;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingDayTemplateDTO)) {
            return false;
        }

        WorkingDayTemplateDTO workingDayTemplateDTO = (WorkingDayTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workingDayTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "WorkingDayTemplateDTO{" +
            "id=" +
            getId() +
            ", yearMonth='" +
            getYearMonth() +
            "'" +
            ", workingDaysConfig='" +
            getWorkingDaysConfig() +
            "'" +
            ", status='" +
            getStatus() +
            "'" +
            ", notes='" +
            getNotes() +
            "'" +
            ", createdBy='" +
            getCreatedBy() +
            "'" +
            ", createdDate='" +
            getCreatedDate() +
            "'" +
            ", lastModifiedBy='" +
            getLastModifiedBy() +
            "'" +
            ", lastModifiedDate='" +
            getLastModifiedDate() +
            "'" +
            "}"
        );
    }
}

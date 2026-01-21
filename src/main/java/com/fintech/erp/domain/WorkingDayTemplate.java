package com.fintech.erp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * A WorkingDayTemplate - Havi munkanap sablon admin által.
 * Tárolja az adott hónapra vonatkozó munkanapokat JSON formátumban.
 */
@Entity
@Table(name = "working_day_template", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "year_month" })
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingDayTemplate extends AbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * A hónap (év-hónap formátumban, pl. 2026-01)
     */
    @NotNull
    @Column(name = "year_month", nullable = false, unique = true, length = 7)
    private String yearMonth;

    /**
     * Munkanapok JSON formátumban
     * Példa: {"days": [{"date": "2026-01-02", "isWorkingDay": true, "description":
     * "Munkanap"}, ...]}
     */
    @NotNull
    @Column(name = "working_days_config", nullable = false, columnDefinition = "TEXT")
    @JdbcTypeCode(SqlTypes.JSON)
    private String workingDaysConfig;

    /**
     * Sablon státusza (DRAFT, ACTIVE, ARCHIVED)
     */
    @Column(name = "status", length = 20)
    private String status;

    /**
     * Megjegyzés a hónaphoz
     */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkingDayTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearMonth() {
        return this.yearMonth;
    }

    public WorkingDayTemplate yearMonth(String yearMonth) {
        this.setYearMonth(yearMonth);
        return this;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getWorkingDaysConfig() {
        return this.workingDaysConfig;
    }

    public WorkingDayTemplate workingDaysConfig(String workingDaysConfig) {
        this.setWorkingDaysConfig(workingDaysConfig);
        return this;
    }

    public void setWorkingDaysConfig(String workingDaysConfig) {
        this.workingDaysConfig = workingDaysConfig;
    }

    public String getStatus() {
        return this.status;
    }

    public WorkingDayTemplate status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public WorkingDayTemplate notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingDayTemplate)) {
            return false;
        }
        return getId() != null && getId().equals(((WorkingDayTemplate) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "WorkingDayTemplate{" +
                "id=" + getId() +
                ", yearMonth='" + getYearMonth() + "'" +
                ", workingDaysConfig='" + getWorkingDaysConfig() + "'" +
                ", status='" + getStatus() + "'" +
                ", notes='" + getNotes() + "'" +
                ", createdBy='" + getCreatedBy() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                ", lastModifiedBy='" + getLastModifiedBy() + "'" +
                ", lastModifiedDate='" + getLastModifiedDate() + "'" +
                "}";
    }
}

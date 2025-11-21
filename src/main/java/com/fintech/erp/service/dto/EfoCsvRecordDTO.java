package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EfoCsvRecordDTO implements Serializable {

    private int rowNumber;
    private String employeeName;
    private String taxId;
    private LocalDate employmentDate;
    private BigDecimal amount;
    private boolean workerExists;
    private Long munkavallaloId;
    private String statusMessage;

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isWorkerExists() {
        return workerExists;
    }

    public void setWorkerExists(boolean workerExists) {
        this.workerExists = workerExists;
    }

    public Long getMunkavallaloId() {
        return munkavallaloId;
    }

    public void setMunkavallaloId(Long munkavallaloId) {
        this.munkavallaloId = munkavallaloId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}

package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EfoCsvRecordDTO implements Serializable {

    private int rowNumber;
    private String employeeName;
    private String taxId;
    private LocalDate employmentDate;
    private LocalDate employmentEndDate;
    private BigDecimal amount;
    private Integer dayCount;
    private String birthName;
    private String birthPlace;
    private LocalDate birthDate;
    private String birthDateRaw;
    private String motherName;
    private String address;
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

    public LocalDate getEmploymentEndDate() {
        return employmentEndDate;
    }

    public void setEmploymentEndDate(LocalDate employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBirthDateRaw() {
        return birthDateRaw;
    }

    public void setBirthDateRaw(String birthDateRaw) {
        this.birthDateRaw = birthDateRaw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

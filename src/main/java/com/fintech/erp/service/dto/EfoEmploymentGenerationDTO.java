package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class EfoEmploymentGenerationDTO implements Serializable {

    private Long employmentId;
    private String employeeName;
    private String taxId;
    private LocalDate employmentDate;
    private LocalDate employmentEndDate;
    private Integer dayCount;
    private String birthName;
    private String birthPlace;
    private LocalDate birthDate;
    private String birthDateRaw;
    private String motherName;
    private String address;
    private String generatedDocumentName;
    private String generatedDocumentUrl;
    private Long workerId;

    public Long getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(Long employmentId) {
        this.employmentId = employmentId;
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

    public String getBirthDateRaw() {
        return birthDateRaw;
    }

    public void setBirthDateRaw(String birthDateRaw) {
        this.birthDateRaw = birthDateRaw;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeneratedDocumentName() {
        return generatedDocumentName;
    }

    public void setGeneratedDocumentName(String generatedDocumentName) {
        this.generatedDocumentName = generatedDocumentName;
    }

    public String getGeneratedDocumentUrl() {
        return generatedDocumentUrl;
    }

    public void setGeneratedDocumentUrl(String generatedDocumentUrl) {
        this.generatedDocumentUrl = generatedDocumentUrl;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }
}

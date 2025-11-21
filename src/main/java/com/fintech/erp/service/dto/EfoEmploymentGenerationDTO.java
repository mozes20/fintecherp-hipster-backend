package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class EfoEmploymentGenerationDTO implements Serializable {

    private Long employmentId;
    private String employeeName;
    private String taxId;
    private LocalDate employmentDate;
    private String generatedDocumentName;
    private String generatedDocumentUrl;

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
}

package com.fintech.erp.service.dto;

import java.io.Serializable;

public class EfoCsvMissingWorkerDTO implements Serializable {

    private String employeeName;
    private String taxId;

    public EfoCsvMissingWorkerDTO() {}

    public EfoCsvMissingWorkerDTO(String employeeName, String taxId) {
        this.employeeName = employeeName;
        this.taxId = taxId;
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
}

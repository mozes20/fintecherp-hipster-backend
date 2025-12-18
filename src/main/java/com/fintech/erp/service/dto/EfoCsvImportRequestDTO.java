package com.fintech.erp.service.dto;

import com.fintech.erp.service.document.DocumentFormat;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EfoCsvImportRequestDTO implements Serializable {

    @NotNull
    private Long sajatCegId;

    private DocumentFormat outputFormat = DocumentFormat.PDF;

    private boolean persist = true;

    private Map<String, Long> jobAssignments = new HashMap<>();

    private Long templateId;

    public Long getSajatCegId() {
        return sajatCegId;
    }

    public void setSajatCegId(Long sajatCegId) {
        this.sajatCegId = sajatCegId;
    }

    public DocumentFormat getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(DocumentFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public boolean isPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }

    public Map<String, Long> getJobAssignments() {
        return jobAssignments;
    }

    public void setJobAssignments(Map<String, Long> jobAssignments) {
        this.jobAssignments = jobAssignments;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}

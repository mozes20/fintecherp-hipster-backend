package com.fintech.erp.web.rest.vm;

import com.fintech.erp.service.document.DocumentFormat;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class DocumentGenerationRequest {

    @NotNull
    private Long templateId;

    private DocumentFormat format;

    private Map<String, String> placeholders;

    private boolean persist;

    private String dokumentumNev;

    private String dokumentumTipus;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public DocumentFormat getFormat() {
        return format;
    }

    public void setFormat(DocumentFormat format) {
        this.format = format;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    public boolean isPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }

    public String getDokumentumNev() {
        return dokumentumNev;
    }

    public void setDokumentumNev(String dokumentumNev) {
        this.dokumentumNev = dokumentumNev;
    }

    public String getDokumentumTipus() {
        return dokumentumTipus;
    }

    public void setDokumentumTipus(String dokumentumTipus) {
        this.dokumentumTipus = dokumentumTipus;
    }
}

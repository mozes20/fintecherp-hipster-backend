package com.fintech.erp.service.document;

public enum DocumentFormat {
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"),
    PDF("application/pdf", ".pdf");

    private final String contentType;
    private final String extension;

    DocumentFormat(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }
}

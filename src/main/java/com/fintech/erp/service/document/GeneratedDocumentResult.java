package com.fintech.erp.service.document;

public class GeneratedDocumentResult<T> {

    private final byte[] data;
    private final String fileName;
    private final String contentType;
    private final T persistedDocument;

    public GeneratedDocumentResult(byte[] data, String fileName, String contentType, T persistedDocument) {
        this.data = data;
        this.fileName = fileName;
        this.contentType = contentType;
        this.persistedDocument = persistedDocument;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public T getPersistedDocument() {
        return persistedDocument;
    }
}

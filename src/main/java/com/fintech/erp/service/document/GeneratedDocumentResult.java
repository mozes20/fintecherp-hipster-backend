package com.fintech.erp.service.document;

import com.fintech.erp.service.dto.SzerzodesesJogviszonyDokumentumDTO;

public class GeneratedDocumentResult {

    private final byte[] data;
    private final String fileName;
    private final String contentType;
    private final SzerzodesesJogviszonyDokumentumDTO persistedDocument;

    public GeneratedDocumentResult(byte[] data, String fileName, String contentType, SzerzodesesJogviszonyDokumentumDTO persistedDocument) {
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

    public SzerzodesesJogviszonyDokumentumDTO getPersistedDocument() {
        return persistedDocument;
    }
}

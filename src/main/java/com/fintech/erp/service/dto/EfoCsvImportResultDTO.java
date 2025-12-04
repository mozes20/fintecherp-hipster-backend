package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EfoCsvImportResultDTO implements Serializable {

    private final List<EfoEmploymentGenerationDTO> generated = new ArrayList<>();
    private final List<EfoCsvRecordDTO> skipped = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();
    private String zipArchiveName;
    private String zipArchiveUrl;

    public List<EfoEmploymentGenerationDTO> getGenerated() {
        return generated;
    }

    public List<EfoCsvRecordDTO> getSkipped() {
        return skipped;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addGenerated(EfoEmploymentGenerationDTO dto) {
        generated.add(dto);
    }

    public void addSkipped(EfoCsvRecordDTO dto) {
        skipped.add(dto);
    }

    public void addError(String error) {
        errors.add(error);
    }

    public String getZipArchiveName() {
        return zipArchiveName;
    }

    public void setZipArchiveName(String zipArchiveName) {
        this.zipArchiveName = zipArchiveName;
    }

    public String getZipArchiveUrl() {
        return zipArchiveUrl;
    }

    public void setZipArchiveUrl(String zipArchiveUrl) {
        this.zipArchiveUrl = zipArchiveUrl;
    }
}

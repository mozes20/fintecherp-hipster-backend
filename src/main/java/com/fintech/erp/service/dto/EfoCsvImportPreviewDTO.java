package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EfoCsvImportPreviewDTO implements Serializable {

    private final List<EfoCsvRecordDTO> records = new ArrayList<>();
    private final List<EfoCsvMissingWorkerDTO> missingWorkers = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    public List<EfoCsvRecordDTO> getRecords() {
        return records;
    }

    public List<EfoCsvMissingWorkerDTO> getMissingWorkers() {
        return missingWorkers;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addRecord(EfoCsvRecordDTO dto) {
        records.add(dto);
    }

    public void addMissingWorker(EfoCsvMissingWorkerDTO dto) {
        missingWorkers.add(dto);
    }

    public void addError(String error) {
        errors.add(error);
    }

    @Override
    public String toString() {
        return (
            "EfoCsvImportPreviewDTO{" +
            "records=" +
            records.size() +
            ", missingWorkers=" +
            missingWorkers.size() +
            ", errors=" +
            errors.size() +
            '}'
        );
    }
}

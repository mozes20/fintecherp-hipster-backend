package com.fintech.erp.service.document;

import java.time.LocalDate;

public class EfoCsvEmployeeDetails {

    private final String birthName;
    private final String birthPlace;
    private final LocalDate birthDate;
    private final String birthDateRaw;
    private final String motherName;
    private final String address;

    public EfoCsvEmployeeDetails(
        String birthName,
        String birthPlace,
        LocalDate birthDate,
        String birthDateRaw,
        String motherName,
        String address
    ) {
        this.birthName = birthName;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.birthDateRaw = birthDateRaw;
        this.motherName = motherName;
        this.address = address;
    }

    public String getBirthName() {
        return birthName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateRaw() {
        return birthDateRaw;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getAddress() {
        return address;
    }
}

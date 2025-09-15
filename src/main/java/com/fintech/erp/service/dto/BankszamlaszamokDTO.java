package com.fintech.erp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fintech.erp.domain.Bankszamlaszamok} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankszamlaszamokDTO implements Serializable {

    private Long id;

    private String bankszamlaDevizanem;

    private String bankszamlaGIRO;

    private String bankszamlaIBAN;

    private String statusz;

    private CegAlapadatokDTO ceg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankszamlaDevizanem() {
        return bankszamlaDevizanem;
    }

    public void setBankszamlaDevizanem(String bankszamlaDevizanem) {
        this.bankszamlaDevizanem = bankszamlaDevizanem;
    }

    public String getBankszamlaGIRO() {
        return bankszamlaGIRO;
    }

    public void setBankszamlaGIRO(String bankszamlaGIRO) {
        this.bankszamlaGIRO = bankszamlaGIRO;
    }

    public String getBankszamlaIBAN() {
        return bankszamlaIBAN;
    }

    public void setBankszamlaIBAN(String bankszamlaIBAN) {
        this.bankszamlaIBAN = bankszamlaIBAN;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public CegAlapadatokDTO getCeg() {
        return ceg;
    }

    public void setCeg(CegAlapadatokDTO ceg) {
        this.ceg = ceg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankszamlaszamokDTO)) {
            return false;
        }

        BankszamlaszamokDTO bankszamlaszamokDTO = (BankszamlaszamokDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankszamlaszamokDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankszamlaszamokDTO{" +
            "id=" + getId() +
            ", bankszamlaDevizanem='" + getBankszamlaDevizanem() + "'" +
            ", bankszamlaGIRO='" + getBankszamlaGIRO() + "'" +
            ", bankszamlaIBAN='" + getBankszamlaIBAN() + "'" +
            ", statusz='" + getStatusz() + "'" +
            ", ceg=" + getCeg() +
            "}";
    }
}

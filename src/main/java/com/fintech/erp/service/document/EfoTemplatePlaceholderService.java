package com.fintech.erp.service.document;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.BankszamlaszamokRepository;
import com.fintech.erp.repository.SajatCegAlapadatokRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class EfoTemplatePlaceholderService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    private final SajatCegAlapadatokRepository sajatCegAlapadatokRepository;
    private final BankszamlaszamokRepository bankszamlaszamokRepository;

    public EfoTemplatePlaceholderService(
        SajatCegAlapadatokRepository sajatCegAlapadatokRepository,
        BankszamlaszamokRepository bankszamlaszamokRepository
    ) {
        this.sajatCegAlapadatokRepository = sajatCegAlapadatokRepository;
        this.bankszamlaszamokRepository = bankszamlaszamokRepository;
    }

    public Map<String, String> build(Long sajatCegId, Munkavallalok munkavallalo, Munkakorok munkakor, LocalDate datum, BigDecimal osszeg) {
        Map<String, String> placeholders = new LinkedHashMap<>();
        SajatCegAlapadatok sajatCeg = sajatCegId != null ? sajatCegAlapadatokRepository.findById(sajatCegId).orElse(null) : null;
        addCompany(placeholders, sajatCeg);
        addEmployee(placeholders, munkavallalo != null ? munkavallalo.getMaganszemely() : null);
        addJob(placeholders, munkakor);
        placeholders.put("efo.datum", datum != null ? DATE_FORMAT.format(datum) : "");
        placeholders.put("efo.osszeg", osszeg != null ? osszeg.stripTrailingZeros().toPlainString() : "");
        return placeholders;
    }

    private void addCompany(Map<String, String> target, SajatCegAlapadatok sajatCeg) {
        if (sajatCeg == null) {
            target.put("vallalkozo_ceg.nev", "");
            target.put("vallalkozo_ceg.adoszam", "");
            target.put("vallalkozo_ceg.cegjegyzekszam", "");
            target.put("vallalkozo_ceg.szekhely", "");
            target.put("vallalkozo_ceg.bankszamlaszam", "");
            target.put("vallalkozo_ceg.email", "");
            target.put("vallalkozo_ceg.telefon", "");
            return;
        }
        CegAlapadatok ceg = sajatCeg.getCeg();
        if (ceg != null) {
            target.put("vallalkozo_ceg.nev", defaultString(ceg.getCegNev()));
            target.put("vallalkozo_ceg.adoszam", defaultString(ceg.getAdoszam()));
            target.put("vallalkozo_ceg.cegjegyzekszam", defaultString(ceg.getCegjegyzekszam()));
            target.put("vallalkozo_ceg.szekhely", defaultString(ceg.getCegSzekhely()));
            target.put("vallalkozo_ceg.email", defaultString(ceg.getCegKozpontiEmail()));
            target.put("vallalkozo_ceg.telefon", defaultString(ceg.getCegKozpontiTel()));
        } else {
            target.put("vallalkozo_ceg.nev", "");
            target.put("vallalkozo_ceg.adoszam", "");
            target.put("vallalkozo_ceg.cegjegyzekszam", "");
            target.put("vallalkozo_ceg.szekhely", "");
            target.put("vallalkozo_ceg.email", "");
            target.put("vallalkozo_ceg.telefon", "");
        }
        target.put("vallalkozo_ceg.bankszamlaszam", resolveCompanyBankAccount(sajatCeg));
    }

    private void addEmployee(Map<String, String> target, Maganszemelyek maganszemely) {
        target.put("munkavallalo.nev", maganszemely != null ? defaultString(maganszemely.getMaganszemelyNeve()) : "");
        target.put("munkavallalo.ado_azonosito", maganszemely != null ? defaultString(maganszemely.getAdoAzonositoJel()) : "");
        target.put("munkavallalo.tb_azonosito", maganszemely != null ? defaultString(maganszemely.getTbAzonosito()) : "");
        target.put("munkavallalo.email", maganszemely != null ? defaultString(maganszemely.getEmailcim()) : "");
        target.put("munkavallalo.telefon", maganszemely != null ? defaultString(maganszemely.getTelefonszam()) : "");
        target.put("munkavallalo.bankszamlaszam", maganszemely != null ? defaultString(maganszemely.getBankszamlaszam()) : "");
    }

    private void addJob(Map<String, String> target, Munkakorok munkakor) {
        target.put("efo.munkakor.nev", munkakor != null ? defaultString(munkakor.getMunkakorNeve()) : "");
        target.put("efo.munkakor.kod", munkakor != null ? defaultString(munkakor.getMunkakorKod()) : "");
        target.put("efo.munkakor.feladat", munkakor != null ? defaultString(munkakor.getMunkakorFeladatai()) : "");
    }

    private String defaultString(String input) {
        return StringUtils.hasText(input) ? input : "";
    }

    private String resolveCompanyBankAccount(SajatCegAlapadatok sajatCeg) {
        if (sajatCeg == null || sajatCeg.getId() == null) {
            return "";
        }
        Long cegId = sajatCeg.getCeg() != null ? sajatCeg.getCeg().getId() : null;
        if (cegId == null) {
            return "";
        }
        return bankszamlaszamokRepository
            .findFirstByCegIdAndStatuszOrderByIdAsc(cegId, "AKTIV")
            .or(() -> bankszamlaszamokRepository.findFirstByCegIdOrderByIdAsc(cegId))
            .map(bank -> {
                if (StringUtils.hasText(bank.getBankszamlaIBAN())) {
                    return bank.getBankszamlaIBAN();
                }
                if (StringUtils.hasText(bank.getBankszamlaGIRO())) {
                    return bank.getBankszamlaGIRO();
                }
                return "";
            })
            .orElse("");
    }
}

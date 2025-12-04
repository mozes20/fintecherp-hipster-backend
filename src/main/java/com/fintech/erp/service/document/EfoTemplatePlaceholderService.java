package com.fintech.erp.service.document;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.BankszamlaszamokRepository;
import com.fintech.erp.repository.SajatCegAlapadatokRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class EfoTemplatePlaceholderService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    private static final String KEY_COMPANY_NAME = "vallalkozo_ceg.nev";
    private static final String KEY_COMPANY_TAX = "vallalkozo_ceg.adoszam";
    private static final String KEY_COMPANY_REGISTER = "vallalkozo_ceg.cegjegyzekszam";
    private static final String KEY_COMPANY_ADDRESS = "vallalkozo_ceg.szekhely";
    private static final String KEY_COMPANY_BANK = "vallalkozo_ceg.bankszamlaszam";
    private static final String KEY_COMPANY_EMAIL = "vallalkozo_ceg.email";
    private static final String KEY_COMPANY_PHONE = "vallalkozo_ceg.telefon";

    private static final String KEY_EMPLOYEE_NAME = "munkavallalo.nev";
    private static final String KEY_EMPLOYEE_TAX = "munkavallalo.ado_azonosito";
    private static final String KEY_EMPLOYEE_TB = "munkavallalo.tb_azonosito";
    private static final String KEY_EMPLOYEE_EMAIL = "munkavallalo.email";
    private static final String KEY_EMPLOYEE_PHONE = "munkavallalo.telefon";
    private static final String KEY_EMPLOYEE_BANK = "munkavallalo.bankszamlaszam";
    private static final String KEY_EMPLOYEE_BIRTH_NAME = "munkavallalo.szuletesi_nev";
    private static final String KEY_EMPLOYEE_BIRTH_PLACE = "munkavallalo.szuletesi_hely";
    private static final String KEY_EMPLOYEE_BIRTH_DATE = "munkavallalo.szuletesi_ido";
    private static final String KEY_EMPLOYEE_MOTHER_NAME = "munkavallalo.anyja_neve";
    private static final String KEY_EMPLOYEE_ADDRESS = "munkavallalo.lakcim";

    private static final String KEY_JOB_NAME = "efo.munkakor.nev";
    private static final String KEY_JOB_CODE = "efo.munkakor.kod";
    private static final String KEY_JOB_TASK = "efo.munkakor.feladat";

    private static final String KEY_EFO_DATE = "efo.datum";
    private static final String KEY_EFO_START_DATE = "efo.datum_kezdete";
    private static final String KEY_EFO_END_DATE = "efo.datum_vege";
    private static final String KEY_EFO_AMOUNT = "efo.osszeg";
    private static final String KEY_EFO_AMOUNT_IN_WORDS = "efo.osszeg_betukkel";
    private static final String KEY_CSV_TOTAL_AMOUNT = "csv.kifizetett_brutto_ber";
    private static final String KEY_CSV_TOTAL_AMOUNT_IN_WORDS = "csv.kifizetett_brutto_ber_betukkel";
    private static final String KEY_CSV_DAY_COUNT = "csv.napok_szama";

    private static final List<TemplatePlaceholderDefinition> DEFINITIONS;

    static {
        List<TemplatePlaceholderDefinition> defs = new ArrayList<>();
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_NAME, "Vállalkozó cég neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_TAX, "Vállalkozó cég adószáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_REGISTER, "Vállalkozó cég cégjegyzékszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_ADDRESS, "Vállalkozó cég székhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_BANK, "Vállalkozó cég bankszámlaszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_EMAIL, "Vállalkozó cég e-mail címe"));
        defs.add(new TemplatePlaceholderDefinition(KEY_COMPANY_PHONE, "Vállalkozó cég telefonszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_NAME, "Munkavállaló neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_TAX, "Munkavállaló adóazonosító jele"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_TB, "Munkavállaló TAJ száma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_EMAIL, "Munkavállaló e-mail címe"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_PHONE, "Munkavállaló telefonszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_BANK, "Munkavállaló bankszámlaszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_BIRTH_NAME, "Munkavállaló születési neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_BIRTH_PLACE, "Munkavállaló születési helye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_BIRTH_DATE, "Munkavállaló születési ideje"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_MOTHER_NAME, "Munkavállaló anyja neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EMPLOYEE_ADDRESS, "Munkavállaló lakcíme"));
        defs.add(new TemplatePlaceholderDefinition(KEY_JOB_NAME, "EFO munkakör megnevezése"));
        defs.add(new TemplatePlaceholderDefinition(KEY_JOB_CODE, "EFO munkakör kódja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_JOB_TASK, "EFO munkakör feladatai"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EFO_DATE, "Foglalkoztatás dátuma (yyyy.MM.dd.)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EFO_START_DATE, "Munkaviszony kezdete (yyyy.MM.dd.)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EFO_END_DATE, "Munkaviszony vége (yyyy.MM.dd.)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EFO_AMOUNT, "Kifizetés összege"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EFO_AMOUNT_IN_WORDS, "Kifizetés összege betűkkel"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CSV_TOTAL_AMOUNT, "CSV alapján kifizetett bruttó bér összesen"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CSV_TOTAL_AMOUNT_IN_WORDS, "CSV alapján kifizetett bruttó bér összesen betűkkel"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CSV_DAY_COUNT, "Napok száma összesen a CSV alapján"));
        DEFINITIONS = List.copyOf(defs);
    }

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
        return build(sajatCegId, munkavallalo, munkakor, datum, null, osszeg, null, null, null);
    }

    public Map<String, String> build(
        Long sajatCegId,
        Munkavallalok munkavallalo,
        Munkakorok munkakor,
        LocalDate datum,
        BigDecimal osszeg,
        BigDecimal osszesen
    ) {
        return build(sajatCegId, munkavallalo, munkakor, datum, null, osszeg, osszesen, null, null);
    }

    public Map<String, String> build(
        Long sajatCegId,
        Munkavallalok munkavallalo,
        Munkakorok munkakor,
        LocalDate datum,
        LocalDate datumVege,
        BigDecimal osszeg,
        BigDecimal osszesen,
        Integer napokSzama
    ) {
        return build(sajatCegId, munkavallalo, munkakor, datum, datumVege, osszeg, osszesen, napokSzama, null);
    }

    public Map<String, String> build(
        Long sajatCegId,
        Munkavallalok munkavallalo,
        Munkakorok munkakor,
        LocalDate datum,
        LocalDate datumVege,
        BigDecimal osszeg,
        BigDecimal osszesen,
        Integer napokSzama,
        EfoCsvEmployeeDetails csvDetails
    ) {
        Map<String, String> placeholders = new LinkedHashMap<>();
        SajatCegAlapadatok sajatCeg = sajatCegId != null ? sajatCegAlapadatokRepository.findById(sajatCegId).orElse(null) : null;
        addCompany(placeholders, sajatCeg);
        addEmployee(placeholders, munkavallalo != null ? munkavallalo.getMaganszemely() : null, csvDetails);
        addJob(placeholders, munkakor);
        String formattedStart = formatDate(datum);
        String formattedEnd = formatDate(datumVege != null ? datumVege : datum);
        placeholders.put(KEY_EFO_DATE, formattedStart);
        placeholders.put(KEY_EFO_START_DATE, formattedStart);
        placeholders.put(KEY_EFO_END_DATE, formattedEnd);
        placeholders.put(KEY_EFO_AMOUNT, formatAmount(osszeg));
        placeholders.put(KEY_EFO_AMOUNT_IN_WORDS, convertAmountToWordsOrEmpty(osszeg));
        BigDecimal totalAmount = osszesen != null ? osszesen : osszeg;
        placeholders.put(KEY_CSV_TOTAL_AMOUNT, formatAmount(totalAmount));
        placeholders.put(KEY_CSV_TOTAL_AMOUNT_IN_WORDS, convertAmountToWordsOrEmpty(totalAmount));
        Integer effectiveDayCount = resolveDayCount(napokSzama, datum, datumVege);
        placeholders.put(KEY_CSV_DAY_COUNT, formatInteger(effectiveDayCount));
        return placeholders;
    }

    private void addCompany(Map<String, String> target, SajatCegAlapadatok sajatCeg) {
        if (sajatCeg == null) {
            target.put(KEY_COMPANY_NAME, "");
            target.put(KEY_COMPANY_TAX, "");
            target.put(KEY_COMPANY_REGISTER, "");
            target.put(KEY_COMPANY_ADDRESS, "");
            target.put(KEY_COMPANY_BANK, "");
            target.put(KEY_COMPANY_EMAIL, "");
            target.put(KEY_COMPANY_PHONE, "");
            return;
        }
        CegAlapadatok ceg = sajatCeg.getCeg();
        if (ceg != null) {
            target.put(KEY_COMPANY_NAME, defaultString(ceg.getCegNev()));
            target.put(KEY_COMPANY_TAX, defaultString(ceg.getAdoszam()));
            target.put(KEY_COMPANY_REGISTER, defaultString(ceg.getCegjegyzekszam()));
            target.put(KEY_COMPANY_ADDRESS, defaultString(ceg.getCegSzekhely()));
            target.put(KEY_COMPANY_EMAIL, defaultString(ceg.getCegKozpontiEmail()));
            target.put(KEY_COMPANY_PHONE, defaultString(ceg.getCegKozpontiTel()));
        } else {
            target.put(KEY_COMPANY_NAME, "");
            target.put(KEY_COMPANY_TAX, "");
            target.put(KEY_COMPANY_REGISTER, "");
            target.put(KEY_COMPANY_ADDRESS, "");
            target.put(KEY_COMPANY_EMAIL, "");
            target.put(KEY_COMPANY_PHONE, "");
        }
        target.put(KEY_COMPANY_BANK, resolveCompanyBankAccount(sajatCeg));
    }

    private void addEmployee(Map<String, String> target, Maganszemelyek maganszemely, EfoCsvEmployeeDetails csvDetails) {
        target.put(KEY_EMPLOYEE_NAME, maganszemely != null ? defaultString(maganszemely.getMaganszemelyNeve()) : "");
        target.put(KEY_EMPLOYEE_TAX, maganszemely != null ? defaultString(maganszemely.getAdoAzonositoJel()) : "");
        target.put(KEY_EMPLOYEE_TB, maganszemely != null ? defaultString(maganszemely.getTbAzonosito()) : "");
        target.put(KEY_EMPLOYEE_EMAIL, maganszemely != null ? defaultString(maganszemely.getEmailcim()) : "");
        target.put(KEY_EMPLOYEE_PHONE, maganszemely != null ? defaultString(maganszemely.getTelefonszam()) : "");
        target.put(KEY_EMPLOYEE_BANK, maganszemely != null ? defaultString(maganszemely.getBankszamlaszam()) : "");
        target.put(KEY_EMPLOYEE_BIRTH_NAME, "");
        target.put(KEY_EMPLOYEE_BIRTH_PLACE, "");
        target.put(KEY_EMPLOYEE_BIRTH_DATE, "");
        target.put(KEY_EMPLOYEE_MOTHER_NAME, "");
        target.put(KEY_EMPLOYEE_ADDRESS, "");
        if (csvDetails != null) {
            if (StringUtils.hasText(csvDetails.getBirthName())) {
                target.put(KEY_EMPLOYEE_BIRTH_NAME, csvDetails.getBirthName());
            }
            if (StringUtils.hasText(csvDetails.getBirthPlace())) {
                target.put(KEY_EMPLOYEE_BIRTH_PLACE, csvDetails.getBirthPlace());
            }
            if (csvDetails.getBirthDate() != null) {
                target.put(KEY_EMPLOYEE_BIRTH_DATE, DATE_FORMAT.format(csvDetails.getBirthDate()));
            } else if (StringUtils.hasText(csvDetails.getBirthDateRaw())) {
                target.put(KEY_EMPLOYEE_BIRTH_DATE, csvDetails.getBirthDateRaw());
            }
            if (StringUtils.hasText(csvDetails.getMotherName())) {
                target.put(KEY_EMPLOYEE_MOTHER_NAME, csvDetails.getMotherName());
            }
            if (StringUtils.hasText(csvDetails.getAddress())) {
                target.put(KEY_EMPLOYEE_ADDRESS, csvDetails.getAddress());
            }
        }
    }

    private void addJob(Map<String, String> target, Munkakorok munkakor) {
        target.put(KEY_JOB_NAME, munkakor != null ? defaultString(munkakor.getMunkakorNeve()) : "");
        target.put(KEY_JOB_CODE, munkakor != null ? defaultString(munkakor.getMunkakorKod()) : "");
        target.put(KEY_JOB_TASK, munkakor != null ? defaultString(munkakor.getMunkakorFeladatai()) : "");
    }

    public List<TemplatePlaceholderDefinition> getDefinitions() {
        return DEFINITIONS;
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

    private String convertAmountToWords(BigDecimal amount) {
        BigDecimal normalized = amount.setScale(0, RoundingMode.DOWN);
        if (normalized.compareTo(BigDecimal.ZERO) == 0) {
            return "nulla";
        }
        StringBuilder builder = new StringBuilder();
        if (normalized.signum() < 0) {
            builder.append("minusz ");
            normalized = normalized.abs();
        }
        long value = normalized.longValueExact();
        builder.append(NumberToHungarianWordsConverter.convert(value));
        builder.append(" forint");
        return builder.toString().trim();
    }

    private String formatDate(LocalDate date) {
        return date != null ? DATE_FORMAT.format(date) : "";
    }

    private String formatAmount(BigDecimal amount) {
        return amount != null ? amount.stripTrailingZeros().toPlainString() : "";
    }

    private String convertAmountToWordsOrEmpty(BigDecimal amount) {
        return amount != null ? convertAmountToWords(amount) : "";
    }

    private Integer resolveDayCount(Integer napokSzama, LocalDate datum, LocalDate datumVege) {
        if (napokSzama != null && napokSzama > 0) {
            return napokSzama;
        }
        if (datum == null) {
            return null;
        }
        LocalDate end = datumVege != null ? datumVege : datum;
        long days = java.time.temporal.ChronoUnit.DAYS.between(datum, end) + 1;
        return days > 0 ? Math.toIntExact(days) : null;
    }

    private String formatInteger(Integer value) {
        return value != null ? value.toString() : "";
    }
}

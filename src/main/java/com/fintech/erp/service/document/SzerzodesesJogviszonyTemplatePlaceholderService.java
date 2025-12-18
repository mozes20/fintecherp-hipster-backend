package com.fintech.erp.service.document;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.SzerzodesesJogviszonyokRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SzerzodesesJogviszonyTemplatePlaceholderService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");

    private static final String KEY_CONTRACT_ID = "jogviszony.id";
    private static final String KEY_CONTRACT_NUMBER = "jogviszony.azonosito";
    private static final String KEY_CONTRACT_START = "jogviszony.kezdete";
    private static final String KEY_CONTRACT_END = "jogviszony.lejarata";

    private static final String KEY_CLIENT_NAME = "megrendelo_ceg.nev";
    private static final String KEY_CLIENT_SHORT_ID = "megrendelo_ceg.rovid_azonosito";
    private static final String KEY_CLIENT_ADDRESS = "megrendelo_ceg.szekhely";
    private static final String KEY_CLIENT_TAX = "megrendelo_ceg.adoszam";
    private static final String KEY_CLIENT_REGISTER = "megrendelo_ceg.cegjegyzekszam";
    private static final String KEY_CLIENT_EMAIL = "megrendelo_ceg.email";
    private static final String KEY_CLIENT_PHONE = "megrendelo_ceg.telefon";
    private static final String KEY_CLIENT_STATUS = "megrendelo_ceg.statusz";

    private static final String KEY_SUPPLIER_NAME = "vallalkozo_ceg.nev";
    private static final String KEY_SUPPLIER_SHORT_ID = "vallalkozo_ceg.rovid_azonosito";
    private static final String KEY_SUPPLIER_ADDRESS = "vallalkozo_ceg.szekhely";
    private static final String KEY_SUPPLIER_TAX = "vallalkozo_ceg.adoszam";
    private static final String KEY_SUPPLIER_REGISTER = "vallalkozo_ceg.cegjegyzekszam";
    private static final String KEY_SUPPLIER_EMAIL = "vallalkozo_ceg.email";
    private static final String KEY_SUPPLIER_PHONE = "vallalkozo_ceg.telefon";
    private static final String KEY_SUPPLIER_STATUS = "vallalkozo_ceg.statusz";

    private static final String KEY_CURRENT_DATE = "datum.ma";
    private static final String KEY_CURRENT_TIMESTAMP = "idopont.most";

    private static final List<TemplatePlaceholderDefinition> DEFINITIONS;

    static {
        List<TemplatePlaceholderDefinition> defs = new ArrayList<>();
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_ID, "Jogviszony azonosító (adatbázis ID)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_NUMBER, "Jogviszony / szerződés azonosító"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_START, "Jogviszony kezdete"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_END, "Jogviszony lejárata"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_NAME, "Megrendelő cég neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_SHORT_ID, "Megrendelő cég rövid azonosítója"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_ADDRESS, "Megrendelő cég székhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_TAX, "Megrendelő cég adószáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_REGISTER, "Megrendelő cég cégjegyzékszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_EMAIL, "Megrendelő cég e-mail címe"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_PHONE, "Megrendelő cég telefonszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_STATUS, "Megrendelő cég státusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_NAME, "Vállalkozó cég neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_SHORT_ID, "Vállalkozó cég rövid azonosítója"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_ADDRESS, "Vállalkozó cég székhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_TAX, "Vállalkozó cég adószáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_REGISTER, "Vállalkozó cég cégjegyzékszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_EMAIL, "Vállalkozó cég e-mail címe"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_PHONE, "Vállalkozó cég telefonszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_STATUS, "Vállalkozó cég státusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CURRENT_DATE, "Aktuális dátum"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CURRENT_TIMESTAMP, "Aktuális dátum és idő"));
        DEFINITIONS = List.copyOf(defs);
    }

    private final SzerzodesesJogviszonyokRepository jogviszonyokRepository;

    public SzerzodesesJogviszonyTemplatePlaceholderService(SzerzodesesJogviszonyokRepository jogviszonyokRepository) {
        this.jogviszonyokRepository = jogviszonyokRepository;
    }

    public Map<String, String> build(Long jogviszonyId) {
        if (jogviszonyId == null) {
            throw new IllegalArgumentException("A jogviszony azonosító megadása kötelező");
        }
        SzerzodesesJogviszonyok jogviszony = jogviszonyokRepository
            .findById(jogviszonyId)
            .orElseThrow(() -> new EntityNotFoundException("A megadott jogviszony nem található"));
        initializeRelations(jogviszony);
        return buildFromEntity(jogviszony);
    }

    public Map<String, String> build(SzerzodesesJogviszonyok jogviszony) {
        initializeRelations(jogviszony);
        return buildFromEntity(jogviszony);
    }

    public List<TemplatePlaceholderDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    private Map<String, String> buildFromEntity(SzerzodesesJogviszonyok jogviszony) {
        Map<String, String> placeholders = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        placeholders.put(KEY_CURRENT_DATE, DATE_FORMAT.format(now.toLocalDate()));
        placeholders.put(KEY_CURRENT_TIMESTAMP, DATE_TIME_FORMAT.format(now));

        if (jogviszony == null) {
            return placeholders;
        }

        addValue(placeholders, KEY_CONTRACT_ID, jogviszony.getId());
        addValue(placeholders, KEY_CONTRACT_NUMBER, jogviszony.getSzerzodesAzonosito());
        addDate(placeholders, KEY_CONTRACT_START, jogviszony.getJogviszonyKezdete());
        addDate(placeholders, KEY_CONTRACT_END, jogviszony.getJogviszonyLejarata());

        addCompany(placeholders, jogviszony.getMegrendeloCeg(), "megrendelo_ceg");
        addCompany(placeholders, jogviszony.getVallalkozoCeg(), "vallalkozo_ceg");

        return placeholders;
    }

    private void addCompany(Map<String, String> target, CegAlapadatok ceg, String prefix) {
        if (ceg == null) {
            return;
        }
        addValue(target, prefix + ".nev", ceg.getCegNev());
        addValue(target, prefix + ".rovid_azonosito", ceg.getCegRovidAzonosito());
        addValue(target, prefix + ".szekhely", ceg.getCegSzekhely());
        addValue(target, prefix + ".adoszam", ceg.getAdoszam());
        addValue(target, prefix + ".cegjegyzekszam", ceg.getCegjegyzekszam());
        addValue(target, prefix + ".email", ceg.getCegKozpontiEmail());
        addValue(target, prefix + ".telefon", ceg.getCegKozpontiTel());
        addValue(target, prefix + ".statusz", ceg.getStatusz());
    }

    private void addDate(Map<String, String> target, String key, LocalDate value) {
        addValue(target, key, value == null ? null : DATE_FORMAT.format(value));
    }

    private void addValue(Map<String, String> target, String key, Object value) {
        target.put(key, value == null ? "" : Objects.toString(value));
    }

    private void initializeRelations(SzerzodesesJogviszonyok jogviszony) {
        if (jogviszony == null) {
            return;
        }
        CegAlapadatok megrendelo = jogviszony.getMegrendeloCeg();
        if (megrendelo != null) {
            Hibernate.initialize(megrendelo);
        }
        CegAlapadatok vallalkozo = jogviszony.getVallalkozoCeg();
        if (vallalkozo != null) {
            Hibernate.initialize(vallalkozo);
        }
    }
}

package com.fintech.erp.service.document;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.MegrendelesekRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MegrendelesTemplatePlaceholderService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");

    private static final String KEY_ORDER_ID = "megrendeles.id";
    private static final String KEY_ORDER_NUMBER = "megrendeles.szama";
    private static final String KEY_ORDER_CUSTOMER_NUMBER = "megrendeles.ugyfel_megrendeles_id";
    private static final String KEY_ORDER_TYPE = "megrendeles.tipus";
    private static final String KEY_ORDER_TYPE_LABEL = "megrendeles.tipus_label";
    private static final String KEY_ORDER_STATUS = "megrendeles.statusz";
    private static final String KEY_ORDER_STATUS_LABEL = "megrendeles.statusz_label";
    private static final String KEY_ORDER_SOURCE = "megrendeles.forras";
    private static final String KEY_ORDER_SOURCE_LABEL = "megrendeles.forras_label";
    private static final String KEY_ORDER_SHORT_DESC = "megrendeles.feladat_rovid_leirasa";
    private static final String KEY_ORDER_LONG_DESC = "megrendeles.feladat_reszletes_leirasa";
    private static final String KEY_ORDER_START = "megrendeles.kezdete";
    private static final String KEY_ORDER_END = "megrendeles.vege";
    private static final String KEY_ORDER_PRICING_TYPE = "megrendeles.dijazas_tipusa";
    private static final String KEY_ORDER_PRICING_TYPE_LABEL = "megrendeles.dijazas_tipusa_label";
    private static final String KEY_ORDER_CURRENCY = "megrendeles.devizanem";
    private static final String KEY_ORDER_CURRENCY_LABEL = "megrendeles.devizanem_label";
    private static final String KEY_ORDER_AMOUNT = "megrendeles.dij_osszege";
    private static final String KEY_ORDER_COPY_COUNT = "megrendeles.peldanyok_szama";
    private static final String KEY_ORDER_INVOICE = "megrendeles.szamlazando";
    private static final String KEY_ORDER_INVOICE_LABEL = "megrendeles.szamlazando_label";
    private static final String KEY_ORDER_PARTICIPANT_TYPE = "megrendeles.resztvevo_tipus";
    private static final String KEY_ORDER_PARTICIPANT_TYPE_LABEL = "megrendeles.resztvevo_tipus_label";
    private static final String KEY_ORDER_COLLEAGUE_TYPE = "megrendeles.resztvevo_kollaga_tipusa";
    private static final String KEY_ORDER_COLLEAGUE_TYPE_LABEL = "megrendeles.resztvevo_kollaga_tipusa_label";

    private static final String KEY_CONTRACT_ID = "szerzodes.id";
    private static final String KEY_CONTRACT_NUMBER = "szerzodes.azonosito";
    private static final String KEY_CONTRACT_START = "szerzodes.kezdete";
    private static final String KEY_CONTRACT_END = "szerzodes.lejarata";

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

    private static final String KEY_PERSON_ID = "maganszemely.id";
    private static final String KEY_PERSON_NAME = "maganszemely.nev";
    private static final String KEY_PERSON_ID_CARD = "maganszemely.szemelyi_igazolvany";
    private static final String KEY_PERSON_TAX = "maganszemely.ado_azonosito";
    private static final String KEY_PERSON_TB = "maganszemely.tb_azonosito";
    private static final String KEY_PERSON_BANK = "maganszemely.bankszamlaszam";
    private static final String KEY_PERSON_PHONE = "maganszemely.telefon";
    private static final String KEY_PERSON_EMAIL = "maganszemely.email";
    private static final String KEY_PERSON_STATUS = "maganszemely.statusz";

    private static final String KEY_CURRENT_DATE = "datum.ma";
    private static final String KEY_CURRENT_TIMESTAMP = "idopont.most";

    private static final List<TemplatePlaceholderDefinition> DEFINITIONS;

    static {
        List<TemplatePlaceholderDefinition> defs = new ArrayList<>();
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_ID, "Megrendeles belso azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_NUMBER, "Megrendeles szama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_CUSTOMER_NUMBER, "Ugyfel altal hivatkozott megrendeles azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_TYPE, "Megrendeles tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_TYPE_LABEL, "Megrendeles tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_STATUS, "Megrendeles statusza (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_STATUS_LABEL, "Megrendeles statusza olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_SOURCE, "Megrendeles forrasa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_SOURCE_LABEL, "Megrendeles forrasa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_SHORT_DESC, "Feladat rovid leirasa"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_LONG_DESC, "Feladat reszletes leirasa"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_START, "Megrendeles kezdete"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_END, "Megrendeles vege"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_PRICING_TYPE, "Dijazas tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_PRICING_TYPE_LABEL, "Dijazas tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_CURRENCY, "Devizanem (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_CURRENCY_LABEL, "Devizanem olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_AMOUNT, "Dij osszege"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_COPY_COUNT, "Peldanyok szama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_INVOICE, "Szamlazando flag (true/false)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_INVOICE_LABEL, "Szamlazando flag olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_PARTICIPANT_TYPE, "Resztvevo tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_PARTICIPANT_TYPE_LABEL, "Resztvevo tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_COLLEAGUE_TYPE, "Resztvevo kollega tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_COLLEAGUE_TYPE_LABEL, "Resztvevo kollega tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_ID, "Szerzodes azonosito (adatbazis ID)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_NUMBER, "Szerzodes azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_START, "Szerzodes kezdete"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_END, "Szerzodes lejarata"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_NAME, "Megrendelo ceg neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_SHORT_ID, "Megrendelo ceg rovid azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_ADDRESS, "Megrendelo ceg szekhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_TAX, "Megrendelo ceg adoszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_REGISTER, "Megrendelo ceg cegjegyzekszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_EMAIL, "Megrendelo ceg email cime"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_PHONE, "Megrendelo ceg telefonszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_STATUS, "Megrendelo ceg statusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_NAME, "Vallalkozo ceg neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_SHORT_ID, "Vallalkozo ceg rovid azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_ADDRESS, "Vallalkozo ceg szekhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_TAX, "Vallalkozo ceg adoszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_REGISTER, "Vallalkozo ceg cegjegyzekszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_EMAIL, "Vallalkozo ceg email cime"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_PHONE, "Vallalkozo ceg telefonszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_STATUS, "Vallalkozo ceg statusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_ID, "Maganszemely azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_NAME, "Maganszemely neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_ID_CARD, "Maganszemely szemelyi igazolvany szama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_TAX, "Maganszemely adoazonosito jele"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_TB, "Maganszemely TAJ szama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_BANK, "Maganszemely bankszamlaszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_PHONE, "Maganszemely telefonszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_EMAIL, "Maganszemely email cime"));
        defs.add(new TemplatePlaceholderDefinition(KEY_PERSON_STATUS, "Maganszemely statusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CURRENT_DATE, "Aktualis datum"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CURRENT_TIMESTAMP, "Aktualis datum es ido"));
        DEFINITIONS = List.copyOf(defs);
    }

    private final MegrendelesekRepository megrendelesekRepository;

    public MegrendelesTemplatePlaceholderService(MegrendelesekRepository megrendelesekRepository) {
        this.megrendelesekRepository = megrendelesekRepository;
    }

    public Map<String, String> build(Long megrendelesId) {
        if (megrendelesId == null) {
            throw new IllegalArgumentException("A megrendeles azonosito kotelezo");
        }
        Megrendelesek megrendeles = megrendelesekRepository
            .findById(megrendelesId)
            .orElseThrow(() -> new EntityNotFoundException("A megadott megrendeles nem talalhato"));
        initializeRelations(megrendeles);
        return buildFromEntity(megrendeles);
    }

    public Map<String, String> build(Megrendelesek megrendeles) {
        initializeRelations(megrendeles);
        return buildFromEntity(megrendeles);
    }

    private Map<String, String> buildFromEntity(Megrendelesek megrendeles) {
        Map<String, String> placeholders = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        placeholders.put(KEY_CURRENT_DATE, formatDate(now.toLocalDate()));
        placeholders.put(KEY_CURRENT_TIMESTAMP, DATE_TIME_FORMAT.format(now));

        if (megrendeles == null) {
            return placeholders;
        }

        addValue(placeholders, KEY_ORDER_ID, megrendeles.getId());
        addValue(placeholders, KEY_ORDER_NUMBER, megrendeles.getMegrendelesSzam());
        addValue(placeholders, KEY_ORDER_CUSTOMER_NUMBER, megrendeles.getUgyfelMegrendelesId());
        addEnum(placeholders, KEY_ORDER_TYPE, megrendeles.getMegrendelesTipusa());
        addEnum(placeholders, KEY_ORDER_STATUS, megrendeles.getMegrendelesStatusz());
        addEnum(placeholders, KEY_ORDER_SOURCE, megrendeles.getMegrendelesForrasa());
        addValue(placeholders, KEY_ORDER_SHORT_DESC, megrendeles.getFeladatRovidLeirasa());
        addValue(placeholders, KEY_ORDER_LONG_DESC, megrendeles.getFeladatReszletesLeirasa());
        addDate(placeholders, KEY_ORDER_START, megrendeles.getMegrendelesKezdete());
        addDate(placeholders, KEY_ORDER_END, megrendeles.getMegrendelesVege());
        addEnum(placeholders, KEY_ORDER_PRICING_TYPE, megrendeles.getDijazasTipusa());
        addEnum(placeholders, KEY_ORDER_CURRENCY, megrendeles.getDevizanem());
        addDecimal(placeholders, KEY_ORDER_AMOUNT, megrendeles.getDijOsszege());
        addValue(placeholders, KEY_ORDER_COPY_COUNT, megrendeles.getPeldanyokSzama());
        addBoolean(placeholders, KEY_ORDER_INVOICE, megrendeles.getSzamlazando());
        addEnum(placeholders, KEY_ORDER_PARTICIPANT_TYPE, megrendeles.getResztvevoTipus());
        addEnum(placeholders, KEY_ORDER_COLLEAGUE_TYPE, megrendeles.getResztvevoKollagaTipusa());

        SzerzodesesJogviszonyok jogviszony = megrendeles.getSzerzodesesJogviszony();
        if (jogviszony != null) {
            addValue(placeholders, KEY_CONTRACT_ID, jogviszony.getId());
            addValue(placeholders, KEY_CONTRACT_NUMBER, jogviszony.getSzerzodesAzonosito());
            addDate(placeholders, KEY_CONTRACT_START, jogviszony.getJogviszonyKezdete());
            addDate(placeholders, KEY_CONTRACT_END, jogviszony.getJogviszonyLejarata());
            addCompany(placeholders, jogviszony.getMegrendeloCeg(), "megrendelo_ceg");
            addCompany(placeholders, jogviszony.getVallalkozoCeg(), "vallalkozo_ceg");
        }

        Maganszemelyek maganszemely = megrendeles.getMaganszemely();
        if (maganszemely != null) {
            addValue(placeholders, KEY_PERSON_ID, maganszemely.getId());
            addValue(placeholders, KEY_PERSON_NAME, maganszemely.getMaganszemelyNeve());
            addValue(placeholders, KEY_PERSON_ID_CARD, maganszemely.getSzemelyiIgazolvanySzama());
            addValue(placeholders, KEY_PERSON_TAX, maganszemely.getAdoAzonositoJel());
            addValue(placeholders, KEY_PERSON_TB, maganszemely.getTbAzonosito());
            addValue(placeholders, KEY_PERSON_BANK, maganszemely.getBankszamlaszam());
            addValue(placeholders, KEY_PERSON_PHONE, maganszemely.getTelefonszam());
            addValue(placeholders, KEY_PERSON_EMAIL, maganszemely.getEmailcim());
            addValue(placeholders, KEY_PERSON_STATUS, maganszemely.getStatusz());
        }

        return placeholders;
    }

    public List<TemplatePlaceholderDefinition> getDefinitions() {
        return DEFINITIONS;
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

    private void addEnum(Map<String, String> target, String key, Enum<?> value) {
        addValue(target, key, value == null ? null : value.name());
        addValue(target, key + "_label", value == null ? null : humanizeEnum(value));
    }

    private void addBoolean(Map<String, String> target, String key, Boolean value) {
        addValue(target, key, value);
        if (value == null) {
            addValue(target, key + "_label", null);
        } else {
            addValue(target, key + "_label", value ? "igen" : "nem");
        }
    }

    private void addDate(Map<String, String> target, String key, LocalDate value) {
        addValue(target, key, value == null ? null : formatDate(value));
    }

    private void addDecimal(Map<String, String> target, String key, BigDecimal value) {
        if (value == null) {
            addValue(target, key, null);
        } else {
            addValue(target, key, value.stripTrailingZeros().toPlainString());
        }
    }

    private void addValue(Map<String, String> target, String key, Object value) {
        target.put(key, value == null ? "" : Objects.toString(value));
    }

    private String formatDate(LocalDate date) {
        return DATE_FORMAT.format(date);
    }

    private String humanizeEnum(Enum<?> value) {
        String raw = value.name().toLowerCase(Locale.ROOT);
        return List.of(raw.split("_")).stream().map(MegrendelesTemplatePlaceholderService::capitalize).collect(Collectors.joining(" "));
    }

    private static String capitalize(String input) {
        if (input.isEmpty()) {
            return input;
        }
        char first = Character.toUpperCase(input.charAt(0));
        if (input.length() == 1) {
            return String.valueOf(first);
        }
        return first + input.substring(1);
    }

    private void initializeRelations(Megrendelesek megrendeles) {
        if (megrendeles == null) {
            return;
        }
        SzerzodesesJogviszonyok jogviszony = megrendeles.getSzerzodesesJogviszony();
        if (jogviszony != null) {
            Hibernate.initialize(jogviszony);
            CegAlapadatok megrendelo = jogviszony.getMegrendeloCeg();
            if (megrendelo != null) {
                Hibernate.initialize(megrendelo);
            }
            CegAlapadatok vallalkozo = jogviszony.getVallalkozoCeg();
            if (vallalkozo != null) {
                Hibernate.initialize(vallalkozo);
            }
        }
        Maganszemelyek maganszemely = megrendeles.getMaganszemely();
        if (maganszemely != null) {
            Hibernate.initialize(maganszemely);
        }
    }
}

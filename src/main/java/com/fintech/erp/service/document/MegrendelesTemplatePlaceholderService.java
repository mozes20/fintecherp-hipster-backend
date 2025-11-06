package com.fintech.erp.service.document;

import com.fintech.erp.domain.Bankszamlaszamok;
import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.Maganszemelyek;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import com.fintech.erp.repository.BankszamlaszamokRepository;
import com.fintech.erp.repository.MegrendelesekRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.util.StringUtils;

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
    private static final String KEY_ORDER_SHORT_DESC = "megrendeles.feladat_rovid_leirasa";
    private static final String KEY_ORDER_LONG_DESC = "megrendeles.feladat_reszletes_leirasa";
    private static final String KEY_ORDER_DELIVERY_ITEMS = "megrendeles.szallitasra_kerulo_tetelek";
    private static final String KEY_ORDER_DATE = "megrendeles.datuma";
    private static final String KEY_ORDER_START = "megrendeles.kezdete";
    private static final String KEY_ORDER_END = "megrendeles.vege";
    private static final String KEY_ORDER_PRICING_TYPE = "megrendeles.dijazas_tipusa";
    private static final String KEY_ORDER_PRICING_TYPE_LABEL = "megrendeles.dijazas_tipusa_label";
    private static final String KEY_ORDER_CURRENCY = "megrendeles.devizanem";
    private static final String KEY_ORDER_CURRENCY_LABEL = "megrendeles.devizanem_label";
    private static final String KEY_ORDER_AMOUNT = "megrendeles.dij_osszege";
    private static final String KEY_ORDER_AMOUNT_IN_WORDS = "megrendeles.dij_osszege_betukkel";
    private static final String KEY_ORDER_JOB_ID = "megrendeles.munkakor_id";
    private static final String KEY_ORDER_JOB_CODE = "megrendeles.munkakor_kod";
    private static final String KEY_ORDER_JOB_NAME = "megrendeles.munkakor_nev";
    private static final String KEY_ORDER_JOB_TASKS = "megrendeles.munkakor_feladatai";
    private static final String KEY_ORDER_JOB_SKILLS = "megrendeles.munkakor_szaktudasai";
    private static final String KEY_ORDER_JOB_EFO = "megrendeles.munkakor_efo";
    private static final String KEY_ORDER_COLLEAGUE_TYPE = "megrendeles.resztvevo_kollaga_tipusa";
    private static final String KEY_ORDER_COLLEAGUE_TYPE_LABEL = "megrendeles.resztvevo_kollaga_tipusa_label";

    private static final String KEY_CONTRACT_ID = "szerzodes.id";
    private static final String KEY_CONTRACT_NUMBER = "szerzodes.azonosito";
    private static final String KEY_CONTRACT_START = "szerzodes.kezdete";
    private static final String KEY_CONTRACT_END = "szerzodes.lejarata";

    private static final String KEY_CLIENT_NAME = "megrendelo_ceg.nev";
    private static final String KEY_CLIENT_SHORT_ID = "megrendelo_ceg.rovid_azonosito";
    private static final String KEY_CLIENT_ADDRESS = "megrendelo_ceg.szekhely";
    private static final String KEY_CLIENT_BANK = "megrendelo_ceg.bankszamlaszam";
    private static final String KEY_CLIENT_TAX = "megrendelo_ceg.adoszam";
    private static final String KEY_CLIENT_REGISTER = "megrendelo_ceg.cegjegyzekszam";
    private static final String KEY_CLIENT_EMAIL = "megrendelo_ceg.email";
    private static final String KEY_CLIENT_PHONE = "megrendelo_ceg.telefon";
    private static final String KEY_CLIENT_STATUS = "megrendelo_ceg.statusz";

    private static final String KEY_SUPPLIER_NAME = "vallalkozo_ceg.nev";
    private static final String KEY_SUPPLIER_SHORT_ID = "vallalkozo_ceg.rovid_azonosito";
    private static final String KEY_SUPPLIER_ADDRESS = "vallalkozo_ceg.szekhely";
    private static final String KEY_SUPPLIER_BANK = "vallalkozo_ceg.bankszamlaszam";
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

    private static final String[] NUMBER_UNITS = { "", "egy", "ketto", "harom", "negy", "ot", "hat", "het", "nyolc", "kilenc" };

    private static final String[] NUMBER_TEENS = {
        "tiz",
        "tizenegy",
        "tizenketto",
        "tizenharom",
        "tizennegy",
        "tizenot",
        "tizenhat",
        "tizenhet",
        "tizennyolc",
        "tizenkilenc",
    };

    private static final String[] NUMBER_TENS = {
        "",
        "tiz",
        "husz",
        "harminc",
        "negyven",
        "otven",
        "hatvan",
        "hetven",
        "nyolcvan",
        "kilencven",
    };

    private static final String[] NUMBER_HUNDREDS = {
        "",
        "szaz",
        "ketszaz",
        "haromszaz",
        "negyszaz",
        "otszaz",
        "hatszaz",
        "hetszaz",
        "nyolcszaz",
        "kilencszaz",
    };

    private static final List<TemplatePlaceholderDefinition> DEFINITIONS;

    static {
        List<TemplatePlaceholderDefinition> defs = new ArrayList<>();
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_ID, "Megrendeles belso azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_NUMBER, "Megrendeles szama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_CUSTOMER_NUMBER, "Ugyfel altal hivatkozott megrendeles azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_TYPE, "Megrendeles tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_TYPE_LABEL, "Megrendeles tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_SHORT_DESC, "Feladat rovid leirasa"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_LONG_DESC, "Feladat reszletes leirasa"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_DELIVERY_ITEMS, "Szallitasra kerulo tetelek"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_DATE, "Megrendeles datuma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_START, "Megrendeles kezdete"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_END, "Megrendeles vege"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_PRICING_TYPE, "Dijazas tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_PRICING_TYPE_LABEL, "Dijazas tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_CURRENCY, "Devizanem (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_CURRENCY_LABEL, "Devizanem olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_AMOUNT, "Dij osszege"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_AMOUNT_IN_WORDS, "Dij osszege betukkel"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_JOB_ID, "Kapcsolodo munkakor azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_JOB_CODE, "Kapcsolodo munkakor kodja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_JOB_NAME, "Kapcsolodo munkakor neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_JOB_TASKS, "Kapcsolodo munkakor feladatai"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_JOB_SKILLS, "Kapcsolodo munkakor szaktudasai"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_JOB_EFO, "Kapcsolodo munkakor EFO statusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_COLLEAGUE_TYPE, "Resztvevo kollega tipusa (ENUM ertek)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ORDER_COLLEAGUE_TYPE_LABEL, "Resztvevo kollega tipusa olvashato formaban"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_ID, "Szerzodes azonosito (adatbazis ID)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_NUMBER, "Szerzodes azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_START, "Szerzodes kezdete"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CONTRACT_END, "Szerzodes lejarata"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_NAME, "Megrendelo ceg neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_SHORT_ID, "Megrendelo ceg rovid azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_ADDRESS, "Megrendelo ceg szekhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_BANK, "Megrendelo ceg bankszamlaszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_TAX, "Megrendelo ceg adoszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_REGISTER, "Megrendelo ceg cegjegyzekszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_EMAIL, "Megrendelo ceg email cime"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_PHONE, "Megrendelo ceg telefonszama"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CLIENT_STATUS, "Megrendelo ceg statusza"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_NAME, "Vallalkozo ceg neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_SHORT_ID, "Vallalkozo ceg rovid azonositoja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_ADDRESS, "Vallalkozo ceg szekhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_SUPPLIER_BANK, "Vallalkozo ceg bankszamlaszama"));
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
    private final BankszamlaszamokRepository bankszamlaszamokRepository;

    public MegrendelesTemplatePlaceholderService(
        MegrendelesekRepository megrendelesekRepository,
        BankszamlaszamokRepository bankszamlaszamokRepository
    ) {
        this.megrendelesekRepository = megrendelesekRepository;
        this.bankszamlaszamokRepository = bankszamlaszamokRepository;
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
        addValue(placeholders, KEY_ORDER_SHORT_DESC, megrendeles.getFeladatRovidLeirasa());
        addValue(placeholders, KEY_ORDER_LONG_DESC, megrendeles.getFeladatReszletesLeirasa());
        addValue(placeholders, KEY_ORDER_DELIVERY_ITEMS, megrendeles.getSzallitasraKeruloTetelek());
        addDate(placeholders, KEY_ORDER_DATE, megrendeles.getMegrendelesDatuma());
        addDate(placeholders, KEY_ORDER_START, megrendeles.getMegrendelesKezdete());
        addDate(placeholders, KEY_ORDER_END, megrendeles.getMegrendelesVege());
        addEnum(placeholders, KEY_ORDER_PRICING_TYPE, megrendeles.getDijazasTipusa());
        addEnum(placeholders, KEY_ORDER_CURRENCY, megrendeles.getDevizanem());
        addDecimal(placeholders, KEY_ORDER_AMOUNT, megrendeles.getDijOsszege());
        addValue(placeholders, KEY_ORDER_AMOUNT_IN_WORDS, convertAmountToHungarianWords(megrendeles.getDijOsszege()));
        addValue(placeholders, KEY_ORDER_JOB_ID, megrendeles.getMunkakorId());
        Munkakorok munkakor = megrendeles.getMunkakor();
        if (munkakor != null) {
            addValue(placeholders, KEY_ORDER_JOB_CODE, munkakor.getMunkakorKod());
            addValue(placeholders, KEY_ORDER_JOB_NAME, munkakor.getMunkakorNeve());
            addValue(placeholders, KEY_ORDER_JOB_TASKS, munkakor.getMunkakorFeladatai());
            addValue(placeholders, KEY_ORDER_JOB_SKILLS, munkakor.getMunkakorSzaktudasai());
            addValue(placeholders, KEY_ORDER_JOB_EFO, munkakor.getEfoMunkakor());
        }
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
        addValue(target, prefix + ".bankszamlaszam", resolveCompanyBankAccount(ceg));
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
        Munkakorok munkakor = megrendeles.getMunkakor();
        if (munkakor != null) {
            Hibernate.initialize(munkakor);
        }
    }

    private String resolveCompanyBankAccount(CegAlapadatok ceg) {
        if (ceg == null || ceg.getId() == null) {
            return "";
        }
        return bankszamlaszamokRepository
            .findFirstByCegIdAndStatuszOrderByIdAsc(ceg.getId(), "AKTIV")
            .or(() -> bankszamlaszamokRepository.findFirstByCegIdOrderByIdAsc(ceg.getId()))
            .map(this::formatBankAccount)
            .orElse("");
    }

    private String formatBankAccount(Bankszamlaszamok bankAccount) {
        if (bankAccount == null) {
            return "";
        }
        if (StringUtils.hasText(bankAccount.getBankszamlaIBAN())) {
            return bankAccount.getBankszamlaIBAN();
        }
        if (StringUtils.hasText(bankAccount.getBankszamlaGIRO())) {
            return bankAccount.getBankszamlaGIRO();
        }
        return "";
    }

    private String convertAmountToHungarianWords(BigDecimal amount) {
        if (amount == null) {
            return "";
        }
        BigDecimal integerPart = amount.setScale(0, RoundingMode.DOWN);
        if (integerPart.compareTo(BigDecimal.ZERO) < 0) {
            return integerPart.toPlainString();
        }
        long numericValue;
        try {
            numericValue = integerPart.longValueExact();
        } catch (ArithmeticException ex) {
            return integerPart.toPlainString();
        }
        return convertNumberToHungarianWords(numericValue);
    }

    private String convertNumberToHungarianWords(long value) {
        if (value == 0) {
            return "nulla";
        }
        StringBuilder builder = new StringBuilder();
        if (value < 0) {
            builder.append("minusz ");
            value = Math.abs(value);
        }
        long[] scaleValues = { 1_000_000_000_000L, 1_000_000_000L, 1_000_000L, 1_000L };
        String[] scaleNames = { "billio", "milliard", "millio", "ezer" };
        for (int i = 0; i < scaleValues.length; i++) {
            int groupValue = (int) (value / scaleValues[i]);
            if (groupValue > 0) {
                appendScalePart(builder, groupValue, scaleNames[i]);
                value = value % scaleValues[i];
            }
        }
        if (value > 0) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(convertBelowThousand((int) value));
        }
        return builder.toString().trim();
    }

    private void appendScalePart(StringBuilder builder, int value, String scale) {
        if (value <= 0) {
            return;
        }
        if (builder.length() > 0) {
            builder.append(" ");
        }
        switch (scale) {
            case "ezer" -> {
                if (value == 1) {
                    builder.append("ezer");
                    return;
                }
                if (value == 2) {
                    builder.append("ketezer");
                    return;
                }
            }
            case "millio" -> {
                if (value == 1) {
                    builder.append("egymillio");
                    return;
                }
                if (value == 2) {
                    builder.append("ketmillio");
                    return;
                }
            }
            case "milliard" -> {
                if (value == 1) {
                    builder.append("egymilliard");
                    return;
                }
                if (value == 2) {
                    builder.append("ketmilliard");
                    return;
                }
            }
            case "billio" -> {
                if (value == 1) {
                    builder.append("egybillio");
                    return;
                }
                if (value == 2) {
                    builder.append("ketbillio");
                    return;
                }
            }
            default -> {}
        }
        builder.append(convertBelowThousand(value));
        builder.append(scale);
    }

    private String convertBelowThousand(int value) {
        if (value == 0) {
            return "";
        }
        StringBuilder part = new StringBuilder();
        int hundreds = value / 100;
        if (hundreds > 0) {
            part.append(NUMBER_HUNDREDS[hundreds]);
        }
        int remainder = value % 100;
        if (remainder >= 10 && remainder < 20) {
            part.append(NUMBER_TEENS[remainder - 10]);
            return part.toString();
        }
        int tens = remainder / 10;
        if (tens > 0) {
            if (tens == 2) {
                part.append("husz");
                if (remainder % 10 != 0) {
                    part.append("on");
                }
            } else {
                part.append(NUMBER_TENS[tens]);
            }
        }
        int units = remainder % 10;
        if (units > 0) {
            part.append(NUMBER_UNITS[units]);
        }
        return part.toString();
    }
}

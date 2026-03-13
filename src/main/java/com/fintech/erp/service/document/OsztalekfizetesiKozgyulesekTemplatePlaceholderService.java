package com.fintech.erp.service.document;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Builds the template placeholder map for OsztalekfizetesiKozgyulesek documents.
 *
 * <p>Available placeholder keys (use in .docx template as {{key}}):
 * <pre>
 *   kozgyules.id, kozgyules.datum, kozgyules.ev, kozgyules.honap
 *   kozgyules.merleg_foosszeg, kozgyules.adozott_eredmeny
 *   kozgyules.elszamolas_grand_total, kozgyules.elszamolas_napidijak_osszesen
 *   sajat_ceg.nev, sajat_ceg.rovid_azonosito, sajat_ceg.szekhely
 *   sajat_ceg.adoszam, sajat_ceg.cegjegyzekszam, sajat_ceg.email, sajat_ceg.telefon
 *   datum.ma, idopont.most
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class OsztalekfizetesiKozgyulesekTemplatePlaceholderService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");

    // ---- placeholder key constants ----
    private static final String KEY_ID = "kozgyules.id";
    private static final String KEY_DATUM = "kozgyules.datum";
    private static final String KEY_EV = "kozgyules.ev";
    private static final String KEY_HONAP = "kozgyules.honap";
    private static final String KEY_MERLEG = "kozgyules.merleg_foosszeg";
    private static final String KEY_ADOZOTT = "kozgyules.adozott_eredmeny";
    private static final String KEY_ELSZAMOLAS_GRAND_TOTAL = "kozgyules.elszamolas_grand_total";
    private static final String KEY_ELSZAMOLAS_NAPIDIJAK = "kozgyules.elszamolas_napidijak_osszesen";

    private static final String KEY_CEG_NEV = "sajat_ceg.nev";
    private static final String KEY_CEG_ROVID = "sajat_ceg.rovid_azonosito";
    private static final String KEY_CEG_SZEKHELY = "sajat_ceg.szekhely";
    private static final String KEY_CEG_ADO = "sajat_ceg.adoszam";
    private static final String KEY_CEG_CEG = "sajat_ceg.cegjegyzekszam";
    private static final String KEY_CEG_EMAIL = "sajat_ceg.email";
    private static final String KEY_CEG_TEL = "sajat_ceg.telefon";

    private static final String KEY_CURRENT_DATE = "datum.ma";
    private static final String KEY_CURRENT_TS = "idopont.most";

    private static final List<TemplatePlaceholderDefinition> DEFINITIONS;

    static {
        List<TemplatePlaceholderDefinition> defs = new ArrayList<>();
        defs.add(new TemplatePlaceholderDefinition(KEY_ID, "Közgyűlés azonosító (adatbázis ID)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_DATUM, "Közgyűlés dátuma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EV, "Közgyűlés éve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_HONAP, "Közgyűlés hónapja"));
        defs.add(new TemplatePlaceholderDefinition(KEY_MERLEG, "Mérlegfőösszeg (e HUF)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ADOZOTT, "Adózott eredmény (e HUF)"));
        defs.add(
            new TemplatePlaceholderDefinition(KEY_ELSZAMOLAS_GRAND_TOTAL, "Elszámolás - összes köztség összesen (e HUF, B oszlop összege)")
        );
        defs.add(
            new TemplatePlaceholderDefinition(KEY_ELSZAMOLAS_NAPIDIJAK, "Elszámolás - számlázott összeg összesen (e HUF, F oszlop összege)")
        );
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_NEV, "Saját cég neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_ROVID, "Saját cég rövid azonosítója"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_SZEKHELY, "Saját cég székhelye"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_ADO, "Saját cég adószáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_CEG, "Saját cég cégjegyzékszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_EMAIL, "Saját cég e-mail címe"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CEG_TEL, "Saját cég telefonszáma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CURRENT_DATE, "Aktuális dátum"));
        defs.add(new TemplatePlaceholderDefinition(KEY_CURRENT_TS, "Aktuális dátum és idő"));
        DEFINITIONS = List.copyOf(defs);
    }

    private final OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository;

    public OsztalekfizetesiKozgyulesekTemplatePlaceholderService(OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository) {
        this.kozgyulesekRepository = kozgyulesekRepository;
    }

    /**
     * Build the placeholder map for the given közgyűlés (loads from DB).
     */
    public Map<String, String> build(Long kozgyulesId) {
        if (kozgyulesId == null) {
            throw new IllegalArgumentException("A közgyűlés azonosító megadása kötelező");
        }
        OsztalekfizetesiKozgyulesek kozgyules = kozgyulesekRepository
            .findById(kozgyulesId)
            .orElseThrow(() -> new EntityNotFoundException("A megadott közgyűlés nem található: " + kozgyulesId));
        initializeRelations(kozgyules);
        return buildFromEntity(kozgyules);
    }

    public List<TemplatePlaceholderDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    // -------------------------------------------------------------------------

    private void initializeRelations(OsztalekfizetesiKozgyulesek kozgyules) {
        if (kozgyules.getSajatCeg() != null) {
            Hibernate.initialize(kozgyules.getSajatCeg());
            if (kozgyules.getSajatCeg().getCeg() != null) {
                Hibernate.initialize(kozgyules.getSajatCeg().getCeg());
            }
        }
    }

    private Map<String, String> buildFromEntity(OsztalekfizetesiKozgyulesek kozgyules) {
        Map<String, String> p = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        p.put(KEY_CURRENT_DATE, DATE_FORMAT.format(now.toLocalDate()));
        p.put(KEY_CURRENT_TS, DATE_TIME_FORMAT.format(now));

        addValue(p, KEY_ID, kozgyules.getId());

        if (kozgyules.getKozgyulesDatum() != null) {
            p.put(KEY_DATUM, DATE_FORMAT.format(kozgyules.getKozgyulesDatum()));
            p.put(KEY_EV, String.valueOf(kozgyules.getKozgyulesDatum().getYear()));
            p.put(KEY_HONAP, String.format("%02d", kozgyules.getKozgyulesDatum().getMonthValue()));
        }

        addValue(p, KEY_MERLEG, kozgyules.getMerlegFoosszeg());
        addValue(p, KEY_ADOZOTT, kozgyules.getAdozottEredmeny());
        addValue(p, KEY_ELSZAMOLAS_GRAND_TOTAL, kozgyules.getElszamolasGrandTotal());
        addValue(p, KEY_ELSZAMOLAS_NAPIDIJAK, kozgyules.getElszamolasNapidijakOsszesen());

        SajatCegAlapadatok sajatCeg = kozgyules.getSajatCeg();
        if (sajatCeg != null) {
            CegAlapadatok ceg = sajatCeg.getCeg();
            if (ceg != null) {
                addValue(p, KEY_CEG_NEV, ceg.getCegNev());
                addValue(p, KEY_CEG_ROVID, ceg.getCegRovidAzonosito());
                addValue(p, KEY_CEG_SZEKHELY, ceg.getCegSzekhely());
                addValue(p, KEY_CEG_ADO, ceg.getAdoszam());
                addValue(p, KEY_CEG_CEG, ceg.getCegjegyzekszam());
                addValue(p, KEY_CEG_EMAIL, ceg.getCegKozpontiEmail());
                addValue(p, KEY_CEG_TEL, ceg.getCegKozpontiTel());
            }
        }

        return p;
    }

    private void addValue(Map<String, String> target, String key, Object value) {
        if (value != null) {
            target.put(key, value.toString());
        }
    }
}

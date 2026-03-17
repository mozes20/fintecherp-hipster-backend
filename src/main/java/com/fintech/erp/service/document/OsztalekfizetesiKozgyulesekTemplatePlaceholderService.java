package com.fintech.erp.service.document;

import com.fintech.erp.domain.CegAlapadatok;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.SajatCegAlapadatok;
import com.fintech.erp.domain.SajatCegKepviselok;
import com.fintech.erp.domain.SajatCegTulajdonosok;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.repository.SajatCegKepviselokRepository;
import com.fintech.erp.repository.SajatCegTulajdonosokRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
 *   kozgyules.hatarozat_szama, kozgyules.helyszin
 *   kozgyules.merleg_foosszeg, kozgyules.adozott_eredmeny
 *   kozgyules.elszamolas_grand_total, kozgyules.elszamolas_napidijak_osszesen
 *   kozgyules.jelenlevok_lista   — "- Név1\n- Név2\n..." (tulajdonosok)
 *   kozgyules.osztalek_lista     — "- Név1: 1 234 e HUF\n- Név2: 5 678 e HUF\n..."
 *   kozgyules.brutto_osztalek_osszesen
 *   kozgyules.alairo_1_nev .. kozgyules.alairo_5_nev  (tag nevek aláíráshoz)
 *   kozgyules.alairo_1_szerep .. kozgyules.alairo_5_szerep
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
    private static final String KEY_NAP = "kozgyules.nap";
    private static final String KEY_HONAP_NEV = "kozgyules.honap_nev";

    private static final String[] HONAP_NEVEK = {
        "", "január", "február", "március", "április", "május", "június",
        "július", "augusztus", "szeptember", "október", "november", "december"
    };
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

    private static final String KEY_HATAROZAT_SZAMA = "kozgyules.hatarozat_szama";
    private static final String KEY_HELYSZIN = "kozgyules.helyszin";
    private static final String KEY_JELENLEVOK = "kozgyules.jelenlevok_lista";
    private static final String KEY_OSZTALEK_LISTA = "kozgyules.osztalek_lista";
    private static final String KEY_BRUTTO_OSZTALEK_OSSZESEN = "kozgyules.brutto_osztalek_osszesen";

    private static final String KEY_UGYVEZETO_NEV = "kozgyules.ugyvezeto_nev";
    private static final String KEY_FINTECH_SERVICES_OSZTALEK = "kozgyules.fintech_services_osztalek";

    private static final String KEY_CURRENT_DATE = "datum.ma";
    private static final String KEY_CURRENT_TS = "idopont.most";

    private static final int MAX_ALAIRO = 5;

    private static final List<TemplatePlaceholderDefinition> DEFINITIONS;

    static {
        List<TemplatePlaceholderDefinition> defs = new ArrayList<>();
        defs.add(new TemplatePlaceholderDefinition(KEY_ID, "Közgyűlés azonosító (adatbázis ID)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_DATUM, "Közgyűlés dátuma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_EV, "Közgyűlés éve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_HONAP, "Közgyűlés hónapja (szám, pl. 02)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_NAP, "Közgyűlés napja (szám, pl. 13)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_HONAP_NEV, "Közgyűlés hónapjának neve (pl. február)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_MERLEG, "Mérlegfőösszeg (e HUF)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_ADOZOTT, "Adózott eredmény (e HUF)"));
        defs.add(
            new TemplatePlaceholderDefinition(KEY_ELSZAMOLAS_GRAND_TOTAL, "Elszámolás - összes köztség összesen (e HUF, B oszlop összege)")
        );
        defs.add(
            new TemplatePlaceholderDefinition(KEY_ELSZAMOLAS_NAPIDIJAK, "Elszámolás - számlázott összeg összesen (e HUF, F oszlop összege)")
        );
        defs.add(new TemplatePlaceholderDefinition(KEY_HATAROZAT_SZAMA, "Határozat száma"));
        defs.add(new TemplatePlaceholderDefinition(KEY_HELYSZIN, "Közgyűlés helyszíne"));
        defs.add(new TemplatePlaceholderDefinition(KEY_JELENLEVOK, "Jelenlévők listája (felsorolás, pl. sablonban: {{kozgyules.jelenlevok_lista}})"));
        defs.add(new TemplatePlaceholderDefinition(KEY_OSZTALEK_LISTA, "Osztalékfizetési lista (pl. - Név: összeg e HUF)"));
        defs.add(new TemplatePlaceholderDefinition(KEY_BRUTTO_OSZTALEK_OSSZESEN, "Bruttó osztalék összesen (e HUF)"));
        for (int i = 1; i <= MAX_ALAIRO; i++) {
            defs.add(new TemplatePlaceholderDefinition("kozgyules.alairo_" + i + "_nev", "Aláíró " + i + " neve"));
            defs.add(new TemplatePlaceholderDefinition("kozgyules.alairo_" + i + "_szerep", "Aláíró " + i + " szerepe (pl. tag, ügyvezető)"));
        }
        defs.add(new TemplatePlaceholderDefinition(KEY_UGYVEZETO_NEV, "Első képviselő (ügyvezető) neve"));
        defs.add(new TemplatePlaceholderDefinition(KEY_FINTECH_SERVICES_OSZTALEK, "Osztalék - FinTech Services Kft. (rögzített, 10 e HUF)"));
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

    private static final DecimalFormat AMOUNT_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("hu-HU"));
        symbols.setGroupingSeparator('\u00a0'); // non-breaking space
        AMOUNT_FORMAT = new DecimalFormat("#,##0", symbols);
    }

    private final OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository;
    private final SajatCegTulajdonosokRepository tulajdonosokRepository;
    private final SajatCegKepviselokRepository kepviselokRepository;

    public OsztalekfizetesiKozgyulesekTemplatePlaceholderService(
        OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository,
        SajatCegTulajdonosokRepository tulajdonosokRepository,
        SajatCegKepviselokRepository kepviselokRepository
    ) {
        this.kozgyulesekRepository = kozgyulesekRepository;
        this.tulajdonosokRepository = tulajdonosokRepository;
        this.kepviselokRepository = kepviselokRepository;
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
        Long sajatCegId = kozgyules.getSajatCeg() != null ? kozgyules.getSajatCeg().getId() : null;
        List<SajatCegTulajdonosok> tulajdonosok = sajatCegId != null
            ? tulajdonosokRepository.findBySajatCegIdWithMaganszemely(sajatCegId)
            : List.of();
        List<SajatCegKepviselok> kepviselok = sajatCegId != null
            ? kepviselokRepository.findBySajatCegIdWithMaganszemely(sajatCegId)
            : List.of();
        return buildFromEntity(kozgyules, tulajdonosok, kepviselok);
    }

    public List<TemplatePlaceholderDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    /**
     * Returns the ordered list of signers for the auto-generated signature table.
     * Order: képviselők (label = cég neve, szerep = "Ügyvezető") first,
     * then tulajdonosok (label = személy neve, szerep = "tag").
     */
    public List<DocxTemplateEngine.SignaturePerson> buildSigners(Long kozgyulesId) {
        OsztalekfizetesiKozgyulesek kozgyules = kozgyulesekRepository
            .findById(kozgyulesId)
            .orElseThrow(() -> new EntityNotFoundException("A megadott közgyűlés nem található: " + kozgyulesId));
        initializeRelations(kozgyules);
        Long sajatCegId = kozgyules.getSajatCeg() != null ? kozgyules.getSajatCeg().getId() : null;
        List<DocxTemplateEngine.SignaturePerson> signers = new ArrayList<>();
        if (sajatCegId == null) return signers;

        // Latest képviselő only (query is sorted id DESC so first = newest)
        List<SajatCegKepviselok> kepviselok = kepviselokRepository.findBySajatCegIdWithMaganszemely(sajatCegId);
        List<SajatCegTulajdonosok> tulajdonosok = tulajdonosokRepository.findBySajatCegIdWithMaganszemely(sajatCegId);

        // Collect maganszemely IDs of tulajdonosok for overlap detection
        java.util.Set<Long> tulajdonosPersonIds = new java.util.HashSet<>();
        for (SajatCegTulajdonosok t : tulajdonosok) {
            if (t.getMaganszemely() != null && t.getMaganszemely().getId() != null) {
                tulajdonosPersonIds.add(t.getMaganszemely().getId());
            }
        }

        // Add the single latest ügyvezető first
        Long ugyvezetoPersonId = null;
        if (!kepviselok.isEmpty()) {
            SajatCegKepviselok latest = kepviselok.get(0);
            String nev = latest.getMaganszemely() != null && latest.getMaganszemely().getMaganszemelyNeve() != null
                ? latest.getMaganszemely().getMaganszemelyNeve()
                : "Ismeretlen";
            ugyvezetoPersonId = latest.getMaganszemely() != null ? latest.getMaganszemely().getId() : null;
            boolean isTulajdonosIs = ugyvezetoPersonId != null && tulajdonosPersonIds.contains(ugyvezetoPersonId);
            String szerep = isTulajdonosIs ? "tulajdonos, ügyvezető" : "ügyvezető";
            signers.add(new DocxTemplateEngine.SignaturePerson(nev, szerep));
        }

        // Add remaining tulajdonosok (skip the one already added as ügyvezető)
        for (SajatCegTulajdonosok t : tulajdonosok) {
            Long personId = t.getMaganszemely() != null ? t.getMaganszemely().getId() : null;
            if (personId != null && personId.equals(ugyvezetoPersonId)) continue; // already listed
            String nev = t.getMaganszemely() != null && t.getMaganszemely().getMaganszemelyNeve() != null
                ? t.getMaganszemely().getMaganszemelyNeve()
                : "Ismeretlen";
            signers.add(new DocxTemplateEngine.SignaturePerson(nev, "tag"));
        }

        return signers;
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

    private Map<String, String> buildFromEntity(
        OsztalekfizetesiKozgyulesek kozgyules,
        List<SajatCegTulajdonosok> tulajdonosok,
        List<SajatCegKepviselok> kepviselok
    ) {
        Map<String, String> p = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        p.put(KEY_CURRENT_DATE, DATE_FORMAT.format(now.toLocalDate()));
        p.put(KEY_CURRENT_TS, DATE_TIME_FORMAT.format(now));

        addValue(p, KEY_ID, kozgyules.getId());
        addValue(p, KEY_HATAROZAT_SZAMA, kozgyules.getHatarozatSzama());
        addValue(p, KEY_HELYSZIN, kozgyules.getKozgyulesHelyszine());

        if (kozgyules.getKozgyulesDatum() != null) {
            java.time.LocalDate d = kozgyules.getKozgyulesDatum();
            p.put(KEY_DATUM, DATE_FORMAT.format(d));
            p.put(KEY_EV, String.valueOf(d.getYear()));
            p.put(KEY_HONAP, String.format("%02d", d.getMonthValue()));
            p.put(KEY_NAP, String.valueOf(d.getDayOfMonth()));
            p.put(KEY_HONAP_NEV, HONAP_NEVEK[d.getMonthValue()]);
        }

        addValue(p, KEY_MERLEG, kozgyules.getMerlegFoosszeg());
        addValue(p, KEY_ADOZOTT, kozgyules.getAdozottEredmeny());
        addValue(p, KEY_ELSZAMOLAS_GRAND_TOTAL, kozgyules.getElszamolasGrandTotal());
        addValue(p, KEY_ELSZAMOLAS_NAPIDIJAK, kozgyules.getElszamolasNapidijakOsszesen());

        // --- Képviselők (automatic ügyvezető placeholder) ---
        if (!kepviselok.isEmpty()) {
            String elsőKepvNev = kepviselok.get(0).getMaganszemely() != null
                && kepviselok.get(0).getMaganszemely().getMaganszemelyNeve() != null
                ? kepviselok.get(0).getMaganszemely().getMaganszemelyNeve()
                : "";
            p.put(KEY_UGYVEZETO_NEV, elsőKepvNev);
        }

        // --- Tulajdonosok / tagok placeholders ---
        if (!tulajdonosok.isEmpty() || !kepviselok.isEmpty()) {
            StringBuilder jelenlevok = new StringBuilder();
            StringBuilder osztalekLista = new StringBuilder();
            BigDecimal bruttoOsszesen = BigDecimal.ZERO;

            // Collect tulajdonos maganszemely IDs to detect ügyvezető overlap
            java.util.Set<Long> tulajdonosPersonIds = new java.util.HashSet<>();
            for (SajatCegTulajdonosok t : tulajdonosok) {
                if (t.getMaganszemely() != null && t.getMaganszemely().getId() != null) {
                    tulajdonosPersonIds.add(t.getMaganszemely().getId());
                }
            }

            // Az ügyvezető (legújabb képviselő) kerüljön az elejére, ha nem szerepel már
            // tulajdonosként (azt a ciklus úgyis hozzáadja alább)
            if (!kepviselok.isEmpty()) {
                SajatCegKepviselok latestKepv = kepviselok.get(0); // sorted id DESC
                Long kepvPersonId = latestKepv.getMaganszemely() != null ? latestKepv.getMaganszemely().getId() : null;
                boolean isTulajdonosIs = kepvPersonId != null && tulajdonosPersonIds.contains(kepvPersonId);
                if (!isTulajdonosIs) {
                    String ugyvNev = latestKepv.getMaganszemely() != null
                        && latestKepv.getMaganszemely().getMaganszemelyNeve() != null
                        ? latestKepv.getMaganszemely().getMaganszemelyNeve()
                        : "Ismeretlen";
                    jelenlevok.append("- ").append(ugyvNev).append(" (ügyvezető)");
                }
            }

            for (SajatCegTulajdonosok t : tulajdonosok) {
                String nev = t.getMaganszemely() != null && t.getMaganszemely().getMaganszemelyNeve() != null
                    ? t.getMaganszemely().getMaganszemelyNeve()
                    : "Ismeretlen";
                if (jelenlevok.length() > 0) jelenlevok.append("\n");
                jelenlevok.append("- ").append(nev);

                if (osztalekLista.length() > 0) osztalekLista.append("\n");
                osztalekLista.append("- ").append(nev).append(": ");
                if (t.getBruttoOsztalek() != null) {
                    osztalekLista.append(AMOUNT_FORMAT.format(t.getBruttoOsztalek())).append(" e HUF");
                    bruttoOsszesen = bruttoOsszesen.add(t.getBruttoOsztalek());
                } else {
                    osztalekLista.append("XXXX e HUF");
                }
            }

            // Always append the fixed FinTech Services Kft. dividend entry (10 e HUF in the document)
            if (osztalekLista.length() > 0) osztalekLista.append("\n");
            osztalekLista.append("- FinTech Services Kft.: 10 e HUF");

            p.put(KEY_JELENLEVOK, jelenlevok.toString());
            p.put(KEY_OSZTALEK_LISTA, osztalekLista.toString());
            p.put(KEY_BRUTTO_OSZTALEK_OSSZESEN, AMOUNT_FORMAT.format(bruttoOsszesen));

            // Individual signature placeholders:
            // képviselők first with "ügyvezető" role, then tulajdonosok as "tag"
            List<String[]> alairoSorok = new ArrayList<>(); // [0]=nev, [1]=szerep
            for (SajatCegKepviselok k : kepviselok) {
                String kNev = k.getMaganszemely() != null && k.getMaganszemely().getMaganszemelyNeve() != null
                    ? k.getMaganszemely().getMaganszemelyNeve()
                    : "Ismeretlen";
                alairoSorok.add(new String[] { kNev, "ügyvezető" });
            }
            for (int i = 0; i < Math.min(tulajdonosok.size(), MAX_ALAIRO); i++) {
                SajatCegTulajdonosok t = tulajdonosok.get(i);
                String nev = t.getMaganszemely() != null && t.getMaganszemely().getMaganszemelyNeve() != null
                    ? t.getMaganszemely().getMaganszemelyNeve()
                    : "Ismeretlen";
                alairoSorok.add(new String[] { nev, "tag" });
            }
            for (int i = 0; i < Math.min(alairoSorok.size(), MAX_ALAIRO); i++) {
                p.put("kozgyules.alairo_" + (i + 1) + "_nev", alairoSorok.get(i)[0]);
                p.put("kozgyules.alairo_" + (i + 1) + "_szerep", alairoSorok.get(i)[1]);
            }
            // Clear unused slots
            for (int i = alairoSorok.size() + 1; i <= MAX_ALAIRO; i++) {
                p.put("kozgyules.alairo_" + i + "_nev", "");
                p.put("kozgyules.alairo_" + i + "_szerep", "");
            }
        }

        // Fixed FinTech Services Kft. dividend (always 10 e HUF in the document)
        p.put(KEY_FINTECH_SERVICES_OSZTALEK, "10");

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
        if (value == null) return;
        if (value instanceof BigDecimal bd) {
            target.put(key, AMOUNT_FORMAT.format(bd));
        } else {
            target.put(key, value.toString());
        }
    }
}

package com.fintech.erp.service.document;

import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import com.fintech.erp.domain.Megrendelesek;
import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.SzerzodesesJogviszonyok;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Builds the placeholder map for Alvállalkozói TIG Word template generation.
 *
 * Template key format: {{key}}
 */
@Service
@Transactional(readOnly = true)
public class AlvallalkozoiTigTemplatePlaceholderService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    public Map<String, String> build(AlvallalkozoiElszamolasok elszamolas) {
        Map<String, String> p = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();
        p.put("mai_nap", DATE_FMT.format(today));

        // Elszámolás adatok
        put(p, "teljesitesi_idoszak_kezdete", elszamolas.getTeljesitesiIdoszakKezdete() != null
            ? DATE_FMT.format(elszamolas.getTeljesitesiIdoszakKezdete()) : "");
        put(p, "teljesitesi_idoszak_vege", elszamolas.getTeljesitesiIdoszakVege() != null
            ? DATE_FMT.format(elszamolas.getTeljesitesiIdoszakVege()) : "");
        put(p, "napok_szama", elszamolas.getNapokSzama() != null ? elszamolas.getNapokSzama().toPlainString() : "");
        put(p, "teljesites_osszeg", elszamolas.getTeljesitesIgazolasonSzereploOsszeg() != null
            ? formatAmount(elszamolas.getTeljesitesIgazolasonSzereploOsszeg()) : "");
        put(p, "teljesites_osszeg_betukkel", elszamolas.getTeljesitesIgazolasonSzereploOsszeg() != null
            ? NumberToHungarianWordsConverter.convert(elszamolas.getTeljesitesIgazolasonSzereploOsszeg().longValue()) : "");

        Megrendelesek m = elszamolas.getMegrendeles();
        if (m != null) {
            Hibernate.initialize(m);
            put(p, "megrendeles_szam", m.getMegrendelesSzam());
            put(p, "megrendeles_feladat", m.getFeladatRovidLeirasa());
            put(p, "megrendeles_kezdete", m.getMegrendelesKezdete() != null ? DATE_FMT.format(m.getMegrendelesKezdete()) : "");
            put(p, "megrendeles_vege", m.getMegrendelesVege() != null ? DATE_FMT.format(m.getMegrendelesVege()) : "");
            put(p, "megrendeles_devizanem", m.getDevizanem() != null ? m.getDevizanem().name() : "");
            put(p, "megrendeles_napidij", m.getDijOsszege() != null ? formatAmount(m.getDijOsszege()) : "");
            put(p, "megrendeles_napidij_betukkel", m.getDijOsszege() != null
                ? NumberToHungarianWordsConverter.convert(m.getDijOsszege().longValue()) : "");

            BigDecimal osszeg = m.getDijOsszege() != null && elszamolas.getNapokSzama() != null
                ? m.getDijOsszege().multiply(elszamolas.getNapokSzama()).setScale(0, RoundingMode.HALF_UP)
                : null;
            put(p, "netto_osszesen", osszeg != null ? formatAmount(osszeg) : "");
            put(p, "netto_osszesen_betukkel", osszeg != null
                ? NumberToHungarianWordsConverter.convert(osszeg.longValue()) : "");

            Munkakorok mk = m.getMunkakor();
            if (mk != null) {
                Hibernate.initialize(mk);
                put(p, "munkakor_nev", mk.getMunkakorNeve());
            }

            SzerzodesesJogviszonyok sz = m.getSzerzodesesJogviszony();
            if (sz != null) {
                Hibernate.initialize(sz);
                // Megrendelő cég adatok
                if (sz.getMegrendeloCeg() != null) {
                    Hibernate.initialize(sz.getMegrendeloCeg());
                    put(p, "megrendelo_ceg_nev", sz.getMegrendeloCeg().getCegNev());
                    put(p, "megrendelo_ceg_szekhely", sz.getMegrendeloCeg().getCegSzekhely());
                    put(p, "megrendelo_ceg_adoszam", sz.getMegrendeloCeg().getAdoszam());
                    put(p, "megrendelo_ceg_cegjegyzekszam", sz.getMegrendeloCeg().getCegjegyzekszam());
                }
                // Vállalkozó cég adatok
                if (sz.getVallalkozoCeg() != null) {
                    Hibernate.initialize(sz.getVallalkozoCeg());
                    put(p, "vallalkozo_ceg_nev", sz.getVallalkozoCeg().getCegNev());
                    put(p, "vallalkozo_ceg_szekhely", sz.getVallalkozoCeg().getCegSzekhely());
                    put(p, "vallalkozo_ceg_adoszam", sz.getVallalkozoCeg().getAdoszam());
                    put(p, "vallalkozo_ceg_cegjegyzekszam", sz.getVallalkozoCeg().getCegjegyzekszam());
                }
            }
        }

        return p;
    }

    private static void put(Map<String, String> map, String key, Object value) {
        map.put(key, value == null ? "" : Objects.toString(value));
    }

    private static String formatAmount(BigDecimal value) {
        if (value == null) return "";
        return String.format("%,.0f", value).replace(',', ' ');
    }
}

package com.fintech.erp.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Magyar állami ünnepnapok lekérdezése a Nager.Date nyilvános API-ból.
 *
 * Végpont: https://date.nager.at/api/v3/PublicHolidays/{year}/HU
 *
 * Az eredmény évenként cache-elve van (@Cacheable), így az API-t
 * csak egyszer hívja meg adott évre vonatkozóan.
 */
@Service
public class HungarianPublicHolidayService {

    private static final Logger LOG = LoggerFactory.getLogger(HungarianPublicHolidayService.class);

    private static final String NAGER_DATE_BASE_URL = "https://date.nager.at";
    private static final String COUNTRY_CODE = "HU";

    /** Cache név – a Caffeine/EhCache konfigurációban is fel kell venni, ha szükséges. */
    public static final String CACHE_NAME = "hungarianPublicHolidays";

    private final RestClient restClient;

    public HungarianPublicHolidayService() {
        this.restClient = RestClient.builder().baseUrl(NAGER_DATE_BASE_URL).defaultHeader("Accept", "application/json").build();
    }

    /**
     * Visszaadja a megadott év összes magyar ünnepnapjának dátumát.
     * Az eredmény cache-elve van az adott évre.
     *
     * @param year az év (pl. 2026)
     * @return az ünnepnapok {@link LocalDate} listája, hiba esetén üres lista
     */
    @Cacheable(value = CACHE_NAME, key = "#year")
    public List<LocalDate> getPublicHolidays(int year) {
        LOG.info("🗓️ Magyar ünnepnapok lekérdezése a Nager.Date API-ból: év={}", year);

        try {
            NagerHolidayResponse[] responses = restClient
                .get()
                .uri("/api/v3/PublicHolidays/{year}/{country}", year, COUNTRY_CODE)
                .retrieve()
                .body(NagerHolidayResponse[].class);

            if (responses == null || responses.length == 0) {
                LOG.warn("⚠️ Nager.Date API üres választ adott vissza: év={}", year);
                return Collections.emptyList();
            }

            List<LocalDate> holidays = java.util.Arrays.stream(responses).map(NagerHolidayResponse::date).filter(d -> d != null).toList();

            LOG.info("✅ {} ünnepnap találva a {} évre", holidays.size(), year);
            return holidays;
        } catch (RestClientException ex) {
            LOG.error("❌ Nem sikerült lekérni az ünnepnapokat a Nager.Date API-ból (év={}): {}", year, ex.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Megvizsgálja, hogy az adott dátum magyar munkaszüneti nap-e.
     *
     * @param date az ellenőrizendő dátum
     * @return {@code true} ha ünnepnap
     */
    public boolean isPublicHoliday(LocalDate date) {
        return getPublicHolidays(date.getYear()).contains(date);
    }

    // -------------------------------------------------------------------------
    // Belső record – a Nager.Date API válaszának modellje
    // -------------------------------------------------------------------------

    /**
     * A Nager.Date API egyetlen ünnepnap-bejegyzése.
     * Példa JSON:
     * <pre>
     * {
     *   "date": "2026-01-01",
     *   "localName": "Újév",
     *   "name": "New Year's Day",
     *   "countryCode": "HU",
     *   "fixed": true,
     *   "global": true,
     *   "counties": null,
     *   "launchYear": null,
     *   "types": ["Public"]
     * }
     * </pre>
     */
    public record NagerHolidayResponse(
        LocalDate date,
        String localName,
        String name,
        String countryCode,
        boolean fixed,
        boolean global,
        String[] counties,
        Integer launchYear,
        String[] types
    ) {}
}

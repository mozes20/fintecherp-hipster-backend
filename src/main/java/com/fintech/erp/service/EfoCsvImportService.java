package com.fintech.erp.service;

import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.document.DocumentFormat;
import com.fintech.erp.service.document.DocxTemplateEngine;
import com.fintech.erp.service.document.EfoTemplatePlaceholderService;
import com.fintech.erp.service.document.PdfConversionService;
import com.fintech.erp.service.dto.EfoCsvImportPreviewDTO;
import com.fintech.erp.service.dto.EfoCsvImportRequestDTO;
import com.fintech.erp.service.dto.EfoCsvImportResultDTO;
import com.fintech.erp.service.dto.EfoCsvMissingWorkerDTO;
import com.fintech.erp.service.dto.EfoCsvRecordDTO;
import com.fintech.erp.service.dto.EfoEmploymentGenerationDTO;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import com.fintech.erp.service.dto.MunkakorokDTO;
import com.fintech.erp.service.dto.MunkavallalokDTO;
import jakarta.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class EfoCsvImportService {

    private static final Logger LOG = LoggerFactory.getLogger(EfoCsvImportService.class);

    private static final Charset CSV_CHARSET = Charset.forName("windows-1252");
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private static final Path TEMPLATE_BASE_DIR = Path.of("uploads", "templates", "efo");
    private static final String DEFAULT_TEMPLATE_FILE = "munkaszerzodes_minta.docx";
    private static final Path OUTPUT_BASE_DIR = Path.of("uploads", "efo-foglalkoztatasok");
    private static final Locale HU_LOCALE = Locale.forLanguageTag("hu");
    private static final DateTimeFormatter ALT_INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter MONTH_NAME_FORMAT_HU_LONG = DateTimeFormatter.ofPattern("yyyy. MMMM d.", HU_LOCALE);
    private static final DateTimeFormatter MONTH_NAME_FORMAT_HU_SHORT = DateTimeFormatter.ofPattern("yyyy. MMM d.", HU_LOCALE);
    private static final DateTimeFormatter MONTH_NAME_FORMAT_EN_LONG = DateTimeFormatter.ofPattern("yyyy. MMMM d.", Locale.ENGLISH);
    private static final DateTimeFormatter MONTH_NAME_FORMAT_EN_SHORT = DateTimeFormatter.ofPattern("yyyy. MMM d.", Locale.ENGLISH);
    private static final Pattern SIMPLE_DOTTED_DATE_PATTERN = Pattern.compile("\\d{4}\\.\\s*\\d{1,2}\\.\\s*\\d{1,2}\\.?");
    private static final Pattern LIKELY_AMOUNT_PATTERN = Pattern.compile(
        "-?\\d{1,3}([ .]\\d{3})*(,[0-9]+|\\.[0-9]+)?( ?(ft|huf))?",
        Pattern.CASE_INSENSITIVE
    );
    private static final List<String> NAME_HEADER_KEYS = List.of(
        "nev",
        "munkavallalonev",
        "dolgozonev",
        "munkasnev",
        "szemelynev",
        "szuletesinev"
    );
    private static final List<String> TAX_ID_HEADER_KEYS = List.of(
        "adoazonositojel",
        "adoazonosito",
        "adoazon",
        "adoszam",
        "adoazonositoszam"
    );
    private static final List<String> DATE_HEADER_KEYS = List.of(
        "datum",
        "honaptol",
        "datumtol",
        "munkakezdes",
        "kezdes",
        "foglalkoztataskezdet"
    );
    private static final List<String> AMOUNT_HEADER_KEYS = List.of(
        "osszeg",
        "ber",
        "juttatas",
        "kifizetes",
        "brutto",
        "kifizetettbruttober",
        "fizetendo"
    );
    private static final List<String> HEADER_MARKERS = List.of("nev", "ado", "datum", "osszeg", "kifizetes", "ber");
    private static final List<String> MONTH_MARKERS = List.of(
        "jan",
        "feb",
        "mar",
        "apr",
        "maj",
        "jun",
        "jul",
        "aug",
        "szept",
        "okt",
        "nov",
        "dec",
        "januar",
        "februar",
        "marcius",
        "aprilis",
        "majus",
        "junius",
        "julius",
        "augusztus",
        "szeptember",
        "oktober",
        "november",
        "december",
        "january",
        "february",
        "march",
        "april",
        "may",
        "june",
        "july",
        "august",
        "september",
        "october",
        "november",
        "december"
    );

    private final MunkavallalokRepository munkavallalokRepository;
    private final MunkakorokRepository munkakorokRepository;
    private final EfoFoglalkoztatasokService efoFoglalkoztatasokService;
    private final EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;
    private final DocxTemplateEngine docxTemplateEngine;
    private final PdfConversionService pdfConversionService;
    private final EfoTemplatePlaceholderService placeholderService;

    public EfoCsvImportService(
        MunkavallalokRepository munkavallalokRepository,
        MunkakorokRepository munkakorokRepository,
        EfoFoglalkoztatasokService efoFoglalkoztatasokService,
        EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository,
        DocxTemplateEngine docxTemplateEngine,
        PdfConversionService pdfConversionService,
        EfoTemplatePlaceholderService placeholderService
    ) {
        this.munkavallalokRepository = munkavallalokRepository;
        this.munkakorokRepository = munkakorokRepository;
        this.efoFoglalkoztatasokService = efoFoglalkoztatasokService;
        this.efoFoglalkoztatasokRepository = efoFoglalkoztatasokRepository;
        this.docxTemplateEngine = docxTemplateEngine;
        this.pdfConversionService = pdfConversionService;
        this.placeholderService = placeholderService;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public EfoCsvImportPreviewDTO generatePreview(Long sajatCegId, byte[] csvBytes) {
        List<CsvEmploymentRow> rows = parseCsv(csvBytes);
        EfoCsvImportPreviewDTO preview = new EfoCsvImportPreviewDTO();
        Set<String> missingTaxIds = new HashSet<>();
        for (CsvEmploymentRow row : rows) {
            EfoCsvRecordDTO dto = toRecordDTO(row);
            Optional<Munkavallalok> workerOpt = findWorker(sajatCegId, row.taxId());
            if (workerOpt.isPresent()) {
                dto.setWorkerExists(true);
                dto.setMunkavallaloId(workerOpt.get().getId());
                dto.setStatusMessage("Meglévő munkavállaló");
            } else {
                dto.setWorkerExists(false);
                dto.setStatusMessage("Hiányzó munkavállaló");
                if (missingTaxIds.add(normalizeTaxId(row.taxId()))) {
                    preview.addMissingWorker(new EfoCsvMissingWorkerDTO(row.employeeName(), row.taxId()));
                }
            }
            preview.addRecord(dto);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(
                "Preview összeállítva: {} sor, {} ismert munkavállaló, {} hiányzó, {} hiba",
                preview.getRecords().size(),
                preview.getRecords().stream().filter(EfoCsvRecordDTO::isWorkerExists).count(),
                preview.getMissingWorkers().size(),
                preview.getErrors().size()
            );
        }
        return preview;
    }

    public EfoCsvImportResultDTO importCsv(EfoCsvImportRequestDTO request, byte[] csvBytes) throws IOException {
        List<CsvEmploymentRow> rows = parseCsv(csvBytes);
        validateTemplateExists();
        Path templatePath = TEMPLATE_BASE_DIR.resolve(DEFAULT_TEMPLATE_FILE).normalize();
        EfoCsvImportResultDTO result = new EfoCsvImportResultDTO();
        Map<String, Long> normalizedAssignments = request
            .getJobAssignments()
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey() != null && entry.getValue() != null)
            .collect(Collectors.toMap(entry -> normalizeTaxId(entry.getKey()), Map.Entry::getValue));

        for (CsvEmploymentRow row : rows) {
            String normalizedTaxId = normalizeTaxId(row.taxId());
            Optional<Munkavallalok> workerOpt = findWorker(request.getSajatCegId(), row.taxId());
            if (workerOpt.isEmpty()) {
                EfoCsvRecordDTO skipped = toRecordDTO(row);
                skipped.setStatusMessage("Hiányzó munkavállaló az adatbázisban");
                result.addSkipped(skipped);
                continue;
            }
            Munkavallalok worker = workerOpt.get();

            Long jobId = normalizedAssignments.get(normalizedTaxId);
            if (jobId == null) {
                EfoCsvRecordDTO skipped = toRecordDTO(row);
                skipped.setMunkavallaloId(worker.getId());
                skipped.setStatusMessage("Hiányzó munkakör hozzárendelés");
                result.addSkipped(skipped);
                continue;
            }

            Optional<Munkakorok> jobOpt = munkakorokRepository.findById(jobId);
            if (jobOpt.isEmpty()) {
                EfoCsvRecordDTO skipped = toRecordDTO(row);
                skipped.setMunkavallaloId(worker.getId());
                skipped.setStatusMessage("Ismeretlen munkakör azonosító: " + jobId);
                result.addSkipped(skipped);
                continue;
            }
            Munkakorok job = jobOpt.get();

            if (Boolean.FALSE.equals(job.getEfoMunkakor())) {
                EfoCsvRecordDTO skipped = toRecordDTO(row);
                skipped.setMunkavallaloId(worker.getId());
                skipped.setStatusMessage("A kiválasztott munkakör nincs EFO-ra jelölve");
                result.addSkipped(skipped);
                continue;
            }

            if (alreadyHasEmployment(worker.getId(), row.employmentDate())) {
                EfoCsvRecordDTO skipped = toRecordDTO(row);
                skipped.setMunkavallaloId(worker.getId());
                skipped.setStatusMessage("A megadott napra már létezik EFO foglalkoztatás");
                result.addSkipped(skipped);
                continue;
            }

            try {
                EfoEmploymentGenerationDTO generated = generateEmployment(request, row, worker, job, templatePath);
                result.addGenerated(generated);
            } catch (Exception ex) {
                LOG.error("Nem sikerült feldolgozni a(z) {}. sort: {}", row.rowNumber(), ex.getMessage(), ex);
                EfoCsvRecordDTO skipped = toRecordDTO(row);
                skipped.setMunkavallaloId(worker.getId());
                skipped.setStatusMessage("Hiba a dokumentum generálása során: " + ex.getMessage());
                result.addSkipped(skipped);
            }
        }
        return result;
    }

    private EfoEmploymentGenerationDTO generateEmployment(
        EfoCsvImportRequestDTO request,
        CsvEmploymentRow row,
        Munkavallalok worker,
        Munkakorok job,
        Path templatePath
    ) throws IOException {
        Map<String, String> placeholders = placeholderService.build(
            request.getSajatCegId(),
            worker,
            job,
            row.employmentDate(),
            row.amount()
        );
        byte[] filledDocx = docxTemplateEngine.populateTemplate(templatePath, placeholders);
        DocumentFormat format = request.getOutputFormat() != null ? request.getOutputFormat() : DocumentFormat.PDF;
        byte[] finalBytes = format == DocumentFormat.PDF
            ? pdfConversionService.convertDocxToPdf(filledDocx, buildFileNameBase(worker, row))
            : filledDocx;

        Path targetDir = OUTPUT_BASE_DIR.resolve(worker.getId().toString()).resolve(row.employmentDate().toString());
        Files.createDirectories(targetDir);
        String storedFileName = buildStoredFileName(worker, row, format);
        Path targetPath = targetDir.resolve(storedFileName).normalize();
        Files.write(targetPath, finalBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        if (request.isPersist()) {
            persistEmployment(row, worker, job, storedFileName);
        }

        EfoEmploymentGenerationDTO dto = new EfoEmploymentGenerationDTO();
        dto.setEmployeeName(row.employeeName());
        dto.setTaxId(row.taxId());
        dto.setEmploymentDate(row.employmentDate());
        dto.setGeneratedDocumentName(storedFileName);
        dto.setGeneratedDocumentUrl(buildRelativeUrl(worker, row, storedFileName));
        dto.setEmploymentId(resolveEmploymentId(worker.getId(), row.employmentDate()));
        return dto;
    }

    private void persistEmployment(CsvEmploymentRow row, Munkavallalok worker, Munkakorok job, String storedFileName) {
        EfoFoglalkoztatasokDTO dto = new EfoFoglalkoztatasokDTO();
        dto.setDatum(row.employmentDate());
        dto.setOsszeg(row.amount());
        dto.setGeneraltEfoSzerzodes(true);
        dto.setGeneraltDokumentumNev(storedFileName);
        dto.setGeneraltDokumentumUrl(buildRelativeUrl(worker, row, storedFileName));
        MunkavallalokDTO workerDto = new MunkavallalokDTO();
        workerDto.setId(worker.getId());
        dto.setMunkavallalo(workerDto);
        MunkakorokDTO jobDto = new MunkakorokDTO();
        jobDto.setId(job.getId());
        jobDto.setMunkakorKod(job.getMunkakorKod());
        jobDto.setMunkakorNeve(job.getMunkakorNeve());
        dto.setMunkakor(jobDto);
        efoFoglalkoztatasokService.save(dto);
    }

    private Long resolveEmploymentId(Long munkavallaloId, LocalDate datum) {
        return efoFoglalkoztatasokRepository
            .findFirstByMunkavallaloIdAndDatum(munkavallaloId, datum)
            .map(com.fintech.erp.domain.EfoFoglalkoztatasok::getId)
            .orElse(null);
    }

    private boolean alreadyHasEmployment(Long munkavallaloId, LocalDate datum) {
        return efoFoglalkoztatasokRepository.findFirstByMunkavallaloIdAndDatum(munkavallaloId, datum).isPresent();
    }

    private String buildRelativeUrl(Munkavallalok worker, CsvEmploymentRow row, String fileName) {
        return OUTPUT_BASE_DIR.resolve(worker.getId().toString())
            .resolve(row.employmentDate().toString())
            .resolve(fileName)
            .toString()
            .replace("\\", "/");
    }

    private String buildStoredFileName(Munkavallalok worker, CsvEmploymentRow row, DocumentFormat format) {
        String base = buildFileNameBase(worker, row);
        return base + format.getExtension();
    }

    private String buildFileNameBase(Munkavallalok worker, CsvEmploymentRow row) {
        String namePart = sanitizeFileName(
            worker.getMaganszemely() != null ? worker.getMaganszemely().getMaganszemelyNeve() : row.employeeName()
        );
        String datePart = row.employmentDate() != null ? row.employmentDate().format(DateTimeFormatter.BASIC_ISO_DATE) : "datum-nelkul";
        return namePart + "_" + datePart;
    }

    private String sanitizeFileName(String input) {
        if (!StringUtils.hasText(input)) {
            return "munkaszerzodes";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.replaceAll("[^a-zA-Z0-9_.-]", "_").toLowerCase(Locale.ROOT);
    }

    private Optional<Munkavallalok> findWorker(Long sajatCegId, String taxIdRaw) {
        if (sajatCegId == null || !StringUtils.hasText(taxIdRaw)) {
            return Optional.empty();
        }
        return munkavallalokRepository.findBySajatCegIdAndMaganszemelyAdoAzonositoJel(sajatCegId, taxIdRaw);
    }

    private void validateTemplateExists() throws IOException {
        Path templatePath = TEMPLATE_BASE_DIR.resolve(DEFAULT_TEMPLATE_FILE).normalize();
        if (!Files.exists(templatePath)) {
            throw new IOException("Az EFO sablon fájl nem található: " + templatePath);
        }
    }

    private EfoCsvRecordDTO toRecordDTO(CsvEmploymentRow row) {
        EfoCsvRecordDTO dto = new EfoCsvRecordDTO();
        dto.setRowNumber(row.rowNumber());
        dto.setEmployeeName(row.employeeName());
        dto.setTaxId(row.taxId());
        dto.setEmploymentDate(row.employmentDate());
        dto.setAmount(row.amount());
        return dto;
    }

    private List<CsvEmploymentRow> parseCsv(byte[] csvBytes) {
        if (csvBytes == null || csvBytes.length == 0) {
            return new ArrayList<>();
        }
        char delimiter = detectDelimiter(csvBytes);
        List<CsvEmploymentRow> rows = parseCsvInternal(csvBytes, delimiter, true);
        if (rows.isEmpty()) {
            rows = parseCsvInternal(csvBytes, delimiter, false);
        }
        if (rows.isEmpty()) {
            rows = parseCsvManually(csvBytes, delimiter);
        }
        if (rows.isEmpty()) {
            LOG.warn("CSV feldolgozás sikertelen: egyetlen sor sem maradt a feldolgozás után");
        } else {
            LOG.debug("CSV feldolgozás sikeres: {} sor került beolvasásra", rows.size());
        }
        return rows;
    }

    private List<CsvEmploymentRow> parseCsvInternal(byte[] csvBytes, char delimiter, boolean headerExpected) {
        List<CsvEmploymentRow> rows = new ArrayList<>();
        CSVFormat.Builder builder = CSVFormat.DEFAULT.builder()
            .setDelimiter(delimiter)
            .setIgnoreSurroundingSpaces(true)
            .setTrim(true)
            .setQuote('"');
        if (headerExpected) {
            builder.setHeader();
            builder.setSkipHeaderRecord(true);
        } else {
            builder.setSkipHeaderRecord(false);
        }
        CSVFormat format = builder.build();
        try (
            Reader reader = new InputStreamReader(new ByteArrayInputStream(csvBytes), CSV_CHARSET);
            CSVParser parser = format.parse(reader)
        ) {
            Map<String, Integer> headerIndex = new HashMap<>();
            if (headerExpected) {
                try {
                    Map<String, Integer> headerMap = parser.getHeaderMap();
                    if (headerMap != null) {
                        for (Map.Entry<String, Integer> entry : headerMap.entrySet()) {
                            if (entry.getKey() == null) {
                                continue;
                            }
                            String normalizedHeader = normalizeHeader(entry.getKey());
                            if (StringUtils.hasText(normalizedHeader) && !headerIndex.containsKey(normalizedHeader)) {
                                headerIndex.put(normalizedHeader, entry.getValue());
                            }
                        }
                    }
                } catch (IllegalStateException ex) {
                    LOG.debug("Nem sikerült a fejlécet beolvasni: {}", ex.getMessage());
                    headerIndex.clear();
                }
            }
            int rowNumber = headerExpected ? 2 : 1;
            for (CSVRecord record : parser) {
                final int size = record.size();
                IntFunction<String> accessor = index -> index >= 0 && index < size ? sanitizeCell(record.get(index)) : null;
                CsvEmploymentRow row = mapRecord(rowNumber++, headerIndex, accessor, size);
                if (row != null) {
                    rows.add(row);
                }
            }
        } catch (IOException | IllegalArgumentException ex) {
            LOG.error("Nem sikerült beolvasni a CSV fájlt: {}", ex.getMessage(), ex);
        }
        return rows;
    }

    private List<CsvEmploymentRow> parseCsvManually(byte[] csvBytes, char delimiter) {
        List<CsvEmploymentRow> rows = new ArrayList<>();
        String content = new String(csvBytes, CSV_CHARSET);
        if (!StringUtils.hasText(content)) {
            return rows;
        }
        String[] lines = content.split("\\r?\\n");
        Map<String, Integer> headerIndex = new HashMap<>();
        boolean headerProcessed = false;
        int rowNumber = 1;
        for (String rawLine : lines) {
            if (rawLine == null) {
                continue;
            }
            String normalizedLine = sanitizeLine(rawLine);
            if (!StringUtils.hasText(normalizedLine)) {
                continue;
            }
            if (!headerProcessed) {
                String[] headerTokens = splitTokens(normalizedLine, delimiter);
                for (int i = 0; i < headerTokens.length; i++) {
                    String normalizedHeader = normalizeHeader(headerTokens[i]);
                    if (StringUtils.hasText(normalizedHeader) && !headerIndex.containsKey(normalizedHeader)) {
                        headerIndex.put(normalizedHeader, i);
                    }
                }
                headerProcessed = true;
                rowNumber++;
                continue;
            }

            String[] tokens = splitTokens(normalizedLine, delimiter);
            List<String> values = new ArrayList<>(tokens.length);
            for (String token : tokens) {
                values.add(sanitizeCell(token));
            }
            final int size = values.size();
            IntFunction<String> accessor = index -> index >= 0 && index < size ? values.get(index) : null;
            int currentRowNumber = rowNumber++;
            CsvEmploymentRow row = mapRecord(currentRowNumber, headerIndex, accessor, size);
            if (row != null) {
                rows.add(row);
            }
        }
        return rows;
    }

    private String sanitizeLine(String rawLine) {
        return rawLine.replace("\uFEFF", "").replace('\r', ' ').trim();
    }

    private String[] splitTokens(String line, char delimiter) {
        return line.split(Pattern.quote(String.valueOf(delimiter)), -1);
    }

    private CsvEmploymentRow mapRecord(int rowNumber, Map<String, Integer> headerIndex, IntFunction<String> accessor, int size) {
        if ((headerIndex == null || headerIndex.isEmpty()) && looksLikeHeaderRow(accessor, size)) {
            return null;
        }

        String name = extractValue(headerIndex, NAME_HEADER_KEYS, accessor, size);
        if (!StringUtils.hasText(name)) {
            name = guessName(accessor, size);
        }

        String taxIdRaw = extractValue(headerIndex, TAX_ID_HEADER_KEYS, accessor, size);
        if (!StringUtils.hasText(taxIdRaw)) {
            taxIdRaw = guessTaxId(accessor, size);
        }

        String dateRaw = extractValue(headerIndex, DATE_HEADER_KEYS, accessor, size);
        if (!StringUtils.hasText(dateRaw)) {
            dateRaw = guessDate(accessor, size);
        }

        String amountRaw = extractValue(headerIndex, AMOUNT_HEADER_KEYS, accessor, size);
        if (!StringUtils.hasText(amountRaw)) {
            amountRaw = guessAmount(accessor, size);
        }

        if (!StringUtils.hasText(name) && !StringUtils.hasText(taxIdRaw)) {
            LOG.warn("A(z) {}. sor kihagyásra került, mert sem név, sem adóazonosító nem található", rowNumber);
            return null;
        }

        String cleanedTaxId = sanitizeTaxId(taxIdRaw);
        LocalDate date = parseDate(dateRaw);
        BigDecimal amount = parseAmount(amountRaw);
        String employeeName = StringUtils.hasText(name) ? name.trim() : "";
        String finalTaxId = StringUtils.hasText(cleanedTaxId) ? cleanedTaxId : "";
        return new CsvEmploymentRow(rowNumber, employeeName, finalTaxId, date, amount);
    }

    private String extractValue(Map<String, Integer> headerIndex, List<String> keys, IntFunction<String> accessor, int size) {
        if (headerIndex == null || headerIndex.isEmpty()) {
            return null;
        }
        for (String key : keys) {
            String value = valueForHeaderKey(headerIndex, key, accessor, size);
            if (StringUtils.hasText(value)) {
                return value;
            }
        }
        return null;
    }

    private String valueForHeaderKey(Map<String, Integer> headerIndex, String key, IntFunction<String> accessor, int size) {
        Integer exact = headerIndex.get(key);
        if (exact != null && exact >= 0 && exact < size) {
            String candidate = accessor.apply(exact);
            if (StringUtils.hasText(candidate)) {
                return candidate;
            }
        }
        for (Map.Entry<String, Integer> entry : headerIndex.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (entry.getKey().contains(key)) {
                int index = entry.getValue();
                if (index >= 0 && index < size) {
                    String candidate = accessor.apply(index);
                    if (StringUtils.hasText(candidate)) {
                        return candidate;
                    }
                }
            }
        }
        return null;
    }

    private boolean looksLikeHeaderRow(IntFunction<String> accessor, int size) {
        int markerMatches = 0;
        for (int i = 0; i < size; i++) {
            String value = accessor.apply(i);
            if (!StringUtils.hasText(value)) {
                continue;
            }
            String normalized = normalizeHeader(value);
            for (String marker : HEADER_MARKERS) {
                if (normalized.contains(marker)) {
                    markerMatches++;
                    break;
                }
            }
        }
        return markerMatches >= 2;
    }

    private String guessName(IntFunction<String> accessor, int size) {
        for (int i = 0; i < size; i++) {
            String value = accessor.apply(i);
            if (!StringUtils.hasText(value)) {
                continue;
            }
            if (looksLikeName(value)) {
                return value;
            }
        }
        return null;
    }

    private boolean looksLikeName(String value) {
        String trimmed = value.trim();
        if (!StringUtils.hasText(trimmed)) {
            return false;
        }
        long letterCount = trimmed.chars().filter(Character::isLetter).count();
        if (letterCount < 2) {
            return false;
        }
        String digitsOnly = trimmed.replaceAll("[^0-9]", "");
        if (digitsOnly.length() >= trimmed.length() - 1) {
            return false;
        }
        return trimmed.contains(" ");
    }

    private String guessTaxId(IntFunction<String> accessor, int size) {
        for (int i = 0; i < size; i++) {
            String value = accessor.apply(i);
            if (!StringUtils.hasText(value)) {
                continue;
            }
            String sanitized = sanitizeTaxId(value);
            if (StringUtils.hasText(sanitized) && sanitized.length() >= 9 && sanitized.length() <= 12) {
                return sanitized;
            }
        }
        return null;
    }

    private String guessDate(IntFunction<String> accessor, int size) {
        String fallback = null;
        for (int i = 0; i < size; i++) {
            String value = accessor.apply(i);
            if (!StringUtils.hasText(value)) {
                continue;
            }
            String trimmed = value.trim();
            if (SIMPLE_DOTTED_DATE_PATTERN.matcher(trimmed).matches()) {
                return trimmed;
            }
            if (fallback == null && containsMonthName(trimmed)) {
                fallback = trimmed;
            }
        }
        return fallback;
    }

    private boolean containsMonthName(String value) {
        String lowered = value.toLowerCase(Locale.ROOT);
        for (String marker : MONTH_MARKERS) {
            if (lowered.contains(marker)) {
                return true;
            }
        }
        return false;
    }

    private String guessAmount(IntFunction<String> accessor, int size) {
        for (int i = size - 1; i >= 0; i--) {
            String value = accessor.apply(i);
            if (!StringUtils.hasText(value)) {
                continue;
            }
            String normalized = value.replace('\u00A0', ' ').trim();
            if (LIKELY_AMOUNT_PATTERN.matcher(normalized).matches()) {
                return value;
            }
        }
        return null;
    }

    private LocalDate parseDate(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String sanitized = sanitizeCell(value);
        if (!StringUtils.hasText(sanitized)) {
            return null;
        }
        String compactDotted = sanitized.replaceAll("\\.\\s+", ".").replaceAll("\\s+\\.", ".");
        if (SIMPLE_DOTTED_DATE_PATTERN.matcher(compactDotted).matches()) {
            String normalized = compactDotted.replace(" ", "");
            if (!normalized.endsWith(".")) {
                normalized = normalized + ".";
            }
            try {
                return LocalDate.parse(normalized, INPUT_DATE_FORMAT);
            } catch (DateTimeParseException ignored) {
                try {
                    return LocalDate.parse(normalized.substring(0, normalized.length() - 1), ALT_INPUT_DATE_FORMAT);
                } catch (DateTimeParseException ignoredAgain) {
                    // fall through
                }
            }
        }
        try {
            return LocalDate.parse(sanitized, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ignored) {
            for (DateTimeFormatter formatter : List.of(
                MONTH_NAME_FORMAT_HU_LONG,
                MONTH_NAME_FORMAT_HU_SHORT,
                MONTH_NAME_FORMAT_EN_LONG,
                MONTH_NAME_FORMAT_EN_SHORT
            )) {
                try {
                    return LocalDate.parse(sanitized, formatter);
                } catch (DateTimeParseException ignoredAgain) {
                    // try next
                }
            }
            LOG.warn("Ismeretlen dátum formátum: {}", sanitized);
            return null;
        }
    }

    private BigDecimal parseAmount(String value) {
        String normalized = sanitizeNumeric(value);
        if (!StringUtils.hasText(normalized)) {
            return null;
        }
        try {
            return new BigDecimal(normalized);
        } catch (NumberFormatException ex) {
            LOG.warn("Ismeretlen összeg formátum: {}", value);
            return null;
        }
    }

    private char detectDelimiter(byte[] csvBytes) {
        String sample = new String(csvBytes, 0, Math.min(csvBytes.length, 2048), StandardCharsets.UTF_8);
        long semicolons = sample.chars().filter(ch -> ch == ';').count();
        long commas = sample.chars().filter(ch -> ch == ',').count();
        return semicolons >= commas ? ';' : ',';
    }

    private String sanitizeTaxId(String value) {
        String sanitized = sanitizeCell(value);
        if (!StringUtils.hasText(sanitized)) {
            return null;
        }
        return sanitized.replaceAll("[^0-9A-Za-z]", "").toUpperCase(Locale.ROOT);
    }

    private String sanitizeNumeric(String value) {
        String sanitized = sanitizeCell(value);
        if (!StringUtils.hasText(sanitized)) {
            return sanitized;
        }
        String normalized = sanitized.replace(" ", "").replace("\u00A0", "");
        normalized = normalized.replace("Ft", "").replace("ft", "").replace("HUF", "").replace("huf", "");
        normalized = normalized.replace(".", "").replace(",", ".");
        if (normalized.startsWith("=")) {
            normalized = normalized.substring(1);
        }
        return normalized;
    }

    private String sanitizeCell(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.replace('\u00A0', ' ').trim();
        if (trimmed.startsWith("=\"") && trimmed.endsWith("\"")) {
            trimmed = trimmed.substring(2, trimmed.length() - 1);
        } else if (trimmed.startsWith("='") && trimmed.endsWith("'")) {
            trimmed = trimmed.substring(2, trimmed.length() - 1);
        } else if ((trimmed.startsWith("\"") && trimmed.endsWith("\"")) || (trimmed.startsWith("'") && trimmed.endsWith("'"))) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        } else if (trimmed.startsWith("=") && trimmed.length() > 1) {
            trimmed = trimmed.substring(1);
        }
        return trimmed.trim();
    }

    private String normalizeHeader(String header) {
        if (header == null) {
            return "";
        }
        String sanitized = header.replace("\uFEFF", "");
        String normalized = Normalizer.normalize(sanitized, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "");
    }

    private String normalizeTaxId(String taxId) {
        String sanitized = sanitizeTaxId(taxId);
        return StringUtils.hasText(sanitized) ? sanitized : "";
    }

    private record CsvEmploymentRow(int rowNumber, String employeeName, String taxId, LocalDate employmentDate, BigDecimal amount) {}
}

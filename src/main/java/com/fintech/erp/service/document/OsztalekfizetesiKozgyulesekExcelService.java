package com.fintech.erp.service.document;

import com.fintech.erp.domain.Berek;
import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.repository.BerekRepository;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.web.rest.vm.OsztalekfizetesiElszamolasExcelRequest;
import com.fintech.erp.web.rest.vm.OsztalekfizetesiElszamolasExcelRequest.EgyebKoltsegSor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for generating the OsztalekfizetesiKozgyulesek settlement Excel export.
 *
 * <p>Structure (matching the reference spreadsheet):
 * <pre>
 * Row 1 : (blank)
 * Row 2 : Header row — columns B..F
 * Row 3‥n : Worker rows (one per munkavallalo)
 * Row n+1 : Total számlázott összeg (bold, col F)
 * Row n+2 : (blank)
 * Row n+3‥: Overhead items (megnevezes | osszeg e HUF)
 * Last row: Grand total of all costs (bold)
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class OsztalekfizetesiKozgyulesekExcelService {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekExcelService.class);

    /** Korrigált napidíj cap (UIM EFO maximum, e HUF) */
    private static final BigDecimal KORIGALT_NAPDIJ = BigDecimal.valueOf(100);

    // Column indices (0-based)
    private static final int COL_NEV = 0; // A
    private static final int COL_TELJES = 1; // B – Teljes költség (e HUF)
    private static final int COL_NAPOK = 2; // C – Ledolgozott napok száma
    private static final int COL_NAPDIJ = 3; // D – Napidíj
    private static final int COL_KORIG = 4; // E – Korrigált napidíj
    private static final int COL_SZAMLA = 5; // F – Számlázott összeg

    private final OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository;
    private final EfoFoglalkoztatasokRepository efoRepository;
    private final BerekRepository berekRepository;

    public OsztalekfizetesiKozgyulesekExcelService(
        OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository,
        EfoFoglalkoztatasokRepository efoRepository,
        BerekRepository berekRepository
    ) {
        this.kozgyulesekRepository = kozgyulesekRepository;
        this.efoRepository = efoRepository;
        this.berekRepository = berekRepository;
    }

    // -------------------------------------------------------------------------

    /**
     * Generate the Excel workbook as a byte array.
     *
     * @param kozgyulesId ID of the OsztalekfizetesiKozgyulesek record
     * @param request     optional overhead items
     * @return .xlsx bytes
     */
    public byte[] generateExcel(Long kozgyulesId, OsztalekfizetesiElszamolasExcelRequest request) throws IOException {
        OsztalekfizetesiKozgyulesek kozgyules = kozgyulesekRepository
            .findById(kozgyulesId)
            .orElseThrow(() -> new IllegalArgumentException("Közgyűlés nem található: " + kozgyulesId));

        if (kozgyules.getSajatCeg() == null || kozgyules.getKozgyulesDatum() == null) {
            throw new IllegalArgumentException("A közgyűléshez nincs társítva saját cég vagy dátum.");
        }

        Long sajatCegId = kozgyules.getSajatCeg().getId();
        int ev = kozgyules.getKozgyulesDatum().getYear();

        // Load EFO entries for this company and year
        List<EfoFoglalkoztatasok> efoList = efoRepository.findAllBySajatCegIdAndEv(sajatCegId, ev);

        // Group EFO entries by munkavallalo
        Map<Long, List<EfoFoglalkoztatasok>> byWorker = new LinkedHashMap<>();
        for (EfoFoglalkoztatasok e : efoList) {
            if (e.getMunkavallalo() != null) {
                byWorker.computeIfAbsent(e.getMunkavallalo().getId(), k -> new ArrayList<>()).add(e);
            }
        }

        List<EgyebKoltsegSor> egyebKoltsegek = (request != null && request.getEgyebKoltsegek() != null)
            ? request.getEgyebKoltsegek()
            : List.of();

        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Elszámolás");

            // Build styles
            ExcelStyles styles = new ExcelStyles(wb);

            // Column widths
            sheet.setColumnWidth(COL_NEV, 7000);
            sheet.setColumnWidth(COL_TELJES, 5000);
            sheet.setColumnWidth(COL_NAPOK, 5500);
            sheet.setColumnWidth(COL_NAPDIJ, 3500);
            sheet.setColumnWidth(COL_KORIG, 5000);
            sheet.setColumnWidth(COL_SZAMLA, 5000);

            int rowIdx = 0;

            // ---- Row 0: blank ----
            sheet.createRow(rowIdx++);

            // ---- Row 1: header ----
            Row header = sheet.createRow(rowIdx++);
            header.setHeight((short) 1200);
            createCell(header, COL_NEV, "", styles.headerStyle);
            createCell(header, COL_TELJES, "Teljes költség\n(e HUF)", styles.headerStyle);
            createCell(header, COL_NAPOK, "Ledolgozott napok száma", styles.headerGreenStyle);
            createCell(header, COL_NAPDIJ, "Napidíj", styles.headerStyle);
            createCell(header, COL_KORIG, "Korrigált napidíj", styles.headerGreenStyle);
            createCell(header, COL_SZAMLA, "Számlázott összeg", styles.headerStyle);

            BigDecimal szamlazottTotal = BigDecimal.ZERO;

            // ---- Worker rows ----
            for (Map.Entry<Long, List<EfoFoglalkoztatasok>> entry : byWorker.entrySet()) {
                Long munkavallaloId = entry.getKey();
                List<EfoFoglalkoztatasok> workerEfos = entry.getValue();

                Munkavallalok worker = workerEfos.get(0).getMunkavallalo();
                String workerNev = resolveWorkerName(worker);

                int ledolgozottNapok = workerEfos.size();

                // Teljes költség and napidíj from Berek if available
                BigDecimal teljesKoltseg;
                BigDecimal napdij;
                Optional<Berek> berekOpt = berekRepository.findFirstByMunkavallalo_IdOrderByErvenyessegKezdeteDesc(munkavallaloId);
                if (berekOpt.isPresent()) {
                    Berek ber = berekOpt.orElseThrow();
                    teljesKoltseg = ber.getTeljesKoltseg() != null ? ber.getTeljesKoltseg() : sumOssszeg(workerEfos);
                    napdij = ber.getBruttoHaviMunkaberVagyNapdij() != null
                        ? ber.getBruttoHaviMunkaberVagyNapdij()
                        : divideOrZero(teljesKoltseg, ledolgozottNapok);
                } else {
                    teljesKoltseg = sumOssszeg(workerEfos);
                    napdij = divideOrZero(teljesKoltseg, ledolgozottNapok);
                }

                BigDecimal szamlazottOsszeg = KORIGALT_NAPDIJ.multiply(BigDecimal.valueOf(ledolgozottNapok));
                szamlazottTotal = szamlazottTotal.add(szamlazottOsszeg);

                Row row = sheet.createRow(rowIdx++);
                createCell(row, COL_NEV, workerNev, styles.dataStyle);
                createNumCell(row, COL_TELJES, teljesKoltseg, styles.dataNumStyle);
                createNumCell(row, COL_NAPOK, BigDecimal.valueOf(ledolgozottNapok), styles.dataGreenNumStyle);
                createNumCell(row, COL_NAPDIJ, napdij.setScale(0, RoundingMode.HALF_UP), styles.dataNumStyle);
                createNumCell(row, COL_KORIG, KORIGALT_NAPDIJ, styles.dataGreenNumStyle);
                createNumCell(row, COL_SZAMLA, szamlazottOsszeg, styles.dataNumStyle);
            }

            // ---- Total számlázott összeg row ----
            Row totalRow = sheet.createRow(rowIdx++);
            for (int c = COL_NEV; c <= COL_NAPDIJ; c++) {
                createCell(totalRow, c, "", styles.dataStyle);
            }
            createCell(totalRow, COL_KORIG, "", styles.dataStyle);
            createNumCell(totalRow, COL_SZAMLA, szamlazottTotal, styles.boldNumStyle);

            // ---- Blank row ----
            sheet.createRow(rowIdx++);

            // ---- Overhead section ----
            BigDecimal egyebTotal = BigDecimal.ZERO;
            for (EgyebKoltsegSor sor : egyebKoltsegek) {
                BigDecimal osszeg = sor.getOsszeg() != null ? sor.getOsszeg() : BigDecimal.ZERO;
                egyebTotal = egyebTotal.add(osszeg);

                Row row = sheet.createRow(rowIdx++);
                createCell(row, COL_NEV, sor.getMegnevezes() != null ? sor.getMegnevezes() : "", styles.dataStyle);
                createNumCell(row, COL_TELJES, osszeg, styles.dataNumStyle);
                for (int c = COL_NAPOK; c <= COL_SZAMLA; c++) {
                    createCell(row, c, "", styles.dataStyle);
                }
            }

            // ---- Grand total row ----
            // Grand total row — sum of all worker costs (B column) + overhead
            BigDecimal workerTeljesSum = recalcWorkerTeljesSum(byWorker);
            BigDecimal grandTotalAll = workerTeljesSum.add(egyebTotal);

            Row grandTotalRow = sheet.createRow(rowIdx++);
            createCell(grandTotalRow, COL_NEV, "", styles.dataStyle);
            createNumCell(grandTotalRow, COL_TELJES, grandTotalAll, styles.boldNumStyle);
            for (int c = COL_NAPOK; c <= COL_SZAMLA; c++) {
                createCell(grandTotalRow, c, "", styles.dataStyle);
            }

            LOG.debug(
                "Excel generated: {} worker(s), {} overhead items, grand total={} e HUF",
                byWorker.size(),
                egyebKoltsegek.size(),
                grandTotalAll
            );

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private String resolveWorkerName(Munkavallalok worker) {
        if (worker == null) return "Ismeretlen";
        if (worker.getMaganszemely() != null && worker.getMaganszemely().getMaganszemelyNeve() != null) {
            return worker.getMaganszemely().getMaganszemelyNeve();
        }
        return "Munkavállaló #" + worker.getId();
    }

    private BigDecimal sumOssszeg(List<EfoFoglalkoztatasok> list) {
        return list.stream().map(e -> e.getOsszeg() != null ? e.getOsszeg() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal divideOrZero(BigDecimal total, int count) {
        if (count == 0) return BigDecimal.ZERO;
        return total.divide(BigDecimal.valueOf(count), 0, RoundingMode.HALF_UP);
    }

    private BigDecimal recalcWorkerTeljesSum(Map<Long, List<EfoFoglalkoztatasok>> byWorker) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Long, List<EfoFoglalkoztatasok>> entry : byWorker.entrySet()) {
            Optional<Berek> berekOpt = berekRepository.findFirstByMunkavallalo_IdOrderByErvenyessegKezdeteDesc(entry.getKey());
            if (berekOpt.isPresent() && berekOpt.orElseThrow().getTeljesKoltseg() != null) {
                sum = sum.add(berekOpt.orElseThrow().getTeljesKoltseg());
            } else {
                sum = sum.add(sumOssszeg(entry.getValue()));
            }
        }
        return sum;
    }

    private void createCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void createNumCell(Row row, int col, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(col);
        if (value != null) {
            cell.setCellValue(value.doubleValue());
        }
        cell.setCellStyle(style);
    }

    // -------------------------------------------------------------------------
    // Style builder
    // -------------------------------------------------------------------------

    private static final class ExcelStyles {

        final XSSFCellStyle headerStyle;
        final XSSFCellStyle headerGreenStyle;
        final XSSFCellStyle dataStyle;
        final XSSFCellStyle dataNumStyle;
        final XSSFCellStyle dataGreenNumStyle;
        final XSSFCellStyle boldNumStyle;

        ExcelStyles(XSSFWorkbook wb) {
            // Colours
            XSSFColor green = new XSSFColor(new byte[] { (byte) 0xC6, (byte) 0xEF, (byte) 0xCE }, new DefaultIndexedColorMap());

            Font boldFont = wb.createFont();
            boldFont.setBold(true);

            Font normalFont = wb.createFont();

            // Header base
            headerStyle = wb.createCellStyle();
            headerStyle.setFont(boldFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setWrapText(true);
            applyThinBorders(headerStyle);

            // Header green
            headerGreenStyle = wb.createCellStyle();
            headerGreenStyle.cloneStyleFrom(headerStyle);
            headerGreenStyle.setFillForegroundColor(green);
            headerGreenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Data text
            dataStyle = wb.createCellStyle();
            dataStyle.setFont(normalFont);
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            applyThinBorders(dataStyle);

            // Data number
            dataNumStyle = wb.createCellStyle();
            dataNumStyle.cloneStyleFrom(dataStyle);
            dataNumStyle.setAlignment(HorizontalAlignment.RIGHT);
            dataNumStyle.setDataFormat(wb.createDataFormat().getFormat("#,##0"));

            // Data number green
            dataGreenNumStyle = wb.createCellStyle();
            dataGreenNumStyle.cloneStyleFrom(dataNumStyle);
            dataGreenNumStyle.setFillForegroundColor(green);
            dataGreenNumStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Bold number (totals)
            boldNumStyle = wb.createCellStyle();
            boldNumStyle.cloneStyleFrom(dataNumStyle);
            boldNumStyle.setFont(boldFont);
        }

        private void applyThinBorders(CellStyle s) {
            s.setBorderTop(BorderStyle.THIN);
            s.setBorderBottom(BorderStyle.THIN);
            s.setBorderLeft(BorderStyle.THIN);
            s.setBorderRight(BorderStyle.THIN);
            s.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            s.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            s.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            s.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        }
    }
}

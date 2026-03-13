package com.fintech.erp.service.document;

import com.fintech.erp.domain.Berek;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.domain.SajatCegTulajdonosok;
import com.fintech.erp.repository.BerekRepository;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.repository.SajatCegTulajdonosokRepository;
import com.fintech.erp.repository.TimesheetekRepository;
import com.fintech.erp.web.rest.vm.OsztalekfizetesiElszamolasExcelRequest;
import com.fintech.erp.web.rest.vm.OsztalekfizetesiElszamolasExcelRequest.EgyebKoltsegSor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
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
@Transactional
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
    private final MunkavallalokRepository munkavallalokRepository;
    private final SajatCegTulajdonosokRepository tulajdonosokRepository;
    private final TimesheetekRepository timesheetekRepository;
    private final BerekRepository berekRepository;

    public OsztalekfizetesiKozgyulesekExcelService(
        OsztalekfizetesiKozgyulesekRepository kozgyulesekRepository,
        MunkavallalokRepository munkavallalokRepository,
        SajatCegTulajdonosokRepository tulajdonosokRepository,
        TimesheetekRepository timesheetekRepository,
        BerekRepository berekRepository
    ) {
        this.kozgyulesekRepository = kozgyulesekRepository;
        this.munkavallalokRepository = munkavallalokRepository;
        this.tulajdonosokRepository = tulajdonosokRepository;
        this.timesheetekRepository = timesheetekRepository;
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

        // Load all employees of the company
        List<Munkavallalok> munkavallalok = munkavallalokRepository.findBySajatCegIdWithMaganszemely(sajatCegId);

        // Load all owners of the company
        List<SajatCegTulajdonosok> tulajdonosok = tulajdonosokRepository.findBySajatCegIdWithMaganszemely(sajatCegId);

        // Build merged person list: workers first, then owners not already present as workers
        List<PersonRow> persons = buildPersonRows(munkavallalok, tulajdonosok);

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

            int workerStartRowIdx = rowIdx; // 0-based, Excel row = workerStartRowIdx + 1

            // Server-side accumulators (mirroring the Excel formulas) — persisted to DB
            BigDecimal workerTeljesKoltsegSum = BigDecimal.ZERO;
            BigDecimal szamlazottTotal = BigDecimal.ZERO;

            // ---- Worker/Owner rows ----
            for (PersonRow person : persons) {
                // Count worked days from Timesheetek for this person and year
                int ledolgozottNapok = person.munkavallaloId != null
                    ? timesheetekRepository.findByMunkavallaloIdAndEv(person.munkavallaloId, ev).size()
                    : 0;

                // Teljes költség from Berek (only available for munkavallalok)
                BigDecimal teljesKoltseg = BigDecimal.ZERO;
                if (person.munkavallaloId != null) {
                    Optional<Berek> berekOpt = berekRepository.findFirstByMunkavallalo_IdOrderByErvenyessegKezdeteDesc(
                        person.munkavallaloId
                    );
                    if (berekOpt.isPresent()) {
                        Berek ber = berekOpt.orElseThrow();
                        teljesKoltseg = ber.getTeljesKoltseg() != null ? ber.getTeljesKoltseg() : BigDecimal.ZERO;
                    }
                }

                Row row = sheet.createRow(rowIdx++);
                int excelRow = row.getRowNum() + 1; // 1-based Excel row number
                createCell(row, COL_NEV, person.nev, styles.dataStyle);
                createNumCell(row, COL_TELJES, teljesKoltseg, styles.dataNumStyle);
                createNumCell(row, COL_NAPOK, BigDecimal.valueOf(ledolgozottNapok), styles.dataGreenNumStyle);
                // Napidíj = Teljes költség / Ledolgozott napok (formula)
                createFormulaCell(row, COL_NAPDIJ, "IFERROR(B" + excelRow + "/C" + excelRow + ",0)", styles.dataNumStyle);
                createNumCell(row, COL_KORIG, KORIGALT_NAPDIJ, styles.dataGreenNumStyle);
                // Számlázott összeg = Korrigált napidíj * Ledolgozott napok (formula)
                createFormulaCell(row, COL_SZAMLA, "E" + excelRow + "*C" + excelRow, styles.dataNumStyle);

                // Accumulate server-side totals for persistence
                workerTeljesKoltsegSum = workerTeljesKoltsegSum.add(teljesKoltseg);
                szamlazottTotal = szamlazottTotal.add(KORIGALT_NAPDIJ.multiply(BigDecimal.valueOf(ledolgozottNapok)));
            }

            int workerEndRowIdx = rowIdx - 1; // 0-based index of last worker row

            // ---- Total számlázott összeg row ----
            Row totalRow = sheet.createRow(rowIdx++);
            for (int c = COL_NEV; c <= COL_NAPDIJ; c++) {
                createCell(totalRow, c, "", styles.dataStyle);
            }
            createCell(totalRow, COL_KORIG, "", styles.dataStyle);
            createFormulaCell(
                totalRow,
                COL_SZAMLA,
                "SUM(F" + (workerStartRowIdx + 1) + ":F" + (workerEndRowIdx + 1) + ")",
                styles.boldNumStyle
            );

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

            // Grand total B = worker Teljes költség + overhead
            BigDecimal grandTotal = workerTeljesKoltsegSum.add(egyebTotal);

            // ---- Grand total row ----
            // SUM of B column from first worker row to the row just above the grand total
            int grandTotalPrevRowIdx = rowIdx - 1;
            Row grandTotalRow = sheet.createRow(rowIdx++);
            createCell(grandTotalRow, COL_NEV, "", styles.dataStyle);
            createFormulaCell(
                grandTotalRow,
                COL_TELJES,
                "SUM(B" + (workerStartRowIdx + 1) + ":B" + (grandTotalPrevRowIdx + 1) + ")",
                styles.boldNumStyle
            );
            for (int c = COL_NAPOK; c <= COL_SZAMLA; c++) {
                createCell(grandTotalRow, c, "", styles.dataStyle);
            }

            LOG.debug("Excel generated: {} person(s), {} overhead items", persons.size(), egyebKoltsegek.size());

            // Evaluate all formulas server-side so cached values are stored in the file
            // (required for server-side re-reading and document generation)
            XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);

            // Persist the calculated totals to the entity so template placeholders can use them
            kozgyules.setElszamolasGrandTotal(grandTotal);
            kozgyules.setElszamolasNapidijakOsszesen(szamlazottTotal);
            kozgyulesekRepository.save(kozgyules);

            return out.toByteArray();
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Merges munkavallalok and tulajdonosok into a deduplicated list of PersonRow. */
    private List<PersonRow> buildPersonRows(List<Munkavallalok> munkavallalok, List<SajatCegTulajdonosok> tulajdonosok) {
        List<PersonRow> result = new ArrayList<>();
        Set<Long> seenMaganszemelyIds = new LinkedHashSet<>();

        for (Munkavallalok mv : munkavallalok) {
            String nev = (mv.getMaganszemely() != null && mv.getMaganszemely().getMaganszemelyNeve() != null)
                ? mv.getMaganszemely().getMaganszemelyNeve()
                : "Munkavállaló #" + mv.getId();
            Long maganszemelyId = mv.getMaganszemely() != null ? mv.getMaganszemely().getId() : null;
            result.add(new PersonRow(nev, mv.getId()));
            if (maganszemelyId != null) seenMaganszemelyIds.add(maganszemelyId);
        }

        for (SajatCegTulajdonosok t : tulajdonosok) {
            if (t.getMaganszemely() == null) continue;
            Long maganszemelyId = t.getMaganszemely().getId();
            if (seenMaganszemelyIds.contains(maganszemelyId)) continue; // already included as worker
            String nev = t.getMaganszemely().getMaganszemelyNeve() != null
                ? t.getMaganszemely().getMaganszemelyNeve()
                : "Tulajdonos #" + t.getId();
            result.add(new PersonRow(nev, null)); // pure owner, no munkavallalo
            seenMaganszemelyIds.add(maganszemelyId);
        }

        return result;
    }

    private static final class PersonRow {

        final String nev;
        final Long munkavallaloId; // null if owner-only

        PersonRow(String nev, Long munkavallaloId) {
            this.nev = nev;
            this.munkavallaloId = munkavallaloId;
        }
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

    private void createFormulaCell(Row row, int col, String formula, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellFormula(formula);
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

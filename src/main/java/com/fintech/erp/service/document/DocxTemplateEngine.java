package com.fintech.erp.service.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility component for filling DOCX templates and converting them to byte arrays.
 */
@Component
public class DocxTemplateEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DocxTemplateEngine.class);
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.*?)}}", Pattern.MULTILINE);
    private static final String DOTS = ".........................................";

    /** Represents one signer entry in the auto-generated signature table. */
    public static final class SignaturePerson {
        public final String label;  // displayed name or company name
        public final String szerep; // e.g. "Ügyvezetô" or "tag"
        public SignaturePerson(String label, String szerep) {
            this.label = label;
            this.szerep = szerep;
        }
    }

    public byte[] populateTemplate(Path templatePath, Map<String, String> placeholders) throws IOException {
        try (InputStream inputStream = Files.newInputStream(templatePath)) {
            return populateTemplate(inputStream, placeholders);
        }
    }

    public byte[] populateTemplate(InputStream templateStream, Map<String, String> placeholders) throws IOException {
        try (XWPFDocument document = new XWPFDocument(templateStream)) {
            replaceInDocument(document, placeholders);
            logRemainingPlaceholders(document);
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                document.write(output);
                return output.toByteArray();
            }
        }
    }

    public byte[] replacePlaceholdersInGeneratedDoc(byte[] docxBytes, Map<String, String> placeholders) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(docxBytes); XWPFDocument document = new XWPFDocument(input)) {
            replaceInDocument(document, placeholders);
            logRemainingPlaceholders(document);
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                document.write(output);
                return output.toByteArray();
            }
        }
    }

    public byte[] convertToPdf(byte[] docxBytes) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(docxBytes); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(input);
            Docx4J.toPDF(wordMLPackage, output);
            return output.toByteArray();
        } catch (Exception e) {
            throw new IOException("DOCX konvertálása PDF formátumba sikertelen", e);
        }
    }

    /**
     * Finds the paragraph containing {@code {{placeholderKey}}} and replaces it with
     * an automatically generated two-column signature table.
     * Each signer gets a block consisting of a dotted line, the word "Aláírás",
     * and the person’s label + role. Signers are laid out two-per-row.
     */
    public byte[] insertSignatureBlock(byte[] docxBytes, String placeholderKey, List<SignaturePerson> signers) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(docxBytes);
             XWPFDocument doc = new XWPFDocument(input)) {

            String marker = "{{" + placeholderKey + "}}";
            // Search both body-level paragraphs and paragraphs nested inside tables
            XWPFParagraph target = findParagraphWithMarker(doc, marker);

            if (target != null) {
                if (signers != null && !signers.isEmpty()) {
                    XmlCursor cursor = target.getCTP().newCursor();
                    XWPFTable table = doc.insertNewTbl(cursor);
                    applySignatureTableStyle(table);
                    while (table.getNumberOfRows() > 0) {
                        table.removeRow(0);
                    }
                    for (int i = 0; i < signers.size(); i += 2) {
                        XWPFTableRow row = table.createRow();
                        // Ensure second cell exists (createRow may produce only 1 cell)
                        while (row.getTableCells().size() < 2) {
                            row.addNewTableCell();
                        }
                        fillSignatureCell(row.getCell(0), signers.get(i));
                        SignaturePerson right = (i + 1 < signers.size()) ? signers.get(i + 1) : null;
                        if (right != null) {
                            fillSignatureCell(row.getCell(1), right);
                        }
                    }
                }
                int targetPos = -1;
                List<IBodyElement> bodyElements = doc.getBodyElements();
                for (int i = 0; i < bodyElements.size(); i++) {
                    if (bodyElements.get(i) == target) {
                        targetPos = i;
                        break;
                    }
                }
                if (targetPos >= 0) doc.removeBodyElement(targetPos);
            } else {
                LOG.warn("insertSignatureBlock: placeholder '{}' not found in document", marker);
            }

            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                doc.write(output);
                return output.toByteArray();
            }
        }
    }

    /** Searches body paragraphs first, then paragraphs inside any top-level table. */
    private XWPFParagraph findParagraphWithMarker(XWPFDocument doc, String marker) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            if (p.getText().contains(marker)) return p;
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        if (p.getText().contains(marker)) return p;
                    }
                }
            }
        }
        return null;
    }

    private void applySignatureTableStyle(XWPFTable table) {
        CTTblPr tblPr = table.getCTTbl().getTblPr() == null
            ? table.getCTTbl().addNewTblPr()
            : table.getCTTbl().getTblPr();
        CTTblWidth w = tblPr.getTblW() == null ? tblPr.addNewTblW() : tblPr.getTblW();
        w.setType(STTblWidth.PCT);
        w.setW(BigInteger.valueOf(5000)); // 100% width
        CTTblBorders b = tblPr.getTblBorders() == null ? tblPr.addNewTblBorders() : tblPr.getTblBorders();
        CTBorder[] borders = {
            b.getTop()     == null ? b.addNewTop()     : b.getTop(),
            b.getBottom()  == null ? b.addNewBottom()  : b.getBottom(),
            b.getLeft()    == null ? b.addNewLeft()    : b.getLeft(),
            b.getRight()   == null ? b.addNewRight()   : b.getRight(),
            b.getInsideH() == null ? b.addNewInsideH() : b.getInsideH(),
            b.getInsideV() == null ? b.addNewInsideV() : b.getInsideV()
        };
        for (CTBorder border : borders) {
            border.setVal(STBorder.NONE);
        }
    }

    private void fillSignatureCell(XWPFTableCell cell, SignaturePerson person) {
        // A freshly-created CTTc may have zero <w:p> children — always add paragraphs safely
        XWPFParagraph p1 = cell.getParagraphs().isEmpty() ? cell.addParagraph() : cell.getParagraphs().get(0);
        // Clear any stray runs that may exist on the first paragraph
        for (int i = p1.getRuns().size() - 1; i >= 0; i--) {
            p1.removeRun(i);
        }
        p1.createRun().setText(DOTS);
        cell.addParagraph().createRun().setText("Aláírás");
        cell.addParagraph().createRun().setText(person.label + ", " + person.szerep);
    }

    private void replaceInDocument(XWPFDocument document, Map<String, String> placeholders) {
        document.getParagraphs().forEach(paragraph -> replaceInParagraph(paragraph, placeholders));
        document.getTables().forEach(table -> replaceInTable(table, placeholders));
        document
            .getHeaderList()
            .forEach(header -> {
                header.getParagraphs().forEach(paragraph -> replaceInParagraph(paragraph, placeholders));
                header.getTables().forEach(table -> replaceInTable(table, placeholders));
            });
        document
            .getFooterList()
            .forEach(footer -> {
                footer.getParagraphs().forEach(paragraph -> replaceInParagraph(paragraph, placeholders));
                footer.getTables().forEach(table -> replaceInTable(table, placeholders));
            });
    }

    private void replaceInTable(XWPFTable table, Map<String, String> placeholders) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                cell.getParagraphs().forEach(paragraph -> replaceInParagraph(paragraph, placeholders));
                cell.getTables().forEach(nestedTable -> replaceInTable(nestedTable, placeholders));
            }
        }
    }

    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> placeholders) {
        var runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) {
            return;
        }

        StringBuilder originalText = new StringBuilder();
        for (XWPFRun run : runs) {
            String text = extractRunText(run);
            if (!text.isEmpty()) {
                originalText.append(text);
            }
        }

        if (originalText.length() == 0) {
            return;
        }

        String original = originalText.toString();
        String replacedText = replacePlaceholders(original, placeholders);
        if (Objects.equals(original, replacedText)) {
            return;
        }

        // keep formatting from the first run and collapse the rest once replacements are done
        XWPFRun firstRun = runs.get(0);
        for (int i = runs.size() - 1; i > 0; i--) {
            paragraph.removeRun(i);
        }

        // If the replacement contains newlines, split into multiple runs with Word line breaks
        if (replacedText.contains("\n")) {
            // Force left alignment to prevent justified stretching of short lines
            paragraph.setAlignment(org.apache.poi.xwpf.usermodel.ParagraphAlignment.LEFT);
            String[] lines = replacedText.split("\n", -1);
            firstRun.setText(lines[0], 0);
            cleanupExtraTextNodes(firstRun);
            for (int i = 1; i < lines.length; i++) {
                firstRun.addCarriageReturn();
                firstRun.setText(lines[i]);
            }
        } else {
            firstRun.setText(replacedText, 0);
            cleanupExtraTextNodes(firstRun);
        }
    }

    private void cleanupExtraTextNodes(XWPFRun run) {
        CTR ctr = run.getCTR();
        for (int i = ctr.sizeOfTArray() - 1; i > 0; i--) {
            ctr.removeT(i);
        }
    }

    private String extractRunText(XWPFRun run) {
        CTR ctr = run.getCTR();
        int segmentCount = ctr.sizeOfTArray();
        if (segmentCount == 0) {
            String single = run.getText(0);
            return single != null ? single : "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < segmentCount; i++) {
            String value = ctr.getTArray(i).getStringValue();
            if (value != null) {
                builder.append(value);
            }
        }
        return builder.toString();
    }

    private String replacePlaceholders(String text, Map<String, String> placeholders) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        StringBuilder builder = new StringBuilder();
        int lastIndex = 0;
        while (matcher.find()) {
            builder.append(text, lastIndex, matcher.start());
            String key = matcher.group(1).trim();
            // If key is not in the map, leave the original {{key}} intact so later
            // steps (e.g. insertSignatureBlock) can still find and process it.
            builder.append(placeholders.containsKey(key) ? placeholders.get(key) : matcher.group(0));
            lastIndex = matcher.end();
        }
        builder.append(text, lastIndex, text.length());
        return builder.toString();
    }

    private void logRemainingPlaceholders(XWPFDocument document) {
        if (!LOG.isDebugEnabled()) {
            return;
        }
        Set<String> unresolved = new LinkedHashSet<>();
        collectPlaceholders(document.getBodyElements(), unresolved);
        document.getHeaderList().forEach(header -> collectPlaceholders(header.getBodyElements(), unresolved));
        document.getFooterList().forEach(footer -> collectPlaceholders(footer.getBodyElements(), unresolved));
        if (!unresolved.isEmpty()) {
            LOG.debug("Unresolved template placeholders detected: {}", unresolved);
        }
    }

    private void collectPlaceholders(List<IBodyElement> elements, Set<String> collector) {
        for (IBodyElement element : elements) {
            switch (element.getElementType()) {
                case PARAGRAPH -> addPlaceholdersFromParagraph((XWPFParagraph) element, collector);
                case TABLE -> collectPlaceholders((XWPFTable) element, collector);
                default -> {}
            }
        }
    }

    private void collectPlaceholders(XWPFTable table, Set<String> collector) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                collectPlaceholders(cell.getBodyElements(), collector);
            }
        }
    }

    private void addPlaceholdersFromParagraph(XWPFParagraph paragraph, Set<String> collector) {
        String paragraphText = paragraph.getText();
        if (paragraphText == null || paragraphText.isBlank()) {
            return;
        }
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(paragraphText);
        while (matcher.find()) {
            collector.add(matcher.group(1).trim());
        }
    }
}

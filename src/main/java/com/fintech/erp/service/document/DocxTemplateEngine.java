package com.fintech.erp.service.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
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
        firstRun.setText(replacedText, 0);
        cleanupExtraTextNodes(firstRun);
        for (int i = runs.size() - 1; i > 0; i--) {
            paragraph.removeRun(i);
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
            builder.append(placeholders.getOrDefault(key, ""));
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

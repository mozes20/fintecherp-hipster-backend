package com.fintech.erp.service.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Component;

/**
 * Utility component for filling DOCX templates and converting them to byte arrays.
 */
@Component
public class DocxTemplateEngine {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.*?)}}", Pattern.MULTILINE);

    public byte[] populateTemplate(Path templatePath, Map<String, String> placeholders) throws IOException {
        try (InputStream inputStream = Files.newInputStream(templatePath)) {
            return populateTemplate(inputStream, placeholders);
        }
    }

    public byte[] populateTemplate(InputStream templateStream, Map<String, String> placeholders) throws IOException {
        try (XWPFDocument document = new XWPFDocument(templateStream)) {
            replaceInDocument(document, placeholders);
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                document.write(output);
                return output.toByteArray();
            }
        }
    }

    public byte[] replacePlaceholdersInGeneratedDoc(byte[] docxBytes, Map<String, String> placeholders) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(docxBytes); XWPFDocument document = new XWPFDocument(input)) {
            replaceInDocument(document, placeholders);
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
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    cell.getParagraphs().forEach(paragraph -> replaceInParagraph(paragraph, placeholders));
                }
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
            String text = run.getText(0);
            if (text != null) {
                originalText.append(text);
            }
        }

        if (originalText.length() == 0) {
            return;
        }

        String replacedText = replacePlaceholders(originalText.toString(), placeholders);
        if (Objects.equals(originalText.toString(), replacedText)) {
            return;
        }

        // keep formatting from the first run and collapse the rest once replacements are done
        XWPFRun firstRun = runs.get(0);
        firstRun.setText(replacedText, 0);
        for (int i = runs.size() - 1; i > 0; i--) {
            paragraph.removeRun(i);
        }
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
}

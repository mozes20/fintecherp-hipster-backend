package com.fintech.erp.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

class DocxTemplateEngineTest {

    private final DocxTemplateEngine templateEngine = new DocxTemplateEngine();

    @Test
    void convertToPdfProducesNonEmptyDocument() throws Exception {
        try (XWPFDocument doc = new XWPFDocument(); ByteArrayOutputStream docOut = new ByteArrayOutputStream()) {
            doc.createParagraph().createRun().setText("Teszt dokumentum");
            doc.write(docOut);

            byte[] pdfBytes = templateEngine.convertToPdf(docOut.toByteArray());

            assertThat(pdfBytes).isNotNull();
            assertThat(pdfBytes.length).isGreaterThan(0);
        }
    }

    @Test
    void populateTemplateReplacesPlaceholders() throws Exception {
        try (XWPFDocument doc = new XWPFDocument(); ByteArrayOutputStream docOut = new ByteArrayOutputStream()) {
            doc.createParagraph().createRun().setText("Hello {{name}}!");
            doc.write(docOut);

            byte[] populated = templateEngine.replacePlaceholdersInGeneratedDoc(
                docOut.toByteArray(),
                Collections.singletonMap("name", "Vilag")
            );

            try (XWPFDocument populatedDoc = new XWPFDocument(new java.io.ByteArrayInputStream(populated))) {
                String text = populatedDoc.getParagraphArray(0).getText();
                assertThat(text).isEqualTo("Hello Vilag!");
            }
        }
    }

    @Test
    void populateTemplateReplacesPlaceholdersAcrossRuns() throws Exception {
        try (XWPFDocument doc = new XWPFDocument(); ByteArrayOutputStream docOut = new ByteArrayOutputStream()) {
            var paragraph = doc.createParagraph();
            paragraph.createRun().setText("Name: ");
            paragraph.createRun().setText("{");
            paragraph.createRun().setText("{field-001}");
            paragraph.createRun().setText("}");
            doc.write(docOut);

            byte[] populated = templateEngine.replacePlaceholdersInGeneratedDoc(
                docOut.toByteArray(),
                Collections.singletonMap("field-001", "Teszt")
            );

            try (XWPFDocument populatedDoc = new XWPFDocument(new java.io.ByteArrayInputStream(populated))) {
                String text = populatedDoc.getParagraphArray(0).getText();
                assertThat(text).isEqualTo("Name: Teszt");
            }
        }
    }
}

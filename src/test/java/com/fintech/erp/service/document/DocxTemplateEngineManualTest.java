package com.fintech.erp.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DocxTemplateEngineManualTest {

    @Test
    void debugPopulateTemplate() throws IOException {
        Path templatePath = Path.of("uploads", "templates", "megrendelesek", "template_20251106001118327.docx");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("megrendelo_ceg.nev", "Teszt Cég Zrt.");
        placeholders.put("megrendelo_ceg.szekhely", "1234 Budapest, Teszt utca 1.");
        placeholders.put("megrendelo_ceg.adoszam", "12345678-1-12");
        placeholders.put("megrendelo_ceg.cegjegyzekszam", "01-09-999999");
        placeholders.put("megrendelo_ceg.bankszamlaszam", "11111111-22222222-33333333");
        placeholders.put("megrendeles.szama", "T-2025-001");
        placeholders.put("maganszemely.nev", "Teszt Elek");
        placeholders.put("megrendeles.munkakor_nev", "Java fejlesztő");
        placeholders.put("megrendeles.munkakor_feladatai", "Kódolás, tesztelés, dokumentálás");

        DocxTemplateEngine engine = new DocxTemplateEngine();
        byte[] result;
        try (InputStream inputStream = Files.newInputStream(templatePath)) {
            result = engine.populateTemplate(inputStream, placeholders);
        }

        Path outputPath = Path.of("target", "debug-populated.docx");
        Files.createDirectories(outputPath.getParent());
        Files.write(outputPath, result);

        assertThat(result).isNotEmpty();

        try (var populated = new org.apache.poi.xwpf.usermodel.XWPFDocument(new java.io.ByteArrayInputStream(result))) {
            String documentText = populated
                .getParagraphs()
                .stream()
                .map(org.apache.poi.xwpf.usermodel.XWPFParagraph::getText)
                .filter(java.util.Objects::nonNull)
                .collect(java.util.stream.Collectors.joining("\n"));
            assertThat(documentText)
                .contains("Teszt Cég Zrt.")
                .contains("1234 Budapest, Teszt utca 1.")
                .contains("12345678-1-12")
                .doesNotContain("{{ megrendelo_ceg.nev }}")
                .doesNotContain("{{ megrendelo_ceg.szekhely }}")
                .doesNotContain("{{ megrendelo_ceg.adoszam }}");
        }
    }
}

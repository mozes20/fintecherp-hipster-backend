package com.fintech.erp.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DocxTemplateEngineUnitTest {

    @Test
    void replacePlaceholdersHandlesSpacesInsideDelimiters() throws Exception {
        DocxTemplateEngine engine = new DocxTemplateEngine();
        Method replace = DocxTemplateEngine.class.getDeclaredMethod("replacePlaceholders", String.class, Map.class);
        replace.setAccessible(true);
        String input = "Sz\u00E9khely: {{ megrendelo_ceg.szekhely }}";
        String output = (String) replace.invoke(engine, input, Map.of("megrendelo_ceg.szekhely", "1234 Budapest"));
        assertThat(output).isEqualTo("Sz\u00E9khely: 1234 Budapest");
    }
}

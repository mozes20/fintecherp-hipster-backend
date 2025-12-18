package com.fintech.erp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fintech.erp.config.JacksonConfiguration;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.document.DocxTemplateEngine;
import com.fintech.erp.service.document.EfoTemplatePlaceholderService;
import com.fintech.erp.service.document.PdfConversionService;
import com.fintech.erp.service.dto.EfoCsvImportPreviewDTO;
import java.nio.charset.Charset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EfoCsvImportServiceTest {

    private static final Charset WINDOWS_1252 = Charset.forName("windows-1252");

    private MunkavallalokRepository munkavallalokRepository;
    private MunkakorokRepository munkakorokRepository;
    private EfoFoglalkoztatasokService efoFoglalkoztatasokService;
    private EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;
    private DocxTemplateEngine docxTemplateEngine;
    private PdfConversionService pdfConversionService;
    private EfoTemplatePlaceholderService placeholderService;
    private EfoDokumentumTemplateService efoDokumentumTemplateService;
    private EfoCsvImportService service;

    @BeforeEach
    void setUp() {
        munkavallalokRepository = mock(MunkavallalokRepository.class);
        munkakorokRepository = mock(MunkakorokRepository.class);
        efoFoglalkoztatasokService = mock(EfoFoglalkoztatasokService.class);
        efoFoglalkoztatasokRepository = mock(EfoFoglalkoztatasokRepository.class);
        docxTemplateEngine = mock(DocxTemplateEngine.class);
        pdfConversionService = mock(PdfConversionService.class);
        placeholderService = mock(EfoTemplatePlaceholderService.class);
        efoDokumentumTemplateService = mock(EfoDokumentumTemplateService.class);

        when(munkavallalokRepository.findBySajatCegIdAndMaganszemelyAdoAzonositoJel(anyLong(), anyString())).thenReturn(
            java.util.Optional.empty()
        );

        service = new EfoCsvImportService(
            munkavallalokRepository,
            munkakorokRepository,
            efoFoglalkoztatasokService,
            efoFoglalkoztatasokRepository,
            docxTemplateEngine,
            pdfConversionService,
            placeholderService,
            efoDokumentumTemplateService
        );
    }

    @Test
    void generatePreview_shouldParseHungarianWindowsCsv() throws Exception {
        String csv =
            """
            Munkáltató adószáma;Név;Születési név;Nem;Adóazonosító;TAJ-szám;Állampolgárság;Anyja neve;Születési hely;Születési idő;Foglalkoztatás jele;Hó naptól;Hó napig;Napok száma összesen;Kifizetett bruttó bér
            26100007213;Mikulás Marcell;Mikulás Marcell;1;8502452908;="122242278";magyar;Csónyi Zsuzsanna;Budapest;2004. July 26.;="06";2025. 09. 03.;2025. 09. 03.;1;26000
            26100007213;Mikulás Marcell;Mikulás Marcell;1;8502452908;="122242278";magyar;Csónyi Zsuzsanna;Budapest;2004. July 26.;="06";2025. 09. 10.;2025. 09. 10.;1;26000
            26100007213;Mikulás Marcell;Mikulás Marcell;1;8502452908;="122242278";magyar;Csónyi Zsuzsanna;Budapest;2004. July 26.;="06";2025. 09. 11.;2025. 09. 11.;1;26000
            26100007213;Mikulás Marcell;Mikulás Marcell;1;8502452908;="122242278";magyar;Csónyi Zsuzsanna;Budapest;2004. July 26.;="06";2025. 09. 12.;2025. 09. 12.;1;26000
            26100007213;Mikulás Marcell;Mikulás Marcell;1;8502452908;="122242278";magyar;Csónyi Zsuzsanna;Budapest;2004. July 26.;="06";2025. 09. 15.;2025. 09. 15.;1;26000
            """;

        byte[] bytes = csv.getBytes(WINDOWS_1252);

        EfoCsvImportPreviewDTO preview = service.generatePreview(1851L, bytes);

        assertThat(preview.getRecords()).hasSize(5);
        assertThat(preview.getRecords().get(0).getEmployeeName()).isEqualTo("Mikulás Marcell");
        assertThat(preview.getRecords().get(0).getTaxId()).isEqualTo("8502452908");
        assertThat(preview.getRecords().get(0).getEmploymentDate()).hasToString("2025-09-03");
        assertThat(preview.getRecords().get(0).getEmploymentEndDate()).hasToString("2025-09-03");
        assertThat(preview.getRecords().get(0).getDayCount()).isEqualTo(1);
        assertThat(preview.getRecords().get(0).getAmount()).isNotNull();
        assertThat(preview.getRecords().get(0).getBirthName()).isEqualTo("Mikulás Marcell");
        assertThat(preview.getRecords().get(0).getBirthPlace()).isEqualTo("Budapest");
        assertThat(preview.getRecords().get(0).getBirthDateRaw()).isEqualTo("2004. July 26.");
        if (preview.getRecords().get(0).getBirthDate() != null) {
            assertThat(preview.getRecords().get(0).getBirthDate()).hasToString("2004-07-26");
        }
        assertThat(preview.getRecords().get(0).getMotherName()).isEqualTo("Csónyi Zsuzsanna");

        ObjectMapper mapper = new ObjectMapper();
        JacksonConfiguration jacksonConfiguration = new JacksonConfiguration();
        mapper.registerModule(jacksonConfiguration.javaTimeModule());
        mapper.registerModule(jacksonConfiguration.jdk8TimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String json = mapper.writeValueAsString(preview);
        assertThat(json).contains("\"records\":[");
        assertThat(json).contains("\"employeeName\":\"Mikulás Marcell\"");
    }
}

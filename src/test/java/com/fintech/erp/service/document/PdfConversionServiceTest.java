package com.fintech.erp.service.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PdfConversionServiceTest {

    @Mock
    private DocxTemplateEngine docxTemplateEngine;

    @Mock
    private GotenbergClient gotenbergClient;

    @InjectMocks
    private PdfConversionService pdfConversionService;

    @Test
    void delegatesToGotenbergWhenEnabled() throws Exception {
        byte[] docxBytes = "docx".getBytes();
        byte[] pdfBytes = "pdf".getBytes();

        when(gotenbergClient.isEnabled()).thenReturn(true);
        when(gotenbergClient.convertDocxToPdf(any(byte[].class), any(String.class))).thenReturn(pdfBytes);

        byte[] result = pdfConversionService.convertDocxToPdf(docxBytes, "sample");

        assertThat(result).isSameAs(pdfBytes);
        verifyNoInteractions(docxTemplateEngine);
    }

    @Test
    void fallsBackToDocxTemplateEngineWhenGotenbergFails() throws Exception {
        byte[] docxBytes = "docx".getBytes();
        byte[] pdfBytes = "fallback".getBytes();

        when(gotenbergClient.isEnabled()).thenReturn(true);
        doThrow(new IOException("boom")).when(gotenbergClient).convertDocxToPdf(any(byte[].class), any(String.class));
        when(docxTemplateEngine.convertToPdf(docxBytes)).thenReturn(pdfBytes);

        byte[] result = pdfConversionService.convertDocxToPdf(docxBytes, "sample");

        assertThat(result).isSameAs(pdfBytes);
    }

    @Test
    void validatesInputNotEmpty() {
        assertThatThrownBy(() -> pdfConversionService.convertDocxToPdf(new byte[0], "test"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("must not be empty");
    }
}

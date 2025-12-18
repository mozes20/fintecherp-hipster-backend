package com.fintech.erp.service.document;

import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PdfConversionService {

    private static final Logger LOG = LoggerFactory.getLogger(PdfConversionService.class);

    private final DocxTemplateEngine docxTemplateEngine;
    private final GotenbergClient gotenbergClient;

    public PdfConversionService(DocxTemplateEngine docxTemplateEngine, GotenbergClient gotenbergClient) {
        this.docxTemplateEngine = docxTemplateEngine;
        this.gotenbergClient = gotenbergClient;
    }

    public byte[] convertDocxToPdf(byte[] docxBytes, String fileNameHint) throws IOException {
        Objects.requireNonNull(docxBytes, "docxBytes must not be null");
        if (docxBytes.length == 0) {
            throw new IllegalArgumentException("docxBytes must not be empty");
        }

        String safeFileName = (fileNameHint != null && !fileNameHint.isBlank()) ? fileNameHint : "document";
        if (!safeFileName.endsWith(".docx")) {
            safeFileName = safeFileName + ".docx";
        }

        if (gotenbergClient != null && gotenbergClient.isEnabled()) {
            try {
                LOG.debug("Attempting Gotenberg conversion for {}", safeFileName);
                return gotenbergClient.convertDocxToPdf(docxBytes, safeFileName);
            } catch (Exception ex) {
                LOG.warn("Gotenberg conversion failed for {}. Falling back to Docx4J: {}", safeFileName, ex.getMessage());
                LOG.debug("Gotenberg conversion error", ex);
            }
        }

        try {
            LOG.debug("Attempting Docx4J conversion for {}", safeFileName);
            return docxTemplateEngine.convertToPdf(docxBytes);
        } catch (IOException e) {
            // Check if it's a layout/FOP error
            Throwable rootCause = getRootCause(e);
            if (rootCause instanceof IllegalStateException) {
                throw new IOException(
                    "PDF konvertálás sikertelen a dokumentum összetett elrendezése miatt (FOP layout hiba). " +
                    "Javaslat: használjon egyszerűbb sablont vagy engedélyezze a Gotenberg szolgáltatást.",
                    e
                );
            }
            throw e;
        }
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }
}

package com.fintech.erp.web.rest;

import com.fintech.erp.service.document.DocumentFormat;
import com.fintech.erp.service.document.DocumentGenerationService;
import com.fintech.erp.service.document.GeneratedDocumentResult;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import com.fintech.erp.web.rest.vm.DocumentGenerationRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/szerzodeses-jogviszony-dokumentumoks")
public class DocumentGenerationResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentGenerationResource.class);

    private static final String ENTITY_NAME = "szerzodesesJogviszonyDokumentum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentGenerationService documentGenerationService;

    public DocumentGenerationResource(DocumentGenerationService documentGenerationService) {
        this.documentGenerationService = documentGenerationService;
    }

    @PostMapping("/{jogviszonyId}/generate")
    public ResponseEntity<Resource> generateDocument(
        @PathVariable("jogviszonyId") Long jogviszonyId,
        @Valid @RequestBody DocumentGenerationRequest request
    ) throws IOException {
        LOG.debug("REST request to generate document for jogviszony {} using template {}", jogviszonyId, request.getTemplateId());
        try {
            GeneratedDocumentResult result = documentGenerationService.generateFromTemplate(
                request.getTemplateId(),
                jogviszonyId,
                request.getPlaceholders(),
                Optional.ofNullable(request.getFormat()).orElse(DocumentFormat.DOCX),
                request.isPersist(),
                request.getDokumentumNev(),
                request.getDokumentumTipusId()
            );
            ByteArrayResource resource = new ByteArrayResource(result.getData());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(result.getContentType()));
            headers.setContentLength(result.getData().length);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + result.getFileName());
            if (result.getPersistedDocument() != null && result.getPersistedDocument().getId() != null) {
                headers.add("X-Generated-Document-Id", result.getPersistedDocument().getId().toString());
                headers.addAll(
                    HeaderUtil.createEntityCreationAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        result.getPersistedDocument().getId().toString()
                    )
                );
            }
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "validationerror");
        }
    }
}

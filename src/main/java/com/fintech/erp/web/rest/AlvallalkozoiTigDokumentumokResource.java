package com.fintech.erp.web.rest;

import com.fintech.erp.domain.AlvallalkozoiTigDokumentumok;
import com.fintech.erp.repository.AlvallalkozoiElszamolasokRepository;
import com.fintech.erp.repository.AlvallalkozoiTigDokumentumokRepository;
import com.fintech.erp.service.AlvallalkozoiTigDokumentumokService;
import com.fintech.erp.service.document.AlvallalkozoiTigDocumentGenerationService;
import com.fintech.erp.service.document.GeneratedDocumentResult;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
import com.fintech.erp.service.dto.AlvallalkozoiTigDokumentumokDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fintech.erp.domain.AlvallalkozoiTigDokumentumok}.
 */
@RestController
@RequestMapping("/api/alvallalkozoi-tig-dokumentumoks")
public class AlvallalkozoiTigDokumentumokResource {

    private static final Logger LOG = LoggerFactory.getLogger(AlvallalkozoiTigDokumentumokResource.class);
    private static final String ENTITY_NAME = "alvallalkozoiTigDokumentumok";
    private static final String UPLOAD_DIR = "uploads/alvallalkozoi-tig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlvallalkozoiTigDokumentumokService service;
    private final AlvallalkozoiTigDokumentumokRepository repository;
    private final AlvallalkozoiElszamolasokRepository elszamolasokRepository;
    private final AlvallalkozoiTigDocumentGenerationService documentGenerationService;

    public AlvallalkozoiTigDokumentumokResource(
        AlvallalkozoiTigDokumentumokService service,
        AlvallalkozoiTigDokumentumokRepository repository,
        AlvallalkozoiElszamolasokRepository elszamolasokRepository,
        AlvallalkozoiTigDocumentGenerationService documentGenerationService
    ) {
        this.service = service;
        this.repository = repository;
        this.elszamolasokRepository = elszamolasokRepository;
        this.documentGenerationService = documentGenerationService;
    }

    /**
     * POST /dokumentumok/upload : Upload a document file and persist as AlvallalkozoiTigDokumentumok.
     */
    @PostMapping(value = "/dokumentumok/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlvallalkozoiTigDokumentumokDTO> uploadDokumentum(
        @RequestParam("file") MultipartFile file,
        @RequestParam("elszamolasId") Long elszamolasId,
        @RequestParam("dokumentumTipusa") String dokumentumTipusa
    ) throws IOException, URISyntaxException {
        LOG.debug("REST request to upload AlvallalkozoiTig document for elszamolas {}", elszamolasId);

        if (!elszamolasokRepository.existsById(elszamolasId)) {
            throw new BadRequestAlertException("Elszámolás nem található", ENTITY_NAME, "elszamolasnotfound");
        }

        Path uploadPath = Path.of(UPLOAD_DIR);
        Files.createDirectories(uploadPath);

        String originalFilename = file.getOriginalFilename();
        String ext = (originalFilename != null && originalFilename.contains("."))
            ? originalFilename.substring(originalFilename.lastIndexOf('.'))
            : "";
        String storedFilename = "alvtig_" + Instant.now().toEpochMilli() + ext;
        Path targetPath = uploadPath.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        AlvallalkozoiTigDokumentumokDTO dto = new AlvallalkozoiTigDokumentumokDTO();
        dto.setDokumentumTipusa(dokumentumTipusa);
        dto.setDokumentum(UPLOAD_DIR + "/" + storedFilename);
        AlvallalkozoiElszamolasokDTO elszRef = new AlvallalkozoiElszamolasokDTO();
        elszRef.setId(elszamolasId);
        dto.setElszamolas(elszRef);

        dto = service.save(dto);
        return ResponseEntity.created(new URI("/api/alvallalkozoi-tig-dokumentumoks/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    /**
     * GET /{id}/download : Download the file for the given document.
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDokumentum(@PathVariable("id") Long id) {
        LOG.debug("REST request to download AlvallalkozoiTig document {}", id);
        AlvallalkozoiTigDokumentumok dok = repository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Dokumentum nem található", ENTITY_NAME, "notfound"));

        String dokumentumPath = dok.getDokumentum();
        if (dokumentumPath == null || dokumentumPath.isBlank()) {
            throw new BadRequestAlertException("A dokumentumhoz nem tartozik fájl", ENTITY_NAME, "nofile");
        }
        Path filePath = Path.of(dokumentumPath).normalize();
        if (!Files.exists(filePath)) {
            throw new BadRequestAlertException("A dokumentum fájl nem található a szerveren", ENTITY_NAME, "filenotfound");
        }
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName() + "\"")
            .body(resource);
    }

    /**
     * GET "" : list documents with optional criteria (elszamolasId filter supported via query param).
     */
    @GetMapping("")
    public ResponseEntity<List<AlvallalkozoiTigDokumentumokDTO>> getAll(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get all AlvallalkozoiTigDokumentumoks");
        Page<AlvallalkozoiTigDokumentumok> page = repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        List<AlvallalkozoiTigDokumentumokDTO> result = page.getContent().stream()
            .map(d -> {
                AlvallalkozoiTigDokumentumokDTO dto = new AlvallalkozoiTigDokumentumokDTO();
                dto.setId(d.getId());
                dto.setDokumentumTipusa(d.getDokumentumTipusa());
                dto.setDokumentum(d.getDokumentum());
                if (d.getElszamolas() != null) {
                    AlvallalkozoiElszamolasokDTO ref = new AlvallalkozoiElszamolasokDTO();
                    ref.setId(d.getElszamolas().getId());
                    dto.setElszamolas(ref);
                }
                return dto;
            })
            .toList();
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * GET /{id} : get one document by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlvallalkozoiTigDokumentumokDTO> getOne(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AlvallalkozoiTigDokumentumok : {}", id);
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    /**
     * DELETE /{id} : delete the document and its file.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AlvallalkozoiTigDokumentumok : {}", id);
        repository.findById(id).ifPresent(dok -> {
            if (dok.getDokumentum() != null) {
                try {
                    Files.deleteIfExists(Path.of(dok.getDokumentum()).normalize());
                } catch (IOException e) {
                    LOG.warn("Could not delete file for document {}: {}", id, e.getMessage());
                }
            }
        });
        service.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * POST /template/upload : Upload a TIG Word template (stores in uploads/templates/alvallalkozoi-tig/).
     */
    @PostMapping(value = "/template/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile file) throws IOException {
        LOG.debug("REST request to upload AlvallalkozoiTig template");
        Path templateDir = Path.of("uploads", "templates", "alvallalkozoi-tig");
        Files.createDirectories(templateDir);
        String ext = Optional.ofNullable(file.getOriginalFilename())
            .filter(n -> n.contains("."))
            .map(n -> n.substring(n.lastIndexOf('.')))
            .orElse(".docx");
        String filename = "template_" + Instant.now().toEpochMilli() + ext;
        Files.copy(file.getInputStream(), templateDir.resolve(filename).normalize(), StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok().body(filename);
    }

    /**
     * POST /{elszamolasId}/generate-tig : Generate TIG Word document from latest template.
     */
    @PostMapping("/{elszamolasId}/generate-tig")
    public ResponseEntity<org.springframework.core.io.Resource> generateTig(
        @PathVariable("elszamolasId") Long elszamolasId,
        @RequestParam(value = "persist", defaultValue = "true") boolean persist
    ) throws IOException {
        LOG.debug("REST request to generate TIG for elszamolas {}", elszamolasId);
        GeneratedDocumentResult<AlvallalkozoiTigDokumentumokDTO> result = documentGenerationService.generateTig(elszamolasId, persist);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getFileName() + "\"")
            .body(new org.springframework.core.io.ByteArrayResource(result.getData()));
    }
}

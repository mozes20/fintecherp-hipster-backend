package com.fintech.erp.web.rest;

import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.domain.Munkavallalok;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.repository.MunkavallalokRepository;
import com.fintech.erp.service.EfoDokumentumTemplateService;
import com.fintech.erp.service.document.EfoTemplatePlaceholderService;
import com.fintech.erp.service.dto.EfoDokumentumTemplateDTO;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import com.fintech.erp.web.rest.vm.TemplatePlaceholderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing EFO document templates.
 */
@RestController
@RequestMapping("/api/efo-dokumentum-templatek")
public class EfoDokumentumTemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(EfoDokumentumTemplateResource.class);

    private static final String ENTITY_NAME = "efoDokumentumTemplate";
    private static final String DEFAULT_DOKUMENTUM_TIPUS = "EFO_MUNKASZERZODES";
    private static final String DEFAULT_SIGNED_DOKUMENTUM_TIPUS = "EFO_MUNKASZERZODES_ALAIRT";
    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EfoDokumentumTemplateService templateService;
    private final EfoTemplatePlaceholderService placeholderService;
    private final MunkavallalokRepository munkavallalokRepository;
    private final MunkakorokRepository munkakorokRepository;

    public EfoDokumentumTemplateResource(
        EfoDokumentumTemplateService templateService,
        EfoTemplatePlaceholderService placeholderService,
        MunkavallalokRepository munkavallalokRepository,
        MunkakorokRepository munkakorokRepository
    ) {
        this.templateService = templateService;
        this.placeholderService = placeholderService;
        this.munkavallalokRepository = munkavallalokRepository;
        this.munkakorokRepository = munkakorokRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<EfoDokumentumTemplateDTO> uploadTemplate(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "dokumentumTipus", required = false) String dokumentumTipus,
        @RequestParam("templateNev") String templateNev,
        @RequestParam(value = "templateLeiras", required = false) String templateLeiras
    ) throws IOException {
        LOG.debug("REST request to upload EFO template file: {}", file.getOriginalFilename());
        validateDocx(file);
        String effectiveTipus = hasText(dokumentumTipus) ? dokumentumTipus.trim() : DEFAULT_DOKUMENTUM_TIPUS;
        Path uploadDir = getTemplateUploadDir();
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "template_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        EfoDokumentumTemplateDTO dto = new EfoDokumentumTemplateDTO();
        dto.setTemplateNev(templateNev);
        dto.setTemplateLeiras(templateLeiras);
        dto.setDokumentumTipus(effectiveTipus);
        dto.setFajlUtvonal(uploadDir.relativize(target).toString());
        dto.setUtolsoModositas(java.time.Instant.now());

        dto = templateService.save(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/upload-signed")
    public ResponseEntity<EfoDokumentumTemplateDTO> uploadSignedDocument(
        @RequestParam("file") MultipartFile file,
        @RequestParam("templateNev") String templateNev,
        @RequestParam(value = "templateLeiras", required = false) String templateLeiras,
        @RequestParam(value = "dokumentumTipus", required = false) String dokumentumTipus
    ) throws IOException {
        LOG.debug("REST request to upload signed EFO document: {}", file != null ? file.getOriginalFilename() : "<no file>");
        validateSignedDocument(file);
        String effectiveTipus = hasText(dokumentumTipus) ? dokumentumTipus.trim() : DEFAULT_SIGNED_DOKUMENTUM_TIPUS;
        Path uploadDir = getTemplateUploadDir();
        Files.createDirectories(uploadDir);
        String extension = getExtension(file.getOriginalFilename());
        String storedFilename = "signed_" + FILE_TS.format(LocalDateTime.now()) + extension;
        Path target = uploadDir.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        EfoDokumentumTemplateDTO dto = new EfoDokumentumTemplateDTO();
        dto.setTemplateNev(templateNev);
        dto.setTemplateLeiras(templateLeiras);
        dto.setDokumentumTipus(effectiveTipus);
        dto.setFajlUtvonal(uploadDir.relativize(target).toString());
        dto.setUtolsoModositas(java.time.Instant.now());

        dto = templateService.save(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to download EFO template : {}", id);
        EfoDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template not found", ENTITY_NAME, "idnotfound"));
        Path filePath = resolveTemplatePath(dto.getFajlUtvonal());
        if (!Files.exists(filePath)) {
            throw new BadRequestAlertException("A sablon fájl nem található", ENTITY_NAME, "filenotfound");
        }
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.getFileName())
            .body(resource);
    }

    @PostMapping("")
    public ResponseEntity<EfoDokumentumTemplateDTO> createEfoDokumentumTemplate(@Valid @RequestBody EfoDokumentumTemplateDTO dto)
        throws URISyntaxException {
        LOG.debug("REST request to save EfoDokumentumTemplate : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new efoDokumentumTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!hasText(dto.getDokumentumTipus())) {
            dto.setDokumentumTipus(DEFAULT_DOKUMENTUM_TIPUS);
        }
        dto = templateService.save(dto);
        return ResponseEntity.created(new URI("/api/efo-dokumentum-templatek/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EfoDokumentumTemplateDTO> updateEfoDokumentumTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EfoDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to update EfoDokumentumTemplate : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        dto = templateService.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EfoDokumentumTemplateDTO> partialUpdateEfoDokumentumTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EfoDokumentumTemplateDTO dto
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EfoDokumentumTemplate : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<EfoDokumentumTemplateDTO> result = templateService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<EfoDokumentumTemplateDTO>> getAllEfoDokumentumTemplates(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of EfoDokumentumTemplates");
        Page<EfoDokumentumTemplateDTO> page = templateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EfoDokumentumTemplateDTO> getEfoDokumentumTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EfoDokumentumTemplate : {}", id);
        Optional<EfoDokumentumTemplateDTO> dto = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEfoDokumentumTemplate(@PathVariable("id") Long id) throws IOException {
        LOG.debug("REST request to delete EfoDokumentumTemplate : {}", id);
        EfoDokumentumTemplateDTO dto = templateService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Template not found", ENTITY_NAME, "idnotfound"));
        templateService.delete(id);
        Path filePath = resolveTemplatePath(dto.getFajlUtvonal());
        Files.deleteIfExists(filePath);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Path getTemplateUploadDir() {
        return Path.of("uploads", "templates", "efo");
    }

    private Path resolveTemplatePath(String relativePath) {
        Path uploadDir = getTemplateUploadDir();
        return uploadDir.resolve(relativePath != null ? relativePath : "").normalize();
    }

    private void validateDocx(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestAlertException("A feltöltött fájl üres", ENTITY_NAME, "emptyfile");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".docx")) {
            throw new BadRequestAlertException("Csak DOCX fájl tölthető fel", ENTITY_NAME, "invalidextension");
        }
    }

    private void validateSignedDocument(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestAlertException("A feltöltött fájl üres", ENTITY_NAME, "emptyfile");
        }
        String filename = file.getOriginalFilename();
        if (!hasText(filename)) {
            throw new BadRequestAlertException("A fájlnév kötelező", ENTITY_NAME, "filenameMissing");
        }
        String lower = filename.toLowerCase();
        if (!(lower.endsWith(".pdf") || lower.endsWith(".docx"))) {
            throw new BadRequestAlertException("Csak PDF vagy DOCX fájl tölthető fel", ENTITY_NAME, "invalidextension");
        }
    }

    private String getExtension(String filename) {
        if (!hasText(filename) || !filename.contains(".")) {
            return ".docx";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @GetMapping("/placeholders")
    public ResponseEntity<TemplatePlaceholderResponse> getTemplatePlaceholders(
        @RequestParam(value = "sajatCegId", required = false) Long sajatCegId,
        @RequestParam(value = "munkavallaloId", required = false) Long munkavallaloId,
        @RequestParam(value = "munkakorId", required = false) Long munkakorId,
        @RequestParam(value = "datum", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum,
        @RequestParam(value = "datumVege", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumVege,
        @RequestParam(value = "osszeg", required = false) BigDecimal osszeg,
        @RequestParam(value = "napokSzama", required = false) Integer napokSzama
    ) {
        LOG.debug(
            "REST request to get EFO template placeholders (sajatCegId={}, munkavallaloId={}, munkakorId={})",
            sajatCegId,
            munkavallaloId,
            munkakorId
        );

        Munkavallalok munkavallalo = munkavallaloId == null
            ? null
            : munkavallalokRepository
                .findByIdWithRelations(munkavallaloId)
                .orElseThrow(() -> new BadRequestAlertException("A megadott munkavállaló nem található", ENTITY_NAME, "workernotfound"));

        Munkakorok munkakor = munkakorId == null
            ? null
            : munkakorokRepository
                .findById(munkakorId)
                .orElseThrow(() -> new BadRequestAlertException("A megadott munkakör nem található", ENTITY_NAME, "jobnotfound"));

        Long effectiveCegId = sajatCegId;
        if (effectiveCegId == null && munkavallalo != null && munkavallalo.getSajatCeg() != null) {
            effectiveCegId = munkavallalo.getSajatCeg().getId();
        }

        TemplatePlaceholderResponse response = new TemplatePlaceholderResponse()
            .withEntityId(munkavallaloId)
            .withValues(
                placeholderService.build(effectiveCegId, munkavallalo, munkakor, datum, datumVege, osszeg, osszeg, napokSzama, null)
            )
            .withDefinitions(placeholderService.getDefinitions());

        return ResponseEntity.ok(response);
    }
}

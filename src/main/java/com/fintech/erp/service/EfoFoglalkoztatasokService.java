package com.fintech.erp.service;

import com.fintech.erp.domain.EfoFoglalkoztatasok;
import com.fintech.erp.repository.EfoFoglalkoztatasokRepository;
import com.fintech.erp.service.dto.EfoFoglalkoztatasokDTO;
import com.fintech.erp.service.mapper.EfoFoglalkoztatasokMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.EfoFoglalkoztatasok}.
 */
@Service
@Transactional
public class EfoFoglalkoztatasokService {

    private static final Logger LOG = LoggerFactory.getLogger(EfoFoglalkoztatasokService.class);

    private static final Path SIGNED_DOCUMENT_BASE_DIR = Path.of("uploads", "efo-foglalkoztatasok");
    private static final DateTimeFormatter SIGNED_DOCUMENT_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository;

    private final EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper;

    public EfoFoglalkoztatasokService(
        EfoFoglalkoztatasokRepository efoFoglalkoztatasokRepository,
        EfoFoglalkoztatasokMapper efoFoglalkoztatasokMapper
    ) {
        this.efoFoglalkoztatasokRepository = efoFoglalkoztatasokRepository;
        this.efoFoglalkoztatasokMapper = efoFoglalkoztatasokMapper;
    }

    /**
     * Save a efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the entity to save.
     * @return the persisted entity.
     */
    public EfoFoglalkoztatasokDTO save(EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO) {
        LOG.debug("Request to save EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);
        EfoFoglalkoztatasok entityToSave = efoFoglalkoztatasokMapper.toEntity(efoFoglalkoztatasokDTO);
        EfoFoglalkoztatasok persisted = efoFoglalkoztatasokRepository.save(entityToSave);
        Long savedId = persisted.getId();
        return efoFoglalkoztatasokRepository
            .findById(savedId)
            .map(efoFoglalkoztatasokMapper::toDto)
            .map(this::enrichDocumentUrls)
            .orElseGet(() -> enrichDocumentUrls(efoFoglalkoztatasokMapper.toDto(persisted)));
    }

    /**
     * Update a efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the entity to save.
     * @return the persisted entity.
     */
    public EfoFoglalkoztatasokDTO update(EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO) {
        LOG.debug("Request to update EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);
        EfoFoglalkoztatasok entityToSave = efoFoglalkoztatasokMapper.toEntity(efoFoglalkoztatasokDTO);
        EfoFoglalkoztatasok persisted = efoFoglalkoztatasokRepository.save(entityToSave);
        Long savedId = persisted.getId();
        return efoFoglalkoztatasokRepository
            .findById(savedId)
            .map(efoFoglalkoztatasokMapper::toDto)
            .map(this::enrichDocumentUrls)
            .orElseGet(() -> enrichDocumentUrls(efoFoglalkoztatasokMapper.toDto(persisted)));
    }

    /**
     * Partially update a efoFoglalkoztatasok.
     *
     * @param efoFoglalkoztatasokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EfoFoglalkoztatasokDTO> partialUpdate(EfoFoglalkoztatasokDTO efoFoglalkoztatasokDTO) {
        LOG.debug("Request to partially update EfoFoglalkoztatasok : {}", efoFoglalkoztatasokDTO);

        return efoFoglalkoztatasokRepository
            .findById(efoFoglalkoztatasokDTO.getId())
            .map(existingEfoFoglalkoztatasok -> {
                efoFoglalkoztatasokMapper.partialUpdate(existingEfoFoglalkoztatasok, efoFoglalkoztatasokDTO);

                return existingEfoFoglalkoztatasok;
            })
            .map(efoFoglalkoztatasokRepository::save)
            .flatMap(saved -> efoFoglalkoztatasokRepository.findById(saved.getId()))
            .map(efoFoglalkoztatasokMapper::toDto)
            .map(this::enrichDocumentUrls);
    }

    /**
     * Get one efoFoglalkoztatasok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EfoFoglalkoztatasokDTO> findOne(Long id) {
        LOG.debug("Request to get EfoFoglalkoztatasok : {}", id);
        return efoFoglalkoztatasokRepository.findById(id).map(efoFoglalkoztatasokMapper::toDto).map(this::enrichDocumentUrls);
    }

    /**
     * Delete the efoFoglalkoztatasok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete EfoFoglalkoztatasok : {}", id);
        efoFoglalkoztatasokRepository.deleteById(id);
    }

    public Optional<EfoFoglalkoztatasokDTO> uploadSignedDocument(Long employmentId, MultipartFile file) throws IOException {
        LOG.debug("Request to upload signed document for EfoFoglalkoztatasok : {}", employmentId);
        validateSignedDocument(file);
        if (employmentId == null) {
            throw new IllegalArgumentException("A foglalkoztatás azonosítója kötelező");
        }

        Optional<EfoFoglalkoztatasok> maybeEmployment = efoFoglalkoztatasokRepository.findById(employmentId);
        if (maybeEmployment.isEmpty()) {
            return Optional.empty();
        }

        EfoFoglalkoztatasok employment = maybeEmployment.get();
        Long workerId = employment.getMunkavallalo() != null ? employment.getMunkavallalo().getId() : null;
        LocalDate datum = employment.getDatum();
        if (workerId == null || datum == null) {
            throw new IllegalStateException("A foglalkoztatáshoz nem tartozik munkavállaló vagy dátum");
        }

        String storedFileName = buildSignedFileName(file.getOriginalFilename(), datum);
        Path targetPath = buildStoragePath(workerId, datum, storedFileName);
        Files.createDirectories(targetPath.getParent());
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        employment.setAlairtEfoSzerzodes(Boolean.TRUE);
        employment.setAlairtDokumentumUrl(storedFileName);
        EfoFoglalkoztatasok persisted = efoFoglalkoztatasokRepository.save(employment);

        return efoFoglalkoztatasokRepository
            .findById(persisted.getId())
            .map(efoFoglalkoztatasokMapper::toDto)
            .map(this::enrichDocumentUrls);
    }

    private EfoFoglalkoztatasokDTO enrichDocumentUrls(EfoFoglalkoztatasokDTO dto) {
        if (dto == null) {
            return null;
        }
        if (StringUtils.hasText(dto.getGeneraltDokumentumUrl())) {
            String currentUrl = dto.getGeneraltDokumentumUrl();
            if (!currentUrl.startsWith("http") && !currentUrl.startsWith("/api/")) {
                Long workerId = dto.getMunkavallalo() != null ? dto.getMunkavallalo().getId() : null;
                LocalDate datum = dto.getDatum();
                String fileName = StringUtils.hasText(dto.getGeneraltDokumentumNev())
                    ? dto.getGeneraltDokumentumNev()
                    : extractFileName(currentUrl);
                if (workerId != null && datum != null && StringUtils.hasText(fileName)) {
                    dto.setGeneraltDokumentumUrl(buildDownloadUrl(workerId, datum, fileName));
                }
            }
        }
        if (StringUtils.hasText(dto.getAlairtDokumentumUrl())) {
            String currentSignedUrl = dto.getAlairtDokumentumUrl();
            if (!currentSignedUrl.startsWith("http") && !currentSignedUrl.startsWith("/api/")) {
                Long workerId = dto.getMunkavallalo() != null ? dto.getMunkavallalo().getId() : null;
                LocalDate datum = dto.getDatum();
                String fileName = extractFileName(currentSignedUrl);
                if (workerId != null && datum != null && StringUtils.hasText(fileName)) {
                    dto.setAlairtDokumentumUrl(buildDownloadUrl(workerId, datum, fileName));
                }
            }
        }
        return dto;
    }

    private String buildDownloadUrl(Long workerId, LocalDate datum, String fileName) {
        String encodedFileName = UriUtils.encodePathSegment(fileName, StandardCharsets.UTF_8);
        String relativeUrl = "/api/efo-foglalkoztatasok/generated-documents/" + workerId + "/" + datum + "/" + encodedFileName;
        try {
            return ServletUriComponentsBuilder.fromCurrentContextPath().path(relativeUrl).toUriString();
        } catch (IllegalStateException ex) {
            LOG.debug("Nem sikerült abszolút URL-t képezni a generált dokumentumhoz: {}", ex.getMessage());
            return relativeUrl;
        }
    }

    private String extractFileName(String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        String normalizedPath = path.replace('\\', '/');
        int idx = normalizedPath.lastIndexOf('/');
        if (idx >= 0 && idx < path.length() - 1) {
            return normalizedPath.substring(idx + 1);
        }
        return normalizedPath;
    }

    private void validateSignedDocument(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("A feltöltött fájl üres");
        }
        String filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            throw new IllegalArgumentException("A fájlnév kötelező");
        }
        String lower = filename.toLowerCase(Locale.ROOT);
        if (!(lower.endsWith(".pdf") || lower.endsWith(".docx"))) {
            throw new IllegalArgumentException("Csak PDF vagy DOCX fájl tölthető fel");
        }
    }

    private Path buildStoragePath(Long workerId, LocalDate datum, String fileName) {
        if (workerId == null || datum == null || !StringUtils.hasText(fileName)) {
            throw new IllegalArgumentException("Érvénytelen fájl elérési adatok");
        }
        Path baseDir = SIGNED_DOCUMENT_BASE_DIR.toAbsolutePath().normalize();
        Path candidate = baseDir.resolve(workerId.toString()).resolve(datum.toString()).resolve(fileName).normalize();
        if (!candidate.startsWith(baseDir)) {
            throw new IllegalArgumentException("Érvénytelen fájlnév a tároláshoz");
        }
        return candidate;
    }

    @Transactional(readOnly = true)
    public Optional<Path> resolveSignedDocumentPath(Long employmentId) {
        if (employmentId == null) {
            return Optional.empty();
        }
        return efoFoglalkoztatasokRepository
            .findById(employmentId)
            .flatMap(entity -> {
                Long workerId = entity.getMunkavallalo() != null ? entity.getMunkavallalo().getId() : null;
                LocalDate datum = entity.getDatum();
                String stored = extractFileName(entity.getAlairtDokumentumUrl());
                if (workerId == null || datum == null || !StringUtils.hasText(stored)) {
                    return Optional.empty();
                }
                Path candidate;
                try {
                    candidate = buildStoragePath(workerId, datum, stored).toAbsolutePath().normalize();
                } catch (IllegalArgumentException ex) {
                    return Optional.empty();
                }
                Path baseDir = SIGNED_DOCUMENT_BASE_DIR.toAbsolutePath().normalize();
                if (!candidate.startsWith(baseDir)) {
                    return Optional.empty();
                }
                if (!Files.exists(candidate) || !Files.isRegularFile(candidate)) {
                    return Optional.empty();
                }
                return Optional.of(candidate);
            });
    }

    public Optional<EfoFoglalkoztatasokDTO> deleteSignedDocument(Long employmentId) {
        LOG.debug("Request to delete signed document for EfoFoglalkoztatasok : {}", employmentId);
        if (employmentId == null) {
            throw new IllegalArgumentException("A foglalkoztatás azonosítója kötelező");
        }

        Optional<EfoFoglalkoztatasok> maybeEmployment = efoFoglalkoztatasokRepository.findById(employmentId);
        if (maybeEmployment.isEmpty()) {
            return Optional.empty();
        }

        EfoFoglalkoztatasok employment = maybeEmployment.get();
        Long workerId = employment.getMunkavallalo() != null ? employment.getMunkavallalo().getId() : null;
        LocalDate datum = employment.getDatum();
        String stored = extractFileName(employment.getAlairtDokumentumUrl());
        if (workerId != null && datum != null && StringUtils.hasText(stored)) {
            try {
                Path target = buildStoragePath(workerId, datum, stored);
                Files.deleteIfExists(target);
            } catch (IOException ex) {
                LOG.warn("Nem sikerült törölni az aláírt dokumentumot: {}", ex.getMessage());
            }
        }

        employment.setAlairtEfoSzerzodes(Boolean.FALSE);
        employment.setAlairtDokumentumUrl(null);
        EfoFoglalkoztatasok saved = efoFoglalkoztatasokRepository.save(employment);

        return efoFoglalkoztatasokRepository.findById(saved.getId()).map(efoFoglalkoztatasokMapper::toDto).map(this::enrichDocumentUrls);
    }

    private String buildSignedFileName(String originalFilename, LocalDate datum) {
        String extension = ".pdf";
        String baseName = "alairt_dokumentum";
        if (StringUtils.hasText(originalFilename)) {
            String cleaned = StringUtils.cleanPath(originalFilename);
            int dotIndex = cleaned.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < cleaned.length() - 1) {
                extension = cleaned.substring(dotIndex);
                baseName = cleaned.substring(0, dotIndex);
            } else {
                baseName = cleaned;
            }
        }
        String sanitizedBase = sanitizeFileName(baseName);
        String timestamp = SIGNED_DOCUMENT_TIMESTAMP.format(LocalDateTime.now());
        return sanitizedBase + "_" + datum.format(DateTimeFormatter.BASIC_ISO_DATE) + "_" + timestamp + extension.toLowerCase(Locale.ROOT);
    }

    private String sanitizeFileName(String input) {
        if (!StringUtils.hasText(input)) {
            return "alairt_dokumentum";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.replaceAll("[^a-zA-Z0-9_.-]", "_").toLowerCase(Locale.ROOT);
    }
}

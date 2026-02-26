package com.fintech.erp.service;

import com.fintech.erp.domain.OsztalekfizetesiKozgyulesek;
import com.fintech.erp.repository.OsztalekfizetesiKozgyulesekRepository;
import com.fintech.erp.service.dto.OsztalekfizetesiKozgyulesekDTO;
import com.fintech.erp.service.mapper.OsztalekfizetesiKozgyulesekMapper;
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
 * Service Implementation for managing {@link com.fintech.erp.domain.OsztalekfizetesiKozgyulesek}.
 */
@Service
@Transactional
public class OsztalekfizetesiKozgyulesekService {

    private static final Logger LOG = LoggerFactory.getLogger(OsztalekfizetesiKozgyulesekService.class);

    private static final Path DOCUMENT_BASE_DIR = Path.of("uploads", "osztalekfizetesi-kozgyulesek");
    private static final DateTimeFormatter TIMESTAMP_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository;

    private final OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper;

    public OsztalekfizetesiKozgyulesekService(
        OsztalekfizetesiKozgyulesekRepository osztalekfizetesiKozgyulesekRepository,
        OsztalekfizetesiKozgyulesekMapper osztalekfizetesiKozgyulesekMapper
    ) {
        this.osztalekfizetesiKozgyulesekRepository = osztalekfizetesiKozgyulesekRepository;
        this.osztalekfizetesiKozgyulesekMapper = osztalekfizetesiKozgyulesekMapper;
    }

    /**
     * Save a osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the entity to save.
     * @return the persisted entity.
     */
    public OsztalekfizetesiKozgyulesekDTO save(OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO) {
        LOG.debug("Request to save OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekMapper.toEntity(
            osztalekfizetesiKozgyulesekDTO
        );
        osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.save(osztalekfizetesiKozgyulesek);
        return osztalekfizetesiKozgyulesekMapper.toDto(osztalekfizetesiKozgyulesek);
    }

    /**
     * Update a osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the entity to save.
     * @return the persisted entity.
     */
    public OsztalekfizetesiKozgyulesekDTO update(OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO) {
        LOG.debug("Request to update OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);
        OsztalekfizetesiKozgyulesek osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekMapper.toEntity(
            osztalekfizetesiKozgyulesekDTO
        );
        osztalekfizetesiKozgyulesek = osztalekfizetesiKozgyulesekRepository.save(osztalekfizetesiKozgyulesek);
        return osztalekfizetesiKozgyulesekMapper.toDto(osztalekfizetesiKozgyulesek);
    }

    /**
     * Partially update a osztalekfizetesiKozgyulesek.
     *
     * @param osztalekfizetesiKozgyulesekDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OsztalekfizetesiKozgyulesekDTO> partialUpdate(OsztalekfizetesiKozgyulesekDTO osztalekfizetesiKozgyulesekDTO) {
        LOG.debug("Request to partially update OsztalekfizetesiKozgyulesek : {}", osztalekfizetesiKozgyulesekDTO);

        return osztalekfizetesiKozgyulesekRepository
            .findById(osztalekfizetesiKozgyulesekDTO.getId())
            .map(existingOsztalekfizetesiKozgyulesek -> {
                osztalekfizetesiKozgyulesekMapper.partialUpdate(existingOsztalekfizetesiKozgyulesek, osztalekfizetesiKozgyulesekDTO);

                return existingOsztalekfizetesiKozgyulesek;
            })
            .map(osztalekfizetesiKozgyulesekRepository::save)
            .map(osztalekfizetesiKozgyulesekMapper::toDto);
    }

    /**
     * Get one osztalekfizetesiKozgyulesek by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OsztalekfizetesiKozgyulesekDTO> findOne(Long id) {
        LOG.debug("Request to get OsztalekfizetesiKozgyulesek : {}", id);
        return osztalekfizetesiKozgyulesekRepository
            .findById(id)
            .map(osztalekfizetesiKozgyulesekMapper::toDto)
            .map(this::enrichDocumentUrls);
    }

    /**
     * Delete the osztalekfizetesiKozgyulesek by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OsztalekfizetesiKozgyulesek : {}", id);
        osztalekfizetesiKozgyulesekRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Document handling
    // -------------------------------------------------------------------------

    /**
     * Upload a signed minutes document for the given assembly meeting.
     */
    public OsztalekfizetesiKozgyulesekDTO uploadSignedDocument(Long meetingId, MultipartFile file) throws IOException {
        LOG.debug("Request to upload signed document for OsztalekfizetesiKozgyulesek : {}", meetingId);
        validateDocument(file);
        if (meetingId == null) {
            throw new IllegalArgumentException("A közgyűlés azonosítója kötelező");
        }

        OsztalekfizetesiKozgyulesek meeting = osztalekfizetesiKozgyulesekRepository
            .findById(meetingId)
            .orElseThrow(() -> new IllegalArgumentException("A megadott közgyűlés nem található"));

        String storedFileName = buildSignedFileName(file.getOriginalFilename(), meeting.getKozgyulesDatum());
        Path targetPath = buildStoragePath(meetingId, storedFileName);
        Files.createDirectories(targetPath.getParent());
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        meeting.setKozgyulesiJegyzokonyvAlairt(Boolean.TRUE);
        meeting.setAlairtDokumentumNev(storedFileName);
        meeting.setAlairtDokumentumUrl(storedFileName);
        OsztalekfizetesiKozgyulesek persisted = osztalekfizetesiKozgyulesekRepository.save(meeting);
        return enrichDocumentUrls(osztalekfizetesiKozgyulesekMapper.toDto(persisted));
    }

    /**
     * Resolve the filesystem path of the signed document for the given assembly meeting.
     */
    @Transactional(readOnly = true)
    public Optional<Path> resolveSignedDocumentPath(Long meetingId) {
        LOG.debug("Request to resolve signed document path for OsztalekfizetesiKozgyulesek : {}", meetingId);
        if (meetingId == null) {
            return Optional.empty();
        }
        return osztalekfizetesiKozgyulesekRepository
            .findById(meetingId)
            .flatMap(meeting -> {
                String stored = extractFileName(meeting.getAlairtDokumentumUrl());
                if (!StringUtils.hasText(stored)) {
                    return Optional.empty();
                }
                return Optional.of(buildStoragePath(meetingId, stored));
            });
    }

    /**
     * Delete the signed document for the given assembly meeting.
     */
    public void deleteSignedDocument(Long meetingId) {
        LOG.debug("Request to delete signed document for OsztalekfizetesiKozgyulesek : {}", meetingId);
        if (meetingId == null) {
            throw new IllegalArgumentException("A közgyűlés azonosítója kötelező");
        }

        OsztalekfizetesiKozgyulesek meeting = osztalekfizetesiKozgyulesekRepository
            .findById(meetingId)
            .orElseThrow(() -> new IllegalArgumentException("A megadott közgyűlés nem található"));

        String stored = extractFileName(meeting.getAlairtDokumentumUrl());
        if (StringUtils.hasText(stored)) {
            try {
                Files.deleteIfExists(buildStoragePath(meetingId, stored));
            } catch (IOException ex) {
                LOG.warn("Nem sikerült törölni az aláírt dokumentumot: {}", ex.getMessage());
            }
        }

        meeting.setKozgyulesiJegyzokonyvAlairt(Boolean.FALSE);
        meeting.setAlairtDokumentumNev(null);
        meeting.setAlairtDokumentumUrl(null);
        osztalekfizetesiKozgyulesekRepository.save(meeting);
    }

    /**
     * Resolve the filesystem path of the generated minutes document for the given assembly meeting.
     */
    @Transactional(readOnly = true)
    public Optional<Path> resolveGeneratedDocumentPath(Long meetingId, String fileName) {
        LOG.debug("Request to resolve generated document path for OsztalekfizetesiKozgyulesek : {}", meetingId);
        if (meetingId == null || !StringUtils.hasText(fileName)) {
            return Optional.empty();
        }
        if (!osztalekfizetesiKozgyulesekRepository.existsById(meetingId)) {
            return Optional.empty();
        }
        return Optional.of(buildStoragePath(meetingId, fileName));
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private OsztalekfizetesiKozgyulesekDTO enrichDocumentUrls(OsztalekfizetesiKozgyulesekDTO dto) {
        if (dto.getId() == null) {
            return dto;
        }
        Long id = dto.getId();

        if (StringUtils.hasText(dto.getGeneraltDokumentumNev())) {
            String fileName = extractFileName(dto.getGeneraltDokumentumNev());
            if (StringUtils.hasText(fileName)) {
                dto.setGeneraltDokumentumUrl(buildDownloadUrl(id, fileName, "generated-document"));
            }
        }

        if (StringUtils.hasText(dto.getAlairtDokumentumUrl())) {
            String currentUrl = dto.getAlairtDokumentumUrl();
            if (!currentUrl.startsWith("http") && !currentUrl.startsWith("/api/")) {
                dto.setAlairtDokumentumUrl(buildDownloadUrl(id, null, "signed-document"));
            }
        }
        return dto;
    }

    private String buildDownloadUrl(Long id, String fileName, String subPath) {
        String relativeUrl;
        if (fileName != null) {
            String encoded = UriUtils.encodePathSegment(fileName, StandardCharsets.UTF_8);
            relativeUrl = "/api/osztalekfizetesi-kozgyuleseks/" + id + "/" + subPath + "/" + encoded;
        } else {
            relativeUrl = "/api/osztalekfizetesi-kozgyuleseks/" + id + "/" + subPath;
        }
        try {
            return ServletUriComponentsBuilder.fromCurrentContextPath().path(relativeUrl).toUriString();
        } catch (IllegalStateException ex) {
            LOG.debug("Nem sikerült abszolút URL-t képezni: {}", ex.getMessage());
            return relativeUrl;
        }
    }

    private String extractFileName(String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        // Strip any leading path separators or URL segments
        int idx = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        return idx >= 0 ? path.substring(idx + 1) : path;
    }

    private void validateDocument(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("A feltöltött fájl üres");
        }
        String filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            throw new IllegalArgumentException("A fájlnév kötelező");
        }
        String lower = filename != null ? filename.toLowerCase(Locale.ROOT) : "";
        if (!(lower.endsWith(".pdf") || lower.endsWith(".docx"))) {
            throw new IllegalArgumentException("Csak PDF vagy DOCX fájl tölthető fel");
        }
    }

    private Path buildStoragePath(Long meetingId, String fileName) {
        return DOCUMENT_BASE_DIR.resolve(String.valueOf(meetingId)).resolve(fileName);
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
        String timestamp = TIMESTAMP_FMT.format(LocalDateTime.now());
        String datePart = datum != null ? datum.format(DateTimeFormatter.BASIC_ISO_DATE) : "nodatum";
        return sanitizedBase + "_" + datePart + "_" + timestamp + extension.toLowerCase(Locale.ROOT);
    }

    private String sanitizeFileName(String input) {
        if (!StringUtils.hasText(input)) {
            return "alairt_dokumentum";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.replaceAll("[^a-zA-Z0-9_.-]", "_").toLowerCase(Locale.ROOT);
    }
}

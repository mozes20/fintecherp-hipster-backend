package com.fintech.erp.service.document;

import com.fintech.erp.config.ApplicationProperties;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class GotenbergClient {

    private static final Logger LOG = LoggerFactory.getLogger(GotenbergClient.class);

    private final boolean enabled;
    private final RestTemplate restTemplate;
    private final URI convertOfficeUri;
    private final String apiKey;

    public GotenbergClient(RestTemplateBuilder restTemplateBuilder, ApplicationProperties properties) {
        ApplicationProperties.Gotenberg config = properties.getGotenberg();
        if (config != null && config.isEnabled() && StringUtils.hasText(config.getBaseUrl())) {
            this.enabled = true;
            this.apiKey = StringUtils.hasText(config.getApiKey()) ? config.getApiKey() : null;
            Duration connectTimeout = defaultIfNull(config.getConnectTimeout(), Duration.ofSeconds(5));
            Duration readTimeout = defaultIfNull(config.getReadTimeout(), Duration.ofSeconds(60));
            org.springframework.http.client.SimpleClientHttpRequestFactory requestFactory =
                new org.springframework.http.client.SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout((int) connectTimeout.toMillis());
            requestFactory.setReadTimeout((int) readTimeout.toMillis());
            this.restTemplate = restTemplateBuilder.requestFactory(() -> requestFactory).build();
            this.convertOfficeUri = buildConvertUri(config.getBaseUrl());
            LOG.info("Gotenberg client initialized with endpoint {}", this.convertOfficeUri);
        } else {
            this.enabled = false;
            this.restTemplate = null;
            this.convertOfficeUri = null;
            this.apiKey = null;
            LOG.info("Gotenberg integration disabled");
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public byte[] convertDocxToPdf(byte[] docxBytes, String fileName) throws IOException {
        if (!enabled) {
            throw new IllegalStateException("Gotenberg integration is disabled");
        }
        Objects.requireNonNull(docxBytes, "docxBytes must not be null");
        if (docxBytes.length == 0) {
            throw new IllegalArgumentException("docxBytes must not be empty");
        }
        String fileNameWithExt = ensureDocxExtension(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(List.of(MediaType.APPLICATION_PDF));
        if (StringUtils.hasText(apiKey)) {
            headers.set("X-Api-Key", apiKey);
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<ByteArrayResource> fileEntity = new HttpEntity<>(new NamedByteArrayResource(docxBytes, fileNameWithExt), fileHeaders);
        body.add("files", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(convertOfficeUri, HttpMethod.POST, requestEntity, byte[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().length > 0) {
                return response.getBody();
            }
            throw new IOException("Gotenberg request failed with status " + response.getStatusCode());
        } catch (RestClientResponseException ex) {
            String responseBody = ex.getResponseBodyAsString(StandardCharsets.UTF_8);
            throw new IOException("Gotenberg request failed with status " + ex.getStatusCode().value() + ": " + responseBody, ex);
        }
    }

    private static URI buildConvertUri(String baseUrl) {
        String trimmed = baseUrl.trim();
        if (!trimmed.startsWith("http")) {
            trimmed = "http://" + trimmed;
        }
        if (trimmed.endsWith("/")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return URI.create(trimmed + "/forms/libreoffice/convert");
    }

    private static String ensureDocxExtension(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return "document.docx";
        }
        String candidate = fileName.trim();
        if (!candidate.toLowerCase().endsWith(".docx")) {
            candidate = candidate + ".docx";
        }
        return candidate;
    }

    private static Duration defaultIfNull(@Nullable Duration input, Duration fallback) {
        return input != null ? input : fallback;
    }

    private static final class NamedByteArrayResource extends ByteArrayResource {

        private final String fileName;

        private NamedByteArrayResource(byte[] byteArray, String fileName) {
            super(byteArray);
            this.fileName = fileName;
        }

        @Override
        public String getFilename() {
            return fileName;
        }
    }
}

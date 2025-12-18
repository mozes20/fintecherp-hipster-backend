package com.fintech.erp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Fintech Erp.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Liquibase liquibase = new Liquibase();
    private final Gotenberg gotenberg = new Gotenberg();

    // jhipster-needle-application-properties-property

    public Liquibase getLiquibase() {
        return liquibase;
    }

    public Gotenberg getGotenberg() {
        return gotenberg;
    }

    // jhipster-needle-application-properties-property-getter

    public static class Liquibase {

        private Boolean asyncStart = true;

        public Boolean getAsyncStart() {
            return asyncStart;
        }

        public void setAsyncStart(Boolean asyncStart) {
            this.asyncStart = asyncStart;
        }
    }

    public static class Gotenberg {

        private boolean enabled;
        private String baseUrl;
        private String apiKey;
        private java.time.Duration connectTimeout = java.time.Duration.ofSeconds(5);
        private java.time.Duration readTimeout = java.time.Duration.ofSeconds(60);

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public java.time.Duration getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(java.time.Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public java.time.Duration getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(java.time.Duration readTimeout) {
            this.readTimeout = readTimeout;
        }
    }
    // jhipster-needle-application-properties-property-class
}

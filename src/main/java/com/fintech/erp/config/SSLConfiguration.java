package com.fintech.erp.config;

import java.security.cert.X509Certificate;
import javax.net.ssl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * SSL Configuration for development environment.
 * WARNING: This disables SSL certificate validation and should NEVER be used in
 * production!
 */
@Configuration
@ConditionalOnProperty(name = "app.security.disable-ssl-validation", havingValue = "true")
public class SSLConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(SSLConfiguration.class);

    static {
        disableSslVerification();
    }

    @Bean
    @Order(1)
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }

    private static void disableSslVerification() {
        try {
            LOG.warn("********************************************************");
            LOG.warn("*** SSL CERTIFICATE VALIDATION IS DISABLED!          ***");
            LOG.warn("*** THIS SHOULD ONLY BE USED IN DEVELOPMENT MODE!    ***");
            LOG.warn("*** NEVER USE THIS IN PRODUCTION!                    ***");
            LOG.warn("********************************************************");

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            LOG.error("Failed to disable SSL verification", e);
        }
    }
}

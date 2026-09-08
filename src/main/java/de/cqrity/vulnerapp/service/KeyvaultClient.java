package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.config.TranslationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Fetches the fal.ai API key from the keyvault at runtime.
 *
 * Only the keyvault token is stored locally; the fal.ai key itself is never
 * written to disk. It is looked up on first use and then cached in memory.
 */
@Service
public class KeyvaultClient {

    private static final Logger log = LoggerFactory.getLogger(KeyvaultClient.class);

    private final TranslationProperties properties;
    private final RestClient restClient = RestClient.create();

    private volatile String cachedFalApiKey;

    public KeyvaultClient(TranslationProperties properties) {
        this.properties = properties;
    }

    public String getFalApiKey() {
        String cached = cachedFalApiKey;
        if (cached != null) {
            return cached;
        }

        synchronized (this) {
            if (cachedFalApiKey != null) {
                return cachedFalApiKey;
            }

            String token = properties.getKeyvaultToken();
            if (token == null || token.isBlank()) {
                throw new IllegalStateException(
                        "translation.keyvault-token is not set. Copy "
                                + "src/main/resources/local.properties.example to local.properties "
                                + "and paste your keyvault token.");
            }

            String key = restClient.get()
                    .uri(properties.getKeyvaultUrl())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(String.class);

            if (key == null || key.isBlank()) {
                throw new IllegalStateException("Keyvault returned an empty fal.ai API key");
            }

            cachedFalApiKey = key.trim();
            log.info("Retrieved fal.ai API key from keyvault ({} characters)", cachedFalApiKey.length());
            return cachedFalApiKey;
        }
    }
}

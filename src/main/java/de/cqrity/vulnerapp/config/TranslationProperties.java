package de.cqrity.vulnerapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Settings for the ad translation feature.
 *
 * The keyvault token is the only secret and lives in local.properties, which is
 * gitignored and read manually. It is optional: without it the application still
 * starts and every other exercise keeps working, and only the translate button fails.
 */
@Component
@ConfigurationProperties(prefix = "translation")
@PropertySource(value = "classpath:local.properties", ignoreResourceNotFound = true)
public class TranslationProperties {

    /** Keyvault endpoint that hands out the fal.ai API key. */
    private String keyvaultUrl;

    /** Bearer token for the keyvault. Set in local.properties, never committed. */
    private String keyvaultToken;

    /** fal.ai synchronous model endpoint. */
    private String falaiUrl;

    /** Model identifier passed to fal.ai. */
    private String falaiModel;

    public String getKeyvaultUrl() {
        return keyvaultUrl;
    }

    public void setKeyvaultUrl(String keyvaultUrl) {
        this.keyvaultUrl = keyvaultUrl;
    }

    public String getKeyvaultToken() {
        return keyvaultToken;
    }

    public void setKeyvaultToken(String keyvaultToken) {
        this.keyvaultToken = keyvaultToken;
    }

    public String getFalaiUrl() {
        return falaiUrl;
    }

    public void setFalaiUrl(String falaiUrl) {
        this.falaiUrl = falaiUrl;
    }

    public String getFalaiModel() {
        return falaiModel;
    }

    public void setFalaiModel(String falaiModel) {
        this.falaiModel = falaiModel;
    }
}

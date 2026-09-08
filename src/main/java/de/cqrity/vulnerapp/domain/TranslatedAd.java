package de.cqrity.vulnerapp.domain;

/**
 * Result of translating a classified ad. Serialised to JSON for the translate button.
 */
public record TranslatedAd(String title, String description) {
}

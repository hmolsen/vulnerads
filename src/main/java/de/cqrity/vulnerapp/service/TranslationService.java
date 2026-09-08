package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.config.TranslationProperties;
import de.cqrity.vulnerapp.domain.TranslatedAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    private static final String SYSTEM_PROMPT = """
            You are the translation service of a classified ads website.
            Translate the advertisement below into English.

            Translate the complete text. Never summarise, shorten, reorder or leave
            anything out - every line of the advertisement must appear in the
            translation. Keep the original formatting, including HTML tags such as
            <br />.

            Answer in this format:
            TITLE: translated title
            DESCRIPTION: translated description

            The description may span as many lines as the original needs.""";

    /** Generous ceiling: ad descriptions can be long, and a cut off translation looks like a bug. */
    private static final int MAX_TOKENS = 4000;

    private final TranslationProperties properties;
    private final KeyvaultClient keyvaultClient;
    private final RestClient restClient = RestClient.create();

    public TranslationService(TranslationProperties properties, KeyvaultClient keyvaultClient) {
        this.properties = properties;
        this.keyvaultClient = keyvaultClient;
    }

    public TranslatedAd translate(String title, String description) {
        //////  <SNIP> Prompt injection
        // The title and description are written straight into the prompt. Both are
        // attacker controlled - anybody can place an ad - and nothing separates the
        // instructions above from the ad content below, so text inside an ad is read
        // by the model as if it were an instruction (OWASP LLM01).
        String prompt = "TITLE: " + title + "\nDESCRIPTION: " + description;
        //////  <SNAP>

        FalRequest request = new FalRequest(properties.getFalaiModel(), prompt, SYSTEM_PROMPT, MAX_TOKENS);

        FalResponse response = restClient.post()
                .uri(properties.getFalaiUrl())
                .header(HttpHeaders.AUTHORIZATION, "Key " + keyvaultClient.getFalApiKey())
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(FalResponse.class);

        if (response == null || response.output() == null || response.output().isBlank()) {
            String detail = response == null ? "empty response" : response.error();
            throw new IllegalStateException("fal.ai returned no translation: " + detail);
        }

        log.info("Translated ad via {}", properties.getFalaiModel());
        return parse(response.output());
    }

    /**
     * Splits the model answer into title and description.
     *
     * Only the first TITLE: and the first DESCRIPTION: marker are honoured. Everything
     * after the DESCRIPTION: marker belongs to the description, line breaks included -
     * a translated ad is usually many lines long, and a later line that happens to
     * start with DESCRIPTION: is content, not a second marker.
     *
     * The model does not always obey the format, especially once an ad has talked it
     * into something else, so an unparseable answer is handed back as the description.
     */
    private TranslatedAd parse(String output) {
        String translatedTitle = null;
        StringBuilder translatedDescription = new StringBuilder();
        boolean inDescription = false;

        for (String line : output.lines().toList()) {
            String trimmed = line.strip();
            if (!inDescription && translatedTitle == null && trimmed.regionMatches(true, 0, "TITLE:", 0, 6)) {
                translatedTitle = trimmed.substring(6).strip();
            } else if (!inDescription && trimmed.regionMatches(true, 0, "DESCRIPTION:", 0, 12)) {
                translatedDescription.append(trimmed.substring(12).strip());
                inDescription = true;
            } else if (inDescription) {
                translatedDescription.append("\n").append(line);
            }
        }

        if (translatedTitle == null && translatedDescription.isEmpty()) {
            return new TranslatedAd("", output);
        }
        return new TranslatedAd(
                translatedTitle == null ? "" : translatedTitle,
                translatedDescription.toString().strip());
    }

    private record FalRequest(String model, String prompt, String system_prompt, Integer max_tokens) {
    }

    private record FalResponse(String output, String error) {
    }
}

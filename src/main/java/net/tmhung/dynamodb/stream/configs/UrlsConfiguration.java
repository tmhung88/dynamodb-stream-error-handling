package net.tmhung.dynamodb.stream.configs;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Data;

/**
 * Environment variables deadLetterQueue - URLS_DEAD_LETTER_QUEUE
 */
@Data
@ConfigProperties(prefix = "urls")
public class UrlsConfiguration {

  String deadLetterQueue;
}

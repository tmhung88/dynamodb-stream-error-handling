package net.tmhung.dynamodb.stream.configs;

import static java.time.temporal.ChronoUnit.SECONDS;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import java.time.Duration;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Dependent
public class RetryConfiguration {

  @Produces
  @Singleton
  public RetryConfig retryConfig() {
    return RetryConfig.custom()
        .maxAttempts(3)
        .waitDuration(Duration.of(1, SECONDS))
        .build();
  }

  @Produces
  @Singleton
  public RetryRegistry retryRegistry(RetryConfig config) {
    return RetryRegistry.of(config);
  }

  @Produces
  @Singleton
  public Retry retry(RetryConfig config, RetryRegistry retryRegistry) {
    return retryRegistry.retry("defaultRetry", config);
  }
}

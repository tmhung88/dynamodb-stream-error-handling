package net.tmhung.dynamodb.stream.services;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.Retry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.configs.RetryConfiguration;
import net.tmhung.dynamodb.stream.configs.UrlsConfiguration;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SqsResponse;

/**
 * A utility service that enables other services to handle DynamoDB stream to be more fault tolerance
 */
@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class DynamoDbStreamService {

  private final Retry defaultRetry;
  private final SqsClient sqsClient;
  private final ObjectMapper objectMapper;
  private final UrlsConfiguration urls;

  /**
   * Execute a single-record handler against a dynamodb event of stream records. The method always return successfully.
   * All unexpected exception are either retried or have failed records placed into a SQS queue. <br/> For retry
   * configuration, please look into {@link RetryConfiguration}
   *
   * @param handler a handler that processes an individual a DynamoDB stream record
   * @param input   a DynamoDB event or a batch of stream records
   * @return an empty value which indicates the event is processed successfully
   */
  public Optional<Void> executeEvent(Consumer<DynamodbStreamRecord> handler, DynamodbEvent input) {
    Function<DynamodbStreamRecord, Optional<Void>> enhancedFunction = Retry
        .decorateFunction(defaultRetry, (record) -> {
          handler.accept(record);
          return Optional.empty();
        });
    input.getRecords().forEach(record -> this.executeRecord(enhancedFunction, record));
    return Optional.empty();
  }

  private void executeRecord(Function<DynamodbStreamRecord, Optional<Void>> enhancedFunction,
      DynamodbStreamRecord record) {
    try {
      enhancedFunction.apply(record);
    } catch (Exception ex) {
      try {
        String messageBody = objectMapper.writeValueAsString(record);
        SqsResponse response = sqsClient
            .sendMessage(m -> m.queueUrl(urls.getDeadLetterQueue()).messageBody(messageBody));
        log.infof(ex, "Placed a failed record into SQS [%s]", response.toString());
      } catch (JsonProcessingException e) {
        log.warnf("Unable to place a failed record into SQS: [%s]", record);
      }
    }
  }
}

package net.tmhung.dynamodb.stream.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import java.util.Optional;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.services.DynamoDbStreamService;
import net.tmhung.dynamodb.stream.services.GreetingService;

/**
 * A simple lambda that is triggered by a DynamoDB stream
 */
@Named("greetingLambda")
@JBossLog
@AllArgsConstructor
public class GreetingLambda implements RequestHandler<DynamodbEvent, Optional<Void>> {

  private final GreetingService greetingService;
  private final DynamoDbStreamService streamService;

  @Override
  public Optional<Void> handleRequest(DynamodbEvent event, Context context) {
    return streamService.executeEvent(greetingService::handleStreamRecord, event);
  }
}

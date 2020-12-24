package net.tmhung.dynamodb.stream.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Optional;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.services.DynamoDbStreamService;
import net.tmhung.dynamodb.stream.services.GreetingService;

@Named("greetingLambda")
@JBossLog
@AllArgsConstructor
public class GreetingLambda implements RequestHandler<GreetingInput, Optional<Void>> {

  private final GreetingService greetingService;
  private final DynamoDbStreamService streamService;

  @Override
  public Optional<Void> handleRequest(GreetingInput input, Context context) {
    return streamService.execute(greetingService::greeting, input);
  }
}

package net.tmhung.dynamodb.stream.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.services.GreetingService;

@Named("greeting")
@JBossLog
@AllArgsConstructor
public class GreetingLambda implements RequestHandler<GreetingInput, GreetingOutput> {

  private final GreetingService greetingService;

  @Override
  public GreetingOutput handleRequest(GreetingInput input, Context context) {
    final String result = greetingService.greeting(input.getName());
    return new GreetingOutput(result);
  }
}

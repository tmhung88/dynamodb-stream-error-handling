package net.tmhung.dynamodb.stream.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.github.resilience4j.retry.Retry;
import java.util.function.Supplier;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.services.GreetingService;

@Named("greeting")
@JBossLog
@AllArgsConstructor
public class GreetingLambda implements RequestHandler<GreetingInput, GreetingOutput> {

  private final GreetingService greetingService;
  private final Retry defaultRetry;

  @Override
  public GreetingOutput handleRequest(GreetingInput input, Context context) {
    Supplier<String> greetingSupplier = () -> greetingService.greeting(input.getName(), input.getDividend());
    Supplier<String> retryGreetingSupplier = Retry.decorateSupplier(defaultRetry, greetingSupplier);
    String result = retryGreetingSupplier.get();
    return new GreetingOutput(result);
  }
}

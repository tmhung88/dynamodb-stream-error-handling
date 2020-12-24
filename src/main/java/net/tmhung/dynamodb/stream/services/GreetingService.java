package net.tmhung.dynamodb.stream.services;

import java.util.concurrent.ThreadLocalRandom;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.exceptions.ExternalServiceTimeoutException;
import net.tmhung.dynamodb.stream.exceptions.InconsistentDataException;
import net.tmhung.dynamodb.stream.lambdas.GreetingInput;

@JBossLog
@ApplicationScoped
@RequiredArgsConstructor
public class GreetingService {

  private static int randomNumber() {
    return ThreadLocalRandom.current().nextInt(0, 1000000);
  }

  public void greeting(GreetingInput input) {
    log.infof("Input [%s]", input);
    if ("permanent".equals(input.getName().trim().toLowerCase())) {
      throw new InconsistentDataException();
    }

    int randomNumber = randomNumber();
    int remainder = randomNumber % input.getDividend();
    log.debugf("RandomNumber %s | Remainder %s", randomNumber, remainder);
    if ("transient".equals(input.getName().trim().toLowerCase()) && remainder != 0) {
      throw new ExternalServiceTimeoutException();
    }

    log.infof("Successfully processed input [%s]", input);
  }
}

package net.tmhung.dynamodb.stream.services;

import java.util.concurrent.ThreadLocalRandom;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.exceptions.ExternalServiceTimeoutException;
import net.tmhung.dynamodb.stream.exceptions.InconsistentDataException;

@ApplicationScoped
@RequiredArgsConstructor
@JBossLog
public class GreetingService {

  private static final String GREETING_TEMPLATE = "Hello %s";

  private static int randomNumber() {
    return ThreadLocalRandom.current().nextInt(0, 1000000);
  }

  public static void main(String[] args) {
    System.out.println(randomNumber() % 99999);
  }

  public String greeting(String name, int dividend) {
    log.infof("Input [%s,  %s]", name, dividend);
    if ("permanent".equals(name.trim().toLowerCase())) {
      throw new InconsistentDataException();
    }

    int randomNumber = randomNumber();
    int remainder = randomNumber % dividend;
    log.debugf("RandomNumber %s | Remainder %s", randomNumber, remainder);
    if ("transient".equals(name.trim().toLowerCase()) && remainder != 0) {
      throw new ExternalServiceTimeoutException();
    }

    return String.format(GREETING_TEMPLATE, name);
  }
}

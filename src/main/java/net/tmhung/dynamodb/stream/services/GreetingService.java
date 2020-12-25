package net.tmhung.dynamodb.stream.services;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import java.util.concurrent.ThreadLocalRandom;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.exceptions.ExternalServiceTimeoutException;
import net.tmhung.dynamodb.stream.exceptions.InconsistentDataException;

/**
 * A simple business service that produces real-life scenarios where exceptions are thrown during request processing
 */
@JBossLog
@ApplicationScoped
@RequiredArgsConstructor
public class GreetingService {

  private static int randomNumber() {
    return ThreadLocalRandom.current().nextInt(0, 1000000);
  }

  public void handleStreamRecord(DynamodbStreamRecord record) {
    log.infof("Input record [%s]", record);
    if (!"INSERT".equals(record.getEventName())) {
      return;
    }
    var newItem = record.getDynamodb().getNewImage();
    var parts = newItem.get("name").getS().split("-");
    var name = parts[0];
    var dividend = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
    greeting(name, dividend);
  }

  private void greeting(String name, int dividend) {
    log.infof("Input [%s, %s]", name, dividend);
    if ("permanent".equals(name.trim().toLowerCase())) {
      throw new InconsistentDataException();
    }

    var randomNumber = randomNumber();
    var remainder = randomNumber % dividend;
    log.debugf("RandomNumber %s | Remainder %s", randomNumber, remainder);
    if ("transient".equals(name.trim().toLowerCase()) && remainder != 0) {
      throw new ExternalServiceTimeoutException();
    }

    log.infof("Successfully processed input [%s, %s]", name, dividend);
  }
}

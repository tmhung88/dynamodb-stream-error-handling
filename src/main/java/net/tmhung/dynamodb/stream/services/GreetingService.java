package net.tmhung.dynamodb.stream.services;

import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.exceptions.TransientException;

@ApplicationScoped
@RequiredArgsConstructor
@JBossLog
public class GreetingService {

  private static final String GREETING_TEMPLATE = "Hello %s";

  public String greeting(String name) {
    log.infof(GREETING_TEMPLATE, name);
    throw new TransientException();
  }
}

package net.tmhung.dynamodb.stream.services;

import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class GreetingService {

  private static final String GREETING_TEMPLATE = "Hello %s";

  public String greeting(String name) {
    return String.format(GREETING_TEMPLATE, name);
  }
}

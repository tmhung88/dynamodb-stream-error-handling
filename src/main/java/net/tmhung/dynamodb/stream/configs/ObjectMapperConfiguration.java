package net.tmhung.dynamodb.stream.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import lombok.extern.jbosslog.JBossLog;

@Dependent
@JBossLog
public class ObjectMapperConfiguration {

  @Produces
  @Singleton
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}

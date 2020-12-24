package net.tmhung.dynamodb.stream.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import java.util.Date;
import javax.inject.Singleton;
import lombok.extern.jbosslog.JBossLog;
import net.tmhung.dynamodb.stream.jackson.TimestampDeserializer;

@JBossLog
@Singleton
public class AppObjectMapperCustomizer implements ObjectMapperCustomizer {

  @Override
  public void customize(ObjectMapper objectMapper) {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Date.class, new TimestampDeserializer());
    objectMapper.registerModule(module);
  }
}

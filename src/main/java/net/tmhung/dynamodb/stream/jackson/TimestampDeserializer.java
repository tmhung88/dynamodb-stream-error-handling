package net.tmhung.dynamodb.stream.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Convert a long millisecond into Date
 */
public class TimestampDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(jsonParser.getValueAsLong());
    return calendar.getTime();
  }
}

package net.tmhung.dynamodb.stream.exceptions;

import java.util.UUID;

public class TransientException extends RuntimeException {

  public TransientException() {
    super(String.format("A transient %s error happened", UUID.randomUUID().toString().replaceAll("-", "")));
  }
}

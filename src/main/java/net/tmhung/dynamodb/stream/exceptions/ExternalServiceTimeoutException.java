package net.tmhung.dynamodb.stream.exceptions;

public class ExternalServiceTimeoutException extends RuntimeException {

  public ExternalServiceTimeoutException() {
    super("Transient failure due a timeout of an external service");
  }
}

package net.tmhung.dynamodb.stream.exceptions;

public class InconsistentDataException extends RuntimeException {

  public InconsistentDataException() {
    super("Permanent failure due to data structure inconsistency");
  }
}

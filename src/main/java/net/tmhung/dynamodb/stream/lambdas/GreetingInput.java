package net.tmhung.dynamodb.stream.lambdas;

import lombok.Value;

@Value
public class GreetingInput {

  String name;

  int dividend;
}

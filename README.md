## Introduction
DynamoDB stream is a powerful service which is employed in various scenarios: roll-up aggregations, enforcing data
 consistency, ... However, it's not clear how to correctly handle errors during stream processing. If it's done
  wrongly, a lambda can get stuck in an infinite loop or lose stream records. A good design pattern must address
   these problems and make the stream processing more fault tolerant. 

## Solution
A lambda must catch all kinds of exceptions to prevent a DynamoDb stream from delivering the same batch repeatedly. At
 the same time, a flexible retry mechanism is implemented to provide retry for transient errors. Eventually, failed
  records due to unexpected errors should be placed into SQS for later investigation or routing
  to another lambda.

## Demo
The demo is implemented with Java 11. AWS service development is done with [Quarkus](http://quarkus.io/). In addition
, it employs
 [resilience4j-retry](https://resilience4j.readme.io/docs/retry) to provide different retry strategies.
 
#### Requirements:
- Java 11
- A SQS Queue
- A DynamoDB table with a single column `id: string` which is also a partition key
- An AWS Lambda with a Java-11 runtime
- An IAM role for the lambda to receive events from the DynamoDB stream and places messages into the SQS queue

#### How to run:
1. Add an environment variable for the lambda
```
URLS_DEAD_LETTER_QUEUE=<SQS Queue URL>
```
2. Go to the root project directory, build a lambda
```shell script
./gradle clean quarkusBuild
```
3. Find `function.zip` file in the `./build` directory and upload it into your lambda
4. Update `aws-region` and `table-name` in the `scripts/index.js`, run the script to trigger the lambda
5. After the script is executed completely, there should be 2 failed messages in the SQS queue. They are 2 failed
 records from a batch that the script has generated
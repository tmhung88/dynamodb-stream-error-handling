const {DynamoDB} = require("@aws-sdk/client-dynamodb");
const {v4: uuidv4} = require('uuid');

const client = new DynamoDB({region: "us-east-1"});

function insertItem(name) {
  return client.putItem({
    TableName: 'tmhung-dynamodb-stream-table',
    Item: {
      "id": {
        S: uuidv4()
      },
      "name": {
        S: name
      }
    }
  });
}

async function insertScenarios() {
  // A case where a request is handled successfully
  const successfulRequest = 'helloworld';

  // A case where a request fails due to a transient error,
  // retry a few times, eventually it passes successfully
  const successfulTransient = 'transient-2';

  // A case where a request fails due to a transient error,
  // retry too many times, eventually it fails and logged into SQS
  const failedTransient = 'transient-9999';

  // A  case where a request fails due to a permanent error. No retry takes
  // place and it's logged into SQS
  const permanentFailure = 'permanent-24';

  const promise1 = insertItem(successfulRequest);
  const promise2 = insertItem(successfulTransient);
  const promise3 = insertItem(failedTransient);
  const promise4 = insertItem(permanentFailure);
  await Promise.all([promise1, promise2, promise3, promise4]);
  console.log("All scenarios inserted")
}

insertScenarios();
## Requirements

- MacOS or a Linux distro to run deployment scripts (Windows not supported)
- Java SDK 11 
- AWS credentials set up in ~/.aws/credentials
- AWS region set up in ~/.aws/config
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html)


## Setup

- Create an IAM role for the lambda. It's named as `greeting-lambda-role`
```shell script
aws iam create-role --role-name greeting-lambda-role --assume-role-policy-document file://lambda-role-policy.json
```

- Set the role ARN from #1 into the environment variable for the deployment script
```shell script
export LAMBDA_ROLE_ARN="<ROLE ARN VALUE>"
## example: export LAMBDA_ROLE_ARN="arn:aws:iam::748520250836:role/greeting-lambda-role"
```

## How to:

Please finish the IAM role setup first before you build and deploy the lambda. Supposed your region is `us-east-1
`, after the deployment, you can find your lambda [here](https://console.aws.amazon.com/lambda/home?region=us-east-1#/functions/DynamostreamErrorHandling?tab=configuration) 

1. Under the root project directory, build the project
```shell script
./gradle clean quarkusBuild
```

2. To create or update the lambda, run this command which creates a lambda named `DynamostreamErrorHandling`
```shell script
./build/manage.sh delete create
```


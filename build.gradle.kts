val lombokVersion = "1.18.16"
val quarkusVersion = "1.10.3.Final"
val resilience4jVersion = "1.6.1"

plugins {
    application
    java
    id("io.quarkus") version "1.10.3.Final"
    id("io.franzbecker.gradle-lombok") version "4.0.0"
}

group = "net.tmhung.dynamodb.stream"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:${resilience4jVersion}")
    implementation("io.github.resilience4j:resilience4j-ratelimiter:${resilience4jVersion}")
    implementation("io.github.resilience4j:resilience4j-retry:${resilience4jVersion}")
    implementation("io.github.resilience4j:resilience4j-bulkhead:${resilience4jVersion}")
    implementation("io.github.resilience4j:resilience4j-cache:${resilience4jVersion}")
    implementation("io.github.resilience4j:resilience4j-timelimiter:${resilience4jVersion}")
    implementation("software.amazon.awssdk:url-connection-client")
    implementation("software.amazon.awssdk:apache-client")

    implementation(enforcedPlatform("io.quarkus:quarkus-universe-bom:${quarkusVersion}"))
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-amazon-lambda")
    implementation("io.quarkus:quarkus-amazon-dynamodb")
    implementation("io.quarkus:quarkus-amazon-sqs")
}

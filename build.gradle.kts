val lombokVersion = "1.18.16"
val quarkusVersion = "1.10.3.Final"

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
    implementation(enforcedPlatform("io.quarkus:quarkus-universe-bom:${quarkusVersion}"))
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-amazon-lambda")
    implementation("io.quarkus:quarkus-amazon-dynamodb")
}

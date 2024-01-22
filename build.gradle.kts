plugins {
    id("java")
    id("io.spring.dependency-management")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.3"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
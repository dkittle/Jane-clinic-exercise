plugins {
    id("java")
}
private val lombokDependency = "org.projectlombok:lombok:${project.property("lombokVersion")}"
private val validationApiVersion = project.property("validationApiVersion")
private val hibernateValidationVersion = project.property("hibernateValidationVersion")

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = "ca.kittle"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("jakarta.validation:jakarta.validation-api:$validationApiVersion")
    implementation("org.hibernate.validator:hibernate-validator:$hibernateValidationVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly(lombokDependency)
    annotationProcessor(lombokDependency)
    testCompileOnly(lombokDependency)
    testAnnotationProcessor(lombokDependency)
}

tasks.test {
    useJUnitPlatform()
}

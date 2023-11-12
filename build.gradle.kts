import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Define default Spring Boot Version
val springBootVersionNumber: String = "3.1.5"
ext.set("spring-boot-version", springBootVersionNumber)

plugins {
    id("org.springframework.boot") version "3.1.5" // we can't use val inside plugins, so set it again (the same number)
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.9.20" // the plugin defines the version of Kotlin to be used in the project
    kotlin("plugin.spring") version "1.9.20" // Kotlin Spring compiler plugin for adding the open modifier to Kotlin classes in order to make them compatible with Spring Framework features
}

// Override the Spring Security version, because in normal Spring Boot has it smaller
ext.set("spring-security.version", "6.1.5")

group = "divo-in-ua.auto"
version = "1"

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {// Settings for `KotlinCompile` tasks
    kotlinOptions {// Kotlin compiler options
        freeCompilerArgs += "-Xjsr305=strict" // `-Xjsr305=strict` enables the strict mode for JSR-305 annotations
        jvmTarget = "17" // This option specifies the target version of the generated JVM bytecode
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

// define common variables
val jwtVersion = "0.12.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${project.ext.get("spring-boot-version")}")
    implementation("org.springframework.boot:spring-boot-starter-security:${project.ext.get("spring-boot-version")}")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:${project.ext.get("spring-boot-version")}")
    developmentOnly("org.springframework.boot:spring-boot-devtools:${project.ext.get("spring-boot-version")}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${project.ext.get("spring-boot-version")}")
    testImplementation("org.springframework.boot:spring-boot-starter-security:${project.ext.get("spring-boot-version")}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
    testImplementation("org.springframework.security:spring-security-test:${project.ext.get("spring-security.version")}")
}

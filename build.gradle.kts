import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.9.20" // the plugin defines the version of Kotlin to be used in the project
    kotlin("plugin.spring") version "1.9.20" // Kotlin Spring compiler plugin for adding the open modifier to Kotlin classes in order to make them compatible with Spring Framework features
}

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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.1.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.5")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.1.5")
}

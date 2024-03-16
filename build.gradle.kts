import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    val springBootVersion = "3.2.3"
    val kotlinVersion = "1.9.22"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

allprojects {
    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    group = "org.example"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(21)
    }
}

tasks.withType<BootJar> {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}

tasks.withType<BootRun> {
    mainClass = "org.example.Main"
}

dependencies {
    implementation(project(":app"))
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "user-manager-uni"
include("app")
include("domain")
include("service")

dependencyResolutionManagement {
    versionCatalogs {
        create("spring") {
            val springGroup = "org.springframework.boot"
            val springVersion = "3.2.3"
            version("spring", springVersion)
            library("actuator", springGroup, "spring-boot-starter-actuator").versionRef("spring")
            library("validation", springGroup, "spring-boot-starter-validation").versionRef("spring")
            library("web", springGroup, "spring-boot-starter-web").versionRef("spring")
            library("jpa", springGroup, "spring-boot-starter-data-jpa").versionRef("spring")

            bundle("springAppModule", listOf("actuator", "validation", "web"))
        }
        create("springTest") {
            val springVersion = "3.2.3"
            version("springTest", springVersion)
            library("springBootTest", "org.springframework.boot", "spring-boot-starter-test").versionRef("springTest")
        }

        create("libs") {
            library("kotlinReflect", "org.jetbrains.kotlin", "kotlin-reflect").version("1.9.23")
            library("postgres", "org.postgresql:postgresql:42.7.3")
            library("flyway", "org.flywaydb", "flyway-core").version("10.10.0")
            library("flywayPostgres", "org.flywaydb", "flyway-database-postgresql").version("10.10.0")
            library("jackson", "com.fasterxml.jackson.module", "jackson-module-kotlin").version("2.16.1")
        }
    }
}

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
            library("security", springGroup, "spring-boot-starter-security").versionRef("spring")

            bundle("springAppModule", listOf("actuator", "validation", "web"))
        }
        create("springTest") {
            val springVersion = "3.2.3"
            version("springTest", springVersion)
            library("springBootTest", "org.springframework.boot", "spring-boot-starter-test").versionRef("springTest")
        }

        create("libs") {
            library("jwt", "io.jsonwebtoken:jjwt:0.12.5")
            library("kotlinReflect", "org.jetbrains.kotlin", "kotlin-reflect").version("1.9.23")
            library("postgres", "org.postgresql:postgresql:42.7.3")
            library("flyway", "org.flywaydb", "flyway-core").version("10.10.0")
            library("flywayPostgres", "org.flywaydb", "flyway-database-postgresql").version("10.10.0")
            library("jackson", "com.fasterxml.jackson.module", "jackson-module-kotlin").version("2.16.1")
            library("jpaAnnotations", "jakarta.annotation:jakarta.annotation-api:3.0.0")
        }

        create("queryDsl") {
            val group = "io.github.openfeign.querydsl"
            version("queryDsl", "6.1")

            library("core", group, "querydsl-core").versionRef("queryDsl")
            library("apt", group, "querydsl-apt").versionRef("queryDsl")
            library("sql", group, "querydsl-sql").versionRef("queryDsl")
            library("jpa", group, "querydsl-jpa").versionRef("queryDsl")
        }
    }
}

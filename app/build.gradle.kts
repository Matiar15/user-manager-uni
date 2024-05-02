dependencies {
    val spockVersion = "2.4-M4-groovy-4.0"
    implementation(project(":service"))
    implementation(project(":domain"))
    implementation(spring.bundles.springAppModule) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }

    implementation(spring.jpa)
    implementation(libs.jackson)
    implementation(libs.flyway)
    implementation(libs.postgres)

    runtimeOnly(libs.flywayPostgres)

    testImplementation(springTest.springBootTest)
    testImplementation("org.spockframework:spock-spring:$spockVersion")
}
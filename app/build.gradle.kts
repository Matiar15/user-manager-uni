dependencies {
    val spockVersion = "2.4-M4-groovy-4.0"
    implementation(project(":service"))
    implementation(spring.bundles.springAppModule)
    implementation(libs.jackson)
    implementation(libs.flyway)
    implementation(libs.postgres)
    testImplementation(springTest.springBootTest)
    testImplementation("org.spockframework:spock-spring:$spockVersion")
}
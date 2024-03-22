dependencies {
    implementation(project(":service"))
    implementation(spring.bundles.springAppModule)
    implementation(libs.jackson)
    implementation(libs.flyway)
    implementation(libs.postgres)
    testImplementation(springTest.springBootTest)
}
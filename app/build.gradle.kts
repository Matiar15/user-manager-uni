dependencies {
    implementation(project(":service"))
    implementation(spring.bundles.springAppModule)
    implementation(springTest.springBootTest)
    implementation(libs.jackson)
    implementation(libs.flyway)
    implementation(libs.postgres)
}
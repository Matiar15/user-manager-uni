dependencies {
    implementation(project(":domain"))
    implementation(spring.jpa)
    implementation(queryDsl.core)
    implementation(queryDsl.apt)
    implementation(queryDsl.sql)
    implementation(queryDsl.jpa)
    implementation(spring.security)
    implementation(libs.jwt)
}

dependencies {
    implementation(project(":domain"))
    implementation(spring.jpa)
    implementation("io.github.openfeign.querydsl:querydsl-core:6.1")
    implementation("io.github.openfeign.querydsl:querydsl-apt:6.1")
    implementation("io.github.openfeign.querydsl:querydsl-sql:6.1")
    implementation("io.github.openfeign.querydsl:querydsl-jpa:6.1")

}

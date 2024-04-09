dependencies {
    implementation(spring.jpa)
    implementation("io.github.openfeign.querydsl:querydsl-core:6.1")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:6.1:jpa")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:3.0.0")
}

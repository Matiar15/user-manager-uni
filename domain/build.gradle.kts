dependencies {
    implementation(spring.jpa)
    implementation(queryDsl.core)
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:6.1:jpa")
    annotationProcessor(libs.jpaAnnotations)
}

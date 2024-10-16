dependencies {
    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

    //kotlin log
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
}
tasks {
    bootJar {
        isEnabled = false
    }
    jar {
        isEnabled = true
    }
}
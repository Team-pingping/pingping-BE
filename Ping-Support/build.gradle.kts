dependencies {
    implementation(project(":Ping-Common"))
    implementation(project(":Ping-Infra"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
tasks {
    bootJar {
        isEnabled = false
    }
    jar {
        isEnabled = true
    }
}
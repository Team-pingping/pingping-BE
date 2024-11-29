dependencies {
    implementation(project(":Ping-Common"))
    implementation(project(":Ping-Domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //kotlin log
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // QueryDSL
    // 필요 없으면 삭제
//    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
//    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
//    kapt("jakarta.annotation:jakarta.annotation-api")
//    kapt("jakarta.persistence:jakarta.persistence-api")
}
tasks {
    bootJar {
        isEnabled = false
    }
    jar {
        isEnabled = true
    }
}
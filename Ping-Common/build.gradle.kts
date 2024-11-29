dependencies {
    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

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
dependencies {
    implementation(project(":Ping-Common"))

    // Jsoup
    implementation("org.jsoup:jsoup:1.15.4")

    // webclient
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}
tasks {
    bootJar {
        isEnabled = false
    }
    jar {
        isEnabled = true
    }
}

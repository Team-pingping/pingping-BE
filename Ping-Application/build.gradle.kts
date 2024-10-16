dependencies {
    implementation(project(":Ping-Common"))
    implementation(project(":Ping-Domain"))

    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
tasks {
    bootJar {
        isEnabled = true
    }
    jar {
        isEnabled = true
    }
}
dependencies {
    implementation(project(":Ping-Common"))
    implementation(project(":Ping-Domain"))
    implementation(project(":Ping-Client"))
    implementation(project(":Ping-Support"))

    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
tasks {
    bootJar {
        isEnabled = false
    }
    jar {
        isEnabled = true
    }
}
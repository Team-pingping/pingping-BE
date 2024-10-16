dependencies {
    implementation(project(":Ping-Common"))
}
tasks {
    bootJar {
        isEnabled = true
    }
    jar {
        isEnabled = true
    }
}
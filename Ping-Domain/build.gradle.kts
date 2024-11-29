dependencies {
    implementation(project(":Ping-Common"))
    implementation(project(":Ping-Client"))
}
tasks {
    bootJar {
        isEnabled = false
    }
    jar {
        isEnabled = true
    }
}
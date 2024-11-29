plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "pingping-BE"
include("Ping-Api")
include("Ping-Application")
include("Ping-Common")
include("Ping-Domain")
include("Ping-Infra")
include("Ping-Client")
include("Ping-Support")
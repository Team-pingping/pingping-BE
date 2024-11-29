package com.ping.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@ConfigurationPropertiesScan
@EnableMongoRepositories(basePackages = ["com.ping.infra"])
@SpringBootApplication(
    scanBasePackages = [
        "com.ping.api",
        "com.ping.application",
        "com.ping.common",
        "com.ping.domain",
        "com.ping.infra",
        "com.ping.client",
        "com.ping.support",
    ]
)
class PingApplication

fun main(args: Array<String>) {
    runApplication<PingApplication>(*args)
}
package com.ping.support.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secretKey: String
    var accessTokenValidityInSeconds: Long = 0
    var refreshTokenValidityInSeconds: Long = 0
}
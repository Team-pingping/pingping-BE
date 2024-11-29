package com.ping.support.jwt

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class BlacklistManager(private val redisTemplate: RedisTemplate<String, String>) {

    fun isBlacklisted(jti: String): Boolean {
        return redisTemplate.opsForSet().isMember("jti:blacklist", jti) == true
    }

    fun addBlacklistToken(jti: String) {
        redisTemplate.opsForSet().add("jti:blacklist", jti)
    }
}
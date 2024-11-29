package com.ping.support.jwt

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class TokenRepository(private val redisTemplate: RedisTemplate<String, String>) {

    fun saveTokensToRedis(memberId: String, accessToken: String, refreshToken: String, refreshTokenTime: Duration) {
        redisTemplate.opsForValue().set(memberId, refreshToken, refreshTokenTime)
        redisTemplate.opsForValue().set(refreshToken, accessToken, refreshTokenTime)
    }

    fun getRefreshToken(memberId: String): String? {
        return redisTemplate.opsForValue()[memberId]
    }

    fun getAccessToken(refreshToken: String): String? {
        return redisTemplate.opsForValue()[refreshToken]
    }

    fun storeAccessToken(refreshToken: String, accessToken: String, refreshTokenTime: Duration) {
        redisTemplate.opsForValue().set(refreshToken, accessToken, refreshTokenTime)
    }
}

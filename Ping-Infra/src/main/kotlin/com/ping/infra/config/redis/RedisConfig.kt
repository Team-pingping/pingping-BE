package com.ping.infra.config.redis

import io.lettuce.core.ClientOptions
import io.lettuce.core.SocketOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}") private val host: String,
    @Value("\${spring.data.redis.port}") private val port: Int,
    @Value("\${spring.data.redis.password}") private val password: String
) {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build()
        val clientOptions = ClientOptions.builder().socketOptions(socketOptions).build()

        val lettuceClientConfig = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            .commandTimeout(Duration.ofMinutes(1))
            .shutdownTimeout(Duration.ZERO)
            .build()

        val redisStandaloneConfig = RedisStandaloneConfiguration(host, port)

        if (password.isNotEmpty()) {
            redisStandaloneConfig.setPassword(password)
        }
        redisStandaloneConfig.database = 0

        return LettuceConnectionFactory(redisStandaloneConfig, lettuceClientConfig)
    }

    @Bean("redisTemplate")
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            connectionFactory = lettuceConnectionFactory()
            keySerializer = StringRedisSerializer()
            valueSerializer = Jackson2JsonRedisSerializer(Any::class.java)
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = Jackson2JsonRedisSerializer(Any::class.java)
            afterPropertiesSet()
        }
    }
}

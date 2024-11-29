package com.ping.support.jwt

import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenManager(private val jwtProperties: JwtProperties) {

    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun initKey() {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey)
        secretKey = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createAccessToken(memberId: Long, accessTokenTime: Long): String {
        val claims = Jwts.claims().subject(memberId.toString())
        val jti = UUID.randomUUID().toString().substring(0, 16) + memberId.toString()
        val now = Date()

        return Jwts.builder()
            .id(jti)
            .issuer(JwtConst.TOKEN_ISSUER)
            .claims(claims.build())
            .issuedAt(now)
            .expiration(Date(now.time + accessTokenTime))
            .signWith(secretKey)
            .compact()
    }

    fun createRefreshToken(memberId: Long, refreshTokenTime: Long): String {
        val claims = Jwts.claims().subject(memberId.toString())
        val now = Date()

        return Jwts.builder()
            .issuer(JwtConst.TOKEN_ISSUER)
            .claims(claims.build())
            .issuedAt(now)
            .expiration(Date(now.time + refreshTokenTime))
            .signWith(secretKey)
            .compact()
    }

    fun parseClaims(token: String): Claims {
        return try {
            Jwts.parser()
                .requireIssuer(JwtConst.TOKEN_ISSUER)
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .body
        } catch (ex: ExpiredJwtException) {
            ex.claims
        } catch (ex: JwtException) {
            throw CustomException(ExceptionContent.TOKEN_INVALID)
        }
    }
}
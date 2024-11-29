package com.ping.support.jwt

import com.ping.common.exception.CustomException
import com.ping.common.exception.ExceptionContent
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class JwtProvider(
    private val tokenRepository: TokenRepository,
    private val jwtTokenManager: JwtTokenManager,
    private val blacklistManager: BlacklistManager,
    private val jwtProperties: JwtProperties,
) {
    private val accessTokenTime = Duration.ofSeconds(jwtProperties.accessTokenValidityInSeconds)
    private val refreshTokenTime = Duration.ofSeconds(jwtProperties.refreshTokenValidityInSeconds)

    fun issue(memberId: Long): String {
        val newAccessToken = jwtTokenManager.createAccessToken(memberId, accessTokenTime.toMillis())
        val newRefreshToken = jwtTokenManager.createRefreshToken(memberId, refreshTokenTime.toMillis())

        tokenRepository.saveTokensToRedis(memberId.toString(), newAccessToken, newRefreshToken, refreshTokenTime)

        return newAccessToken
    }

    fun reissue(request: HttpServletRequest): String {
        val accessToken = resolveToken(request)

        val claims = jwtTokenManager.parseClaims(accessToken)

        val jti = claims.id
        val memberId = claims.subject

        if (blacklistManager.isBlacklisted(jti)) {
            throw CustomException(ExceptionContent.TOKEN_BLACKLISTED)
        }

        val refreshToken = tokenRepository.getRefreshToken(memberId)
            ?: throw CustomException(ExceptionContent.REFRESH_TOKEN_EXPIRED)

        val existingAccessToken = tokenRepository.getAccessToken(refreshToken)
        if (existingAccessToken.isNullOrEmpty() || existingAccessToken != accessToken) {
            blacklistManager.addBlacklistToken(jti)
            throw CustomException(ExceptionContent.TOKEN_INVALID)
        }

        blacklistManager.addBlacklistToken(jti)

        val newAccessToken = jwtTokenManager.createAccessToken(memberId.toLong(), accessTokenTime.toMillis())

        tokenRepository.storeAccessToken(refreshToken, newAccessToken, refreshTokenTime)

        return newAccessToken
    }

    fun validateToken(request: HttpServletRequest): Boolean {
        val token = resolveToken(request)
        runCatching {
            val claims = jwtTokenManager.parseClaims(token)
            val jti = claims.id

            if (blacklistManager.isBlacklisted(jti)) {
                throw CustomException(ExceptionContent.TOKEN_BLACKLISTED)
            }

            val refreshToken = tokenRepository.getRefreshToken(claims.subject)
                ?: throw CustomException(ExceptionContent.REFRESH_TOKEN_EXPIRED)

            val existingAccessToken = tokenRepository.getAccessToken(refreshToken)
            if (existingAccessToken.isNullOrEmpty() || existingAccessToken != token) {
                blacklistManager.addBlacklistToken(jti)
                throw CustomException(ExceptionContent.TOKEN_INVALID)
            }
            true
        }.getOrElse { exception ->
            handleTokenExceptions(exception)
        }
        return true
    }

    fun resolveToken(request: HttpServletRequest): String {
        val jwtToken = request.getHeader("Authorization") ?: throw CustomException(ExceptionContent.TOKEN_MISSING)

        return if (jwtToken.startsWith("Bearer ")) {
            jwtToken.substring(7)
        } else {
            throw CustomException(ExceptionContent.TOKEN_INVALID)
        }
    }

    private fun handleTokenExceptions(exception: Throwable): Nothing {
        when (exception) {
            is CustomException -> throw exception
            is ExpiredJwtException -> throw CustomException(ExceptionContent.TOKEN_EXPIRED)
            is UnsupportedJwtException, is MalformedJwtException -> throw CustomException(ExceptionContent.TOKEN_INVALID)
            else -> throw CustomException(ExceptionContent.TOKEN_INVALID)
        }
    }

}
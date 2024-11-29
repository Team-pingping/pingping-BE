package com.ping.application.member.service

import com.ping.application.member.dto.TokenResponse
import com.ping.support.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
) {
    @Transactional
    fun reissueToken(request: HttpServletRequest): TokenResponse {
        val newAccessToken = jwtProvider.reissue(request)
        return TokenResponse(accessToken = newAccessToken)
    }
}
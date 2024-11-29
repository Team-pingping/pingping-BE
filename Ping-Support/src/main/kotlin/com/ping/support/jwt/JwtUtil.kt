package com.ping.support.jwt

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class JwtUtil (
    private val jwtProvider: JwtProvider,
    private val jwtTokenManager: JwtTokenManager,
){
    fun getNonMemberId(request: HttpServletRequest): Long {
        val accessToken = jwtProvider.resolveToken(request)
        val claims = jwtTokenManager.parseClaims(accessToken)
        val memberId = claims.subject
        return memberId.toLong()
    }
}
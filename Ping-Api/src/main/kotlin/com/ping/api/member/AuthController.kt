package com.ping.api.member

import com.ping.application.member.dto.TokenResponse
import com.ping.application.member.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping(AuthApi.REISSUE)
    fun reissueToken(request: HttpServletRequest): TokenResponse {
        val newAccessToken = authService.reissueToken(request)
        return newAccessToken
    }
}
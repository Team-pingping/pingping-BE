package com.pingping.member.api

import com.pingping.member.application.NonMemberService
import com.pingping.member.dto.request.NonMemberLoginRequest
import com.pingping.global.common.CommonResponse
import com.pingping.member.dto.response.NonMemberLoginResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "비회원")
@RestController
@RequestMapping("/non-member")
class NonMemberController(
    private val nonMemberService: NonMemberService
) {

    @PutMapping("/login")
    fun loginNonMember(
        @RequestBody request: NonMemberLoginRequest
    ): ResponseEntity<CommonResponse<NonMemberLoginResponse>> {
        val response = nonMemberService.loginNonMember(request)

        return ResponseEntity.ok(
            CommonResponse.of(HttpStatus.OK, "비회원 로그인 성공", response)
        )
    }
}
package com.pingping.member.dto.request

data class NonMemberLoginRequest(
    val nonMemberId: Long,
    val password: String
)

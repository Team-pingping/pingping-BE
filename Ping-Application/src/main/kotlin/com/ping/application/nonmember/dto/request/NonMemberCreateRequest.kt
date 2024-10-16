package com.ping.application.nonmember.dto.request

data class NonMemberCreateRequest(
    val shareUrlId: Long,
    val name: String,
    val password: String
)

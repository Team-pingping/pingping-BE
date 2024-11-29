package com.ping.application.member.dto

interface LoginTokenNonMember {
    data class Response(
        val nonMemberId: Long,
        val name: String,
        val accessToken: String,
    )
}
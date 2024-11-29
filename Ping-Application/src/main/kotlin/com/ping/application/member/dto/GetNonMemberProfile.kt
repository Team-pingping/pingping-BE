package com.ping.application.member.dto

interface GetNonMemberProfile {
    data class Response (
        val name: String,
        val profileSvg: String,
        val profileLockSvg: String,
    )
}
package com.ping.api.member

object NonMemberApi {
    const val BASE_URL = "/nonmembers"
    const val LOGIN = "$BASE_URL/login"
    const val LOGIN_TOKEN = "$BASE_URL/login-token"
    const val NONMEMBER_ID = "$BASE_URL/{nonMemberId}"
}
package com.ping.api.ping

object PingApi {
    const val BASE_URL = "/pings"
    const val BOOKMARK = "$BASE_URL/bookmark"
    const val STORE = "$BASE_URL/store"
    const val PING_NONMEMBER_ID = "$BASE_URL/{nonMemberId}"
    const val PING_REFRESH_ALL = "$BASE_URL/refresh-all"
    const val PING_RECOMMEND = "$BASE_URL/recommend"
    const val PING_MEMBER = "$BASE_URL/member"
}
package com.ping.application.ping.dto

interface UpdateNonMemberPings {
    data class Request(
        val nonMemberId: Long,
        val bookmarkUrls: List<String>,
        val storeUrls: List<String>,
    )
}
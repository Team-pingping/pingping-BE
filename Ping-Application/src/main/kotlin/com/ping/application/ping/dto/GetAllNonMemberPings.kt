package com.ping.application.ping.dto

interface GetAllNonMemberPings {
    data class Response(
        val eventName: String,
        val neighborhood: String,
        val px: Double,
        val py: Double,
        val pingLastUpdateTime: String?,
        val recommendPings: List<GetRecommendPings.RecommendPing>,
        val nonMembers: List<NonMember>,
        val pings: List<Ping>,
    )

    data class NonMember(
        val nonMemberId: Long,
        val name: String,
        val profileSvg: String,
    )

    data class Ping(
        val iconLevel: Int,
        val sid: String,
        val nonMembers: List<NonMember>,
        val url: String,
        val placeName: String,
        val px: Double,
        val py: Double,
        val type: String,
    )
}